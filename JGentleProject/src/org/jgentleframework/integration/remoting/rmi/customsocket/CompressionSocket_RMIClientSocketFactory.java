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
package org.jgentleframework.integration.remoting.rmi.customsocket;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.jgentleframework.utils.network.sockets.CompressionSocket;

/**
 * The Class CompressionSocket_RMIClientSocketFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class CompressionSocket_RMIClientSocketFactory implements
		RMIClientSocketFactory, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 7842121942488110368L;

	/**
	 * Create a client socket connected to the specified host and port.
	 * 
	 * @param host
	 *            - the host name
	 * @param port
	 *            - the port number
	 * @return a socket connected to the specified host and port.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @exception IOException
	 *                if an I/O error occurs during socket creation.
	 */
	public Socket createSocket(String host, int port) throws IOException {

		Socket s = new CompressionSocket(host, port);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {

		return obj instanceof CompressionSocket_RMIClientSocketFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return getClass().getName().hashCode();
	}
}
