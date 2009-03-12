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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.integration.remoting.RemoteLookupException;

/**
 * Chịu trách nhiệm <code>lookup stub</code> của <code>RMI service</code>, dựa
 * trên các thông tin về <code>host</code>, <code>port</code> của
 * <code><code>registry</code></code> và <code>RMI service name</code>. Nếu tiến
 * trình <code>lookup</code> không thành công, ngoại lệ {@link RemoteException}
 * được ném ra bởi cơ chế RMI sẽ được <i>convert</i> và trả ngược lại là một
 * ngoại lệ <code>run-time</code> của JGentle ({@link RemoteLookupException}).
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 31, 2009
 */
public interface RmiBinder {
	/**
	 * Returns the <code>object class</code> of current
	 * <code>service interface</code>.
	 * 
	 * @return the serviceInterface
	 */
	public Class<?> getServiceInterface();

	/**
	 * Sets the service interface.
	 * 
	 * @param serviceInterface
	 *            the service interface
	 */
	public void setServiceInterface(Class<?> serviceInterface);

	/**
	 * Checks if is connect failure.
	 * 
	 * @param ex
	 *            the {@link RemoteException}
	 * @return returns <b>true</b> if it is, if not, returns <b>false</b>.
	 */
	public boolean isConnectFailure(RemoteException ex);

	/**
	 * Returns stub of current <code>RMI binder</code>.
	 * 
	 * @return Remote
	 */
	public Remote getStub();

	/**
	 * Try to refresh stub and retry invocation.
	 * 
	 * @param invocation
	 *            the invocation
	 * @return the object
	 * @throws Throwable
	 *             the throwable
	 */
	public Object refreshStubAndRetryInvocation(MethodInvocation invocation)
			throws Throwable;

	/**
	 * <code>invoke</code> the given {@link MethodInvocation} basing on current
	 * <code>stub</code>
	 * 
	 * @param invocation
	 *            the method invocation
	 * @param stub
	 *            the specified <code>stub</code>
	 * @throws RemoteException
	 *             throws this exception if the invoking process is failure.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public Object invoke(MethodInvocation invocation, Remote stub)
			throws RemoteException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException;

	/**
	 * Gets the registry client socket factory.
	 * 
	 * @return the registry client socket factory
	 */
	public RMIClientSocketFactory getRegistryClientSocketFactory();

	/**
	 * Gets the registry host.
	 * 
	 * @return the registry host
	 */
	public String getRegistryHost();

	/**
	 * Gets the registry port.
	 * 
	 * @return the registryPort
	 */
	public int getRegistryPort();

	/**
	 * Gets the service name.
	 * 
	 * @return the service name
	 */
	public String getServiceName();

	/**
	 * Checks if is cache stub.
	 * 
	 * @return true, if is cache stub
	 */
	public boolean isCacheStub();

	/**
	 * Checks if is lookup stub on startup.
	 * 
	 * @return boolean
	 */
	public boolean isLookupStubOnStartup();

	/**
	 * Checks if is refresh stub on connect failure.
	 * 
	 * @return true, if is refresh stub on connect failure
	 */
	public boolean isRefreshStubOnConnectFailure();

	/**
	 * Sets the cache stub.
	 * 
	 * @param cacheStub
	 *            the cache stub
	 */
	public void setCacheStub(boolean cacheStub);

	/**
	 * Sets the lookup stub on startup.
	 * 
	 * @param lookupStubOnStartup
	 *            the lookup stub on startup
	 */
	public void setLookupStubOnStartup(boolean lookupStubOnStartup);

	/**
	 * Sets the refresh stub on connect failure.
	 * 
	 * @param refreshStubOnConnectFailure
	 *            the refresh stub on connect failure
	 */
	public void setRefreshStubOnConnectFailure(
			boolean refreshStubOnConnectFailure);

	/**
	 * Sets the registry client socket factory.
	 * 
	 * @param registryClientSocketFactory
	 *            the registry client socket factory
	 */
	public void setRegistryClientSocketFactory(
			RMIClientSocketFactory registryClientSocketFactory);

	/**
	 * Sets the registry host.
	 * 
	 * @param registryHost
	 *            the registry host
	 */
	public void setRegistryHost(String registryHost);

	/**
	 * Sets the registry port.
	 * 
	 * @param registryPort
	 *            the registry port
	 */
	public void setRegistryPort(int registryPort);

	/**
	 * Sets the service name.
	 * 
	 * @param serviceName
	 *            the service name
	 */
	public void setServiceName(String serviceName);
}