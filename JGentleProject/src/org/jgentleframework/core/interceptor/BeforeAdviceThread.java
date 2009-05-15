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

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.context.aop.advice.MethodBeforeAdvice;
import org.jgentleframework.core.intercept.AbstractInterceptedAdviceInvocation;

/**
 * The Class BeforeAdviceThread.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 2, 2008 8:41:26 AM
 * @see MethodBeforeAdvice
 */
class BeforeAdviceThread implements Runnable {
	/** The advice. */
	MethodBeforeAdvice	advice;
	/** The invocation. */
	MethodInvocation	invocation;

	/**
	 * Instantiates a new before advice thread.
	 * 
	 * @param advice
	 *            the {@link MethodBeforeAdvice}
	 * @param invocation
	 *            the invocation
	 */
	public BeforeAdviceThread(MethodBeforeAdvice advice,
			MethodInvocation invocation) {

		this.advice = advice;
		this.invocation = invocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		try {
			advice.before(new AbstractInterceptedAdviceInvocation(invocation) {
				@Override
				public Object proceed() throws Throwable {

					return null;
				}
			});
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
