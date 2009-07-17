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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.Context;
import org.jgentleframework.context.injecting.autodetect.AbstractDetector;
import org.jgentleframework.context.injecting.autodetect.Detector;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * This is the interface which is responsible for core of container of JGentle
 * and bean instantiation. It always ensures that returned instances are
 * properly injected before they are returned. It is implemented by objects that
 * hold a number of bean definitions, each uniquely identified by a String ID of
 * {@link Definition} or given mapping type. Depending on the bean's
 * {@link Definition}, the provider will return either an independent instance
 * of a contained object (the Prototype design pattern), or a single shared
 * instance (a superior alternative to the Singleton design pattern, in which
 * the instance is a singleton in the scope of the factory).
 * <p>
 * Moreover, the {@link Provider} is the heart of JGentle framework, it holds
 * all common services of core JGentle such as IoC (Inversion of Control), DM
 * (Definition Management), SH (Service Handler) ... and its API has also a few
 * additional features: it allows pre-constructed instances to have their fields
 * and methods injected and offers programmatic introspection to support tool
 * development.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 12, 2007
 * @see Context
 * @see IAbstractBeanFactory
 */
public interface Provider extends IAbstractBeanFactory, Context {
	/**
	 * Returns an instance bound to the given type
	 * 
	 * @param type
	 *            the given type.
	 * @return an instance of the bean
	 */
	public <T> T getBean(Class<T> type);

	/**
	 * Gets the root scope name {@link Map map}.
	 * 
	 * @return the singletonScopeName
	 */
	public Map<Object, String> getRootScopeName();

	/**
	 * Returns an instance bound to the given {@link Definition}
	 * 
	 * @param def
	 *            the corresponding definition of bean.
	 * @return an instance of the bean
	 */
	public Object getBean(Definition def);

	/**
	 * Returns an instance bound to the given refer string.
	 * 
	 * @param refer
	 *            the refer string
	 * @return an instance of the bean.
	 */
	public Object getBean(String refer);

	/**
	 * Returns an instance bound to the given ID of {@link Definition}
	 * 
	 * @param ID
	 *            the ID of corresponding {@link Definition} of bean.
	 * @return an instance of the bean
	 */
	public Object getBeanBoundToDefinition(String ID);

	/**
	 * Returns an instance bound to the given mapping name
	 * 
	 * @param mappingName
	 *            the mapping name.
	 * @return an instance of the bean
	 */
	public Object getBeanBoundToMapping(String mappingName);

	/**
	 * Returns the constant instance bound to given name.
	 * 
	 * @param instanceName
	 *            the instance name
	 * @return an instance of the bean if it exists, otherwise returns
	 *         <b>null</b>.
	 */
	public Object getBeanBoundToName(String instanceName);

	/**
	 * Returns the list of configurable instances of this container.
	 */
	public List<Configurable> getConfigInstances();

	/**
	 * Returns the detector controller of this container.
	 * 
	 * @see AbstractDetector
	 */
	public Detector getDetectorController();

	/**
	 * Sets the list of configurable instances to this container.
	 * 
	 * @param configInstances
	 *            the list of configurable instances.
	 */
	public void setConfigInstances(ArrayList<Configurable> configInstances);
}
