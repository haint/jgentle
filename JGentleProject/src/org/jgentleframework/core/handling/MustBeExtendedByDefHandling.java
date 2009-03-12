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
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgentleframework.configure.annotation.MustBeExtendedBy;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Class validator này được implements từ interface {@link AnnotationValidator}
 * thực thi việc validate cho annotation {@link MustBeExtendedBy}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 20, 2007
 * @see MustBeExtendedBy
 */
public class MustBeExtendedByDefHandling implements
		AnnotationValidator<MustBeExtendedBy> {
	@Override
	public <V extends RuntimeException, U extends Annotation> boolean catchException(
			V exception, U annotation) {

		return false;
	}

	@Override
	public void validate(MustBeExtendedBy annotation, Annotation[] annoList,
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
		List<Class<?>> extendedList = Arrays.asList(annotation.clazz());
		int i = 0;
		if (clazz.isInterface()) {
			Set<Class<?>> currentExtended = ReflectUtils.getAllInterfaces(
					clazz, true);
			for (Class<?> obj : extendedList) {
				if (currentExtended.contains(obj)) {
					i++;
				}
			}
		}
		else {
			List<Class<?>> currentExtended = ReflectUtils.getAllSuperClass(
					clazz, false);
			for (Class<?> obj : extendedList) {
				if (currentExtended.contains(obj)) {
					i++;
				}
			}
		}
		if (i == 0) {
			throw exception;
		}
	}
}
