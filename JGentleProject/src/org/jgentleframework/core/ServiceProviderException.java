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
 * The Class ServiceProviderException.
 * 
 * @author LE QUOC CHUNG
 * @date Sep 11, 2007
 */
public class ServiceProviderException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Instantiates a new service provider exception.
	 */
	public ServiceProviderException() {

		super();
	}

	/**
	 * Instantiates a new service provider exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public ServiceProviderException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new service provider exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public ServiceProviderException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new service provider exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public ServiceProviderException(Throwable cause) {

		super(cause);
	}
}
