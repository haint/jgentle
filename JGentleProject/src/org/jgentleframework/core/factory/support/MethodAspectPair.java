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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * The Class MethodAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 11, 2008
 * @see MethodInterceptor
 */
class MethodAspectPair {
	/** The method. */
	final Method			method;
	/** The interceptors. */
	List<MethodInterceptor>	interceptors;

	/**
	 * Instantiates a new method aspect pair.
	 * 
	 * @param method
	 *            the method
	 */
	public MethodAspectPair(Method method) {

		this.method = method;
		this.interceptors = new LinkedList<MethodInterceptor>();
	}

	/**
	 * Instantiates a new method aspect pair.
	 * 
	 * @param method
	 *            the method
	 * @param interceptors
	 *            the interceptors
	 */
	public MethodAspectPair(Method method, MethodInterceptor... interceptors) {

		this.method = method;
		addAll(Arrays.asList(interceptors));
	}

	/**
	 * Instantiates a new method aspect pair.
	 * 
	 * @param method
	 *            the method
	 * @param interceptors
	 *            the interceptors
	 */
	public MethodAspectPair(Method method, List<MethodInterceptor> interceptors) {

		this.method = method;
		addAll(interceptors);
	}

	/**
	 * Adds all method intercepters present at given {@link List}.
	 * 
	 * @param interceptors
	 *            the list containing the interceptors.
	 */
	public void addAll(List<MethodInterceptor> interceptors) {

		if (this.interceptors == null) {
			this.interceptors = new LinkedList<MethodInterceptor>();
		}
		this.interceptors.addAll(interceptors);
	}

	/**
	 * Adds the given method interceptor
	 * 
	 * @param index
	 *            the index
	 * @param interceptor
	 *            the interceptor
	 */
	public void add(int index, MethodInterceptor interceptor) {

		if (this.interceptors == null) {
			this.interceptors = new LinkedList<MethodInterceptor>();
		}
		this.interceptors.add(index, interceptor);
	}

	/**
	 * Adds the given method interceptor
	 * 
	 * @param interceptor
	 *            the interceptor
	 */
	public void add(MethodInterceptor interceptor) {

		if (this.interceptors == null) {
			this.interceptors = new LinkedList<MethodInterceptor>();
		}
		this.interceptors.add(interceptor);
	}

	/**
	 * Checks for interceptors.
	 * 
	 * @return true, if successful
	 */
	boolean hasInterceptors() {

		return interceptors != null && !interceptors.isEmpty();
	}

	/**
	 * Gets the method.
	 * 
	 * @return the method
	 */
	public Method getMethod() {

		return method;
	}
}
