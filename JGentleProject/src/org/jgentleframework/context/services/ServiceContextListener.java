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
package org.jgentleframework.context.services;

/**
 * Represents the Service Listener that allows for custom behaviour when each
 * service is added or removed.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 1, 2007
 * @see ServiceHandler
 */
public interface ServiceContextListener {
	/**
	 * The implementation of this method will be invoked automatically when each
	 * service is added.
	 * 
	 * @param serviceClass
	 *            the corresponding service object class.
	 */
	public void serviceAvailable(Class<?> serviceClass);

	/**
	 * The implementation of this method will be invoked automatically when each
	 * service is revoked.
	 * 
	 * @param serviceClass
	 *            the corresponding service object class.
	 */
	public void serviceRevoked(Class<?> serviceClass);
}
