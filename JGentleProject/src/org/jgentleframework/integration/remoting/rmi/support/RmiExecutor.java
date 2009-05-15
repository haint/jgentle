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
package org.jgentleframework.integration.remoting.rmi.support;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.integration.remoting.RemotingException;

/**
 * This class is responsible for rmi service creation, management and execution.
 * It provides some static methods in order to create rmi registry, execute rmi
 * service ... and instantiate exported object bean.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 10, 2009
 * @see RmiExporter
 * @see RmiBinder
 */
public abstract class RmiExecutor {
	/** The Constant log. */
	public static final Log	log	= LogFactory.getLog(RmiExecutor.class);

	/**
	 * Run service.
	 * 
	 * @param rmiExporter
	 *            the rmi exporter
	 * @throws RemoteException
	 *             the remote exception
	 */
	public static void runService(RmiExporter rmiExporter)
			throws RemoteException {

		// Chuẩn bị khởi tạo rmi service
		prepareService(rmiExporter);
		Remote exportedObject = rmiExporter.getExportedObject();
		int servicePort = rmiExporter.getServicePort();
		String serviceName = rmiExporter.getServiceName();
		// Export RMI object bean thành remote object.
		if (rmiExporter.getRmiClientSocketFactory() != null) {
			UnicastRemoteObject.exportObject(exportedObject, servicePort,
					rmiExporter.getRmiClientSocketFactory(), rmiExporter
							.getRmiServerSocketFactory());
		}
		else {
			UnicastRemoteObject.exportObject(exportedObject, servicePort);
		}
		// Bind RMI object bean vào registry.
		try {
			if (rmiExporter.isReplaceExistingBinding()) {
				rmiExporter.getRegistry().rebind(serviceName, exportedObject);
			}
			else {
				rmiExporter.getRegistry().bind(serviceName, exportedObject);
			}
		}
		catch (AlreadyBoundException ex) {
			unexportObjectBeanService(rmiExporter);
			throw new IllegalStateException(
					"Already an RMI object bound for name [" + serviceName
							+ "'] :: " + ex.toString());
		}
		catch (RemoteException ex) {
			unexportObjectBeanService(rmiExporter);
			throw ex;
		}
		if (log.isInfoEnabled()) {
			log.info("RMI service ['" + serviceName + "'] at service_port ["
					+ servicePort + "], registry_host ["
					+ rmiExporter.getRegistryHost() + "], registry_port ["
					+ rmiExporter.getRegistryPort() + "] is running ... !");
		}
	}

	/**
	 * Prepares service.
	 * 
	 * @param rmiExporter
	 *            the rmi exporter
	 * @throws RemoteException
	 *             the remote exception
	 */
	public static void prepareService(RmiExporter rmiExporter)
			throws RemoteException {

		// Kiểm tra service.
		rmiExporter.checkService();
		if (rmiExporter.getServiceName() == null
				|| (rmiExporter.getServiceName() != null && rmiExporter
						.getServiceName().isEmpty())) {
			throw new RemotingException(
					"Property 'serviceName' must not be null or empty.");
		}
		if (rmiExporter.getRmiClientSocketFactory() instanceof RMIServerSocketFactory) {
			rmiExporter
					.setRmiServerSocketFactory((RMIServerSocketFactory) rmiExporter
							.getRmiClientSocketFactory());
		}
		if ((rmiExporter.getRmiClientSocketFactory() != null && rmiExporter
				.getRmiServerSocketFactory() == null)
				|| (rmiExporter.getRmiClientSocketFactory() == null && rmiExporter
						.getRmiServerSocketFactory() != null)) {
			throw new RemotingException(
					"Either RMIClientSocketFactory or RMIServerSocketFactory is null.");
		}
		if (rmiExporter.getRegistryClientSocketFactory() instanceof RMIServerSocketFactory) {
			rmiExporter
					.setRegistryServerSocketFactory((RMIServerSocketFactory) rmiExporter
							.getRegistryClientSocketFactory());
		}
		// Kiểm tra socket factories của RMI registry.
		if (rmiExporter.getRegistryClientSocketFactory() == null
				&& rmiExporter.getRegistryServerSocketFactory() != null) {
			throw new RemotingException(
					"RMIServerSocketFactory without RMIClientSocketFactory for registry not supported");
		}
		if (rmiExporter.getRegistry() == null) {
			rmiExporter.setRegistry(getRegistry(rmiExporter
					.isAutoCreateRegistry(), rmiExporter.getRegistryHost(),
					rmiExporter.getRegistryPort(), rmiExporter
							.getRegistryClientSocketFactory(), rmiExporter
							.getRegistryServerSocketFactory()));
		}
		// creates the exported rmi object
		rmiExporter.setExportedObject(getObjectBeanExporter(rmiExporter));
	}

	/**
	 * Returns the exported object bean . The exported bean may be standard
	 * exported object in case its corresponding declared class is implemented
	 * {@link Remote} interface. Otherwise, exported bean will be an instance of
	 * {@link RmiWrappingBeanExporter}, representing the proxy which can receive
	 * and pass the invoking of client to real target exported bean.
	 * 
	 * @param rmiExporter
	 *            the rmi exporter
	 * @return the object bean exporter
	 */
	public static Remote getObjectBeanExporter(RmiExporter rmiExporter) {

		if (((rmiExporter.getServiceInterface() == null) || (Remote.class
				.isAssignableFrom(rmiExporter.getServiceInterface())))
				&& rmiExporter.getServiceObject() instanceof Remote) {
			if (log.isInfoEnabled()) {
				log.info("RMI service [" + rmiExporter.getServiceName()
						+ "] is a standard RMI exported object.");
			}
			return (Remote) rmiExporter.getServiceObject();
		}
		else {
			return new RmiWrappingBeanExporterImpl(
					(RmiWrapperExporter) rmiExporter, rmiExporter
							.getServiceObject());
		}
	}

