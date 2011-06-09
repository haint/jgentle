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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements Zip Compressing Server Sockets basing on
 * {@link GZipCompressingSocket}. A server socket waits for requests to come in
 * over the network. It performs some operation based on that request, and then
 * possibly returns a result to the requester.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 3, 2008
 */
public class GZipCompressingServerSocket extends ServerSocket {
	/**
	 * Instantiates a new compressing server socket.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public GZipCompressingServerSocket(int port) throws IOException {

		super(port);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#accept()
	 */
	public Socket accept() throws IOException {

		Socket returnValue = (Socket) new GZipCompressingSocket();
		implAccept(returnValue);
		return returnValue;
	}
}
