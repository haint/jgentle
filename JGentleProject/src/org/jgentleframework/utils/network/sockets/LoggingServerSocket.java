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

import java.net.*;
import java.io.*;

/**
 * The Class LoggingServerSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class LoggingServerSocket extends ServerSocket {
	/** The _file name prefix. */
	private String	_fileNamePrefix;
	/** The _file index. */
	private int		_fileIndex;

	/**
	 * Instantiates a new logging server socket.
	 * 
	 * @param port
	 *            the port
	 * @param fileNamePrefix
	 *            the file name prefix
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public LoggingServerSocket(int port, String fileNamePrefix)
			throws IOException {

		super(port);
		_fileNamePrefix = fileNamePrefix;
		_fileIndex = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.ServerSocket#accept()
	 */
	public Socket accept() throws IOException {

		Socket returnValue = new LoggingSocket(_fileNamePrefix + "SocketNumber"
				+ _fileIndex);
		_fileIndex++;
		implAccept(returnValue);
		return returnValue;
	}
}
