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

import java.beans.beancontext.BeanContextMembershipListener;
import java.lang.reflect.Constructor;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Interface ObjectBeanService.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 13, 2007
 */
public interface ObjectBeanService extends BeanContextMembershipListener {
	static final String	childrenAdded	= "childrenAdded";

	static final String	childrenRemoved	= "childrenRemoved";

	/**
	 * Returns the corresponding constructor of service class used to create
	 * service instance.
	 */
	public Constructor<?> getConstructor();

	/**
	 * Returns the arguments used for the constructor call. This method is only
	 * invoked if {@link #getConstructor()} returns a <b>not-null</b> value.
	 */
	public Object[] getArgs();

	/**
	 * Sets the args.
	 * 
	 * @param args
	 *            the args to set
	 */
	public void setArgs(Object[] args);

	/**
	 * Sets the constructor.
	 * 
	 * @param constructor
	 *            the constructor to set
	 */
	public void setConstructor(Constructor<?> constructor);

	/**
	 * Returns alias name of this {@link ObjectBeanService}.
	 * 
	 * @return the alias
	 */
	public String getAlias();

	/**
	 * Returns the {@link Definition} of this {@link ObjectBeanService}.
	 * 
	 * @return the definition
	 */
	public Definition getDefinition();

	/**
	 * Returns the {@link Provider} instance of this {@link ObjectBeanService}.
	 * 
	 * @return the provider
	 */
	public Provider getProvider();

	/**
	 * Returns the singleton object of this {@link ObjectBeanService}.
	 * 
	 * @return ServiceClass
	 */
	public ServiceClass getSingletonObject();

	/**
	 * Sets the {@link Provider} to this {@link ObjectBeanService}.
	 * 
	 * @param provider
	 *            the current {@link Provider}
	 */
	public void setProvider(Provider provider);

	/**
	 * Sets the singleton object to this {@link ObjectBeanService}.
	 * 
	 * @param singletonObject
	 *            the desired singleton object instance.
	 */
	public void setSingletonObject(ServiceClass singletonObject);
}