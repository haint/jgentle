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
package org.jgentleframework.integration;

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * The Class RunTimeIntegrationException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 24, 2009
 */
public class RunTimeIntegrationException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 4679753723373345428L;

	/**
	 * Instantiates a new run time integration exception.
	 */
	public RunTimeIntegrationException() {

		super();
	}

	/**
	 * Instantiates a new run time integration exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RunTimeIntegrationException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new run time integration exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RunTimeIntegrationException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new run time integration exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RunTimeIntegrationException(Throwable cause) {

		super(cause);
	}
}
