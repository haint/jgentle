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
package org.jgentleframework.core.factory.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;

import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.intercept.MethodInterceptorCallback;
import org.jgentleframework.core.interceptor.InterceptorUtils;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;

/**
 * The Class AbstractProcesserChecker.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 26, 2008
 */
public abstract class AbstractProcesserChecker {
	/**
	 * Find in out non runtime.
	 * 
	 * @param metaObj
	 *            the meta obj
	 * @param definition
	 *            the definition
	 * @return true, if is invocation.
	 */
	public static boolean findInOutNonRuntime(MetaDefObject metaObj,
			Definition definition) {

		// find all injected fields
		List<Field> injectedFields = definition != null ? definition
				.getFieldsAnnotatedWith(Inject.class) : null;
		boolean result = false;
		if (injectedFields != null) {
			for (int i = 0; i < injectedFields.size(); i++) {
				Field field = injectedFields.get(i);
				Definition def = definition.getMemberDefinition(field);
				Inject inject = def.getAnnotation(Inject.class);
				if (inject.invocation()) {
					injectedFields.remove(field);
					result = true;
				}
			}
			metaObj.setInjectedFields(injectedFields
					.toArray(new Field[injectedFields.size()]));
		}
		// find all outjected fields
		List<Field> outjectedFields = definition != null ? definition
				.getFieldsAnnotatedWith(Outject.class) : null;
		if (outjectedFields != null) {
			for (int i = 0; i < outjectedFields.size(); i++) {
				Field field = outjectedFields.get(i);
				Definition def = definition.getMemberDefinition(field);
				Outject outject = def.getAnnotation(Outject.class);
				if (outject.invocation()) {
					outjectedFields.remove(field);
					result = true;
				}
			}
			metaObj.setOutjectedFields(outjectedFields
					.toArray(new Field[outjectedFields.size()]));
		}
		// find all injected methods (setter)
		List<Method> injectedMethods = definition != null ? definition
				.getMethodsAnnotatedWith(Inject.class) : null;
		if (injectedMethods != null) {
			for (int i = 0; i < injectedMethods.size(); i++) {
				Method method = injectedMethods.get(i);
				if (!Utils.isSetter(method))
					continue;
				Definition def = definition.getMemberDefinition(method);
				Inject inject = def.getAnnotation(Inject.class);
				if (inject.invocation()) {
					injectedMethods.remove(method);
					result = true;
				}
			}
			metaObj.setSetters(injectedMethods
					.toArray(new Method[injectedMethods.size()]));
		}
		// find all outjected methods (getter)
		List<Method> outjectedMethods = definition != null ? definition
				.getMethodsAnnotatedWith(Outject.class) : null;
		if (outjectedMethods != null) {
			for (int i = 0; i < outjectedMethods.size(); i++) {
				Method method = outjectedMethods.get(i);
				if (!Utils.isGetter(method))
					continue;
				Definition def = definition.getMemberDefinition(method);
				Outject outject = def.getAnnotation(Outject.class);
				if (outject.invocation()) {
					outjectedMethods.remove(method);
					result = true;
				}
			}
			metaObj.setGetters(outjectedMethods
					.toArray(new Method[outjectedMethods.size()]));
		}
		return result;
	}

	/**
	 * Creates the construction proxy.
	 * 
	 * @param selector
	 *            the selector
	 * @return the cached constructor
	 */
	abstract protected CachedConstructor createConstructionProxy(
			CoreInstantiationSelector selector, MetaDefObject mdo)
			throws SecurityException, NoSuchMethodException;

	/**
	 * Creates the construction proxy.
	 * 
	 * @param selector
	 *            the selector
	 * @param interfaze
	 *            the interfaze
	 * @param interceptor
	 *            the interceptor
	 * @param methodList
	 *            the method list
	 * @return the cached constructor
	 */
	abstract protected CachedConstructor createConstructionProxy(
			CoreInstantiationSelector selector, Class<?> interfaze,
			net.sf.cglib.proxy.MethodInterceptor interceptor,
			final List<Method> methodList, MetaDefObject mdo)
			throws SecurityException, NoSuchMethodException;

	/**
	 * Creates the pure bean instance.
	 * 
	 * @param targetSelector
	 *            the target selector
	 * @param targetClass
	 *            the target class
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return the object
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws SecurityException
	 *             the security exception
	 */
	@SuppressWarnings("unchecked")
	protected Object createPureBeanInstance(Selector targetSelector,
			Class<?> targetClass, Definition definition, Provider provider,
			boolean runtimeLoading) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {

		Object result = null;
		CoreInstantiationSelector selector = (CoreInstantiationSelector) targetSelector;
		CachedConstructor cons = null;
		MetaDefObject metaObj = new MetaDefObject();
		boolean invocation = findInOutNonRuntime(metaObj, definition);
		if (!targetClass.isInterface() && !targetClass.isAnnotation()) {
			metaObj = new MetaDefObject();
			invocation = findInOutNonRuntime(metaObj, definition);
			if (invocation) {
				MethodInterceptor inoutInterceptor = InterceptorUtils
						.createsBothInOutInterceptor(definition, provider,
								runtimeLoading);
				net.sf.cglib.proxy.MethodInterceptor intercept = new MethodInterceptorCallback(
						inoutInterceptor);
				final List<Method> methodList = new ArrayList<Method>();
				Enhancer.getMethods(targetClass, null, methodList);
				cons = createConstructionProxy(selector, selector.getType(),
						intercept, methodList, metaObj);
			}
			else {
				cons = createConstructionProxy(selector, metaObj);
			}
		}
		else {
			if (targetClass.isAnnotation()) {
				MethodInterceptor annotationInterceptor = InterceptorUtils
						.createsAnnotationAroundAdviceMethodInterceptor(
								definition, provider, runtimeLoading,
								(Class<? extends Annotation>) targetClass);
				net.sf.cglib.proxy.MethodInterceptor intercept = new MethodInterceptorCallback(
						annotationInterceptor);
				List<Method> methodList = definition.getMethodsAnnotatedWith(
						Inject.class, Outject.class);
				cons = createConstructionProxy(selector, targetClass,
						intercept, methodList, metaObj);
			}
			else if (targetClass.isInterface())
				throw new InOutDependencyException(
						"The ["
								+ targetClass
								+ "] is an interface and is not intercepted, "
								+ "or current provider does not support this kind of bean instantiation!"
								+ " Can not build bean instance from this interface! ");
		}
		if (cons != null) {
			selector.getCachingList().put(definition, cons);
			result = cons.newInstance(selector.getArgs());
		}
		prepareSingletonBean(selector, provider, result);
		CommonFactory.singleton().executeProcessAfterBeanCreated(targetClass,
				metaObj, provider, result, definition);
		return result;
	}

