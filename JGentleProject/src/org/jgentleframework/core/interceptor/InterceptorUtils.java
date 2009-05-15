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
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.aopalliance.intercept.FieldInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.configure.aopweaving.annotation.After;
import org.jgentleframework.configure.aopweaving.annotation.Around;
import org.jgentleframework.configure.aopweaving.annotation.Before;
import org.jgentleframework.configure.aopweaving.annotation.Throws;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;

/**
 * The Class InterceptorUtils.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 17, 2008
 * @see MethodInterceptor
 * @see FieldInterceptor
 */
public abstract class InterceptorUtils {
	/**
	 * Creates field stack method interceptor.
	 * 
	 * @param field
	 *            the field
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsFieldStackMethodInterceptor(
			Field field) {

		return new FieldStackMethodInterceptor(field);
	}

	/**
	 * Creates after stack interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsAfterStackInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading) {

		return new AfterReturningStackMethodInterceptor(definition, provider,
				runtimeLoading);
	}

	/**
	 * Creates annotation around advice method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @param targetClass
	 *            the target class
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsAnnotationAroundAdviceMethodInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading,
			Class<? extends Annotation> targetClass) {

		return new AnnotationAroundAdviceMethodInterceptor(definition,
				provider, runtimeLoading, targetClass);
	}

	/**
	 * Creates around method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsAroundMethodInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading) {

		return new AroundMethodInterceptor(definition, provider, runtimeLoading);
	}

	/**
	 * Creates before stack interceptor.
	 * 
	 * @param definition
	 *            the given {@link Definition}
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return {@link MethodInterceptor}
	 */
	public static MethodInterceptor createsBeforeStackInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading) {

		return new BeforeAdviceStackMethodInterceptor(definition, provider,
				runtimeLoading);
	}

	/**
	 * Creates a new InvocationInOutjectionMethodInterceptor.
	 * 
	 * @param definition
	 *            the given {@link Definition}
	 * @param provider
	 *            the given {@link Provider}
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsBothInOutInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading) {

		return new InvocationInOutjectionMethodInterceptor(definition,
				provider, runtimeLoading);
	}

	/**
	 * Creates throws advice stack interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 * @return the method interceptor
	 */
	public static MethodInterceptor createsThrowsAdviceStackInterceptor(
			Definition definition, Provider provider, boolean runtimeLoading) {

		try {
			return new ThrowsAdviceStackMethodInterceptor(definition, provider,
					runtimeLoading);
		}
		catch (ClassNotFoundException e) {
			InterceptionException ex = new InterceptionException(
					"Could not created 'Throws Advice Interceptor'");
			ex.initCause(e);
			throw ex;
		}
	}

	/**
	 * Checks for after returning avice.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @return true, if successful
	 */
	public static boolean hasAfterReturningAvice(Definition definition,
			Method method) {

		Definition defMethod = definition.getMemberDefinition(method);
		if (defMethod != null)
			return defMethod.isAnnotationPresent(After.class);
		else
			return false;
	}

	/**
	 * Checks for around avice.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @return true, if successful
	 */
	public static boolean hasAroundAvice(Definition definition, Method method) {

		Definition defMethod = definition.getMemberDefinition(method);
		if (defMethod != null)
			return defMethod.isAnnotationPresent(Around.class);
		else
			return false;
	}

	/**
	 * Checks for befored avice.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @return true, if successful
	 */
	public static boolean hasBeforedAvice(Definition definition, Method method) {

		Definition defMethod = definition.getMemberDefinition(method);
		if (defMethod != null)
			return defMethod.isAnnotationPresent(Before.class);
		else
			return false;
	}

	/**
	 * Checks for throws avice.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @return true, if successful
	 */
	public static boolean hasThrowsAvice(Definition definition, Method method) {

		Definition defMethod = definition.getMemberDefinition(method);
		if (defMethod != null)
			return defMethod.isAnnotationPresent(Throws.class);
		else
			return false;
	}

	/**
	 * Checks if is invocation.
	 * 
	 * @param definition
	 *            the definition
	 * @param field
	 *            the field
	 * @return true, if is invocation
	 */
	public static boolean isInvocation(Definition definition, Field field) {

		Definition defField = definition.getMemberDefinition(field);
		// checking invocation in out
		if (defField != null && defField.isAnnotationPresent(Inject.class)) {
			Inject inject = defField.getAnnotation(Inject.class);
			return inject.invocation();
		}
		else if (defField != null
				&& defField.isAnnotationPresent(Outject.class)) {
			Outject outject = defField.getAnnotation(Outject.class);
			return outject.invocation();
		}
		return false;
	}

	/**
	 * Checks if is invocation.
	 * 
	 * @param definition
	 *            the definition
	 * @param method
	 *            the method
	 * @return true, if is invocation
	 */
	public static boolean isInvocation(Definition definition, Method method) {

		Definition defMethod = definition.getMemberDefinition(method);
		// checking invocation in out
		if (defMethod != null && defMethod.isAnnotationPresent(Inject.class)
				&& Utils.isSetter(method)) {
			Inject inject = defMethod.getAnnotation(Inject.class);
			return inject.invocation();
		}
		else if (defMethod != null
				&& defMethod.isAnnotationPresent(Outject.class)
				&& Utils.isGetter(method)) {
			Outject outject = defMethod.getAnnotation(Outject.class);
			return outject.invocation();
		}
		return false;
	}

	/**
	 * Checks if is runtime loading.
	 * 
	 * @param interceptor
	 *            the interceptor
	 * @return true, if is runtime loading
	 * @see RuntimeLoading
	 */
	public static boolean isRuntimeLoading(Interceptor interceptor) {

		if (ReflectUtils.isCast(RuntimeLoading.class, interceptor)) {
			RuntimeLoading runtime = (RuntimeLoading) interceptor;
			return runtime.isRuntimeLoading();
		}
		return false;
	}
}
