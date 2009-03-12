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
package org.jgentleframework.context.support;

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Class CoreInstantiationSelectorImpl.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 9, 2008
 */
public class CoreInstantiationSelectorImpl extends SelectorImpl implements
		CoreInstantiationSelector {
	/** The mapping name. */
	String		mappingName	= null;
	/** The type. */
	Class<?>	type		= null;
	/** The arg types. */
	Class<?>[]	argTypes	= null;
	/** The args. */
	Object[]	args		= null;

	/**
	 * Instantiates a new core selector.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param definition
	 *            the definition
	 * @param type
	 *            the type
	 * @param mappingName
	 *            the mapping name
	 * @param argTypes
	 *            the arg types
	 * @param args
	 *            the args
	 */
	public CoreInstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String mappingName, Class<?>[] argTypes, Object[] args,
			Definition definition) {

		this.targetClass = targetClass;
		this.definition = definition;
		this.type = type;
		this.mappingName = mappingName;
		this.argTypes = argTypes != null ? argTypes.clone() : this.argTypes;
		this.args = args != null ? args.clone() : this.args;
	}

	/**
	 * Instantiates a new core selector.
	 * 
	 * @param mappingName
	 *            the mapping name
	 */
	public CoreInstantiationSelectorImpl(String mappingName) {

		this.mappingName = mappingName;
	}

	/**
	 * Instantiates a new core selector.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param definition
	 *            the definition
	 * @param type
	 *            the type
	 * @param mappingName
	 *            the mapping name
	 */
	public CoreInstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition) {

		this.targetClass = targetClass;
		this.definition = definition;
		this.type = type;
		this.mappingName = mappingName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#getMappingName()
	 */
	@Override
	public String getMappingName() {

		return this.mappingName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#getType()
	 */
	@Override
	public Class<?> getType() {

		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#setMappingName(java.lang.String)
	 */
	@Override
	public void setMappingName(String mappingName) {

		this.mappingName = mappingName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#setType(java.lang.Class)
	 */
	@Override
	public void setType(Class<?> type) {

		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#getArgTypes()
	 */
	@Override
	public Class<?>[] getArgTypes() {

		return argTypes != null ? argTypes.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#setArgTypes(java.lang.Class<?>[])
	 */
	@Override
	public void setArgTypes(Class<?>[] argTypes) {

		this.argTypes = argTypes != null ? argTypes.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return args != null ? args.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.support.CoreInstantiationSelector#setArgs(java.lang.Object[])
	 */
	@Override
	public void setArgs(Object[] args) {

		this.args = args != null ? args.clone() : null;
	}
}
