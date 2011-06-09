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
package org.jgentleframework.integration.remoting.rmi.context;

import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.objectmeta.Binder;
import org.jgentleframework.context.beans.FactoryBean;
import org.jgentleframework.context.beans.Initializing;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.integration.remoting.enums.RemoteType;
import org.jgentleframework.integration.remoting.enums.SSLCipherSuites;
import org.jgentleframework.integration.remoting.rmi.RmiExportingException;
import org.jgentleframework.integration.remoting.rmi.annotation.RmiExporting;
import org.jgentleframework.integration.remoting.rmi.customsocket.CompressionSocket_RMIClientSocketFactory;
import org.jgentleframework.integration.remoting.rmi.customsocket.CompressionSocket_RMIServerSocketFactory;
import org.jgentleframework.integration.remoting.rmi.customsocket.SSLSocket_RMIClientSocketFactory;
import org.jgentleframework.integration.remoting.rmi.customsocket.SSLSocket_RMIServerSocketFactory;
import org.jgentleframework.integration.remoting.rmi.support.RmiExporter;
import org.jgentleframework.integration.remoting.rmi.support.RmiExporterImpl;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class RmiExporterProxyFactoryBean.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 10, 2009
 * @see FactoryBean
 * @see ProviderAware
 * @see RmiExporter
 */
