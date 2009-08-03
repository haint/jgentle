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
package org.jgentleframework.services.objectpooling.context;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.objectmeta.Binder;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.InvalidRemovingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.intercept.JGentleNamingPolicy;
import org.jgentleframework.core.interceptor.ReturnScopeName;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.services.objectpooling.PoolOperationException;
import org.jgentleframework.services.objectpooling.Pool;
import org.jgentleframework.services.objectpooling.annotation.Pooling;
import org.jgentleframework.services.objectpooling.support.PoolInvocationMethodInterceptor;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This enum contains some constants representing supported pooling scopes of
 * <code>Object Pooling</code> service.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 7, 2009
 * @see ScopeImplementation
 */
public enum PoolScope implements ScopeImplementation {
	/**
	 * The <code>[Common Pool]</code> provides robust pooling functionality for
	 * arbitrary object beans.
	 */
	CommonPool,
	/**
	 * The <code>[Stack Pool]</code> is an ObjectPool that uses
	 * {@link java.util.Stack stack} to maintain the pool so the pool works in a
	 * Last In First Out (LIFO) fashion, let you create a pool that puts no
	 * limit on the number of objects that are created. Initial objects lay idle
	 * in the pool, and more are created if heavy demand ensues.
	 */
	StackPool,
	/**
	 * The <code>[Weak Pool]</code> is a {@link java.lang.ref.SoftReference}
	 * based implementation of ObjectPool. Objects in this pool are maintained
	 * using an {@link Queue queue}, but these objects are marked as softly
	 * reachable for the <i>garbage collector</i>. When these objects are lying
	 * idle in the pool, they can be reclaimed by the <i>garbage collector</i>
	 * if it determines that there is a need to reclaim memory for other
	 * memory-intensive operations.
	 */
	WeakPool;
	/** The log. */
	private final Log			log				= LogFactory.getLog(getClass());

	/** The pool list */
	ConcurrentMap<String, Pool>	poolServices	= new ConcurrentHashMap<String, Pool>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#getBean
	 * (org.jgentleframework.context.support.Selector, java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object getBean(Selector selector, String scopeName,
			ObjectBeanFactory objFactory) {

		Object result = null;
		Provider provider = objFactory.getProvider();
		Class<?> targetClass = selector.getTargetClass();
		Definition definition = selector.getDefinition();
		Pooling poolingConfig = definition.getAnnotation(Pooling.class);
		if (poolingConfig == null) {
			poolingConfig = provider.getBean(Pooling.class);
		}
		PoolScope scope = null;
		Map<String, ScopeInstance> scopeList = objFactory.getScopeList();
		synchronized (scopeList) {
			scope = (PoolScope) scopeList.get(scopeName);
		}
		synchronized (scopeName) {
			Pool pool = this.poolServices.get(scopeName);
			if (pool != null) {
				synchronized (pool) {
					result = this.obtainInstance(pool, poolingConfig,
							targetClass);
				}
			}
			else {
				Class<? extends Pool> clazz = null;
				switch (scope) {
				case CommonPool:
					clazz = org.jgentleframework.services.objectpooling.CommonPool.class;
					break;
				case StackPool:
					clazz = org.jgentleframework.services.objectpooling.StackPool.class;
					break;
				case WeakPool:
					clazz = org.jgentleframework.services.objectpooling.WeakPool.class;
					break;
				default:
					if (log.isErrorEnabled()) {
						log.error("The specified scope is invalid !",
								new IllegalStateException());
					}
				}
				if (clazz != null) {
					Binder binder = new Binder(provider);
					binder.bind("config", "definition", "selector").to(
							poolingConfig, definition, selector).in(clazz).id(
							clazz.toString()).scope(Scope.SINGLETON);
					binder.flush();
					pool = (Pool) provider.getBeanBoundToDefinition(clazz
							.toString());
					this.poolServices.put(scopeName, pool);
					result = this.obtainInstance(pool, poolingConfig,
							targetClass);
				}
				else {
					if (log.isErrorEnabled()) {
						log.error("The specified scope is invalid !",
								new IllegalStateException());
					}
				}
			}
		}
		return result;
	}

	/**
	 * Return instance to the pool.
	 * 
	 * @param instance
	 *            the instance
	 * @return true, if successful
	 */
	public boolean returnInstance(Object instance) {

		boolean result = true;
		Assertor.notNull(instance,
				"The instance need to be returned must not be null !");
		if (ReflectUtils.isCast(ReturnScopeName.class, instance)) {
			String scopeName = ((ReturnScopeName) instance).returnsScopeName();
			Pool pool = this.poolServices.get(scopeName);
			if (pool == null) {
				if (log.isInfoEnabled()) {
					log
							.info(
									"The instance bean was not created by this pool !!",
									new PoolOperationException());
				}
				return false;
			}
			else {
				try {
					pool.returnObject(instance);
				}
				catch (Throwable e) {
					if (log.isErrorEnabled()) {
						log.error(
								"Could not return bean instance to the pool !",
								e);
					}
					return false;
				}
			}
		}
		else {
			if (log.isInfoEnabled()) {
				log
						.info(" In case of Just In Time Pool, this method can not be executed !! "
								+ "Or the instance bean was not created by this pool !!");
			}
			return false;
		}
		return result;
	}

	/**
	 * Returns the pool service according to given object instance.
	 * 
	 * @param instance
	 *            the given instance
	 */
	public Pool getPool(Object instance) {

		Pool result = null;
		Assertor.notNull(instance,
				"The instance need to be returned must not be null !");
		if (ReflectUtils.isCast(ReturnScopeName.class, instance)) {
			String scopeName = ((ReturnScopeName) instance).returnsScopeName();
			result = this.poolServices.get(scopeName);
		}
		else {
			if (log.isErrorEnabled()) {
				log.error("The instance bean was not created by this pool !!");
			}
		}
		if (result == null) {
			if (log.isErrorEnabled()) {
				log.error("The instance bean was not created by this pool !!",
						new PoolOperationException());
			}
		}
		return result;
	}

	/**
	 * Obtain instance.
	 * 
	 * @param pool
	 *            the pool
	 * @param poolingConfig
	 *            the pooling config
	 * @param targetClass
	 *            the target class
	 * @return the object
	 */
	private Object obtainInstance(Pool pool, Pooling poolingConfig,
			Class<?> targetClass) {

		Object result = null;
		if (poolingConfig.enable() == true) {
			if (poolingConfig.JustInTime() == true) {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(targetClass);
				enhancer.setUseFactory(false);
				enhancer.setUseCache(true);
				enhancer.setNamingPolicy(new JGentleNamingPolicy());
				Callback callback = new PoolInvocationMethodInterceptor(pool);
				enhancer.setCallback(callback);
				result = enhancer.create();
			}
			else {
				try {
					result = pool.obtainObject();
				}
				catch (NoSuchElementException e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage(), e);
					}
				}
				catch (Throwable e) {
					if (log.isErrorEnabled()) {
						log.error(e.getMessage(), e);
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#putBean
	 * (java.lang.String, java.lang.Object,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object putBean(String scopeName, Object bean,
			ObjectBeanFactory objFactory)
			throws InvalidAddingOperationException {

		if (log.isFatalEnabled()) {
			log.fatal("This operation is not supported !! ",
					new UnsupportedOperationException());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#remove
	 * (java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object remove(String scopeName, ObjectBeanFactory objFactory)
			throws InvalidRemovingOperationException {

		if (log.isFatalEnabled()) {
			log.fatal("This operation is not supported !! ",
					new UnsupportedOperationException());
		}
		return null;
	}
}
