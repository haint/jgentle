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
package org.jgentleframework.context.services;

import java.beans.beancontext.BeanContextChild;
import java.beans.beancontext.BeanContextServiceRevokedListener;
import java.beans.beancontext.BeanContextServicesSupport;
import java.beans.beancontext.BeanContextSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TooManyListenersException;

import org.jgentleframework.configure.annotation.AnnotationClass;
import org.jgentleframework.context.enums.RegisterSystemAnnotation;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.IllegalPropertyException;
import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.core.provider.Domain;
import org.jgentleframework.core.provider.DomainManager;
import org.jgentleframework.core.provider.DomainManagerImpl;
import org.jgentleframework.core.provider.GetAnnotationClass;
import org.jgentleframework.core.provider.GetAnnotationValidator;
import org.jgentleframework.core.provider.ObjectBeanService;
import org.jgentleframework.core.provider.ServiceClass;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The implementation of {@link ServiceHandler} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 9, 2007
 * @see ServiceHandler
 */
public class ServiceHandlerImpl implements ServiceHandler {
	/** The {@link AnnotationRegister}. */
	private AnnotationRegister			annoRegister;

	/** The context. */
	private BeanContextServicesSupport	context				= new BeanContextServicesSupport();

	/** The definition manager. */
	private DefinitionManager			definitionManager	= null;

	/** The domain context. */
	private BeanContextSupport			domainContext		= new BeanContextSupport();

	/** The domain listener. */
	private DomainListener				domainListener		= new DomainListener();

	/** The domain manager. */
	private DomainManager				domainManager;

	/** The service listener. */
	private ServiceBindingListener		serviceListener		= new ServiceBindingListener();

