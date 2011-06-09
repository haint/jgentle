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
import java.util.Set;

import org.jgentleframework.configure.annotation.MustBeImplementedBy;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an implementation of {@link AnnotationValidator} interface. It
 * is reponsible for {@link MustBeImplementedBy} annotation validation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 27, 2007
 * @see MustBeImplementedBy
 */
public class MustBeImplementedByDefHandling implements
		AnnotationValidator<MustBeImplementedBy> {
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
	public void validate(MustBeImplementedBy annotation, Annotation[] annoList,
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
		if (!ReflectUtils.isAnnotation(object)) {
			throw exception;
		}
		Class<?>[] interfaceList = annotation.interfaces();
		Set<Class<?>> current = ReflectUtils.getAllInterfaces(clazz, true);
		if (annotation.logicalBool() == true) {
			for (Class<?> obj : interfaceList) {
				if (!current.contains(obj)) {
					throw exception;
				}
			}
		}
		else {
			int i = 0;
			for (Class<?> obj : interfaceList) {
				if (current.contains(obj)) {
					i++;
				}
			}
			if (i == 0) {
				throw exception;
			}
		}
	}
}
