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
package org.jgentleframework.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.context.aop.advice.MethodBeforeAdvice;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;

/**
 * Represents a {@link MethodInterceptor} containing {@link MethodBeforeAdvice}
 * instances need to be invoked.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 19, 2008
 */
abstract class AbstractBeforeAdvice implements MethodInterceptor {
	/** The definition. */
	Definition	definition;

	/**
	 * The Constructor.
	 * 
	 * @param definition
	 *            the definition
	 */
	public AbstractBeforeAdvice(Definition definition) {

		Assertor
				.notNull(definition, "The given definition must not be null!");
		this.definition = definition;
	}
}
