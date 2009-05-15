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
package org.jgentleframework.core.intercept;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.utils.Assertor;

/**
 * Intercepts a method with a stack of interceptors.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 1, 2008
 */
public class MethodInterceptorStackCallback implements
		net.sf.cglib.proxy.MethodInterceptor {
	/** The interceptors. */
	final MethodInterceptor[]	interceptors;
	/** The method. */
	final Method				method;
	/** The created bean. */
	final Object				createdBean;

	/**
	 * Instantiates a new interceptor stack callback.
	 * 
	 * @param method
	 *            the method
	 * @param interceptors
	 *            the interceptors
	 */
	public MethodInterceptorStackCallback(Method method, Object createdBean,
			MethodInterceptor... interceptors) {

		this.method = method;
		this.createdBean = createdBean;
		Assertor.notNull(interceptors,
				"The list of interceptors must not be null!");
		Assertor.notEmpty(interceptors,
				"The list of interceptors must not be empty !");
		this.interceptors = interceptors != null ? interceptors.clone()
				: interceptors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[],
	 *      net.sf.cglib.proxy.MethodProxy)
	 */
	public Object intercept(Object proxy, Method method, Object[] arguments,
			MethodProxy methodProxy) throws Throwable {

		return new InterceptedMethodInvocation(proxy, methodProxy, arguments)
				.proceed();
	}

	/**
	 * The Class InterceptedMethodInvocation.
	 */
	class InterceptedMethodInvocation extends AbstractMethodInvocation
			implements MethodInvocation {
		/** The index. */
		int	index	= -1;

		/**
		 * The Constructor.
		 * 
		 * @param proxy
		 *            the proxy
		 * @param methodProxy
		 *            the method proxy
		 * @param arguments
		 *            the arguments
		 */
		public InterceptedMethodInvocation(Object proxy,
				MethodProxy methodProxy, Object[] arguments) {

			super(proxy, methodProxy, arguments);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.aopalliance.intercept.Joinpoint#proceed()
		 */
		public Object proceed() throws Throwable {

			Object result = null;
			try {
				index++;
				if (createdBean == null) {
					result = index == interceptors.length ? methodProxy
							.invokeSuper(proxy, arguments)
							: interceptors[index].invoke(this);
				}
				else {
					method.setAccessible(true);
					result = index == interceptors.length ? method.invoke(
							createdBean, arguments) : interceptors[index]
							.invoke(this);
					method.setAccessible(false);
				}
			}
			finally {
				index--;
			}
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.aopalliance.intercept.MethodInvocation#getMethod()
		 */
		public Method getMethod() {

			return method;
		}
	}
}
