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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.jgentleframework.services.eventservices.annotation.Subscriber;
import org.jgentleframework.services.eventservices.context.EventServiceContext;
import org.jgentleframework.services.eventservices.objectmeta.ISubscriber;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEvent;
import org.jgentleframework.services.eventservices.objectmeta.Subscription;

/**
 * Event class là một cài đặt của interface EventClass thể hiện đối tượng
 * EventClass của một event chỉ định.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 28, 2007
 */
public class EventClassImpl extends Observable implements EventClass {
	EventServiceContext<?>			context				= null;

	HashMap<String, ISubscriber>	subscriberList		= null;

	ObjectEvent						event				= null;

	Subscription					subscription		= null;

	Subscription					transientSubscrip	= null;

	/**
	 * @param event
	 * @param context
	 */
	public EventClassImpl(ObjectEvent event, EventServiceContext<?> context) {

		this.event = event;
		this.subscription = event.getSubscription();
		this.addObserver((Observer) this.subscription);
		this.context = context;
		this.subscriberList = context.getSubscriberList();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventClass#fireEvent(java
	 * .lang.String, java.lang.Object)
	 */
	@Override
	public void fireEvent(final String authenticationCode, final Object... args) {

		setChanged();
		notifyObservers(args);
		List<String> names = new ArrayList<String>();
		if (this.transientSubscrip != null) {
			names.addAll(this.transientSubscrip.getSubscriberNames());
		}
		else {
			names.addAll(this.subscription.getSubscriberNames());
		}
		if (this.subscription.isInParallel()) {
			List<ISubscriber> list = new ArrayList<ISubscriber>();
			for (String name : names) {
				if (this.subscriberList.containsKey(name)) {
					list.add(this.subscriberList.get(name));
				}
				else {
					throw new EventServicesException(
							"Does not found subscriber '" + name
									+ "' in subscriber List.");
				}
			}
			for (final ISubscriber subscriber : list) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

						try {
							executeSubscriber(subscriber, authenticationCode,
									args);
						}
						catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
						catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				});
				thread.start();
			}
		}
		else {
			for (String name : names) {
				if (this.subscriberList.containsKey(name)) {
					ISubscriber subscriber = this.subscriberList.get(name);
					try {
						executeSubscriber(subscriber, authenticationCode, args);
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
					catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Thực thi subscriber.
	 * 
	 * @param subscriber
	 *            đối tượng subscriber chỉ định cần thực thi.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected static void executeSubscriber(ISubscriber subscriber,
			String authenticationCode, Object... args)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Subscriber anno = subscriber.getDefinition().getAnnotation(
				Subscriber.class);
		Method method = subscriber.getSubscriberMethod();
		Object source = subscriber.getSource();
		if (anno.queued()) {
			SubscriberMessages mess = new SubscriberMessages(
					authenticationCode, args);
			if (subscriber.getQueueThread() == null) {
				throw new EventServicesException(
						"Message Receiver of this subscriber "
								+ subscriber.getName()
								+ " is null or just is removed.");
			}
			subscriber.getQueueThread().addMessage(mess);
		}
		else {
			List<String> authenticationCodes = Arrays.asList(anno
					.authenticationCode());
			method.setAccessible(true);
			if (authenticationCodes.size() == 1
					&& authenticationCodes.get(0).isEmpty()) {
				method.invoke(source, args);
			}
			else {
				if (authenticationCodes.contains(authenticationCode)) {
					method.invoke(source, args);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.eventServices.EventClass#getEvent()
	 */
	public ObjectEvent getEvent() {

		return event;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventClass#getSubscription()
	 */
	public Subscription getSubscription() {

		return subscription;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventClass#getTransientSubscrip
	 * ()
	 */
	public Subscription getTransientSubscrip() {

		return transientSubscrip;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.eventServices.EventClass#getContext()
	 */
	public EventServiceContext<?> getContext() {

		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventClass#getSubscriberList
	 * ()
	 */
	public HashMap<String, ISubscriber> getSubscriberList() {

		return subscriberList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.eventServices.EventClass#setTransientSubscrip
	 * (org.jgentleframework.services.eventServices.objectmeta.Subscription)
	 */
	public void setTransientSubscrip(Subscription transientSubscrip) {

		this.transientSubscrip = transientSubscrip;
	}
}
