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

import java.util.ArrayList;

import org.jgentleframework.services.eventservices.ISubscriptionFilter;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public interface Subscription {
	/**
	 * Chỉ định filter cho subscription hiện hành.
	 * 
	 * @param filterClass
	 *            đối tượng object class tương ứng được mapping ứng với instance
	 *            class của filter chỉ định.
	 */
	public void withFilter(Class<?> filterClass);

	/**
	 * Chỉ định filter cho subscription hiện hành.
	 * 
	 * @param filter
	 */
	public void withFilter(String filter);

	/**
	 * Trả về danh sách các tên định của các subscribers được cấu hình trong
	 * subscription hiện hành.
	 * 
	 * @return the subscriberNames
	 */
	public ArrayList<String> getSubscriberNames();

	/**
	 * Trả về đối tượng filter của subscription hiện hành.
	 */
	public ISubscriptionFilter getFilter();

	/**
	 * Thiết lập filter cho subscription hiện hành.
	 * 
	 * @param filter
	 *            đối tượng filter cần thiết lập.
	 */
	public void setFilter(ISubscriptionFilter filter);

	/**
	 * Trả về chuỗi String định danh Filter.
	 * 
	 * @return the filterString
	 */
	public String getFilterString();

	/**
	 * Thiết lập chuỗi String định danh Filter
	 * 
	 * @param filterString
	 *            chuỗi string định danh filter cần thiết lập.
	 */
	public void setFilterString(String filterString);

	/**
	 * Trả về cách thức thực thi subscriber trong subscription hiện hành.
	 * 
	 * @return trả về true nếu các subscribers được chỉ định thực thi cùng lúc,
	 *         ngược lại trả về false nếu các subscribers được chỉ định thực thi
	 *         tuần tự.
	 */
	public boolean isInParallel();

	/**
	 * Thiết lập cách thực thi subscribers trong subscription hiện hành.
	 * 
	 * @param inParallel
	 *            giá trị bool cần thiết lập, nếu là true các subscribers sẽ
	 *            được chỉ định thực thi cùng lúc, nếu là false các subscribers
	 *            sẽ được chỉ định thực thi tuần tự.
	 */
	public void setInParallel(boolean inParallel);
}