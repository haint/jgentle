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
package org.jgentleframework.core;

/**
 * The Class JGentleRuntimeException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 22, 2007
 * @see RuntimeException
 */
public class JGentleRuntimeException extends RuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 7751295094743604631L;

	/**
	 * Instantiates a new jgentle runtime exception.
	 */
	public JGentleRuntimeException() {

		super();
	}

	/**
	 * Instantiates a new jgentle runtime exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public JGentleRuntimeException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new jgentle runtime exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public JGentleRuntimeException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new jgentle runtime exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public JGentleRuntimeException(Throwable cause) {

		super(cause);
	}
}
