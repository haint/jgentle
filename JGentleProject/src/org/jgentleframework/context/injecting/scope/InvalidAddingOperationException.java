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
package org.jgentleframework.context.injecting.scope;

import org.jgentleframework.core.JGentleException;

/**
 * The Class InvalidAddingOperationException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 12, 2008
 */
public class InvalidAddingOperationException extends JGentleException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -4444217466990278905L;

	/**
	 * Instantiates a new invalid adding operation exception.
	 */
	public InvalidAddingOperationException() {

		super();
	}

	/**
	 * Instantiates a new invalid adding operation exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public InvalidAddingOperationException(String strEx) {

		super(strEx);
	}
}
