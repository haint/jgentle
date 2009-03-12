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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jgentleframework.configure.enums.Types;

/**
 * The Class AnnotationUtils.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 15, 2008
 */
public abstract class AnnotationUtils {
	/** The attribute name for annotations with a single element. */
	static final String	VALUE	= "value";

	/**
	 * Get a single {@link Annotation} of <code>annotationType</code> from the
	 * supplied {@link Class}, traversing its interfaces and super classes if no
	 * annotation can be found on the given class itself. </p>
	 * <p>
	 * This method explicitly handles class-level annotations which are not
	 * declared as {@link java.lang.annotation.Inherited inherited} as well as
	 * annotations on interfaces.
	 * </p>
	 * 
	 * @param clazz
	 *            the class to look for annotations on
	 * @param annotationType
	 *            the annotation class to look for
	 * @return the annotation of the given type found, or <code>null</code>
	 */
	public static <A extends Annotation> A findAnnotation(final Class<?> clazz,
			final Class<A> annotationType) {

		Assertor.notNull(clazz, "Class must not be null");
		A annotation = clazz.getAnnotation(annotationType);
		if (annotation != null) {
			return annotation;
		}
		for (Class<?> ifc : clazz.getInterfaces()) {
			annotation = findAnnotation(ifc, annotationType);
			if (annotation != null) {
				return annotation;
			}
		}
		if (clazz.getSuperclass() == null
				|| Object.class.equals(clazz.getSuperclass())) {
			return null;
		}
		return findAnnotation(clazz.getSuperclass(), annotationType);
	}

	/**
	 * Finds the first {@link Class} in the inheritance hierarchy of the
	 * specified <code>clazz</code> (including the specified <code>clazz</code>
	 * itself) which declares an annotation for the specified
	 * <code>annotationType</code>, or <code>null</code> if not found. If the
	 * supplied <code>clazz</code> is <code>null</code>, <code>null</code> will
	 * be returned. </p>
	 * <p>
	 * If the supplied <code>clazz</code> is an interface, only the interface
	 * itself will be checked; the inheritance hierarchy for interfaces will not
	 * be traversed.
	 * </p>
	 * <p>
	 * The standard {@link Class} API does not provide a mechanism for
	 * determining which class in an inheritance hierarchy actually declares an
	 * {@link Annotation}, so we need to handle this explicitly.
	 * </p>
	 * 
	 * @param annotationType
	 *            the Class object corresponding to the annotation type
	 * @param clazz
	 *            the Class object corresponding to the class on which to check
	 *            for the annotation, or <code>null</code>.
	 * @return the first {@link Class} in the inheritance hierarchy of the
	 *         specified <code>clazz</code> which declares an annotation for the
	 *         specified <code>annotationType</code>, or <code>null</code> if
	 *         not found.
	 * @see Class#isAnnotationPresent(Class)
	 * @see Class#getDeclaredAnnotations()
	 */
	public static Class<?> findAnnotationDeclaringClass(
			final Class<? extends Annotation> annotationType,
			final Class<?> clazz) {

		Assertor.notNull(annotationType, "annotationType must not be null");
		if ((clazz == null) || clazz.equals(Object.class)) {
			return null;
		}
		// else...
		return (isAnnotationDeclaredLocally(annotationType, clazz)) ? clazz
				: findAnnotationDeclaringClass(annotationType, clazz
						.getSuperclass());
	}

