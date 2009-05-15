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

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.integration.remoting.DefaultID;
import org.jgentleframework.integration.remoting.rmi.support.RmiExporter;

/**
 * The Interface IRmiExporterProxyFactoryBean.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 12, 2009
 */
public interface IRmiExporterProxyFactoryBean extends DefaultID {
	/**
	 * Gets the service class.
	 * 
	 * @return the serviceClass
	 */
	public Class<?> getServiceClass();

	/**
	 * Sets the service class.
	 * 
	 * @param serviceClass
	 *            the serviceClass to set
	 */
	public void setServiceClass(Class<?> serviceClass);

	/**
	 * Gets the provider.
	 * 
	 * @return the provider
	 */
	public Provider getProvider();

	/**
	 * Gets the service object id.
	 * 
	 * @return the serviceObjectID
	 */
	public String getServiceObjectID();

	/**
	 * Sets the service object id.
	 * 
	 * @param serviceObjectID
	 *            the serviceObjectID to set
	 */
	public void setServiceObjectID(String serviceObjectID);

	/**
	 * Gets the exporter id.
	 * 
	 * @return the exporterID
	 */
	public String getExporterID();

	/**
	 * Sets the exporter id.
	 * 
	 * @param exporterID
	 *            the exporterID to set
	 */
	public void setExporterID(String exporterID);

	/**
	 * Gets the service object.
	 * 
	 * @return the serviceObject
	 */
	public Object getServiceObject();

	/**
	 * Sets the service object.
	 * 
	 * @param serviceObject
	 *            the serviceObject to set
	 */
	public void setServiceObject(Object serviceObject);

	/**
	 * Gets the rmi exporter.
	 * 
	 * @return the rmiExporter
	 */
	public RmiExporter getRmiExporter();

	/**
	 * Sets the rmi exporter.
	 * 
	 * @param rmiExporter
	 *            the rmiExporter to set
	 */
	public void setRmiExporter(RmiExporter rmiExporter);
}