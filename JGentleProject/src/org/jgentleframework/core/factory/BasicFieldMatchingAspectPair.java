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
package org.jgentleframework.core.factory;

import java.lang.reflect.Field;

import org.jgentleframework.configure.enums.MetadataKey;
import org.jgentleframework.context.aop.support.FieldMatching;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.core.reflection.metadata.MetadataController;
import org.jgentleframework.core.reflection.metadata.MetadataImpl;

/**
 * The Class BasicFieldMatchingAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 15, 2008
 * @see MetadataController
 * @see FieldMatching
 */
public class BasicFieldMatchingAspectPair extends MetadataController implements
		FieldMatching {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -8966885237861867293L;
	/** The field. */
	final transient Field		field;

	/**
	 * Instantiates a new field matching aspect pair.
	 * 
	 * @param field
	 *            the field
	 */
	public BasicFieldMatchingAspectPair(Field field, Definition definition) {

		super(null, null);
		this.field = field;
		addMetadata(new MetadataImpl(MetadataKey.DEFINITION, definition));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.FieldMatching#getField()
	 */
	@Override
	public Field getField() {

		return this.field;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.Matching#getTargetObject()
	 */
	@Override
	public Object getTargetObject() {

		return getField();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.support.ClassMatching#getTargetClass()
	 */
	@Override
	public Class<?> getTargetClass() {

		return this.field.getDeclaringClass();
	}
}
