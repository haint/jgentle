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
package org.jgentleframework.integration.remoting;

import org.jgentleframework.core.JGentelIllegalArgumentException;

/**
 * The Class RemotingException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 26, 2008
 */
public class RemotingException extends JGentelIllegalArgumentException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7820208802741116092L;

	/**
	 * Instantiates a new remoting exception.
	 */
	public RemotingException() {

		super();
	}

	/**
	 * Instantiates a new remoting exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RemotingException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new remoting exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RemotingException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new remoting exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RemotingException(Throwable cause) {

		super(cause);
	}
}
