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

import java.util.ArrayList;
import java.util.HashMap;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.services.eventservices.EventClass;
import org.jgentleframework.services.eventservices.EventServicesConfig;
import org.jgentleframework.services.eventservices.objectmeta.ISubscriber;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxy;

/**
 * Chịu trách nhiệm quản lý, thực thi các <code>bean</code> có chức năng
 * <code>fire</code> và <code>receive event</code> trong <i>"Event Service"</i>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 29, 2007
 */
public interface EventServiceContext<T extends EventServicesConfig> {
	/**
	 * Trả về danh sách các event list.
	 * 
	 * @return the objEventList
	 */
	public HashMap<String, EventClass> getObjEventList();

	/**
	 * Trả về danh sách event proxy list
	 * 
	 * @return the eventProxyList
	 */
	public ArrayList<ObjectEventProxy> getEventProxyList();

	/**
	 * Trả về danh sách subscriber list.
	 * 
	 * @return the subscriberList
	 */
	public HashMap<String, ISubscriber> getSubscriberList();

	/**
	 * Trả về đối tượng Annotation Object Handler của event service context hiện
	 * hành.
	 * 
	 * @return the aoh
	 */
	public ServiceHandler getAoh();

	/**
	 * Trả về đối tượng Definition Manager của event service context hiện hành.
	 * 
	 * @return the defManager
	 */
	public DefinitionManager getDefManager();

	/**
	 * Trả về đối tượng provider của event service context hiện hành.
	 * 
	 * @return the provider
	 */
	public Provider getProvider();

	/**
	 * Trả về đối tượng Event Class dựa trên tên định danh của event.
	 * 
	 * @param name
	 *            tên định danh của event.
	 * @return trả về Event Class nếu có, nếu không một ngoại lệ sẽ được ném ra
	 *         lúc run-time.
	 */
	public EventClass getEvent(String name);

	/**
	 * Kiểm tra một event có được định nghĩa trong event context hay không.
	 * 
	 * @param name
	 *            tên định danh của event.
	 * @return trả về true nếu có, nếu không trả về false.
	 */
	public boolean containsEvent(String name);

	/**
	 * Tra về danh sách chứa các event class hiện hành.
	 * 
	 * @return the eventClassList
	 */
	public HashMap<String, EventClass> getEventClassList();

	/**
	 * Trả về danh sách configInstances của EventServicesContext hiện hành.
	 * 
	 * @return the configInstances
	 */
	public ArrayList<T> getConfigInstances();
}