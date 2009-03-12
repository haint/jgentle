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
package org.jgentleframework.core.factory;

/**
 * The Class RequiredException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 4, 2008
 */
public class RequiredException extends InOutDependencyException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 212409236975605358L;

	/**
	 * Instantiates a new required exception.
	 */
	public RequiredException() {

		super();
	}

	/**
	 * Instantiates a new required exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public RequiredException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new required exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public RequiredException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new required exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public RequiredException(Throwable cause) {

		super(cause);
	}
}
