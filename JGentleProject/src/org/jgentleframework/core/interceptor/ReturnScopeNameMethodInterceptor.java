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

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

/**
 * The Class ReturnScopeNameMethodInterceptor.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 30, 2009
 */
public class ReturnScopeNameMethodInterceptor implements
		net.sf.cglib.proxy.MethodInterceptor {
	/** The scope name. */
	String	scopeName	= null;

	/**
	 * Instantiates a new return scope name method interceptor.
	 * 
	 * @param scopeName
	 *            the scope name
	 */
	public ReturnScopeNameMethodInterceptor(String scopeName) {

		this.scopeName = scopeName;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[],
	 * net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object arg0, Method arg1, Object[] arg2,
			MethodProxy arg3) throws Throwable {

		return scopeName;
	}
}
