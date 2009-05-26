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
package org.jgentleframework.configure.enums;

/**
 * Represents the <code>prefix</code> type of path.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 21, 2007
 */
public enum PathType {
	/**
	 * equals <code>System.getProperty("user.dir")</code>
	 */
	USERDIR ("user.dir"),
	/**
	 * equals <code>System.getProperty("user.home")</code>
	 */
	USERHOME ("user.home"),
	/**
	 * equals <code>System.getProperty("java.io.tmpdir")</code>
	 */
	TEMPDIR ("java.io.tmpdir"),
	/**
	 * equals <code>System.getProperty("java.home")</code>
	 */
	JAVAHOME ("java.home"),
	/** The absolute path. */
	ABSOLUTEPATH (""),
	/** The class path. */
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
	 * Returns the "prefix" of path.
	 */
	public String getType() {

		if (type != null && !type.isEmpty())
			return System.getProperty(type);
		else
			return type;
	}
}
