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

/**
 * The Class AbstractConfig.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 17, 2007
 * @see SystemConfigImpl
 * @see Configurable
 */
public abstract class AbstractConfig extends SystemConfigImpl implements
		Configurable {
	/**
	 * Configuration method.
	 */
	public abstract void configure();

	/** The Constant ANNOTATION_CONFIG_LIST. */
	public static final String	ANNOTATION_CONFIG_LIST			= "ANNOTATION_CONFIG_LIST";
	/** The Constant BEAN_CLASS_LIST. */
	public static final String	BEAN_CLASS_LIST					= "BEAN_CLASS_LIST";
	/** The Constant CSC_CLASS_LIST. */
	public static final String	CSC_CLASS_LIST					= "CSC_CLASS_LIST";
	/** The Constant DEFINITION_POST_PROCESSOR. */
	public static final String	DEFINITION_POST_PROCESSOR		= "DEFINITION_POST_PROCESSOR";
	/** The Constant OBJECT_ATTACH_LIST. */
	public static final String	OBJECT_ATTACH_LIST				= "OBJECT_ATTACH_LIST";
	/** The Constant OBJECT_BINDING_CONSTANT_LIST. */
	public static final String	OBJECT_BINDING_CONSTANT_LIST	= "OBJECT_BINDING_CONSTANT_LIST";
	/** The Constant OBJECT_BINDING_INTERCEPTOR_LIST. */
	public static final String	OBJECT_BINDING_INTERCEPTOR_LIST	= "OBJECT_BINDING_INTERCEPTOR_LIST";
	/** The Constant OBJECT_CONSTANT_LIST. */
	public static final String	OBJECT_CONSTANT_LIST			= "OBJECT_CONSTANT_LIST";
	/** The Constant ANNOTATION_BEAN_PROCESSOR_LIST. */
	public static final String	ANNOTATION_BEAN_PROCESSOR_LIST	= "ANNOTATION_BEAN_PROCESSOR_LIST";
}
