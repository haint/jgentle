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
package org.jgentleframework.core.intercept;

/**
 * Superclass for all interception infrastructure exceptions.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 4, 2008
 */
public class InterceptionException extends RuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 5034843579008688502L;

	/**
	 * Instantiates a new interception exception.
	 */
	public InterceptionException() {

		super();
	}

	/**
	 * Instantiates a new interception exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public InterceptionException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new interception exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public InterceptionException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new interception exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public InterceptionException(Throwable cause) {

		super(cause);
	}
}
