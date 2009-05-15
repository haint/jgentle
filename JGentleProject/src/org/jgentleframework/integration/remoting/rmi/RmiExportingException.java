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
package org.jgentleframework.integration.remoting.rmi;

import org.jgentleframework.integration.remoting.RemotingException;

/**
 * The Class RmiExportingException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 26, 2008
 */
public class RmiExportingException extends RemotingException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Instantiates a new rMI exporting exception.
	 */
	public RmiExportingException() {

		super();
	}

	/**
	 * Instantiates a new RMI exporting exception.
	 * 
	 * @param message
	 *            the message
	 */
	public RmiExportingException(String message) {

		super(message);
	}

	/**
	 * Instantiates a new RMI exporting exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RmiExportingException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new RMI exporting exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RmiExportingException(Throwable cause) {

		super(cause);
	}
}
