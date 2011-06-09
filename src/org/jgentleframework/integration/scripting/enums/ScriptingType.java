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
package org.jgentleframework.integration.scripting.enums;

/**
 * Represents all scripting languages are supported by JGentle Scripting
 * Service.
 * 
 * @author gnut
 */
public enum ScriptingType {
	/** The JAVASCRIPT. */
	JAVASCRIPT ("javascript"),
	/** The GROOVY. */
	GROOVY ("groovy"),
	/** The RUBY. */
	RUBY ("ruby"),
	/** The BEANSHELL. */
	BEANSHELL ("beanshell"),
	/** The AWK. */
	AWK ("awk"),
	/** The FREEMARKER. */
	FREEMARKER ("freemarker"),
	/** The JACL. */
	JACL ("jacl"),
	/** The JASKELL. */
	JASKELL ("jaskell"),
	/** The JELLY. */
	JELLY ("jelly"),
	/** The JEP. */
	JEP ("jep"),
	/** The JEXL. */
	JEXL ("jexl"),
	/** The JUDOSCRIPT. */
	JUDOSCRIPT ("judoscript"),
	/** The JUEL. */
	JUEL ("juel"),
	/** The OGNL. */
	OGNL ("ognl"),
	/** The PNUT. */
	PNUT ("pnut"),
	/** The PYTHON. */
	PYTHON ("python"),
	/** The SCHEME. */
	SCHEME ("scheme"),
	/** The SLEEP. */
	SLEEP ("sleep"),
	/** The TCL. */
	TCL ("tcl"),
	/** The VELOCITY. */
	VELOCITY ("velocity"),
	/** The XPATH. */
	XPATH ("xpath"),
	/** The XSLT. */
	XSLT ("xslt");
	/** The type. */
	private String	type;

	/**
	 * Instantiates a new scripting type.
	 * 
	 * @param type
	 *            the type
	 */
	private ScriptingType(String type) {

		this.type = type;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {

		return type;
	}
}
