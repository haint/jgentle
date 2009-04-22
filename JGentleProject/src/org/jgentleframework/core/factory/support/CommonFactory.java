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
package org.jgentleframework.core.factory.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.context.beans.DefinitionAware;
import org.jgentleframework.context.beans.FactoryBean;
import org.jgentleframework.context.beans.FactoryBeanProcessException;
import org.jgentleframework.context.beans.Initializing;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.beans.TargetClassesAware;
import org.jgentleframework.context.beans.annotation.InitializingMethod;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.InOutExecutor;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is responsible for object bean instantiation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 18, 2007
 */
public class CommonFactory {
	/** Singleton object. */
	private static CommonFactory	objInstance	= new CommonFactory();

	private final Log				log			= LogFactory.getLog(getClass());

	/**
	 * Returns a unique instance of {@link CommonFactory}.
	 * 
	 * @return {@link CommonFactory}
	 */
	public static CommonFactory singleton() {

		return CommonFactory.objInstance;
	}

	/**
	 * Constructor.
	 */
	private CommonFactory() {

	}

	/**
	 * Do init.
	 * 
	 * @param def
	 *            the definition
	 * @param obj
	 *            object bean
	 * @param clazz
	 *            the <code>object class</code>
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws SecurityException
	 *             the security exception
	 */
	public void doInit(Definition def, Object obj, Class<?> clazz)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException, NoSuchMethodException {

		if (ReflectUtils.isCast(Initializing.class, obj)) {
			((Initializing) obj).activate();
		}
		else if (def.isAnnotationPresentAtAnyMethods(InitializingMethod.class)) {
			List<Method> methods = def
					.getMethodsAnnotatedWith(InitializingMethod.class);
			for (Method method : methods) {
				method.setAccessible(true);
				method.invoke(obj);
				method.setAccessible(false);
			}
		}
		else {
			ReflectUtils.invokeMethod(obj, "init", null, null, true, true);
		}
	}

	/**
	 * Embed {@link Definition}.
	 * 
	 * @param definition
	 *            the definition
	 * @param obj
	 *            the obj
	 */
	public void embedDefinition(Definition definition, Object obj) {

		if (ReflectUtils.isCast(DefinitionAware.class, obj)) {
			((DefinitionAware) obj).setDefinition(definition);
		}
	}

	/**
	 * Embed {@link Provider}.
	 * 
	 * @param provider
	 *            the provider
	 * @param obj
	 *            the obj
	 */
	public void embedProvider(Provider provider, Object obj) {

		if (ReflectUtils.isCast(ProviderAware.class, obj)) {
			ProviderAware aware = (ProviderAware) obj;
			aware.setProvider(provider);
		}
	}

	/**
	 * Embed target classes.
	 * 
	 * @param classes
	 *            the classes
	 * @param obj
	 *            the obj
	 */
	public void embedTargetClasses(Class<?>[] classes, Object obj) {

		if (ReflectUtils.isCast(TargetClassesAware.class, obj)) {
			((TargetClassesAware) obj).setTargetClasses(classes);
		}
	}

	/**
	 * Executes factory bean.
	 * 
	 * @param factoryBean
	 *            the factory bean
	 * @param targetClass
	 *            the target class
	 * @return {@link Object}
	 */
	public Object executeFactoryBean(FactoryBean factoryBean,
			Class<?> targetClass) {

		Object bean = null;
		try {
			bean = factoryBean.getBean();
		}
		catch (Exception e) {
			FactoryBeanProcessException exception = new FactoryBeanProcessException(
					"Could not execute 'getBean()' method of FactoryBean '"
							+ targetClass + "'");
			exception.initCause(e);
			if (log.isFatalEnabled()) {
				log.fatal(
						"Could not execute 'getBean()' method of FactoryBean ["
								+ targetClass + "]", e);
			}
			// throw exception;
		}
		if (bean == null) {
			throw new FactoryBeanProcessException(
					"The returned value of 'getBean()' method of FactoryBean '"
							+ targetClass.getName() + "' must not be null!");
		}
		return bean;
	}

	/**
	 * Executes process after bean is created.
	 * 
	 * @param targetClass
	 *            the target object class
	 * @param metaObj
	 *            the {@link MetaDefObject} instance
	 * @param provider
	 *            current provider
	 * @param result
	 *            the object bean has just bean instantiated.
	 * @param definition
	 *            the {@link Definition}
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	public void executeProcessAfterBeanCreated(Class<?> targetClass,
			MetaDefObject metaObj, Provider provider, Object result,
			Definition definition) throws IllegalArgumentException,
			SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		/*
		 * Xử lý thông tin các inject non-invocation
		 */
		if (!targetClass.isAnnotation()) {
			InOutExecutor.executesFieldInject(metaObj.getInjectedFields(),
					provider, result, definition);
			InOutExecutor.executesMethodInject(metaObj.getSetters(), provider,
					result, definition);
		}
		// embed
		embedProvider(provider, result);
		embedDefinition(definition, result);
		embedTargetClasses(new Class<?>[] { targetClass }, result);
		/*
		 * Chỉ định thực thi init
		 */
		if (!targetClass.isAnnotation()) {
			doInit(definition, result, targetClass);
		}
		/*
		 * Xử lý thông tin các outject non-invocation
		 */
		InOutExecutor.executesFieldOutject(metaObj.getOutjectedFields(),
				provider, result, definition);
		InOutExecutor.executesMethodOutject(metaObj.getGetters(), provider,
				result, definition);
	}
}
