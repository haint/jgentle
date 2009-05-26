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
package org.jgentleframework.integration.scripting;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.intercept.BasicMethodInvocation;
import org.jgentleframework.core.intercept.BeanInstantiationInterceptor;
import org.jgentleframework.core.intercept.ObjectInstantiation;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.integration.scripting.annotation.ScriptingInject;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class ScriptingInstantiationInterceptor.
 * 
 * @author gnut
 */
public class ScriptingInstantiationInterceptor implements
		BeanInstantiationInterceptor, ProviderAware {
	/** The log. */
	private final Log			log					= LogFactory
															.getLog(getClass());

	/** The definition manager. */
	private DefinitionManager	definitionManager	= null;

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.intercept.BeanInstantiationInterceptor#
	 * isSupportedByCore()
	 */
	@Override
	public boolean isSupportedByCore() {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.intercept.InstantiationInterceptor#instantiate
	 * (org.jgentleframework.core.intercept.ObjectInstantiation)
	 */
	@Override
	public Object instantiate(ObjectInstantiation oi) throws Throwable {

		Object result = null;
		Class<?> target = oi.getTargetClass();
		Definition definition = this.definitionManager.getDefinition(target);
		if (definition.isAnnotationPresent(ScriptingInject.class)) {
			if (!target.isInterface()) {
				if (log.isFatalEnabled()) {
					log.fatal("Could not binding to Scripting service",
							new ScriptException(
									"Target class must be a interface!"));
				}
			}
			final ScriptingInject scriptingInject = definition
					.getAnnotation(ScriptingInject.class);
			Object previous = oi.getPreviousResult();
			if (previous == null) {
				ScriptEngineManager engineManager = new ScriptEngineManager();
				ScriptEngine engine = engineManager
						.getEngineByName(scriptingInject.lang().getType());
				if (engine == null) {
					if (log.isFatalEnabled()) {
						log.fatal("Script engine with name : "
								+ scriptingInject.lang().getType()
								+ " is not found!", new ScriptException(
								"Script engine with name : "
										+ scriptingInject.lang().getType()
										+ " is not found!"));
					}
					throw new ScriptException("Script engine with name : "
							+ scriptingInject.lang().getType()
							+ " is not found!");
				}
				File parentFile = null;
				if (!(PathType.CLASSPATH.equals(scriptingInject.pathType()) || PathType.CLASSPATH
						.equals(scriptingInject.pathType()))) {
					parentFile = new File(scriptingInject.pathType().getType());
				}
				File sourceFile = null;
				if (parentFile != null) {
					sourceFile = new File(parentFile, scriptingInject
							.scriptFile());
				}
				else {
					if (PathType.CLASSPATH.equals(scriptingInject.pathType())) {
						sourceFile = new File(
								ScriptingInstantiationInterceptor.class
										.getResource(
												scriptingInject.scriptFile())
										.toURI());
					}
					else {
						sourceFile = new File(scriptingInject.scriptFile());
					}
				}
				engine.eval(new FileReader(sourceFile));
				Invocable inv = (Invocable) engine;
				final Object scriptObject = inv.getInterface(target);
				result = scriptObject;
				final Enhancer enhancer = new Enhancer();
				enhancer.setInterfaces(new Class<?>[] { target });
				enhancer.setCallback(new MethodInterceptor() {
					@Override
					public Object intercept(Object obj, Method method,
							Object[] args, MethodProxy proxy) throws Throwable {

						if (method.getName().equals("hashCode")) {
							final int prime = 31;
							int result = 1;
							result = prime * result + super.hashCode();
							result = prime * result + enhancer.hashCode();
							return result;
						}
						else {
							MethodInvocation invocation = new BasicMethodInvocation(
									obj, method, args);
							return invoke(invocation, scriptObject);
						}
					}

					protected Object invoke(MethodInvocation invocation,
							Object stub) throws NoSuchMethodException,
							IllegalArgumentException, IllegalAccessException,
							InvocationTargetException {

						Object result = null;
						Method method = null;
						Method methodType = invocation.getMethod();
						Class<?> clazz = stub.getClass();
						Class<?>[] classType = ReflectUtils
								.getClassTypeOf(invocation.getArguments());
						method = ReflectUtils.getSupportedMethod(clazz,
								methodType.getName(), classType);
						result = method.invoke(stub, invocation.getArguments());
						return result;
					}
				});
				if (oi.args() != null && oi.argTypes() != null)
					result = enhancer.create(oi.argTypes(), oi.args());
				else
					result = enhancer.create();
				oi.setPreviousResult(result);
			}
			else {
				if (log.isFatalEnabled()) {
					log
							.fatal(
									"Does not support multible Instantiation Interceptor "
											+ "conjointly with Scripting Instantiation",
									new ScriptException(
											"Does not support multible Instantiation Interceptor!"));
				}
			}
			return oi.proceed();
		}
		else {
			if (log.isWarnEnabled()) {
				log.warn("The target interface is not annotated with ["
						+ ScriptingInject.class + "]");
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.beans.ProviderAware#setProvider(org.
	 * jgentleframework.context.injecting.Provider)
	 */
	@Override
	public void setProvider(Provider provider) {

		this.definitionManager = provider.getDefinitionManager();
	}
}