	/**
	 * Constructor.
	 * 
	 * @param definitionManager
	 *            đối tượng Definition Manager
	 */
	public ServiceHandlerImpl(DefinitionManager definitionManager) {

		this.definitionManager = definitionManager;
		this.domainManager = new DomainManagerImpl(this.definitionManager);
		this.annoRegister = definitionManager.getAnnotationRegister();
		// add context chính nó và khởi tạo bộ listener cho các service trong
		// context.
		this.context.add(this.context);
		this.context.addBeanContextServicesListener(this.serviceListener);
		// Khởi tạo bộ quản lý domain và listener của nó
		this.context.add(this.domainContext);
		this.domainContext
				.addBeanContextMembershipListener(this.domainListener);
		// Đăng kí các annotation liên quan và validator tương ứng
		this.registerAnnotations(RegisterSystemAnnotation.class);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(java.lang
	 * .Class,org.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider)
	 */
	@Override
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, Domain domain) {

		return addService(serviceClass, domain, null, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(java.lang
	 * .Class,org.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProvider, java.lang.Class<?>[], java.lang.Object[])
	 */
	@Override
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, Domain domain,
			Class<?>[] argsType, Object[] args) {

		return addService(null, serviceClass, domain, argsType, args);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(java.lang
	 * .Class, java.lang.String)
	 */
	@Override
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, String domain) {

		return addService(serviceClass, domain, null, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(java.lang
	 * .Class, java.lang.String, java.lang.Class<?>[], java.lang.Object[])
	 */
	@Override
	public ObjectBeanService addService(
			Class<? extends ServiceClass> serviceClass, String domain,
			Class<?>[] argsType, Object[] args) {

		if (containsDomain(domain)) {
			return addService(serviceClass, this.domainManager
					.getDomain(domain), argsType, args);
		}
		else {
			throw new RuntimeException("Domain:" + domain
					+ " must be registered !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(org.exxlabs
	 * .jgentle.context.injecting.Provider, java.lang.Class,
	 * org.jgentleframework.
	 * core.metadatahandling.aohhandling.provider.ServiceProvider)
	 */
	@Override
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, Domain domain) {

		return addService(provider, serviceClass, domain, null, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(org.exxlabs
	 * .jgentle.context.injecting.Provider, java.lang.Class,
	 * org.jgentleframework.
	 * core.metadatahandling.aohhandling.provider.ServiceProvider,
	 * java.lang.Class<?>[], java.lang.Object[])
	 */
	@Override
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, Domain domain,
			Class<?>[] argsType, Object[] args) {

		if (containsDomain(domain)) {
			this.context.addService(serviceClass, domain);
			return domain.registerService(provider, serviceClass, this.context,
					argsType, args);
		}
		else {
			throw new RuntimeException("Domain:" + domain.getDomainName()
					+ " must be registered !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(org.exxlabs
	 * .jgentle.context.injecting.Provider, java.lang.Class, java.lang.String)
	 */
	@Override
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, String domain) {

		if (containsDomain(domain)) {
			return addService(provider, serviceClass, this.domainManager
					.getDomain(domain), null, null);
		}
		else {
			throw new RuntimeException("Domain:" + domain
					+ " must be registered !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#addService(org.exxlabs
	 * .jgentle.context.injecting.Provider, java.lang.Class, java.lang.String,
	 * java.lang.Class<?>[], java.lang.Object[])
	 */
	@Override
	public ObjectBeanService addService(Provider provider,
			Class<? extends ServiceClass> serviceClass, String domain,
			Class<?>[] argsType, Object[] args) {

		if (containsDomain(domain)) {
			return addService(provider, serviceClass, this.domainManager
					.getDomain(domain), argsType, args);
		}
		else {
			throw new RuntimeException("Domain:" + domain
					+ " must be registered !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#addValidator(java.lang.Class,
	 * org.jgentleframework.core.
	 * metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public <T extends Annotation> void addValidator(Class<T> anno,
			AnnotationValidator<T> validator) {

		this.annoRegister.addValidator(anno, validator);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#clearAllAnnotationRegistered()
	 */
	@Override
	public void clearAllAnnotationRegistered() {

		this.annoRegister.clearAllAnnotationRegistered();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager
	 * #containsDomain(org.jgentleframework.core.metadatahandling
	 * .aohhandling.provider.ServiceProvider)
	 */
	@Override
	public boolean containsDomain(Domain domain) {

		return this.domainManager.containsDomain(domain);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#containsDomain(java.lang.String)
	 */
	@Override
	public boolean containsDomain(String domain) {

		return this.domainManager.containsDomain(domain);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#countAnnotationRegistered()
	 */
	@Override
	public int countAnnotationRegistered() {

		return this.annoRegister.countAnnotationRegistered();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#countValidator()
	 */
	@Override
	public int countValidator() {

		return this.annoRegister.countValidator();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getAnnoRegister()
	 */
	@Override
	public AnnotationRegister getAnnoRegister() {

		return annoRegister;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#getAnnotationRegistered()
	 */
	@Override
	public ArrayList<Class<? extends Annotation>> getAnnotationRegistered() {

		return this.annoRegister.getAnnotationRegistered();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.services.ServiceHandler#getContext()
	 */
	@Override
	public BeanContextServicesSupport getContext() {

		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getDefinitionManager
	 * ()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return definitionManager;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#getDomain(java.lang.String)
	 */
	@Override
	public Domain getDomain(String domain) {

		return this.domainManager.getDomain(domain);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getDomainContext()
	 */
	@Override
	public BeanContextSupport getDomainContext() {

		return domainContext;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getDomainListener()
	 */
	@Override
	public DomainListener getDomainListener() {

		return domainListener;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#getDomainValues()
	 */
	@Override
	public Collection<Domain> getDomainValues() {

		return this.domainManager.getDomainValues();
	}

	/**
	 * Gets the service.
	 * 
	 * @param child
	 *            the child
	 * @param requestor
	 *            the requestor
	 * @param serviceClass
	 *            the service class
	 * @param serviceSelector
	 *            the service selector
	 * @param bcsrl
	 *            the bcsrl
	 * @return the service
	 * @throws TooManyListenersException
	 *             the too many listeners exception
	 */
	private Object getService(BeanContextChild child, Object requestor,
			Class<?> serviceClass, Object serviceSelector,
			BeanContextServiceRevokedListener bcsrl)
			throws TooManyListenersException {

		if (child == null)
			child = this.context;
		if (bcsrl == null)
			bcsrl = this.serviceListener;
		return this.context.getService(child, requestor, serviceClass,
				serviceSelector, bcsrl);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getService(java.lang
	 * .Object, java.lang.Class, java.lang.Object)
	 */
	@Override
	public Object getService(Object requestor, Class<?> serviceClass,
			Object serviceSelector) throws TooManyListenersException {

		return getService(null, requestor, serviceClass, serviceSelector, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getService(java.lang
	 * .Object, java.lang.String, java.lang.Object)
	 */
	@Override
	public Object getService(Object requestor, String alias,
			Object serviceSelector) throws TooManyListenersException {

		Class<?> serviceClass = this.domainManager.getServiceClass(alias);
		return getService(requestor, serviceClass, serviceSelector);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#getServiceClass(java.lang.String)
	 */
	@Override
	public Class<? extends ServiceClass> getServiceClass(String alias) {

		return this.domainManager.getServiceClass(alias);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getServiceListener()
	 */
	@Override
	public ServiceBindingListener getServiceListener() {

		return serviceListener;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#getValidatorlist()
	 */
	@Override
	public HashMap<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>> getValidatorlist() {

		return this.annoRegister.getValidatorlist();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#isAnnotationListEmpty()
	 */
	@Override
	public boolean isAnnotationListEmpty() {

		return this.annoRegister.isAnnotationListEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#isRegisteredAnnotation(java.lang.Class)
	 */
	@Override
	public boolean isRegisteredAnnotation(Class<? extends Annotation> anno) {

		return this.annoRegister.isRegisteredAnnotation(anno);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#iteratorRegisteredAnno()
	 */
	@Override
	public Iterator<Class<? extends Annotation>> iteratorRegisteredAnno() {

		return this.annoRegister.iteratorRegisteredAnno();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#newDomain(java.lang
	 * .String)
	 */
	@Override
	public void newDomain(String domain) throws JGentleException {

		newDomain(domain, this.domainContext);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#newDomain(java.lang.String,
	 * java.beans.beancontext.BeanContextSupport)
	 */
	@Override
	public void newDomain(String domain, BeanContextSupport domainContext)
			throws JGentleException {

		this.domainManager.newDomain(domain, domainContext);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#registerAnnotation(java.lang.Class)
	 */
	@Override
	public void registerAnnotation(Class<? extends Annotation> anno) {

		this.annoRegister.registerAnnotation(anno);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#registerAnnotation(java.lang.Class,
	 * org.jgentleframework
	 * .core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public <T extends Annotation> void registerAnnotation(Class<T> anno,
			AnnotationValidator<T> validator) {

		this.annoRegister.registerAnnotation(anno, validator);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#registerAnnotations
	 * (java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void registerAnnotations(Class<? extends Enum<?>> enumAnnoClass) {

		Method methodAnno = null;
		Method methodValidate = null;
		int a = 0;
		int v = 0;
		for (Method methodObj : enumAnnoClass.getMethods()) {
			if (methodObj.isAnnotationPresent(AnnotationClass.class)) {
				methodAnno = methodObj;
				a++;
			}
			if (methodObj
					.isAnnotationPresent(org.jgentleframework.configure.annotation.AnnotationValidators.class)) {
				methodValidate = methodObj;
				v++;
			}
		}
		// validate dữ liệu method
		if (a > 1 || v > 1) {
			throw new RuntimeException(
					"Has more than one @AnnotationClass methods or @AnnotationValidators methods.");
		}
		else {
			if (methodAnno != null) {
				if (!methodAnno.getReturnType().equals(Class.class)) {
					throw new IllegalPropertyException(
							"The return-type of method annotated with @AnnotationClass must be Class type.");
				}
				if (methodAnno.getParameterTypes().length > 0) {
					throw new IllegalPropertyException(
							"The method annotated with @AnnotationClass is invalid.");
				}
			}
			if (methodValidate != null) {
				if (!methodValidate.getReturnType().equals(
						AnnotationValidator.class)) {
					throw new IllegalPropertyException(
							"The return-type of method annotated with @AnnotationValidators must be AnnotationValidator.");
				}
				if (methodValidate.getParameterTypes().length > 0) {
					throw new IllegalPropertyException(
							"The method annotated with @AnnotationValidators is invalid.");
				}
			}
		}
		for (Enum obj : enumAnnoClass.getEnumConstants()) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(obj.name().replace("_", "."));
				if (!clazz.isAnnotation()) {
					throw new IllegalPropertyException(
							"Object class of annotation is invalid.");
				}
			}
			catch (ClassNotFoundException e) {
				if (methodAnno == null) {
					if (ReflectUtils.getAllInterfaces(enumAnnoClass, true)
							.contains(GetAnnotationClass.class)) {
						clazz = ((GetAnnotationClass) obj).getAnnotationClass();
					}
					else {
						throw new IllegalPropertyException(
								"Does not found object class of annotation or enum is invalid.");
					}
				}
				else {
					try {
						methodAnno.setAccessible(true);
						clazz = (Class<?>) methodAnno.invoke(obj);
					}
					catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					}
					catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
					catch (InvocationTargetException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (clazz == null) {
				throw new IllegalPropertyException(
						"Object class of annotation is invalid.");
			}
			if (methodValidate == null
					&& !ReflectUtils.getAllInterfaces(enumAnnoClass, true)
							.contains(GetAnnotationValidator.class)) {
				this.annoRegister
						.registerAnnotation((Class<? extends Annotation>) clazz);
			}
			else {
				if (methodValidate != null) {
					try {
						methodValidate.setAccessible(true);
						Object validator = methodValidate.invoke(obj);
						if (validator != null)
							this.annoRegister
									.registerAnnotation(
											(Class<Annotation>) clazz,
											(AnnotationValidator<Annotation>) validator);
						else {
							this.annoRegister
									.registerAnnotation((Class<? extends Annotation>) clazz);
						}
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
					catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				else {
					Object validator = ((GetAnnotationValidator) obj)
							.getAnnotationValidator();
					if (validator != null)
						this.annoRegister.registerAnnotation(
								(Class<Annotation>) clazz,
								(AnnotationValidator<Annotation>) validator);
					else {
						this.annoRegister
								.registerAnnotation((Class<? extends Annotation>) clazz);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#removeDomain(java
	 * .lang.String)
	 */
	@Override
	public Domain removeDomain(String domain) {

		return removeDomain(domain, this.domainContext);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ServiceProviderManager#removeDomain(java.lang.String,
	 * java.beans.beancontext.BeanContextSupport)
	 */
	@Override
	public Domain removeDomain(String domain, BeanContextSupport domainContext) {

		return this.domainManager.removeDomain(domain, domainContext);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister
	 * #removeValidator(org.jgentleframework.core.metadatahandling
	 * .aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public <T extends Annotation> void removeValidator(
			AnnotationValidator<T> validator) {

		this.annoRegister.removeValidator(validator);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#removeValidator(java.lang.Class)
	 */
	@Override
	public <T extends Annotation> void removeValidator(Class<T> anno) {

		this.annoRegister.removeValidator(anno);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#subListAnnoRegistered(int, int)
	 */
	@Override
	public List<Class<? extends Annotation>> subListAnnoRegistered(
			int fromIndex, int toIndex) {

		return this.annoRegister.subListAnnoRegistered(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * AnnotationRegister#unregisterAnnotation(java.lang.Class)
	 */
	@Override
	public void unregisterAnnotation(Class<? extends Annotation> anno) {

		this.annoRegister.unregisterAnnotation(anno);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.services.ServiceHandler#getDomainManager()
	 */
	@Override
	public DomainManager getDomainManager() {

		return this.domainManager;
	}
}
