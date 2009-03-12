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
package org.jgentleframework.integration.remoting.rmi;

import org.jgentleframework.integration.remoting.RemoteAccessException;

/**
 * The Class RmiBindingException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 3, 2008
 */
public class RmiBindingException extends RemoteAccessException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -1560888612654183605L;

	/**
	 * Instantiates a new RrMI binding exception.
	 */
	public RmiBindingException() {

		super();
	}

	/**
	 * Instantiates a new RMI binding exception.
	 * 
	 * @param message
	 *            the message
	 */
	public RmiBindingException(String message) {

		super(message);
	}

	/**
	 * Instantiates a new RMI binding exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RmiBindingException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new RMI binding exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RmiBindingException(Throwable cause) {

		super(cause);
	}
}
