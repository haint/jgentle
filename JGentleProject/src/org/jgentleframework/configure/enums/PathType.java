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
package org.jgentleframework.configure.enums;

/**
 * Chỉ định thông tin các kiểu đường dẫn <code>prefix</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 21, 2007
 */
public enum PathType {
	/**
	 * Tương đương vị trí đường dẫn tại
	 * <code>System.getProperty("user.dir")</code>
	 */
	USERDIR ("user.dir"),
	/**
	 * Tương đương vị trí đường dẫn tại
	 * <code>System.getProperty("user.home")</code>
	 */
	USERHOME ("user.home"),
	/**
	 * Tương đương vị trí đường dẫn tại
	 * <code>System.getProperty("java.io.tmpdir")</code>
	 */
	TEMPDIR ("java.io.tmpdir"),
	/**
	 * Tương đương vị trí đường dẫn tại
	 * <code>System.getProperty("java.home")</code>
	 */
	JAVAHOME ("java.home"),
	/** Chỉ định đường dẫn là đường dẫn tuyệt đối. */
	ABSOLUTEPATH (""),
	/** Chỉ định đường dẫn sẽ được tìm trong classpath. */
	CLASSPATH ("");
	/** The type. */
	String	type;

	/**
	 * Instantiates a new path type.
	 * 
	 * @param type
	 *            the type
	 */
	PathType(String type) {

		this.type = type;
	}

	/**
	 * Trả về tên thuộc tính của kiểu type prefix.
	 * <p>
	 * Ex: với <code><b>USERDIR</b></code> sẽ trả về <code>"user.dir"</code>
	 * <p>
	 * - Để có thể lấy ra đường dẫn chỉ định từ name cần sử dụng static method
	 * System.getProperty
	 * 
	 * @return the type
	 */
	public String getType() {

		return type;
	}
}
