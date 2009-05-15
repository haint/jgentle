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
import java.net.Socket;

import org.jgentleframework.utils.stream.XorInputStream;
import org.jgentleframework.utils.stream.XorOutputStream;

/**
 * The Class XorSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class XorSocket extends Socket {
	/*
	 * The pattern used to "encrypt" and "decrypt" each byte sent or received by
	 * the socket.
	 */
	/** The pattern. */
	private final byte		pattern;

	/* The InputStream used by the socket. */
	/** The in. */
	private InputStream		in	= null;

	/* The OutputStream used by the socket */
	/** The out. */
	private OutputStream	out	= null;

	/**
	 * Constructor for class XorSocket.
	 * 
	 * @param pattern
	 *            the pattern
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public XorSocket(byte pattern) throws IOException {

		super();
		this.pattern = pattern;
	}

	/**
	 * Constructor for class XorSocket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param pattern
	 *            the pattern
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public XorSocket(String host, int port, byte pattern) throws IOException {

		super(host, port);
		this.pattern = pattern;
	}

	/*
	 * Returns a stream of type XorInputStream.(non-Javadoc)
	 * @see java.net.Socket#getInputStream()
	 */
	public synchronized InputStream getInputStream() throws IOException {

		if (in == null) {
			in = new XorInputStream(super.getInputStream(), pattern);
		}
		return in;
	}

	/*
	 * Returns a stream of type XorOutputStream. (non-Javadoc)
	 * @see java.net.Socket#getOutputStream()
	 */
	public synchronized OutputStream getOutputStream() throws IOException {

		if (out == null) {
			out = new XorOutputStream(super.getOutputStream(), pattern);
		}
		return out;
	}
}
