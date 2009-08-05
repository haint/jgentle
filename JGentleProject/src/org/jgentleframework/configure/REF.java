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
package org.jgentleframework.configure;

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Provides some static method in order to refer specified instances.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 */
public abstract class REF {
	/**
	 * Refers to an instance bound to given ID of {@link Definition}.
	 * 
	 * @param ID
	 *            the ID of specified {@link Definition}
	 */
	public static final String ref(String ID) {

		return REF_ID + ID;
	}

	/**
	 * Refers to an constant instance bound to given name.
	 * 
	 * @param constantName
	 *            name of constant
	 */
	public static final String refConstant(String constantName) {

		return REF_CONSTANT + constantName;
	}

	/**
	 * Refers to an instance bound to mapping type. This method is only used
	 * when binding bean by {@link BindingConfig#bind()} method and its
	 * overloadings. In other cases, using this method is the cause of
	 * unforeseen exceptions.
	 */
	public static final String refMapping() {

		return Configurable.REF_MAPPING;
	}

	/**
	 * Refers to an instance bound to given mapping name.
	 * 
	 * @param mappingName
	 *            name of specified mapping.
	 */
	public static final String refMapping(String mappingName) {

		return REF_MAPPING + mappingName;
	}

	/**
	 * Refers to an instance bound to the given type.
	 * 
	 * @param type
	 *            the given type
	 */
	public static final String refMapping(Class<?> type) {

		return REF_MAPPING + type.toString();
	}

	/** The Constant REF_ID. */
	public static final String	REF_ID			= Configurable.REF_ID + ":";

	/** The Constant REF_CONSTANT. */
	public static final String	REF_CONSTANT	= Configurable.REF_CONSTANT
														+ ":";

	/** The Constant REF_MAPPING. */
	public static final String	REF_MAPPING		= Configurable.REF_MAPPING
														+ ":";
}
