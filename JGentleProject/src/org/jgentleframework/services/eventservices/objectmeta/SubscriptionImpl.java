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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.jgentleframework.services.eventservices.EventClass;
import org.jgentleframework.services.eventservices.EventServicesException;
import org.jgentleframework.services.eventservices.ISubscriptionFilter;

/**
 * Chỉ định thông tin cấu hình một Subscription.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 * @see ObjectEventImpl
 */
public class SubscriptionImpl implements Subscription, Observer {
	// Danh sách chứa đựng các tên định danh của các subscribers.
	ArrayList<String>	subscriberNames	= new ArrayList<String>();
	// Đối tượng filter của subscription hiện hành.
	ISubscriptionFilter	filter			= null;
	// Chuỗi String mô tả thông tin của SubscriptionFilter
	String				filterString	= null;
	boolean				inParallel		= false;

	/**
	 * Constructor
	 * 
	 * @param subscriberNames
	 *            danh sách tên định danh các subscribers cần khởi tạo.
	 */
	public SubscriptionImpl(boolean inParallel, String... subscriberNames) {

		this.inParallel = inParallel;
		for (String name : subscriberNames) {
			if (this.subscriberNames.contains(name)) {
				throw new EventServicesException("This name: " + name
						+ " is existed.");
			}
			this.subscriberNames.add(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#withFilter(java.lang.Class)
	 */
	@Override
	public void withFilter(Class<?> filterClass) {

		setFilter(null);
		this.filterString = filterClass.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#withFilter(java.lang.String)
	 */
	@Override
	public void withFilter(String filter) {

		setFilter(null);
		if (filter.equals("RefMappingBean")) {
			throw new EventServicesException(
					"'withFilter method' is not compatible with 'refMapping method'");
		}
		this.filterString = filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#getSubscriberNames()
	 */
	@Override
	public ArrayList<String> getSubscriberNames() {

		return subscriberNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#getFilter()
	 */
	@Override
	public ISubscriptionFilter getFilter() {

		return filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#setFilter(org.jgentleframework.services.eventServices.ISubscriptionFilter)
	 */
	@Override
	public void setFilter(ISubscriptionFilter filter) {

		this.filterString = "";
		this.filter = filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#getFilterString()
	 */
	@Override
	public String getFilterString() {

		return filterString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#setFilterString(java.lang.String)
	 */
	@Override
	public void setFilterString(String filterString) {

		this.filterString = filterString;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (this.filter != null) {
			if (arg.getClass().isArray()) {
				Object[] args = new Object[Array.getLength(arg)];
				for (int i = 0; i < args.length; i++) {
					args[i] = Array.get(arg, i);
				}
				((EventClass) o).setTransientSubscrip(this.filter.filter(
						(EventClass) o, this, args));
			}
			else {
				((EventClass) o).setTransientSubscrip(this.filter.filter(
						(EventClass) o, this, arg));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#isInParallel()
	 */
	@Override
	public boolean isInParallel() {

		return inParallel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscription#setInParallel(boolean)
	 */
	@Override
	public void setInParallel(boolean inParallel) {

		this.inParallel = inParallel;
	}
}
