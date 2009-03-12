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
 * This represents a field of a class.
 */
public interface Field extends Member {
	/**
	 * Same as <code>getReadLocator(USER_SIDE)</code>.
	 * 
	 * @see #getReadLocator(int)
	 */
	CodeLocator getReadLocator();

	/**
	 * This methods returns the points where the current field is read.
	 * <p>
	 * There are two different behaviors for this method depending on the side
	 * of the locator. At the user side, the locator designates all the points
	 * in methods bodies where the field is read (similarly to
	 * <code>Code.getReadLocator(Field)</code>). At the provider side, it
	 * really may depend on the implementor choice (e.g. it can return a locator
	 * on the body of the field's getter).
	 * <p>
	 * In Java, the user side is most of the time used so that you can use the
	 * method <code>getReadLocator()</code>.
	 * 
	 * @param side
	 *            USER_SIDE || PROVIDER_SIDE
	 * @see #getReadLocator()
	 */
	CodeLocator getReadLocator(int side);

	/**
	 * Same as <code>getWriteLocator(USER_SIDE)</code>.
	 * 
	 * @see #getWriteLocator(int)
	 */
	CodeLocator getWriteLocator();

	/**
	 * This methods returns the points where the current field is written.
	 * <p>
	 * There are two different behaviors for this method depending on the side
	 * of the locator. At the user side, the locator designates all the points
	 * in methods bodies where the field is written (similarly to
	 * <code>Code.getWriteLocator(Field)</code>). At the provider side, it
	 * really may depend on the implementor choice (e.g. it can return a locator
	 * on the body of the field's setter).
	 * <p>
	 * In Java, the user side is most of the time used so that you can use the
	 * method <code>getWriteLocator()</code>.
	 * 
	 * @param side
	 *            USER_SIDE || PROVIDER_SIDE
	 * @see #getWriteLocator()
	 */
	CodeLocator getWriteLocator(int side);
}
