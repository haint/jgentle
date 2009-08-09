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
import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.configure.annotation.AnnotatedWith;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.data.NullAnno;

/**
 * The validator of {@link AnnotatedWith} annotation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 19, 2007
 */
public class AnnotatedWithDefHandling implements
		AnnotationValidator<AnnotatedWith> {
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
	public void validate(AnnotatedWith annotation, Annotation[] annoList,
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
		Class<? extends Annotation>[] clazzlist = annotation.annotation();
		String[] strList = annotation.annoClassPath();
		if (clazzlist.length == 0
				|| (clazzlist.length == 1 && clazzlist[0] == NullAnno.class)) {
			if ((strList.length == 1 && strList[0].isEmpty())
					|| strList.length == 0) {
				throw exception;
			}
			else {
				checkStrList(strList, exception, annoRawList);
			}
		}
		else {
			for (Class<? extends Annotation> clazzObj : clazzlist) {
				if (!annoRawList.contains(clazzObj)) {
					throw exception;
				}
			}
			if (!((strList.length == 1 && strList[0].isEmpty()) || strList.length == 0)) {
				checkStrList(strList, exception, annoRawList);
			}
		}
	}

	/**
	 * Check str list.
	 * 
	 * @param strList
	 *            danh sách mảng chuỗi các object class của annotation
	 * @param exception
	 *            ngoại lệ sẽ được ném ra
	 * @param annoRawList
	 *            danh sách annotation gốc được chỉ định.
	 */
	@SuppressWarnings("unchecked")
	private void checkStrList(String[] strList, RuntimeException exception,
			List<Class<? extends Annotation>> annoRawList) {

		List<Class<? extends Annotation>> strClazzList = new ArrayList<Class<? extends Annotation>>();
		for (String str : strList) {
			try {
				Class<?> obj = Class.forName(str);
				if (!obj.isAnnotation()) {
					throw exception;
				}
				else {
					strClazzList.add((Class<? extends Annotation>) obj);
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
		// Kiểm tra thông tin trong strList
		for (Class<? extends Annotation> clazzObj : strClazzList) {
			if (!annoRawList.contains(clazzObj)) {
				throw exception;
			}
		}
	}
}
