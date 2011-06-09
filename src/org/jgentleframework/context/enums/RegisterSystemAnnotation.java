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
 * The Enum RegisterSystemAnnotation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 19, 2007
 * @see RegisterAnnotationInjecting
 * @see RegisterAnnotationContext
 */
public enum RegisterSystemAnnotation {
	/** The Unique. */
	Unique (Unique.class,new UniqueDefHandling()),
	/** The Must be implemented by. */
	MustBeImplementedBy (MustBeImplementedBy.class,
			new MustBeImplementedByDefHandling()),
	/** The Must be extended by. */
	MustBeExtendedBy (MustBeExtendedBy.class,new MustBeExtendedByDefHandling()),
	/** The Annotated with value. */
	AnnotatedWithValue (AnnotatedWithValue.class,
			new AnnotatedWithValueDefHandling()),
	/** The Annotated with. */
	AnnotatedWith (AnnotatedWith.class,new AnnotatedWithDefHandling()),
	/** The Bean services. */
	BeanServices (BeanServices.class,null);
	/** The annotation class. */
	Class<? extends Annotation>					annotationClass;

	/** The validator. */
	AnnotationValidator<? extends Annotation>	validator	= null;

	/**
	 * Instantiates a new register system annotation.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param validator
	 *            the validator
	 */
	<T extends Annotation> RegisterSystemAnnotation(Class<T> clazz,
			AnnotationValidator<T> validator) {

		this.annotationClass = clazz;
		this.validator = validator;
	}

	/**
	 * Gets the annotation class.
	 * 
	 * @return the annotation class
	 */
	@AnnotationClass
	public Class<? extends Annotation> getAnnotationClass() {

		return annotationClass;
	}

	/**
	 * Gets the validator.
	 * 
	 * @return the validator
	 */
	@AnnotationValidators
	public AnnotationValidator<? extends Annotation> getValidator() {

		return validator;
	}
}
