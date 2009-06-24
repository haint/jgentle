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

public interface ObjectEventProxy {
	/**
	 * Thiết lập host sẽ được proxy ánh xạ đến
	 * 
	 * @param host
	 *            host chỉ định proxy sẽ ánh xạ đến (domain name hoặc ip
	 *            address).
	 * @param name
	 *            tên định danh của event sẽ được ánh xạ.
	 */
	public ObjectEventProxy host(String host, String name);

	/**
	 * Thiết lập port chỉ định của host (event Server).
	 * 
	 * @param port
	 *            port number
	 */
	public ObjectEventProxy port(int port);

	/**
	 * Trả về tên định danh của proxy
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Thiết lập tên định danh cho proxy
	 * 
	 * @param name
	 *            tên định danh chỉ định.
	 */
	public void setName(String name);

	/**
	 * Trả về địa chỉ host của event server mà proxy hiện hành sẽ ánh xạ đến.
	 */
	public String getHost();

	/**
	 * Thiết lập địa chỉ host của event server mà proxy hiện hành sẽ ánh xạ đến.
	 * 
	 * @param host
	 *            host chỉ định proxy sẽ ánh xạ đến (domain name hoặc ip
	 *            address).
	 */
	public void setHost(String host);

	/**
	 * Trả về port chỉ định của host mà proxy hiện hành sẽ ánh xạ đến.
	 */
	public int getPort();

	/**
	 * Thiết lập port chỉ định của host mà proxy hiện hành sẽ ánh xạ đến.
	 * 
	 * @param port
	 *            port number
	 */
	public void setPort(int port);
}