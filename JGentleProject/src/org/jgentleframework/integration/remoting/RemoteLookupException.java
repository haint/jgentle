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

/**
 * The Class RemoteLookupException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 18, 2008
 */
public class RemoteLookupException extends RemoteAccessException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 4195479087721861524L;

	/**
	 * Instantiates a new remote lookup exception.
	 */
	public RemoteLookupException() {

		super();
	}

	/**
	 * Instantiates a new remote lookup exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RemoteLookupException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new remote lookup exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RemoteLookupException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new remote lookup exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RemoteLookupException(Throwable cause) {

		super(cause);
	}
}
