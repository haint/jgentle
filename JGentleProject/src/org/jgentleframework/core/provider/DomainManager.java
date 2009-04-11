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

import java.beans.beancontext.BeanContextSupport;
import java.util.Collection;

import org.jgentleframework.core.JGentleException;

/**
 * This implementation is responsible for {@link Domain domain} management. It
 * provides some methods to create, remove ... and manage <b>domain</b>. A
 * domain is specified by a unique string name and all services in system must
 * be executed in the specified domain.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 10, 2007
 */
public interface DomainManager {
	/**
	 * Creates a new domain bound to a given name
	 * 
	 * @param name
	 *            given name of domain
	 */
	public void newDomain(String name, BeanContextSupport domainContext)
			throws JGentleException;

	/**
	 * Returns the specified domain bound to given name.
	 * 
	 * @param name
	 *            the given name of domain to return.
	 * @return returns the specified {@link Domain} if it exists, if not, return
	 *         <b>null</b>.
	 */
	public Domain getDomain(String name);

	/**
	 * Removes the specified {@link Domain}.
	 * 
	 * @param name
	 *            the name of domain need to be removed
	 * @return returns the specified domain was removed.
	 */
	public Domain removeDomain(String name, BeanContextSupport domainContext);

	/**
	 * Returns <b>true</b> if the domain bound to given name is existed.
	 * 
	 * @param name
	 *            name of domain need to be tested.
	 * @return <b>true</b> if the specified domain is existed, <b>false</b>
	 *         otherwise.
	 */
	public boolean containsDomain(String name);

	/**
	 * Returns <b>true</b> if the specified domain is existed.
	 * 
	 * @param domain
	 *            the {@link Domain} instance.
	 * @return <b>true</b> if specified domain is existed, <b>false</b>
	 *         otherwise.
	 */
	public boolean containsDomain(Domain domain);

	/**
	 * Returns the {@link Collection} of all created {@link Domain}
	 */
	public Collection<Domain> getDomainValues();

	/**
	 * Returns the object class of the specified {@link ServiceClass}
	 * implementation bound to given alias name.
	 * 
	 * @param alias
	 *            the alias name
	 * @return returns the object class if it exists, if not, returns
	 *         <b>null</b>.
	 */
	public Class<? extends ServiceClass> getServiceClass(String alias);
}