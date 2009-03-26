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

import org.jgentleframework.configure.annotation.AnnotationClass;
import org.jgentleframework.context.annotation.ComponentServiceContext;

/**
 * The Enum RegisterAnnotationContext.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 27, 2007
 */
public enum RegisterAnnotationContext {
	CSContext (ComponentServiceContext.class);
	Class<? extends Annotation>	annotationClass;

	/**
	 * Instantiates a new register annotation context.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	RegisterAnnotationContext(Class<? extends Annotation> clazz) {

		this.annotationClass = clazz;
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
}
