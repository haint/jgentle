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
package org.jgentleframework.context.injecting;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.core.reflection.metadata.MetaDataFactory;

/**
 * This class represents an {@link ObjectBeanFactory} in JGentle system, a core
 * factory of the {@link Provider} which can return an Object instance (possibly
 * shared or independent) when invoked. It manages all necessary information
 * includes the meta objects ({@link ObjectBindingConstant},
 * {@link ObjectAttach}, {@link ObjectConstant},
 * {@link ObjectBindingInterceptor}, ... ) in order to instantiate bean.
 * <p>
 * On the other hand, it looks like similar to configurable class (extension of
 * {@link Configurable} interface or {@link AbstractConfig}) class which
 * provides a few methods in order to configure bean instance, however there is
 * a difference that the configurable class is only runable at startup time
 * (configuration time) while {@link ObjectBeanFactory} is only used at
 * run-time.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 10, 2008
 * @see MetaDataFactory
 * @see ServiceHandler
 */
public interface ObjectBeanFactory {
	/**
	 * Returns current corresponding {@link DefinitionManager} of this
	 * {@link ObjectBeanFactory}.
	 */
	public DefinitionManager getDefinitionManager();

	/**
	 * Returns current corresponding {@link Provider} of this
	 * {@link ObjectBeanFactory}.
	 * 
	 * @return the provider
	 */
	public Provider getProvider();

	/**
	 * Returns current {@link ServiceHandler} of this {@link ObjectBeanFactory}.
	 * 
	 * @return the service handler
	 */
	public ServiceHandler getServiceHandler();

	/**
	 * Creates the {@link Definition} bound to the given object class. If the
	 * given object class is not annotated with {@code @Bean annotation}, the
	 * default value will be used to create corresponding {@link Definition}.
	 * 
	 * @param objClass
	 *            the given object class.
	 * @param notLazyList
	 *            the not lazy list
	 */
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList);

	/**
	 * Creates the {@link Definition} bound to the given object class. If the
	 * given object class is not annotated with {@code @Bean annotation}, the
	 * default value will be used to create corresponding {@link Definition}.
	 * 
	 * @param objClass
	 *            the given object class.
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            The {@link Types} indicates type of given object class, only
	 *            object class is corresponding to given type to be in used.
	 */
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList,
			Types type);

	/**
	 * Creates the {@link Definition} bound to the given object class. If the
	 * given object class is not annotated with {@code @Bean annotation}, the
	 * default value will be used to create corresponding {@link Definition}.
	 * 
	 * @param objClass
	 *            the given object class.
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            The {@link Types} indicates type of given object class, only
	 *            object class is corresponding to given type to be in used.
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList,
			Types type, List<Object> annotateIDList);

	/**
	 * Creates the {@link Definition} bound to the given {@link ObjectAttach}.
	 * 
	 * @param objAth
	 *            the given {@link ObjectAttach}
	 * @param notLazyList
	 *            the not lazy list
	 * @see ObjectAttach
	 */
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList);

	/**
	 * Creates the {@link Definition} bound to the given {@link ObjectAttach}.
	 * 
	 * @param objAth
	 *            the given {@link ObjectAttach}
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            The {@link Types} indicates type of given object class, only
	 *            object class is corresponding to given type to be in used.
	 * @see ObjectAttach
	 */
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList, Types type);

	/**
	 * Creates the {@link Definition} bound to the given {@link ObjectAttach}.
	 * 
	 * @param objAth
	 *            the given {@link ObjectAttach}
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            The {@link Types} indicates type of given object class, only
	 *            object class is corresponding to given type to be in used.
	 * @param annotateIDList
	 *            the annotate id list
	 * @see ObjectAttach
	 */
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList, Types type, List<Object> annotateIDList);

	/**
	 * Creates the {@link Definition} bound to the given
	 * {@link ObjectBindingConstant}.
	 * 
	 * @param objBndCst
	 *            the given {@link ObjectBindingConstant}
	 * @param notLazyList
	 *            the not lazy list
	 * @see ObjectBindingConstant
	 */
	public void load_ObjectBindingConstant(ObjectBindingConstant objBndCst,
			List<Object> notLazyList);

	/**
	 * Creates the {@link Definition} bound to the given
	 * {@link ObjectBindingConstant}.
	 * 
	 * @param objBndCst
	 *            the given {@link ObjectBindingConstant}
	 * @param notLazyList
	 *            the not lazy list
	 * @param annotateIDList
	 *            the annotate id list
	 * @see ObjectBindingConstant
	 */
	public void load_ObjectBindingConstant(ObjectBindingConstant objBndCst,
			List<Object> notLazyList, List<Object> annotateIDList);

	/**
	 * Creates the {@link Definition} bound to the given {@link ObjectConstant}.
	 * 
	 * @param objCst
	 *            the given {@link ObjectConstant}
	 * @see ObjectConstant
	 */
	public void load_ObjectConstant(ObjectConstant objCst);

	/**
	 * Gets the scope list.
	 * 
	 * @return the scope list
	 */
	public Map<String, ScopeInstance> getScopeList();

	/**
	 * Gets the map direct list.
	 * 
	 * @return the map direct list
	 */
	public Map<String, Object> getMapDirectList();

	/**
	 * Gets the mapping list.
	 * 
	 * @return the mapping list
	 */
	public Map<Class<?>, Class<?>> getMappingList();

	/**
	 * Gets the alias map.
	 * 
	 * @return the alias map
	 */
	public Map<String, Entry<Class<?>, Class<?>>> getAliasMap();

	/**
	 * Creates {@link ScopeImplementation}.
	 * 
	 * @param scopeName
	 *            the scope name
	 * @return returns a {@link ScopeImplementation} instance.
	 */
	public ScopeImplementation createScopeInstance(String scopeName);

	/**
	 * Returns an instance bound to the given refer string.
	 * 
	 * @param refer
	 *            the refer string
	 * @return an instance of the bean.
	 */
	public abstract Object getBean(String refer);

	/**
	 * Gets the ref instance.
	 * 
	 * @param refInstance
	 *            the ref instance
	 * @return the ref instance
	 */
	public abstract Object getRefInstance(String refInstance);
}
