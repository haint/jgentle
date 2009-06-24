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

import java.util.HashMap;

import org.jgentleframework.services.eventservices.context.EventServiceContext;
import org.jgentleframework.services.eventservices.objectmeta.ISubscriber;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEvent;
import org.jgentleframework.services.eventservices.objectmeta.Subscription;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 28, 2007
 */
public interface EventClass {
	/**
	 * Thực thi fire event
	 * 
	 * @param args
	 *            đối số truyền vào từ publisher.
	 */
	public void fireEvent(final String authenticationCode, final Object... args);

	/**
	 * Trả về object event của event class hiện hành.
	 * 
	 * @return the event
	 */
	public ObjectEvent getEvent();

	/**
	 * Trả về subscription hiện hành của event
	 * 
	 * @return the subscription
	 */
	public Subscription getSubscription();

	/**
	 * Trả về transient subscription hiện hành của event.
	 * 
	 * @return the transientSubscrip
	 */
	public Subscription getTransientSubscrip();

	/**
	 * Trả về EventServiceContext của event class hiện hành.
	 * 
	 * @return the context
	 */
	public EventServiceContext<?> getContext();

	/**
	 * @return the subscriberList
	 */
	public HashMap<String, ISubscriber> getSubscriberList();

	/**
	 * @param transientSubscrip
	 *            the transientSubscrip to set
	 */
	public void setTransientSubscrip(Subscription transientSubscrip);
}