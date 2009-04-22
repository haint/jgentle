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

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import org.jgentleframework.context.beans.Disposable;
import org.jgentleframework.context.beans.Initializing;

/**
 * The Class RmiExporterImpl.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 27, 2008
 */
public class RmiExporterImpl extends RmiWrapperExporter implements
		Initializing, Disposable {
	/** The service name. */
	private String	serviceName				= "default";

	/** The service port. */
	private int		servicePort				= 0;

	/** The registry host. */
	private String	registryHost			= "localhost";

	/** The registry port. */
	private int		registryPort			= Registry.REGISTRY_PORT;

	/** The auto create registry. */
	private boolean	autoCreateRegistry		= false;

	/** The replace existing binding. */
	private boolean	replaceExistingBinding	= true;

	/** The autorun. */
	private boolean	autorun					= true;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiExporter#isAutorun
	 * ()
	 */
	public boolean isAutorun() {

		return autorun;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiExporter#setAutorun
	 * (boolean)
	 */
	public void setAutorun(boolean autorun) {

		this.autorun = autorun;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getServiceName()
	 */
	public String getServiceName() {

		return serviceName;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setServiceName(java.lang.String)
	 */
	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getServicePort()
	 */
	public int getServicePort() {

		return servicePort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setServicePort(int)
	 */
	public void setServicePort(int servicePort) {

		this.servicePort = servicePort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * isAutoCreateRegistry()
	 */
	public boolean isAutoCreateRegistry() {

		return autoCreateRegistry;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setAutoCreateRegistry(boolean)
	 */
	public void setAutoCreateRegistry(boolean autoCreateRegistry) {

		this.autoCreateRegistry = autoCreateRegistry;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRegistryPort()
	 */
	public int getRegistryPort() {

		return registryPort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRegistryPort(int)
	 */
	public void setRegistryPort(int registryPort) {

		this.registryPort = registryPort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * getRegistryHost()
	 */
	public String getRegistryHost() {

		return registryHost;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setRegistryHost(java.lang.String)
	 */
	public void setRegistryHost(String registryHost) {

		this.registryHost = registryHost;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * isReplaceExistingBinding()
	 */
	public boolean isReplaceExistingBinding() {

		return replaceExistingBinding;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiExporter#
	 * setReplaceExistingBinding(boolean)
	 */
	public void setReplaceExistingBinding(boolean replaceExistingBinding) {

		this.replaceExistingBinding = replaceExistingBinding;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.Initializing#activate()
	 */
	@Override
	public void activate() {

		if (this.isAutorun()) {
			try {
				// Khởi động rmi service
				RmiExecutor.runService(this);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.Disposable#destroy()
	 */
	@Override
	public void destroy() throws Exception {

		RmiExecutor.stopService(this);
	}
}
