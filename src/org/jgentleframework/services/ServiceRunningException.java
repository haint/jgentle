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
package org.jgentleframework.services;

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * The Class ServiceRunningException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 24, 2009
 */
public class ServiceRunningException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -2375175311495592636L;

	/**
	 * Instantiates a new service running exception.
	 */
	public ServiceRunningException() {

		super();
	}

	/**
	 * Instantiates a new service running exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public ServiceRunningException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new service running exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ServiceRunningException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new service running exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ServiceRunningException(Throwable cause) {

		super(cause);
	}
}
