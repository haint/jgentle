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

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * The Class MatchingException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 28, 2009
 */
public class MatchingException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 8923948882960126475L;

	/**
	 * Instantiates a new matching exception.
	 */
	public MatchingException() {

		super();
	}

	/**
	 * Instantiates a new matching exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public MatchingException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new matching exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MatchingException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new matching exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public MatchingException(Throwable cause) {

		super(cause);
	}
}
