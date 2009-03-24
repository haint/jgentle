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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jgentleframework.core.IllegalPropertyException;
import org.jgentleframework.core.JGentleRuntimeException;

/**
 * Chịu trách nhiệm xác nhận các thông tin trạng thái của các entities, nếu
 * thông tin hoặc trạng thái không phù hợp sẽ ném ra các ngoại lệ tương ứng.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 9, 2008
 */
public abstract class Assertor {
	/**
	 * Hàm <b>throwRunTimeException</b> ném ra một ngoại lệ lúc runtime. *
	 */
	public static void throwRunTimeException() {

		throwRunTimeException("[Quá trình xác nhận failed] - Có lỗi xảy ra lúc run-time.");
	}

	/**
	 * Hàm <b>throwRunTimeException</b> ném ra một ngoại lệ lúc runtime.
	 * 
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	public static void throwRunTimeException(String message) {

		throw new RuntimeException(message);
	}

	/**
	 * Xác nhận một String không chứa giá trị là chuỗi "NULL", nếu String cần
	 * xác nhận chứa giá trị là "NULL" một ngoại lệ IllegalPropertyException sẽ
	 * được ném ra.
	 * 
	 * @param strPar
	 *            chuỗi String cần xác nhận.
	 */
	public static void notStringEqualNULL(String strPar) {

		notStringEqualNULL(strPar,
				"[Quá trình xác nhận failed] - String cung cấp không được phép là NULL");
	}

