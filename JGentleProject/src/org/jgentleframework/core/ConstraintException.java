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
 * The Class ConstraintException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 1, 2007
 */
public class ConstraintException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -5938419793170473346L;

	/**
	 * Instantiates a new constraint exception.
	 */
	public ConstraintException() {

		super();
	}

	/**
	 * Instantiates a new constraint exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public ConstraintException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new constraint exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ConstraintException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new constraint exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ConstraintException(Throwable cause) {

		super(cause);
	}
}
