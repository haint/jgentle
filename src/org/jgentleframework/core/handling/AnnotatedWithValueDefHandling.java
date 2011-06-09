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
 * Project: JGentleFramework
 */
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jgentleframework.configure.annotation.AnnotatedWithValue;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.NullAnno;

/**
 * This class is an implementation of {@link AnnotationValidator} interface. It
 * is reponsible for {@link AnnotatedWithValue} annotation validation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 20, 2007
 * @see AnnotationValidator
 * @see AnnotatedWithValue
 */
public class AnnotatedWithValueDefHandling implements
		AnnotationValidator<AnnotatedWithValue> {
	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.AnnotationValidator#catchException
	 * (java.lang.RuntimeException, java.lang.annotation.Annotation)
	 */
	@Override
	public <V extends RuntimeException, U extends Annotation> boolean catchException(
			V exception, U annotation) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.AnnotationValidator#validate(java.
	 * lang.annotation.Annotation, java.lang.annotation.Annotation[],
	 * java.lang.Object, java.lang.Class,
	 * org.jgentleframework.core.handling.DefinitionManager)
	 */
	@Override
	public void validate(AnnotatedWithValue annotation, Annotation[] annoList,
			Object object, Class<?> clazz, DefinitionManager definitionManager) {

		RuntimeException exception = null;
		try {
			exception = annotation.throwsException().newInstance();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		List<Class<? extends Annotation>> annoRawList = new ArrayList<Class<? extends Annotation>>();
		for (Annotation anno : clazz.getAnnotations()) {
			annoRawList.add(anno.annotationType());
		}
		Class<? extends Annotation> annoClazz = annotation.annotation();
		String strClazz = annotation.annoClassPath();
		if (annoClazz == null || annoClazz.equals(NullAnno.class)) {
			if (strClazz == null || strClazz.isEmpty()) {
				throw exception;
			}
			else {
				// kiểm tra annoClassPath
				checkStrList(strClazz, exception, annotation, annoRawList,
						clazz.getAnnotations(), annotation.object_value_pair());
			}
		}
		else {
			if (!annoRawList.contains(annoClazz)) {
				throw exception;
			}
			else {
				for (Annotation anno : clazz.getAnnotations()) {
					if (anno.annotationType().equals(annoClazz)) {
						checkValuePair(annotation.object_value_pair(),
								exception, anno);
					}
					else {
						throw exception;
					}
				}
			}
			if (!(strClazz == null || strClazz.isEmpty())) {
				checkStrList(strClazz, exception, annotation, annoRawList,
						clazz.getAnnotations(), annotation.object_value_pair());
			}
		}
	}

	/**
	 * Kiểm tra thông tin của annoClassPath.
	 * 
	 * @param strClazz
	 *            đối tượng String giữ thông về classpath của annotation
	 * @param exception
	 *            ngoại lệ sẽ được ném ra nếu lỗi
	 * @param annotation
	 *            annotation cần validate
	 * @param annoRawList
	 *            danh sách object class của các annotation gốc
	 * @param annoList
	 *            danh sách các annotation gốc
	 * @param object_value_pair
	 *            mảng danh sách các cặp dữ liệu cần validate
	 */
	private void checkStrList(String strClazz, RuntimeException exception,
			AnnotatedWithValue annotation,
			List<Class<? extends Annotation>> annoRawList,
			Annotation[] annoList, String[] object_value_pair) {

		try {
			Class<?> annoClazz = Class.forName(strClazz);
			if (!annoClazz.isAnnotation()) {
				throw exception;
			}
			else {
				if (!annoRawList.contains(annoClazz)) {
					throw exception;
				}
				else {// Kiểm tra thông tin object_value_pair
					for (Annotation anno : annoList) {
						if (anno.annotationType().equals(annoClazz)) {
							checkValuePair(object_value_pair, exception, anno);
						}
					}
				}
			}
		}
		catch (ClassNotFoundException e) {
			try {
				throw exception.initCause(new Throwable(e.getMessage()));
			}
			catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Kiểm tra thông tin các cặp giá trị trong một annotation chỉ định.
	 * 
	 * @param object_value_pair
	 *            các cặp giá trị
	 * @param exception
	 *            ngoại lệ sẽ được ném ra
	 * @param annotation
	 *            annotation chỉ định kiểm tra.
	 */
	private void checkValuePair(String[] object_value_pair,
			RuntimeException exception, Annotation annotation) {

		// Kiểm tra thông tin trùng lắp trong các cặp giá trị
		ArrayList<String> valueList = new ArrayList<String>();
		for (String obj : object_value_pair) {
			String[] valuePair = obj.split(":=");
			if (!valueList.contains(valuePair[0])) {
				valueList.add(valuePair[0]);
			}
			else {
				throw exception;
			}
		}
		// Kiểm tra thông tin giá trị trong các cặp giá trị có tưong ứng với
		// annotation hay không.
		for (String string : object_value_pair) {
			String[] valuePair = string.split(":=");
			// Nếu thông tin của valuePair không hợp lệ
			if (valuePair.length < 2 || valuePair[0] == ""
					|| valuePair[1] == "") {
				throw exception;
			}
			// Lấy về đối tượng value chỉ định và thông tin của nó có phải là 1
			// array hay không.
			Object objName = null;
			boolean arrayBool = false;
			try {
				objName = ReflectUtils.invokeMethod(annotation, valuePair[0],
						null, null, false, false);
				arrayBool = objName != null ? objName.getClass().isArray()
						: arrayBool;
			}
			catch (RuntimeException e) {
				try {
					throw exception.initCause(new Throwable(e.getMessage()));
				}
				catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (arrayBool == true) {
				if (valuePair[1].startsWith("{") && valuePair[1].endsWith("}")) {
					valuePair[1] = valuePair[1].substring(1, valuePair[1]
							.length() - 1);
					String[] compare = valuePair[1].split(",");
					List<String> compareList = Arrays.asList(compare);
					if (Array.getLength(objName) != compare.length) {
						throw exception;
					}
					else {
						Class<?> type = objName.getClass().getComponentType();
						if (type.isAnnotation()) {
							throw exception;
						}
						for (int i = 0; i < Array.getLength(objName); i++) {
							if (type.toString().startsWith("class")
									|| type.toString().startsWith("interface")) {
								String[] strObj = Array.get(objName, i)
										.toString().split(" ");
								if (!compareList.contains(strObj[1].trim())) {
									throw exception;
								}
							}
							else {
								if (!compareList.contains(Array.get(objName, i)
										.toString())) {
									throw exception;
								}
							}
						}
					}
				}
				else {
					try {
						throw exception.initCause(new Throwable(
								"Invalid syntax: " + string));
					}
					catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
			else {// nếu thông tin của value không phải là 1 array
				if (ReflectUtils.isAnnotation(objName)) {
					throw exception;
				}
				else {
					Class<?> type = objName.getClass();
					if (type.toString().startsWith("class")
							|| type.toString().startsWith("interface")) {
						String[] strObj = objName.toString().split(" ");
						if (!valuePair[1].equals(strObj[1].trim())) {
							throw exception;
						}
					}
					else {
						if (!valuePair[1].equals(objName.toString().trim())) {
							throw exception;
						}
					}
				}
			}
		}
	}
}
