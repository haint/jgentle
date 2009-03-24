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
package org.jgentleframework.context.services;

import java.beans.beancontext.BeanContextServiceAvailableEvent;
import java.beans.beancontext.BeanContextServiceRevokedEvent;
import java.beans.beancontext.BeanContextServicesListener;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * The Class ServiceBindingListener.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 18, 2007
 * @see ServiceContextListener
 */
public class ServiceBindingListener implements BeanContextServicesListener {
	/** The listener list. */
	HashMap<String, ServiceContextListener>	listenerList	= new HashMap<String, ServiceContextListener>();

	/**
	 * Action to do.
	 * 
	 * @param bcsae
	 *            the bcsae
	 */
	private void actionToDo(BeanContextServiceAvailableEvent bcsae) {

		for (ServiceContextListener obj : this.listenerList.values()) {
			obj.serviceAvailable(bcsae.getServiceClass());
		}
	}

	/**
	 * Action to do.
	 * 
	 * @param bcsre
	 *            the bcsre
	 */
	private void actionToDo(BeanContextServiceRevokedEvent bcsre) {

		for (ServiceContextListener obj : this.listenerList.values()) {
			obj.serviceRevoked(bcsre.getServiceClass());
		}
	}

	/**
	 * Adds the listener.
	 * 
	 * @param id
	 *            the id
	 * @param listener
	 *            the listener
	 * @return {@link ServiceContextListener}
	 */
	public synchronized ServiceContextListener addListener(String id,
			ServiceContextListener listener) {

		return this.listenerList.put(id, listener);
	}

	/**
	 * Removes the listener.
	 * 
	 * @param listener
	 *            the listener
	 * @return {@link ServiceContextListener}
	 */
	public synchronized ServiceContextListener removeListener(
			ServiceContextListener listener) {

		for (Entry<String, ServiceContextListener> obj : this.listenerList
				.entrySet()) {
			if (obj.getValue() == listener) {
				return this.listenerList.remove(obj.getKey());
			}
		}
		return null;
	}

	/**
	 * Removes the listener.
	 * 
	 * @param id
	 *            the id
	 * @return {@link ServiceContextListener}
	 */
	public synchronized ServiceContextListener removeListener(String id) {

		return this.listenerList.remove(id);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextServicesListener#serviceAvailable(java
	 * .beans.beancontext.BeanContextServiceAvailableEvent)
	 */
	@Override
	public void serviceAvailable(BeanContextServiceAvailableEvent bcsae) {

		synchronized (bcsae) {
			actionToDo(bcsae);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextServiceRevokedListener#serviceRevoked
	 * (java.beans.beancontext.BeanContextServiceRevokedEvent)
	 */
	@Override
	public void serviceRevoked(BeanContextServiceRevokedEvent bcsre) {

		synchronized (bcsre) {
			actionToDo(bcsre);
		}
	}
}