	/**
	 * Xác nhận một String không chứa giá trị là chuỗi "NULL", nếu String cần
	 * xác nhận chứa giá trị là "NULL" một ngoại lệ IllegalPropertyException sẽ
	 * được ném ra.
	 * 
	 * @param strPar
	 *            chuỗi String cần xác nhận.
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	public static void notStringEqualNULL(String strPar, String message) {

		if (strPar.equals("NULL")) {
			throw new IllegalPropertyException(message);
		}
	}

	/**
	 * Xác nhận một class có chứa một annotation cụ thể nào hay không, nếu class
	 * Type cung cấp không chứa annotation type cung cấp, một ngoại lệ
	 * JGentleRuntimeException sẽ được ném ra.
	 * 
	 * @param clazz
	 *            class Type cần kiểm tra
	 * @param annotationType
	 *            annotation cần xác nhận có hay không trong <b>clazz</b>.
	 */
	public static void hasAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationType) {

		hasAnnotation(clazz, annotationType,
				"[Quá trình xác nhận failed] - Class " + clazz.getName()
						+ " cung cấp không chứa annotation "
						+ annotationType.getName());
	}

	/**
	 * Xác nhận một class có chứa một annotation cụ thể nào hay không, nếu class
	 * Type cung cấp không chứa annotation type cung cấp , một ngoại lệ
	 * JGentleRuntimeException sẽ được ném ra.
	 * 
	 * @param clazz
	 *            class Type cần kiểm tra
	 * @param annotationType
	 *            annotation cần xác nhận có hay không trong <b>clazz</b>.
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	@SuppressWarnings("unchecked")
	public static void hasAnnotation(Class clazz, Class annotationType,
			String message) {

		isAnnotation(annotationType);
		if (!clazz.isAnnotationPresent(annotationType)) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một classType có phải là một annotation hay không, nếu classType
	 * cung cấp không phải là một annotation một ngoại lệ
	 * JGentleRuntimeException sẽ được ném ra.
	 * 
	 * @param annotation
	 *            class type của annotation cần xác nhận
	 */
	public static void isAnnotation(Class<? extends Annotation> annotation) {

		isAnnotation(annotation, "[Assertion failed] - Class " + annotation
				+ " is not a annotation.");
	}

	/**
	 * Xác nhận một classType có phải là một annotation hay không, nếu classType
	 * cung cấp không phải là một annotation một ngoại lệ
	 * JGentleRuntimeException sẽ được ném ra.
	 * 
	 * @param annotation
	 *            class type của annotation cần xác nhận
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	public static void isAnnotation(Class<? extends Annotation> annotation,
			String message) {

		if (!annotation.isAnnotation()) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận 2 object có cùng kiểu khởi tạo giống nhau, nếu không ném ra một
	 * ngoại lệ JGentleRuntimeException.
	 * 
	 * @param object1
	 *            đối tượng cần so sánh 1
	 * @param object2
	 *            đối tượng cần so sánh 2
	 */
	public static void hasEqualsType(Object object1, Object object2) {

		hasEqualsType(object1, object2,
				"[Quá trình xác nhận failed] - 2 object cung cấp không cùng kiểu.");
	}

	/**
	 * Xác nhận 2 object có cùng kiểu khởi tạo giống nhau, nếu không ném ra một
	 * ngoại lệ JGentleRuntimeException.
	 * 
	 * @param object1
	 *            đối tượng cần so sánh 1
	 * @param object2
	 *            đối tượng cần so sánh 2
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	public static void hasEqualsType(Object object1, Object object2,
			String message) {

		Assertor.notNull(object1);
		Assertor.notNull(object2);
		if (!object1.getClass().equals(object2.getClass())) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một chỉ số index là hợp lệ trong một ArrayList, có nghĩa là
	 * index phải >= 0 hoặc index phải <= size của {@link ArrayList} trừ 1.
	 * 
	 * @param arrayList
	 *            đối tượng ArrayList muốn kiểm tra
	 * @param index
	 *            chỉ số index
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 */
	public static void hasValidIndex(ArrayList<?> arrayList, int index,
			String message) {

		Assertor
				.notNull(arrayList,
						"[Quá trình xác nhận failed] - đối tượng arrayList không được phép là NULL");
		if (index < 0 || index > arrayList.size() - 1) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một chỉ số index là hợp lệ trong một ArrayList, có nghĩa là
	 * index phải >= 0 hoặc index phải <= size của {@link ArrayList} trừ 1.
	 * 
	 * @param arrayList
	 *            đối tượng ArrayList muốn kiểm tra
	 * @param index
	 *            chỉ số index
	 */
	public static void hasValidIndex(ArrayList<?> arrayList, int index) {

		hasValidIndex(arrayList, index,
				"[Quá trình xác nhận failed] - index nằm ngoài phạm vi của arrayList.");
	}

	/**
	 * Xác nhận một biểu thức trả về kiểu boolean, ném ra ngoại lệ
	 * <code>JGentleRuntimeException</code> nếu kết quả kiểm tra là
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, &quot;Giá trị phải lớn hơn 0&quot;);
	 * </pre>
	 * 
	 * @param expression
	 *            biểu thức boolean cần kiểm tra
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu quá trình xác nhận <code>failed</code>
	 */
	public static void isTrue(boolean expression, String message) {

		if (!expression) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một biểu thức trả về kiểu boolean, ném ra ngoại lệ
	 * <code>JGentleRuntimeException</code> nếu kết quả kiểm tra là
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0);
	 * </pre>
	 * 
	 * @param expression
	 *            biểu thức boolean cần kiểm tra
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu quá trình xác nhận <code>failed</code>
	 */
	public static void isTrue(boolean expression) {

		isTrue(expression,
				"[Quá trình xác nhận failed] - Biểu thức này phải là true");
	}

	/**
	 * Xác nhận một object là <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value, &quot;giá trị value phải là null&quot;);
	 * </pre>
	 * 
	 * @param object
	 *            đối tượng cần kiểm tra
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu quá trình xác nhận <code>failed</code>
	 */
	public static void isNull(Object object, String message) {

		if (object != null) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một object là <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value);
	 * </pre>
	 * 
	 * @param object
	 *            đối tượng cần kiểm tra
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu đối tượng không phải là <code>null</code>
	 */
	public static void isNull(Object object) {

		isNull(object,
				"[Quá trình xác nhận failed ] - Tham số đối tượng object phải là null");
	}

	/**
	 * Xác nhận một object không phải là <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz, &quot;Class không được phép là null&quot;);
	 * </pre>
	 * 
	 * @param object
	 *            đối tượng cần kiểm tra
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu đối tượng là <code>null</code>
	 */
	public static void notNull(Object object, String message) {

		if (object == null) {
			throw new NullPointerException(message);
		}
	}

	/**
	 * Xác nhận một object không phải là <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz);
	 * </pre>
	 * 
	 * @param object
	 *            đối tượng cần kiểm tra
	 * @throws JGentleRuntimeException
	 *             ném ra ngoại lệ nếu đối tượng là <code>null</code>
	 */
	public static void notNull(Object object) {

		notNull(object,
				"[Assertion failed] - this Object argument must not be null !");
	}

	/**
	 * Xác nhận một String không rỗng; có nghĩa là String không được phép empty
	 * và không được phép <code>null</code>.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name, &quot;Name không được phép rỗng&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            Chuỗi cần xác nhận
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed.
	 */
	public static void hasLength(String text, String message) {

		if (text == null || text.isEmpty()) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Xác nhận một String không rỗng; có nghĩa là String không được phép empty
	 * và không được phép <code>null</code>.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name);
	 * </pre>
	 * 
	 * @param text
	 *            Chuỗi cần xác nhận
	 */
	public static void hasLength(String text) {

		hasLength(
				text,
				"[Quá trình xác nhận failed] - tham số String không được phép <code>null</code> hoặc rỗng.");
	}

	/**
	 * Xác nhận một String có nội dung hợp lệ; có nghĩa là String không được
	 * phép là <code>null</code> và phải có ít nhất một kí tự không phải là
	 * khoảng trắng.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;Name không được phép rỗng&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            Chuỗi cần xác nhận
	 * @param message
	 *            Chuỗi String mô tả message của exception nếu quá trình xác
	 *            nhận failed.
	 */
	public static void hasText(String text, String message) {

		if (text == null) {
			throw new JGentleRuntimeException(message);
		}
		int strLen = text.length();
		if (strLen == 0)
			throw new JGentleRuntimeException(message);
		int notSpaceNum = 0;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				notSpaceNum++;
			}
		}
		if (notSpaceNum == 0)
			throw new JGentleRuntimeException(message);
	}

	/**
	 * Xác nhận một String có nội dung hợp lệ; có nghĩa là String không được
	 * phép là <code>null</code> và phải có ít nhất một kí tự không phải là
	 * khoảng trắng.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;Name không được phép rỗng&quot;);
	 * </pre>
	 * 
	 * @param text
	 *            Chuỗi cần xác nhận
	 */
	public static void hasText(String text) {

		hasText(
				text,
				"[Quá trình xác nhận failed] - Tham số chuỗi này không hợp lệ; nó không được phép là <code>null</code>, rỗng, hoặc chỉ có khoảng trắng.");
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot;);
	 * </pre>
	 * 
	 * @param textToSearch
	 *            the text to search
	 * @param substring
	 *            the substring to find within the text
	 * @param message
	 *            the exception message to use if the assertion fails
	 */
	public static void doesNotContain(String textToSearch, String substring,
			String message) {

		if ((textToSearch == null || textToSearch.isEmpty())
				&& (substring == null || substring.isEmpty())
				&& (textToSearch != null && textToSearch.indexOf(substring) != -1)) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;);
	 * </pre>
	 * 
	 * @param textToSearch
	 *            the text to search
	 * @param substring
	 *            the substring to find within the text
	 */
	public static void doesNotContain(String textToSearch, String substring) {

		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring ["
						+ substring + "]");
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array, &quot;The array must have elements&quot;);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws JGentleRuntimeException
	 *             if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array, String message) {

		if (array == null || array.length < 1) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array);
	 * </pre>
	 * 
	 * @param array
	 *            the array to check
	 * @throws JGentleRuntimeException
	 *             if the object array is <code>null</code> or has no elements
	 */
	public static void notEmpty(Object[] array) {

		notEmpty(
				array,
				"[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection
	 *            the collection to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws JGentleRuntimeException
	 *             if the collection is <code>null</code> or has no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {

		if (collection == null || collection.size() < 1) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection
	 *            the collection to check
	 * @throws JGentleRuntimeException
	 *             if the collection is <code>null</code> or has no elements
	 */
	public static void notEmpty(Collection<?> collection) {

		notEmpty(
				collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map, &quot;Map must have entries&quot;);
	 * </pre>
	 * 
	 * @param map
	 *            the map to check
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws JGentleRuntimeException
	 *             if the map is <code>null</code> or has no entries
	 */
	public static void notEmpty(Map<?, ?> map, String message) {

		if (map == null || map.size() < 1) {
			throw new JGentleRuntimeException(message);
		}
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map);
	 * </pre>
	 * 
	 * @param map
	 *            the map to check
	 * @throws JGentleRuntimeException
	 *             if the map is <code>null</code> or has no entries
	 */
	public static void notEmpty(Map<?, ?> map) {

		notEmpty(
				map,
				"[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param clazz
	 *            the required class
	 * @param obj
	 *            the object to check
	 * @throws JGentleRuntimeException
	 *             if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj) {

		isInstanceOf(clazz, obj, "");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param clazz
	 *            the required class
	 * @param obj
	 *            the object to check
	 * @param message
	 *            a message which will be prepended to the message produced by
	 *            the function itself, and which may be used to provide context.
	 *            It should normally end in a ": " or ". " so that the function
	 *            generate message looks ok when prepended to it.
	 * @throws JGentleRuntimeException
	 *             if the object is not an instance of clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class<?> clazz, Object obj, String message) {

		Assertor.notNull(clazz,
				"The clazz to perform the instanceof assertion cannot be null");
		Assertor.isTrue(clazz.isInstance(obj), message + "Object of class '"
				+ (obj != null ? obj.getClass().getName() : "[null]")
				+ "' must be an instance of '" + clazz.getName() + "'");
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType
	 *            the super type to check
	 * @param subType
	 *            the sub type to check
	 * @throws JGentleRuntimeException
	 *             if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {

		isAssignable(superType, subType, "");
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType
	 *            the super type to check
	 * @param subType
	 *            the sub type to check
	 * @param message
	 *            a message which will be prepended to the message produced by
	 *            the function itself, and which may be used to provide context.
	 *            It should normally end in a ": " or ". " so that the function
	 *            generate message looks ok when prepended to it.
	 * @throws JGentleRuntimeException
	 *             if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType,
			String message) {

		Assertor.notNull(superType, "superType cannot be null");
		Assertor.notNull(subType, "subType cannot be null");
		Assertor.isTrue(superType.isAssignableFrom(subType), message + "Type ["
				+ subType.getName() + "] is not assignable to type ["
				+ superType.getName() + "]");
	}

	/**
	 * Assert a boolean expression, throwing <code>IllegalStateException</code>
	 * if the test result is <code>false</code>. Call isTrue if you wish to
	 * throw JGentleRuntimeException on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @param message
	 *            the exception message to use if the assertion fails
	 * @throws IllegalStateException
	 *             if expression is <code>false</code>
	 */
	public static void state(boolean expression, String message) {

		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing {@link IllegalStateException} if
	 * the test result is <code>false</code>.
	 * <p>
	 * Call {@link #isTrue(boolean)} if you wish to throw
	 * {@link JGentleRuntimeException} on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null);
	 * </pre>
	 * 
	 * @param expression
	 *            a boolean expression
	 * @throws IllegalStateException
	 *             if the supplied expression is <code>false</code>
	 */
	public static void state(boolean expression) {

		state(expression,
				"[Assertion failed] - this state invariant must be true");
	}
}
