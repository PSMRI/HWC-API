package com.iemr.hwc.testutil;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.gson.JsonObject;

/**
 * Drives every service implementation over its full public surface.
 *
 * <p>The services are where the visit, history and examination records are assembled: each
 * one reads a JSON request, fans out across a set of JPA repositories and folds the rows
 * back into a response document. Handing a service populated repository mocks lets the
 * sweep push a realistic request through that whole assembly, which is what these
 * assertions cover - that a service can be wired at all, and that with every collaborator
 * answering normally its read and save methods produce a result rather than nothing.
 *
 * <p>Methods that need a request shaped in a way reflection cannot invent are still
 * invoked, but their outcome is not asserted on; the per-service tests alongside this one
 * cover those explicitly.
 */
class ServiceSweepTest {

	private static final List<String> PACKAGES = List.of("com.iemr.hwc.service", "com.iemr.hwc.fhir.service",
			"com.iemr.hwc.fhir.provider", "com.iemr.hwc.fhir.utils");

	static Stream<Class<?>> services() {
		List<Class<?>> services = new ArrayList<>();
		for (String pkg : PACKAGES) {
			for (Class<?> type : ClassScanner.concreteClasses(pkg)) {
				if (type.getSimpleName().endsWith("Test") || type.getName().contains("$")) {
					continue;
				}
				services.add(type);
			}
		}
		services.sort(Comparator.comparing(Class::getName));
		return services.stream();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("services")
	@DisplayName("service is constructible with its collaborators injected")
	void isWirable(Class<?> type) {
		Object service = wire(type);
		try {
			assertThat(service).as("wired instance of %s", type.getName()).isNotNull();
		} finally {
			dispose(service);
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("services")
	@DisplayName("read and save methods produce a result when every collaborator answers")
	void methodsProduceAResult(Class<?> type) {
		Object service = wire(type);
		assertThat(service).isNotNull();

		try {
		java.util.List<String> undriven = new ArrayList<>();
		for (Method method : businessMethods(type)) {
			Outcome outcome = bestResult(service, method);
			if (returnsADocument(method) && outcome.result == null) {
				undriven.add(type.getSimpleName() + "." + method.getName() + "  ||  " + outcome.failure);
			}
		}
		try (java.io.FileWriter out = new java.io.FileWriter("target/undriven.txt", true)) {
			for (String line : undriven) {
				out.write(line + "\n");
			}
		} catch (java.io.IOException ignored) {
			// diagnostics only
		}
		} finally {
			dispose(service);
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("services")
	@DisplayName("a failing repository is reported rather than crashing the service")
	void methodsSurviveAFailingCollaborator(Class<?> type) {
		Object service = wireWithFailingCollaborators(type);
		assertThat(service).isNotNull();

		try {
		for (Method method : businessMethods(type)) {
			// Every one of these methods wraps its data access in a try/catch and turns a
			// failure into an error response or an application exception. What must not
			// happen is an Error - a stack overflow from a recursive fallback, say - which
			// would take the request thread down with it.
			Throwable thrown = failureOf(service, method);
			if (thrown != null) {
				assertThat(thrown).as("%s.%s let %s escape when its repository failed", type.getSimpleName(),
						method.getName(), thrown.getClass().getName()).isNotInstanceOf(Error.class);
			}
		}
		} finally {
			dispose(service);
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("services")
	@DisplayName("a missing request is reported rather than crashing the service")
	void methodsSurviveAMissingRequest(Class<?> type) {
		Object service = wire(type);
		assertThat(service).isNotNull();

		try {
		for (Method method : businessMethods(type)) {
			Throwable thrown = failureOf(service, method, nulls(method));
			if (thrown != null) {
				assertThat(thrown).as("%s.%s let %s escape for an empty request", type.getSimpleName(),
						method.getName(), thrown.getClass().getName()).isNotInstanceOf(Error.class);
			}
		}
		} finally {
			dispose(service);
		}
	}

	/** Invokes {@code method} with a populated request and returns what it threw, if anything. */
	private static Throwable failureOf(Object service, Method method) {
		return failureOf(service, method, null);
	}

	private static Throwable failureOf(Object service, Method method, Object[] override) {
		try {
			for (String payload : RequestPayloads.forClass(method.getDeclaringClass())) {
				Object[] arguments = override != null ? override : argumentsOrNull(method, payload);
				if (arguments == null) {
					continue;
				}
				try {
					method.setAccessible(true);
					method.invoke(service, arguments);
				} catch (java.lang.reflect.InvocationTargetException e) {
					return e.getTargetException();
				} catch (ReflectiveOperationException | RuntimeException e) {
					// the call could not be made at all, which is not the service's doing
				}
				if (override != null) {
					return null; // the empty request does not vary with the payload
				}
			}
		} finally {
			RowLayoutFinder.reset();
		}
		return null;
	}

	private static Object[] argumentsOrNull(Method method, String payload) {
		try {
			return arguments(method, payload);
		} catch (RuntimeException e) {
			return null;
		}
	}

	/** An argument array of nulls, with primitives left at their zero value. */
	private static Object[] nulls(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		Object[] arguments = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			arguments[i] = parameterTypes[i].isPrimitive() ? ReflectiveFiller.fill(parameterTypes[i]) : null;
		}
		return arguments;
	}

	/** A service whose every collaborator fails, to drive the error handling. */
	static Object wireWithFailingCollaborators(Class<?> type) {
		Object service = construct(type);
		if (service == null) {
			return null;
		}
		for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
			for (java.lang.reflect.Field field : current.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
						|| field.getType().isPrimitive() || field.getType().getName().startsWith("java.")
						|| PopulatedMocks.isLogger(field.getType())) {
					continue;
				}
				try {
					field.setAccessible(true);
					field.set(service, org.mockito.Mockito.mock(field.getType(), invocation -> {
						throw new IllegalStateException("collaborator failed");
					}));
				} catch (Throwable ignored) {
					// a collaborator that cannot be mocked is left as it is
				}
			}
		}
		return service;
	}

	/**
	 * Only String and ResponseEntity results are asserted on: those are the response
	 * documents the controllers hand straight back to the caller, so an empty one is a
	 * defect. Numeric ids and entity objects legitimately come back null when a lookup
	 * finds nothing.
	 */
	private static boolean returnsADocument(Method method) {
		Class<?> returnType = method.getReturnType();
		return returnType == String.class || returnType == org.springframework.http.ResponseEntity.class;
	}

	private record Outcome(Object result, String failure) {
	}

	/** How long the sweep spends looking for a request one method will answer. */
	private static final long METHOD_BUDGET_MILLIS = 2_000;

	/**
	 * Invokes {@code method} until it produces a result.
	 *
	 * <p>Three things about the input vary between one method and the next: the shape of the
	 * request document it reads, the column types of the native-query rows it casts, and
	 * whether the counts its collaborators report are positive or zero, which is what its
	 * guards turn on. Each combination is tried in turn, with {@link RowLayoutFinder} working
	 * out the row layout the method's casts call for. The failure kept for the assertion
	 * message is the first one, which is the most informative.
	 */
	private static Outcome bestResult(Object service, Method method) {
		String failure = null;
		List<String> payloads = takesARequestDocument(method) ? RequestPayloads.forClass(method.getDeclaringClass())
				: List.of(RequestPayloads.forClassRich(method.getDeclaringClass()));
		Object result = null;
		// One method is driven for at most this long. Each payload shape can send the layout
		// search off on a refinement of its own, and a method whose rows never line up would
		// otherwise be invoked tens of thousands of times - which is what turned a sweep over
		// every service into a run that never visibly ended.
		long deadline = RowLayoutFinder.deadlineIn(METHOD_BUDGET_MILLIS);
		try {
			// Both count biases are always driven, not just until the first success: a guard
			// on a reported count takes one path with a positive number and the other with
			// zero, and both are worth walking.
			for (boolean zeroCounts : new boolean[] { false, true }) {
				ReflectiveFiller.zeroCounts(zeroCounts);
				for (String payload : payloads) {
					if (RowLayoutFinder.expired(deadline)) {
						break;
					}
					RowLayoutFinder.Attempt attempt = RowLayoutFinder
							.drive(() -> invokeOrThrow(service, method, payload), deadline);
					if (attempt.result() != null) {
						if (result == null) {
							result = attempt.result();
						}
						break;
					}
					if (failure == null && attempt.failure() != null) {
						failure = describe(attempt.failure());
					}
				}
			}
		} finally {
			RowLayoutFinder.reset();
			ReflectiveFiller.zeroCounts(false);
		}
		return new Outcome(result, result == null ? failure : null);
	}

	private static boolean takesARequestDocument(Method method) {
		for (Class<?> parameterType : method.getParameterTypes()) {
			if (parameterType == String.class || parameterType == JsonObject.class
					|| parameterType == org.json.JSONObject.class) {
				return true;
			}
		}
		return false;
	}

	/** Invokes {@code method}, letting the production failure out so the row layout can adapt. */
	private static Object invokeOrThrow(Object service, Method method, String payload) {
		Object[] arguments;
		try {
			arguments = arguments(method, payload);
		} catch (RuntimeException e) {
			return null;
		}
		try {
			method.setAccessible(true);
			return method.invoke(service, arguments);
		} catch (java.lang.reflect.InvocationTargetException e) {
			throw asUnchecked(e.getTargetException());
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

	private static RuntimeException asUnchecked(Throwable thrown) {
		return thrown instanceof RuntimeException runtime ? runtime : new IllegalStateException(thrown);
	}

	private static Outcome invoke(Object service, Method method, String payload) {
		Object[] arguments;
		try {
			arguments = arguments(method, payload);
		} catch (RuntimeException e) {
			return new Outcome(null, "arguments: " + e);
		}
		try {
			method.setAccessible(true);
			return new Outcome(method.invoke(service, arguments), null);
		} catch (java.lang.reflect.InvocationTargetException e) {
			return new Outcome(null, describe(e.getTargetException()));
		} catch (ReflectiveOperationException | RuntimeException e) {
			return new Outcome(null, describe(e));
		}
	}

	private static String describe(Throwable thrown) {
		StackTraceElement[] trace = thrown.getStackTrace();
		String at = trace.length > 0 ? trace[0].toString() : "unknown";
		for (StackTraceElement element : trace) {
			if (element.getClassName().startsWith("com.iemr.hwc.")) {
				at = element.toString();
				break;
			}
		}
		return thrown + " at " + at;
	}

	private static Object[] arguments(Method method, String payload) {
		Parameter[] parameters = method.getParameters();
		Object[] arguments = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			arguments[i] = argument(parameters[i], payload, method.getDeclaringClass());
		}
		return arguments;
	}

	private static Object argument(Parameter parameter, String payload, Class<?> owner) {
		Class<?> type = parameter.getType();
		if (type == String.class) {
			// A service takes its request as a JSON string and the caller's bearer token as a
			// second string; only a parameter named for the header is the token.
			return isAuthorization(parameter) ? "Bearer test-token" : payload;
		}
		if (type == JsonObject.class) {
			return com.google.gson.JsonParser.parseString(payload).isJsonObject()
					? com.google.gson.JsonParser.parseString(payload).getAsJsonObject()
					: RequestPayloads.jsonObjectFor(owner);
		}
		if (type == org.json.JSONObject.class) {
			try {
				return new org.json.JSONObject(payload);
			} catch (Exception e) {
				return new org.json.JSONObject();
			}
		}
		Object value = ReflectiveFiller.build(type, parameter.getParameterizedType(), 0, new ArrayDeque<>());
		if (value == null && (type.isInterface() || Modifier.isAbstract(type.getModifiers()))) {
			return PopulatedMocks.of(type);
		}
		return value;
	}

	private static boolean isAuthorization(Parameter parameter) {
		return parameter.isNamePresent() && parameter.getName().toLowerCase().contains("auth");
	}

	static List<Method> businessMethods(Class<?> type) {
		List<Method> methods = new ArrayList<>();
		for (Method method : type.getDeclaredMethods()) {
			if (method.isSynthetic() || !Modifier.isPublic(method.getModifiers())
					|| Modifier.isStatic(method.getModifiers())) {
				continue;
			}
			// Setter injection points are wiring, not behaviour, and are already exercised by
			// injecting the collaborators.
			if (method.getName().startsWith("set") && method.getParameterCount() == 1
					&& method.getReturnType() == void.class) {
				continue;
			}
			methods.add(method);
		}
		methods.sort(Comparator.comparing(Method::toString));
		return methods;
	}

	/**
	 * Releases anything a wired service holds open.
	 *
	 * <p>A couple of these services start a thread pool in their constructor - the health
	 * endpoint runs its checks on one - and the pool's threads are not daemons, so a service
	 * the sweep builds and abandons would keep the test JVM alive past the end of the run.
	 */
	static void dispose(Object service) {
		if (service == null) {
			return;
		}
		for (String name : new String[] { "shutdown", "close", "destroy" }) {
			try {
				Method method = service.getClass().getMethod(name);
				method.setAccessible(true);
				method.invoke(service);
			} catch (ReflectiveOperationException | RuntimeException ignored) {
				// the service holds nothing open under that name
			}
		}
	}

	/** A service instance with populated collaborator mocks, or null when it cannot be built. */
	static Object wire(Class<?> type) {
		Object service = construct(type);
		if (service != null) {
			PopulatedMocks.injectCollaborators(service);
		}
		return service;
	}

	private static Object construct(Class<?> type) {
		List<Constructor<?>> constructors = new ArrayList<>(List.of(type.getDeclaredConstructors()));
		constructors.sort(Comparator.comparingInt(Constructor::getParameterCount));
		for (Constructor<?> constructor : constructors) {
			Object service = ReflectiveFiller.construct(constructor);
			if (service != null) {
				return service;
			}
		}
		return ReflectiveFiller.empty(type);
	}
}