	/**
	 * Executes advice.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @param aspectPair
	 *            the aspect pair
	 */
	protected void executesAdvice(Definition definition, Method method,
			Provider provider, boolean runtimeLoading,
			MethodAspectPair aspectPair) {

		// perform around advice
		if (InterceptorUtils.hasAroundAvice(definition, method)) {
			MethodInterceptor around = InterceptorUtils
					.createsAroundMethodInterceptor(definition, provider,
							runtimeLoading);
			aspectPair.add(0, around);
		}
		// perform after advice
		if (InterceptorUtils.hasAfterReturningAvice(definition, method)) {
			MethodInterceptor after = InterceptorUtils
					.createsAfterStackInterceptor(definition, provider,
							runtimeLoading);
			aspectPair.add(0, after);
		}
		// perform before advice
		if (InterceptorUtils.hasBeforedAvice(definition, method)) {
			MethodInterceptor before = InterceptorUtils
					.createsBeforeStackInterceptor(definition, provider,
							runtimeLoading);
			aspectPair.add(0, before);
		}
		// perform throws advice
		if (InterceptorUtils.hasThrowsAvice(definition, method)) {
			MethodInterceptor throwz = InterceptorUtils
					.createsThrowsAdviceStackInterceptor(definition, provider,
							runtimeLoading);
			aspectPair.add(0, throwz);
		}
	}

	/**
	 * Executes in out.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @param methodAspectList
	 *            the method aspect list
	 * @param invocationINOUT
	 *            the invocation inout
	 */
	@SuppressWarnings("unchecked")
	protected void executesInOut(Class<?> targetClass, Definition definition,
			Provider provider, boolean runtimeLoading,
			Map<Method, MethodAspectPair> methodAspectList,
			boolean invocationINOUT) {

		// if target class is annotation type
		if (targetClass.isAnnotation()) {
			MethodInterceptor annoInterceptor = InterceptorUtils
					.createsAnnotationAroundAdviceMethodInterceptor(definition,
							provider, runtimeLoading,
							(Class<? extends Annotation>) targetClass);
			Method[] methodArray = ReflectUtils
					.getAllDeclaredMethods(targetClass);
			for (int i = 0; i < methodArray.length; i++) {
				if (methodAspectList.containsKey(methodArray[i]))
					methodAspectList.get(methodArray[i]).interceptors.add(0,
							annoInterceptor);
				else
					methodAspectList.put(methodArray[i], new MethodAspectPair(
							methodArray[i], annoInterceptor));
			}
		}
		else {
			// perform invocation in/out
			MethodInterceptor inOutInterceptor = null;
			if (invocationINOUT) {
				inOutInterceptor = InterceptorUtils
						.createsBothInOutInterceptor(definition, provider,
								runtimeLoading);
				Method[] methodArray = ReflectUtils.getDeclaredMethods(
						targetClass, false, false);
				for (int i = 0; i < methodArray.length; i++) {
					if (methodAspectList.containsKey(methodArray[i]))
						methodAspectList.get(methodArray[i]).interceptors.add(
								0, inOutInterceptor);
					else
						methodAspectList.put(methodArray[i],
								new MethodAspectPair(methodArray[i],
										inOutInterceptor));
				}
			}
		}
	}

	/**
	 * Prepares singleton bean.
	 * 
	 * @param selector
	 *            the selector
	 * @param provider
	 *            the provider
	 * @param result
	 *            the result
	 */
	public static void prepareSingletonBean(CoreInstantiationSelector selector,
			Provider provider, Object result) {

		String scopeName = Utils.createScopeName(selector.getType(), selector
				.getTargetClass(), selector.getDefinition(), selector
				.getReferenceName());
		Map<String, ScopeInstance> scopeList = provider.getObjectBeanFactory()
				.getScopeList();
		ScopeInstance scope = null;
		synchronized (scopeList) {
			scope = scopeList.get(scopeName);
		}
		if (scope != null && scope.equals(Scope.SINGLETON)) {
			synchronized (provider.getObjectBeanFactory().getMapDirectList()) {
				provider.getObjectBeanFactory().getMapDirectList().put(
						scopeName, result);
			}
		}
	}
}
