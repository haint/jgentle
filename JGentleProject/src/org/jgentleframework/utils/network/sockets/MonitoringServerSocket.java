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

import java.net.*;
import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class implements Monitoring Server Sockets basing on
 * {@link MonitoringSocket}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see MonitoringSocket
 */
public class MonitoringServerSocket extends ServerSocket {
	/** The _number of open sockets. */
	private static int	numberOfOpenSockets	= 0;

	/** The log. */
	private final Log	log					= LogFactory.getLog(getClass());

	/**
	 * _increment number of open sockets.
	 */
	private static void incrementNumberOfOpenSockets() {

		numberOfOpenSockets++;
	}

	/**
	 * Instantiates a new monitoring server socket.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public MonitoringServerSocket(int port) throws IOException {

		super(port);
		incrementNumberOfOpenSockets();
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#accept()
	 */
	public Socket accept() throws IOException {

		Socket returnValue = new MonitoringSocket();
		implAccept(returnValue);
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.ServerSocket#setSoTimeout(int)
	 */
	public void setSoTimeout(int timeout) throws SocketException {

		super.setSoTimeout(timeout);
		printMonitoringServerSocketStatus();
	}

	/**
	 * Prints the monitoring server socket status.
	 */
	private synchronized void printMonitoringServerSocketStatus() {

		if (log.isInfoEnabled()) {
			log.info("There are currently " + numberOfOpenSockets
					+ " open MonitoringServerSockets");
			try {
				log.info("\t getSoTimeout() returns " + getSoTimeout());
			}
			catch (IOException e) {
				log
						.info("\t getSoTimeout() is currently throwing an exception !");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {

		decrementNumberOfOpenSockets();
	}

	/**
	 * Decrement number of open sockets.
	 */
	private static void decrementNumberOfOpenSockets() {

		numberOfOpenSockets--;
	}
}
