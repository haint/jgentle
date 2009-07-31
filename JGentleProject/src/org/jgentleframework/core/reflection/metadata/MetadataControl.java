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

import org.aopalliance.reflect.Metadata;

/**
 * The Interface MetadataControl.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 */
public interface MetadataControl {
	/**
	 * Returns the metadata that is associated to this object from its key.
	 * 
	 * @param key
	 *            the key
	 */
	Metadata getMetadata(Object key);

	/**
	 * Returns all the metadatas that are associated to the current object.
	 */
	Metadata[] getMetadatas();

	/**
	 * Associates a metadata to the current object.
	 * <p>
	 * If a metadata already exists with the same key, then its value is
	 * replaced by the newly given one.
	 * 
	 * @param metadata
	 *            the metadata
	 */
	void addMetadata(Metadata metadata);

	/**
	 * Removes a metadata from its key.
	 * <p>
	 * If none metadata having the given key exists, then this method has no
	 * effect.
	 * 
	 * @param key
	 *            the key
	 */
	void removeMetadata(Object key);
}
