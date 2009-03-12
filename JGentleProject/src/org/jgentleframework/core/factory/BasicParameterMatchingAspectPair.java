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
package org.jgentleframework.core.factory;

import java.lang.reflect.Method;

import org.jgentleframework.context.aop.support.ParameterMatching;
import org.jgentleframework.core.reflection.metadata.MetadataController;
import org.jgentleframework.utils.Assertor;

/**
 * The Class BasicParameterMatchingAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 16, 2008
 * @see MetadataController
 * @see ParameterMatching
 */
public class BasicParameterMatchingAspectPair extends MetadataController
		implements ParameterMatching<Method> {
	/** The method. */
	final transient Method	method;
	/** The parameter index. */
	final int				parameterIndex;

	/**
	 * Instantiates a new basic parameter matching aspect pair.
	 * 
	 * @param method
	 *            the method
	 * @param parameterIndex
	 *            the parameter index
	 */
	public BasicParameterMatchingAspectPair(Method method, int parameterIndex) {

		Assertor.notNull(method,
				"The given method according to index must not be null!");
		this.method = method;
		this.parameterIndex = parameterIndex;
	}

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -7137591263862996915L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.support.ParameterMatching#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.support.ParameterMatching#getElement()
	 */
	@Override
	public Method getElement() {

		return this.method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.support.ParameterMatching#getIndex()
	 */
	@Override
	public int getIndex() {

		return this.parameterIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.support.Matching#getTargetObject()
	 */
	@Override
	public Object getTargetObject() {

		return this.method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.support.ClassMatching#getTargetClass()
	 */
	@Override
	public Class<?> getTargetClass() {

		return this.method.getDeclaringClass();
	}
}
