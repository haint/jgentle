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

import java.rmi.server.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.integration.remoting.enums.SSLCipherSuites;
import org.jgentleframework.utils.Assertor;

/**
 * A factory for creating SSLSocket_RMIClientSocket objects. Redirects to the
 * standard SSL Socket factory found in JSSE.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 13, 2009
 * @see RMIClientSocketFactory
 */
public class SSLSocket_RMIClientSocketFactory implements
		RMIClientSocketFactory, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1654586784453189946L;

	/** The CIPHERS. */
	private final String[]		Ciphers;											;

	/** The _hash code. */
	private int					hashCode			= SSLSocket_RMIClientSocketFactory.class
															.toString()
															.hashCode();

	/** The log. */
	private transient Log		log					= LogFactory
															.getLog(getClass());

	/**
	 * Instantiates a new sSL socket_ rmi client socket factory.
	 * 
	 * @param sslCipherSuites
	 *            the ssl cipher suites
	 */
	public SSLSocket_RMIClientSocketFactory(SSLCipherSuites[] sslCipherSuites) {

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
	 * @see
	 * java.rmi.server.RMIClientSocketFactory#createSocket(java.lang.String,
	 * int)
	 */
	public Socket createSocket(String host, int port) {

		try {
			java.security.Security
					.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			SSLSocket returnValue = (SSLSocket) socketFactory.createSocket(
					host, port);
			returnValue.setEnabledCipherSuites(Ciphers);
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

		if (object instanceof SSLSocket_RMIClientSocketFactory) {
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

	/**
	 * Overrides default readObject method.
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {

		stream.defaultReadObject();
		log = LogFactory.getLog(getClass());
	}
}
