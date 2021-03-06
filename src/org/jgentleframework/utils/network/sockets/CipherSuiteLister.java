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
package org.jgentleframework.utils.network.sockets;

import javax.net.ssl.SSLServerSocketFactory;

public class CipherSuiteLister {
	public static void main(String args[]) {

		try {
			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			String[] suites = socketFactory.getSupportedCipherSuites();
			System.out.println("Supported cipher suites:");
			for (int counter = 0; counter < suites.length; counter++) {
				System.out.println("\t" + suites[counter]);
			}
		}
		catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}
}
