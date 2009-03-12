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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class MonitoringSocket.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class MonitoringSocket extends Socket {
	/** The _number of open sockets. */
	private static int			numberOfOpenSockets	= 0;

	/** The Constant log. */
	private static final Log	log						= LogFactory
																.getLog(MonitoringSocket.class);

	/**
	 * Increment number of open sockets.
	 */
	private synchronized static void incrementNumberOfOpenSockets() {

		numberOfOpenSockets++;
	}

	/**
	 * Instantiates a new monitoring socket.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public MonitoringSocket() throws IOException {

		incrementNumberOfOpenSockets();
		printMonitoringSocketStatus();
	}

	/**
	 * Instantiates a new monitoring socket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public MonitoringSocket(String host, int port) throws IOException {

		super(host, port);
		incrementNumberOfOpenSockets();
		printMonitoringSocketStatus();
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#close()
	 */
	public void close() throws IOException {

		super.close();
		decrementNumberOfOpenSockets();
		printMonitoringSocketStatus();
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.Socket#setSoTimeout(int)
	 */
	public synchronized void setSoTimeout(int timeout) throws SocketException {

		if (log.isInfoEnabled()) {
			log.info("Set timeout called");
		}
		super.setSoTimeout(timeout);
	}

	/**
	 * Prints the monitoring socket status.
	 */
	private static synchronized void printMonitoringSocketStatus() {

		if (log.isInfoEnabled()) {
			log.info("There are currently " + numberOfOpenSockets
					+ " open MonitoringSockets");
		}
	}

	/**
	 * Decrement number of open sockets.
	 */
	private synchronized static void decrementNumberOfOpenSockets() {

		numberOfOpenSockets--;
	}
}
