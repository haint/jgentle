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
package org.jgentleframework.reflection;

/**
 * This interface represents the class identification, designate a {@link Class}
 * or a set of {@link Class} that corresponds to one regular expression of name.
 * The identification data shall be used conjointly with reflection tools in
 * order to access specified classes.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 22, 2008
 */
public interface SingleClassIdentification extends Identification<Class<?>> {
	/**
	 * Gets the modifiers.
	 * 
	 * @return the modifiers
	 */
	public int getModifiers();

	/**
	 * Sets the modifiers.
	 * 
	 * @param modifiers
	 *            the modifiers to set
	 */
	public void setModifiers(int modifiers);

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName();
}