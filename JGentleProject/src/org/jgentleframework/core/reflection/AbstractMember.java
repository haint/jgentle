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
package org.jgentleframework.core.reflection;

import org.aopalliance.reflect.Class;
import org.aopalliance.reflect.Member;

/**
 * This class is an abstract class which represents a {@link Member}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 16, 2008
 * @see Member
 */
public abstract class AbstractMember extends AbstractProgramUnit implements
		Member {
	/**
	 * Constructor.
	 * 
	 * @param declaringClass
	 *            the class that declares this member.
	 * @param name
	 *            name of current member.
	 * @param modifiers
	 *            modifiers of current member
	 */
	public AbstractMember(Class declaringClass, String name, int modifiers) {

		this.declaringClass = declaringClass;
		this.name = name;
		this.modifiers = modifiers;
	}

	/** The declaring class. */
	Class	declaringClass	= null;

	/** The name. */
	String	name;

	/** The modifiers. */
	int		modifiers;

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.reflect.Member#getDeclaringClass()
	 */
	@Override
	public Class getDeclaringClass() {

		return this.declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.reflect.Member#getName()
	 */
	@Override
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.reflect.Member#getModifiers()
	 */
	@Override
	public int getModifiers() {

		return this.modifiers;
	}
}
