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
package org.jgentleframework.integration.remoting;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class RemoteAbstractExporter.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 17, 2008
 */
public abstract class RemoteAbstractExporter implements RemoteExporter {
	/** The service interface. */
	protected Class<?>	serviceInterface	= null;

	/** The service object. */
	protected Object	serviceObject		= null;

	/** The bean class loader. */
	private ClassLoader	beanClassLoader		= ReflectUtils
													.getDefaultClassLoader();

	/** The log. */
	protected final Log	log					= LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#getServiceInterface
	 * ()
	 */
	public Class<?> getServiceInterface() {

		return serviceInterface;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#setServiceInterface
	 * (java.lang.Class)
	 */
	public void setServiceInterface(Class<?> serviceInterface) {

		this.serviceInterface = serviceInterface;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#getServiceObject
	 * ()
	 */
	public Object getServiceObject() {

		return serviceObject;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#setServiceObject
	 * (java.lang.Object)
	 */
	public void setServiceObject(Object serviceObject) {

		this.serviceObject = serviceObject;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#getBeanClassLoader
	 * ()
	 */
	public ClassLoader getBeanClassLoader() {

		return beanClassLoader;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#setBeanClassLoader
	 * (java.lang.ClassLoader)
	 */
	public void setBeanClassLoader(ClassLoader beanClassLoader) {

		this.beanClassLoader = beanClassLoader;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.integration.remoting.RemoteExporter#
	 * setDefaultBeanClassLoader()
	 */
	public void setDefaultBeanClassLoader() {

		this.beanClassLoader = ReflectUtils.getDefaultClassLoader();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteExporter#checkService()
	 */
	public void checkService() {

		if (this.serviceInterface == null || this.serviceObject == null) {
			throw new RemotingException(
					"Service interface or service object must not be null !");
		}
	}
}
