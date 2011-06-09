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
package org.jgentleframework.core.intercept;


import net.sf.cglib.reflect.FastClass;

/**
 * Gives JGentle classes custom names.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 1, 2008
 */
public class JGentleFastClass {
	/**
	 * Creates the {@link FastClass}.
	 * 
	 * @param type
	 *            the type
	 * @return the fast class
	 */
	public static FastClass create(Class<?> type) {

		return create(type.getClassLoader(), type);
	}

	/**
	 * Creates the {@link FastClass}.
	 * 
	 * @param loader
	 *            the loader
	 * @param type
	 *            the type
	 * @return the fast class
	 */
	public static FastClass create(ClassLoader loader, Class<?> type) {

		FastClass.Generator generator = new FastClass.Generator();
		generator.setType(type);
		generator.setClassLoader(loader);
		generator.setNamingPolicy(new JGentleNamingPolicy());
		return generator.create();
	}
}
