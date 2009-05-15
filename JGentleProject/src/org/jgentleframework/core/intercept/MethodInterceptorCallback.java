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
 * The Class MethodInterceptorCallback.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 22, 2008
 */
public class MethodInterceptorCallback implements
		net.sf.cglib.proxy.MethodInterceptor {
	/** The interceptor. */
	final MethodInterceptor	interceptor;

	/**
	 * Instantiates a new method interceptor callback.
	 * 
	 * @param interceptor
	 *            the interceptor
	 */
	public MethodInterceptorCallback(MethodInterceptor interceptor) {

		Assertor
				.notNull(interceptor, "The given interceptor must not be null!");
		this.interceptor = interceptor;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[],
	 * net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		return new SingleInterceptedMethodInvocation(obj, proxy, args, method)
				.proceed();
	}

	/**
	 * The Class SingleInterceptedMethodInvocation.
	 * 
	 * @author Quoc Chung - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Dec 22, 2008
	 */
	class SingleInterceptedMethodInvocation extends AbstractMethodInvocation
			implements MethodInvocation {
		/** The method. */
		Method	method;

		/** The index. */
		int		index	= -1;

		/**
		 * The Constructor.
		 * 
		 * @param proxy
		 *            the proxy
		 * @param methodProxy
		 *            the method proxy
		 * @param arguments
		 *            the arguments
		 * @param method
		 *            the method
		 */
		public SingleInterceptedMethodInvocation(Object proxy,
				MethodProxy methodProxy, Object[] arguments, Method method) {

			super(proxy, methodProxy, arguments);
			this.method = method;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.MethodInvocation#getMethod()
		 */
		@Override
		public Method getMethod() {

			return this.method;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#proceed()
		 */
		@Override
		public Object proceed() throws Throwable {

			Object result;
			try {
				index++;
				// Nếu phương thức triệu gọi là của annotation.
				if (method.getDeclaringClass().isAnnotation()) {
//					if (index == 0) {
//						result = interceptor.invoke(this);
//					}
//					else
//						throw new MethodInterceptorCallbackException();
					try {
						result = index != 0 ? methodProxy.invokeSuper(proxy,
								arguments) : interceptor.invoke(this);
					}
					catch (NoSuchMethodError e) {
						throw new MethodInterceptorCallbackException();
					}
				}
				else {
					result = index != 0 ? methodProxy.invokeSuper(proxy,
							arguments) : interceptor.invoke(this);
				}
			}
			finally {
				index--;
			}
			return result;
		}
	}
}
