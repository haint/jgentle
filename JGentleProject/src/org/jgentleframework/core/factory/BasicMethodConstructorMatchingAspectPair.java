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
package org.jgentleframework.core.factory;

import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.MetadataKey;
import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.reflection.metadata.MetadataController;
import org.jgentleframework.reflection.metadata.MetadataImpl;

/**
 * The Class BasicMethodConstructorMatchingAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 11, 2008
 * @see MetadataController
 * @see MethodConstructorMatching
 */
public class BasicMethodConstructorMatchingAspectPair extends
		MetadataController implements MethodConstructorMatching<Method> {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -8732252753999849073L;
	/** The method. */
	final transient Method		method;

	
	/**
	 * Instantiates a new basic method constructor matching aspect pair.
	 * 
	 * @param method the method
	 * @param definition the definition
	 */
	public BasicMethodConstructorMatchingAspectPair(Method method,
			Definition definition) {

		super(null, null);
		this.method = method;
		addMetadata(new MetadataImpl(MetadataKey.DEFINITION, definition));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.MethodConstructorMatching#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.MethodConstructorMatching#getElement()
	 */
	@Override
	public Method getElement() {

		return this.method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.ClassMatching#getTargetClass()
	 */
	@Override
	public Class<?> getTargetClass() {

		return this.method.getDeclaringClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.Matching#getTargetObject()
	 */
	@Override
	public Object getTargetObject() {

		return getElement();
	}
}
