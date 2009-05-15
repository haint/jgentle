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

import org.jgentleframework.utils.stream.CompressionInputStream;
import org.jgentleframework.utils.stream.CompressionOutputStream;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class CompressionSocket extends Socket {
	/* InputStream used by socket */
	private InputStream		in;

	/* OutputStream used by socket */
	private OutputStream	out;

	/*
	 * No-arg constructor for class CompressionSocket
	 */
	public CompressionSocket() {

		super();
	}

	/*
	 * Constructor for class CompressionSocket
	 */
	public CompressionSocket(String host, int port) throws IOException {

		super(host, port);
	}

	/*
	 * Returns a stream of type CompressionInputStream
	 */
	public InputStream getInputStream() throws IOException {

		if (in == null) {
			in = new CompressionInputStream(super.getInputStream());
		}
		return in;
	}

	/*
	 * Returns a stream of type CompressionOutputStream
	 */
	public OutputStream getOutputStream() throws IOException {

		if (out == null) {
			out = new CompressionOutputStream(super.getOutputStream());
		}
		return out;
	}

	/*
	 * Flush the CompressionOutputStream before closing the socket.
	 */
	public synchronized void close() throws IOException {

		OutputStream o = getOutputStream();
		o.flush();
		super.close();
	}
}
