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
package org.jgentleframework.services.datalocator.context;

import java.util.List;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.services.datalocator.data.Manager;

/**
 * The Interface RepositoryServiceContext.
 * 
 * @param <T>  *
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 26, 2007
 */
public interface RepositoryServiceContext<T extends Configurable> {
	/**
	 * Returns the current {@link ServiceProvider}.
	 * 
	 * @return the service provider
	 */
	public ServiceProvider getServiceProvider();

	/**
	 * Returns the {@link List list} containing all config instance of current
	 * {@link RepositoryServiceContext}.
	 * 
	 * @return the config instances
	 */
	public List<T> getConfigInstances();

	/**
	 * Returns the {@link Manager}.
	 * 
	 * @param enumConfig
	 *            the enum configuration class.
	 * @return {@link Manager}
	 */
	public Manager getManager(Class<?> enumConfig);
}