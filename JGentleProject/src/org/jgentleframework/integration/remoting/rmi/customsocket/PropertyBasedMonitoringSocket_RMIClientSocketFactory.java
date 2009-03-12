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
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

import org.jgentleframework.utils.network.sockets.MonitoringSocket;

/**
 * A factory for creating PropertyBasedMonitoringSocket_RMIClientSocket objects.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 14, 2009
 */
public class PropertyBasedMonitoringSocket_RMIClientSocketFactory implements
		RMIClientSocketFactory, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID				= 5514008898768078335L;

	/** The Constant USE_MONITORING_SOCKETS_PROPERTY. */
	private static final String	USE_MONITORING_SOCKETS_PROPERTY	= PropertyBasedMonitoringSocket_RMIClientSocketFactory.class
																		.getPackage()
																		.toString();

	/** The Constant TRUE. */
	private static final String	TRUE							= "true";

	/** The _hash code. */
	private int					hashCode						= "PropertyBasedMonitoringSocket_RMIClientSocketFactory"
																		.hashCode();

	/** The _is monitoring on. */
	private boolean				isMonitoringOn;

	/**
	 * Instantiates a new property based monitoring socket_ rmi client socket
	 * factory.
	 */
	public PropertyBasedMonitoringSocket_RMIClientSocketFactory() {

		String monitoringProperty = System
				.getProperty(USE_MONITORING_SOCKETS_PROPERTY);
		if ((null != monitoringProperty)
				&& (monitoringProperty.equalsIgnoreCase(TRUE))) {
			isMonitoringOn = true;
			hashCode++;
		}
		else {
			isMonitoringOn = false;
		}
		return;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.rmi.server.RMIClientSocketFactory#createSocket(java.lang.String,
	 * int)
	 */
	public Socket createSocket(String host, int port) {

		try {
			if (isMonitoringOn) {
				return new MonitoringSocket(host, port);
			}
			else {
				return new Socket(host, port);
			}
		}
		catch (IOException e) {
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object object) {

		if (object instanceof PropertyBasedMonitoringSocket_RMIClientSocketFactory) {
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
