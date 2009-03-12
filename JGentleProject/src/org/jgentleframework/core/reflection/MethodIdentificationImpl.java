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

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jgentleframework.utils.ReflectUtils;

/**
 * This class implements the {@link MethodIdentification} interface, designates
 * a method or a set of methods corresponding to declaring class and one
 * <code>regular expression</code> of name.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 21, 2008
 */
class MethodIdentificationImpl implements MethodIdentification {
	/** The name. */
	String							name				= null;
	/** The declaring class. */
	Class<?>						declaringClass		= null;
	/** The found on superclass. */
	boolean							foundOnSuperclass	= false;
	/** The modifiers. */
	int								modifiers			= Identification.NO_MODIFIERS;
	/** The args type. */
	Class<?>[]						argsType			= null;
	/** The throwable classes. */
	Class<? extends Throwable>[]	throwableClasses	= null;

	/**
	 * The Constructor.
	 */
	public MethodIdentificationImpl() {

	}

	/**
	 * The Constructor.
	 * 
	 * @param name
	 *            the name
	 * @param declaringClass
	 *            the declaring class
	 * @param foundOnSuperclass
	 *            the found on superclass
	 * @param modifiers
	 *            the modifiers
	 * @param argsType
	 *            the args type
	 * @param throwableClasses
	 *            the throwable classes
	 */
	public MethodIdentificationImpl(String name, Class<?> declaringClass,
			boolean foundOnSuperclass, int modifiers, Class<?>[] argsType,
			Class<? extends Throwable>[] throwableClasses) {

		this.name = name;
		this.declaringClass = declaringClass;
		this.foundOnSuperclass = foundOnSuperclass;
		this.modifiers = modifiers;
		this.argsType = argsType;
		this.throwableClasses = throwableClasses;
	}

	/**
	 * The Constructor.
	 * 
	 * @param name
	 *            the name
	 */
	public MethodIdentificationImpl(String name) {

		this.name = name;
	}

	/**
	 * The Constructor.
	 * 
	 * @param name
	 *            the name
	 * @param foundOnSuperclass
	 *            the foundOnSuperclass boolean.
	 */
	public MethodIdentificationImpl(String name, boolean foundOnSuperclass) {

		this.name = name;
		this.foundOnSuperclass = foundOnSuperclass;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType) {

		this.name = name;
		this.argsType = argsType;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param modifiers
	 *            the modifiers
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			int modifiers) {

		this.name = name;
		this.argsType = argsType;
		this.modifiers = modifiers;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param foundOnSuperclass
	 *            the found on superclass
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			boolean foundOnSuperclass) {

		this.name = name;
		this.argsType = argsType;
		this.foundOnSuperclass = foundOnSuperclass;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param foundOnSuperclass
	 *            the found on superclass
	 * @param modifiers
	 *            the modifiers
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			boolean foundOnSuperclass, int modifiers) {

		this.name = name;
		this.argsType = argsType;
		this.foundOnSuperclass = foundOnSuperclass;
		this.modifiers = modifiers;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param throwableClasses
	 *            the throwable classes
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			Class<? extends Throwable>[] throwableClasses) {

		this.name = name;
		this.argsType = argsType;
		this.throwableClasses = throwableClasses;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param throwableClasses
	 *            the throwable classes
	 * @param modifiers
	 *            the modifiers
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			Class<? extends Throwable>[] throwableClasses, int modifiers) {

		this.name = name;
		this.argsType = argsType;
		this.throwableClasses = throwableClasses;
		this.modifiers = modifiers;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param throwableClasses
	 *            the throwable classes
	 * @param foundOnSuperclass
	 *            the found on superclass
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			Class<? extends Throwable>[] throwableClasses,
			boolean foundOnSuperclass) {

		this.name = name;
		this.argsType = argsType;
		this.throwableClasses = throwableClasses;
		this.foundOnSuperclass = foundOnSuperclass;
	}

	/**
	 * Instantiates a new method identification impl.
	 * 
	 * @param name
	 *            the name
	 * @param argsType
	 *            the args type
	 * @param throwableClasses
	 *            the throwable classes
	 * @param declaringClass
	 *            the declaring class
	 */
	public MethodIdentificationImpl(String name, Class<?>[] argsType,
			Class<? extends Throwable>[] throwableClasses,
			Class<?> declaringClass) {

		this.name = name;
		this.argsType = argsType;
		this.throwableClasses = throwableClasses;
		this.declaringClass = declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.Identification#getMember()
	 */
	@Override
	public Method[] getMember() {

		if (this.name == null || this.name.isEmpty()
				|| this.declaringClass == null) {
			throw new ReflectException(
					"The identification data is not enough or invalid !");
		}
		Method[] lst = ReflectUtils.methods(this.name, this.declaringClass,
				foundOnSuperclass);
		ArrayList<Method> result = new ArrayList<Method>();
		for (Method method : lst) {
			if (method.getModifiers() == this.modifiers
					|| method.getModifiers() == Identification.NO_MODIFIERS) {
				// Checking argsType
				if (this.argsType != null && this.argsType.length != 0) {
					if (this.argsType.length != method.getParameterTypes().length)
						continue;
					else {
						int error = 0;
						for (int i = 0; i < this.argsType.length; i++) {
							if (!this.argsType[i].equals(method
									.getParameterTypes()[i]))
								error++;
							break;
						}
						if (error != 0)
							continue;
					}
				}
				// Checking ExceptionType
				if (this.throwableClasses != null
						&& this.throwableClasses.length != 0) {
					if (this.throwableClasses.length != method
							.getExceptionTypes().length)
						continue;
					else {
						int error = 0;
						for (Class<? extends Throwable> exception : this.throwableClasses) {
							if (!ReflectUtils.isDeclaredException(method,
									exception)) {
								error++;
								break;
							}
						}
						if (error != 0)
							continue;
					}
				}
				result.add(method);
			}
		}
		return result.toArray(new Method[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MemberIdentification#setFoundOnSuperclass(boolean)
	 */
	@Override
	public void setFoundOnSuperclass(boolean bool) {

		this.foundOnSuperclass = bool;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MemberIdentification#setModifiers(int)
	 */
	@Override
	public void setModifiers(int modifiers) {

		this.modifiers = modifiers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#getDeclaringClass()
	 */
	public Class<?> getDeclaringClass() {

		return declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MemberIdentification#setDeclaringClass(java.lang.Class)
	 */
	@Override
	public void setDeclaringClass(Class<?> declaringClass) {

		this.declaringClass = declaringClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#getArgsType()
	 */
	public Class<?>[] getArgsType() {

		return argsType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#setArgsType(java.lang.Class)
	 */
	public void setArgsType(Class<?>[] argsType) {

		this.argsType = argsType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#getThrowableClasses()
	 */
	public Class<? extends Throwable>[] getThrowableClasses() {

		return throwableClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#setThrowableClasses(java.lang.Class)
	 */
	public void setThrowableClasses(
			Class<? extends Throwable>[] throwableClasses) {

		this.throwableClasses = throwableClasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#getName()
	 */
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#isFoundOnSuperclass()
	 */
	public boolean isFoundOnSuperclass() {

		return this.foundOnSuperclass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.general.reflection.MethodIdentification#getModifiers()
	 */
	public int getModifiers() {

		return this.modifiers;
	}
}
