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
package org.jgentleframework.utils;

import org.jgentleframework.utils.enums.PropertyKeys;

/**
 * Miscellaneous System Properties ({@link System#getProperty(String)}) utility
 * methods.
 * <p>
 * Mainly for use within the framework, but also useful for application code.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 26, 2009
 */
public abstract class SystemPropertiesUtils {
	/**
	 * Returns the result of key via {@link System#getProperty(String)} method
	 * 
	 * @param propertyKey
	 *            the specified key
	 */
	public static String getProperty(PropertyKeys propertyKey) {

		return System.getProperty(propertyKey.getKey());
	}

	/**
	 * Returns the result of key via {@link System#getProperty(String, String)}
	 * method
	 * 
	 * @param propertyKey
	 *            the specified key
	 * @param defaultValue
	 *            the <code>default value</code> if there is no property with
	 *            that key or {@link System#getProperty(String)} returns
	 *            <code>null</code>.
	 * @return the string value of the system property, or the
	 *         <code>default value</code> if there is no property with that key
	 *         or {@link System#getProperty(String)} method returns
	 *         <code>null</code>.
	 */
	public static String getProperty(PropertyKeys propertyKey,
			String defaultValue) {

		String result = System.getProperty(propertyKey.getKey());
		return result == null ? defaultValue : result;
	}
}
