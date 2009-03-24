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

import java.beans.beancontext.BeanContextMembershipEvent;
import java.beans.beancontext.BeanContextMembershipListener;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jgentleframework.core.provider.Domain;

/**
 * Represents the Domain Listener that allows for custom behaviour when each
 * {@link Domain} is added or removed.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 1, 2007
 * @see DomainContextListener
 */
public class DomainListener implements BeanContextMembershipListener {
	/** The listener list. */
	HashMap<String, DomainContextListener>	listenerList	= new HashMap<String, DomainContextListener>();

	/**
	 * Action to do.
	 * 
	 * @param bcme
	 *            the bcme
	 * @param number
	 *            the number
	 */
	private void actionToDo(BeanContextMembershipEvent bcme, int number) {

		for (DomainContextListener obj : listenerList.values()) {
			switch (number) {
			case 1:
				obj.childrenAdded(bcme.iterator());
				break;
			case 2:
				obj.childrenRemoved(bcme.iterator());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Adds the listener.
	 * 
	 * @param id
	 *            the id
	 * @param listener
	 *            the listener
	 * @return {@link DomainContextListener}
	 */
	public DomainContextListener addListener(String id,
			DomainContextListener listener) {

		return this.listenerList.put(id, listener);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextMembershipListener#childrenAdded(java
	 * .beans.beancontext.BeanContextMembershipEvent)
	 */
	@Override
	public void childrenAdded(BeanContextMembershipEvent bcme) {

		synchronized (bcme) {
			actionToDo(bcme, 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextMembershipListener#childrenRemoved(
	 * java.beans.beancontext.BeanContextMembershipEvent)
	 */
	@Override
	public void childrenRemoved(BeanContextMembershipEvent bcme) {

		synchronized (bcme) {
			actionToDo(bcme, 2);
		}
	}

	/**
	 * Removes the listener.
	 * 
	 * @param listener
	 *            the listener
	 * @return {@link DomainContextListener}
	 */
	public DomainContextListener removeListener(DomainContextListener listener) {

		for (Entry<String, DomainContextListener> obj : this.listenerList
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
	 * @return {@link DomainContextListener}
	 */
	public DomainContextListener removeListener(String id) {

		return this.listenerList.remove(id);
	}
}