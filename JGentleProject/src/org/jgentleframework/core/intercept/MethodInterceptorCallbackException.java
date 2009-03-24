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
package org.jgentleframework.core.intercept;

/**
 * The Class MethodInterceptorCallbackException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 22, 2009
 */
public class MethodInterceptorCallbackException extends Exception {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -2208728507911276251L;

	/**
	 * Instantiates a new method interceptor callback exception.
	 */
	public MethodInterceptorCallbackException() {

		super();
	}

	/**
	 * Instantiates a new method interceptor callback exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public MethodInterceptorCallbackException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new method interceptor callback exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MethodInterceptorCallbackException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new method interceptor callback exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public MethodInterceptorCallbackException(Throwable cause) {

		super(cause);
	}
}
