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
package org.jgentleframework.services.eventservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgentleframework.services.eventservices.objectmeta.ObjectEvent;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventImpl;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxy;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxyImpl;

/**
 * Chỉ định các method sẽ được dùng để cấu hình event services.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public class EventServicesConfigImpl implements EventServicesConfig {
	/**
	 * Danh sách các event đã được cấu hình.
	 */
	List<ObjectEvent>			eventList		= new ArrayList<ObjectEvent>();

	/**
	 * Danh sách các event proxy đã được cấu hình.
	 */
	List<ObjectEventProxyImpl>	eventProxyList	= new ArrayList<ObjectEventProxyImpl>();

	/**
	 * Danh sách các subscribers đã được cấu hình với key là đối tượng object
	 * class chứa thông tin subscriber, còn value chính là ID chỉ định
	 * definition của subscriber tương ứng. Nếu object class của subscriber có
	 * chứa value tương ứng là null hoặc rỗng, mặc định container sẽ tìm thông
	 * tin definition trong object class chỉ định.
	 */
	Map<Class<?>, String>		subscriberList	= new HashMap<Class<?>, String>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventServicesConfig#newEvent
	 * (java.lang.String)
	 */
	public ObjectEvent newEvent(String name) {

		ObjectEvent event = new ObjectEventImpl(name);
		this.eventList.add(event);
		return event;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventServicesConfig#newEventProxy
	 * (java.lang.String)
	 */
	public ObjectEventProxy newEventProxy(String name) {

		ObjectEventProxyImpl eventProxy = new ObjectEventProxyImpl(name);
		this.eventProxyList.add(eventProxy);
		return eventProxy;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.eventServices.EventServicesConfig#
	 * addSubscribers(java.lang.Class)
	 */
	public void addSubscribers(Class<?>... classes) {

		for (Class<?> obj : classes) {
			if (this.subscriberList.containsKey(obj)) {
				throw new EventServicesException(
						"This subscriber with object class " + obj.getName()
								+ " is existed.");
			}
			this.subscriberList.put(obj, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventServicesConfig#addSubscriber
	 * (java.lang.Class, java.lang.String)
	 */
	public void addSubscriber(Class<?> clazz, String ID) {

		if (this.subscriberList.containsKey(clazz)) {
			throw new EventServicesException(
					"This subscriber with object class " + clazz.getName()
							+ " is existed.");
		}
		this.subscriberList.put(clazz, ID);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.eventServices.EventServicesConfig#
	 * removeSubscriber(java.lang.Class)
	 */
	public void removeSubscriber(Class<?> clazz) {

		if (!this.subscriberList.containsKey(clazz)) {
			throw new EventServicesException(
					"This subscriber with object class " + clazz.getName()
							+ " is not existed.");
		}
		this.subscriberList.remove(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.eventServices.EventServicesConfig#
	 * clearSubscribers()
	 */
	public void clearSubscribers() {

		this.subscriberList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventServicesConfig#getEventList
	 * ()
	 */
	public List<ObjectEvent> getEventList() {

		return eventList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.eventServices.EventServicesConfig#
	 * getEventProxyList()
	 */
	public List<ObjectEventProxyImpl> getEventProxyList() {

		return eventProxyList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.eventServices.EventServicesConfig#
	 * getSubscribersList()
	 */
	public Map<Class<?>, String> getSubscribersList() {

		return subscriberList;
	}
}
