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
package org.jgentleframework.integration.remoting.rmi.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.Remote;
import java.rmi.RemoteException;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.core.intercept.BasicMethodInvocation;
import org.jgentleframework.integration.remoting.RemoteInvocation;
import org.jgentleframework.integration.remoting.RemoteInvocationException;
import org.jgentleframework.utils.ExceptionUtils;

/**
 * This class is a method interceptor which is reponsible for
 * <code>client RMI invocation</code>. It wraps all client invocations in
 * {@link RemoteInvocation} at run-time and then create the connection to
 * specified <code>RMI stub</code> in order to execute them.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 17, 2008
 * @see MethodInterceptor
 */
public class RmiBinderInterceptor implements MethodInterceptor {
	/** The binder. */
	private RmiBinder	binder	= null;

	private final Log	log		= LogFactory.getLog(getClass());

	/**
	 * Constructor.
	 * 
	 * @param binder
	 *            the binder
	 */
	public RmiBinderInterceptor(RmiBinder binder) {

		this.binder = binder;
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

		Remote stub = (Remote) this.binder.getStub();
		MethodInvocation invocation = new BasicMethodInvocation(obj, method,
				args);
		Object result = null;
		if (Modifier.isAbstract(method.getModifiers())) {
			try {
				result = this.binder.invoke(invocation, stub);
			}
			catch (InvocationTargetException ex) {
				Throwable targetEx = ex.getTargetException();
				if (targetEx instanceof RemoteException) {
					RemoteException rex = (RemoteException) targetEx;
					throw ExceptionUtils.convertRmiAccessException(invocation
							.getMethod(), rex, this.binder
							.isConnectFailure(rex), this.binder
							.getServiceName());
				}
				else {
					Throwable exToThrow = ex.getTargetException();
					ExceptionUtils.fillInClientStackTraceIfPossible(exToThrow);
					throw exToThrow;
				}
			}
			catch (NoSuchMethodException ex) {
				throw new RemoteInvocationException(
						"No matching RMI stub method found for: " + method, ex);
			}
			catch (RemoteException e) {
				if (this.binder.isConnectFailure(e)
						&& this.binder.isRefreshStubOnConnectFailure()) {
					this.binder.refreshStubAndRetryInvocation(invocation);
				}
				else {
					throw new RemoteInvocationException(
							"Invocation of method [" + invocation.getMethod()
									+ "] failed in RMI service ["
									+ this.binder.getServiceName() + "]", e);
				}
			}
			catch (Throwable ex) {
				if (log.isErrorEnabled()) {
					log.error("Invocation of method [" + invocation.getMethod()
							+ "] failed in RMI service ["
							+ this.binder.getServiceName() + "]", ex);
				}
				throw new RemoteInvocationException("Invocation of method ["
						+ invocation.getMethod() + "] failed in RMI service ["
						+ this.binder.getServiceName() + "]", ex);
			}
		}
		return result;
	}

	/**
	 * Gets the binder.
	 * 
	 * @return the binder
	 */
	public RmiBinder getBinder() {

		return binder;
	}

	/**
	 * Sets the binder.
	 * 
	 * @param binder
	 *            the new binder
	 */
	public void setBinder(RmiBinder binder) {

		this.binder = binder;
	}
}
