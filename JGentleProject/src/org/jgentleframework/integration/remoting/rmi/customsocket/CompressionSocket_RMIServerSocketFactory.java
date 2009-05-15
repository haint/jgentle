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
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

import org.jgentleframework.utils.network.sockets.CompressionServerSocket;

/**
 * The Class CompressionSocket_RMIServerSocketFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class CompressionSocket_RMIServerSocketFactory implements
		RMIServerSocketFactory, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -4031408120865937040L;

	/**
	 * Create a server socket on the specified port (port 0 indicates an
	 * anonymous port).
	 * 
	 * @param port
	 *            the port number
	 * @return the server socket on the specified port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @exception IOException
	 *                if an I/O error occurs during server socket creation *
	 */
	public ServerSocket createServerSocket(int port) throws IOException {

		ServerSocket activeSocket = new CompressionServerSocket(port);
		return activeSocket;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {

		return obj instanceof CompressionSocket_RMIServerSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return getClass().getName().hashCode();
	}
}
