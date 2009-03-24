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

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.jgentleframework.utils.ReflectUtils;

/**
 * This class implements the {@link FieldIdentification} interface, specify a
 * field or a set of fields corresponding to declaring class and one regular
 * expression of name.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 21, 2008
 * @see MemberIdentification
 * @see MethodIdentification
 * @see SingleClassIdentification
 */
class FieldIdentificationImpl implements FieldIdentification {
	/** The name. */
	String		name				= null;

	/** The declaring class. */
	Class<?>	declaringClass		= null;

	/** The found on superclass. */
	boolean		foundOnSuperclass	= false;

	/** The modifiers. */
	int			modifiers			= Identification.NO_MODIFIERS;

	/**
	 * Constructor.
	 */
	public FieldIdentificationImpl() {

	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of member.
	 */
	public FieldIdentificationImpl(String name) {

		this.name = name;
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of member.
	 * @param foundOnSuperclass
	 *            the foundOnSuperclass boolean
	 */
	public FieldIdentificationImpl(String name, boolean foundOnSuperclass) {

		this.name = name;
		this.foundOnSuperclass = foundOnSuperclass;
	}

	/**
	 * The Constructor.
	 * 
	 * @param name
	 *            the name of member.
	 * @param foundOnSuperclass
	 *            the foundOnSuperclass boolean
	 * @param modifiers
	 *            the modifiers
	 */
	public FieldIdentificationImpl(String name, boolean foundOnSuperclass,
			int modifiers) {

		this.name = name;
		this.foundOnSuperclass = foundOnSuperclass;
		this.modifiers = modifiers;
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of member.
	 * @param declaringClass
	 *            the declaring class
	 * @param foundOnSuperclass
	 *            the foundOnSuperclass boolean
	 * @param modifiers
	 *            the modifiers
	 */
	public FieldIdentificationImpl(String name, Class<?> declaringClass,
			boolean foundOnSuperclass, int modifiers) {

		this.name = name;
		this.declaringClass = declaringClass;
		this.foundOnSuperclass = foundOnSuperclass;
		this.modifiers = modifiers;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.general.reflection.FieldIdentification#getModifiers
	 * ()
	 */
	@Override
	public int getModifiers() {

		return modifiers;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.general.reflection.MemberIdentification#setModifiers
	 * (int)
	 */
	@Override
	public void setModifiers(int modifiers) {

		this.modifiers = modifiers;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.general.reflection.FieldIdentification#getName()
	 */
	@Override
	public String getName() {

		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.general.reflection.Identification#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.general.reflection.FieldIdentification#getDeclaringClass
	 * ()
	 */
	@Override
	public Class<?> getDeclaringClass() {

		return declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.general.reflection.MemberIdentification#
	 * setDeclaringClass(java.lang.Class)
	 */
	@Override
	public void setDeclaringClass(Class<?> declaringClass) {

		this.declaringClass = declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.general.reflection.Identification#getMember()
	 */
	@Override
	public Field[] getMember() {

		if (this.name == null || this.name.isEmpty()
				|| this.declaringClass == null) {
			throw new ReflectException(
					"The identification data is not enough or invalid !");
		}
		Field[] lst = ReflectUtils.fields(this.name, this.declaringClass,
				foundOnSuperclass);
		ArrayList<Field> result = new ArrayList<Field>();
		for (Field field : lst) {
			if (this.modifiers != Identification.NO_MODIFIERS) {
				if (field.getModifiers() == this.modifiers) {
					result.add(field);
				}
			}
			else
				result.add(field);
		}
		return result.toArray(new Field[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.general.reflection.MemberIdentification#
	 * setFoundOnSuperclass(boolean)
	 */
	@Override
	public void setFoundOnSuperclass(boolean bool) {

		this.foundOnSuperclass = bool;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.general.reflection.FieldIdentification#
	 * isFoundOnSuperclass()
	 */
	@Override
	public boolean isFoundOnSuperclass() {

		return foundOnSuperclass;
	}
}
