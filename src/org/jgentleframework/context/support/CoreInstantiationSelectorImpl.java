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

import java.util.Map;

import org.jgentleframework.core.factory.support.CachedConstructor;
import org.jgentleframework.reflection.metadata.Definition;

/**
 * The Class CoreInstantiationSelectorImpl.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 9, 2008
 */
public class CoreInstantiationSelectorImpl extends SelectorImpl implements
		CoreInstantiationSelector {
	/** The reference name. */
	String		referenceName	= null;

	/** The type. */
	Class<?>	type			= null;

	/** The arg types. */
	Class<?>[]	argTypes		= null;

	/** The args. */
	Object[]	args			= null;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#getCachingList
	 * ()
	 */
	@Override
	public Map<Definition, CachedConstructor> getCachingList() {

		return cachingList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#setCachingList
	 * (java.util.Map)
	 */
	@Override
	public void setCachingList(Map<Definition, CachedConstructor> cachingList) {

		this.cachingList = cachingList;
	}

	/** The caching list. */
	Map<Definition, CachedConstructor>	cachingList	= null;

	/**
	 * Instantiates a new core instantiation selector impl.
	 * 
	 * @param type
	 *            the type
	 * @param targetClass
	 *            the target class
	 * @param referenceName
	 *            the reference name
	 * @param argTypes
	 *            the arg types
	 * @param args
	 *            the args
	 * @param definition
	 *            the definition
	 */
	public CoreInstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String referenceName, Class<?>[] argTypes, Object[] args,
			Definition definition) {

		this.targetClass = targetClass;
		this.definition = definition;
		this.type = type;
		this.referenceName = referenceName;
		this.argTypes = argTypes != null ? argTypes.clone() : this.argTypes;
		this.args = args != null ? args.clone() : this.args;
	}

	/**
	 * Instantiates a new core instantiation selector impl.
	 * 
	 * @param referenceName
	 *            the reference name
	 */
	public CoreInstantiationSelectorImpl(String referenceName) {

		this.referenceName = referenceName;
	}

	/**
	 * Instantiates a new core instantiation selector impl.
	 * 
	 * @param type
	 *            the type
	 * @param targetClass
	 *            the target class
	 * @param referenceName
	 *            the reference name
	 * @param definition
	 *            the definition
	 */
	public CoreInstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String referenceName, Definition definition) {

		this.targetClass = targetClass;
		this.definition = definition;
		this.type = type;
		this.referenceName = referenceName;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.CoreInstantiationSelector#
	 * getReferenceName()
	 */
	@Override
	public String getReferenceName() {

		return this.referenceName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#getType()
	 */
	@Override
	public Class<?> getType() {

		return this.type;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#setMappingName
	 * (java.lang.String)
	 */
	@Override
	public void setReferenceName(String referenceName) {

		this.referenceName = referenceName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#setType
	 * (java.lang.Class)
	 */
	@Override
	public void setType(Class<?> type) {

		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#getArgTypes
	 * ()
	 */
	@Override
	public Class<?>[] getArgTypes() {

		return argTypes != null ? argTypes.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#setArgTypes
	 * (java.lang.Class<?>[])
	 */
	@Override
	public void setArgTypes(Class<?>[] argTypes) {

		this.argTypes = argTypes != null ? argTypes.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return args != null ? args.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.CoreInstantiationSelector#setArgs
	 * (java.lang.Object[])
	 */
	@Override
	public void setArgs(Object[] args) {

		this.args = args != null ? args.clone() : null;
	}
}
