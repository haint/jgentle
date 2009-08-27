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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.aopalliance.reflect.Metadata;

/**
 * Represents the {@link AnnotationMetadata annotation metadata} will be created in
 * JGentle system. An {@link AnnotationMetadata annotation metadata} will hold a list of
 * {@link Metadata metadatas} which may be another {@link AnnotationMetadata annotation
 * metadatas} or basic datas (values of attributes of an annotation) are
 * interpreted.
 * <p>
 * If {@link AnnotationMetadata annotation metadata} is interpreted of annotation, key of
 * it will be the instance which is annotated with that <code>annotation</code>,
 * and value will be the <code>annotation instance</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 5, 2007
 * @see Metadata
 */
public interface AnnotationMetadata extends Metadata {
	/**
	 * Removes all the {@link Metadata metadatas} in current {@link AnnotationMetadata
	 * annotation metadata}
	 */
	public void clear();

	/**
	 * Returns <code>true</code> if current {@link AnnotationMetadata annotation metadata}
	 * contains the given {@link Metadata metadata}
	 * 
	 * @param metadata
	 *            the desired {@link Metadata} need to be tested.
	 * @return returns <b>true</b> if this {@link AnnotationMetadata annotation metadata}
	 *         contains the given {@link Metadata}, <b>false</b> otherwise.
	 */
	public boolean contains(Metadata metadata);

	/**
	 * Returns <b>true</b> if this {@link AnnotationMetadata annotation metadata} contains
	 * a {@link Metadata metadata} corresponds to the given object key.
	 * 
	 * @param key
	 *            key of {@link Metadata} need to be tested.
	 * @return returns <b>true</b> if this {@link AnnotationMetadata annotation metadata}
	 *         contains a {@link Metadata metadata} corresponds to the given
	 *         key, <b>false</b> otherwise.
	 */
	public boolean contains(Object key);

	/**
	 * Returns the number of all {@link Metadata metadatas} in current
	 * {@link AnnotationMetadata annotation metadata}
	 */
	public int count();

	/**
	 * Returns the {@link Metadata metadata} corresponds to the given object
	 * key, or <code>null</code> if current {@link AnnotationMetadata annotation metadata}
	 * doesn't contain any mapping corresponding to the given key.
	 * 
	 * @param key
	 *            the given object key
	 */
	public Metadata getMetadata(Object key);

	/**
	 * Returns the {@link Map map} containing all {@link Metadata metadatas} in
	 * current {@link AnnotationMetadata annotation metadata}
	 */
	public Map<Object, Metadata> getMetaList();

	/**
	 * Returns the name of this {@link AnnotationMetadata annotation metadata}, if it's
	 * not specified, a default name is name of object class of {@link AnnotationMetadata
	 * annotation metadata} will be returned.
	 */
	public String getName();

	/**
	 * Returns the parents of current {@link AnnotationMetadata annotation metadata}, if
	 * returning value is <b>null</b>, it will be key of one {@link Definition
	 * definition}.
	 * 
	 * @see Definition
	 * @see DefinitionCore
	 */
	public AnnotationMetadata getParents();

	/**
	 * Returns the object class type of current {@link AnnotationMetadata annotation
	 * metadata}
	 */
	public Class<?> getType();

	/**
	 * Returns <b>true</b> if current {@link AnnotationMetadata annotation metadata} is
	 * empty, otherwise returns <b>false</b>.
	 */
	public boolean isEmpty();

	/**
	 * Returns a {@link Set set} of keys corresponds to all {@link Metadata
	 * metadatas} existing in current {@link AnnotationMetadata annotation metadata}
	 */
	public Set<Object> keySet();

	/**
	 * Puts a {@link Metadata metadata} into current {@link AnnotationMetadata annotation
	 * metadata}.
	 * 
	 * @param data
	 *            the given metadata.
	 */
	public Metadata putMetaData(Metadata data);

	/**
	 * Removes a specified {@link Metadata metadata}
	 * 
	 * @param key
	 *            key of {@link Metadata} need to be removed.
	 */
	public Metadata removeMetadata(Object key);

	/**
	 * Sets name to current {@link AnnotationMetadata annotation metadata}.
	 * 
	 * @param name
	 *            the given name
	 */
	public void setName(String name);

	/**
	 * Returns a {@link Collection collection} view of {@link Metadata
	 * metadatas} existing in current {@link AnnotationMetadata annotation metadata}.
	 * 
	 * @return {@link Collection}
	 */
	public Collection<Metadata> values();
}