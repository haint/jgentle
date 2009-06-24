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
package org.jgentleframework.services.objectpooling.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.jgentleframework.services.objectpooling.PoolType;

/**
 * The Class PoolInvocationMethodInterceptor.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 22, 2009
 */
public class PoolInvocationMethodInterceptor implements MethodInterceptor {
	/** The pool. */
	PoolType	pool	= null;

	/**
	 * Instantiates a new pool invocation method interceptor.
	 * 
	 * @param pool
	 *            the pool
	 */
	public PoolInvocationMethodInterceptor(PoolType pool) {

		this.pool = pool;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[],
	 * net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		Object result = null;
		// not thread-safe
		Object instance = pool.obtainObject();
		result = proxy.invokeSuper(object, args);
		pool.returnObject(instance);
		return result;
	}
}
