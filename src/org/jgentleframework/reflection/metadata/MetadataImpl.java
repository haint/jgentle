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

import org.aopalliance.reflect.Metadata;

/**
 * This class represents a {@link Metadata} information that can be used for
 * basic configuration information designation.
 * <p>
 * <b>Note:</b> As concerns using {@link Metadata} in order to represent
 * attribute information of one annotation, key of {@link Metadata} is name of
 * attribute, and value (data) is returned data of corresponding attribute.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 4, 2007
 */
public class MetadataImpl implements Metadata {
	/** The value. */
	protected Object	value	= null;
	/** The key. */
	protected Object	key		= null;

	/**
	 * Instantiates a new metadata impl.
	 */
	public MetadataImpl() {

	}

	/**
	 * Constructor.
	 * 
	 * @param key
	 *            the key
	 */
	public MetadataImpl(Object key) {

		this.key = key;
	}

	/**
	 * Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public MetadataImpl(Object key, Object value) {

		this.value = value;
		this.key = key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.reflect.Metadata#getKey()
	 */
	@Override
	public Object getKey() {

		return this.key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.reflect.Metadata#getValue()
	 */
	@Override
	public Object getValue() {

		return this.value;
	}
}
