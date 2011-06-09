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
package org.jgentleframework.core.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The Interface CachedConstructor.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 17, 2008
 */
public interface CachedConstructor {
	/**
	 * Constructs an instance for the given arguments.
	 * 
	 * @param arguments
	 *            the arguments
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	Object newInstance(Object... arguments) throws InvocationTargetException;

	/**
	 * Gets the meta def object.
	 */
	MetaDefObject getMetaDefObject();

	/**
	 * Sets the meta def object.
	 * 
	 * @param mdo
	 */
	void setMetaDefObject(MetaDefObject mdo);
	
	/**
	 * Gets the java constructor.
	 * 
	 * @return the java constructor
	 */
	Constructor<?> getJavaConstructor();

	/**
	 * Returns the hashcodeID of this {@link CachedConstructor}
	 * 
	 * @return int
	 */
	int hashcodeID();
}
