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
package org.jgentleframework.core.reflection.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an implementation of {@link AnnoMeta} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 4, 2007
 * @see AnnoMeta
 * @see Metadata
 * @see MetadataImpl
 */
class AnnoMetaImpl extends MetadataImpl implements Metadata, AnnoMeta {
	/**
	 * The {@link Map map} containing all {@link Metadata metadata} objects of
	 * this {@link AnnoMeta annotation metadata}
	 */
	Map<Object, Metadata>	metaList	= new HashMap<Object, Metadata>();

	/** The current name of this {@link AnnoMeta} */
	String					name		= "";

	/**
	 * The {@link AnnoMeta} parents of this {@link AnnoMeta} if it exists, if
	 * not, this mean current {@link AnnoMeta} is a {@link AnnoMeta} of one
	 * {@link Definition} and its value will be <code>null</code>.
	 */
	AnnoMeta				parents;

	/** The type. */
	Class<?>				type		= null;

	/**
	 * The Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public AnnoMetaImpl(Object key, Object value) {

		super(key, value);
		if (ReflectUtils.isClass(key)) {
			type = (Class<?>) key;
		}
		else if (ReflectUtils.isMethod(key)) {
			type = ((Method) key).getDeclaringClass();
		}
		else if (ReflectUtils.isField(key)) {
			type = ((Field) key).getDeclaringClass();
		}
		this.name = type != null ? type.toString() : "";
	}

	/**
	 * The Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param parents
	 *            the parents
	 */
	public AnnoMetaImpl(Object key, Object value, AnnoMeta parents) {

		super(key, value);
		if (ReflectUtils.isClass(key)) {
			type = (Class<?>) key;
			this.name = type.getName();
		}
		this.parents = parents;
	}

	/**
	 * The Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @param parents
	 *            the parents
	 * @param name
	 *            the name
	 */
	public AnnoMetaImpl(Object key, Object value, AnnoMeta parents, String name) {

		super(key, value);
		if (ReflectUtils.isClass(key))
			type = (Class<?>) key;
		this.parents = parents;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#clear()
	 */
	@Override
	public void clear() {

		metaList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta#contains(java.lang
	 * .Object)
	 */
	@Override
	public boolean contains(Object key) {

		return metaList.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.AnnoMeta#contains(org.
	 * aopalliance.reflect.Metadata)
	 */
	@Override
	public boolean contains(Metadata value) {

		return metaList.containsValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#count()
	 */
	@Override
	public int count() {

		return metaList.size();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta#getMetadata(java
	 * .lang.Object)
	 */
	@Override
	public Metadata getMetadata(Object key) {

		return metaList.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#getName()
	 */
	@Override
	public String getName() {

		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#getType()
	 */
	@Override
	public Class<?> getType() {

		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return metaList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#keySet()
	 */
	@Override
	public Set<Object> keySet() {

		return metaList.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta#putMetaData(org
	 * .aopalliance.reflect.Metadata)
	 */
	@Override
	public Metadata putMetaData(Metadata data) {

		return metaList.put(data.getKey(), data);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta#removeMetadata(
	 * java.lang.Object)
	 */
	@Override
	public Metadata removeMetadata(Object key) {

		return metaList.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String name) {

		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#values()
	 */
	@Override
	public Collection<Metadata> values() {

		return metaList.values();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#getParents()
	 */
	@Override
	public AnnoMeta getParents() {

		return parents;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.MetadataImpl#getValue()
	 */
	@Override
	public Object getValue() {

		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.AnnoMeta#getMetaList()
	 */
	@Override
	public Map<Object, Metadata> getMetaList() {

		return metaList;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuffer string = new StringBuffer();
		string.append("Annotation metadata");
		string.append(" '");
		string.append(this.name != null && !this.name.isEmpty() ? name
				: "Not specified name" + "'");
		string.append(" according to key '");
		string.append(this.getKey().toString());
		string.append("'#");
		string.append(super.toString());
		return string.toString();
	}
}
