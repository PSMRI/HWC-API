package com.iemr.hwc.testutil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Builds a JSON document that a service's own entities can be read out of.
 *
 * <p>The services deserialise their request into entities and DTOs with Gson - a visit
 * record here, a list of chief complaints there - and a field only reaches the save path
 * if the request actually carries it. Rather than guessing per endpoint, this reads the
 * entity and DTO classes the service names in its own bytecode and emits one object
 * carrying every field those classes declare. Gson ignores the names a given target does
 * not declare, so the same document populates whichever of them a method maps it to.
 *
 * <p>Values are typed by what the model declares for that name: a date-valued name gets a
 * string in the format Gson's default adapter reads, a scalar gets {@code "1"} - which
 * Gson coerces to whatever numeric or boolean type the field has - a collection gets an
 * array, and a nested record gets a shallower copy of this same object. A name declared
 * with conflicting shapes in different classes is left out, because a value of the wrong
 * shape makes Gson abandon the whole document rather than that one field.
 */
public final class DataModelPayloads {

	/** The format Gson's built-in Date and Timestamp adapters read. */
	private static final String DATE_VALUE = "Jan 1, 2024, 10:30:00 AM";

	private static final int NESTING = 2;

	/** Field names carried by one document, capped so a payload stays cheap to parse. */
	private static final int MAX_FIELDS = 150;

	/** A service that names more model classes than this is capped, to keep the document small. */
	private static final int MAX_MODEL_CLASSES = 30;

	private enum Shape {
		SCALAR, DATE, COLLECTION, RECORD, CONFLICTING
	}

	private static final Map<String, JsonObject> DOCUMENTS = new java.util.HashMap<>();

	private DataModelPayloads() {
	}

	/** An object carrying every unambiguous field name of the model {@code owner} works with. */
	public static synchronized JsonObject documentFor(Class<?> owner) {
		return DOCUMENTS.computeIfAbsent(owner.getName(), name -> build(shapesFor(owner), NESTING)).deepCopy();
	}

	/** The same document as a JSON string. */
	public static String documentAsStringFor(Class<?> owner) {
		return documentFor(owner).toString();
	}

	/** An array holding one copy of {@link #documentFor(Class)}. */
	public static JsonArray documentArrayFor(Class<?> owner) {
		JsonArray array = new JsonArray();
		array.add(documentFor(owner));
		return array;
	}

	private static JsonObject build(Map<String, Shape> shapes, int depth) {
		JsonObject json = new JsonObject();
		int added = 0;
		for (Map.Entry<String, Shape> entry : shapes.entrySet()) {
			JsonElement value = value(shapes, entry.getValue(), depth);
			if (value == null) {
				continue;
			}
			json.add(entry.getKey(), value);
			if (++added >= MAX_FIELDS) {
				break;
			}
		}
		return json;
	}

	private static JsonElement value(Map<String, Shape> shapes, Shape shape, int depth) {
		switch (shape) {
		case DATE:
			return new JsonPrimitive(DATE_VALUE);
		case SCALAR:
			// "1" parses as a number, a boolean or a string, so one value fits every scalar.
			return new JsonPrimitive("1");
		case COLLECTION:
			// A nested list stays empty: the list-valued variants in RequestPayloads cover the
			// collection case at the top level, where the services actually read one.
			return new JsonArray();
		case RECORD:
			return depth > 0 ? build(shapes, depth - 1) : JsonNull.INSTANCE;
		default:
			return null;
		}
	}

	private static Map<String, Shape> shapesFor(Class<?> owner) {
		Map<String, Shape> collected = new LinkedHashMap<>();
		int used = 0;
		for (String name : ClassConstants.referencedProjectClasses(owner)) {
			if (!isModelClass(name)) {
				continue;
			}
			Class<?> type = load(name);
			if (type == null) {
				continue;
			}
			for (Field field : declaredFields(type)) {
				Shape shape = shapeOf(field);
				if (shape == null) {
					continue;
				}
				collected.merge(field.getName(), shape,
						(existing, added) -> existing == added ? existing : Shape.CONFLICTING);
			}
			if (++used >= MAX_MODEL_CLASSES) {
				break;
			}
		}
		return collected;
	}

	private static boolean isModelClass(String name) {
		return name.startsWith("com.iemr.hwc.data.") || name.startsWith("com.iemr.hwc.fhir.dto.");
	}

	private static Class<?> load(String name) {
		try {
			return Class.forName(name, false, DataModelPayloads.class.getClassLoader());
		} catch (ClassNotFoundException | LinkageError e) {
			return null;
		}
	}

	private static Field[] declaredFields(Class<?> type) {
		try {
			return type.getDeclaredFields();
		} catch (Throwable e) {
			return new Field[0];
		}
	}

	private static Shape shapeOf(Field field) {
		if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
			return null;
		}
		Class<?> type = field.getType();
		if (isDate(type)) {
			return Shape.DATE;
		}
		if (isScalar(type)) {
			return Shape.SCALAR;
		}
		if (Collection.class.isAssignableFrom(type) || type.isArray()) {
			return elementIsScalar(field) ? Shape.COLLECTION : Shape.COLLECTION;
		}
		if (Map.class.isAssignableFrom(type)) {
			return Shape.RECORD;
		}
		if (type.getName().startsWith("com.iemr.hwc.")) {
			return Shape.RECORD;
		}
		// Anything else - a JsonElement, a third-party model - is left out rather than risk
		// handing Gson a value it cannot read.
		return null;
	}

	private static boolean elementIsScalar(Field field) {
		Type generic = field.getGenericType();
		if (!(generic instanceof ParameterizedType parameterized)) {
			return false;
		}
		Type[] arguments = parameterized.getActualTypeArguments();
		return arguments.length == 1 && arguments[0] instanceof Class<?> element && isScalar(element);
	}

	private static boolean isScalar(Class<?> type) {
		return type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type)
				|| type == Boolean.class || type == Character.class || type.isEnum();
	}

	private static boolean isDate(Class<?> type) {
		return java.util.Date.class.isAssignableFrom(type) || type == java.sql.Timestamp.class
				|| type == java.sql.Time.class || type == java.time.LocalDate.class
				|| type == java.time.LocalDateTime.class;
	}
}
