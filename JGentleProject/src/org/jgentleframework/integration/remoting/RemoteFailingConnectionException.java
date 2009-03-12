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
 * The Class RemoteFailingConnectionException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 20, 2008
 */
public class RemoteFailingConnectionException extends
		JGentelIllegalArgumentException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 6088756415115783806L;

	/**
	 * Instantiates a new remote failing connection exception.
	 */
	public RemoteFailingConnectionException() {

		super();
	}

	/**
	 * Instantiates a new remote failing connection exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RemoteFailingConnectionException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new remote failing connection exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RemoteFailingConnectionException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new remote failing connection exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RemoteFailingConnectionException(Throwable cause) {

		super(cause);
	}
}
