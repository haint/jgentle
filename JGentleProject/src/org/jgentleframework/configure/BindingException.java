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
package org.jgentleframework.configure;

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * The Class BindingException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class BindingException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 8240205856369616177L;

	/**
	 * Instantiates a new binding exception.
	 */
	public BindingException() {

		super();
	}

	/**
	 * Instantiates a new binding exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public BindingException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new binding exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public BindingException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new binding exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public BindingException(Throwable cause) {

		super(cause);
	}
}
