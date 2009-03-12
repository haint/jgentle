/*
 * Copyright 2007-2008 the original author or authors.
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
package org.jgentleframework.core.provider;

import java.util.Iterator;


/**
 * Định nghĩa các phương thức bắt sự kiện của một service class object bên trong
 * 1 service provider (domain).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 13, 2007
 */
public interface DomainListener {
	/**
	 * Nhận sự kiện khi có một serviceClass được add vào service provider hiện
	 * hành đang cất giữ service bean tương ứng.
	 * 
	 * @param childrenAffected
	 *            các đối tượng child bị tác động (added) khi sự kiện
	 *            childrenAdded được ném ra.
	 * @param domain
	 *            domain (service provider) đang cất giữ service bean hiện hành.
	 * @param objBeanService
	 *            đối tượng ObjectBeanService đại diện cho service bean hiện
	 *            hành.
	 */
	public void childrenAdded(Iterator<?> childrenAffected,
			Domain domain, ObjectBeanService objBeanService);

	/**
	 * Nhận sự kiện khi có một serviceClass bị remove khỏi service provider hiện
	 * hành đang cất giữ service bean tương ứng.
	 * 
	 * @param childrenAffected
	 *            các đối tượng child bị tác động (removed) khi sự kiện
	 *            childrenAdded được ném ra.
	 * @param domain
	 *            domain (service provider) đang cất giữ service bean hiện hành.
	 * @param objBeanService
	 *            đối tượng ObjectBeanService đại diện cho service bean hiện
	 *            hành.
	 */
	public void childrenRemoved(Iterator<?> childrenAffected,
			Domain domain, ObjectBeanService objBeanService);
}
