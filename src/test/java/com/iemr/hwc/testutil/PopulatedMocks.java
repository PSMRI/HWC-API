package com.iemr.hwc.testutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * Creates collaborator mocks that answer with populated results instead of nulls.
 *
 * <p>The wide services in this code base take dozens of repositories and mappers and then
 * dereference whatever comes back. Handing them mocks that return fully built objects lets a
 * test drive the real branches without stubbing every lookup by hand.
 */
public final class PopulatedMocks {

	private PopulatedMocks() {
	}

	/** Answers every call with a populated instance of its return type. */
	public static final Answer<Object> POPULATED_RESULTS = invocation -> {
		Method method = invocation.getMethod();
		Class<?> returnType = method.getReturnType();
		if (returnType == void.class) {
			return null;
		}
		// Repository save() and friends are generic, so their erased return type is Object;
		// echoing the argument back is what the real call does.
		if (returnType == Object.class && invocation.getArguments().length == 1) {
			return invocation.getArgument(0);
		}
		// The rows a native query returns are built from that query's own recorded layout, so
		// which query is answering has to be known before the rows are made.
		RowLayouts.answering(method.getDeclaringClass().getName() + "." + method.getName());
		Object value = ReflectiveFiller.build(returnType, method.getGenericReturnType(), 0, new ArrayDeque<>());
		if (value == null && returnType.isPrimitive()) {
			return Mockito.RETURNS_DEFAULTS.answer(invocation);
		}
		return value;
	};

	/**
	 * True for a logging facade. Every class here logs before and after its real work, often
	 * outside the try block, so a mocked logger would decide the outcome of the call instead
	 * of the collaborator under test.
	 */
	public static boolean isLogger(Class<?> type) {
		String name = type.getName();
		return name.startsWith("org.slf4j.") || name.startsWith("org.apache.log") || name.startsWith("java.util.logging.");
	}

	/** A mock of {@code type} whose calls answer with populated results. */
	public static <T> T of(Class<T> type) {
		return Mockito.mock(type, Mockito.withSettings().defaultAnswer(POPULATED_RESULTS));
	}

	/**
	 * Puts a populated mock of {@code type} into the named field of {@code target}.
	 *
	 * <p>These services take their collaborators through dozens of injected fields, so a
	 * test that wants to steer one of them names it here and lets
	 * {@link #injectCollaborators(Object)} fill in the rest.
	 */
	public static <T> T inject(Object target, String field, Class<T> type) {
		T mock = of(type);
		org.springframework.test.util.ReflectionTestUtils.setField(target, field, mock);
		return mock;
	}

	/**
	 * Wires {@code target} for a unit test: every collaborator still null becomes a populated
	 * mock, every {@code @Value} setting gets a usable value, and the REST clients are
	 * replaced outright so no call reaches the network.
	 */
	public static void injectCollaborators(Object target) {
		for (Class<?> current = target.getClass(); current != null
				&& current != Object.class; current = current.getSuperclass()) {
			for (Field field : current.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
						|| field.getType().isPrimitive() || isLogger(field.getType())) {
					continue;
				}
				try {
					field.setAccessible(true);
					if (isRestClient(field.getType())) {
						// A real RestTemplate would try to reach the configured host, so it is
						// replaced whether or not the class built one for itself.
						field.set(target, of(field.getType()));
						continue;
					}
					if (field.get(target) != null) {
						continue;
					}
					if (isSetting(field.getType())) {
						field.set(target, setting(field));
						continue;
					}
					if (field.getType().isEnum() || field.getType().getName().startsWith("java.")) {
						continue;
					}
					field.set(target, of(field.getType()));
				} catch (Throwable ignored) {
					// a collaborator that cannot be mocked is left as it is
				}
			}
		}
	}

	private static boolean isRestClient(Class<?> type) {
		String name = type.getName();
		return name.equals("org.springframework.web.client.RestTemplate")
				|| name.equals("org.springframework.web.client.RestClient")
				|| name.equals("org.springframework.web.reactive.function.client.WebClient");
	}

	/** True for the field types the {@code @Value} settings use. */
	private static boolean isSetting(Class<?> type) {
		return type == String.class || type == Integer.class || type == Long.class || type == Short.class
				|| type == Double.class || type == Boolean.class;
	}

	/**
	 * A value for a {@code @Value} setting. URLs have to parse as absolute for the REST calls
	 * that build a URI from them, so a field whose name reads like a location gets one.
	 */
	private static Object setting(Field field) {
		Class<?> type = field.getType();
		if (type == Integer.class) {
			return 10;
		}
		if (type == Long.class) {
			return 10L;
		}
		if (type == Short.class) {
			return (short) 10;
		}
		if (type == Double.class) {
			return 10.0d;
		}
		if (type == Boolean.class) {
			return Boolean.TRUE;
		}
		String name = field.getName().toLowerCase();
		if (name.contains("url") || name.contains("uri") || name.contains("endpoint") || name.contains("host")) {
			return "http://localhost:8080/" + field.getName();
		}
		return "1";
	}
}
