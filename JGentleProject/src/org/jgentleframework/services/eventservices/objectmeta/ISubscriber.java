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

import java.lang.reflect.Method;

import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.services.eventservices.SubscriberQueuedThread;

public interface ISubscriber {
	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name);

	/**
	 * @return the subscriberMethod
	 */
	public Method getSubscriberMethod();

	/**
	 * @param subscriberMethod
	 *            the subscriberMethod to set
	 */
	public void setSubscriberMethod(Method subscriberMethod);

	/**
	 * @return the source
	 */
	public Object getSource();

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Object source);

	/**
	 * @return the definition
	 */
	public Definition getDefinition();

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(Definition definition);

	/**
	 * Trả về đối tượng thực thi xử lý các event message được gửi tới queue của
	 * subscriber nếu subscriber được chỉ định có sử dụng queue.
	 * 
	 * @return trả về đối tượng SubscriberQueuedThread nếu có, nếu không trả về
	 *         null.
	 */
	public SubscriberQueuedThread getQueueThread();

	/**
	 * Thiết lập đối tượng thực thi xử lý các event message được gửi tới queue
	 * của subscriber nếu subscriber được chỉ định có sử dụng queue.
	 * 
	 * @param queueThread
	 *            đối tượng SubscriberQueuedThread cần thiết lập.
	 */
	public void setQueueThread(SubscriberQueuedThread queueThread);
}