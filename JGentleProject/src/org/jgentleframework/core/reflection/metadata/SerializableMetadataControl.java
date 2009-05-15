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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class SerializableMetadataControl.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 18, 2009
 */
public class SerializableMetadataControl implements SerializableMetadata {
	/** The attributes. */
	Map<String, Serializable>	attributes	= null;

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * addMetadata(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void addMetadata(String key, Serializable metadata)
			throws IllegalStateException {

		if (this.attributes == null) {
			this.attributes = new HashMap<String, Serializable>();
		}
		if (this.attributes.containsKey(key)) {
			throw new IllegalStateException("This metadata key: " + key
					+ " is existed !");
		}
		this.attributes.put(key, metadata);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * getMetadata(java.lang.String)
	 */
	@Override
	public Serializable getMetadata(String key) {

		if (this.attributes == null) {
			return null;
		}
		return (Serializable) this.attributes.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * getMetadatas()
	 */
	@Override
	public Serializable[] getMetadatas() {

		return this.attributes.values().toArray(
				new Serializable[this.attributes.values().size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * getMetadatasMap()
	 */
	@Override
	public Map<String, Serializable> getMetadatasMap() {

		return this.attributes;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * removeMetadata(java.lang.String)
	 */
	@Override
	public void removeMetadata(String key) {

		this.attributes.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SerializableMetadata#
	 * setMetadatasMap(java.util.Map)
	 */
	@Override
	public void setMetadatasMap(Map<String, Serializable> attributes) {

		this.attributes = attributes;
	}
}
