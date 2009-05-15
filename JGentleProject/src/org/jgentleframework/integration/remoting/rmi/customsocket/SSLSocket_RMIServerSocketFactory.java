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
package org.jgentleframework.integration.remoting.rmi.customsocket;

import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.integration.remoting.enums.SSLCipherSuites;
import org.jgentleframework.utils.Assertor;

/**
 * A factory for creating SSLSocket_RMIServerSocket objects.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 13, 2009
 */
public class SSLSocket_RMIServerSocketFactory implements RMIServerSocketFactory {
	/** The CIPHERS. */
	private final String[]	Ciphers;

	/** The _hash code. */
	private int				hashCode	= SSLSocket_RMIServerSocketFactory.class
												.toString().hashCode();

	/** The log. */
	private final Log		log			= LogFactory.getLog(getClass());

	/**
	 * Instantiates a new SSL socket_ rmi server socket factory.
	 * 
	 * @param sslCipherSuites
	 *            the ssl cipher suites
	 */
	public SSLSocket_RMIServerSocketFactory(SSLCipherSuites[] sslCipherSuites) {

		Assertor.notNull(sslCipherSuites,
				"The sslCipherSuites argument must not be null !");
		Assertor.notEmpty(sslCipherSuites,
				"The sslCipherSuites argument must not be empty !");
		Ciphers = new String[sslCipherSuites.length];
		for (int i = 0; i < sslCipherSuites.length; i++) {
			Ciphers[i] = sslCipherSuites[i].name();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.rmi.server.RMIServerSocketFactory#createServerSocket(int)
	 */
	public ServerSocket createServerSocket(int port) {

		try {
			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			SSLServerSocket returnValue = (SSLServerSocket) socketFactory
					.createServerSocket(port);
			returnValue.setEnabledCipherSuites(Ciphers);
			returnValue.setNeedClientAuth(false);
			return returnValue;
		}
		catch (Exception ignored) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create SSL Socket !! ", ignored);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {

		Assertor.notNull(object);
		if (object instanceof SSLSocket_RMIServerSocketFactory) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return hashCode;
	}
}
