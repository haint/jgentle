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
import java.util.HashMap;

import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.handling.DefinitionManager;

/**
 * The implementation of {@link DomainManager} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 11, 2007
 * @see DomainManager
 */
public class DomainManagerImpl implements DomainManager {
	/** The domain list. */
	HashMap<String, Domain>	domainList	= new HashMap<String, Domain>();
	/** The def manager. */
	DefinitionManager		defManager;

	/**
	 * Instantiates a new domain manager impl.
	 * 
	 * @param defManager
	 *            the def manager
	 */
	public DomainManagerImpl(DefinitionManager defManager) {

		this.defManager = defManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#newDomain(java.lang.String)
	 */
	public synchronized void newDomain(String domain,
			BeanContextSupport domainContext) throws JGentleException {

		synchronized (domainList) {
			if (this.domainList.containsKey(domain)) {
				throw new JGentleException("Domain name: " + domain
						+ " is existed !");
			}
			else {
				Domain obj = new DomainImpl(domain, this.defManager);
				domainContext.add(obj);
				this.domainList.put(domain, obj);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#getDomain(java.lang.String)
	 */
	public Domain getDomain(String domain) {

		return this.domainList.get(domain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#removeDomain(java.lang.String)
	 */
	public Domain removeDomain(String domain, BeanContextSupport domainContext) {

		synchronized (domainList) {
			domainContext.remove(this.domainList.get(domain));
			return this.domainList.remove(domain);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#containsDomain(java.lang.String)
	 */
	public boolean containsDomain(String domain) {

		return this.domainList.containsKey(domain) ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#containsDomain(org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProvider)
	 */
	public boolean containsDomain(Domain domain) {

		return this.domainList.values().contains(domain) ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.provider.ServiceProviderManager#getDomainValues()
	 */
	@Override
	public Collection<Domain> getDomainValues() {

		return this.domainList.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.provider.DomainManager#getServiceClass(java.lang.String)
	 */
	@Override
	public Class<? extends ServiceClass> getServiceClass(String alias) {

		for (Domain domain : getDomainValues()) {
			if (domain.containsAlias(alias)) {
				return domain.getServiceClass(alias);
			}
		}
		return null;
	}
}
