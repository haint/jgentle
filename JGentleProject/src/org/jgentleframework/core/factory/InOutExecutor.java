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
package org.jgentleframework.core.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.enums.WrapperPrimitiveTypeList;
import org.jgentleframework.context.beans.Builder;
import org.jgentleframework.context.beans.Filter;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.core.GenericException;
import org.jgentleframework.core.InvalidOperationException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;
import org.jgentleframework.utils.data.NullClass;

/**
 * This is an abstract class that is responsible for dependency data controller
 * and processer.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 17, 2007
 */
public abstract class InOutExecutor {
	/** The log. */
	private static final Log	log	= LogFactory.getLog(InOutExecutor.class);

	/**
	 * Executes field inject.
	 * 
	 * @param fields
	 *            the fields
	 * @param provider
	 *            the provider
	 * @param target
	 *            the target
	 * @param definition
	 *            the definition
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	public static Map<Field, Object> executesFieldInject(Field[] fields,
			Provider provider, Object target, Definition definition)
			throws IllegalArgumentException, IllegalAccessException {

		Assertor.notNull(definition, "The given definition must not be null!");
		Assertor.notNull(provider, "The current provider must not be null!");
		Assertor.notNull(target, "The current target must not be null!");
		Map<Field, Object> result = new HashMap<Field, Object>();
		synchronized (target) {
			if (fields != null) {
				ArrayList<Field> fieldList = new ArrayList<Field>();
				for (Field field : fields) {
					Definition defField = definition.getMemberDefinition(field);
					if (defField != null
							&& defField.isAnnotationPresent(Inject.class)) {
						Inject inject = defField.getAnnotation(Inject.class);
						Object injected = InOutExecutor.getInjectedDependency(
								inject, field.getType(), provider);
						field.setAccessible(true);
						Object current = field.get(target);
						if (inject.alwaysInject() == false && current != null) {
							continue;
						}
						result.put(field, current);
						field.set(target, injected);
						if (!fieldList.contains(field))
							fieldList.add(field);
						field.setAccessible(false);
					}
					else
						continue;
				}
				// Executes filtering
				if (ReflectUtils.isCast(Filter.class, target)) {
					Filter filter = (Filter) target;
					filter.filters(result);
				}
			}
		}
		return result;
	}

	/**
	 * Executes field outject.
	 * 
	 * @param fields
	 *            the fields
	 * @param provider
	 *            the provider
	 * @param target
	 *            the target
	 * @param definition
	 *            the definition
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	public static void executesFieldOutject(Field[] fields, Provider provider,
			Object target, Definition definition)
			throws IllegalArgumentException, IllegalAccessException {

		Assertor.notNull(definition, "The given definition must not be null!");
		Assertor.notNull(provider, "The current provider must not be null!");
		Assertor.notNull(target, "The current target must not be null!");
		synchronized (target) {
			if (fields != null) {
				for (Field field : fields) {
					Object outjectObj = null;
					Definition defField = definition.getMemberDefinition(field);
					if (defField != null
							&& defField.isAnnotationPresent(Outject.class)) {
						Outject outject = defField.getAnnotation(Outject.class);
						field.setAccessible(true);
						outjectObj = field.get(target);
						field.setAccessible(false);
						/*
						 * Executes builder
						 */
						if (ReflectUtils.isCast(Builder.class, target)) {
							Builder builder = (Builder) target;
							if (builder.getOutjectValue(field) != null)
								outjectObj = builder.getOutjectValue(field);
						}
						InOutExecutor.setOutjectedDependency(outject,
								outjectObj, provider, field.getClass());
					}
					else
						continue;
				}
			}
		}
	}

	/**
	 * Executes method inject.
	 * 
	 * @param setters
	 *            the setters
	 * @param provider
	 *            the provider
	 * @param target
	 *            the target
	 * @param definition
	 *            the definition
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @return returns the {@link HashMap} containing all previous values of all
	 *         injected fields.
	 */
	public static Map<Field, Object> executesMethodInject(Method[] setters,
			Provider provider, Object target, Definition definition)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Assertor.notNull(definition, "The given definition must not be null!");
		Assertor.notNull(provider, "The current provider must not be null!");
		Assertor.notNull(target, "The current target must not be null!");
		HashMap<Field, Object> result = new HashMap<Field, Object>();
		synchronized (target) {
			if (setters != null) {
				for (Method method : setters) {
					Definition defMethod = definition
							.getMemberDefinition(method);
					if (defMethod == null)
						continue;
					else {
						Object[] args = Utils.getInjectedParametersOf(method,
								defMethod, provider);
						method.setAccessible(true);
						// Find field corresponding to setter method.
						Field field = null;
						try {
							field = Utils.getFieldOfDefaultSetGetter(method,
									(Class<?>) definition.getKey());
							field.setAccessible(true);
							result.put(field, field.get(target));
							field.setAccessible(false);
						}
						catch (InvalidOperationException e) {
							throw new InOutDependencyException(e.getMessage());
						}
						catch (NoSuchFieldException e) {
						}
						method.invoke(target, args);
						method.setAccessible(false);
					}
				}
				// Executes filtering
				if (ReflectUtils.isCast(Filter.class, target)) {
					Filter filter = (Filter) target;
					filter.filters(result);
				}
			}
		}
		return result;
	}

	/**
	 * Executes disinjection.
	 * 
	 * @param map
	 *            the map containing all previous values of injected fields
	 *            before they are injected.
	 * @param target
	 *            the target
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public static void executesDisinjection(HashMap<Field, Object> map,
			Object target) throws IllegalArgumentException,
			IllegalAccessException {

		Assertor.notNull(map, "The current map must not be null!");
		Assertor.notNull(target, "The current target object must not be null!");
		synchronized (target) {
			for (Entry<Field, Object> entry : map.entrySet()) {
				Field field = entry.getKey();
				field.setAccessible(true);
				field.set(target, entry.getValue());
				field.setAccessible(false);
			}
		}
	}

	/**
	 * Executes method outject.
	 * 
	 * @param getters
	 *            the getters
	 * @param provider
	 *            the provider
	 * @param target
	 *            the target
	 * @param definition
	 *            the definition
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static void executesMethodOutject(Method[] getters,
			Provider provider, Object target, Definition definition)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Assertor.notNull(definition, "The given definition must not be null!");
		Assertor.notNull(provider, "The current provider must not be null!");
		Assertor.notNull(target, "The current target must not be null!");
		synchronized (target) {
			if (getters != null) {
				for (Method method : getters) {
					Definition defMethod = definition
							.getMemberDefinition(method);
					if (defMethod == null)
						continue;
					else {
						Class<?> returnType = method.getReturnType();
						Object[] args = Utils.getInjectedParametersOf(method,
								defMethod, provider);
						Object outjectObj = null;
						method.setAccessible(true);
						outjectObj = args.length == 0 ? method.invoke(target)
								: method.invoke(target, args);
						method.setAccessible(false);
						Outject outject = defMethod
								.getAnnotation(Outject.class);
						Field field = null;
						try {
							field = Utils.getFieldOfDefaultSetGetter(method,
									(Class<?>) definition.getKey());
						}
						catch (InvalidOperationException e) {
							throw new InOutDependencyException(e.getMessage());
						}
						catch (NoSuchFieldException e) {
						}
						/*
						 * Executes builder
						 */
						if (ReflectUtils.isCast(Builder.class, target)) {
							Builder builder = (Builder) target;
							if (builder.getOutjectValue(field) != null)
								outjectObj = builder.getOutjectValue(field);
						}
						// executes outjection
						Class<?> type = field != null ? field.getClass()
								: returnType;
						InOutExecutor.setOutjectedDependency(outject,
								outjectObj, provider, type);
					}
				}
			}
		}
	}

	/**
	 * Returns the specified injected dependency instance.
	 * 
	 * @param inject
	 *            the corresponding {@link Inject} instance
	 * @param type
	 *            the type of original target.
	 * @param provider
	 *            the given {@link Provider}.
	 * @return returns the dependency instance if it exists, if not, returns
	 *         <b>null</b>.
	 */
	public static Object getInjectedDependency(Inject inject, Class<?> type,
			Provider provider) {

		Object result = null;
		String value = inject.value();
		if (provider != null) {
			if (value != null && !value.isEmpty()) {
				result = provider.getBean(value);
				result = result == null || result == NullClass.class ? provider
						.getBean(type) : result;
			}
			else {
				if (result == null)
					result = provider.getBean(type);
			}
		}
		else {
			if (log.isFatalEnabled()) {
				log.fatal("The given Provider (see " + Provider.class
						+ ")must not be null !! ",
						new InOutDependencyException());
			}
		}
		// checking constrant
		if ((result == null || result == NullClass.class)
				&& inject.required() == true) {
			throw new RequiredException(
					"Injected dependency object must not be null.");
		}
		if (result != null && result != NullClass.class && type.isPrimitive()) {
			int i = 0;
			for (WrapperPrimitiveTypeList wt : WrapperPrimitiveTypeList
					.values()) {
				if (type.equals(wt.getPrimitive())) {
					Class<?> wrappingType = wt.getWrapper();
					if (!ReflectUtils.isCast(wrappingType, result)) {
						throw new InOutDependencyException(
								"The injected dependency instance can not be cast to '"
										+ wrappingType + "'");
					}
				}
				i++;
			}
			if (i == 0) {
				throw new InOutDependencyException(
						"Can not identify the primitive type '" + type + "'");
			}
		}
		else if (result != null && result != NullClass.class) {
			if (!ReflectUtils.isCast(type, result)) {
				throw new InOutDependencyException(
						"The injected dependency instance can not be cast to '"
								+ type + "'");
			}
		}
		return result;
	}

	/**
	 * Sets the outjected dependency.
	 * 
	 * @param outject
	 *            the {@link Outject} annotation.
	 * @param object
	 *            the object
	 * @param provider
	 *            the provider
	 */
	public static void setOutjectedDependency(Outject outject, Object object,
			Provider provider, Class<?> type) {

		// required checking
		if (object == null && outject.required() == true) {
			throw new RequiredException("Outjected instance must not be null.");
		}
		ScopeImplementation scopeImpl = outject.scope();
		if (scopeImpl.equals(Scope.PROTOTYPE)) {
			throw new InvalidOperationException(
					"The outjected dependency must not be prototype-scoped!");
		}
		String scopeName = null;
		if (outject.value() == null || outject.value().isEmpty()) {
			scopeName = REF.refMapping(type);
		}
		else {
			try {
				scopeName = Utils.createScopeName(outject.value(), provider);
			}
			catch (GenericException e1) {
				throw new InvalidOperationException(e1.getMessage());
			}
		}
		if (scopeName == null || scopeName.isEmpty())
			throw new InvalidOperationException(
					"Could not create the scope name from outject value name '"
							+ outject.value() + "'");
		synchronized (scopeImpl) {
			try {
				scopeImpl.putBean(scopeName, object, provider
						.getObjectBeanFactory());
			}
			catch (InvalidAddingOperationException e) {
				e.printStackTrace();
			}
		}
	}
}
