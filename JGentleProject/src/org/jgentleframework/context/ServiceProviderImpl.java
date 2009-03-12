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
package org.jgentleframework.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.JGentelIllegalArgumentException;
import org.jgentleframework.utils.Assertor;

/**
 * This class is an implementation of {@link ServiceProvider} interface. It's
 * responsible for a container which manages all of services of system.
 * Moreover, because of {@link ServiceProvider} is an extension of
 * {@link Provider} so it has all features as same as an {@link Provider}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 11, 2007
 * @see Provider
 * @see ServiceProvider
 */
public class ServiceProviderImpl extends AbstractServiceManagement implements
		ServiceProvider {
	/**
	 * The Constructor.
	 * 
	 * @param annoObjectHandler
	 *            the anno object handler
	 * @param OLArray
	 *            the OLArray
	 */
	public ServiceProviderImpl(ServiceHandler annoObjectHandler,
			List<Map<String, Object>> OLArray) {

		super(annoObjectHandler, OLArray);
	}

	/**
	 * This {@link HashMap} holds all installed
	 * {@link ComponentServiceContextType} in this ServiceProvider.
	 */
	private HashMap<Class<?>, ComponentServiceContextType<Configurable>>	CSCList	= new HashMap<Class<?>, ComponentServiceContextType<Configurable>>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.context.ServiceProvider#addCSContext(java.lang.Class,
	 * org.exxlabs.jgentle.context.ComponentServiceContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Configurable> void addCSContext(Class<?> clazzType,
			ComponentServiceContextType<T> csc) {

		Assertor.notNull(clazzType, "Object class must not be null.");
		Assertor.notNull(csc, "Component Service Context must not be null.");
		synchronized (this.CSCList) {
			if (this.CSCList.containsKey(clazzType)) {
				throw new JGentelIllegalArgumentException("Object class "
						+ clazzType.getName() + " is existed.");
			}
			this.CSCList.put(clazzType,
					(ComponentServiceContextType<Configurable>) csc);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.exxlabs.jgentle.context.ServiceProvider#countCSC()
	 */
	@Override
	public int countCSC() {

		return this.CSCList.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.exxlabs.jgentle.context.ServiceProvider#cscValues()
	 */
	@Override
	public Collection<ComponentServiceContextType<Configurable>> cscValues() {

		return this.CSCList.values();
	}

	/*
	 * (non-Javadoc)
	 * @see org.exxlabs.jgentle.context.ServiceProvider#getCSCList()
	 */
	@Override
	public HashMap<Class<?>, ComponentServiceContextType<Configurable>> getCSCList() {

		return CSCList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.context.ServiceProvider#getCSContext(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCSContext(Class<T> clazzType) {

		Assertor.notNull(clazzType, "Object class must not be null.");
		synchronized (CSCList) {
			if (!this.CSCList.containsKey(clazzType)) {
				throw new JGentelIllegalArgumentException("Object class "
						+ clazzType.getName() + " is not existed.");
			}
			return (T) this.CSCList.get(clazzType);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.context.ServiceProvider#removeComponentServiceContext
	 * (java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeComponentServiceContext(Class<T> clazzType) {

		Assertor.notNull(clazzType, "Object class must not be null.");
		synchronized (CSCList) {
			if (!this.CSCList.containsKey(clazzType)) {
				throw new JGentelIllegalArgumentException("Object class "
						+ clazzType.getName() + " is not existed.");
			}
			return (T) this.CSCList.remove(clazzType);
		}
	}
}
