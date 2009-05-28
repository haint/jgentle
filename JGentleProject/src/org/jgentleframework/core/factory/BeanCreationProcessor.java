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
package org.jgentleframework.core.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.Interceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.annotation.DefaultConstructor;
import org.jgentleframework.context.beans.BeanPostInstantiation;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.context.support.InstantiationSelector;
import org.jgentleframework.context.support.InstantiationSelectorImpl;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.IllegalPropertyException;
import org.jgentleframework.core.factory.support.CoreProcessor;
import org.jgentleframework.core.factory.support.CoreProcessorImpl;
import org.jgentleframework.core.intercept.BeanInstantiationInterceptor;
import org.jgentleframework.core.intercept.InstantiationInterceptor;
import org.jgentleframework.core.intercept.InstantiationInterceptorStackCallback;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.provider.ServiceClass;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This <code>processor</code> is responsible for custom configured bean
 * instantiation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 23, 2008
 * @see ServiceClass
 */
@BeanServices(alias = BeanCreationProcessor.ALIAS, lazy_init = false)
public class BeanCreationProcessor implements ServiceClass,
		BeanPostInstantiationSupportInterface {
	/** the alias name of current processor. */
	public final static String	ALIAS		= "BEAN_CREATION_PROCESSOR";

	/** The current {@link Provider}. */
	Provider					provider	= null;

	/** The bpt lst. */
	List<Object>				bptLst		= new LinkedList<Object>();

	/** The processor. */
	CoreProcessor				processor	= null;

	/** The log. */
	private final Log			log			= LogFactory.getLog(getClass());

	/**
	 * Constructor.
	 * 
	 * @param provider
	 *            the current {@link Provider}
	 */
	@DefaultConstructor
	public BeanCreationProcessor(Provider provider) {

		this.provider = provider;
		processor = new CoreProcessorImpl(this.provider);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.ServiceClass#handle(org.jgentleframework
	 * .context.support.Selector, java.lang.Object)
	 */
	@Override
	public Object handle(Selector targetSelector, Object requestor) {

		if (!ReflectUtils.isCast(CoreInstantiationSelector.class,
				targetSelector)) {
			if (log.isFatalEnabled()) {
				log.fatal("Target selector can not be casted to '"
						+ CoreInstantiationSelector.class.toString() + "'");
			}
			throw new IllegalPropertyException(
					"Target selector can not be casted to '"
							+ CoreInstantiationSelector.class.toString() + "'");
		}
		// executes before BeanPostInstantiation
		for (Object bpt : bptLst) {
			BeanPostInstantiation beanPost = this.getBeanPostInstantiation(bpt);
			beanPost.BeforeInstantiation(targetSelector.getDefinition(),
					(CoreInstantiationSelector) targetSelector);
		}
		// creates bean
		Object result = null;
		try {
			if (targetSelector instanceof CoreInstantiationSelectorImpl
					&& !(targetSelector instanceof InstantiationSelectorImpl)) {
				result = this.processor.handle(targetSelector, result);
			}
			else if (targetSelector instanceof InstantiationSelectorImpl) {
				InstantiationSelector selector = (InstantiationSelector) targetSelector;
				/*
				 * executes instantiation
				 */
				boolean coreSupport = validatesInterceptor(selector
						.getInterceptors());
				InstantiationInterceptorStackCallback iiscb = selector
						.getInstantiationInterceptors() != null
						&& selector.getInstantiationInterceptors().length != 0 ? new InstantiationInterceptorStackCallback(
						this, this.provider, selector)
						: null;
				try {
					result = iiscb != null ? iiscb.instantiate() : result;
				}
				catch (Throwable e) {
					if (log.isErrorEnabled()) {
						log.error("Could not instantiate object bean basing "
								+ "on current Instantiation Interceptors !", e);
					}
					InterceptionException ex = new InterceptionException();
					ex.initCause(e);
					throw ex;
				}
				if (!coreSupport) {
					return result;
				}
				else {
					result = this.processor.handle(targetSelector, result);
				}
			}
		}
		catch (SecurityException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		catch (IllegalArgumentException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		catch (NoSuchMethodException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		catch (InstantiationException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		catch (IllegalAccessException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		catch (InvocationTargetException e) {
			if (log.isErrorEnabled()) {
				log.error("Could not instantiate object bean !", e);
			}
		}
		// Execute after BeanPostInstantiation
		for (Object bpt : bptLst) {
			BeanPostInstantiation beanPost = this.getBeanPostInstantiation(bpt);
			beanPost.AfterInstantiation(result, targetSelector.getDefinition(),
					(CoreInstantiationSelector) targetSelector);
		}
		return result;
	}

	/**
	 * Creates the {@link BeanPostInstantiation} instance from an identification
	 * of its.
	 * 
	 * @param bp
	 *            the object identifies the {@link BeanPostInstantiation} need
	 *            to be creates.
	 * @return the bean post instantiation
	 */
	protected BeanPostInstantiation getBeanPostInstantiation(Object bp) {

		Assertor.notNull(bp, "The '" + BeanPostInstantiation.class.getName()
				+ "' instance must not be null!");
		BeanPostInstantiation beanPost = null;
		if (ReflectUtils.isCast(String.class, bp)) {
			Object obj = this.provider.getBean((String) bp);
			if (obj != null
					&& ReflectUtils.isCast(BeanPostInstantiation.class, obj))
				beanPost = (BeanPostInstantiation) obj;
			else
				throw new InterceptionException(
						"The registered '"
								+ BeanPostInstantiation.class.getName()
								+ "' instance could not be found or it is not an instance of '"
								+ BeanPostInstantiation.class + "'!");
		}
		else {
			beanPost = (BeanPostInstantiation) bp;
		}
		return beanPost;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #addBeanPostInstantiation(java.lang.Object)
	 */
	public boolean addBeanPostInstantiation(Object instance) {

		if (!this.bptLst.contains(instance)) {
			return this.bptLst.add(instance);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #addBeanPostInstantiation(int, java.lang.Object)
	 */
	public void addBeanPostInstantiation(int index, Object instance) {

		if (!this.bptLst.contains(instance)) {
			this.bptLst.add(index, instance);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #addBeanPostInstantiation(java.util.Collection)
	 */
	public boolean addBeanPostInstantiation(Collection<Object> collection) {

		if (!this.bptLst.containsAll(collection)) {
			return this.bptLst.addAll(collection);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #addBeanPostInstantiation(int, java.util.Collection)
	 */
	public boolean addBeanPostInstantiation(int index,
			Collection<Object> collection) {

		if (!this.bptLst.containsAll(collection)) {
			return this.bptLst.addAll(index, collection);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #removeBeanPostInstantiation(int)
	 */
	public Object removeBeanPostInstantiation(int index) {

		return this.bptLst.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface
	 * #removeBeanPostInstantiation(java.lang.Object)
	 */
	public boolean removeBeanPostInstantiation(Object obj) {

		return this.bptLst.remove(obj);
	}

	/**
	 * Returns the current {@link Provider} of this processor.
	 * 
	 * @return the {@link Provider}
	 */
	public Provider getProvider() {

		return this.provider;
	}

	/**
	 * Validates an interceptor or a set of interceptors.
	 * 
	 * @param interceptors
	 *            an interceptor or a set of interceptors need to be validated.
	 * @return <b>true</b> if a set of {@link Interceptor}s is supported by core,
	 *         <b>false</b> otherwise.
	 */
	protected boolean validatesInterceptor(Interceptor... interceptors) {

		Assertor.notNull((Object[]) interceptors);
		int supportedByCore = 0;
		int numOfInstantiation = 0;
		for (Interceptor icpt : interceptors) {
			if (ReflectUtils.isCast(BeanInstantiationInterceptor.class, icpt)) {
				numOfInstantiation++;
				if (((BeanInstantiationInterceptor) icpt).isSupportedByCore()) {
					supportedByCore++;
				}
			}
			else if (ReflectUtils.isCast(InstantiationInterceptor.class, icpt)) {
				numOfInstantiation++;
				supportedByCore++;
			}
		}
		if (supportedByCore > 1 && supportedByCore != numOfInstantiation) {
			throw new InterceptionException(
					"Invalid bean configuration! There is at least one instantiation interceptor is not supported by core !");
		}
		if (supportedByCore != 0 || numOfInstantiation == 0) {
			return true;
		}
		return false;
	}
}
