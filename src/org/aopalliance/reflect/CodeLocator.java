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
 * This interface represents a locator on a base program piece of code.
 * <p>
 * The AOP Alliance implementation provider should provide code locators
 * implementations in order to support several kinds of code locators (e.g. as
 * the ones used in the <code>Code</code> interface).
 * <p>
 * The AOP Alliance client program gets the locator by navigating the base
 * program metamodel (using the {@link org.aopalliance.reflect} package) and
 * using the <code>get...Locator(...)</code> methods.
 * <p>
 * Code locators are quite different from unit locators.
 * 
 * @see Code
 * @see Code#getLocator()
 * @see Code#getCallLocator(Method)
 * @see Code#getReadLocator(Field)
 * @see Code#getWriteLocator(Field)
 * @see Code#getThrowLocator(Class)
 * @see Code#getCatchLocator(Class)
 * @see Method#getCallLocator()
 * @see UnitLocator
 */
public interface CodeLocator extends Locator {
}
