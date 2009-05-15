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
package org.jgentleframework.integration.remoting;

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * The Class RemoteAccessException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 20, 2008
 */
public class RemoteAccessException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -8445599502913048181L;

	/**
	 * Instantiates a new remote access exception.
	 */
	public RemoteAccessException() {

		super();
	}

	/**
	 * Instantiates a new remote access exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RemoteAccessException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new remote access exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RemoteAccessException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new remote access exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RemoteAccessException(Throwable cause) {

		super(cause);
	}
}
