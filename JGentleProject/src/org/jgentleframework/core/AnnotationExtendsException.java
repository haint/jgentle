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
 * The Class AnnotationExtendsException.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 24, 2007
 * @see JGentleRuntimeException
 */
public class AnnotationExtendsException extends JGentleRuntimeException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 8068047221540707967L;

	/**
	 * Instantiates a new annotation extends exception.
	 */
	public AnnotationExtendsException() {

		super();
	}

	/**
	 * Instantiates a new annotation extends exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public AnnotationExtendsException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new annotation extends exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public AnnotationExtendsException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new annotation extends exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public AnnotationExtendsException(Throwable cause) {

		super(cause);
	}
}
