package com.iemr.hwc.testutil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Discovers the column types a service expects from its native-query rows.
 *
 * <p>A native query hands each row back as an {@code Object[]} and the service casts every
 * column to the type that column holds, usually while calling an entity constructor:
 * {@code new BenMedHistory((Date) obj[0], (String) obj[1], ...)}. A test cannot know that
 * sequence up front, but it does not have to: the casts run left to right, so the first
 * {@link ClassCastException} names the type wanted for the leftmost column that is still
 * wrong. Assigning that type and retrying resolves the row one column at a time, and a
 * row is never wider than {@link ReflectiveFiller#rowWidth()} columns, so the search ends.
 */
public final class RowLayoutFinder {

	private static final Pattern CAST = Pattern
			.compile("class ([\\w.$\\[\\];]+) cannot be cast to class ([\\w.$\\[\\];]+)");

	private RowLayoutFinder() {
	}

	/** The result of driving a call: what it produced, and why it last failed. */
	public record Attempt(Object result, Throwable failure) {
	}

	/**
	 * Invokes {@code call}, refining the row layout until it produces a result.
	 *
	 * <p>A failed cast names the type wanted but not the column, so the layout is resolved
	 * twice over: once assuming the columns are read in order, which settles the entity
	 * constructors, and once by searching for the column that moves the call past its
	 * failure, which settles the rest. Each column is resolved at most once either way, so
	 * the work is bounded by the width of a row.
	 *
	 * <p>The layout is left pinned to whatever succeeded so a caller can invoke the same
	 * method again; {@link #reset()} clears it.
	 */
	public static Attempt drive(Supplier<Object> call) {
		return drive(call, deadlineIn(DEFAULT_BUDGET_MILLIS));
	}

	/**
	 * As {@link #drive(Supplier)}, but abandoning the search once {@code deadline} passes.
	 *
	 * <p>Refining a layout means invoking the method again, and a method whose rows never
	 * line up is invoked once per refinement step of each of the three strategies below -
	 * over a thousand times, each rebuilding the mock graph it reads. Left unbounded that
	 * turns a sweep over every service into a run with no useful end, so the search gets a
	 * budget: whatever layout it has settled by then is what the call is driven with.
	 */
	public static Attempt drive(Supplier<Object> call, long deadline) {
		Attempt perQuery = resolvePerQuery(call, deadline);
		if (perQuery.result() != null || expired(deadline)) {
			return perQuery;
		}
		RowLayouts.reset();
		Attempt sequential = resolveInColumnOrder(call, deadline);
		if (sequential.result() != null || expired(deadline)) {
			return sequential.result() != null ? sequential : perQuery;
		}
		Attempt searched = searchForColumns(call, deadline);
		return searched.result() != null ? searched : perQuery;
	}

	/** How long one call may be refined for before the search settles for what it has. */
	private static final long DEFAULT_BUDGET_MILLIS = 750;

	/** A deadline {@code millis} from now, in {@link System#nanoTime()} terms. */
	public static long deadlineIn(long millis) {
		return System.nanoTime() + millis * 1_000_000L;
	}

	/** True once {@code deadline} has passed. */
	public static boolean expired(long deadline) {
		return System.nanoTime() - deadline >= 0;
	}

	/**
	 * Resolves the columns left to right.
	 *
	 * <p>The common shape is an entity constructor taking one column after another, and
	 * because those casts run in order the failure always names the type of the leftmost
	 * column still unresolved. One pass settles those rows.
	 */
	private static Attempt resolveInColumnOrder(Supplier<Object> call, long deadline) {
		Class<?>[] columns = new Class<?>[ReflectiveFiller.rowWidth()];
		Set<String> seen = new LinkedHashSet<>();
		Attempt attempt = run(columns, call);
		for (int column = 0; attempt.result() == null && column < columns.length && !expired(deadline); column++) {
			Class<?> wanted = castTarget(attempt.failure());
			if (wanted == null || !seen.add(signature(attempt.failure()))) {
				return attempt;
			}
			columns[column] = wanted;
			attempt = run(columns, call);
		}
		return attempt;
	}

	/**
	 * Resolves the columns by search, for the rows read out of order.
	 *
	 * <p>Where a service reads column five before column one, each column that does not
	 * already hold the wanted type is tried in turn and the one that moves the call past its
	 * current failure is kept.
	 */
	/**
	 * Resolves each query's columns separately.
	 *
	 * <p>A read that calls many queries cannot be settled with one layout, because their
	 * entity mappers disagree about the column types. The query that answered most recently
	 * before a failed cast is the one the failure came from, so the wanted type is recorded
	 * against that query and the call retried.
	 */
	private static Attempt resolvePerQuery(Supplier<Object> call, long deadline) {
		RowLayouts.reset();
		Attempt attempt = runPlain(call);
		for (int step = 0; attempt.result() == null && step < MAX_QUERY_STEPS && !expired(deadline); step++) {
			Cast cast = castOf(attempt.failure());
			if (cast == null
					|| !RowLayouts.recordColumn(RowLayouts.lastAnswered(), cast.actual(), cast.wanted())) {
				return attempt;
			}
			attempt = runPlain(call);
		}
		return attempt;
	}

	/** Enough steps to resolve the columns of a wide multi-query read. */
	private static final int MAX_QUERY_STEPS = 400;

	private static Attempt runPlain(Supplier<Object> call) {
		try {
			return new Attempt(call.get(), null);
		} catch (Throwable thrown) {
			return new Attempt(null, thrown);
		}
	}

	private static Attempt searchForColumns(Supplier<Object> call, long deadline) {
		Class<?>[] columns = new Class<?>[ReflectiveFiller.rowWidth()];
		Set<String> seen = new LinkedHashSet<>();

		Attempt attempt = run(columns, call);
		for (int step = 0; attempt.result() == null && step < columns.length && !expired(deadline); step++) {
			Class<?> wanted = castTarget(attempt.failure());
			if (wanted == null || !seen.add(signature(attempt.failure()))) {
				return attempt;
			}
			Attempt progressed = null;
			for (int column = 0; column < columns.length && progressed == null && !expired(deadline); column++) {
				if (wanted.equals(columns[column])) {
					continue;
				}
				Class<?> previous = columns[column];
				columns[column] = wanted;
				Attempt candidate = run(columns, call);
				if (candidate.result() != null) {
					return candidate;
				}
				if (seen.contains(signature(candidate.failure()))) {
					columns[column] = previous;
				} else {
					progressed = candidate;
				}
			}
			if (progressed == null) {
				return attempt;
			}
			attempt = progressed;
		}
		return attempt;
	}

	private static Attempt run(Class<?>[] columns, Supplier<Object> call) {
		ReflectiveFiller.rowColumns(columns);
		try {
			return new Attempt(call.get(), null);
		} catch (Throwable thrown) {
			return new Attempt(null, thrown);
		}
	}

	/** Clears the pinned and per-query layouts so later calls go back to the default rows. */
	public static void reset() {
		ReflectiveFiller.rowColumns(null);
		RowLayouts.reset();
	}

	/** The types a failed cast names: what was there, and what was wanted. */
	private record Cast(Class<?> actual, Class<?> wanted) {
	}

	private static Cast castOf(Throwable thrown) {
		for (Throwable current = thrown; current != null; current = current.getCause()) {
			if (!(current instanceof ClassCastException) || current.getMessage() == null) {
				continue;
			}
			Matcher matcher = CAST.matcher(current.getMessage());
			if (!matcher.find()) {
				continue;
			}
			Class<?> actual = load(matcher.group(1));
			Class<?> wanted = load(matcher.group(2));
			return wanted == null ? null : new Cast(actual, wanted);
		}
		return null;
	}

	private static Class<?> load(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException | LinkageError e) {
			return null;
		}
	}

	/** The type a failed cast asked for, or null when the failure was not a cast. */
	private static Class<?> castTarget(Throwable thrown) {
		Cast cast = castOf(thrown);
		return cast == null ? null : cast.wanted();
	}

	/** Identifies one failure, so a search that stops making progress can be abandoned. */
	private static String signature(Throwable thrown) {
		if (thrown == null) {
			return "none";
		}
		String at = "";
		for (StackTraceElement element : thrown.getStackTrace()) {
			if (element.getClassName().startsWith("com.iemr.hwc.")) {
				at = element.toString();
				break;
			}
		}
		return thrown.getClass().getName() + "|" + thrown.getMessage() + "|" + at;
	}
}
