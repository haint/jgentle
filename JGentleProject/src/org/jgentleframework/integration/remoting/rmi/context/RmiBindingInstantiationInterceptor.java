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
package org.jgentleframework.integration.remoting.rmi.context;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.intercept.BeanInstantiationInterceptor;
import org.jgentleframework.core.intercept.ObjectInstantiation;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.integration.remoting.annotation.Remote;
import org.jgentleframework.integration.remoting.enums.RemoteType;
import org.jgentleframework.integration.remoting.rmi.RmiBindingException;
import org.jgentleframework.integration.remoting.rmi.annotation.RmiBinding;
import org.jgentleframework.integration.remoting.rmi.support.RmiBinder;
import org.jgentleframework.integration.remoting.rmi.support.RmiBinderImpl;
import org.jgentleframework.integration.remoting.rmi.support.RmiBinderInterceptor;

/**
 * The Class RmiBindingInstantiationInterceptor.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 23, 2008
 */
public class RmiBindingInstantiationInterceptor implements
		BeanInstantiationInterceptor, ProviderAware {
	/** The log. */
	private final Log			log					= LogFactory
															.getLog(getClass());

	/** The definition manager. */
	private DefinitionManager	definitionManager	= null;

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.intercept.BeanInstantiationInterceptor#
	 * isSupportedByCore()
	 */
	@Override
	public boolean isSupportedByCore() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.intercept.InstantiationInterceptor#instantiate
	 * (org.jgentleframework.core.intercept.ObjectInstantiation)
	 */
	@Override
	public Object instantiate(ObjectInstantiation oi) throws Throwable {

		Object result = null;
		Class<?> target = oi.getTargetClass();
		Definition definition = this.definitionManager.getDefinition(target);
		if (definition.isAnnotationPresent(Remote.class)) {
			if (!target.isInterface()) {
				if (log.isFatalEnabled()) {
					log.fatal("Could not binding to RMI service",
							new RmiBindingException());
				}
			}
			Remote remote = definition.getAnnotation(Remote.class);
			if (remote.type() == RemoteType.RMI
					&& definition.isAnnotationPresent(RmiBinding.class)) {
				RmiBinding rmiBinding = definition
						.getAnnotation(RmiBinding.class);
				result = instantiate(definition, target, rmiBinding, oi);
			}
			else {
				if (log.isWarnEnabled()) {
					log
							.warn("The remote type is not ["
									+ RemoteType.RMI.name()
									+ "] or current target interface is not annotated with ["
									+ RmiBinding.class + " annotation]");
				}
			}
		}
		else {
			if (log.isWarnEnabled()) {
				log.warn("The target interface is not annotated with ["
						+ Remote.class + "]");
			}
		}
		return result;
	}

	/**
	 * Instantiate.
	 * 
	 * @param definition
	 *            the definition
	 * @param target
	 *            the target
	 * @param rmiBinding
	 *            the rmi binding
	 * @param oi
	 *            the {@link ObjectInstantiation}
	 * @return the object
	 * @throws Throwable
	 *             the throwable
	 */
	protected Object instantiate(Definition definition, Class<?> target,
			RmiBinding rmiBinding, ObjectInstantiation oi) throws Throwable {

		Object result = null;
		Object previous = oi.getPreviousResult();
		Enhancer enhancer = new Enhancer();
		enhancer.setInterfaces(new Class<?>[] { target });
		RmiBinder binder = new RmiBinderImpl(rmiBinding.serviceName(),
				rmiBinding.registryHost(), rmiBinding.registryPort(),
				rmiBinding.refreshStubOnConnectFailure(), rmiBinding
						.lookupStubOnStartup(), rmiBinding.cacheStub(), null);
		Callback callback = new RmiBinderInterceptor(binder);
		enhancer.setCallback(callback);
		if (oi.args() != null && oi.argTypes() != null)
			result = enhancer.create(oi.argTypes(), oi.args());
		else
			result = enhancer.create();
		if (previous == null) {
			oi.setPreviousResult(result);
		}
		else {
			if (log.isFatalEnabled()) {
				log.fatal(
						"Does not support multible Instantiation Interceptor "
								+ "conjointly with RMI Binding Instantiation",
						new RmiBindingException());
			}
		}
		return oi.proceed();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.beans.ProviderAware#setProvider(org.
	 * jgentleframework.context.injecting.Provider)
	 */
	@Override
	public void setProvider(Provider provider) {

		this.definitionManager = provider.getDefinitionManager();
	}
}
