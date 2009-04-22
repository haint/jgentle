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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.context.beans.annotation.AlwaysReload;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.context.support.InstantiationSelector;
import org.jgentleframework.context.support.InstantiationSelectorImpl;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.intercept.JGentleFastClass;
import org.jgentleframework.core.intercept.JGentleNamingPolicy;
import org.jgentleframework.core.intercept.MethodInterceptorStackCallback;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.interceptor.InterceptorUtils;
import org.jgentleframework.core.provider.ServiceClass;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.data.NullClass;

/**
 * This is core processor of JGentle system which is a part of JGentle kernel,
 * is responsible for bean instantiation. All core services of JGentle are
 * deployed in {@link CoreProcessorImpl}, includes IoC, AOH, AOP ...
 * {@link CoreProcessorImpl}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 11, 2007
 * @see ServiceClass
 */
public class CoreProcessorImpl extends AbstractProcesserChecker implements
		CoreProcessor {
	/** The definition manager. */
	private DefinitionManager	definitionManager	= null;

	/** The current {@link Provider}. */
	private final Provider		provider;

	/**
	 * Constructor.
	 * 
	 * @param provider
	 *            the current (provider)
	 */
	public CoreProcessorImpl(Provider provider) {

		this.provider = provider;
		this.definitionManager = this.provider.getDefinitionManager();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.support.CoreProcessor#getProvider()
	 */
	@Override
	public Provider getProvider() {

		return this.provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.factory.support.CoreProcessor#getDefinitionManager
	 * ()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return this.definitionManager;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.factory.support.CoreProcessor#handle(org.
	 * jgentleframework.context.support.Selector, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object handle(Selector targetSelector, Object previousResult)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {

		Object result = null;
		Definition definition = targetSelector.getDefinition();
		Class<?> targetClass = targetSelector.getTargetClass();
		CoreInstantiationSelector selector = (CoreInstantiationSelector) targetSelector;
		boolean runtimeLoading = definition != null
				&& definition.isAnnotationPresent(AlwaysReload.class) ? definition
				.getAnnotation(AlwaysReload.class).value()
				: false;
		// find in cache
		if (cachingList.containsKey(definition)) {
			CachedConstructor cons = cachingList.get(definition);
			int hashcodeID = cons.hashcodeID();
			if (hashcodeID == (definition.hashCode() ^ cons.hashCode())) {
				result = cons.newInstance(selector.getArgs());
				// executes process after bean is created
				MetaDefObject metaObj = new MetaDefObject();
				findInOutNonRuntime(metaObj, definition);
				prepareSingletonBean(selector, provider, result);
				CommonFactory.singleton().executeProcessAfterBeanCreated(targetClass,
						metaObj, provider, result, definition);
				return result;
			}
		}
		// create bean instance
		if (targetSelector instanceof InstantiationSelectorImpl) {
			InstantiationSelector instSelector = (InstantiationSelector) targetSelector;
			// nếu bean đã được khởi tạo 1 phần trước đó
			if (previousResult != null
					&& (targetClass.isAnnotation() || targetClass.isInterface())) {
				throw new InterceptionException(
						"Bean instantiation is not supported or completed if target class is "
								+ "annotation or interface and if at least one 'instantiation' interceptor "
								+ "has instantiated the corresponding 'object bean'");
			}
			else {
				HashMap<Method, MethodAspectPair> methodAspectList = new HashMap<Method, MethodAspectPair>();
				HashMap<Interceptor, Matcher<Definition>> map = instSelector
						.getMapMatcherInterceptor();
				boolean invocationINOUT = false;
				ElementAspectFactory elementAspectFactory = new ElementAspectFactory();
				/*
				 * perform method interceptor
				 */
				MethodInterceptor[] methodIcptLst = instSelector
						.getMethodInterceptors();
				Set<Method> methods = definition.getAllAnnotatedMethods();
				if (methods != null) {
					for (Method method : methods) {
						invocationINOUT = invocationINOUT == false ? InterceptorUtils
								.isInvocation(definition, method)
								: invocationINOUT;
						// creates Aspect Pair
						MethodAspectPair aspectPair = elementAspectFactory
								.analysesMethod(methodIcptLst, map, definition,
										method);
						// performs advice
						executesAdvice(definition, method, provider,
								runtimeLoading, aspectPair);
						if (aspectPair.hasInterceptors())
							methodAspectList.put(method, aspectPair);
					}
				}
				// Perform field interceptor
				boolean bool = executesFieldInterceptor(targetClass,
						instSelector, definition, elementAspectFactory,
						invocationINOUT, map, methodAspectList);
				if (bool == true)
					invocationINOUT = true;
				// perform invocation in/out or annotation attributes injection
				executesInOut(targetClass, definition, provider,
						runtimeLoading, methodAspectList, invocationINOUT);
				// Create callbacks.
				final List<Method> methodList = new ArrayList<Method>();
				Enhancer.getMethods(targetClass, null, methodList);
				Callback[] callbacks = new Callback[methodList.size()];
				Class<? extends Callback>[] callbackTypes = new Class[methodList
						.size()];
				for (int i = 0; i < methodList.size(); i++) {
					MethodAspectPair pair = methodAspectList.get(methodList
							.get(i));
					if (pair == null) {
						callbacks[i] = NoOp.INSTANCE;
						callbackTypes[i] = NoOp.class;
					}
					else {
						callbacks[i] = new MethodInterceptorStackCallback(
								pair.getMethod(),
								previousResult,
								pair.interceptors
										.toArray(new MethodInterceptor[pair.interceptors
												.size()]));
						callbackTypes[i] = net.sf.cglib.proxy.MethodInterceptor.class;
					}
				}
				// Nếu không tìm thấy bất kì chỉ định khởi tạo interceptor nào
				// ==> tự động return null.
				if (methodAspectList.size() == 0 && (targetClass.isInterface())) {
					return NullClass.class;
				}
				// Create the proxied class.
				Enhancer enhancer = new Enhancer();
				if (targetClass.isAnnotation() || targetClass.isInterface())
					enhancer.setInterfaces(new Class<?>[] { targetClass });
				else
					enhancer.setSuperclass(targetClass);
				enhancer.setCallbackFilter(new CallbackFilter() {
					public int accept(Method method) {

						return methodList.indexOf(method);
					}
				});
				enhancer.setCallbackTypes(callbackTypes);
				enhancer.setUseFactory(false);
				enhancer.setUseCache(true);
				enhancer.setNamingPolicy(new JGentleNamingPolicy());
				Class<?> proxied = enhancer.createClass();
				Enhancer.registerStaticCallbacks(proxied, callbacks);
				CachedConstructor cons = createConstructionProxy(definition,
						proxied, instSelector.getArgTypes());
				this.cachingList.put(definition, cons);
				result = cons.newInstance(instSelector.getArgs());
				// executes process after bean is created
				MetaDefObject metaObj = new MetaDefObject();
				findInOutNonRuntime(metaObj, definition);
				prepareSingletonBean(selector, provider, result);
				CommonFactory.singleton().executeProcessAfterBeanCreated(targetClass,
						metaObj, provider, result, definition);
			}
		}
		else if (targetSelector instanceof CoreInstantiationSelectorImpl
				&& !(targetSelector instanceof InstantiationSelectorImpl)) {
			result = createPureBeanInstance(targetSelector, targetClass,
					definition, provider, runtimeLoading);
		}
		return result;
	}

	/**
	 * Creates a construction proxy given a class and parameter types.
	 */
	CachedConstructor createConstructionProxy(final Definition definition,
			Class<?> clazz, Class<?>[] parameterTypes) {

		FastClass fastClass = JGentleFastClass.create(clazz);
		final FastConstructor fastConstructor = fastClass
				.getConstructor(parameterTypes);
		return new CachedConstructor() {
			public Object newInstance(Object... arguments)
					throws InvocationTargetException {

				return fastConstructor.newInstance(arguments);
			}

			@Override
			public int hashcodeID() {

				return definition.hashCode() ^ this.hashCode();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.factory.support.AbstractProcesserChecker#
	 * createConstructionProxy
	 * (org.jgentleframework.context.support.CoreInstantiationSelector)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected CachedConstructor createConstructionProxy(
			CoreInstantiationSelector selector) {

		Assertor.notNull(selector, "The selector ["
				+ CoreInstantiationSelector.class.toString()
				+ "] must not be null !");
		// Create
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(selector.getTargetClass());
		Callback[] callbacks = new Callback[] { NoOp.INSTANCE };
		Class<? extends Callback>[] callbackTypes = new Class[] { NoOp.class };
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {

				return 0;
			}
		});
		enhancer.setCallbackTypes(callbackTypes);
		enhancer.setUseFactory(false);
		enhancer.setUseCache(true);
		enhancer.setNamingPolicy(new JGentleNamingPolicy());
		Class<?> proxied = enhancer.createClass();
		// Store callbacks.
		Enhancer.registerStaticCallbacks(proxied, callbacks);
		CachedConstructor cons = createConstructionProxy(selector
				.getDefinition(), proxied, selector.getArgTypes());
		return cons;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.factory.support.AbstractProcesserChecker#
	 * createConstructionProxy
	 * (org.jgentleframework.context.support.CoreInstantiationSelector,
	 * java.lang.Class, net.sf.cglib.proxy.MethodInterceptor, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected CachedConstructor createConstructionProxy(
			final CoreInstantiationSelector selector, Class<?> interfaze,
			net.sf.cglib.proxy.MethodInterceptor interceptor,
			final List<Method> methodList) {

		Assertor.notNull(interceptor,
				"The given interceptor must not be null !");
		Assertor.notNull(interceptor,
				"The given method list must not be null !");
		// Create
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(selector.getTargetClass());
		if (interfaze != null && interfaze.isAnnotation())
			enhancer
					.setInterfaces(new Class<?>[] { interfaze, Annotation.class });
		else if (interfaze != null && interfaze.isInterface())
			enhancer.setInterfaces(new Class<?>[] { interfaze });
		Callback[] callbacks = new Callback[] { NoOp.INSTANCE, interceptor };
		Class<? extends Callback>[] callbackTypes = new Class[] { NoOp.class,
				net.sf.cglib.proxy.MethodInterceptor.class };
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {

				if (methodList != null && methodList.contains(method)
						&& !selector.getTargetClass().isAnnotation())
					return 1;
				else if (selector.getTargetClass().isAnnotation())
					return 1;
				else
					return 0;
			}
		});
		enhancer.setCallbackTypes(callbackTypes);
		enhancer.setUseFactory(false);
		enhancer.setUseCache(true);
		enhancer.setNamingPolicy(new JGentleNamingPolicy());
		Class<?> proxied = enhancer.createClass();
		// Store callbacks.
		Enhancer.registerStaticCallbacks(proxied, callbacks);
		CachedConstructor cons = createConstructionProxy(selector
				.getDefinition(), proxied, selector.getArgTypes());
		return cons;
	}
}
