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

import org.jgentleframework.context.beans.Initializing;

/**
 * Thực thi khởi tạo một <code>RMI Binder</code>, chịu trách nhiệm khởi tạo
 * <code>stub</code>, kết nối đến <code>registry</code>, thực thi các
 * <code>invocation</code> đến <code>remote service</code>. Đồng thời cũng chịu
 * trách nhiệm <code>wrapping</code> các <code>exception</code> được ném ra bởi
 * cơ chế <code>remote</code> của <b>RMI</b>, thực thi các xử lý thích hợp với
 * từng <code>exception</code> trước khi trao hẳn quyền xử lý
 * <code>exception</code> về phía <code>client</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 18, 2008
 */
public class RmiBinderImpl extends RmiAbstractBinder implements Initializing,
		RmiBinder {
	/** The cache stub. */
	private boolean					cacheStub					= true;

	/** The lookup stub on startup. */
	private boolean					lookupStubOnStartup			= true;

	/** The refresh stub on connect failure. */
	private boolean					refreshStubOnConnectFailure	= false;

	/** The registry client socket factory. */
	private RMIClientSocketFactory	registryClientSocketFactory	= null;

	/** The registry host. Default value is '127.0.0.1' */
	private String					registryHost				= "";

	/** Registry port. */
	private int						registryPort				= Registry.REGISTRY_PORT;

	/** Service Name. */
	private String					serviceName					= "default";

	/**
	 * Constructor.
	 * 
	 * @param serviceName
	 *            the service name
	 * @param registryHost
	 *            the registry host
	 * @param registryPort
	 *            the registry port
	 * @param refreshStubOnConnectFailure
	 *            the refresh stub on connect failure
	 * @param lookupStubOnStartup
	 *            the lookup stub on startup
	 * @param cacheStub
	 *            the cache stub
	 * @param registryClientSocketFactory
	 *            the registry client socket factory
	 */
	public RmiBinderImpl(String serviceName, String registryHost,
			int registryPort, boolean refreshStubOnConnectFailure,
			boolean lookupStubOnStartup, boolean cacheStub,
			RMIClientSocketFactory registryClientSocketFactory) {

		this.serviceName = serviceName;
		this.registryHost = registryHost;
		this.registryPort = registryPort;
		this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
		this.lookupStubOnStartup = lookupStubOnStartup;
		this.cacheStub = cacheStub;
		this.registryClientSocketFactory = registryClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.bean.Initializing#activate()
	 */
	@Override
	public void activate() {

		prepare();
		if (this.lookupStubOnStartup) {
			synchronized (stubSyn) {
				Remote remoteObj = lookupStub(this.registryHost,
						this.registryPort, this.serviceName,
						this.registryClientSocketFactory);
				if (this.cacheStub) {
					this.stub = remoteObj;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * getRegistryClientSocketFactory()
	 */
	public RMIClientSocketFactory getRegistryClientSocketFactory() {

		return registryClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * getRegistryHost()
	 */
	public String getRegistryHost() {

		return registryHost;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * getRegistryPort()
	 */
	public int getRegistryPort() {

		return registryPort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * getServiceName()
	 */
	public String getServiceName() {

		return serviceName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiBinder#isCacheStub
	 * ()
	 */
	public boolean isCacheStub() {

		return cacheStub;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * isLookupStubOnStartup()
	 */
	public boolean isLookupStubOnStartup() {

		return lookupStubOnStartup;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * isRefreshStubOnConnectFailure()
	 */
	public boolean isRefreshStubOnConnectFailure() {

		return refreshStubOnConnectFailure;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiBinder#setCacheStub
	 * (boolean)
	 */
	public void setCacheStub(boolean cacheStub) {

		this.cacheStub = cacheStub;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setLookupStubOnStartup(boolean)
	 */
	public void setLookupStubOnStartup(boolean lookupStubOnStartup) {

		this.lookupStubOnStartup = lookupStubOnStartup;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setRefreshStubOnConnectFailure(boolean)
	 */
	public void setRefreshStubOnConnectFailure(
			boolean refreshStubOnConnectFailure) {

		this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setRegistryClientSocketFactory(java.rmi.server.RMIClientSocketFactory)
	 */
	public void setRegistryClientSocketFactory(
			RMIClientSocketFactory registryClientSocketFactory) {

		this.registryClientSocketFactory = registryClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setRegistryHost(java.lang.String)
	 */
	public void setRegistryHost(String registryHost) {

		this.registryHost = registryHost;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setRegistryPort(int)
	 */
	public void setRegistryPort(int registryPort) {

		this.registryPort = registryPort;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.support.RmiBinder#
	 * setServiceName(java.lang.String)
	 */
	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}
}
