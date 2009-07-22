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
package org.jgentleframework.context.injecting;

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Class AppropriateScopeNameClass.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 17, 2009
 */
public class AppropriateScopeNameClass {
	
	/**
	 * Instantiates a new appropriate scope name class.
	 */
	public AppropriateScopeNameClass() {

	
	}
	/**
	 * Instantiates a new appropriate scope name class.
	 * 
	 * @param clazz the clazz
	 * @param targetClass the target class
	 * @param defininition the defininition
	 * @param ref the ref
	 * @param scopeName the scope name
	 * @param mappingName the mapping name
	 */
	public AppropriateScopeNameClass(Class<?> clazz, Class<?> targetClass,
			Definition defininition, String ref, String scopeName,
			String mappingName) {

		this.clazz = clazz;
		this.targetClass = targetClass;
		this.definition = defininition;
		this.ref = ref;
		this.scopeName = scopeName;
		this.mappingName = mappingName;
	}

	/** The clazz. */
	public Class<?>		clazz		= null;

	/** The target class. */
	public Class<?>		targetClass	= null;

	/** The definition. */
	public Definition	definition	= null;

	/** The ref. */
	public String		ref			= null;

	/** The scope name. */
	public String		scopeName	= null;

	/** The mapping name. */
	String				mappingName	= null;
}
