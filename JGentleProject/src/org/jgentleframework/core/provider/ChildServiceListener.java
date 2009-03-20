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
 * Represents the Child Service Listener that allows for custom behaviour when
 * each child service of a specified {@link Domain} is added or removed.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 13, 2007
 * @see Domain
 * @see DomainManager
 */
public interface ChildServiceListener {
	/**
	 * The implementation of this method will be invoked automatically when each
	 * service is added to current domain.
	 * 
	 * @param childrenAffected
	 *            all children services are affected by adding operation.
	 * @param domain
	 *            the current domain
	 * @param objBeanService
	 *            the corresponding {@link ObjectBeanService} of adding service.
	 */
	public void childrenAdded(Iterator<?> childrenAffected, Domain domain,
			ObjectBeanService objBeanService);

	/**
	 * The implementation of this method will be invoked automatically when each
	 * service is removed from current domain.
	 * 
	 * @param childrenAffected
	 *            all children services are affected by removing operation.
	 * @param domain
	 *            the current domain
	 * @param objBeanService
	 *            the corresponding {@link ObjectBeanService} of removing
	 *            service.
	 */
	public void childrenRemoved(Iterator<?> childrenAffected, Domain domain,
			ObjectBeanService objBeanService);
}
