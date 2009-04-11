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
package org.jgentleframework.utils;

import java.security.SecureRandom;

/**
 * The Class UniqueNumberGenerator.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 24, 2007
 */
public final class UniqueNumberGenerator {
	/** The Constant random. */
	static final SecureRandom	random	= new SecureRandom();

	/** The current. */
	static long					current	= System.currentTimeMillis();
	static {
		random.nextInt();
	}

	/**
	 * Creates an unique ID.
	 * 
	 * @return the next uid
	 */
	public static synchronized String getNextUID() {

		return getNextStringUID() + String.valueOf(getNextLongUID());
	}

	/**
	 * Creates an unique ID.
	 * 
	 * @return the next string uid
	 */
	public static synchronized String getNextStringUID() {

		StringBuffer buffer = new StringBuffer(30);
		buffer.setLength(0);
		Object obj = new Object();
		int hash = obj.hashCode();
		int rand = random.nextInt();
		long time = System.currentTimeMillis() & 0xFFFFFFFF;
		buffer.append(Integer.toHexString(rand)).append(Long.toHexString(time))
				.append(Integer.toHexString(hash));
		obj = null;
		return buffer.toString();
	}

	/**
	 * Creates an unique ID.
	 * 
	 * @return the next long uid
	 */
	public static synchronized long getNextLongUID() {

		return current++;
	}
}
