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
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.StubNotFoundException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.integration.remoting.RemoteAbstractBinder;
import org.jgentleframework.integration.remoting.RemoteInvocationImpl;
import org.jgentleframework.integration.remoting.RemoteLookupException;
import org.jgentleframework.integration.remoting.RemotingException;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.SystemException;

/**
 * The Class RmiAbstractBinder.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 18, 2008
 * @see RemoteAbstractBinder
 */
public abstract class RmiAbstractBinder extends RemoteAbstractBinder implements
		RmiBinder {
	/** Cached Stub. */
	protected Remote	stub	= null;

	/** The stub syn. */
	protected Object	stubSyn	= new Object();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiBinder#getStub()
	 */
	@Override
	public Remote getStub() {

		Remote result = null;
		synchronized (stubSyn) {
			if ((this.isLookupStubOnStartup() && !this
					.isRefreshStubOnConnectFailure())
					|| !this.isCacheStub()) {
				result = this.stub != null ? this.stub : lookupStub(this
						.getRegistryHost(), this.getRegistryPort(), this
						.getServiceName(), this
						.getRegistryClientSocketFactory());
			}
			else {
				if (this.stub == null) {
					this.stub = lookupStub(this.getRegistryHost(), this
							.getRegistryPort(), this.getServiceName(), this
							.getRegistryClientSocketFactory());
				}
				result = this.stub;
			}
		}
		return result;
	}

	/**
	 * Traditional RMI Invoking.
	 * 
	 * @param invocation
	 *            the invocation
	 * @return the object
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws Throwable
	 */
	protected Object invoke(MethodInvocation invocation)
			throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Object result = null;
		Method method = null;
		Method methodType = invocation.getMethod();
		Class<?> clazz = stub.getClass();
		Class<?>[] classType = ReflectUtils.getClassTypeOf(invocation
				.getArguments());
		method = ReflectUtils.getSupportedMethod(clazz, methodType.getName(),
				classType);
		result = method.invoke(stub, invocation.getArguments());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiBinder#invoke
	 * (org.aopalliance.intercept.MethodInvocation, java.rmi.Remote)
	 */
	@Override
	public Object invoke(MethodInvocation invocation, Remote stub)
			throws RemoteException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Assertor.notNull(invocation);
		Object result = null;
		if (stub != null
				&& ReflectUtils.isCast(RmiWrappingBeanExporter.class, stub)) {
			result = invoke(invocation, (RmiWrappingBeanExporter) stub);
		}
		else if (stub == null) {
			RemoteLookupException ex = new RemoteLookupException(
					"Lookup stub of RMI is failed");
			throw ex;
		}
		else {
			result = invoke(invocation);
		}
		return result;
	}

	/**
	 * Invokes the given {@link MethodInvocation} beasing on the given
	 * {@link RmiWrappingBeanExporter}.
	 * 
	 * @param methodInvocation
	 *            the given {@link MethodInvocation}
	 * @param wrapperBean
	 *            the given {@link RmiWrappingBeanExporter}
	 * @return returns the result of invocation.
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected Object invoke(MethodInvocation methodInvocation,
			RmiWrappingBeanExporter wrapperBean) throws RemoteException {

		return wrapperBean.invoke(new RemoteInvocationImpl(methodInvocation));
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * isConnectFailure(java.rmi.RemoteException)
	 */
	@Override
	public boolean isConnectFailure(RemoteException ex) {

		return (ex instanceof ConnectException
				|| ex instanceof ConnectIOException
				|| ex instanceof UnknownHostException
				|| ex instanceof NoSuchObjectException
				|| ex instanceof StubNotFoundException
				|| isCorbaConnectFailure(ex.getCause()) || "com.evermind.server.rmi.RMIConnectionException"
				.equals(ex.getClass().getName()));
	}

	/**
	 * Check whether the given RMI exception root cause indicates a CORBA
	 * connection failure.
	 * 
	 * @param ex
	 *            the ex
	 * @return true, if is corba connect failure
	 */
	private boolean isCorbaConnectFailure(Throwable ex) {

		return ((ex instanceof COMM_FAILURE || ex instanceof NO_RESPONSE) && ((SystemException) ex).completed == CompletionStatus.COMPLETED_NO);
	}

	/**
	 * Thực thi việc <code>lookup stub</code>, dựa trên thông số về
	 * <code>host, port, service name</code> chỉ định. Tiến trình thực thi sẽ tự
	 * động truy xuất đến <code>registry</code>, nếu có bất cứ thực thi nào
	 * không thể thực hiện, ngoại lệ {@link RemoteException} được ném ra bởi cơ
	 * chế bên trong của <code>RMI</code> sẽ được <code>convert</code> lại thành
	 * {@link RemoteLookupException} và được <i>throw</i> ra ngoài.
	 * 
	 * @param host
	 *            registry host
	 * @param port
	 *            registry port
	 * @param serviceName
	 *            service name
	 * @param registryClientSocketFactory
	 *            chỉ định tham số này nếu có.
	 * @return trả về <code>Remote object</code> tương ứng với
	 *         <code>service name</code> nếu thành công.
	 * @throws RemoteLookupException
	 *             ném ra ngoại lệ này nếu tiến trình thực thi gặp lỗi.
	 */
	protected Remote lookupStub(String host, int port, String serviceName,
			RMIClientSocketFactory registryClientSocketFactory)
			throws RemoteLookupException {

		Remote stub = null;
		Registry registry = null;
		try {
			if (registryClientSocketFactory != null) {
				registry = LocateRegistry.getRegistry(host, port,
						registryClientSocketFactory);
			}
			else {
				registry = LocateRegistry.getRegistry(host, port);
			}
			stub = registry.lookup(serviceName);
		}
		catch (RemoteException e) {
			RemoteLookupException ex = new RemoteLookupException(
					"Lookup stub of RMI is failed");
			ex.initCause(e);
			throw ex;
		}
		catch (NotBoundException e) {
			RemoteLookupException ex = new RemoteLookupException(
					"Does not found RMI Service '" + serviceName
							+ "' in RMI registry (" + host + ":" + port + "'");
			ex.initCause(e);
			throw ex;
		}
		return stub;
	}

	/**
	 * Prepares binding service.
	 */
	public void prepare() {

		if (this.getServiceName() == null || this.getServiceName().isEmpty()) {
			throw new RemotingException(
					"The property 'serviceName' is required !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * refreshStubAndRetryInvocation(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object refreshStubAndRetryInvocation(MethodInvocation invocation)
			throws Throwable {

		Remote stub = null;
		synchronized (stubSyn) {
			this.stub = null;
			stub = lookupStub(this.getRegistryHost(), this.getRegistryPort(),
					this.getServiceName(), this
							.getRegistryClientSocketFactory());
			if (this.isCacheStub()) {
				this.stub = stub;
			}
		}
		return invoke(invocation, stub);
	}
}
