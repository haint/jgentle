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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jgentleframework.reflection.FieldIdentification;
import org.jgentleframework.reflection.Identification;
import org.jgentleframework.reflection.MethodIdentification;
import org.jgentleframework.reflection.SingleClassIdentification;

/**
 * This enum represents the information of all object types. This information
 * will be used to recognize the type of objects or bean instances before they
 * are performed.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 26, 2007
 */
public enum Types {
	CONSTRUCTOR (Constructor.class,null),
	/** The METHOD. */
	METHOD (Method.class,MethodIdentification.class),
	/** The FIELD. */
	FIELD (Field.class,FieldIdentification.class),
	/** The CLASS. */
	CLASS (Class.class,SingleClassIdentification.class),
	/** The ALL. */
	ALL (null,null),
	/** The ANNOTATION. */
	ANNOTATION (Annotation.class,null),
	/** The NO n_ annotation. */
	NON_ANNOTATION (null,null),
	/** The ENUM. */
	ENUM (Enum.class,null),
	/** The DEFAULT. */
	DEFAULT (null,null);
	/** The object class represents class type of enum constant. */
	Class<?>							classType		= null;

	/** The object class represents the identification of enum constant. */
	Class<? extends Identification<?>>	identification	= null;

	/**
	 * The Constructor.
	 * 
	 * @param type
	 *            the type
	 * @param identification
	 *            the identification
	 */
	Types(Class<?> type, Class<? extends Identification<?>> identification) {

		this.classType = type;
		this.identification = identification;
	}

	/**
	 * Gets the class type.
	 * 
	 * @return the class type
	 */
	public Class<?> getClassType() {

		return classType;
	}

	/**
	 * Sets the class type.
	 * 
	 * @param classType
	 *            the class type
	 */
	public void setClassType(Class<?> classType) {

		this.classType = classType;
	}

	/**
	 * Gets the identification.
	 * 
	 * @return the identification
	 */
	public Class<? extends Identification<?>> getIdentification() {

		return identification;
	}

	/**
	 * Sets the identification.
	 * 
	 * @param identification
	 *            the identification to set
	 */
	public void setIdentification(
			Class<? extends Identification<?>> identification) {

		this.identification = identification;
	}
}
