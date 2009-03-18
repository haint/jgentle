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
package org.jgentleframework.core.interceptor;

import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.aopweaving.annotation.Before;
import org.jgentleframework.context.aop.advice.MethodBeforeAdvice;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.RequiredException;
import org.jgentleframework.core.intercept.AbstractInterceptedAdviceInvocation;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Intercepts with a stack of {@link MethodBeforeAdvice}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 26, 2008
 * @see MethodInterceptor
 * @see MethodBeforeAdvice
 */
class BeforeAdviceStackMethodInterceptor extends AbstractBeforeAdvice implements
		RuntimeLoading {
	/** The before advices. */
	List<MethodBeforeAdvice>	beforeAdviceList	= new LinkedList<MethodBeforeAdvice>();

	/** The provider. */
	final Provider				provider;

	/** The runtime loading. */
	private boolean				runtimeLoading		= false;

	/**
	 * Instantiates a new before advice method interceptor.
	 * 
	 * @param definition
	 *            the definition
	 * @param provider
	 *            the provider
	 * @param runtimeLoading
	 *            the runtime loading
	 */
	public BeforeAdviceStackMethodInterceptor(Definition definition,
			Provider provider, boolean runtimeLoading) {

		super(definition);
		this.provider = provider;
		this.runtimeLoading = runtimeLoading;
	}

	/**
	 * Find advice instances.
	 * 
	 * @param before
	 *            the before
	 * @param beforeAdviceList
	 *            the before advice list
	 */
	private void findAdviceInstances(Before before,
			List<MethodBeforeAdvice> beforeAdviceList) {

		List<MethodBeforeAdvice> list = new LinkedList<MethodBeforeAdvice>();
		String[] objStr = before.value();
		if (objStr != null && !(objStr.length == 1 && objStr[0].isEmpty())) {
			for (String str : objStr) {
				if (!str.isEmpty()) {
					Object obj = this.provider.getBean(str);
					if (obj != null)
						if (ReflectUtils.isCast(MethodBeforeAdvice.class, obj)) {
							if (!list.contains(obj))
								list.add((MethodBeforeAdvice) obj);
						}
						else {
							throw new InterceptionException(
									"The registered object of before advice can not be casted to '"
											+ MethodBeforeAdvice.class + "'!");
						}
					else {
						if (before.required()) {
							throw new RequiredException(
									"The reference of Method Before Advice instance must not be null!");
						}
					}
				}
			}
		}
		this.beforeAdviceList.addAll(list);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		boolean parallel = false;
		// checking runtime loading
		Definition defMethod = definition.getMemberDefinition(invocation
				.getMethod());
		Before before = defMethod != null
				&& defMethod.isAnnotationPresent(Before.class) ? defMethod
				.getAnnotation(Before.class) : null;
		if (before != null) {
			if (before.invocation() || runtimeLoading
					|| this.beforeAdviceList.isEmpty()) {
				this.beforeAdviceList.clear();
				findAdviceInstances(before, this.beforeAdviceList);
			}
			parallel = before.parallel();
			new InterceptedMethodBeforeAdviceInvocation(invocation, parallel)
					.proceed();
		}
		return invocation.proceed();
	}

	/**
	 * The Class InterceptedMethodBeforeAdviceInvocation.
	 */
	class InterceptedMethodBeforeAdviceInvocation extends
			AbstractInterceptedAdviceInvocation implements MethodInvocation {
		/** The index. */
		int		index		= -1;

		/** The parallel. */
		boolean	parallel	= false;

		/**
		 * Instantiates a new intercepted method before advice invocation.
		 * 
		 * @param invocation
		 *            the invocation
		 * @param parallel
		 *            the parallel
		 */
		public InterceptedMethodBeforeAdviceInvocation(
				MethodInvocation invocation, boolean parallel) {

			super(invocation);
			this.parallel = parallel;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#proceed()
		 */
		public Object proceed() throws Throwable {

			if (parallel) {
				if (beforeAdviceList != null && !beforeAdviceList.isEmpty()) {
					for (MethodBeforeAdvice advice : beforeAdviceList) {
						Thread thread = new Thread(new BeforeAdviceThread(
								advice, this.invocation));
						thread.start();
					}
				}
			}
			else {
				try {
					if (beforeAdviceList != null && !beforeAdviceList.isEmpty()) {
						index++;
						if (index != beforeAdviceList.size())
							beforeAdviceList.get(index).before(this);
					}
				}
				finally {
					index--;
				}
			}
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.interceptor.RuntimeLoading#setRuntimeLoading(boolean)
	 */
	@Override
	public void setRuntimeLoading(boolean runtimeLoading) {

		this.runtimeLoading = runtimeLoading;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.interceptor.RuntimeLoading#isRuntimeLoading()
	 */
	@Override
	public boolean isRuntimeLoading() {

		return runtimeLoading;
	}
}
