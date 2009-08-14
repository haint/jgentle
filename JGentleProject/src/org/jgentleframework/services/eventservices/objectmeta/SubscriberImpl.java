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

import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.services.eventservices.SubscriberQueuedThread;

/**
 * Đối tượng chứa đựng thông tin một subscriber.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public class SubscriberImpl implements ISubscriber {
	/**
	 * Tên định danh của subscriber.
	 */
	String							name;
	/**
	 * method chỉ định của subscriber.
	 */
	Method							subscriberMethod;
	/**
	 * Đối tượng object source của subscriber.
	 */
	Object							source;
	/**
	 * Definition của subscriberMethod.
	 */
	Definition						definition;
	/**
	 * Đối tượng thực thi nhận các event được gửi đến subscriber thông qua
	 * queue.
	 */
	private SubscriberQueuedThread	queueThread	= null;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            tên định danh của subscriber
	 * @param subscriberMethod
	 *            method chỉ định của subscriber.
	 * @param source
	 *            Đối tượng object source của subscriber.
	 * @param def
	 *            Definition của subscriberMethod.
	 */
	public SubscriberImpl(String name, Method subscriberMethod, Object source,
			Definition def) {

		this.name = name;
		this.subscriberMethod = subscriberMethod;
		this.source = source;
		this.definition = def;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#getName()
	 */
	public String getName() {

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#setName(java.lang.String)
	 */
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#getSubscriberMethod()
	 */
	public Method getSubscriberMethod() {

		return subscriberMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#setSubscriberMethod(java.lang.reflect.Method)
	 */
	public void setSubscriberMethod(Method subscriberMethod) {

		this.subscriberMethod = subscriberMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#getSource()
	 */
	public Object getSource() {

		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#setSource(java.lang.Object)
	 */
	public void setSource(Object source) {

		this.source = source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#getDefinition()
	 */
	public Definition getDefinition() {

		return definition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.Subscriber#setDefinition(org.jgentleframework.core.reflection.metadata.Definition)
	 */
	public void setDefinition(Definition definition) {

		this.definition = definition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ISubscriber#getQueueThread()
	 */
	@Override
	public SubscriberQueuedThread getQueueThread() {

		return queueThread;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ISubscriber#setQueueThread(org.jgentleframework.services.eventServices.SubscriberQueuedThread)
	 */
	@Override
	public void setQueueThread(SubscriberQueuedThread queueThread) {

		this.queueThread = queueThread;
	}
}
