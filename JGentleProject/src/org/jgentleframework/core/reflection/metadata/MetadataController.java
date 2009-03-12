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

import java.io.Serializable;
import java.util.HashMap;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.core.reflection.ReflectException;
import org.jgentleframework.utils.Assertor;

/**
 * The Class MetadataController.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 * @see MetadataControl
 * @see MetadataImpl
 */
public class MetadataController extends MetadataImpl implements
		MetadataControl, Serializable {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 127175785196162625L;

	/**
	 * Instantiates a new metadata controller.
	 */
	public MetadataController() {

	}

	/**
	 * The Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public MetadataController(Object key, Object value) {

		super(key, value);
	}

	/**
	 * Constructor.
	 * 
	 * @param key
	 *            the key
	 */
	public MetadataController(Object key) {

		super(key);
	}

	/** The metadata list. */
	protected HashMap<Object, Metadata>	metadataList	= new HashMap<Object, Metadata>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.reflection.metadata.MetadataControl#addMetadata(org.aopalliance.reflect.Metadata)
	 */
	@Override
	public void addMetadata(Metadata metadata) {

		Assertor.notNull(metadata, "The metadata must not be null!");
		if (metadata.getKey() == null) {
			throw new ReflectException("The key of metadata must not be null !");
		}
		this.metadataList.put(metadata.getKey(), metadata);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.reflection.metadata.MetadataControl#getMetadata(java.lang.Object)
	 */
	@Override
	public Metadata getMetadata(Object key) {

		return this.metadataList.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.reflection.metadata.MetadataControl#getMetadatas()
	 */
	@Override
	public Metadata[] getMetadatas() {

		return this.metadataList.values().toArray(
				new Metadata[this.metadataList.values().size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.reflection.metadata.MetadataControl#removeMetadata(java.lang.Object)
	 */
	@Override
	public void removeMetadata(Object key) {

		this.metadataList.remove(key);
	}
}
