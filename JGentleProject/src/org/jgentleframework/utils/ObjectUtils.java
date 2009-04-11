/*
 * Copyright 2007-2008 the original author or authors.
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
 * Project: JGentleFramework
 */
package org.jgentleframework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple utility methods for Object controller.
 * <p>
 * Mainly for use within the framework, but also useful for application code.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 22, 2008
 */
public final class ObjectUtils {
	/** The Constant DONTKNOW. */
	public static final Integer	DONTKNOW		= 5;

	/** The Constant ANNOTATION. */
	public static final Integer	ANNOTATION		= 4;

	/** The Constant CLASS. */
	public static final Integer	CLASS			= 3;

	/** The Constant METHOD. */
	public static final Integer	METHOD			= 2;

	/** The Constant FIELD. */
	public static final Integer	FIELD			= 1;

	/** The Constant EMPTY_STRING. */
	private static final String	EMPTY_STRING	= "";

	/** The Constant log. */
	public static final Log		log				= LogFactory
														.getLog(ObjectUtils.class);

	/**
	 * Append the given Object to the given array, returning a new array
	 * consisting of the input array contents plus the given Object.
	 * 
	 * @param array
	 *            the array to append to (can be <code>null</code>)
	 * @param obj
	 *            the Object to append
	 * @return the new array (of the same component type; never
	 *         <code>null</code> )
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {

		Class<?> compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		}
		else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	/**
	 * Check whether the given array contains the given element.
	 * 
	 * @param array
	 *            the array to check (may be <code>null</code>, in which case
	 *            the return value will always be <code>false</code>)
	 * @param element
	 *            the element to check for
	 * @return whether the element has been found in the given array
	 */
	public static boolean containsElement(Object[] array, Object element) {

		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (nullSafeEquals(array[i], element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a deep copy of the given object.
	 * 
	 * @param object
	 *            the given object need to copied.
	 */
	public static Object deepCopy(Object object) {

		Assertor.notNull(object);
		Object result = null;
		try {
			ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(bufOut);
			objOut.writeObject(object);
			objOut.flush();
			ObjectInputStream objIn = new ObjectInputStream(
					new ByteArrayInputStream(bufOut.toByteArray()));
			result = objIn.readObject();
			objIn.close();
			objOut.close();
			bufOut.close();
		}
		catch (IOException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not copy object [" + object + "]!!", e);
			}
		}
		catch (ClassNotFoundException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not copy object [" + object + "]!!", e);
			}
		}
		return result;
	}

	/**
	 * Equals.
	 * 
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @return true, if equal
	 */
	public static boolean equals(Object a, Object b) {

		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}

	/**
	 * Return a hex String form of an object's identity hash code.
	 * 
	 * @param obj
	 *            the object
	 * @return the object's identity code in hex notation
	 */
	public static String getIdentityHexString(Object obj) {

		return Integer.toHexString(System.identityHashCode(obj));
	}

	/**
	 * Trả về số Integer mô tả kiểu type của object, kiểu type của object khi
	 * kiểm tra bằng method này chỉ có thể là Method, Field, Class hoặc
	 * Annotation.
	 * 
	 * @param obj
	 *            đối tượng object chỉ định cần kiểm tra.
	 * @return Integer
	 */
	public static Integer getObjectType(Object obj) {

		if (ReflectUtils.isMethod(obj)) {
			return ObjectUtils.METHOD;
		}
		else if (ReflectUtils.isField(obj)) {
			return ObjectUtils.FIELD;
		}
		else if (ReflectUtils.isClass(obj)) {
			return ObjectUtils.CLASS;
		}
		else if (ReflectUtils.isAnnotation(obj)) {
			return ObjectUtils.ANNOTATION;
		}
		else {
			return ObjectUtils.DONTKNOW;
		}
	}

	/**
	 * Return a String representation of an object's overall identity.
	 * 
	 * @param obj
	 *            the object (may be <code>null</code>)
	 * @return the object's identity as String representation, or an empty
	 *         String if the object was <code>null</code>
	 */
	public static String identityToString(Object obj) {

		if (obj == null) {
			return EMPTY_STRING;
		}
		return obj.getClass().getName() + "@" + getIdentityHexString(obj);
	}

	/**
	 * Return whether the given array is empty: that is, <code>null</code> or of
	 * zero length.
	 * 
	 * @param array
	 *            the array to check
	 * @return whether the given array is empty
	 */
	public static boolean isEmpty(Object[] array) {

		return (array == null || array.length == 0);
	}

	/**
	 * Determine if the given objects are equal, returning <code>true</code> if
	 * both are <code>null</code> or <code>false</code> if only one is
	 * <code>null</code>.
	 * <p>
	 * Compares arrays with <code>Arrays.equals</code>, performing an equality
	 * check based on the array elements rather than the array reference.
	 * 
	 * @param o1
	 *            first Object to compare
	 * @param o2
	 *            second Object to compare
	 * @return whether the given objects are equal
	 * @see java.util.Arrays#equals
	 */
	public static boolean nullSafeEquals(Object o1, Object o2) {

		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	/**
	 * Convert the given array (which may be a primitive array) to an object
	 * array (if necessary of primitive wrapper objects).
	 * <p>
	 * A <code>null</code> source value will be converted to an empty Object
	 * array.
	 * 
	 * @param source
	 *            the (potentially primitive) array
	 * @return the corresponding object array (never <code>null</code>)
	 * @throws IllegalArgumentException
	 *             if the parameter is not an array
	 */
	public static Object[] toObjectArray(Object source) {

		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: "
					+ source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class<?> wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}
}
