/*
 * Copyright 2007-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project: JGentleProject
 */
package org.jgentleframework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

import org.jgentleframework.core.CouldNotInstantiateException;

/**
 * Mainly for use within the framework, but to some degree also useful for
 * application classes.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 20, 2009
 */
public abstract class InstanceUtils {
	/**
	 * Find a method with the given method name and minimal parameters (best
	 * case: none), declared on the given class or one of its superclasses. Will
	 * return a public, protected, package access, or private method.
	 * <p>
	 * Checks <code>Class.getDeclaredMethods</code>, cascading upwards to all
	 * superclasses.
	 * 
	 * @param clazz
	 *            the class to check
	 * @param methodName
	 *            the name of the method to find
	 * @return the Method object, or <code>null</code> if not found
	 * @throws IllegalArgumentException
	 *             if methods of the given name were found but could not be
	 *             resolved to a unique method with minimal parameters
	 * @see java.lang.Class#getDeclaredMethods
	 */
	public static Method findDeclaredMethodWithMinimalParameters(
			Class<?> clazz, String methodName) throws IllegalArgumentException {

		Method targetMethod = doFindMethodWithMinimalParameters(clazz
				.getDeclaredMethods(), methodName);
		if (targetMethod == null && clazz.getSuperclass() != null) {
			return findDeclaredMethodWithMinimalParameters(clazz
					.getSuperclass(), methodName);
		}
		return targetMethod;
	}

	/**
	 * Find a method with the given method name and minimal parameters (best
	 * case: none) in the given list of methods.
	 * 
	 * @param methods
	 *            the methods to check
	 * @param methodName
	 *            the name of the method to find
	 * @return the Method object, or <code>null</code> if not found
	 * @throws IllegalArgumentException
	 *             if methods of the given name were found but could not be
	 *             resolved to a unique method with minimal parameters
	 */
	private static Method doFindMethodWithMinimalParameters(Method[] methods,
			String methodName) throws IllegalArgumentException {

		Method targetMethod = null;
		int numMethodsFoundWithCurrentMinimumArgs = 0;
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				int numParams = methods[i].getParameterTypes().length;
				if (targetMethod == null
						|| numParams < targetMethod.getParameterTypes().length) {
					targetMethod = methods[i];
					numMethodsFoundWithCurrentMinimumArgs = 1;
				}
				else {
					if (targetMethod.getParameterTypes().length == numParams) {
						// Additional candidate with same length.
						numMethodsFoundWithCurrentMinimumArgs++;
					}
				}
			}
		}
		if (numMethodsFoundWithCurrentMinimumArgs > 1) {
			throw new IllegalArgumentException(
					"Cannot resolve method '"
							+ methodName
							+ "' to a unique method. Attempted to resolve to overloaded method with "
							+ "the least number of parameters, but there were "
							+ numMethodsFoundWithCurrentMinimumArgs
							+ " candidates.");
		}
		return targetMethod;
	}

	/**
	 * Convenience method to instantiate a class using its no-arg constructor.
	 * As this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * <p>
	 * Note that this method tries to set the constructor accessible if given a
	 * non-accessible (that is, non-public) constructor.
	 * 
	 * @param clazz
	 *            class to instantiate
	 * @return the new instance
	 * @throws CouldNotInstantiateException
	 *             if the bean cannot be instantiated
	 */
	public static Object instantiateClass(Class<?> clazz) {

		Assertor.notNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			throw new CouldNotInstantiateException("Specified class [" + clazz
					+ "] is an interface");
		}
		try {
			return instantiateClass(clazz
					.getDeclaredConstructor((Class[]) null), null);
		}
		catch (NoSuchMethodException ex) {
			throw new CouldNotInstantiateException(
					"No default constructor found", ex);
		}
	}

	/**
	 * Convenience method to instantiate a class using the given constructor. As
	 * this method doesn't try to load classes by name, it should avoid
	 * class-loading issues.
	 * <p>
	 * Note that this method tries to set the constructor accessible if given a
	 * non-accessible (that is, non-public) constructor.
	 * 
	 * @param ctor
	 *            the constructor to instantiate
	 * @param args
	 *            the constructor arguments to apply
	 * @return the new instance
	 * @throws CouldNotInstantiateException
	 *             if the bean cannot be instantiated
	 */
	public static Object instantiateClass(Constructor<?> ctor, Object[] args) {

		Assertor.notNull(ctor, "Constructor must not be null");
		try {
			ReflectUtils.makeAccessible(ctor);
			return ctor.newInstance(args);
		}
		catch (InstantiationException ex) {
			throw new CouldNotInstantiateException("Is it an abstract class?",
					ex);
		}
		catch (IllegalAccessException ex) {
			throw new CouldNotInstantiateException(
					"Has the class definition changed? Is the constructor accessible?",
					ex);
		}
		catch (IllegalArgumentException ex) {
			throw new CouldNotInstantiateException(
					"Illegal arguments for constructor", ex);
		}
		catch (InvocationTargetException ex) {
			throw new CouldNotInstantiateException(
					"Constructor threw exception", ex.getTargetException());
		}
	}

	/**
	 * Check if the given type represents a "simple" property: a primitive, a
	 * String or other CharSequence, a Number, a Date, a URI, a URL, a Locale, a
	 * Class, or a corresponding array.
	 * <p>
	 * Used to determine properties to check for a "simple" dependency-check.
	 * 
	 * @param clazz
	 *            the type to check
	 * @return whether the given type represents a "simple" property
	 */
	public static boolean isSimpleProperty(Class<?> clazz) {

		Assertor.notNull(clazz, "Class must not be null");
		return isSimpleValueType(clazz)
				|| (clazz.isArray() && isSimpleValueType(clazz
						.getComponentType()));
	}

	/**
	 * Check if the given type represents a "simple" value type: a primitive, a
	 * String or other CharSequence, a Number, a Date, a URI, a URL, a Locale or
	 * a Class.
	 * 
	 * @param clazz
	 *            the type to check
	 * @return whether the given type represents a "simple" value type
	 */
	public static boolean isSimpleValueType(Class<?> clazz) {

		return ObjectClassUtils.isPrimitiveOrWrapper(clazz)
				|| CharSequence.class.isAssignableFrom(clazz)
				|| Number.class.isAssignableFrom(clazz)
				|| Date.class.isAssignableFrom(clazz)
				|| clazz.equals(URI.class) || clazz.equals(URL.class)
				|| clazz.equals(Locale.class) || clazz.equals(Class.class);
	}
}
