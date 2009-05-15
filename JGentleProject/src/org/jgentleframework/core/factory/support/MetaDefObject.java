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
package org.jgentleframework.core.factory.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The Class MetaDefObject.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 27, 2008 3:10:16 PM
 */
public class MetaDefObject {
	/** The injecter. */
	Method[]	setters			= null;
	/** The injected fields. */
	Field[]		injectedFields	= null;
	/** The outjecter. */
	Method[]	getters			= null;
	/** The outjected fields. */
	Field[]		outjectedFields	= null;

	/**
	 * Gets the setters.
	 * 
	 * @return the setters
	 */
	public Method[] getSetters() {

		return setters != null ? setters.clone() : null;
	}

	/**
	 * Sets the setters.
	 * 
	 * @param setters
	 *            the setters to set
	 */
	public void setSetters(Method[] setters) {

		this.setters = setters != null ? setters.clone() : null;
	}

	/**
	 * Gets the injected fields.
	 * 
	 * @return the injectedFields
	 */
	public Field[] getInjectedFields() {

		return injectedFields != null ? injectedFields.clone() : null;
	}

	/**
	 * Sets the injected fields.
	 * 
	 * @param injectedFields
	 *            the injectedFields to set
	 */
	public void setInjectedFields(Field[] injectedFields) {

		this.injectedFields = injectedFields != null ? injectedFields.clone()
				: injectedFields;
	}

	/**
	 * Gets the getters.
	 * 
	 * @return the getters
	 */
	public Method[] getGetters() {

		return getters != null ? getters.clone() : null;
	}

	/**
	 * Sets the getters.
	 * 
	 * @param getters
	 *            the getters to set
	 */
	public void setGetters(Method[] getters) {

		this.getters = getters != null ? getters.clone() : null;
	}

	/**
	 * Gets the outjected fields.
	 * 
	 * @return the outjectedFields
	 */
	public Field[] getOutjectedFields() {

		return outjectedFields != null ? outjectedFields.clone() : null;
	}

	/**
	 * Sets the outjected fields.
	 * 
	 * @param outjectedFields
	 *            the outjectedFields to set
	 */
	public void setOutjectedFields(Field[] outjectedFields) {

		this.outjectedFields = outjectedFields != null ? outjectedFields
				.clone() : null;
	}
}