	/**
	 * Gets the registry.
	 * 
	 * @param registryPort
	 *            the registry port
	 * @param autoCreateRegistry
	 *            the auto create registry
	 * @return Registry
	 * @throws RemoteException
	 *             the remote exception
	 */
	public static Registry getRegistry(boolean autoCreateRegistry,
			int registryPort) throws RemoteException {

		if (autoCreateRegistry) {
			return LocateRegistry.createRegistry(registryPort);
		}
		try {
			Registry reg = LocateRegistry.getRegistry(registryPort);
			// Kiểm tra Registry.
			testRegistry(reg);
			return reg;
		}
		catch (RemoteException ex) {
			return LocateRegistry.createRegistry(registryPort);
		}
	}

	/**
	 * Gets the registry.
	 * 
	 * @param registryPort
	 *            the registry port
	 * @param clientSocketFactory
	 *            the client socket factory
	 * @param serverSocketFactory
	 *            the server socket factory
	 * @param autoCreateRegistry
	 *            the auto create registry
	 * @return {@link Registry}
	 * @throws RemoteException
	 *             the remote exception
	 */
	public static Registry getRegistry(boolean autoCreateRegistry,
			int registryPort, RMIClientSocketFactory clientSocketFactory,
			RMIServerSocketFactory serverSocketFactory) throws RemoteException {

		if (clientSocketFactory != null) {
			if (autoCreateRegistry) {
				return LocateRegistry.createRegistry(registryPort,
						clientSocketFactory, serverSocketFactory);
			}
			try {
				Registry reg = LocateRegistry.getRegistry(null, registryPort,
						clientSocketFactory);
				testRegistry(reg);
				return reg;
			}
			catch (RemoteException ex) {
				return LocateRegistry.createRegistry(registryPort,
						clientSocketFactory, serverSocketFactory);
			}
		}
		else {
			return getRegistry(autoCreateRegistry, registryPort);
		}
	}

	/**
	 * Gets the registry.
	 * 
	 * @param registryHost
	 *            the registry host
	 * @param registryPort
	 *            the registry port
	 * @param clientSocketFactory
	 *            the client socket factory
	 * @param serverSocketFactory
	 *            the server socket factory
	 * @param autoCreateRegistry
	 *            the auto create registry
	 * @return {@link Registry}
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected static Registry getRegistry(boolean autoCreateRegistry,
			String registryHost, int registryPort,
			RMIClientSocketFactory clientSocketFactory,
			RMIServerSocketFactory serverSocketFactory) throws RemoteException {

		if (registryHost != null && !registryHost.isEmpty()) {
			if (log.isInfoEnabled()) {
				log.info("Looking for RMI registry at port '" + registryPort
						+ "' of host [" + registryHost + "]");
			}
			Registry reg = LocateRegistry.getRegistry(registryHost,
					registryPort, clientSocketFactory);
			// Kiểm tra registry.
			testRegistry(reg);
			return reg;
		}
		else {
			return getRegistry(autoCreateRegistry, registryPort,
					clientSocketFactory, serverSocketFactory);
		}
	}

	/**
	 * Stop service.
	 * 
	 * @param rmiExporter
	 *            the rmi exporter
	 * @throws AccessException
	 *             the access exception
	 * @throws RemoteException
	 *             the remote exception
	 * @throws NotBoundException
	 *             the not bound exception
	 */
	public static void stopService(RmiExporter rmiExporter)
			throws AccessException, RemoteException, NotBoundException {

		Registry registry = rmiExporter.getRegistry();
		String serviceName = rmiExporter.getServiceName();
		int servicePort = rmiExporter.getServicePort();
		int registryPort = rmiExporter.getRegistryPort();
		if (log.isInfoEnabled()) {
			log.info("Unbinding RMI service '" + serviceName
					+ "' from registry at port '" + servicePort + "'");
		}
		try {
			registry.unbind(serviceName);
		}
		catch (NotBoundException ex) {
			if (log.isWarnEnabled()) {
				log.warn("RMI service '" + serviceName
						+ "' is not bound to registry at port '" + registryPort
						+ "' anymore", ex);
			}
		}
		finally {
			unexportObjectBeanService(rmiExporter);
		}
	}

	/**
	 * Test registry.
	 * 
	 * @param registry
	 *            the registry
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected static void testRegistry(Registry registry)
			throws RemoteException {

		registry.list();
	}

	/**
	 * Unexport object bean service.
	 * 
	 * @param rmiExporter
	 *            the rmi exporter
	 */
	public static void unexportObjectBeanService(RmiExporter rmiExporter) {

		try {
			UnicastRemoteObject.unexportObject(rmiExporter.getExportedObject(),
					true);
		}
		catch (NoSuchObjectException ex) {
			if (log.isWarnEnabled()) {
				log.warn("RMI object for service ["
						+ rmiExporter.getServiceName()
						+ "] isn't exported anymore", ex);
			}
		}
	}
}
