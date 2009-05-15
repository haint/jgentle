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
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.integration.remoting.enums.SSLCipherSuites;

/**
 * The Class SSLSocketTools.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 4, 2008
 */
public class SSLSocketTools {
	/** The log. */
	private final Log	log	= LogFactory.getLog(getClass());

	/**
	 * Creates the server socket.
	 * 
	 * @param port
	 *            the port
	 * @param cipherSuites
	 *            the cipher suites
	 * @return the sSL server socket
	 */
	public SSLServerSocket createServerSocket(int port,
			SSLCipherSuites[] cipherSuites) {

		SSLServerSocket returnValue = null;
		try {
			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			returnValue = (SSLServerSocket) socketFactory
					.createServerSocket(port);
			String[] CIPHERS = new String[cipherSuites.length];
			for (int i = 0; i < cipherSuites.length; i++) {
				CIPHERS[i] = cipherSuites[i].name();
			}
			returnValue.setEnabledCipherSuites(CIPHERS);
			returnValue.setEnableSessionCreation(true);
			return returnValue;
		}
		catch (IOException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create SSL server socket !!", e);
			}
		}
		return returnValue;
	}

	/**
	 * Creates the socket.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param cipherSuites
	 *            the cipher suites
	 * @return the socket
	 */
	public Socket createSocket(String host, int port,
			SSLCipherSuites[] cipherSuites) {

		SSLSocket returnValue = null;
		try {
			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			returnValue = (SSLSocket) socketFactory.createSocket(host, port);
			String[] CIPHERS = new String[cipherSuites.length];
			for (int i = 0; i < cipherSuites.length; i++) {
				CIPHERS[i] = cipherSuites[i].name();
			}
			returnValue.setEnabledCipherSuites(CIPHERS);
			return returnValue;
		}
		catch (IOException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create SSL socket !!", e);
			}
		}
		return returnValue;
	}
}
