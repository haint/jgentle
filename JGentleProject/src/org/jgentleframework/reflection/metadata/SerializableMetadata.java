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

import java.io.Serializable;
import java.util.Map;

/**
 * The Interface SerializableMetadata.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 18, 2009
 */
public interface SerializableMetadata {
	/**
	 * Returns the metadata that is associated to this object from its key.
	 * 
	 * @param key
	 *            the key
	 * @return the metadata value, or <code>null</code> if not defined
	 */
	Serializable getMetadata(String key);

	/**
	 * Returns all the metadatas that are associated to the current object.
	 * 
	 * @return the metadatas
	 */
	Serializable[] getMetadatas();

	/**
	 * Associates a serializable metadata to the current object.
	 * <p>
	 * If a metadata already exists with the same key, then its value is
	 * replaced by the newly given one.
	 * <p>
	 * Attribute keys have to be unique, and no overriding of existing
	 * attributes is allowed.
	 * 
	 * @param metadata
	 *            the metadata
	 * @param key
	 *            the key
	 */
	void addMetadata(String key, Serializable metadata);

	/**
	 * Removes a metadata from its key.
	 * <p>
	 * If none metadata having the given key exists, then this method has no
	 * effect.
	 * 
	 * @param key
	 *            the key
	 */
	void removeMetadata(String key);

	/**
	 * Sets the Metadatas Map. Only here for special purposes: Preferably, use
	 * {@link #addMetadata(String, Serializable)} and
	 * {@link #getMetadata(String)}.
	 * 
	 * @param attributes
	 *            the attributes Map
	 * @see #addMetadata(String, Serializable)
	 * @see #getMetadata(String)
	 */
	public void setMetadatasMap(Map<String, Serializable> attributes);

	/**
	 * Returns the Metadatas Map.
	 * 
	 * @return the Metadatas Map, or <code>null</code> if none created
	 * @see #addMetadata(String, Serializable)
	 * @see #getMetadata(String)
	 */
	public Map<String, Serializable> getMetadatasMap();
}
