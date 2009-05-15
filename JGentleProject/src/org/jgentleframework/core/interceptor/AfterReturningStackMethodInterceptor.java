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

import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.aopweaving.annotation.After;
import org.jgentleframework.context.aop.advice.AfterReturning;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.RequiredException;
import org.jgentleframework.core.intercept.AbstractInterceptedAdviceInvocation;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Intercepts with a stack of {@link After}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 26, 2008
 * @see MethodInterceptor
 * @see After
 */
class AfterReturningStackMethodInterceptor implements MethodInterceptor {
	/** The after advices. */
	List<AfterReturning>	afterAdvices	= new LinkedList<AfterReturning>();

	/** The provider. */
	final Provider			provider;

	/** The runtime loading. */
	private boolean			runtimeLoading	= false;

	/** The definition. */
	Definition				definition;

	/**
	 * Instantiates a new after returning stack method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 */
	public AfterReturningStackMethodInterceptor(Definition definition,
			Provider provider, boolean runtimeLoading) {

		Assertor.notNull(definition, "The given definition must not be null!");
		this.definition = definition;
		this.provider = provider;
		this.runtimeLoading = runtimeLoading;
	}

	/**
	 * Find advice instances.
	 * 
	 * @param after
	 *            the after
	 * @param afterAdviceList
	 *            the after advice list
	 */
	private void findAdviceInstances(After after,
			List<AfterReturning> afterAdviceList) {

		List<AfterReturning> list = new LinkedList<AfterReturning>();
		String[] objStr = after.value();
		if (objStr != null && !(objStr.length == 1 && objStr[0].isEmpty())) {
			for (String str : objStr) {
				if (!str.isEmpty()) {
					Object obj = this.provider.getBean(str);
					if (obj != null)
						if (ReflectUtils.isCast(AfterReturning.class, obj)) {
							if (!list.contains(obj))
								list.add((AfterReturning) obj);
						}
						else
							throw new InterceptionException(
									"The registered object of after advice can not be casted to '"
											+ AfterReturning.class + "'!");
					else {
						if (after.required()) {
							throw new RequiredException(
									"The reference of After Advice instance must not be null!");
						}
					}
				}
			}
		}
		this.afterAdvices.addAll(list);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		// checking runtime loading
		Definition defMethod = definition.getMemberDefinition(invocation
				.getMethod());
		After after = defMethod != null
				&& defMethod.isAnnotationPresent(After.class) ? defMethod
				.getAnnotation(After.class) : null;
		if (after != null) {
			if (after.invocation() || runtimeLoading
					|| this.afterAdvices.isEmpty()) {
				this.afterAdvices.clear();
				findAdviceInstances(after, this.afterAdvices);
			}
		}
		Object returnValue = invocation.proceed();
		new InterceptedMethodAfterReturningInvocation(invocation, returnValue)
				.proceed();
		return returnValue;
	}

	/**
	 * The Class InterceptedMethodAfterReturningInvocation.
	 */
	class InterceptedMethodAfterReturningInvocation extends
			AbstractInterceptedAdviceInvocation implements MethodInvocation {
		/** The index. */
		int		index		= -1;

		/** The return value. */
		Object	returnValue	= null;

		/**
		 * Instantiates a new intercepted method after returning invocation.
		 * 
		 * @param invocation
		 *            the invocation
		 * @param returnValue
		 *            the return value
		 */
		public InterceptedMethodAfterReturningInvocation(
				MethodInvocation invocation, Object returnValue) {

			super(invocation);
			this.returnValue = returnValue;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#proceed()
		 */
		@Override
		public Object proceed() throws Throwable {

			try {
				index++;
				if (index != afterAdvices.size())
					afterAdvices.get(index).afterReturning(returnValue, this);
			}
			finally {
				index--;
			}
			return null;
		}
	}
}
