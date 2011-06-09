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
 * This interface represents a locator on a base program structural unit.
 * <p>
 * A program unit represents any structural part of the program (i.e. any part
 * excepting code) such as a class, a method, a field...
 * <p>
 * Unit locators are quite different from code locators.
 * 
 * @see ProgramUnit
 * @see ProgramUnit#getLocator()
 * @see CodeLocator
 */
public interface UnitLocator extends Locator {
}
