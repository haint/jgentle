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

import org.jgentleframework.services.eventservices.objectmeta.Subscription;

/**
 * Chỉ định thực thể filter của một subscription chỉ định.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public interface ISubscriptionFilter {
	/**
	 * @param eventClassImpl
	 * @param subscription
	 * @param args
	 * @return {@link Subscription}
	 * @throws EventServicesException
	 */
	Subscription filter(EventClass eventClassImpl, Subscription subscription,
			Object... args) throws EventServicesException;
}
