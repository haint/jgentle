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
 * The Class BeanNotFoundException.
 * 
 * @author LE QUOC CHUNG
 */
public class BeanNotFoundException extends Exception {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 2321961732932739912L;

	/**
	 * Instantiates a new bean not found exception.
	 */
	public BeanNotFoundException() {

		super();
	}

	/**
	 * Instantiates a new bean not found exception.
	 * 
	 * @param strEx
	 *            the str ex
	 */
	public BeanNotFoundException(String strEx) {

		super(strEx);
	}
}
