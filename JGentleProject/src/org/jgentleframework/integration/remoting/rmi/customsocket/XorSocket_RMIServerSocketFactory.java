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
package org.jgentleframework.integration.remoting.rmi.customsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

import org.jgentleframework.utils.network.sockets.XorServerSocket;

/**
 * The Class XorSocket_RMIServerSocketFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class XorSocket_RMIServerSocketFactory implements RMIServerSocketFactory {
	/** The pattern. */
	private byte	pattern;

	/**
	 * Instantiates a new xor socket_ rmi server socket factory.
	 * 
	 * @param pattern
	 *            the pattern
	 */
	public XorSocket_RMIServerSocketFactory(byte pattern) {

		this.pattern = pattern;
	}

	/*
	 * (non-Javadoc)
	 * @see java.rmi.server.RMIServerSocketFactory#createServerSocket(int)
	 */
	public ServerSocket createServerSocket(int port) throws IOException {

		return new XorServerSocket(port, pattern);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return (int) pattern;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {

		if (obj != null)
			return (getClass() == obj.getClass() && pattern == ((XorSocket_RMIServerSocketFactory) obj).pattern);
		else
			return false;
	}
}
