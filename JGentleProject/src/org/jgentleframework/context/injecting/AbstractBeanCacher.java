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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.scope.ScopeController;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.AmbiguousException;
import org.jgentleframework.core.factory.support.AbstractProcesserChecker;
import org.jgentleframework.core.factory.support.CachedConstructor;
import org.jgentleframework.core.factory.support.CommonFactory;
import org.jgentleframework.core.factory.support.MetaDefObject;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;

/**
 * The Class AbstractBeanCacher.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 19, 2009
 */
public abstract class AbstractBeanCacher extends AbstractLoadingFactory
		implements Provider {
	/** The alias map. */
	protected Map<String, Entry<Class<?>, Class<?>>>	aliasMap			= null;

	/** The caching list. */
	protected Map<Definition, CachedConstructor>		cachingList			= new HashMap<Definition, CachedConstructor>();

	/** The log. */
	protected final Log									log					= LogFactory
																					.getLog(this
																							.getClass());

	/** The map direct list. */
	protected Map<String, Object>						mapDirectList		= null;

	/** The mapping list. */
	protected Map<Class<?>, Class<?>>					mappingList			= null;

	/** The NULL sharedobject. */
	protected final Object								NULL_SHAREDOBJECT	= new Object();

	/** The root scope name. */
	protected Map<Object, String>						rootScopeName		= new HashMap<Object, String>();

	/** The {@link ScopeController}. */
	protected ScopeController							scopeController		= new ScopeController();

	/** The scope list. */
	protected Map<String, ScopeInstance>				scopeList			= null;

	/** The {@link ServiceHandler}. */
	protected ServiceHandler							serviceHandler		= null;

	/**
	 * Do appropriate scope name.
	 * 
	 * @param obj
	 *            the obj
	 * @return the appropriate scope name class
	 */
	protected AppropriateScopeNameClass doAppropriateScopeName(Object obj) {

		Class<?> clazz = null;
		Class<?> targetClass = null;
		Definition definition = null;
		String ref = null;
		String scopeName = null;
		String mappingName = null;
		Object root = null;
		if (ReflectUtils.isClass(obj)) {
			clazz = (Class<?>) obj;
			targetClass = this.mappingList.get(obj);
			targetClass = null == targetClass ? clazz : targetClass;
			definition = this.defManager.getDefinition(targetClass);
			root = clazz;
			scopeName = Utils.createScopeName(clazz, targetClass, definition,
					mappingName);
		}
		else if (ReflectUtils.isCast(Definition.class, obj)) {
			definition = (Definition) obj;
			clazz = (Class<?>) definition.getKey();
			targetClass = (Class<?>) definition.getKey();
			root = definition;
			scopeName = Utils.createScopeName(clazz, targetClass, definition,
					mappingName);
		}
		else if (ReflectUtils.isCast(String.class, obj)) {
			String str = (String) obj;
			if (str.startsWith(Configurable.REF_MAPPING)) {
				ref = str.replaceFirst(Configurable.REF_MAPPING, "").trim();
				Entry<Class<?>, Class<?>> entry = aliasMap.get(ref);
				if (entry == null) {
					throw new AmbiguousException("The alias name [" + ref
							+ "] is not existed !");
				}
				clazz = entry.getKey();
				targetClass = entry.getValue();
				definition = this.getDefinitionManager().getDefinition(
						entry.getValue());
				mappingName = ref;
				root = str;
				scopeName = Utils.createScopeName(clazz, targetClass,
						definition, mappingName);
			}
			else if (str.startsWith(Configurable.REF_CONSTANT)) {
				String instanceName = str.replaceFirst(
						Configurable.REF_CONSTANT, "").trim();
				ref = REF.REF_CONSTANT + instanceName;
				root = str;
				scopeName = Utils.createScopeName(instanceName);
				this.rootScopeName.put(root, scopeName);
			}
		}
		ScopeInstance scope = null;
		synchronized (scopeList) {
			scope = scopeList.get(scopeName);
		}
		// If is Singleton scope
		if ((scope != null && scope.equals(Scope.SINGLETON))) {
			this.rootScopeName.put(root, scopeName);
		}
		return new AppropriateScopeNameClass(clazz, targetClass, definition,
				ref, scopeName, mappingName);
	}

	/**
	 * Returns caching result.
	 * 
	 * @param selector
	 *            the selector
	 * @return the object
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	protected Object returnsCachingResult(Selector selector)
			throws InvocationTargetException, IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			NoSuchMethodException {

		Object result = NULL_SHAREDOBJECT;
		CoreInstantiationSelector targetSelector = (CoreInstantiationSelector) selector;
		Definition definition = targetSelector.getDefinition();
		// find in cache
		if (cachingList.containsKey(definition)) {
			CachedConstructor cons = cachingList.get(definition);
			int hashcodeID = cons.hashcodeID();
			if (hashcodeID == (definition.hashCode() ^ cons.hashCode())) {
				result = cons.newInstance(targetSelector.getArgs());
				// executes process after bean is created
				MetaDefObject metaObj = new MetaDefObject();
				AbstractProcesserChecker.findInOutNonRuntime(metaObj,
						definition);
				AbstractProcesserChecker.prepareSingletonBean(targetSelector,
						this, result);
				CommonFactory.singleton().executeProcessAfterBeanCreated(
						targetSelector.getTargetClass(), metaObj, this, result,
						definition);
				return result;
			}
		}
		return result;
	}

	/**
	 * Returns shared object.
	 * 
	 * @param scopeName
	 *            the scope name
	 * @return the object
	 */
	protected Object returnSharedObject(String scopeName) {

		if (mapDirectList.containsKey(scopeName)) {
			return mapDirectList.get(scopeName);
		}
		return NULL_SHAREDOBJECT;
	}
}
