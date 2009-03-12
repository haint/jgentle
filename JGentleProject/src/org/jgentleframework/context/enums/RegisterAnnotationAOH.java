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
package org.jgentleframework.context.enums;

import java.lang.annotation.Annotation;

import org.jgentleframework.configure.annotation.AnnotatedWith;
import org.jgentleframework.configure.annotation.AnnotatedWithValue;
import org.jgentleframework.configure.annotation.AnnotationClass;
import org.jgentleframework.configure.annotation.AnnotationValidators;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.annotation.MustBeExtendedBy;
import org.jgentleframework.configure.annotation.MustBeImplementedBy;
import org.jgentleframework.configure.annotation.Unique;
import org.jgentleframework.core.handling.AnnotatedWithDefHandling;
import org.jgentleframework.core.handling.AnnotatedWithValueDefHandling;
import org.jgentleframework.core.handling.MustBeExtendedByDefHandling;
import org.jgentleframework.core.handling.MustBeImplementedByDefHandling;
import org.jgentleframework.core.handling.UniqueDefHandling;
import org.jgentleframework.core.provider.AnnotationValidator;

/**
 * Chứa đựng thông tin các annotation cần phải đăng kí của
 * ServiceHandler.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 19, 2007
 */
public enum RegisterAnnotationAOH {
	Unique(Unique.class, new UniqueDefHandling()), MustBeImplementedBy(
			MustBeImplementedBy.class, new MustBeImplementedByDefHandling()), MustBeExtendedBy(
			MustBeExtendedBy.class, new MustBeExtendedByDefHandling()), AnnotatedWithValue(
			AnnotatedWithValue.class, new AnnotatedWithValueDefHandling()), AnnotatedWith(
			AnnotatedWith.class, new AnnotatedWithDefHandling()), BeanServices(
			BeanServices.class, null);
	Class<? extends Annotation>					annoClazz;
	AnnotationValidator<? extends Annotation>	validator	= null;

	<T extends Annotation> RegisterAnnotationAOH(Class<T> clazz,
			AnnotationValidator<T> validator) {

		this.annoClazz = clazz;
		this.validator = validator;
	}

	@AnnotationClass
	public Class<? extends Annotation> getAnnoClazz() {

		return annoClazz;
	}

	@AnnotationValidators
	public AnnotationValidator<? extends Annotation> getValidator() {

		return validator;
	}
}
