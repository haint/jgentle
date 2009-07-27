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
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Interface CoreInstantiationSelector.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 12, 2008
 * @see Selector
 */
public interface CoreInstantiationSelector extends Selector {
	/**
	 * Gets the caching list.
	 */
	public Map<Definition, CachedConstructor> getCachingList();

	/**
	 * Sets the caching list.
	 * 
	 * @param cachingList
	 *            the cachingList to set
	 */
	public void setCachingList(Map<Definition, CachedConstructor> cachingList);

	/**
	 * Gets the type.
	 */
	public Class<?> getType();

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(Class<?> type);

	/**
	 * Gets the reference name.
	 */
	public String getReferenceName();

	/**
	 * Sets the reference name.
	 * 
	 * @param referenceName
	 *            the new reference name
	 */
	public void setReferenceName(String referenceName);

	/**
	 * Returns an array containing the class objects in order to identify the
	 * constructor's formal parameter types.
	 */
	public Class<?>[] getArgTypes();

	/**
	 * Sets the arg types.
	 * 
	 * @param argTypes
	 *            the arg types
	 */
	public void setArgTypes(Class<?>[] argTypes);

	/**
	 * Returns the arguments used for the suitable constructor call which has
	 * given argument types.
	 */
	public Object[] getArgs();

	/**
	 * Sets the args.
	 * 
	 * @param args
	 *            the args
	 */
	public void setArgs(Object[] args);
}
