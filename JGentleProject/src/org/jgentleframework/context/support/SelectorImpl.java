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
package org.jgentleframework.context.support;

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The implementation of {@link Selector} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 12, 2008
 * @see Selector
 */
public class SelectorImpl implements Selector {
	/** The definition. */
	protected Definition	definition	= null;

	/** The target class. */
	protected Class<?>		targetClass	= null;

	protected String		scopeName	= null;

	/**
	 * Instantiates a new selector impl.
	 */
	public SelectorImpl() {

	}

	/**
	 * Instantiates a new selector impl.
	 * 
	 * @param definition
	 *            the definition
	 * @param targetClass
	 *            the target class
	 */
	public SelectorImpl(Definition definition, Class<?> targetClass,
			String scopeName) {

		this.definition = definition;
		this.targetClass = targetClass;
		this.scopeName = scopeName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.support.Selector#getDefinition()
	 */
	@Override
	public Definition getDefinition() {

		return this.definition;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.support.Selector#getTargetClass()
	 */
	@Override
	public Class<?> getTargetClass() {

		return this.targetClass;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.Selector#setDefinition(org.
	 * jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public void setDefinition(Definition definition) {

		this.definition = definition;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.Selector#setTargetClass(java.lang
	 * .Class)
	 */
	@Override
	public void setTargetClass(Class<?> targetClass) {

		this.targetClass = targetClass;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.support.Selector#getScopeName()
	 */
	@Override
	public String getScopeName() {

		return this.scopeName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.Selector#setScopeName(java.lang.
	 * String)
	 */
	@Override
	public void setScopeName(String scopeName) {

		this.scopeName = scopeName;
	}
}
