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
package org.aopalliance.reflect;

/**
 * This element represents classes in the base program.
 */
public interface Class extends ProgramUnit {
	/**
	 * Returns the class locator that corresponds to this class.
	 * <p>
	 * This method returns exactly the same result as
	 * <code>ProgramUnit.getLocator()</code> but with a more precise type (<code>ClassLocator</code>
	 * instead of <code>UnitLocator</code>).
	 * 
	 * @see ProgramUnit#getLocator()
	 */
	ClassLocator getClassLocator();

	/**
	 * Gets the class's full name.
	 */
	String getName();

	/**
	 * Gets the fields of this class (including superclass fields).
	 */
	Field[] getFields();

	/**
	 * Gets the fields declared by this class.
	 */
	Field[] getDeclaredFields();

	/**
	 * Gets the methods of this class (including superclass methods).
	 */
	Method[] getMethods();

	/**
	 * Gets the methods declared by this class.
	 */
	Method[] getDeclaredMethods();

	/**
	 * Gets the superclass of this class.
	 */
	Class getSuperclass();

	/**
	 * Gets all the interfaces implemented by this class.
	 */
	Class[] getInterfaces();
}
