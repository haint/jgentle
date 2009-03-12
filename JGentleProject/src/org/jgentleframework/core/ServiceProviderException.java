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
package org.jgentleframework.core;


/**
 * Bắt các ngoại lệ xảy ra khi làm việc với Service Provider.
 * 
 * @author LE QUOC CHUNG
 * @date Sep 11, 2007
 */
public class ServiceProviderException extends JGentelIllegalArgumentException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public ServiceProviderException() {

		super();
	}

	public ServiceProviderException(String strEx) {

		super(strEx);
	}

	public ServiceProviderException(String message, Throwable cause) {

		super(message, cause);
	}

	public ServiceProviderException(Throwable cause) {

		super(cause);
	}
}
