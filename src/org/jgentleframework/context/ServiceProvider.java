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
package org.jgentleframework.context;

import java.util.Collection;
import java.util.Map;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.injecting.Provider;

/**
 * Represents a {@link ServiceProvider}, an extension of core {@link Provider}.
 * This container supports all features which are supported by {@link Provider}
 * besides some features such as AOP, Interceptors, ... and all services,
 * framework integrations such as (JMX, JMS, Hibernate, ...).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 11, 2007
 * @see Provider
 * @see IAbstractServiceManagement
 */
public interface ServiceProvider extends Provider, IAbstractServiceManagement {
	/**
	 * Returns an registered {@link ComponentServiceContextType} instance.
	 * 
	 * @param clazzType
	 *            the registered class type of
	 *            {@link ComponentServiceContextType} need to be queried.
	 * @return return an {@link ComponentServiceContextType} instance if it
	 *         existed, if not, throws the run-time exception.
	 */
	public <T> T getCSContext(Class<T> clazzType);

	/**
	 * Adds a {@link ComponentServiceContextType}
	 * 
	 * @param clazzType
	 *            the class tpye of {@link ComponentServiceContextType} need to
	 *            be added.
	 * @param csc
	 *            the {@link ComponentServiceContextType} instance need to be
	 *            add.
	 */
	public <T extends Configurable> void addCSContext(Class<?> clazzType,
			ComponentServiceContextType<T> csc);

	/**
	 * Removes the specified {@link ComponentServiceContextType} bound to the
	 * given class type.
	 * 
	 * @param clazzType
	 *            the given class type
	 * @return returns the removed {@link ComponentServiceContextType} instance.
	 */
	public <T> T removeComponentServiceContext(Class<T> clazzType);

	/**
	 * Returns the number of registered {@link ComponentServiceContextType}
	 * instance,
	 * 
	 * @return int
	 */
	public int countCSC();

	/**
	 * Returns the {@link Collection} of all current
	 * {@link ComponentServiceContextType} instances of this
	 * {@link ServiceProvider}
	 */
	public Collection<ComponentServiceContextType<Configurable>> cscValues();

	/**
	 * Returns the Map of all current {@link ComponentServiceContextType}
	 * instances of this {@link ServiceProvider}
	 */
	public Map<Class<?>, ComponentServiceContextType<Configurable>> getCSCList();
}
