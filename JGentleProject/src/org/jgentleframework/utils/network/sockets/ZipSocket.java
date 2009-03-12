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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * The Class ZipSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class ZipSocket extends Socket {
	/** The in. */
	private InputStream		in;

	/** The out. */
	private OutputStream	out;

	/**
	 * Instantiates a new zip socket.
	 */
	public ZipSocket() {

		super();
	}

	/**
	 * Instantiates a new zip socket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ZipSocket(String host, int port) throws IOException {

		super(host, port);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {

		if (in == null) {
			in = new ZipInputStream(super.getInputStream());
			((ZipInputStream) in).getNextEntry();
		}
		return in;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#getOutputStream()
	 */
	public OutputStream getOutputStream() throws IOException {

		if (out == null) {
			out = new ZipOutputStream(super.getOutputStream());
			((ZipOutputStream) out).putNextEntry(new ZipEntry("dummy"));
		}
		return out;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#close()
	 */
	public void close() throws IOException {

		OutputStream o = getOutputStream();
		o.flush();
		((ZipOutputStream) o).closeEntry();
		((ZipOutputStream) o).finish();
		super.close();
	}
}
