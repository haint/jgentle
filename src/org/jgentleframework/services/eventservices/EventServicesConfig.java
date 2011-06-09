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

import java.util.List;
import java.util.Map;

import org.jgentleframework.services.eventservices.objectmeta.ObjectEvent;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxy;
import org.jgentleframework.services.eventservices.objectmeta.ObjectEventProxyImpl;

/**
 * Interface này chỉ định các methods cho phép cấu hình các thông tin cho Event
 * Services hoạt động.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 16, 2008
 */
public interface EventServicesConfig {
	/**
	 * Khởi tạo mới 1 event.
	 * 
	 * @param name
	 *            tên định danh của event.
	 * @return trả về một ObjectEventImpl định nghĩa thông tin cấu hình của
	 *         event.
	 */
	public ObjectEvent newEvent(String name);

	/**
	 * Khởi tạo mới 1 event proxy.
	 * 
	 * @param name
	 *            tên định danh của event
	 * @return trả về một ObjectEventProxyImpl định nghĩa thông tin cấu hình của
	 *         event proxy.
	 */
	public ObjectEventProxy newEventProxy(String name);

	/**
	 * Add thông tin một hoặc nhiều subscribers.
	 * 
	 * @param classes
	 *            đối tượng object classes của các subscribers chỉ định.
	 */
	public void addSubscribers(Class<?>... classes);

	/**
	 * Add thông tin một subscriber.
	 * 
	 * @param clazz
	 *            đối tượng object class của subscriber.
	 * @param ID
	 *            tên định danh của definition tương ứng.
	 */
	public void addSubscriber(Class<?> clazz, String ID);

	/**
	 * Gỡ bỏ một thông tin subscriber đã được cấu hình.
	 * 
	 * @param clazz
	 *            đối tượng object class của subscriber.
	 */
	public void removeSubscriber(Class<?> clazz);

	/**
	 * Gỡ bỏ toàn bộ thông tin subscriber đã được cấu hình.
	 */
	public void clearSubscribers();

	/**
	 * Trả về danh sách event list đã được cấu hình.
	 * 
	 * @return trả về danh sách các event nếu có, nếu không trả về 1 danh sách
	 *         rỗng.
	 */
	public List<ObjectEvent> getEventList();

	/**
	 * Trả về danh sách event proxy list đã được cấu hình.
	 * 
	 * @return trả về danh sách các event proxy nếu có, nếu không trả về 1 danh
	 *         sách rỗng.
	 */
	public List<ObjectEventProxyImpl> getEventProxyList();

	/**
	 * Trả về danh sách các object class của các subscribers được chỉ định.
	 */
	public Map<Class<?>, String> getSubscribersList();
}