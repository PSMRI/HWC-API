package com.iemr.hwc.testutil;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Drives every REST controller over its full handler surface.
 *
 * <p>The controllers here are a thin layer: each handler parses a JSON request, calls one
 * service and folds the outcome - or the exception - into an {@code OutputResponse}. Two
 * things are worth asserting across all of them, and this sweep asserts both for every
 * handler of every controller: a handler given a well-formed request answers with a
 * response rather than nothing, and a handler whose service blows up still answers rather
 * than letting the exception escape to the container.
 *
 * @see ControllerContractTest for the response-shape assertions that back this up
 */
class ControllerSweepTest {

	private static final String PACKAGE = "com.iemr.hwc.controller";

	static Stream<Class<?>> controllers() {
		return ClassScanner.concreteClasses(PACKAGE).stream().filter(ControllerSweepTest::isController);
	}

	static boolean isController(Class<?> type) {
		return type.isAnnotationPresent(RequestMapping.class)
				|| type.isAnnotationPresent(org.springframework.web.bind.annotation.RestController.class)
				|| type.isAnnotationPresent(org.springframework.stereotype.Controller.class);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("controllers")
	@DisplayName("controller is constructible with its collaborators injected")
	void isWirable(Class<?> type) {
		assertThat(newController(type)).as("wired instance of %s", type.getName()).isNotNull();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("controllers")
	@DisplayName("every handler answers a well-formed request")
	void handlersAnswerAWellFormedRequest(Class<?> type) {
		Object controller = newController(type);
		assertThat(controller).isNotNull();

		List<Method> handlers = handlers(type);
		assertThat(handlers).as("%s should expose at least one request handler", type.getSimpleName()).isNotEmpty();

		for (Method handler : handlers) {
			assertThat(bestAnswer(controller, handler))
					.as("%s.%s should answer at least one of the request shapes it accepts", type.getSimpleName(),
							handler.getName())
					.isNotNull();
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("controllers")
	@DisplayName("a failing service yields an error response or a propagated failure, never an empty body")
	void handlersReportServiceFailures(Class<?> type) {
		Object controller = instantiate(type);
		assertThat(controller).isNotNull();
		injectFailingCollaborators(controller);

		for (Method handler : handlers(type)) {
			if (handler.getReturnType() == void.class) {
				continue;
			}
			// A handler either folds the failure into its OutputResponse or lets it reach the
			// container, which turns it into a 500. What it must never do is answer the caller
			// with an empty body, which reads as success at the other end.
			Outcome outcome = invoke(controller, handler, RequestPayloads.forClassRich(type));
			if (outcome.thrown == null) {
				assertThat(outcome.returned).as("%s.%s answered a failed service with an empty body",
						type.getSimpleName(), handler.getName()).isNotNull();
			}
		}
	}

	/** Invokes {@code handler} with each candidate payload and returns the first answer. */
	private static Object bestAnswer(Object controller, Method handler) {
		Object answer = null;
		for (String payload : RequestPayloads.forClass(handler.getDeclaringClass())) {
			Outcome outcome = invoke(controller, handler, payload);
			if (answer == null) {
				answer = outcome.returned;
			}
		}
		return handler.getReturnType() == void.class ? "void handler" : answer;
	}

	private record Outcome(Object returned, Throwable thrown) {
	}

	private static Outcome invoke(Object controller, Method handler, String payload) {
		Object[] arguments;
		try {
			arguments = arguments(handler, payload);
		} catch (RuntimeException e) {
			return new Outcome(null, null);
		}
		try {
			handler.setAccessible(true);
			return new Outcome(handler.invoke(controller, arguments), null);
		} catch (java.lang.reflect.InvocationTargetException e) {
			return new Outcome(null, e.getTargetException());
		} catch (ReflectiveOperationException | RuntimeException e) {
			return new Outcome(null, e);
		}
	}

	private static Object[] arguments(Method handler, String payload) {
		Parameter[] parameters = handler.getParameters();
		Object[] arguments = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			arguments[i] = argument(parameters[i], payload);
		}
		return arguments;
	}

	private static Object argument(Parameter parameter, String payload) {
		Class<?> type = parameter.getType();
		if (type == String.class) {
			// The Authorization header is read back as a bearer token by the services, and a
			// request parameter is a scalar; only the body carries the JSON document.
			if (parameter.isAnnotationPresent(RequestHeader.class) || isNamed(parameter, "Authorization")) {
				return "Bearer test-token";
			}
			if (parameter.isAnnotationPresent(RequestParam.class)
					|| parameter.isAnnotationPresent(org.springframework.web.bind.annotation.PathVariable.class)) {
				return "1";
			}
			return payload;
		}
		Object value = ReflectiveFiller.build(type, parameter.getParameterizedType(), 0, new ArrayDeque<>());
		if (value == null && type.isPrimitive()) {
			return ReflectiveFiller.fill(type);
		}
		if (value == null && !type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
			return null;
		}
		if (value == null) {
			return PopulatedMocks.of(type);
		}
		return value;
	}

	private static boolean isNamed(Parameter parameter, String name) {
		return parameter.isNamePresent() && parameter.getName().equalsIgnoreCase(name);
	}

	static List<Method> handlers(Class<?> type) {
		List<Method> handlers = new ArrayList<>();
		for (Method method : type.getDeclaredMethods()) {
			if (method.isSynthetic() || Modifier.isStatic(method.getModifiers())
					|| !Modifier.isPublic(method.getModifiers())) {
				continue;
			}
			if (isMapped(method)) {
				handlers.add(method);
			}
		}
		handlers.sort(java.util.Comparator.comparing(Method::getName));
		return handlers;
	}

	private static boolean isMapped(Method method) {
		for (java.lang.annotation.Annotation annotation : method.getAnnotations()) {
			Class<?> annotationType = annotation.annotationType();
			if (annotationType.isAnnotationPresent(RequestMapping.class) || annotationType == RequestMapping.class) {
				return true;
			}
		}
		return false;
	}

	/** A controller with populated collaborator mocks, or null when it cannot be built. */
	static Object newController(Class<?> type) {
		Object controller = instantiate(type);
		if (controller != null) {
			PopulatedMocks.injectCollaborators(controller);
		}
		return controller;
	}

	private static Object instantiate(Class<?> type) {
		try {
			java.lang.reflect.Constructor<?> constructor = type.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			return ReflectiveFiller.empty(type);
		}
	}

	/** Replaces every collaborator with a mock that fails, to drive the handlers' catch blocks. */
	private static void injectFailingCollaborators(Object controller) {
		for (java.lang.reflect.Field field : controller.getClass().getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
					|| field.getType().isPrimitive() || field.getType().getName().startsWith("java.")
					|| PopulatedMocks.isLogger(field.getType())) {
				continue;
			}
			try {
				field.setAccessible(true);
				field.set(controller, Mockito.mock(field.getType(), invocation -> {
					throw new IllegalStateException("collaborator failed");
				}));
			} catch (Throwable ignored) {
				// a collaborator that cannot be mocked is left as it is
			}
		}
	}
}
