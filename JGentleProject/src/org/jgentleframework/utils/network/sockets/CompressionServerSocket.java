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
package org.jgentleframework.utils.network.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Class CompressionServerSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class CompressionServerSocket extends ServerSocket {
	/** The closed. */
	private boolean	closed;

	/**
	 * Instantiates a new compression server socket.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public CompressionServerSocket(int port) throws IOException {

		super(port);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#accept()
	 */
	public Socket accept() throws IOException {

		Socket s = new CompressionSocket();
		implAccept(s);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#getLocalPort()
	 */
	public int getLocalPort() {

		if (closed == true)
			return -1;
		return super.getLocalPort();
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#close()
	 */
	public void close() throws IOException {

		closed = true;
		super.close();
	}
}