	/**
	 * Returns a single annotation which is declared on the given object.
	 * 
	 * @param obj
	 *            the given object
	 * @param annotation
	 *            annotation type
	 * @param type
	 *            the type
	 * @return returns the annotation object if existed, if not, returns null.
	 */
	public static Annotation getAnnotation(Object obj, Types type,
			Class<? extends Annotation> annotation) {

		Annotation result = null;
		switch (type) {
		case METHOD:
			if (!obj.getClass().equals(Method.class)) {
				return null;
			}
			Method method = (Method) obj;
			result = method.getAnnotation(annotation);
			break;
		case FIELD:
			Field field = (Field) obj;
			if (!obj.getClass().equals(Field.class)) {
				return null;
			}
			result = field.getAnnotation(annotation);
			break;
		case CLASS:
			Class<?> clazz = (Class<?>) obj;
			result = clazz.getAnnotation(annotation);
			break;
		case ENUM:
			Class<?> enumClass = (Class<?>) obj;
			result = enumClass.getAnnotation(annotation);
			break;
		case ANNOTATION:
			Annotation temp;
			try {
				temp = (Annotation) obj;
			}
			catch (RuntimeException e) {
				return result;
			}
			result = temp.annotationType().getAnnotation(annotation);
			break;
		case DEFAULT:
			if (obj.getClass().equals(Method.class)) {
				Method methods = (Method) obj;
				result = methods.getAnnotation(annotation);
				return result;
			}
			if (obj.getClass().equals(Field.class)) {
				Field fields = (Field) obj;
				result = fields.getAnnotation(annotation);
				return result;
			}
			try {
				Annotation temp1 = null;
				temp1 = (Annotation) obj;
				result = temp1.annotationType().getAnnotation(annotation);
				return result;
			}
			catch (RuntimeException e) {
				try {
					Class<?> clazzTemp = (Class<?>) obj;
					result = clazzTemp.getAnnotation(annotation);
					return result;
				}
				catch (RuntimeException e1) {
					return null;
				}
			}
		default:
			return null;
		}
		return result;
	}

