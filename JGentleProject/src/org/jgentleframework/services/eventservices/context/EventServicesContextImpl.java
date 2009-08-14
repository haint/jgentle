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
package org.jgentleframework.services.eventservices.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jgentleframework.context.ComponentServiceContextType;
import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.services.eventservices.EventClass;
import org.jgentleframework.services.eventservices.EventClassImpl;
import org.jgentleframework.services.eventservices.EventServicesConfig;
import org.jgentleframework.services.eventservices.EventServicesException;
import org.jgentleframework.services.eventservices.ISubscriptionFilter;
import org.jgentleframework.services.eventservices.SubscriberQueuedThread;
import org.jgentleframework.services.eventservices.annotation.Subscriber;
import org.jgentleframework.services.eventservices.objectmeta.ISubscriber;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEvent;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxy;
import org.jgentleframework.services.eventservices.objectmeta.SubscriberImpl;
import org.jgentleframework.services.eventservices.objectmeta.Subscription;

/**
 * Một cài đặt của <code>interface EventServicesContex</code>t, chịu trách nhiệm
 * quản lý, thực thi các <code>bean</code> có chức năng <code>fire</code> và
 * <code>receive event</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 * @see EventServiceContext
 * @see ComponentServiceContextType
 */
public class EventServicesContextImpl<T extends EventServicesConfig> implements
		ComponentServiceContextType<T>, EventServiceContext<T> {
	private ServiceHandler			serviceHandler;

	/**
	 * Danh sách các config instances tương ứng của EventServicesContext hiện
	 * đang nắm giữ.
	 */
	private ArrayList<T>			configInstances;

	/**
	 * Đối tượng Definition Manager
	 */
	private DefinitionManager		defManager;

	/**
	 * Danh sách các event đã được cấu hình.
	 */
	HashMap<String, EventClass>		eventClassList	= new HashMap<String, EventClass>();

	/**
	 * Danh sách các event proxy đã được cấu hình.
	 */
	ArrayList<ObjectEventProxy>		eventProxyList	= new ArrayList<ObjectEventProxy>();

	/**
	 * Đối tượng {@link Provider}
	 */
	private Provider				provider;

	/**
	 * Danh sách các subscribers đã được cấu hình.
	 */
	HashMap<String, ISubscriber>	subscriberList	= new HashMap<String, ISubscriber>();

	/**
	 * @param eventList
	 * @param eventProxyList
	 * @param subscriberList
	 * @param provider
	 */
	public EventServicesContextImpl(ArrayList<ObjectEvent> eventList,
			ArrayList<ObjectEventProxy> eventProxyList,
			HashMap<Class<?>, String> subscriberList, Provider provider) {

		this.provider = provider;
		this.serviceHandler = provider.getServiceHandler();
		this.defManager = serviceHandler.getDefinitionManager();
		// Kiểm tra thông tin subscriberList
		this.checkSubscriberList(subscriberList);
		// Kiểm tra thông tin eventList
		this.checkEventList(eventList);
		// Kiểm tra thông tin eventProxyList
		this.checkEventProxyList(eventProxyList);
	}

	/**
	 * Kiểm tra thông tin eventList được truyền vào.
	 * 
	 * @param eventList
	 */
	private void checkEventList(ArrayList<ObjectEvent> eventList) {

		for (ObjectEvent obj : eventList) {
			String name = obj.getName();
			Subscription subscription = obj.getSubscription();
			if (this.eventClassList.containsKey(name)) {
				throw new EventServicesException("This " + name
						+ " name of event is existed.");
			}
			if (subscription == null) {
				throw new EventServicesException("Subscription of " + name
						+ " event must be not null.");
			}
			// Kiểm tra thông tin subscription tương ứng với filter và
			// subscriber name.
			String filterStr = subscription.getFilterString();
			if ((filterStr == null || filterStr.isEmpty())
					&& subscription.getFilter() == null) {
				if (subscription.getSubscriberNames().size() == 0) {
					throw new EventServicesException(
							"Subscriber names must be defined in subscription if subscription filter is not defined.");
				}
			}
			// Xử lý chuỗi filter string
			if (filterStr != null && !filterStr.isEmpty()) {
				// Nếu filter string chỉ định tường minh 1 class name
				if (filterStr.indexOf(" ") != -1) {
					String[] strs = filterStr.split(" ");
					if (!strs[0].equals("Class")) {
						throw new EventServicesException("Filter string "
								+ filterStr + " is invalid.");
					}
					subscription.setFilterString(strs[1].trim());
					Class<?> clazz = null;
					try {
						clazz = Class.forName(subscription.getFilterString());
					}
					catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					subscription.setFilter((ISubscriptionFilter) this.provider
							.getBean(clazz));
				}
				// Nếu filter string chỉ định một mapping bean.
				if (filterStr.indexOf(":") != -1) {
					ISubscriptionFilter result = null;
					result = (ISubscriptionFilter) provider.getBean(filterStr);
					// if (values[0].equals(Configurable.REF_CONSTANT)) {
					// result = (ISubscriptionFilter) InOutExecutor
					// .getFromMapDirectList(this.provider, values[1]);
					// }
					// else if (values[0].equals(Configurable.REF_MAPPING)) {
					// result = (ISubscriptionFilter) InOutExecutor
					// .getFromAliasMap(this.provider, values[1]);
					// }
					// else if (values[0].equals(Configurable.REF_ID)) {
					// Definition def = this.defManager
					// .getDefinition(values[1]);
					// result = (ISubscriptionFilter) this.provider.getBean(
					// (Class<?>) def.getKey(), values[1]);
					// }
					// else {
					// throw new InOutDependencyException("Filter string "
					// + filterStr + " is invalid.");
					// }
					subscription.setFilter(result);
				}
			}
			// add thông tin event vào event list.
			EventClass event = new EventClassImpl(obj, this);
			this.eventClassList.put(name, event);
		}
	}

	/**
	 * Kiểm tra thông tin event proxy list được truyền vào.
	 * 
	 * @param eventProxyList
	 *            danh sách các object event proxy.
	 */
	private void checkEventProxyList(ArrayList<ObjectEventProxy> eventProxyList) {

	// TODO checkEventProxyList
	}

	/**
	 * Kiểm tra thông tin subscriberList được truyền vào.
	 * 
	 * @param subscriberList
	 */
	private void checkSubscriberList(HashMap<Class<?>, String> subscriberList) {

		for (Entry<Class<?>, String> etr : subscriberList.entrySet()) {
			Class<?> clazz = etr.getKey();
			String ID = subscriberList.get(clazz);
			// Nếu ID được chỉ định tường minh.
			if (ID != null && !ID.isEmpty()) {
				String name = null;
				Definition def = null;
				Method method = null;
				Object source = null;
				if (!this.defManager.containsDefinition(ID)) {
					throw new EventServicesException("Defnition with ID " + ID
							+ " is not existed.");
				}
				for (Entry<Method, Definition> entry : this.defManager
						.getDefinition(ID).getMethodDefList().entrySet()) {
					def = entry.getValue();
					if (def.isAnnotationPresent(Subscriber.class)) {
						Subscriber anno = def.getAnnotation(Subscriber.class);
						name = anno.value();
						method = entry.getKey();
						// if (source == null) {
						// source = this.provider.getBean(clazz, ID);
						// }
						ISubscriber subscriber = new SubscriberImpl(name,
								method, source, def);
						if (this.subscriberList.containsKey(name)) {
							throw new EventServicesException("Subscriber name "
									+ name + " is existed.");
						}
						if (anno.queued() == true) {
							subscriber
									.setQueueThread(new SubscriberQueuedThread(
											subscriber));
							Thread thread = new Thread(subscriber
									.getQueueThread());
							thread.start();
						}
						this.subscriberList.put(name, subscriber);
					}
				}
			}
			// Nếu ID không được chỉ định tường minh.
			else {
				// Nạp thông tin definition của clazz
				if (!this.defManager.containsDefinition(clazz)) {
					this.defManager.loadDefinition(clazz);
				}
				String name = null;
				Definition def = null;
				Method method = null;
				Object source = null;
				for (Entry<Method, Definition> entry : this.defManager
						.getDefinition(clazz).getMethodDefList().entrySet()) {
					def = entry.getValue();
					if (def.isAnnotationPresent(Subscriber.class)) {
						Subscriber anno = def.getAnnotation(Subscriber.class);
						name = anno.value();
						method = entry.getKey();
						if (source == null) {
							source = this.provider.getBean(clazz);
						}
						ISubscriber subscriber = new SubscriberImpl(name,
								method, source, def);
						if (this.subscriberList.containsKey(name)) {
							throw new EventServicesException("Subscriber "
									+ name + " is existed.");
						}
						if (anno.queued() == true) {
							subscriber
									.setQueueThread(new SubscriberQueuedThread(
											subscriber));
							Thread thread = new Thread(subscriber
									.getQueueThread());
							thread.start();
						}
						this.subscriberList.put(name, subscriber);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #containsEvent(java.lang.String)
	 */
	@Override
	public boolean containsEvent(String name) {

		return this.eventClassList.containsKey(name);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getAoh()
	 */
	public ServiceHandler getAoh() {

		return serviceHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getConfigInstances()
	 */
	@Override
	public ArrayList<T> getConfigInstances() {

		return configInstances;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getDefManager()
	 */
	public DefinitionManager getDefManager() {

		return defManager;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getEvent(java.lang.String)
	 */
	@Override
	public EventClass getEvent(String name) {

		if (!this.eventClassList.containsKey(name)) {
			throw new EventServicesException(
					"Does not found any event with name '" + name + "'");
		}
		return this.eventClassList.get(name);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getEventClassList()
	 */
	@Override
	public HashMap<String, EventClass> getEventClassList() {

		return eventClassList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getEventProxyList()
	 */
	public ArrayList<ObjectEventProxy> getEventProxyList() {

		return eventProxyList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventservices.context.EventServiceContext
	 * #getProvider()
	 */
	@Override
	public Provider getProvider() {

		return provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getObjEventList()
	 */
	public HashMap<String, EventClass> getObjEventList() {

		return this.eventClassList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.context.EventServiceContext
	 * #getSubscriberList()
	 */
	public HashMap<String, ISubscriber> getSubscriberList() {

		return subscriberList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.ComponentServiceContext#init(org.exxlabs.
	 * jgentle.context.ServiceProvider, T[])
	 */
	@Override
	public void init(ServiceProvider serviceProvider,
			ArrayList<T> configInstances) {

		this.provider = serviceProvider;
		this.configInstances = configInstances;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> returnClassType() {

		return (Class<T>) EventServicesConfig.class;
	}
}
