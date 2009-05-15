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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipCompressingSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 13, 2009
 * @see AbstractCompressingSocket
 */
public class ZipCompressingSocket extends AbstractCompressingSocket {
	/**
	 * Instantiates a new compressing socket.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ZipCompressingSocket() throws IOException {

	}

	/**
	 * Instantiates a new compressing socket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ZipCompressingSocket(String host, int port) throws IOException {

		super(host, port);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {

		if (null == compressingInputStream) {
			InputStream originalInputStream = super.getInputStream();
			compressingInputStream = new ZipInputStream(
					originalInputStream);
			((ZipInputStream) compressingInputStream).getNextEntry();
		}
		return compressingInputStream;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {

		if (null == compressingOutputStream) {
			OutputStream originalOutputStream = super.getOutputStream();
			compressingOutputStream = new ZipOutputStream(
					originalOutputStream);
			((ZipOutputStream) compressingOutputStream)
					.putNextEntry(new ZipEntry("dummy"));
		}
		return compressingOutputStream;
	}
}
