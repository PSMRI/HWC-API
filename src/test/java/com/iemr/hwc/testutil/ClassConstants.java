package com.iemr.hwc.testutil;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Reads the string literals a compiled class holds in its constant pool.
 *
 * <p>The controllers and services in this code base pull their request fields out of a
 * {@code JsonObject} by name, so the literals a class carries are exactly the keys a
 * request has to supply for that class to take its main path. Reading them off the class
 * file lets the sweeps build a payload the production code recognises without a
 * hand-maintained list per endpoint.
 */
public final class ClassConstants {

	private static final Map<String, Set<String>> CACHE = new HashMap<>();

	private static final int UTF8 = 1;
	private static final int INTEGER = 3;
	private static final int FLOAT = 4;
	private static final int LONG = 5;
	private static final int DOUBLE = 6;
	private static final int CLASS = 7;
	private static final int STRING = 8;
	private static final int FIELDREF = 9;
	private static final int METHODREF = 10;
	private static final int INTERFACE_METHODREF = 11;
	private static final int NAME_AND_TYPE = 12;
	private static final int METHOD_HANDLE = 15;
	private static final int METHOD_TYPE = 16;
	private static final int DYNAMIC = 17;
	private static final int INVOKE_DYNAMIC = 18;
	private static final int MODULE = 19;
	private static final int PACKAGE = 20;

	private ClassConstants() {
	}

	/** The project classes {@code type} names anywhere in its constant pool. */
	public static synchronized Set<String> referencedProjectClasses(Class<?> type) {
		return REFERENCES.computeIfAbsent(type.getName(), name -> readReferences(type));
	}

	private static Set<String> readReferences(Class<?> type) {
		String resource = type.getName().replace('.', '/') + ".class";
		try (InputStream in = type.getClassLoader().getResourceAsStream(resource)) {
			if (in == null) {
				return Set.of();
			}
			return projectClassNames(allUtf8(new DataInputStream(in)));
		} catch (IOException | RuntimeException e) {
			return Set.of();
		}
	}

	/**
	 * Every {@code com.iemr.hwc} class named by a pool entry.
	 *
	 * <p>Class entries and type descriptors both hold internal names, so the names are
	 * matched out of the raw UTF-8 text rather than by walking each entry kind: a field of
	 * type {@code Lcom/iemr/hwc/data/anc/ANCCareDetails;} matters as much as a class the
	 * code instantiates.
	 */
	private static Set<String> projectClassNames(List<String> utf8) {
		Set<String> names = new LinkedHashSet<>();
		java.util.regex.Matcher matcher = PROJECT_CLASS.matcher("");
		for (String text : utf8) {
			if (text == null || !text.contains("com/iemr/hwc/")) {
				continue;
			}
			matcher.reset(text);
			while (matcher.find()) {
				names.add(matcher.group().replace('/', '.'));
			}
		}
		return names;
	}

	private static final java.util.regex.Pattern PROJECT_CLASS = java.util.regex.Pattern
			.compile("com/iemr/hwc/[A-Za-z0-9_/$]+");

	private static final Map<String, Set<String>> REFERENCES = new HashMap<>();

	/** The string literals declared in {@code type}, in constant-pool order. */
	public static synchronized Set<String> stringLiterals(Class<?> type) {
		return CACHE.computeIfAbsent(type.getName(), name -> read(type));
	}

	private static Set<String> read(Class<?> type) {
		String resource = type.getName().replace('.', '/') + ".class";
		try (InputStream in = type.getClassLoader().getResourceAsStream(resource)) {
			if (in == null) {
				return Set.of();
			}
			return parse(new DataInputStream(in));
		} catch (IOException | RuntimeException e) {
			return Set.of();
		}
	}

	/** Every UTF-8 entry of the pool, which is where both literals and descriptors live. */
	private static List<String> allUtf8(DataInputStream in) throws IOException {
		if (in.readInt() != 0xCAFEBABE) {
			return List.of();
		}
		in.readUnsignedShort();
		in.readUnsignedShort();
		int count = in.readUnsignedShort();
		List<String> utf8 = new ArrayList<>();
		for (int i = 1; i < count; i++) {
			int tag = in.readUnsignedByte();
			switch (tag) {
			case UTF8:
				utf8.add(in.readUTF());
				break;
			case STRING:
			case CLASS:
			case METHOD_TYPE:
			case MODULE:
			case PACKAGE:
				in.readUnsignedShort();
				break;
			case INTEGER:
			case FLOAT:
			case FIELDREF:
			case METHODREF:
			case INTERFACE_METHODREF:
			case NAME_AND_TYPE:
			case DYNAMIC:
			case INVOKE_DYNAMIC:
				in.readInt();
				break;
			case LONG:
			case DOUBLE:
				in.readLong();
				i++;
				break;
			case METHOD_HANDLE:
				in.readUnsignedByte();
				in.readUnsignedShort();
				break;
			default:
				return utf8;
			}
		}
		return utf8;
	}

	private static Set<String> parse(DataInputStream in) throws IOException {
		if (in.readInt() != 0xCAFEBABE) {
			return Set.of();
		}
		in.readUnsignedShort(); // minor version
		in.readUnsignedShort(); // major version
		int count = in.readUnsignedShort();
		String[] utf8 = new String[count];
		List<Integer> stringIndexes = new ArrayList<>();
		for (int i = 1; i < count; i++) {
			int tag = in.readUnsignedByte();
			switch (tag) {
			case UTF8:
				utf8[i] = in.readUTF();
				break;
			case STRING:
				stringIndexes.add(in.readUnsignedShort());
				break;
			case CLASS:
			case METHOD_TYPE:
			case MODULE:
			case PACKAGE:
				in.readUnsignedShort();
				break;
			case INTEGER:
			case FLOAT:
			case FIELDREF:
			case METHODREF:
			case INTERFACE_METHODREF:
			case NAME_AND_TYPE:
			case DYNAMIC:
			case INVOKE_DYNAMIC:
				in.readInt();
				break;
			case LONG:
			case DOUBLE:
				in.readLong();
				i++; // eight-byte constants take two pool slots
				break;
			case METHOD_HANDLE:
				in.readUnsignedByte();
				in.readUnsignedShort();
				break;
			default:
				// an unknown tag means the rest of the pool cannot be walked safely
				return collect(utf8, stringIndexes);
			}
		}
		return collect(utf8, stringIndexes);
	}

	private static Set<String> collect(String[] utf8, List<Integer> stringIndexes) {
		Set<String> literals = new LinkedHashSet<>();
		for (int index : stringIndexes) {
			if (index > 0 && index < utf8.length && utf8[index] != null) {
				literals.add(utf8[index]);
			}
		}
		return literals;
	}
}
