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
package org.jgentleframework.core.reflection.metadata;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.aopalliance.reflect.Metadata;

/**
 * Represents {@link AnnoMeta annometa} is created in JGentle system. An
 * {@link AnnoMeta annometa} will have a holding list of {@link Metadata
 * metadatas} which may be another {@link AnnoMeta annometas} or also may be
 * basic datas (values of attributes of an annotation) which is interpreted.
 * <p>
 * If {@link AnnoMeta annometa} is interpreted of annotation, key of
 * {@link AnnoMeta} will be the instance which is annotated with that
 * <code>annotation</code>, and value of {@link AnnoMeta annometa} will be the
 * <code>annotation instance</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 5, 2007
 * @see Metadata
 */
public interface AnnoMeta extends Metadata {
	/**
	 * Removes all the {@link Metadata}s in this {@link AnnoMeta}
	 */
	public void clear();

	/**
	 * Returns {@link AnnoMeta} if this {@link AnnoMeta} contains a specified
	 * {@link Metadata}
	 * 
	 * @param metadata
	 *            the desired {@link Metadata} need to be tested.
	 * @return returns <b>true</b> if this {@link AnnoMeta} contains specified
	 *         {@link Metadata}, <b>false</b> otherwise.
	 */
	public boolean contains(Metadata metadata);

	/**
	 * Returns <b>true</b> if this {@link AnnoMeta} contains a {@link Metadata}
	 * for specified key.
	 * 
	 * @param key
	 *            key of {@link Metadata} need to be tested.
	 * @return returns <b>true</b> if this {@link AnnoMeta} contains a
	 *         {@link Metadata} for specified key, <b>false</b> otherwise.
	 */
	public boolean contains(Object key);

	/**
	 * Returns the number of {@link Metadata} in this {@link AnnoMeta}
	 */
	public int count();

	/**
	 * Returns the {@link Metadata} to which the specified key is mapped, or
	 * null if this {@link AnnoMeta} contains no mapping for the key.
	 * 
	 * @param key
	 *            object key
	 * @return {@link Metadata}
	 */
	public Metadata getMetadata(Object key);

	/**
	 * Returns the list of {@link Metadata}s in this {@link AnnoMeta}
	 */
	public HashMap<Object, Metadata> getMetaList();

	/**
	 * Returns the name of this {@link AnnoMeta}, if it is not specified, an
	 * default name is name of object class of {@link AnnoMeta} will be
	 * specified.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Returns the parents of current {@link AnnoMeta}, if returned value is
	 * <b>null</b>, this {@link AnnoMeta} is key of one {@link Definition}.
	 * 
	 * @see Definition
	 * @see DefinitionCore
	 */
	public AnnoMeta getParents();

	/**
	 * Returns the class type of current {@link AnnoMeta}
	 */
	public Class<?> getType();

	/**
	 * Returns <b>true</b> if this {@link AnnoMeta} is empty, otherwise returns
	 * <b>false</b>.
	 */
	public boolean isEmpty();

	/**
	 * Returns a {@link Set} of keys of {@link Metadata}s in this
	 * {@link AnnoMeta}
	 */
	public Set<Object> keySet();

	/**
	 * Puts a {@link Metadata} into current {@link AnnoMeta}.
	 * 
	 * @param data
	 *            desired metadata.
	 * @return {@link Metadata}
	 */
	public Metadata putMetaData(Metadata data);

	/**
	 * Removes a specified {@link Metadata}
	 * 
	 * @param key
	 *            key of {@link Metadata} need to be remove.
	 * @return {@link Metadata}
	 */
	public Metadata removeMetadata(Object key);

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(String name);

	/**
	 * Returns a {@link Collection} view of the {@link Metadata}s contained in
	 * this {@link AnnoMeta}.
	 * 
	 * @return {@link Collection}
	 */
	public Collection<Metadata> values();
}