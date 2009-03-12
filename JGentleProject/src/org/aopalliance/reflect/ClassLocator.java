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
 * This interface represents a specific unit locator for base program classes.
 * <p>
 * For instance, this locator can represent a given class, or a given classes
 * set (e.g. all the classes of a given package or all the classes that
 * implement a given interface).
 * 
 * @see ProgramUnit#getLocator()
 * @see Class#getClassLocator()
 */
public interface ClassLocator extends UnitLocator {
}
