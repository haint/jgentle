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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jgentleframework.configure.annotation.Unique;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.NullClass;

/**
 * This is an implementation of {@link AnnotationValidator} interface in order
 * to validate an annotation is unique corresponding to target element annotated
 * with {@link Unique} annotation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 27, 2007
 * @see Unique
 */
public class UniqueDefHandling implements AnnotationValidator<Unique> {
	/**
	 * Instantiates a new unique def handling.
	 */
	public UniqueDefHandling() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.provider.AnnotationValidator#catchException(java.lang.RuntimeException,
	 *      java.lang.annotation.Annotation)
	 */
	@Override
	public <V extends RuntimeException, U extends Annotation> boolean catchException(
			V exception, U annotation) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.provider.AnnotationValidator#validate(java.lang.annotation.Annotation,
	 *      java.lang.annotation.Annotation[], java.lang.Object,
	 *      java.lang.Class,
	 *      org.jgentleframework.core.metadatahandling.defhandling.DefinitionManager)
	 */
	@Override
	public void validate(Unique annotation, Annotation[] annoList,
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
		if (ReflectUtils.isClass(object)) {
			if (!((Class<?>) object).isAnnotation()) {
				throw exception;
			}
		}
		else {
			if (ReflectUtils.isAnnotation(object) == false) {
				throw exception;
			}
		}
		List<Class<?>> except = Arrays.asList(annotation.except());
		List<Annotation> listAnno = new LinkedList<Annotation>();
		// loại bỏ các annotation không được đăng kí.
		for (Annotation anno : clazz.getAnnotations()) {
			if (definitionManager.getAnnotationRegister()
					.isRegisteredAnnotation(anno.annotationType())) {
				listAnno.add(anno);
			}
		}
		if (except.size() == 0
				|| (except.size() == 1 && except.get(0).equals(NullClass.class))) {
			if (listAnno.size() != 1) {
				throw exception;
			}
		}
		else {
			for (Annotation anno : listAnno) {
				if (!except.contains(anno.annotationType())) {
					throw exception;
				}
			}
		}
	}
}
