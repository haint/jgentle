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
package org.jgentleframework.integration.remoting.rmi.customsocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.utils.network.sockets.MonitoringSocket;

/**
 * A factory for creating MonitoringSocket_RMIClientSocket objects.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 13, 2008
 */
public class MonitoringSocket_RMIClientSocketFactory implements
		RMIClientSocketFactory, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 5083816997131569046L;

	/** The _hash code. */
	private int					hashCode			= "MonitoringSocket_RMIClientSocketFactory"
															.hashCode();

	/** The log. */
	private transient Log		log					= LogFactory
															.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * @see
	 * java.rmi.server.RMIClientSocketFactory#createSocket(java.lang.String,
	 * int)
	 */
	public Socket createSocket(String host, int port) {

		try {
			return new MonitoringSocket(host, port);
		}
		catch (IOException e) {
			if (log.isWarnEnabled()) {
				log.error("Could not create Monitoring Socket !", e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {

		if (object instanceof MonitoringSocket_RMIClientSocketFactory) {
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
