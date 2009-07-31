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

import java.lang.reflect.Method;

/**
 * This interface represents the method identification, designates a method or a
 * set of methods corresponding to declaring class and one regular expression of
 * name. The identification data shall be used conjointly with reflection tools
 * in order to access specified methods.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 22, 2008
 * @see MemberIdentification
 * @see FieldIdentification
 * @see Identification
 * @see Method
 */
public interface MethodIdentification extends MemberIdentification<Method> {
	/**
	 * Gets the declaring class.
	 */
	public Class<?> getDeclaringClass();

	/**
	 * Gets the args type.
	 */
	public Class<?>[] getArgsType();

	/**
	 * Sets the args type.
	 * 
	 * @param argsType
	 *            the argsType to set
	 */
	public void setArgsType(Class<?>[] argsType);

	/**
	 * Gets the throwable classes.
	 */
	public Class<? extends Throwable>[] getThrowableClasses();

	/**
	 * Sets the throwable classes.
	 * 
	 * @param throwableClasses
	 *            the throwableClasses to set
	 */
	public void setThrowableClasses(
			Class<? extends Throwable>[] throwableClasses);

	/**
	 * Gets the name.
	 */
	public String getName();

	/**
	 * Checks if is found on superclass.
	 */
	public boolean isFoundOnSuperclass();

	/**
	 * Gets the modifiers.
	 */
	public int getModifiers();
}