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
package org.jgentleframework.core.reflection;

import org.jgentleframework.core.JGentleException;

/**
 * The Class AnnotationBeanException.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 11, 2008
 */
public class AnnotationBeanException extends JGentleException {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 5907822132286000115L;

	/**
	 * Instantiates a new annotation bean exception.
	 */
	public AnnotationBeanException() {

		super();
	}

	/**
	 * Instantiates a new annotation bean exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public AnnotationBeanException(String strEx) {

		super(strEx);
	}

	/**
	 * Instantiates a new annotation bean exception.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public AnnotationBeanException(String message, Throwable cause) {

		super(message, cause);
	}

	/**
	 * Instantiates a new annotation bean exception.
	 * 
	 * @param cause
	 *            the cause
	 */
	public AnnotationBeanException(Throwable cause) {

		super(cause);
	}
}
