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
package org.jgentleframework.core.provider;

import java.beans.beancontext.BeanContextServiceProvider;
import java.beans.beancontext.BeanContextServices;
import java.util.HashMap;
import java.util.Iterator;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;

/**
 * {@link Domain} is a {@link BeanContextServiceProvider}. It contains all
 * {@link ServiceClass} instance in JGentle system. It provides some necessary
 * features in order to manage {@link ServiceClass} includes instantiate
 * {@link ServiceClass} instane, register or manage status of {@link Domain}.
 * Moreover, it also offers some programmatic introspection tools in order to
 * support tool development.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 10, 2007
 * @see ObjectBeanService
 */
public interface Domain extends BeanContextServiceProvider {
	/**
	 * Returns <b>true</b> if an {@link ObjectBeanService} is existed in this
	 * {@link Domain}, otherwise returns <b>false</b>.
	 * 
	 * @param obs
	 *            the specified {@link ObjectBeanService} need to be tested.
	 */
	public boolean contains(ObjectBeanService obs);

	/**
	 * Returns <b>true</b> if the specified {@link Domain} bound to given alias
	 * name is exitsted, otherwise, returns <b>false</b>.
	 * 
	 * @param alias
	 *            the alias name of specified {@link Domain} need to be tested.
	 */
	public boolean containsAlias(String alias);

	/**
	 * Returns <b>true</b> if the specified {@link ServiceClass} instance
	 * according to given object class is registered. Otherwise, returns
	 * <b>false</b>.
	 * 
	 * @param serviceClass
	 *            the object class of {@link ServiceClass} need to be tested.
	 */
	public boolean containsServiceClass(
			Class<? extends ServiceClass> serviceClass);

	/**
	 * Returns the {@link HashMap} containing all object classes of registered
	 * {@link ServiceClass}s bound to their alias corresponding to key of the
	 * {@link HashMap}
	 */
	public HashMap<String, Class<? extends ServiceClass>> getAliasRegistered();

	/**
	 * Returns the current {@link DefinitionManager} of this {@link Domain}
	 * 
	 * @see DefinitionManager
	 */
	public DefinitionManager getDefinitionManager();

	/**
	 * Returns the name of this {@link Domain}
	 */
	public String getDomainName();

	/**
	 * Returns the {@link HashMap} containing all {@link ObjectBeanService}s
	 * corresponding to object classes of registered {@link ServiceClass}. Key
	 * of returned {@link HashMap} is object class type of {@link ServiceClass},
	 * and its value is corresponding {@link ObjectBeanService} instance.
	 */
	public HashMap<Class<? extends ServiceClass>, ObjectBeanService> getRegisteredServiceList();

	/**
	 * Returns the result product of the specified service.
	 * 
	 * @param bcs
	 *            the {@link BeanContextServices} contains specified service.
	 * @param requestor
	 *            the requestor
	 * @param alias
	 *            the alias name of service.
	 * @param serviceSelector
	 *            selector
	 */
	public Object getService(BeanContextServices bcs, Object requestor,
			String alias, Object serviceSelector);

	/**
	 * Returns the service instance of the specified {@link ServiceClass}.
	 * 
	 * @param <T>
	 *            type of service bean.
	 * @param serviceClass
	 *            object class type of {@link ServiceClass}
	 */
	public <T extends ServiceClass> T getServiceInstance(Class<T> serviceClass);

	/**
	 * Returns the object class type of specified {@link ServiceClass} bound to
	 * its given alias name.
	 * 
	 * @param alias
	 *            the alias name of {@link ServiceClass}
	 */
	public Class<? extends ServiceClass> getServiceClass(String alias);

	/**
	 * Returns <b>true</b> if this domain is empty, otherwise returns
	 * <b>false</b>.
	 */
	public boolean isEmpty();

	/**
	 * Returns <b>true</b> if the specified {@link ServiceClass} is registered,
	 * otherwise returns <b>false</b>.
	 * 
	 * @param serviceClass
	 *            the object class type of the specified {@link ServiceClass}
	 *            need to tested
	 */
	public boolean isServiceClassRegistered(
			Class<? extends ServiceClass> serviceClass);

	/**
	 * Returns an {@link Iterator} containing all {@link ObjectBeanService} of
	 * this {@link Domain}
	 * 
	 * @return Iterator
	 */
	public Iterator<ObjectBeanService> iteratorService();

	/**
	 * Registers a {@link ServiceClass}
	 * 
	 * @param serviceClass
	 *            object class type of {@link ServiceClass} need to be
	 *            registered.
	 * @param bcs
	 * @return returns the previous {@link ObjectBeanService} according to
	 *         registerd {@link ServiceClass} if it was registered, if not,
	 *         returns <b>null</b>.
	 */
	public ObjectBeanService registerService(
			Class<? extends ServiceClass> serviceClass, BeanContextServices bcs);

	/**
	 * Registers an {@link ServiceClass} to this {@link Domain}
	 * 
	 * @param serviceClass
	 *            the object class type of {@link ServiceClass} need to be
	 *            registerd
	 * @param bcs
	 * @param argsType
	 *            the object class types of arguments of specified constructor
	 *            are used to instantiate instance corresponding to
	 *            {@link ServiceClass}.
	 * @param args
	 *            the arguments of specified constructor of object class of
	 *            {@link ServiceClass} are used to instantiate instance
	 *            corresponding to {@link ServiceClass}.
	 * @return returns the previous {@link ObjectBeanService} according to
	 *         registerd {@link ServiceClass} if it was registered, if not,
	 *         returns <b>null</b>.
	 */
	public ObjectBeanService registerService(
			Class<? extends ServiceClass> serviceClass,
			BeanContextServices bcs, Class<?>[] argsType, Object[] args);

	/**
	 * Registers an {@link ServiceClass} to this {@link Domain}
	 * 
	 * @param provider
	 *            the {@link Provider}
	 * @param serviceClass
	 *            the object class type of {@link ServiceClass} need to be
	 *            registerd
	 * @param bcs
	 * @param argsType
	 *            the object class types of arguments of specified constructor
	 *            are used to instantiate instance corresponding to
	 *            {@link ServiceClass}.
	 * @param args
	 *            the arguments of specified constructor of object class of
	 *            {@link ServiceClass} are used to instantiate instance
	 *            corresponding to {@link ServiceClass}.
	 * @return returns the previous {@link ObjectBeanService} according to
	 *         registerd {@link ServiceClass} if it was registered, if not,
	 *         returns <b>null</b>.
	 */
	public ObjectBeanService registerService(Provider provider,
			Class<? extends ServiceClass> serviceClass,
			BeanContextServices bcs, Class<?>[] argsType, Object[] args);

	/**
	 * Sets the given domain name to current {@link Domain}
	 * 
	 * @param domainName
	 *            given domain name
	 */
	public void setDomainName(String domainName);

	/**
	 * Unregisters the specified registered service of this {@link Domain}
	 * 
	 * @param bcs
	 *            BeanContextServices
	 * @param serviceClass
	 *            the object class type of service.
	 * @return returns the corresponding {@link ObjectBeanService} of service
	 *         was removed.
	 */
	public <T extends ServiceClass> ObjectBeanService unregisterService(
			BeanContextServices bcs, Class<T> serviceClass);

	/**
	 * Unregisters the given registered service of this {@link Domain}
	 * 
	 * @param bcs
	 *            BeanContextServices
	 * @param alias
	 *            the alias name of service need to be unregistered.
	 * @return returns the corresponding {@link ObjectBeanService} of service
	 *         was removed.
	 */
	public ObjectBeanService unregisterService(BeanContextServices bcs,
			String alias);
}