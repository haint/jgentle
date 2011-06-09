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
package org.jgentleframework.context.services;

import java.beans.beancontext.BeanContextServicesSupport;
import java.beans.beancontext.BeanContextSupport;
import java.util.TooManyListenersException;

import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.provider.Domain;
import org.jgentleframework.core.provider.DomainManager;
import org.jgentleframework.core.provider.ObjectBeanService;
import org.jgentleframework.core.provider.ServiceClass;

/**
 * Represents the service handler, the controller of all services in JGentle
 * system. It contains all core services of container such as IoC service, ...
 * moreover if associated provider is the {@link ServiceProvider}, besides
 * core services such as IoC, AOP, Intercepting service, ... it also holds
 * adscititious services such as RMI support, JDBC wrapper, JMS support, ... or
 * customized service of other third party.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 29, 2007
 * @see DomainManager
 * @see AnnotationRegister
 */
public interface ServiceHandler extends DomainManager, AnnotationRegister {
	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the specified {@link Domain} instance
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, Domain domain);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the specified {@link Domain} instance
	 * @param argsType
	 *            the argument types of specified constructor of object class
	 *            corresponding to service to be used in order to new instance.
	 * @param args
	 *            the arguments used for the specified constructor call.
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, Domain domain,
			Class<?>[] argsType, Object[] args);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the name of specified {@link Domain}
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, String domain);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the name of specified {@link Domain}
	 * @param argsType
	 *            the argument types of specified constructor of object class
	 *            corresponding to service to be used in order to new instance.
	 * @param args
	 *            the arguments used for the specified constructor call.
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, String domain,
			Class<?>[] argsType, Object[] args);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param provider
	 *            the specified {@link Provider} used to instantiate service
	 *            instance.
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the specified {@link Domain} instance
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, Domain domain);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param provider
	 *            the specified {@link Provider} used to instantiate service
	 *            instance.
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the specified {@link Domain} instance
	 * @param argsType
	 *            the argument types of specified constructor of object class
	 *            corresponding to service to be used in order to new instance.
	 * @param args
	 *            the arguments used for the specified constructor call.
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, Domain domain,
			Class<?>[] argsType, Object[] args);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param provider
	 *            the specified {@link Provider} used to instantiate service
	 *            instance.
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the name of specified {@link Domain}
	 * @param argsType
	 *            the argument types of specified constructor of object class
	 *            corresponding to service to be used in order to new instance.
	 * @param args
	 *            the arguments used for the specified constructor call.
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, String domain,
			Class<?>[] argsType, Object[] args);

	/**
	 * Add the specified service to given specified {@link Domain}.
	 * 
	 * @param provider
	 *            the specified {@link Provider} used to instantiate service
	 *            instance.
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param domain
	 *            the specified {@link Domain} instance
	 * @return returns the previous corresponding {@link ObjectBeanService} of
	 *         service if it's existed, otherwise returns <b>null</b>
	 */
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, String domain);

	/**
	 * Returns the {@link AnnotationRegister} instance of this
	 * {@link ServiceHandler}
	 */
	public AnnotationRegister getAnnoRegister();

	public BeanContextServicesSupport getContext();

	/**
	 * Returns the asscociated {@link DefinitionManager} instance of this
	 * {@link ServiceHandler}
	 */
	public DefinitionManager getDefinitionManager();

	public BeanContextSupport getDomainContext();

	/**
	 * Returns the current DomainListener instance of this
	 * {@link ServiceHandler}
	 * 
	 * @return the domainListener
	 */
	public DomainListener getDomainListener();

	/**
	 * Returns the current {@link DomainManager} instance of this
	 * {@link ServiceHandler}
	 */
	public DomainManager getDomainManager();

	/**
	 * Returns the result product of the specified service.
	 * 
	 * @param requestor
	 *            the requestor
	 * @param serviceClass
	 *            the object class type ({@link ServiceClass} of service.
	 * @param serviceSelector
	 *            the selector
	 * @throws TooManyListenersException
	 */
	public Object getService(Object requestor, Class<?> serviceClass,
			Object serviceSelector) throws TooManyListenersException;

	/**
	 * Returns the result product of the specified service.
	 * 
	 * @param requestor
	 *            the requestor
	 * @param alias
	 *            the alias name of the specified Service.
	 * @param serviceSelector
	 *            the selector
	 * @throws TooManyListenersException
	 */
	public Object getService(Object requestor, String alias,
			Object serviceSelector) throws TooManyListenersException;

	/**
	 * @return the serviceListener
	 */
	public ServiceBindingListener getServiceListener();

	/**
	 * Creates a new {@link Domain} bound to the given name.
	 * 
	 * @param domain
	 *            the specified name of {@link Domain}
	 */
	public void newDomain(String domain) throws JGentleException;

	/**
	 * Registers a set of annotations which is specified in {@link Enum}
	 * 
	 * @param enumAnnoClass
	 *            the object class of enum containing a set of annotations need
	 *            to be registered.
	 */
	public void registerAnnotations(Class<? extends Enum<?>> enumAnnoClass);

	/**
	 * Removes the specified {@link Domain} bound to the given name.
	 * 
	 * @param domain
	 *            the specified name of {@link Domain} need to be removed.
	 * @return the {@link Domain} was removed.
	 */
	public Domain removeDomain(String domain);
}
