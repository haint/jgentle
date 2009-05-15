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
package org.jgentleframework.configure.aopweaving.enums;

import java.lang.annotation.Annotation;

import org.jgentleframework.configure.annotation.AnnotationClass;
import org.jgentleframework.configure.aopweaving.annotation.After;
import org.jgentleframework.configure.aopweaving.annotation.Around;
import org.jgentleframework.configure.aopweaving.annotation.Before;
import org.jgentleframework.configure.aopweaving.annotation.Throws;

/**
 * Chứa đựng các thông tin về các annotation cần phải đăng kí của AOP system.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 1, 2007
 */
public enum RegisterAnnotationAOP {
	/** The After returning advice. */
	AfterReturningAdvice(After.class),
	/** The Around advice. */
	AroundAdvice(Around.class),
	/** The Method before advice. */
	MethodBeforeAdvice(Before.class),
	/** The Throws advice. */
	ThrowsAdvice(Throws.class);
	/** The anno clazz. */
	Class<? extends Annotation>	annoClazz;

	/**
	 * Instantiates a new register annotation aop.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	RegisterAnnotationAOP(Class<? extends Annotation> clazz) {

		this.annoClazz = clazz;
	}

	/**
	 * Gets the anno clazz.
	 * 
	 * @return the anno clazz
	 */
	@AnnotationClass
	public Class<? extends Annotation> getAnnoClazz() {

		return annoClazz;
	}
}
