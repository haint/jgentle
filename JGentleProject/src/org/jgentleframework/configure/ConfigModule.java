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
package org.jgentleframework.configure;

import java.util.ArrayList;
import java.util.Map;

/**
 * The Interface ConfigModule.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 17, 2008
 */
public interface ConfigModule {
	/**
	 * Returns the {@link Map} containing all configured information.
	 */
	Map<String, ArrayList<?>> getOptionsList();

	/**
	 * Returns the target object class of current proxy.
	 */
	Class<? extends Configurable> getTargetClass();

	/**
	 * Returns the config instance appropriate to given type.
	 * 
	 * @param type
	 *            the given type
	 */
	Object getConfigInstance(Class<?> type);
}
