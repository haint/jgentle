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
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.network.sockets.MonitoringServerSocket;
import org.jgentleframework.utils.network.sockets.MonitoringSocket;

/**
 * The Class PropertyBasedMonitoringSocket_RMISocketFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 13, 2009
 */
public class PropertyBasedMonitoringSocket_RMISocketFactory extends
		RMISocketFactory {
	/** The Constant USE_MONITORING_SOCKETS_PROPERTY. */
	private static final String	USE_MONITORING_SOCKETS_PROPERTY	= "com.ora.rmibook.useMonitoringSockets";

	/** The Constant TRUE. */
	private static final String	TRUE							= "true";

	/** The _hash code. */
	private int					hashCode						= "PropertyBasedMonitoringSocket_RMISocketFactory"
																		.hashCode();

	/** The _is monitoring on. */
	private boolean				isMonitoringOn;

	/** The log. */
	private final Log			log								= LogFactory
																		.getLog(getClass());

	/**
	 * Instantiates a new property based monitoring socket_ rmi socket factory.
	 */
	public PropertyBasedMonitoringSocket_RMISocketFactory() {

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
	 * @see java.rmi.server.RMISocketFactory#createSocket(java.lang.String, int)
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
			if (log.isWarnEnabled()) {
				log.error("Could not create socket !", e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.rmi.server.RMISocketFactory#createServerSocket(int)
	 */
	public ServerSocket createServerSocket(int port) {

		try {
			if (isMonitoringOn) {
				return new MonitoringServerSocket(port);
			}
			else {
				return new ServerSocket(port);
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
	@Override
	public boolean equals(Object object) {

		Assertor.notNull(object);
		if (object instanceof PropertyBasedMonitoringSocket_RMISocketFactory) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return hashCode;
	}
}
