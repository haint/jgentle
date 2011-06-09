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
package org.jgentleframework.reflection.metadata;

import java.lang.annotation.Annotation;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.reflection.IAnnotationVisitor;

/**
 * Provides some static methods are responsible for {@link Metadata metadatas}
 * creation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2007
 */
public abstract class MetaDataFactory {
	/**
	 * Creates a new {@link Definition definition}
	 * 
	 * @param key
	 *            key of {@link Definition definition}
	 * @param visitor
	 *            the {@link IAnnotationVisitor visitor}
	 */
	public static Definition createDefinition(Object key,
			IAnnotationVisitor visitor) {

		return new DefinitionImpl(key, visitor);
	}

	/**
	 * Creates a new {@link Definition definition}
	 * 
	 * @param key
	 *            key of {@link Definition definition}
	 * @param annoList
	 *            an array of original annotations of {@link Definition
	 *            definition}
	 * @param visitor
	 *            the visitor
	 */
	public static Definition createDefinition(Object key,
			Annotation[] annoList, IAnnotationVisitor visitor) {

		return new DefinitionImpl(key, annoList, visitor);
	}

	/**
	 * Creates a new {@link AnnotationMetadata annotation metadata}
	 * 
	 * @param key
	 *            key of {@link AnnotationMetadata annotation metadata}
	 * @param value
	 *            value of {@link AnnotationMetadata annotation metadata}
	 */
	public static AnnotationMetadata createAnnotationMetadata(Object key,
			Object value) {

		return new AnnotationMetadataImpl(key, value);
	}

	/**
	 * Creates a new {@link AnnotationMetadata annotation metadata}
	 * 
	 * @param key
	 *            key of {@link AnnotationMetadata annotation metadata}
	 * @param value
	 *            value of {@link AnnotationMetadata annotation metadata}
	 * @param annoParents
	 *            annotationMetadata parents of {@link AnnotationMetadata
	 *            annotation metadata} need to be created.
	 */
	public static AnnotationMetadata createAnnotationMetadata(Object key,
			Object value, AnnotationMetadata annoParents) {

		return new AnnotationMetadataImpl(key, value, annoParents);
	}

	/**
	 * Creates a new {@link AnnotationMetadata annotation metadata}
	 * 
	 * @param key
	 *            key of {@link AnnotationMetadata annotation metadata}
	 * @param value
	 *            value of {@link AnnotationMetadata annotation metadata}
	 * @param annoParents
	 *            annotationMetadata parents of {@link AnnotationMetadata
	 *            annotation metadata} need to be created.
	 * @param name
	 *            name of desired annotation metadata.
	 */
	public static AnnotationMetadata createAnnotationMetadata(Object key,
			Object value, AnnotationMetadata annoParents, String name) {

		return new AnnotationMetadataImpl(key, value, annoParents, name);
	}

	/**
	 * Creates a new {@link Metadata metadata}
	 * 
	 * @param key
	 *            key of {@link AnnotationMetadata annotation metadata}
	 */
	public static Metadata createMetaData(Object key) {

		return new MetadataImpl(key);
	}

	/**
	 * Creates a new {@link Metadata metadata}
	 * 
	 * @param key
	 *            key of {@link Metadata metadata}
	 * @param value
	 *            value of {@link Metadata metadata}
	 */
	public static Metadata createMetaData(Object key, Object value) {

		return new MetadataImpl(key, value);
	}
}
