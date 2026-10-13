package com.iemr.hwc.testutil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Builds the JSON request bodies the controller and service sweeps feed to a class.
 *
 * <p>Almost every endpoint here takes its request as a raw JSON string and reads named
 * fields out of it, so a payload only reaches the interesting code if it carries the
 * names that class looks for. Those names are recovered from the class's own string
 * literals ({@link ClassConstants}); because a single field is read as a number in one
 * place and as an object or an array in another, the payload is offered in several
 * shapes and the sweep invokes the method once per shape.
 */
public final class RequestPayloads {

	private static final Gson GSON = new Gson();

	private RequestPayloads() {
	}

	private static final java.util.Map<String, List<String>> CACHE = new java.util.HashMap<>();

	/** Candidate request bodies for {@code type}, richest first. */
	public static synchronized List<String> forClass(Class<?> type) {
		return CACHE.computeIfAbsent(type.getName(), name -> buildPayloads(type));
	}

	private static List<String> buildPayloads(Class<?> type) {
		Set<String> keys = fieldNames(type);

		List<String> payloads = new ArrayList<>();
		// A request is read in one of two ways: deserialised whole into an entity, or picked
		// apart field by field with the names the class itself carries. The data-model
		// document covers the first, and the literal-keyed objects cover the second, with
		// each field offered as a record, as a list of records and as a scalar in turn.
		payloads.add(DataModelPayloads.documentAsStringFor(type));
		payloads.add(object(keys, () -> DataModelPayloads.documentFor(type)).toString());
		// The save methods read their request in nested sections - the visit record holds a
		// visit record of its own, which holds the entity - so one shape offers every field
		// name again one level down, as an object, for those lookups to find.
		payloads.add(object(keys, () -> section(type, keys)).toString());
		payloads.add(object(keys, () -> DataModelPayloads.documentArrayFor(type)).toString());
		payloads.add(DataModelPayloads.documentArrayFor(type).toString());
		payloads.add(object(keys, () -> new JsonPrimitive(1)).toString());
		payloads.add(object(keys, () -> new JsonPrimitive("1")).toString());
		payloads.add(object(keys, () -> object(keys, () -> new JsonPrimitive(1))).toString());
		payloads.add("{}");
		payloads.add("[]");
		payloads.add("\"1\"");
		return payloads;
	}

	/** A single populated request body for {@code type}. */
	public static String forClassRich(Class<?> type) {
		return forClass(type).get(0);
	}

	/**
	 * One request section: the data model's fields, plus every other name the class looks up
	 * offered as an empty object so a nested {@code getAsJsonObject} call finds something.
	 */
	private static JsonObject section(Class<?> type, Set<String> keys) {
		JsonObject json = DataModelPayloads.documentFor(type);
		for (String key : keys) {
			if (!json.has(key)) {
				json.add(key, new JsonObject());
			}
		}
		return json;
	}

	/** The identifier-shaped literals of {@code type}, which are its candidate JSON field names. */
	public static Set<String> fieldNames(Class<?> type) {
		Set<String> keys = new LinkedHashSet<>();
		for (String literal : ClassConstants.stringLiterals(type)) {
			if (isFieldName(literal)) {
				keys.add(literal);
			}
		}
		return keys;
	}

	private static boolean isFieldName(String literal) {
		if (literal.isEmpty() || literal.length() > 60) {
			return false;
		}
		if (!Character.isJavaIdentifierStart(literal.charAt(0))) {
			return false;
		}
		for (int i = 1; i < literal.length(); i++) {
			if (!Character.isJavaIdentifierPart(literal.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	private static JsonObject object(Set<String> keys,
			java.util.function.Supplier<? extends com.google.gson.JsonElement> value) {
		JsonObject json = new JsonObject();
		for (String key : keys) {
			json.add(key, value.get());
		}
		return json;
	}

	private static JsonArray array(com.google.gson.JsonElement element) {
		JsonArray array = new JsonArray();
		array.add(element);
		return array;
	}

	/** The rich payload of {@code type} parsed as a {@link JsonObject}. */
	public static JsonObject jsonObjectFor(Class<?> type) {
		return JsonParser.parseString(forClassRich(type)).getAsJsonObject();
	}

	/** Serialises {@code value} to JSON, or {@code "{}"} when it cannot be serialised. */
	public static String toJson(Object value) {
		try {
			return GSON.toJson(value);
		} catch (RuntimeException e) {
			return "{}";
		}
	}
}
