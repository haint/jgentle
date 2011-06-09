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
package org.jgentleframework.services.eventservices.objectmeta;

import org.jgentleframework.utils.Assertor;

/**
 * Lưu trữ thông tin cấu hình của một event.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public class ObjectEventImpl implements ObjectEvent {
	/**
	 * Tên định danh của event
	 */
	String			name;
	/**
	 * Subscription của event hiện hành.
	 */
	Subscription	subscription	= null;

	/**
	 * Hàm khởi tạo.
	 * 
	 * @param name
	 *            tên định danh của event.
	 */
	public ObjectEventImpl(String name) {

		Assertor.notNull(name, "Name must be not null.");
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEvent#setSubscription(java.lang.String)
	 */
	@Override
	public Subscription setSubscription(String... subscriberNames) {

		return this.setSubscription(false, subscriberNames);
	}

	@Override
	public Subscription setSubscription(boolean inParallel,
			String... subscriberNames) {

		Subscription obj = new SubscriptionImpl(inParallel, subscriberNames);
		this.subscription = obj;
		return this.subscription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEvent#getName()
	 */
	public String getName() {

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEvent#setName(java.lang.String)
	 */
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEvent#getSubscription()
	 */
	public Subscription getSubscription() {

		return subscription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEvent#setSubscription(org.jgentleframework.services.eventServices.objectmeta.SubscriptionImpl)
	 */
	public void setSubscription(Subscription subscription) {

		this.subscription = subscription;
	}
}
