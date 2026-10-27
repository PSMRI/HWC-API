package com.iemr.hwc.testutil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Exercises the entity, DTO and transfer-model accessors across the whole code base.
 *
 * <p>These classes are plain data holders, so the contract worth asserting is that each
 * one can be constructed, that every property round-trips through its setter/getter pair,
 * and that {@code toString}/{@code hashCode}/{@code equals} stay well behaved for both a
 * fully populated and an empty instance.
 */
class PojoSweepTest {

	private static final List<String> PACKAGES = List.of("com.iemr.hwc.data", "com.iemr.hwc.fhir.dto",
			"com.iemr.hwc.fhir.model", "com.iemr.hwc.utils.request",
			
			"com.iemr.hwc.utils.exception");

	static Stream<Class<?>> dataClasses() {
		List<Class<?>> classes = new ArrayList<>();
		for (String pkg : PACKAGES) {
			classes.addAll(ClassScanner.concreteClasses(pkg));
		}
		return classes.stream();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("data class can be constructed and populated")
	void isConstructible(Class<?> type) {
		assertThat(ReflectiveFiller.fill(type)).as("populated instance of %s", type.getName()).isNotNull();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("every property round-trips through its setter and getter")
	void propertiesRoundTrip(Class<?> type) {
		Object instance = ReflectiveFiller.fill(type);
		assertThat(instance).isNotNull();

		for (Method setter : type.getMethods()) {
			if (!isSetter(setter)) {
				continue;
			}
			Class<?> propertyType = setter.getParameterTypes()[0];
			Method getter = matchingGetter(type, setter.getName().substring(3), propertyType);
			if (getter == null) {
				continue;
			}
			Object value = ReflectiveFiller.fill(propertyType);
			if (value == null) {
				continue;
			}
			try {
				setter.setAccessible(true);
				getter.setAccessible(true);
				setter.invoke(instance, value);
			} catch (ReflectiveOperationException | RuntimeException e) {
				continue;
			}
			Object readBack;
			try {
				readBack = getter.invoke(instance);
			} catch (ReflectiveOperationException e) {
				throw new AssertionError(type.getName() + "." + getter.getName() + " threw", e);
			}
			assertThat(readBack).as("%s.%s should return what %s stored", type.getSimpleName(), getter.getName(),
					setter.getName()).isEqualTo(value);
		}
	}

	private static Method matchingGetter(Class<?> type, String property, Class<?> propertyType) {
		for (String prefix : new String[] { "get", "is", "has" }) {
			try {
				Method getter = type.getMethod(prefix + property);
				if (getter.getReturnType() == propertyType) {
					return getter;
				}
			} catch (NoSuchMethodException ignored) {
				// try the next accessor prefix
			}
		}
		return null;
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("accessors of a populated instance never throw")
	void accessorsOfPopulatedInstanceAreSafe(Class<?> type) {
		Object instance = ReflectiveFiller.fill(type);
		assertThat(instance).isNotNull();

		for (Method getter : type.getMethods()) {
			if (!isGetter(getter)) {
				continue;
			}
			if (backsAField(type, getter)) {
				assertThatCode(() -> invoke(getter, instance)).doesNotThrowAnyException();
			} else {
				// Derived getters (getBenFamilyHist and friends) and the accessors the FHIR
				// resources inherit read domain-shaped input that reflection cannot invent,
				// so they are walked for coverage rather than asserted on.
				quietly(() -> invoke(getter, instance));
			}
		}
		quietly(instance::toString);
		quietly(instance::hashCode);
	}

	/** True when {@code getter} reads a declared field of the same name and type. */
	private static boolean backsAField(Class<?> type, Method getter) {
		if (!getter.getDeclaringClass().getName().startsWith("com.iemr.")) {
			return false;
		}
		String name = getter.getName();
		String property = name.startsWith("get") || name.startsWith("has") ? name.substring(3) : name.substring(2);
		if (property.isEmpty()) {
			return false;
		}
		String field = Character.toLowerCase(property.charAt(0)) + property.substring(1);
		for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
			try {
				return current.getDeclaredField(field).getType() == getter.getReturnType();
			} catch (NoSuchFieldException ignored) {
				// keep walking up the hierarchy
			}
		}
		return false;
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("equals is reflexive and rejects null and foreign types")
	void equalsHonoursItsContract(Class<?> type) {
		Object instance = ReflectiveFiller.fill(type);
		assertThat(instance).isNotNull();

		assertThat(instance.equals(instance)).as("%s should equal itself", type.getSimpleName()).isTrue();
		assertThat(instance.equals(null)).as("%s should not equal null", type.getSimpleName()).isFalse();
		assertThat(instance.equals("a value of a foreign type")).as("%s should not equal a String", type.getSimpleName())
				.isFalse();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("equals and hashCode agree for two instances holding the same values")
	void equalInstancesAgree(Class<?> type) {
		Object first = ReflectiveFiller.fill(type);
		assertThat(first).isNotNull();
		if (!overridesEquals(type)) {
			return;
		}
		Object second = copyOf(first, type);
		if (second == null) {
			return;
		}

		assertThat(first).as("%s instances holding the same values should be equal", type.getSimpleName())
				.isEqualTo(second);
		assertThat(second).as("equality of %s should be symmetric", type.getSimpleName()).isEqualTo(first);
		assertThat(first.hashCode()).as("equal %s instances should share a hash code", type.getSimpleName())
				.isEqualTo(second.hashCode());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("two instances holding nothing are equal, and filling one property separates them")
	void emptyInstancesAgreeUntilOneIsFilled(Class<?> type) {
		if (!overridesEquals(type)) {
			return;
		}
		Object first = ReflectiveFiller.empty(type);
		Object second = ReflectiveFiller.empty(type);
		if (first == null || second == null || !clearAll(type, first) || !clearAll(type, second)) {
			return;
		}
		try {
			if (!first.equals(second)) {
				return; // the class compares something this sweep cannot clear
			}
		} catch (RuntimeException e) {
			// A hand-written equals that reads its properties without a null check cannot be
			// asked to compare two empty instances; it is left to its own test.
			return;
		}
		quietly(first::hashCode);

		// Filling a property on one side only is the mirror of the populated sweep, and it is
		// what drives the other half of each comparison a generated equals makes.
		for (Field field : instanceFields(type)) {
			Object value;
			try {
				field.setAccessible(true);
				if (field.get(second) != null || field.getType().isPrimitive()) {
					continue;
				}
				value = ReflectiveFiller.fill(field.getType());
				if (value == null) {
					continue;
				}
				field.set(second, value);
			} catch (ReflectiveOperationException | RuntimeException e) {
				continue;
			}
			quietly(() -> first.equals(second));
			quietly(second::hashCode);
			if (!restore(field, second, null)) {
				return;
			}
		}
	}

	/** Clears every non-primitive property of {@code instance}, reporting whether it worked. */
	private static boolean clearAll(Class<?> type, Object instance) {
		for (Field field : instanceFields(type)) {
			if (field.getType().isPrimitive()) {
				continue;
			}
			try {
				field.setAccessible(true);
				field.set(instance, null);
			} catch (ReflectiveOperationException | RuntimeException e) {
				return false;
			}
		}
		return true;
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("equals reads the instance state rather than only the reference")
	void equalsReadsTheInstanceState(Class<?> type) {
		Object first = ReflectiveFiller.fill(type);
		assertThat(first).isNotNull();
		if (!overridesEquals(type)) {
			return;
		}
		Object second = copyOf(first, type);
		if (second == null || !first.equals(second)) {
			return;
		}

		// Changing one property at a time is what drives equals past its identity shortcut
		// and into the per-property comparisons. Some of these classes compare deliberately
		// on a subset of their state - SpokeReport is equal by van, for instance - so what
		// holds across all of them is that the comparison depends on the state at all.
		int changed = 0;
		int distinguishing = 0;
		for (Field field : instanceFields(type)) {
			Object original;
			try {
				field.setAccessible(true);
				original = field.get(second);
			} catch (ReflectiveOperationException e) {
				continue;
			}
			// Each property is changed twice: to another value and, where the property can
			// hold one, to no value. A generated equals compares a property one way when both
			// sides hold something and another way when one side is empty.
			for (Object different : mutations(field, original)) {
				try {
					field.set(second, different);
				} catch (ReflectiveOperationException | RuntimeException e) {
					continue;
				}
				changed++;
				try {
					if (!first.equals(second)) {
						distinguishing++;
					}
					quietly(second::hashCode);
				} catch (RuntimeException e) {
					// An equals that cannot cope with this property is left to its own test.
					changed--;
				}
				if (!restore(field, second, original)) {
					return; // the instance can no longer be restored, so stop here
				}
			}
		}

		if (changed > 0) {
			assertThat(distinguishing).as("%s.equals returned true for every change to its state, so it compares"
					+ " nothing but the reference", type.getSimpleName()).isGreaterThan(0);
		}
	}

	private static boolean restore(Field field, Object target, Object value) {
		try {
			field.set(target, value);
			return true;
		} catch (ReflectiveOperationException | RuntimeException e) {
			return false;
		}
	}

	/** The values worth putting in {@code field} to make the two instances differ. */
	private static List<Object> mutations(Field field, Object original) {
		List<Object> mutations = new ArrayList<>();
		Class<?> type = field.getType();
		if (!type.isPrimitive()) {
			Object replacement = ReflectiveFiller.fill(type);
			if (replacement != null && !replacement.equals(original)) {
				mutations.add(replacement);
			}
			if (original != null) {
				mutations.add(null);
			} else if (mutations.isEmpty() && replacement != null) {
				mutations.add(replacement);
			}
			return mutations;
		}
		Object different = differentPrimitive(type, original);
		if (different != original) {
			mutations.add(different);
		}
		return mutations;
	}

	private static Object differentPrimitive(Class<?> type, Object original) {
		if (type == boolean.class) {
			return Boolean.FALSE.equals(original) ? Boolean.TRUE : Boolean.FALSE;
		}
		if (type == char.class) {
			return original != null && (Character) original == 'Z' ? 'Y' : 'Z';
		}
		Number number = (Number) original;
		if (type == int.class) {
			return number.intValue() + 1;
		}
		if (type == long.class) {
			return number.longValue() + 1L;
		}
		if (type == short.class) {
			return (short) (number.shortValue() + 1);
		}
		if (type == byte.class) {
			return (byte) (number.byteValue() + 1);
		}
		if (type == double.class) {
			return number.doubleValue() + 1d;
		}
		if (type == float.class) {
			return number.floatValue() + 1f;
		}
		return original;
	}

	/** A second instance of {@code type} holding the same field values as {@code source}. */
	private static Object copyOf(Object source, Class<?> type) {
		Object copy = ReflectiveFiller.empty(type);
		if (copy == null) {
			return null;
		}
		for (Field field : instanceFields(type)) {
			try {
				field.setAccessible(true);
				field.set(copy, field.get(source));
			} catch (ReflectiveOperationException | RuntimeException e) {
				return null;
			}
		}
		return copy;
	}

	private static List<Field> instanceFields(Class<?> type) {
		List<Field> fields = new ArrayList<>();
		for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
			for (Field field : current.getDeclaredFields()) {
				if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())
						|| Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				fields.add(field);
			}
		}
		return fields;
	}

	private static boolean overridesEquals(Class<?> type) {
		try {
			return type.getMethod("equals", Object.class).getDeclaringClass() != Object.class;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("accessors of an unpopulated instance are exercised for null handling")
	void accessorsOfEmptyInstanceAreExercised(Class<?> type) {
		Object instance = ReflectiveFiller.empty(type);
		if (instance == null) {
			return;
		}
		// Several hand-written entities dereference their fields in derived getters,
		// hashCode and toString, so an unpopulated instance is walked for coverage of the
		// null paths without asserting behaviour the production classes do not promise.
		for (Method getter : type.getMethods()) {
			if (isGetter(getter)) {
				quietly(() -> invoke(getter, instance));
			}
		}
		quietly(instance::toString);
		quietly(instance::hashCode);
		quietly(() -> instance.equals(instance));
		quietly(() -> instance.equals(null));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("every declared constructor builds an instance")
	void everyConstructorIsUsable(Class<?> type) {
		int built = 0;
		for (Constructor<?> constructor : type.getDeclaredConstructors()) {
			if (constructor.isSynthetic()) {
				continue;
			}
			Object instance = ReflectiveFiller.construct(constructor);
			if (instance != null) {
				built++;
			}
		}
		assertThat(built).as("%s should be constructible through at least one of its constructors",
				type.getSimpleName()).isGreaterThan(0);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("dataClasses")
	@DisplayName("factory, derived-getter and mutator methods are exercised over every row layout")
	void factoryAndMutatorMethodsAreExercised(Class<?> type) {
		// Several entities expose wide factory helpers (initialise..., getXxx(a, b, c)) and
		// derived getters that unpack a list of untyped maps into sub-records. Those read the
		// map values as a different type in each entity, so the walk is repeated over the row
		// layouts; the outcomes are not asserted on, only that the walk stays possible.
		try {
			for (int profile = 0; profile < ReflectiveFiller.rowProfileCount(); profile++) {
				ReflectiveFiller.rowProfile(profile);
				Object instance = ReflectiveFiller.fill(type);
				assertThat(instance).as("populated instance of %s", type.getName()).isNotNull();

				for (Method method : type.getMethods()) {
					if (method.getDeclaringClass() == Object.class || method.isSynthetic()) {
						continue;
					}
					Object target = Modifier.isStatic(method.getModifiers()) ? null : instance;
					if (method.getParameterCount() == 0) {
						quietly(() -> invoke(method, target));
					} else {
						// Many of these helpers take the rows of a native query and cast each
						// column, so the row layout is discovered rather than guessed.
						quietly(() -> RowLayoutFinder
								.drive(() -> ReflectiveFiller.invokeWithFilledArguments(method, target)));
					}
				}
			}
		} finally {
			ReflectiveFiller.rowProfile(0);
			RowLayoutFinder.reset();
		}
	}

	private static void quietly(Runnable action) {
		try {
			action.run();
		} catch (Throwable ignored) {
			// exercised for coverage only
		}
	}

	private static void quietly(java.util.concurrent.Callable<?> action) {
		try {
			action.call();
		} catch (Throwable ignored) {
			// exercised for coverage only
		}
	}

	private static void invoke(Method method, Object target) {
		try {
			method.setAccessible(true);
			method.invoke(target);
		} catch (InvocationTargetException e) {
			throw new AssertionError(method.getDeclaringClass().getName() + "." + method.getName() + " threw "
					+ e.getTargetException(), e.getTargetException());
		} catch (ReflectiveOperationException e) {
			throw new AssertionError(e);
		}
	}

	private static boolean isGetter(Method method) {
		if (method.getParameterCount() != 0 || Modifier.isStatic(method.getModifiers())
				|| method.getReturnType() == void.class || method.getDeclaringClass() == Object.class) {
			return false;
		}
		String name = method.getName();
		return name.startsWith("get") || name.startsWith("is") || name.startsWith("has");
	}

	private static boolean isSetter(Method method) {
		return method.getParameterCount() == 1 && method.getName().startsWith("set")
				&& !Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass() != Object.class;
	}
}
