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

import java.beans.beancontext.BeanContextServices;
import java.beans.beancontext.BeanContextSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.ServiceProviderException;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Quản lý việc nạp, và sản sinh các đối tượng services trong hệ thống.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 9, 2007
 * @see Domain
 */
class DomainImpl extends BeanContextSupport implements Domain {
	/** The Constant serialVersionUID. */
	private static final long											serialVersionUID		= -5830600236030819223L;

	/** The list of registered services. */
	private HashMap<String, Class<? extends ServiceClass>>				aliasRegistered			= new HashMap<String, Class<? extends ServiceClass>>();

	/** The {@link DefinitionManager}. */
	private transient DefinitionManager									definitionManager		= null;

	/** domain name of this domain. */
	String																domainName				= "";

	/** The registered service list. */
	private HashMap<Class<? extends ServiceClass>, ObjectBeanService>	registeredServiceList	= new HashMap<Class<? extends ServiceClass>, ObjectBeanService>();

	/**
	 * Instantiates a new domain impl.
	 * 
	 * @param domain
	 *            the domain
	 * @param defManager
	 *            the def manager
	 */
	public DomainImpl(String domain, DefinitionManager defManager) {

		this.domainName = domain;
		this.definitionManager = defManager;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#contains(org.jgentleframework
	 * .core.provider.ObjectBeanService)
	 */
	public boolean contains(ObjectBeanService obs) {

		return super.contains(obs);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#containsAlias(java.lang.String)
	 */
	@Override
	public boolean containsAlias(String alias) {

		return this.aliasRegistered.containsKey(alias);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#containsServiceClass(java.lang
	 * .Class)
	 */
	@Override
	public boolean containsServiceClass(
			Class<? extends ServiceClass> serviceClass) {

		return this.registeredServiceList.containsKey(serviceClass);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.provider.Domain#getAliasRegistered()
	 */
	@Override
	public HashMap<String, Class<? extends ServiceClass>> getAliasRegistered() {

		return aliasRegistered;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextServiceProvider#getCurrentServiceSelectors
	 * (java.beans.beancontext.BeanContextServices, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<?> getCurrentServiceSelectors(BeanContextServices bcs,
			Class serviceClass) {

		// always return null.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.provider.Domain#getDefinitionManager()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return definitionManager;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#getDomainName()
	 */
	public String getDomainName() {

		return domainName;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#getRegisteredServiceList()
	 */
	@Override
	public HashMap<Class<? extends ServiceClass>, ObjectBeanService> getRegisteredServiceList() {

		return registeredServiceList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider
	 * #getService(org.jgentleframework.configure.injecting.InjectCreator,
	 * java.beans.beancontext.BeanContextServices, java.lang.Object,
	 * java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getService(BeanContextServices bcs, Object requestor,
			Class serviceClass, Object serviceSelector) {

		Selector selector = null;
		if (!ReflectUtils.isCast(Selector.class, serviceSelector)) {
			System.out.println(serviceSelector.getClass());
			throw new IllegalArgumentException(
					"Service selector is not a selector !! This instance is not an implementation or extension of '"
							+ Selector.class.toString() + "'");
		}
		else
			selector = (Selector) serviceSelector;
		Object result = null;
		ServiceClass obj = getServiceInstance(serviceClass);
		result = obj.handle(selector, requestor);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider
	 * #getService(org.jgentleframework.configure.injecting.InjectCreator,
	 * java.beans.beancontext.BeanContextServices, java.lang.Object,
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getService(BeanContextServices bcs, Object requestor,
			String alias, Object serviceSelector) {

		Class<? extends ServiceClass> serviceClass = null;
		synchronized (this.aliasRegistered) {
			if (this.aliasRegistered.containsKey(alias)) {
				serviceClass = this.aliasRegistered.get(alias);
			}
			else {
				throw new NullPointerException();
			}
			return getService(bcs, requestor, serviceClass, serviceSelector);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#getServiceClass(java.lang.String)
	 */
	@Override
	public Class<? extends ServiceClass> getServiceClass(String alias) {

		if (!containsAlias(alias)) {
			throw new ServiceProviderException("Alias name: " + alias
					+ " is not existed.");
		}
		else {
			return this.aliasRegistered.get(alias);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#getServiceInstance(java.lang
	 * .Class, java.beans.beancontext.BeanContextServices)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ServiceClass> T getServiceInstance(Class<T> serviceClass) {

		ObjectBeanService obs = null;
		if (isServiceClassRegistered(serviceClass)) {
			obs = this.registeredServiceList.get(serviceClass);
		}
		else {
			throw new ServiceProviderException("Service must be registered!");
		}
		boolean singleton = true;
		Definition defSC = obs.getDefinition();
		if (defSC.isAnnotationPresent(BeanServices.class)) {
			BeanServices annoBS = defSC.getAnnotation(BeanServices.class);
			singleton = annoBS.singleton();
		}
		// Khởi tạo service bean
		T obj = null;
		/*
		 * Nếu service cần truy vấn là singleton
		 */
		if (singleton) {
			obj = (T) obs.getSingletonObject();
		}
		if (obj == null) {
			if (obs.getConstructor() != null) {
				try {
					obj = (T) obs.getConstructor().newInstance(obs.getArgs());
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			else
				obj = (T) init_serviceBean(obs.getProvider(), serviceClass);
			if (singleton)
				obs.setSingletonObject(obj);
		}
		return obj;
	}

	/**
	 * Init_service bean.
	 * 
	 * @param provider
	 *            the provider
	 * @param clazz
	 *            the clazz
	 * @return the object
	 */
	private Object init_serviceBean(Provider provider, Class<?> clazz) {

		Object result = null;
		if (provider == null) {
			result = ReflectUtils.createInstance(clazz);
		}
		else {
			result = provider.getBean(clazz);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#isEmpty()
	 */
	public boolean isEmpty() {

		return super.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#isServiceClassRegistered(java.lang.Class)
	 */
	public boolean isServiceClassRegistered(
			Class<? extends ServiceClass> serviceClass) {

		return this.registeredServiceList.containsKey(serviceClass);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider#iterator()
	 */
	@SuppressWarnings("unchecked")
	public Iterator<ObjectBeanService> iteratorService() {

		return this.iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#registerService(java.lang.Class
	 * , java.beans.beancontext.BeanContextServices)
	 */
	@Override
	public ObjectBeanService registerService(
			Class<? extends ServiceClass> serviceClass, BeanContextServices bcs) {

		return registerService(null, serviceClass, bcs, null, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#registerService(java.lang.Class
	 * , java.beans.beancontext.BeanContextServices, java.lang.Class<?>[],
	 * java.lang.Object[])
	 */
	@Override
	public ObjectBeanService registerService(
			Class<? extends ServiceClass> serviceClass,
			BeanContextServices bcs, Class<?>[] argsType, Object[] args) {

		return registerService(null, serviceClass, bcs, argsType, args);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.provider.Domain#registerService(org.
	 * jgentleframework.context.injecting.Provider, java.lang.Class,
	 * java.beans.beancontext.BeanContextServices, java.lang.Class<?>[],
	 * java.lang.Object[])
	 */
	@Override
	public synchronized ObjectBeanService registerService(Provider provider,
			Class<? extends ServiceClass> serviceClass,
			BeanContextServices bcs, Class<?>[] argsType, Object[] args) {

		Assertor.notNull(serviceClass);
		Assertor.notNull(bcs);
		Definition defSC = this.definitionManager.getDefinition(serviceClass);
		ObjectBeanService returnObject = null;
		/*
		 * Nếu definition của service class không cấu hình annotation
		 * BeanServices
		 */
		BeanServices annoBS = null;
		String alias = "";
		boolean singleton = true;
		boolean lazy_init = false;
		if (defSC.isAnnotationPresent(BeanServices.class)) {
			annoBS = defSC.getAnnotation(BeanServices.class);
			alias = annoBS.alias();
			singleton = annoBS.singleton();
			lazy_init = annoBS.lazy_init();
		}
		ObjectBeanService obs = (alias.isEmpty() || alias.equals("NULL")) ? new ObjectBeanServiceImpl(
				defSC, serviceClass)
				: new ObjectBeanServiceImpl(defSC, serviceClass, alias);
		// thiết lập provider cho obs
		if (provider != null) {
			obs.setProvider(provider);
		}
		/*
		 * Đăng kí object class của service class vào service provider hiện hành
		 */
		returnObject = (ObjectBeanService) this.registeredServiceList.put(
				serviceClass, obs);
		// add obs vao BeanContextSupport
		this.add(obs);
		this.addBeanContextMembershipListener(obs); // add listener của obs
		// Đăng kí tên định danh nếu có
		synchronized (aliasRegistered) {
			if (!(alias.isEmpty() || alias.equals("NULL")))
				if (this.aliasRegistered.containsKey(alias)) {
					throw new ServiceProviderException(
							"This alias value is existed !");
				}
			this.aliasRegistered.put(alias, serviceClass);
		}
		if (args != null) {
			try {
				Constructor<?> constructor = serviceClass
						.getConstructor(argsType);
				constructor.setAccessible(true);
				obs.setArgs(args);
				obs.setConstructor(constructor);
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		/*
		 * Kiểm tra các thuộc tính singleton và lazy-init.
		 */
		if (singleton && !lazy_init) {
			ServiceClass obj = null;
			// Nếu không truyền tham số khởi tạo
			if (args == null) {
				obj = (ServiceClass) init_serviceBean(provider, serviceClass);
			}
			// Nếu có truyền tham số khởi tạo.
			else {
				try {
					Constructor<?> constructor = serviceClass
							.getConstructor(argsType);
					constructor.setAccessible(true);
					obj = (ServiceClass) constructor.newInstance(args);
				}
				catch (SecurityException e) {
					e.printStackTrace();
				}
				catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (InstantiationException e) {
					e.printStackTrace();
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			obs.setSingletonObject((ServiceClass) obj);
		}
		return returnObject;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextServiceProvider#releaseService(java
	 * .beans.beancontext.BeanContextServices, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void releaseService(BeanContextServices bcs, Object requestor,
			Object service) {

		bcs.remove(service);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#setDomainName(java.lang.String)
	 */
	public void setDomainName(String domainName) {

		this.domainName = domainName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#unregisterService(java.beans
	 * .beancontext.BeanContextServices, java.lang.Class)
	 */
	public synchronized <T extends ServiceClass> ObjectBeanService unregisterService(
			BeanContextServices bcs, Class<T> serviceClass) {

		Assertor.notNull(bcs);
		Assertor.notNull(serviceClass);
		if (!this.registeredServiceList.containsKey(serviceClass)) {
			throw new ServiceProviderException(
					"service does not be registered !.");
		}
		ObjectBeanService obs = this.registeredServiceList.remove(serviceClass);
		for (Entry<String, Class<? extends ServiceClass>> element : this.aliasRegistered
				.entrySet()) {
			if (element.getValue().equals(serviceClass)) {
				this.aliasRegistered.remove(element.getKey());
			}
		}
		this.removeBeanContextMembershipListener(obs);
		this.remove(obs);
		return obs;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.Domain#unregisterService(java.beans
	 * .beancontext.BeanContextServices, java.lang.String)
	 */
	public synchronized ObjectBeanService unregisterService(
			BeanContextServices bcs, String alias) {

		Assertor.notNull(alias);
		Assertor.notStringEqualNULL(alias);
		return unregisterService(bcs, this.aliasRegistered.get(alias));
	}
}
