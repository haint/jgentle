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
 * The Class ZipServerSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class ZipServerSocket extends ServerSocket {
	/**
	 * Instantiates a new compressing server socket.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ZipServerSocket(int port) throws IOException {

		super(port);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#accept()
	 */
	@Override
	public Socket accept() throws IOException {

		Socket returnValue = (Socket) new ZipSocket();
		implAccept(returnValue);
		return returnValue;
	}
}
