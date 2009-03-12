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
package org.jgentleframework.core.reflection;

import java.lang.reflect.Modifier;

/**
 * The Class SingleClassIdentificationImpl.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 22, 2008
 * @see Identification
 */
class SingleClassIdentificationImpl implements SingleClassIdentification {
	/** The name. */
	String	name		= null;
	/** The modifier. */
	int		modifiers	= Modifier.PUBLIC;

	/**
	 * Constructor.
	 */
	public SingleClassIdentificationImpl() {

	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of class.
	 * @param modifiers
	 *            the modifiers
	 */
	public SingleClassIdentificationImpl(String name, int modifiers) {

		this.name = name;
		this.modifiers = modifiers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.Identification#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.Identification#getMember()
	 */
	@Override
	public Class<?>[] getMember() {

		if (this.name == null || this.name.isEmpty()) {
			throw new ReflectException(
					"The identification data is not enough or invalid !");
		}
		Class<?>[] result = new Class<?>[1];
		try {
			Class<?> clazz = Class.forName(this.name);
			if (clazz.getModifiers() == this.modifiers)
				result[0] = clazz;
		}
		catch (ClassNotFoundException e) {
			ReflectException ex = new ReflectException(e.getMessage());
			ex.initCause(e);
			throw ex;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.SingleClassIdentification#getModifier()
	 */
	public int getModifiers() {

		return this.modifiers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.SingleClassIdentification#setModifier(int)
	 */
	public void setModifiers(int modifiers) {

		this.modifiers = modifiers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.SingleClassIdentification#getName()
	 */
	public String getName() {

		return this.name;
	}
}
