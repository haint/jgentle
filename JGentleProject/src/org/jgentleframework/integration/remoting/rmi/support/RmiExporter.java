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

import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;

import org.jgentleframework.integration.remoting.RemoteExporter;

/**
 * The Interface RmiExporter.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 25, 2009
 * @see RmiExporterImpl
 */
public interface RmiExporter extends RemoteExporter {
	/**
	 * Gets the exported object.
	 * 
	 * @return the exportedObject
	 */
	public Remote getExportedObject();

	/**
	 * Gets the registry.
	 * 
	 * @return the registry
	 */
	public Registry getRegistry();

	/**
	 * Gets the registry client socket factory.
	 * 
	 * @return the registryClientSocketFactory
	 */
	public RMIClientSocketFactory getRegistryClientSocketFactory();

	/**
	 * Gets the registry host.
	 * 
	 * @return the registry host
	 */
	public String getRegistryHost();

	/**
	 * Returns the registry port of current RMI service.
	 * 
	 * @return int
	 */
	public int getRegistryPort();

	/**
	 * Gets the registry server socket factory.
	 * 
	 * @return the registryServerSocketFactory
	 */
	public RMIServerSocketFactory getRegistryServerSocketFactory();

	/**
	 * Gets the rmi client socket factory.
	 * 
	 * @return the rmiClientSocketFactory
	 */
	public RMIClientSocketFactory getRmiClientSocketFactory();

	/**
	 * Gets the rmi server socket factory.
	 * 
	 * @return the rmiServerSocketFactory
	 */
	public RMIServerSocketFactory getRmiServerSocketFactory();

	/**
	 * Returns the service name of current remote service.
	 * 
	 * @return String
	 */
	public String getServiceName();

	/**
	 * Gets the service port.
	 * 
	 * @return the port
	 */
	public int getServicePort();

	/**
	 * Checks if is auto create registry.
	 * 
	 * @return boolean
	 */
	public boolean isAutoCreateRegistry();

	/**
	 * Checks if is autorun.
	 * 
	 * @return boolean.
	 */
	public boolean isAutorun();

	/**
	 * Checks if is replace existing binding.
	 * 
	 * @return boolean
	 */
	public boolean isReplaceExistingBinding();

	/**
	 * Sets the auto create registry.
	 * 
	 * @param autoCreateRegistry
	 *            the auto create registry
	 */
	public void setAutoCreateRegistry(boolean autoCreateRegistry);

	/**
	 * Thiết lập chế độ <code>autorun</code> cho
	 * <code>rmi service exporter</code>. Nếu chỉ định <b>true</b>, tự động ngay
	 * sau khi <code>service bean</code> được <code>container</code> khởi tạo,
	 * <code>rmi service</code> cũng sẽ được <code>export</code>. Ngược lại nếu
	 * chỉ định là <b>false</b>, chỉ khi nào <code>rmi service</code> được chỉ
	 * định <code>run service (thông qua runService method)</code> thì exporter
	 * mới được khởi động.
	 * 
	 * @param autorun
	 *            giá trị boolean cần thiết lập.
	 */
	public void setAutorun(boolean autorun);

	/**
	 * Sets the exported object.
	 * 
	 * @param exportedObject
	 *            the exportedObject to set
	 */
	public void setExportedObject(Remote exportedObject);

	/**
	 * Sets the registry.
	 * 
	 * @param registry
	 *            the registry
	 */
	public void setRegistry(Registry registry);

	/**
	 * Sets the registry client socket factory.
	 * 
	 * @param registryClientSocketFactory
	 *            the registryClientSocketFactory to set
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
	 * Sets the registry server socket factory.
	 * 
	 * @param registryServerSocketFactory
	 *            the registryServerSocketFactory to set
	 */
	public void setRegistryServerSocketFactory(
			RMIServerSocketFactory registryServerSocketFactory);

	/**
	 * Set whether to replace an existing binding in the RMI registry, that is,
	 * whether to simply override an existing binding with the specified service
	 * in case of a naming conflict in the registry.
	 * 
	 * @param replaceExistingBinding
	 *            the replace existing binding
	 */
	public void setReplaceExistingBinding(boolean replaceExistingBinding);

	/**
	 * Sets the rmi client socket factory.
	 * 
	 * @param rmiClientSocketFactory
	 *            the rmiClientSocketFactory to set
	 */
	public void setRmiClientSocketFactory(
			RMIClientSocketFactory rmiClientSocketFactory);

	/**
	 * Sets the rmi server socket factory.
	 * 
	 * @param rmiServerSocketFactory
	 *            the rmiServerSocketFactory to set
	 */
	public void setRmiServerSocketFactory(
			RMIServerSocketFactory rmiServerSocketFactory);

	/**
	 * Sets the service name of exported RMI service.
	 * 
	 * @param serviceName
	 *            the service name
	 */
	public void setServiceName(String serviceName);

	/**
	 * Sets the service port of the exported RMI service will use.
	 * <p>
	 * Default is 0 (anonymous port).
	 * 
	 * @param servicePort
	 *            the service port
	 */
	public void setServicePort(int servicePort);
}