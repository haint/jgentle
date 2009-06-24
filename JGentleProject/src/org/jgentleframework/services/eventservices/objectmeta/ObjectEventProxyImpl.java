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
package org.jgentleframework.services.eventservices.objectmeta;

import org.jgentleframework.utils.Assertor;

/**
 * Chứa đựng thông tin cấu hình một event proxy.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 * @see ObjectEventImpl
 */
public class ObjectEventProxyImpl implements ObjectEventProxy {
	String	name;
	String	host;
	int		port;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            tên định danh của event proxy.
	 */
	public ObjectEventProxyImpl(String name) {

		Assertor.notNull(name);
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#host(java.lang.String,
	 *      java.lang.String)
	 */
	public ObjectEventProxy host(String host, String name) {

		Assertor.notNull(host);
		this.host = host;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#port(int)
	 */
	public ObjectEventProxy port(int port) {

		Assertor.notNull(port);
		this.port = port;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#getName()
	 */
	public String getName() {

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#setName(java.lang.String)
	 */
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#getHost()
	 */
	public String getHost() {

		return host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#setHost(java.lang.String)
	 */
	public void setHost(String host) {

		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#getPort()
	 */
	public int getPort() {

		return port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.services.eventServices.objectmeta.ObjectEventProxy#setPort(int)
	 */
	public void setPort(int port) {

		this.port = port;
	}
}