public class RmiExporterProxyFactoryBean implements FactoryBean, ProviderAware,
		IRmiExporterProxyFactoryBean, Initializing {
	/** The auto create registry. */
	private boolean						autoCreateRegistry			= false;

	/** The autorun. */
	private boolean						autorun						= true;

	/** The exporter id. */
	protected String					exporterID					= DEFAULT_RMIEXPORTER_ID;

	/** The scope. */
	protected Scope						scope						= Scope.SINGLETON;

	/** The log. */
	private final Log					log							= LogFactory
																			.getLog(getClass());

	/** The current provider. */
	Provider							provider					= null;

	/** The registry. */
	protected Registry					registry					= null;

	/** The registry client socket factory. */
	protected RMIClientSocketFactory	registryClientSocketFactory	= null;

	/** The registry host. */
	private String						registryHost				= null;

	/** The registry port. */
	private int							registryPort				= Registry.REGISTRY_PORT;

	/** The registry server socket factory. */
	protected RMIServerSocketFactory	registryServerSocketFactory	= null;

	/** The replace existing binding. */
	private boolean						replaceExistingBinding		= true;

	/** The rmi client socket factory. */
	protected RMIClientSocketFactory	rmiClientSocketFactory		= null;

	/** The rmi exporter. */
	RmiExporter							rmiExporter					= null;

	/** The rmi server socket factory. */
	protected RMIServerSocketFactory	rmiServerSocketFactory		= null;

	/** The service class. */
	@Inject(value = REF.REF_CONSTANT + DEFAULT_SERVICE_CLASS, required = false, alwaysInject = true)
	Class<?>							serviceClass				= null;

	/** The service interface. */
	protected Class<?>					serviceInterface			= null;

	/** The service name. */
	private String						serviceName					= "default";

	/** The service object. */
	protected Object					serviceObject				= null;

	/** The ID of object service. */
	String								serviceObjectID				= null;

	/** The service port. */
	private int							servicePort					= 0;

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.FactoryBean#getBean()
	 */
	@Override
	public Object getBean() throws Exception {

		if (this.rmiExporter == null) {
			this.rmiExporter = (RmiExporter) this.provider
					.getBeanBoundToDefinition(exporterID);
		}
		return this.rmiExporter;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getExporterID()
	 */
	public String getExporterID() {

		return exporterID;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getProvider()
	 */
	public Provider getProvider() {

		return provider;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getRmiExporter()
	 */
	public RmiExporter getRmiExporter() {

		if (this.rmiExporter == null) {
			try {
				this.rmiExporter = (RmiExporter) this.getBean();
			}
			catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("Could not create RMI exporter !", e);
				}
			}
		}
		return rmiExporter;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getServiceClass()
	 */
	public Class<?> getServiceClass() {

		return serviceClass;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getServiceObject()
	 */
	public Object getServiceObject() {

		return serviceObject;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#getServiceObjectID()
	 */
	public String getServiceObjectID() {

		return serviceObjectID;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.FactoryBean#isSingleton()
	 */
	@Override
	public boolean isSingleton() {

		return false;
	}

	/**
	 * Prepare attributes.
	 * 
	 * @param serviceObject
	 *            the service object
	 * @param definition
	 *            the definition
	 */
	protected void prepareAttributes(Object serviceObject, Definition definition) {

		DefinitionManager defManager = this.provider.getDefinitionManager();
		this.serviceObject = serviceObject;
		Class<?> ownerClass = definition.getOwnerClass();
		// find service interface
		Set<Class<?>> interfaces = ReflectUtils.getAllInterfaces(ownerClass,
				true);
		for (Class<?> interfaze : interfaces) {
			Definition defInterfaze = defManager.getDefinition(interfaze);
			if (defInterfaze
					.isAnnotationPresent(org.jgentleframework.integration.remoting.annotation.Remote.class)) {
				org.jgentleframework.integration.remoting.annotation.Remote remoteAnno = defInterfaze
						.getAnnotation(org.jgentleframework.integration.remoting.annotation.Remote.class);
				if (remoteAnno.type().equals(RemoteType.RMI)) {
					this.serviceInterface = interfaze;
					break;
				}
			}
		}
		// other
		if (definition.isAnnotationPresent(RmiExporting.class)) {
			RmiExporting anno = definition.getAnnotation(RmiExporting.class);
			this.autoCreateRegistry = anno.autoCreateRegistry();
			this.autorun = anno.autorun();
			this.registryHost = anno.registryHost();
			this.registryPort = anno.registryPort();
			this.replaceExistingBinding = anno.replaceExistingBinding();
			this.serviceName = anno.serviceName();
			this.servicePort = anno.servicePort();
			this.exporterID = anno.rmiExporter().exporterID();
			this.scope = anno.rmiExporter().scope();
			if (anno.compressing()
					&& anno.SSLCipherSuites()[0] == SSLCipherSuites.NONE) {
				this.rmiClientSocketFactory = new CompressionSocket_RMIClientSocketFactory();
				this.rmiServerSocketFactory = new CompressionSocket_RMIServerSocketFactory();
			}
			else if (!anno.compressing()
					&& anno.SSLCipherSuites()[0] != SSLCipherSuites.NONE) {
				this.rmiClientSocketFactory = new SSLSocket_RMIClientSocketFactory(
						anno.SSLCipherSuites());
				this.rmiServerSocketFactory = new SSLSocket_RMIServerSocketFactory(
						anno.SSLCipherSuites());
			}
			else if (anno.compressing()
					&& anno.SSLCipherSuites()[0] != SSLCipherSuites.NONE) {
				if (log.isErrorEnabled()) {
					log
							.error(
									"Does not support RMI compression type if the SSL/TLS is activated !",
									new RmiExportingException());
				}
				this.rmiClientSocketFactory = new SSLSocket_RMIClientSocketFactory(
						anno.SSLCipherSuites());
				this.rmiServerSocketFactory = new SSLSocket_RMIServerSocketFactory(
						anno.SSLCipherSuites());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#setExporterID(java.lang.String)
	 */
	public void setExporterID(String exporterID) {

		this.exporterID = exporterID;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.beans.ProviderAware#setProvider(org.
	 * jgentleframework.context.injecting.Provider)
	 */
	@Override
	public void setProvider(Provider provider) {

		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean
	 * #setRmiExporter(org.jgentleframework.integration
	 * .remoting.rmi.support.RmiExporter)
	 */
	public void setRmiExporter(RmiExporter rmiExporter) {

		this.rmiExporter = rmiExporter;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#setServiceClass(java.lang.Class)
	 */
	public void setServiceClass(Class<?> serviceClass) {

		this.serviceClass = serviceClass;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#setServiceObject(java.lang.Object)
	 */
	public void setServiceObject(Object serviceObject) {

		this.serviceObject = serviceObject;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.rmi.context.
	 * IRmiExporterProxyFactoryBean#setServiceObjectID(java.lang.String)
	 */
	public void setServiceObjectID(String serviceObjectID) {

		this.serviceObjectID = serviceObjectID;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.Initializing#initialize()
	 */
	@Override
	public void initialize() {

		Object objService = null;
		Definition definition = null;
		if (serviceObjectID != null && !serviceObjectID.isEmpty()) {
			objService = this.provider
					.getBeanBoundToDefinition(serviceObjectID);
			definition = this.provider.getDefinitionManager().getDefinition(
					serviceObjectID);
		}
		else {
			if (serviceClass != null) {
				objService = this.provider.getBean(serviceClass);
				definition = this.provider.getDefinitionManager()
						.getDefinition(serviceClass);
			}
			else {
				if (log.isFatalEnabled()) {
					log
							.fatal(
									"Could not found object service bean of RMI exporter !",
									new RmiExportingException());
				}
			}
		}
		// check object bean service
		if (objService == null && log.isFatalEnabled()) {
			log.fatal("Could not found object service bean of RMI exporter !",
					new RmiExportingException());
		}
		else if (definition == null && log.isFatalEnabled()) {
			log.fatal(
					"Could not found the definition appropriate to the given "
							+ "object service bean of RMI exporter !",
					new RmiExportingException());
		}
		else if (objService != null && definition != null) {
			prepareAttributes(objService, definition);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Binder binder = new Binder(getProvider());
		map.put("serviceName", this.serviceName);
		map.put("servicePort", this.servicePort);
		map.put("registryHost", this.registryHost);
		map.put("registryPort", this.registryPort);
		map.put("autoCreateRegistry", this.autoCreateRegistry);
		map.put("replaceExistingBinding", this.replaceExistingBinding);
		map.put("autorun", this.autorun);
		map
				.put("registryClientSocketFactory",
						this.registryClientSocketFactory);
		map
				.put("registryServerSocketFactory",
						this.registryServerSocketFactory);
		map.put("rmiClientSocketFactory", this.rmiClientSocketFactory);
		map.put("rmiServerSocketFactory", this.rmiServerSocketFactory);
		map.put("serviceInterface", this.serviceInterface);
		map.put("serviceObject", this.serviceObject);
		binder.bind(map).in(RmiExporterImpl.class).id(exporterID).scope(
				this.scope);
		binder.flush();
	}
}
