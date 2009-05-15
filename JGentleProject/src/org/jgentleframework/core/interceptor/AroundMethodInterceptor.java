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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.aopweaving.annotation.Around;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.RequiredException;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class AroundMethodInterceptor.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 15, 2008
 */
class AroundMethodInterceptor implements MethodInterceptor {
	/** The after advices. */
	MethodInterceptor	aroundrAdvice	= null;
	/** The provider. */
	final Provider		provider;
	/** The runtime loading. */
	private boolean		runtimeLoading	= false;
	/** The definition. */
	Definition			definition;

	/**
	 * Instantiates a new around method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 */
	public AroundMethodInterceptor(Definition definition, Provider provider,
			boolean runtimeLoading) {

		Assertor.notNull(definition, "The given definition must not be null!");
		this.definition = definition;
		this.provider = provider;
		this.runtimeLoading = runtimeLoading;
	}

	/**
	 * Find advice instances.
	 * 
	 * @param around
	 *            the around
	 */
	private void findAdviceInstances(Around around) {

		String objStr = around.value();
		if (objStr != null && !objStr.isEmpty()) {
			Object obj = this.provider.getBean(objStr);
			if (obj != null)
				if (ReflectUtils.isCast(MethodInterceptor.class, obj)) {
					this.aroundrAdvice = (MethodInterceptor) obj;
				}
				else
					throw new InterceptionException(
							"The registered object of around advice can not be casted to '"
									+ MethodInterceptor.class + "'!");
			else {
				if (around.required()) {
					throw new RequiredException(
							"The reference of Around Advice instance must not be null!");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Object result = null;
		// checking runtime loading
		Definition defMethod = definition.getMemberDefinition(invocation
				.getMethod());
		Around around = defMethod != null
				&& defMethod.isAnnotationPresent(Around.class) ? defMethod
				.getAnnotation(Around.class) : null;
		if (around != null) {
			if (around.invocation() || runtimeLoading
					|| this.aroundrAdvice == null) {
				findAdviceInstances(around);
			}
			if (this.aroundrAdvice != null)
				result = this.aroundrAdvice.invoke(invocation);
		}
		return result;
	}
}
