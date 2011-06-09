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

import java.lang.reflect.InvocationTargetException;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.handling.DefinitionManager;

/**
 * This is core processor of JGentle system which is a part of JGentle kernel,
 * is responsible for bean instantiation. All core services of JGentle are
 * deployed in {@link CoreProcessorImpl}, includes IoC, AOP ...
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 2, 2008
 */
public interface CoreProcessor {
	/**
	 * Creates the bean instance
	 * 
	 * @param targetSelector
	 * @param previousResult
	 */
	public Object handle(Selector targetSelector, Object previousResult)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException;

	/**
	 * Returns the current {@link Provider} this processor.
	 */
	public Provider getProvider();

	/**
	 * Returns the current {@link DefinitionManager}.
	 */
	public DefinitionManager getDefinitionManager();
}