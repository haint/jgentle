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
 * Chỉ định một Remote Binder.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 18, 2008
 */
public abstract class RemoteAbstractBinder {
	/**
	 * Đối tượng <code>object class</code> của <code>service interface</code>.
	 */
	protected Class<?>	serviceInterface	= null;

	/**
	 * Returns the <code>object class</code> of current
	 * <code>service interface</code>.
	 * 
	 * @return the serviceInterface
	 */
	public Class<?> getServiceInterface() {

		return serviceInterface;
	}

	/**
	 * Sets the service interface.
	 * 
	 * @param serviceInterface
	 *            the service interface
	 */
	public void setServiceInterface(Class<?> serviceInterface) {

		this.serviceInterface = serviceInterface;
	}
}
