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
package org.jgentleframework.core.factory;

import org.jgentleframework.core.JGentleRuntimeException;

/**
 * @author <a href="mailto:skydunkpro@yahoo.com">LE QUOC CHUNG</a>
 * @date Oct 3, 2007
 */
public class InOutDependencyException extends JGentleRuntimeException {
	

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6021011935463960165L;

	public InOutDependencyException() {

		super();
	}

	public InOutDependencyException(String strEx) {

		super(strEx);
	}

	public InOutDependencyException(String message, Throwable cause) {

		super(message, cause);
	}

	public InOutDependencyException(Throwable cause) {

		super(cause);
	}
}
