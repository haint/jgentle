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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.InOutExecutor;
import org.jgentleframework.core.intercept.MethodInterceptorCallbackException;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Intercepts the returned instance of an attribute of the {@link Annotation}.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 21, 2008
 * @see MethodInterceptor
 * @see RuntimeLoading
 */
class AnnotationAroundAdviceMethodInterceptor extends AbstractBeforeAdvice
		implements RuntimeLoading {
	/** The runtime loading. */
	boolean								runtimeLoading		= false;

	/** The attributes mapping. */
	Map<Method, Object>					attributesMapping	= new HashMap<Method, Object>();

	/** The outject method map. */
	Map<Method, Outject>				outjectMethodMap	= new HashMap<Method, Outject>();

	/** The provider. */
	final Provider						provider;

	/** The target class. */
	final Class<? extends Annotation>	targetClass;

	/** The log. */
	final Log							log					= LogFactory
																	.getLog(getClass());

	/**
	 * Instantiates a new annotation around advice method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @param targetClass
	 *            the target class
	 */
	public AnnotationAroundAdviceMethodInterceptor(Definition definition,
			Provider provider, boolean runtimeLoading,
			Class<? extends Annotation> targetClass) {

		super(definition);
		this.provider = provider;
		this.runtimeLoading = runtimeLoading;
		this.targetClass = targetClass;
		if (!runtimeLoading)
			init();
	}

	/**
	 * inits.
	 */
	private void init() {

		Set<Method> methods = definition.getAllAnnotatedMethods();
		if (methods != null) {
			for (Method method : methods) {
				Definition defMethod = this.definition
						.getMemberDefinition(method);
				if (defMethod != null) {
					if (defMethod.isAnnotationPresent(Inject.class)) {
						Inject inject = defMethod.getAnnotation(Inject.class);
						Object injected = null;
						if (!inject.invocation())
							injected = InOutExecutor.getInjectedDependency(
									inject, method.getReturnType(),
									this.provider);
						this.attributesMapping.put(method, injected);
					}
					if (defMethod.isAnnotationPresent(Outject.class)) {
						Outject outject = defMethod
								.getAnnotation(Outject.class);
						if (!outject.invocation()) {
							InOutExecutor.setOutjectedDependency(outject,
									this.attributesMapping.get(method),
									this.provider, method.getReturnType());
						}
						this.outjectMethodMap.put(method, outject);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		// checking runtime loading.
		if (runtimeLoading)
			init();
		Object result = null;
		Method invoMethod = invocation.getMethod();
		Object proxy = invocation.getThis();
		try {
			if (invoMethod.getName().equals("hashCode")) {
				return this.hashCode(proxy);
			}
			else if (invoMethod.getName().equals("equals")) {
				return equals(invocation.getArguments()[0], proxy);
			}
			else if (invoMethod.getName().equals("toString")) {
				return toString(proxy);
			}
			else if (invoMethod.getName().equals("annotationType")) {
				return annotationType();
			}
		}
		catch (Exception e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not invoke core method [" + invoMethod
						+ "] of this annotation proxy !!", e);
			}
		}
		result = this.attributesMapping.get(invoMethod);
		// executes wrapping
		try {
			Definition defMethod = this.definition
					.getMemberDefinition(invoMethod);
			if (defMethod != null
					&& defMethod.isAnnotationPresent(Inject.class)) {
				Inject inject = defMethod.getAnnotation(Inject.class);
				if (inject.invocation()) {
					result = InOutExecutor.getInjectedDependency(inject,
							invoMethod.getReturnType(), this.provider);
				}
			}
			if (result == null)
				result = invoMethod.getDefaultValue();
			// proceed the invocation
			Object anotherResult = null;
			try {
				anotherResult = invocation.proceed();
			}
			catch (MethodInterceptorCallbackException e) {
			}
			result = result != anotherResult && anotherResult != null ? anotherResult
					: result;
		}
		finally {
			if (this.outjectMethodMap.containsKey(invoMethod)) {
				Outject outject = this.outjectMethodMap.get(invoMethod);
				if (outject.invocation())
					InOutExecutor.setOutjectedDependency(outject, result,
							provider, invoMethod.getReturnType());
			}
		}
		return result;
	}

	/**
	 * Hash code.
	 * 
	 * @param proxy
	 *            the proxy
	 * @return the int
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	protected int hashCode(Object proxy) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Method[] methods = targetClass.getDeclaredMethods();
		int hashCode = 0;
		for (Method method : methods) {
			Class<?> type = method.getReturnType();
			Object value = (Object) this.attributesMapping.get(method);
			if (value == null)
				value = method.getDefaultValue();
			// if type is primitive type
			if (type.isPrimitive()) {
				// //////////////////////////////////
				// //////////////////////////////////
				hashCode += 127 * method.getName().hashCode()
						^ value.hashCode();
			}
			else if (type.isArray()) {
				Object array = method.invoke(proxy);
				Class<?> comtype = type.getComponentType();
				if (comtype == byte.class || comtype == Byte.class) {
					byte[] valueArr = new byte[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getByte(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == char.class || comtype == Character.class) {
					char[] valueArr = new char[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getChar(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == double.class || comtype == Double.class) {
					double[] valueArr = new double[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getDouble(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == float.class || comtype == Float.class) {
					float[] valueArr = new float[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getFloat(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == int.class || comtype == Integer.class) {
					int[] valueArr = new int[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getInt(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == long.class || comtype == Long.class) {
					long[] valueArr = new long[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getLong(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == short.class || comtype == Short.class) {
					short[] valueArr = new short[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getShort(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else if (comtype == boolean.class || comtype == Boolean.class) {
					boolean[] valueArr = new boolean[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.getBoolean(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
				else {
					Object[] valueArr = new Object[Array.getLength(array)];
					for (int i = 0; i < valueArr.length; i++) {
						valueArr[i] = Array.get(array, i);
					}
					hashCode += 127 * method.getName().hashCode()
							^ Arrays.hashCode(valueArr);
				}
			}
			else {
				// Object value = method.invoke(proxy);
				hashCode += 127 * method.getName().hashCode()
						^ value.hashCode();
			}
		}
		return hashCode;
	}

	/**
	 * Equals.
	 * 
	 * @param o
	 *            the o
	 * @param proxy
	 *            the proxy
	 * @return true, if successful
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	protected boolean equals(Object o, Object proxy)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		if (!ReflectUtils.isCast(o, this.targetClass))
			return false;
		Method[] lst = this.targetClass.getDeclaredMethods();
		for (Method method : lst) {
			Object oResult;
			Object thisResult;
			method.setAccessible(true);
			oResult = method.invoke(o);
			thisResult = (Object) this.attributesMapping.get(method);
			if (thisResult == null)
				thisResult = method.getDefaultValue();
			Class<?> returnType = method.getReturnType();
			if (returnType.isArray()) {
				Object[] oResultArray = new Object[Array.getLength(oResult)];
				for (int i = 0; i < oResultArray.length; i++) {
					oResultArray[i] = Array.get(oResult, i);
				}
				Object[] thisResultArray = new Object[Array
						.getLength(thisResult)];
				for (int i = 0; i < thisResultArray.length; i++) {
					thisResultArray[i] = Array.get(thisResult, i);
				}
				if (oResultArray.length != thisResultArray.length)
					return false;
				if (!Arrays.equals(oResultArray, thisResultArray))
					return false;
			}
			else if (!oResult.equals(thisResult)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * To string.
	 * 
	 * @param proxy
	 *            the proxy
	 * @return the string
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	protected String toString(Object proxy) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		Method[] lst = this.targetClass.getDeclaredMethods();
		String result = "@" + this.targetClass.getName() + "(";
		for (int i = 0; i < lst.length; i++) {
			Object thisResult = (Object) this.attributesMapping.get(lst[i]);
			if (thisResult == null)
				thisResult = lst[i].getDefaultValue();
			if (i != 0)
				result += ",";
			result += lst[i].getName() + "=" + thisResult.toString();
		}
		result += ")";
		return result;
	}

	/**
	 * Annotation type.
	 * 
	 * @return the class<? extends annotation>
	 */
	protected Class<? extends Annotation> annotationType() {

		return this.targetClass;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.interceptor.RuntimeLoading#isRuntimeLoading()
	 */
	@Override
	public boolean isRuntimeLoading() {

		return this.runtimeLoading;
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
}
