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

import java.io.*;
import java.net.*;

import org.jgentleframework.utils.stream.LoggingInputStream;
import org.jgentleframework.utils.stream.LoggingOutputStream;

/**
 * The Class LoggingSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class LoggingSocket extends Socket {
	/** The _logging input stream. */
	private LoggingInputStream	_loggingInputStream;
	/** The _logging output stream. */
	private LoggingOutputStream	_loggingOutputStream;
	/** The _file name. */
	private String				_fileName;

	/**
	 * Instantiates a new logging socket.
	 * 
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public LoggingSocket(String fileName) throws IOException {

		_fileName = fileName;
	}

	/**
	 * Instantiates a new logging socket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public LoggingSocket(String host, int port, String fileName)
			throws IOException {

		super(host, port);
		_fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.Socket#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {

		if (null == _loggingInputStream) {
			_loggingInputStream = new LoggingInputStream(
					super.getInputStream(), _fileName);
		}
		return _loggingInputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.Socket#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {

		if (null == _loggingOutputStream) {
			_loggingOutputStream = new LoggingOutputStream(super
					.getOutputStream(), _fileName);
		}
		return _loggingOutputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.Socket#close()
	 */
	public synchronized void close() throws IOException {

		if (null != _loggingOutputStream) {
			_loggingOutputStream.flush();
			_loggingOutputStream.close();
		}
		if (null != _loggingInputStream) {
			_loggingInputStream.close();
		}
	}
}
