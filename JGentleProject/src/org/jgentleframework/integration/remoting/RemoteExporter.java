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
package org.jgentleframework.integration.remoting;

/**
 * @author Quoc Chung - mailto: <a href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 10, 2009
 */
public interface RemoteExporter {
	/**
	 * Returns the service interface of current remote service.
	 * 
	 * @return the service interface
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
	 * Returns the service object of current remote service.
	 * 
	 * @return the serviceObject
	 */
	public Object getServiceObject();

	/**
	 * Sets the service object.
	 * 
	 * @param serviceObject
	 *            the service object
	 */
	public void setServiceObject(Object serviceObject);

	/**
	 * Returns the current bean {@link ClassLoader} of current remote service.
	 * 
	 * @return ClassLoader
	 */
	public ClassLoader getBeanClassLoader();

	/**
	 * Sets the bean class loader.
	 * 
	 * @param beanClassLoader
	 *            the bean class loader
	 */
	public void setBeanClassLoader(ClassLoader beanClassLoader);

	/**
	 * Sets default bean {@link ClassLoader}.
	 */
	public void setDefaultBeanClassLoader();

	/**
	 * Check service.
	 */
	public void checkService();
}