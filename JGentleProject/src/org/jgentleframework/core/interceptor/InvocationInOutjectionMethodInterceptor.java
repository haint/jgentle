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
package org.jgentleframework.core.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.context.beans.annotation.DisablesInOut;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.InOutExecutor;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.Utils;

/**
 * This class represents a {@link MethodInterceptor} which is resposible for the
 * injecting and outjecting at run-time of all invocation injected/oujected
 * properties of bean instance.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 22, 2007
 * @see MethodInterceptor
 */
class InvocationInOutjectionMethodInterceptor implements MethodInterceptor,
		RuntimeLoading {
	/** The definition. */
	private Definition		definition;

	/** The flag. */
	private int				flag			= 0;

	/** The fields. */
	private Field[]			injectedFields;

	/** The setters. */
	private Method[]		injectedSetters;

	/** The outjected fields. */
	private Field[]			outjectedFields;

	/** The getters. */
	private Method[]		outjectedGetters;

	/** The provider. */
	private final Provider	provider;

	/** The runtime loading. */
	private boolean			runtimeLoading	= false;

	/**
	 * Instantiates a new invocation in/outject method interceptor.
	 * 
	 * @param definition
	 *            the definition is correspondent with target object holding
	 *            setter/getter methods and in/outjected fields.
	 * @param provider
	 *            the current {@link Provider}
	 * @param runtimeLoading
	 *            if specifies <code>true</code>, the intercepter will always
	 *            reload the in/out elements depend upon the given
	 *            {@link Definition} before every invoking, otherwise, if
	 *            sepecifies <code>false</code>, the interceptor will only load
	 *            the in/out elements at instantiation time.
	 */
	public InvocationInOutjectionMethodInterceptor(Definition definition,
			Provider provider, boolean runtimeLoading) {

		this.definition = definition;
		this.provider = provider;
		this.runtimeLoading = runtimeLoading;
		if (!runtimeLoading)
			init();
	}

	/**
	 * Gets the in/out fields.
	 */
	private synchronized void getInOutField() {

		Set<Field> set = definition.getAllAnnotatedFields();
		List<Field> fieldInject = new ArrayList<Field>();
		List<Field> fieldOutject = new ArrayList<Field>();
		if (set != null) {
			for (Field field : set) {
				Definition defField = this.definition
						.getMemberDefinition(field);
				if (defField.isAnnotationPresent(Inject.class)) {
					Inject inject = defField.getAnnotation(Inject.class);
					if (inject.invocation() == true
							&& !fieldInject.contains(field)) {
						fieldInject.add(field);
					}
				}
				if (defField.isAnnotationPresent(Outject.class)) {
					Outject outject = defField.getAnnotation(Outject.class);
					if (outject.invocation() == true
							&& !fieldOutject.contains(field)) {
						fieldOutject.add(field);
					}
				}
			}
		}
		this.injectedFields = fieldInject
				.toArray(new Field[fieldInject.size()]);
		this.outjectedFields = fieldOutject.toArray(new Field[fieldOutject
				.size()]);
	}

	/**
	 * Gets the in/out setters or getters.
	 */
	private synchronized void getInOutMethod() {

		Set<Method> set = definition.getAllAnnotatedMethods();
		List<Method> methodInject = new ArrayList<Method>();
		List<Method> methodOutject = new ArrayList<Method>();
		if (set != null) {
			for (Method method : set) {
				Definition defMethod = this.definition
						.getMemberDefinition(method);
				if (defMethod.isAnnotationPresent(Inject.class)
						&& Utils.isSetter(method)) {
					Inject inject = defMethod.getAnnotation(Inject.class);
					if (inject.invocation() == true
							&& !methodInject.contains(method)) {
						methodInject.add(method);
					}
				}
				else if (defMethod.isAnnotationPresent(Outject.class)
						&& Utils.isGetter(method)) {
					Outject outject = defMethod.getAnnotation(Outject.class);
					if (outject.invocation() == true
							&& !methodOutject.contains(method)) {
						methodOutject.add(method);
					}
				}
			}
		}
		this.injectedSetters = methodInject.toArray(new Method[methodInject
				.size()]);
		this.outjectedGetters = methodOutject.toArray(new Method[methodOutject
				.size()]);
	}

	/**
	 * Inits the in/outjected fields or setters/getters.
	 */
	private void init() {

		Assertor
				.notNull(this.definition,
						"The definition according to this interceptor must not be null !");
		Assertor
				.notNull(this.provider,
						"The given provider according to this interceptor must not be null !");
		getInOutMethod();
		getInOutField();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object proxy = invocation.getThis();
		Map<Field, Object> map = new HashMap<Field, Object>();
		Method method = invocation.getMethod();
		Definition defMethod = definition.getMemberDefinition(method);
		Object result = null;
		if (defMethod != null
				&& defMethod.isAnnotationPresent(DisablesInOut.class)) {
			result = invocation.proceed();
		}
		else {
			synchronized (this.definition) {
				// checking runtime loading
				if (runtimeLoading)
					init();
				// executes wrapping
				try {
					takeCurrentFlag(proxy, map);
					result = invocation.proceed();
				}
				finally {
					releaseFlag(proxy, map);
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.interceptor.RuntimeLoading#isRuntimeLoading()
	 */
	@Override
	public boolean isRuntimeLoading() {

		return runtimeLoading;
	}

	/**
	 * Release flag.
	 * 
	 * @param proxy
	 *            the proxy
	 * @param map
	 *            the map
	 * @throws Throwable
	 *             the throwable
	 */
	private synchronized void releaseFlag(Object proxy, Map<Field, Object> map)
			throws Throwable {

		this.flag--;
		if (flag == 0) {
			InOutExecutor.executesFieldOutjecting(outjectedFields, provider,
					proxy, definition);
			InOutExecutor.executesMethodOutjecting(outjectedGetters, provider,
					proxy, definition);
			InOutExecutor.executesDisinjection(map, proxy);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.interceptor.RuntimeLoading#setRuntimeLoading
	 * (boolean)
	 */
	@Override
	public void setRuntimeLoading(boolean runtimeLoading) {

		this.runtimeLoading = runtimeLoading;
	}

	/**
	 * Take current flag.
	 * 
	 * @param proxy
	 *            the proxy
	 * @param map
	 *            the map
	 * @throws Throwable
	 *             the throwable
	 */
	private synchronized void takeCurrentFlag(Object proxy,
			Map<Field, Object> map) throws Throwable {

		flag++;
		if (flag == 1
				&& !this.definition.isAnnotationPresent(DisablesInOut.class)) {
			map.putAll(InOutExecutor.executesInjectingAndFiltering(
					injectedFields, injectedSetters, provider, proxy,
					definition));
		}
	}
}
