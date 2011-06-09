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

import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;

import org.jgentleframework.integration.remoting.RemoteAbstractExporter;

/**
 * Là một <code>abstract class</code> chịu trách nhiệm quản lý, cũng như cho
 * phép <code>wrapping</code> một <code>JGentle object bean</code> thành một
 * <code>RMI Wrapping Bean</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 28, 2008
 * @see RemoteAbstractExporter
 */
public abstract class RmiWrapperExporter extends RemoteAbstractExporter
		implements RmiExporter {
	/** The exported object. */
	protected Remote					exportedObject;

	/** The registry. */
	protected Registry					registry;

	/** The registry client socket factory. */
	protected RMIClientSocketFactory	registryClientSocketFactory;

	/** The registry server socket factory. */
	protected RMIServerSocketFactory	registryServerSocketFactory;

	/** The rmi client socket factory. */
	protected RMIClientSocketFactory	rmiClientSocketFactory;

	/** The rmi server socket factory. */
	protected RMIServerSocketFactory	rmiServerSocketFactory;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiExporter#getRegistry
	 * ()
	 */
	public Registry getRegistry() {

		return registry;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiExporter#setRegistry
	 * (java.rmi.registry.Registry)
	 */
	public void setRegistry(Registry registry) {

		this.registry = registry;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRegistryClientSocketFactory()
	 */
	@Override
	public RMIClientSocketFactory getRegistryClientSocketFactory() {

		return registryClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRegistryClientSocketFactory(java.rmi.server.RMIClientSocketFactory)
	 */
	@Override
	public void setRegistryClientSocketFactory(
			RMIClientSocketFactory registryClientSocketFactory) {

		this.registryClientSocketFactory = registryClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRegistryServerSocketFactory()
	 */
	@Override
	public RMIServerSocketFactory getRegistryServerSocketFactory() {

		return registryServerSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRegistryServerSocketFactory(java.rmi.server.RMIServerSocketFactory)
	 */
	@Override
	public void setRegistryServerSocketFactory(
			RMIServerSocketFactory registryServerSocketFactory) {

		this.registryServerSocketFactory = registryServerSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRmiClientSocketFactory()
	 */
	@Override
	public RMIClientSocketFactory getRmiClientSocketFactory() {

		return rmiClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRmiClientSocketFactory(java.rmi.server.RMIClientSocketFactory)
	 */
	@Override
	public void setRmiClientSocketFactory(
			RMIClientSocketFactory rmiClientSocketFactory) {

		this.rmiClientSocketFactory = rmiClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRmiServerSocketFactory()
	 */
	@Override
	public RMIServerSocketFactory getRmiServerSocketFactory() {

		return rmiServerSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRmiServerSocketFactory(java.rmi.server.RMIServerSocketFactory)
	 */
	@Override
	public void setRmiServerSocketFactory(
			RMIServerSocketFactory rmiServerSocketFactory) {

		this.rmiServerSocketFactory = rmiServerSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getExportedObject()
	 */
	@Override
	public Remote getExportedObject() {

		if (exportedObject == null) {
			exportedObject = RmiExecutor.getObjectBeanExporter(this);
		}
		return exportedObject;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setExportedObject(java.rmi.Remote)
	 */
	@Override
	public void setExportedObject(Remote exportedObject) {

		this.exportedObject = exportedObject;
	}
}
