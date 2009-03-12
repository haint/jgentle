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
	 * Gets the type.
	 * 
	 * @return the type
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
	 * Gets the mapping name.
	 * 
	 * @return the mappingName
	 */
	public String getMappingName();

	/**
	 * Sets the mapping name.
	 * 
	 * @param mappingName
	 *            the mappingName to set
	 */
	public void setMappingName(String mappingName);

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
	 * Returns the arguments used for the corresponding constructor call which
	 * has given argument types.
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
