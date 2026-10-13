package com.iemr.hwc.testutil;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Stream;

/** Enumerates the compiled classes of a package straight off the build output directory. */
public final class ClassScanner {

	private ClassScanner() {
	}

	/** Returns every concrete, instantiable class under {@code packageName}, sub-packages included. */
	public static List<Class<?>> concreteClasses(String packageName) {
		List<Class<?>> result = new ArrayList<>();
		for (Class<?> type : classes(packageName)) {
			if (type.isInterface() || type.isEnum() || type.isAnnotation() || type.isAnonymousClass()
					|| type.isLocalClass() || Modifier.isAbstract(type.getModifiers())) {
				continue;
			}
			if (type.isMemberClass() && !Modifier.isStatic(type.getModifiers())) {
				continue;
			}
			result.add(type);
		}
		return result;
	}

	/** Returns every class under {@code packageName}, sub-packages included. */
	public static List<Class<?>> classes(String packageName) {
		List<Class<?>> result = new ArrayList<>();
		Set<String> seen = new HashSet<>();
		for (Path base : roots(packageName)) {
			collect(base, packageName, seen, result);
		}
		result.sort(Comparator.comparing(Class::getName));
		return result;
	}

	/**
	 * Every production classpath directory that holds the package. A package that exists in
	 * both the main and the test output directory yields one root per directory, and only
	 * walking all of them - minus this scanner's own test root - sweeps the production
	 * classes rather than whichever root the class loader happens to return first.
	 */
	private static List<Path> roots(String packageName) {
		List<Path> roots = new ArrayList<>();
		Path testRoot = testOutputRoot();
		Enumeration<URL> urls;
		try {
			urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replace('.', '/'));
		} catch (IOException e) {
			return roots;
		}
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if (!"file".equals(url.getProtocol())) {
				continue;
			}
			try {
				Path base = Paths.get(url.toURI());
				if (testRoot == null || !base.startsWith(testRoot)) {
					roots.add(base);
				}
			} catch (Exception ignored) {
				// not a readable directory; nothing to sweep
			}
		}
		return roots;
	}

	/** The directory the tests themselves are compiled into, so it can be left out of a sweep. */
	private static Path testOutputRoot() {
		try {
			return Paths.get(ClassScanner.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (Exception e) {
			return null;
		}
	}

	private static void collect(Path base, String packageName, Set<String> seen, List<Class<?>> result) {
		try (Stream<Path> paths = Files.walk(base)) {
			List<Path> files = paths.filter(p -> p.toString().endsWith(".class")).sorted().toList();
			for (Path file : files) {
				String relative = base.relativize(file).toString();
				String className = packageName + "." + relative.substring(0, relative.length() - ".class".length())
						.replace(java.io.File.separatorChar, '.').replace('/', '.');
				if (!seen.add(className)) {
					continue;
				}
				try {
					result.add(Class.forName(className, false, Thread.currentThread().getContextClassLoader()));
				} catch (Throwable ignored) {
					// not loadable in the test JVM; nothing to sweep
				}
			}
		} catch (IOException ignored) {
			// unreadable root; nothing to sweep
		}
	}
}
