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
package org.jgentleframework.services.eventservices;

import org.jgentleframework.services.ServiceRunningException;

/**
 * The Class EventServicesException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
public class EventServicesException extends ServiceRunningException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -9153303414282403904L;

	/**
	 * Instantiates a new event services exception.
	 */
	public EventServicesException() {

		super();
	}

	/**
	 * Instantiates a new event services exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public EventServicesException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new event services exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public EventServicesException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new event services exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public EventServicesException(Throwable cause) {

		super(cause);
	}
}
