package com.iemr.hwc.testutil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Remembers the column types of each native query separately.
 *
 * <p>A master-data read calls thirty queries in a row and hands each one's rows to the
 * entity that maps it, and those entities disagree about what column zero holds - one
 * casts it to a Short and the next to an Integer. One layout for the whole call therefore
 * cannot work, so the layouts are kept per query: {@link PopulatedMocks} records which
 * query is answering, {@link ReflectiveFiller} builds that query's rows from its own
 * layout, and {@link RowLayoutFinder} fills the layout in as the casts tell it what each
 * column has to hold.
 */
public final class RowLayouts {

	private static final ThreadLocal<Map<String, Class<?>[]>> LAYOUTS = ThreadLocal.withInitial(LinkedHashMap::new);

	private static final ThreadLocal<String> ANSWERING = new ThreadLocal<>();

	private RowLayouts() {
	}

	/** Records that {@code query} is the one now being answered. */
	public static void answering(String query) {
		ANSWERING.set(query);
	}

	/** The query that answered most recently, which is the one a failed cast came from. */
	public static String lastAnswered() {
		return ANSWERING.get();
	}

	/** The column types recorded for {@code query}; a null entry means "not yet known". */
	public static Class<?>[] layoutOf(String query) {
		return query == null ? null : LAYOUTS.get().get(query);
	}

	/**
	 * Records that a column of {@code query} holds {@code wanted} rather than {@code actual}.
	 *
	 * <p>The failed cast names both types but not the column, so the column is worked out
	 * from the value that was there: it is the leftmost column still unknown that currently
	 * holds a value of {@code actual}'s type. An entity casts its columns left to right, so
	 * that identifies the one the cast was on. Returns false when no column is left to
	 * record against, which ends the search for that query.
	 */
	public static boolean recordColumn(String query, Class<?> actual, Class<?> wanted) {
		if (query == null || wanted == null) {
			return false;
		}
		Class<?>[] layout = LAYOUTS.get().computeIfAbsent(query,
				name -> new Class<?>[ReflectiveFiller.rowWidth()]);
		for (int column = 0; column < layout.length; column++) {
			if (layout[column] == null && (actual == null || ReflectiveFiller.defaultColumnType(column) == actual)) {
				layout[column] = wanted;
				return true;
			}
		}
		for (int column = 0; column < layout.length; column++) {
			if (layout[column] == null) {
				layout[column] = wanted;
				return true;
			}
		}
		return false;
	}

	/** Forgets every recorded layout. */
	public static void reset() {
		LAYOUTS.get().clear();
		ANSWERING.remove();
	}
}
