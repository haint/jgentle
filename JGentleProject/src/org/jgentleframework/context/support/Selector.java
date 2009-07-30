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
 * The Interface Selector.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 12, 2008
 * @see CoreInstantiationSelector
 * @see InstantiationSelector
 */
public interface Selector {
	/**
	 * Gets the definition.
	 * 
	 * @return the definition
	 */
	public Definition getDefinition();

	/**
	 * Sets the definition.
	 * 
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(Definition definition);

	/**
	 * Gets the target class.
	 * 
	 * @return the target class
	 */
	public Class<?> getTargetClass();

	/**
	 * Sets the target class.
	 * 
	 * @param targetClass
	 *            the new target class
	 */
	public void setTargetClass(Class<?> targetClass);

	/**
	 * Sets the scope name.
	 * 
	 * @param scopeName
	 *            the new scope name
	 */
	public void setScopeName(String scopeName);

	/**
	 * Gets the scope name.
	 * 
	 * @return the scope name
	 */
	public String getScopeName();
}
