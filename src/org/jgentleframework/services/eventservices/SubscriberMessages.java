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
package org.jgentleframework.services.eventservices;

/**
 * Thực thể lưu trữ thông tin một message được gửi đến cho subscriber.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 28, 2007
 */
public class SubscriberMessages {
	/** The authentication code. */
	String		authenticationCode;

	/** The args. */
	Object[]	args;

	/**
	 * Constructor.
	 * 
	 * @param authenticationCode
	 *            the authentication code
	 * @param args
	 *            the args
	 */
	public SubscriberMessages(String authenticationCode, Object[] args) {

		this.authenticationCode = authenticationCode;
		this.args = args != null ? args.clone() : null;
	}

	/**
	 * Gets the authentication code.
	 * 
	 * @return the authenticationCode
	 */
	public String getAuthenticationCode() {

		return authenticationCode;
	}

	/**
	 * Sets the authentication code.
	 * 
	 * @param authenticationCode
	 *            the authenticationCode to set
	 */
	public void setAuthenticationCode(String authenticationCode) {

		this.authenticationCode = authenticationCode;
	}

	/**
	 * Gets the args.
	 * 
	 * @return the args
	 */
	public Object[] getArgs() {

		return args != null ? args.clone() : null;
	}

	/**
	 * Sets the args.
	 * 
	 * @param args
	 *            the args to set
	 */
	public void setArgs(Object[] args) {

		this.args = args != null ? args.clone() : null;
	}
}
