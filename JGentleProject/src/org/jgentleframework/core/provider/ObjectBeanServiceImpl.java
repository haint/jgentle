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

import java.beans.ConstructorProperties;
import java.beans.beancontext.BeanContextChildSupport;
import java.beans.beancontext.BeanContextMembershipEvent;
import java.lang.reflect.Constructor;

import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The implementation of {@link ObjectBeanService}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 12, 2007
 * @see ObjectBeanService
 */
class ObjectBeanServiceImpl extends BeanContextChildSupport implements
		ObjectBeanService {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 2135890119487753437L;

	/** The alias. */
	String						alias				= null;

	/** The args. */
	transient Object[]			args				= null;

	/** The constructor. */
	transient Constructor<?>	constructor			= null;

	/** The definition. */
	Definition					definition			= null;

	/** The provider. */
	transient Provider			provider			= null;

	/** The singleton object. */
	transient ServiceClass		singletonObject		= null;

	/**
	 * Instantiates a new object bean service impl.
	 * 
	 * @param definition
	 *            the definition
	 * @param serviceClass
	 *            the service class
	 */
	@ConstructorProperties(value = { "definition, serviceClass" })
	public ObjectBeanServiceImpl(Definition definition,
			Class<? extends ServiceClass> serviceClass) {

		this.definition = definition;
	}

	/**
	 * Instantiates a new object bean service impl.
	 * 
	 * @param definition
	 *            the definition
	 * @param serviceClass
	 *            the service class
	 * @param alias
	 *            the alias
	 */
	@ConstructorProperties(value = { "definition,serviceClass,alias" })
	public ObjectBeanServiceImpl(Definition definition,
			Class<? extends ServiceClass> serviceClass, String alias) {

		this.definition = definition;
		this.alias = alias;
	}

	/**
	 * Action to do.
	 * 
	 * @param bcme
	 *            the bcme
	 * @param name
	 *            the name
	 */
	private void actionToDo(BeanContextMembershipEvent bcme, String name) {

		/*
		 * Nếu là singleton
		 */
		if (this.definition.getAnnotation(BeanServices.class).singleton() == true) {
			if (!(this.singletonObject == null)
					&& ReflectUtils.isCast(ChildServiceListener.class,
							this.singletonObject)) {
				ChildServiceListener obj = (ChildServiceListener) this.singletonObject;
				if (name.equals(ObjectBeanService.childrenAdded))
					obj.childrenAdded(bcme.iterator(), (Domain) bcme
							.getSource(), this);
				if (name.equals(ObjectBeanService.childrenRemoved))
					obj.childrenRemoved(bcme.iterator(), (Domain) bcme
							.getSource(), this);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextMembershipListener#childrenAdded(java
	 * .beans.beancontext.BeanContextMembershipEvent)
	 */
	@Override
	public void childrenAdded(BeanContextMembershipEvent bcme) {

		if (this.singletonObject == null)
			return;
		synchronized (bcme) {
			actionToDo(bcme, ObjectBeanService.childrenAdded);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.beans.beancontext.BeanContextMembershipListener#childrenRemoved(
	 * java.beans.beancontext.BeanContextMembershipEvent)
	 */
	@Override
	public void childrenRemoved(BeanContextMembershipEvent bcme) {

		if (this.singletonObject == null)
			return;
		synchronized (bcme) {
			actionToDo(bcme, ObjectBeanService.childrenRemoved);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ObjectBeanService#getAlias()
	 */
	public String getAlias() {

		return alias;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.provider.ObjectBeanService#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return this.args != null ? this.args.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.ObjectBeanService#getConstructor()
	 */
	@Override
	public Constructor<?> getConstructor() {

		return this.constructor;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ObjectBeanService#getDefinition()
	 */
	public Definition getDefinition() {

		return this.definition;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.provider.ObjectBeanService#getProvider()
	 */
	@Override
	public Provider getProvider() {

		return provider;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ObjectBeanService#getSingletonObject()
	 */
	public ServiceClass getSingletonObject() {

		return singletonObject;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.ObjectBeanService#setArgs(java.lang
	 * .Object[])
	 */
	@Override
	public void setArgs(Object[] args) {

		this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.ObjectBeanService#setConstructor(java
	 * .lang.reflect.Constructor)
	 */
	@Override
	public void setConstructor(Constructor<?> constructor) {

		this.constructor = constructor;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.provider.ObjectBeanService#setProvider(org.
	 * jgentleframework.context.injecting.Provider)
	 */
	@Override
	public void setProvider(Provider provider) {

		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.provider.
	 * ObjectBeanService
	 * #setSingletonObject(org.jgentleframework.core.metadatahandling
	 * .aohhandling.pvdhandler.ServiceClass)
	 */
	public void setSingletonObject(ServiceClass singletonObject) {

		this.singletonObject = singletonObject;
	}
}