	/**
	 * Retrieve the given annotation's attributes as a Map.
	 * 
	 * @param annotation
	 *            the annotation to retrieve the attributes for
	 * @return the Map of annotation attributes, with attribute names as keys
	 *         and corresponding attribute values as values
	 */
	public static Map<String, Object> getAnnotationAttributes(
			final Annotation annotation) {

		final Map<String, Object> attrs = new HashMap<String, Object>();
		final Method[] methods = annotation.annotationType()
				.getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			final Method method = methods[j];
			if (method.getParameterTypes().length == 0
					&& method.getReturnType() != void.class) {
				try {
					attrs.put(method.getName(), method.invoke(annotation));
				}
				catch (final Exception ex) {
					throw new IllegalStateException(
							"Could not obtain annotation attribute values", ex);
				}
			}
		}
		return attrs;
	}

	/**
	 * Returns an array containing all annotations are declared on the given
	 * object.
	 * <p>
	 * <b>Note:</b> object type must be {@link Types#METHOD},
	 * {@link Types#FIELD}, {@link Types#CLASS}, {@link Types#CONSTRUCTOR},
	 * {@link Types#ENUM} or {@link Types#ANNOTATION}. Otherwise, returns null.
	 * 
	 * @param obj
	 *            the given object declaring annotations.
	 * @param type
	 *            the type
	 * @return Returns an array containing all annotations if existed, if not,
	 *         returns an empty array.
	 */
	public static Annotation[] getAnnotations(Object obj, Types type) {

		Assertor.notNull(obj);
		Annotation[] result = null;
		switch (type) {
		case CONSTRUCTOR:
			if (!obj.getClass().equals(Constructor.class))
				return null;
			Constructor<?> cons = (Constructor<?>) obj;
			result = cons.getAnnotations();
			break;
		case METHOD:
			if (!obj.getClass().equals(Method.class))
				return null;
			Method method = (Method) obj;
			result = method.getAnnotations();
			break;
		case FIELD:
			if (!obj.getClass().equals(Field.class)) {
				return null;
			}
			Field field = (Field) obj;
			result = field.getAnnotations();
			break;
		case CLASS:
			Class<?> clazz = (Class<?>) obj;
			result = clazz.getAnnotations();
			break;
		case ENUM:
			Class<?> enumClass = (Class<?>) obj;
			result = enumClass.getAnnotations();
			break;
		case ANNOTATION:
			Annotation temp;
			try {
				temp = (Annotation) obj;
			}
			catch (RuntimeException e) {
				return result;
			}
			result = temp.annotationType().getAnnotations();
			break;
		case DEFAULT:
			if (obj.getClass().equals(Method.class)) {
				Method methods = (Method) obj;
				result = methods.getAnnotations();
				return result;
			}
			else if (obj.getClass().equals(Field.class)) {
				Field fields = (Field) obj;
				result = fields.getAnnotations();
				return result;
			}
			else if (obj.getClass().equals(Constructor.class)) {
				Constructor<?> constructor = (Constructor<?>) obj;
				result = constructor.getAnnotations();
				return result;
			}
			try {
				Annotation temp1 = null;
				temp1 = (Annotation) obj;
				result = temp1.annotationType().getAnnotations();
				return result;
			}
			catch (RuntimeException e) {
				try {
					Class<?> clazzTemp = (Class<?>) obj;
					result = clazzTemp.getAnnotations();
					return result;
				}
				catch (RuntimeException e1) {
					return null;
				}
			}
		default:
			return null;
		}
		return result;
	}

	/**
	 * Retrieve the <em>default value</em> of the <code>&quot;value&quot;</code>
	 * attribute of a single-element Annotation, given an annotation instance.
	 * 
	 * @param annotation
	 *            the annotation instance from which to retrieve the default
	 *            value
	 * @return the default value, or <code>null</code> if not found
	 * @see #getDefaultValue(Annotation, String)
	 */
	public static Object getDefaultValue(final Annotation annotation) {

		return getDefaultValue(annotation, VALUE);
	}

	/**
	 * Retrieve the <em>default value</em> of a named Annotation attribute,
	 * given an annotation instance.
	 * 
	 * @param annotation
	 *            the annotation instance from which to retrieve the default
	 *            value
	 * @param attributeName
	 *            the name of the attribute value to retrieve
	 * @return the default value of the named attribute, or <code>null</code> if
	 *         not found.
	 * @see #getDefaultValue(Class, String)
	 */
	public static Object getDefaultValue(final Annotation annotation,
			final String attributeName) {

		return getDefaultValue(annotation.annotationType(), attributeName);
	}

	/**
	 * Retrieve the <em>default value</em> of the <code>&quot;value&quot;</code>
	 * attribute of a single-element Annotation, given the {@link Class
	 * annotation type}.
	 * 
	 * @param annotationType
	 *            the <em>annotation type</em> for which the default value
	 *            should be retrieved
	 * @return the default value, or <code>null</code> if not found
	 * @see #getDefaultValue(Class, String)
	 */
	public static Object getDefaultValue(
			final Class<? extends Annotation> annotationType) {

		return getDefaultValue(annotationType, VALUE);
	}

	/**
	 * Retrieve the <em>default value</em> of a named Annotation attribute,
	 * given the {@link Class annotation type}.
	 * 
	 * @param annotationType
	 *            the <em>annotation type</em> for which the default value
	 *            should be retrieved
	 * @param attributeName
	 *            the name of the attribute value to retrieve.
	 * @return the default value of the named attribute, or <code>null</code> if
	 *         not found
	 * @see #getDefaultValue(Annotation, String)
	 */
	public static Object getDefaultValue(
			final Class<? extends Annotation> annotationType,
			final String attributeName) {

		try {
			final Method method = annotationType.getDeclaredMethod(
					attributeName, new Class[0]);
			return method.getDefaultValue();
		}
		catch (final Exception ex) {
			return null;
		}
	}

	/**
	 * Returns an array containing all methods occur in the given
	 * {@link Annotation}
	 * 
	 * @param anno
	 *            the annotation instance
	 * @return an array containing all methods occur in the given
	 *         {@link Annotation} if they exists, if not, returns an empty
	 *         array.
	 */
	public static Method[] getMethodsInAnnotation(Annotation anno) {

		Method[] methods = anno.annotationType().getDeclaredMethods();
		return methods;
	}

	/**
	 * Retrieve the <em>value</em> of the <code>&quot;value&quot;</code>
	 * attribute of a single-element Annotation, given an annotation instance.
	 * 
	 * @param annotation
	 *            the annotation instance from which to retrieve the value
	 * @return the attribute value, or <code>null</code> if not found
	 * @see #getValue(Annotation, String)
	 */
	public static Object getValue(final Annotation annotation) {

		return getValue(annotation, VALUE);
	}

	/**
	 * Retrieve the <em>value</em> of a named Annotation attribute, given an
	 * annotation instance.
	 * 
	 * @param annotation
	 *            the annotation instance from which to retrieve the value
	 * @param attributeName
	 *            the name of the attribute value to retrieve
	 * @return the attribute value, or <code>null</code> if not found
	 * @see #getValue(Annotation)
	 */
	public static Object getValue(final Annotation annotation,
			final String attributeName) {

		try {
			final Method method = annotation.annotationType()
					.getDeclaredMethod(attributeName, new Class[0]);
			return method.invoke(annotation);
		}
		catch (final Exception ex) {
			return null;
		}
	}

	/**
	 * Returns <code>true</code> if an annotation for the specified
	 * <code>annotationType</code> is declared locally on the supplied
	 * <code>clazz</code>, else <code>false</code>. The supplied {@link Class}
	 * object may represent any type. </p>
	 * <p>
	 * Note: this method does <strong>not</strong> determine if the annotation
	 * is {@link java.lang.annotation.Inherited inherited}. For greater clarity
	 * regarding inherited annotations, consider using
	 * {@link #isAnnotationInherited(Class, Class)} instead.
	 * </p>
	 * 
	 * @param annotationType
	 *            the Class object corresponding to the annotation type
	 * @param clazz
	 *            the Class object corresponding to the class on which to check
	 *            for the annotation
	 * @return <code>true</code> if an annotation for the specified
	 *         <code>annotationType</code> is declared locally on the supplied
	 *         <code>clazz</code>
	 * @see Class#getDeclaredAnnotations()
	 * @see #isAnnotationInherited(Class, Class)
	 */
	public static boolean isAnnotationDeclaredLocally(
			final Class<? extends Annotation> annotationType,
			final Class<?> clazz) {

		Assertor.notNull(annotationType, "annotationType must not be null");
		Assertor.notNull(clazz, "clazz must not be null");
		boolean declaredLocally = false;
		for (final Annotation annotation : Arrays.asList(clazz
				.getDeclaredAnnotations())) {
			if (annotation.annotationType().equals(annotationType)) {
				declaredLocally = true;
				break;
			}
		}
		return declaredLocally;
	}

	/**
	 * Returns <code>true</code> if an annotation for the specified
	 * <code>annotationType</code> is present on the supplied <code>clazz</code>
	 * and is {@link java.lang.annotation.Inherited inherited} (i.e., not
	 * declared locally for the class), else <code>false</code>. </p>
	 * <p>
	 * If the supplied <code>clazz</code> is an interface, only the interface
	 * itself will be checked. In accord with standard meta-annotation
	 * semantics, the inheritance hierarchy for interfaces will not be
	 * traversed. See the {@link java.lang.annotation.Inherited JavaDoc} for the
	 * &#064;Inherited meta-annotation for further details regarding annotation
	 * inheritance.
	 * </p>
	 * 
	 * @param annotationType
	 *            the Class object corresponding to the annotation type
	 * @param clazz
	 *            the Class object corresponding to the class on which to check
	 *            for the annotation
	 * @return <code>true</code> if an annotation for the specified
	 *         <code>annotationType</code> is present on the supplied
	 *         <code>clazz</code> and is {@link java.lang.annotation.Inherited
	 *         inherited}
	 * @see Class#isAnnotationPresent(Class)
	 * @see #isAnnotationDeclaredLocally(Class, Class)
	 */
	public static boolean isAnnotationInherited(
			final Class<? extends Annotation> annotationType,
			final Class<?> clazz) {

		Assertor.notNull(annotationType, "annotationType must not be null");
		Assertor.notNull(clazz, "clazz must not be null");
		return (clazz.isAnnotationPresent(annotationType) && !isAnnotationDeclaredLocally(
				annotationType, clazz));
	}
}
