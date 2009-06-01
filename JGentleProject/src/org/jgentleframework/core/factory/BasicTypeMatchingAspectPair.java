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
package org.jgentleframework.core.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.MetadataKey;
import org.jgentleframework.context.aop.support.ClassMatching;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.core.reflection.metadata.MetadataController;
import org.jgentleframework.core.reflection.metadata.MetadataImpl;

/**
 * The Class BasicTypeMatchingAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 28, 2009
 */
public class BasicTypeMatchingAspectPair extends MetadataController implements
		ClassMatching {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -4341067904383965149L;

	/** The target class. */
	final transient Class<?>	targetClass;

	/**
	 * Instantiates a new basic type matching aspect pair.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param definition
	 *            the definition
	 */
	public BasicTypeMatchingAspectPair(Class<?> targetClass,
			Definition definition) {

		super(null, null);
		this.targetClass = targetClass;
		addMetadata(new MetadataImpl(MetadataKey.DEFINITION, definition));
	}

	/**
	 * Instantiates a new basic type matching aspect pair.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param method
	 *            the method
	 * @param definition
	 *            the definition
	 */
	public BasicTypeMatchingAspectPair(Class<?> targetClass, Method method,
			Definition definition) {

		super(null, null);
		this.targetClass = targetClass;
		addMetadata(new MetadataImpl(MetadataKey.DEFINITION, definition));
		addMetadata(new MetadataImpl(MetadataKey.METHOD, method));
	}

	/**
	 * Instantiates a new basic type matching aspect pair.
	 * 
	 * @param targetClass
	 *            the target class
	 * @param field
	 *            the field
	 * @param definition
	 *            the definition
	 */
	public BasicTypeMatchingAspectPair(Class<?> targetClass, Field field,
			Definition definition) {

		super(null, null);
		this.targetClass = targetClass;
		addMetadata(new MetadataImpl(MetadataKey.DEFINITION, definition));
		addMetadata(new MetadataImpl(MetadataKey.FIELD, field));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.aop.support.ClassMatching#getTargetClass()
	 */
	@Override
	public Class<?> getTargetClass() {

		return this.targetClass;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.aop.support.Matching#getTargetObject()
	 */
	@Override
	public Object getTargetObject() {

		return this.targetClass;
	}
}
