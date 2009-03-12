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
package org.jgentleframework.configure;

import org.jgentleframework.core.JGentelIllegalArgumentException;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 1, 2008
 */
public class AnnotatingRuntimeException extends JGentelIllegalArgumentException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9095569791540907582L;

	public AnnotatingRuntimeException() {

		super();
	}

	public AnnotatingRuntimeException(String strEx) {

		super(strEx);
	}

	public AnnotatingRuntimeException(String message, Throwable cause) {

		super(message, cause);
	}

	public AnnotatingRuntimeException(Throwable cause) {

		super(cause);
	}
}
