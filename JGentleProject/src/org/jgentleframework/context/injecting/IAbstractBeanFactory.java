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
package org.jgentleframework.context.injecting;

import org.jgentleframework.context.injecting.scope.ScopeController;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Interface IAbstractBeanFactory.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 10, 2008
 * @see AbstractBeanFactory
 * @see ObjectBeanFactory
 */
public interface IAbstractBeanFactory {
	/**
	 * Returns a bean instance.
	 * 
	 * @param type
	 *            the target class type of desired bean.
	 * @param targetClass
	 *            the target class of desired bean.
	 * @param mappingName
	 *            the mapping name if it exists.
	 * @param definition
	 *            the definition of instantiating bean if it exists.
	 * @return the bean instance
	 */
	public Object getBeanInstance(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition);

	/**
	 * Returns a bean instance.
	 * 
	 * @param targetClass
	 *            the target class of desired bean.
	 * @param definition
	 *            the definition of instantiating bean if it exists.
	 * @return {@link Object}
	 */
	public Object getBeanInstance(Class<?> targetClass, Definition definition);

	/**
	 * Returns current {@link DefinitionManager definition manager}.
	 */
	public DefinitionManager getDefinitionManager();

	/**
	 * Returns the {@link ObjectBeanFactory} instance.
	 * 
	 * @return the object bean factory
	 */
	public ObjectBeanFactory getObjectBeanFactory();

	/**
	 * Returns the {@link ScopeController scope controller}.
	 */
	public ScopeController getScopeController();

	/**
	 * Returns current {@link ServiceHandler service handler} of this
	 * {@link Provider provider}.
	 */
	public ServiceHandler getServiceHandler();

	/**
	 * Returns <b>true</b> if given name is existed as a constant.
	 * 
	 * @param name
	 *            the given name to be tested.
	 * @return <b>true</b> is it exists, <b>false</b> otherwise.
	 */
	public boolean isContainsConstant(String name);

	/**
	 * Returns <b>true</b> if given name is existed as a mapping name.
	 * <p>
	 * ex: attach(A.class).<b>withName("Name")</b>.to(B.class).scope(
	 * Scope.SINGLETON);
	 * 
	 * @param name
	 *            the given name to be tested.
	 * @return <b>true</b> is it exists, <b>false</b> otherwise.
	 */
	public boolean isContainsMappingName(String name);

	/**
	 * Returns <b>true</b> if the instantiated beans whose {@link Definition}
	 * corresponding to given type are prototype-scoped beans. Otherwise returns
	 * <b>false</b>.
	 * 
	 * @param clazz
	 *            the given type.
	 * @return true, if checks if is prototype
	 */
	public boolean isPrototype(Class<?> clazz);

	/**
	 * Returns <b>true</b> if the instantiated beans whose {@link Definition}
	 * corresponding to given ID are prototype-scoped beans. Otherwise returns
	 * <b>false</b>.
	 * 
	 * @param ID
	 *            the ID of definition.
	 * @return true, if checks if is prototype
	 */
	public boolean isPrototype(String ID);

	/**
	 * Returns <b>true</b> if the instantiated beans whose {@link Definition
	 * definitions} corresponding to given type are singleton-scoped beans.
	 * Otherwise returns <b>false</b>.
	 * 
	 * @param clazz
	 *            the given type.
	 * @return true, if checks if is singleton
	 */
	public boolean isSingleton(Class<?> clazz);

	/**
	 * Returns <b>true</b> if given bean is a singleton-scoped bean. Otherwise
	 * returns <b>false</b>.
	 * 
	 * @param bean
	 *            the given bean
	 * @return true, if checks if is singleton
	 */
	public boolean isSingleton(Object bean);

	/**
	 * Returns <b>true</b> if the instantiated beans whose {@link Definition
	 * definitions} corresponding to given ID are singleton-scoped beans.
	 * Otherwise returns <b>false</b>.
	 * 
	 * @param ID
	 *            the ID of definition.
	 * @return true, if checks if is singleton
	 */
	public boolean isSingleton(String ID);

	/**
	 * Returns the object bean corresponding to represented String.
	 * 
	 * @param refInstance
	 *            the string represents object bean.
	 * @return Object
	 */
	public Object getRefInstance(String refInstance);
}
