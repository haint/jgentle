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
package org.jgentleframework.utils.data;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.reflection.metadata.MetadataController;

/**
 * This class represents a pair of values (key,data).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 23, 2008
 * @see Metadata
 */
public class Pair<T, V> extends MetadataController implements Metadata {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -7190466257395210011L;

	/** The key. */
	T							keyPair				= null;

	/** The value. */
	V							valuePair			= null;

	/**
	 * Constructor.
	 */
	public Pair() {

	}

	/**
	 * Constructor.
	 * 
	 * @param keyPair
	 *            the specified key of current pair
	 * @param valuePair
	 *            the specified value of current pair
	 */
	public Pair(T keyPair, V valuePair) {

		this.keyPair = keyPair;
		this.valuePair = valuePair;
	}

	/**
	 * Returns the key of this pair.
	 * 
	 * @return the key
	 */
	public synchronized T getKeyPair() {

		return keyPair;
	}

	/**
	 * Returns the value of this pair.
	 * 
	 * @return the value
	 */
	public synchronized V getValuePair() {

		return this.valuePair;
	}

	/**
	 * Puts a specified key and value to this pair.
	 * 
	 * @param keyPair
	 *            the specified key
	 * @param valuePair
	 *            the specified value
	 */
	public synchronized void put(T keyPair, V valuePair) {

		synchronized (this) {
			this.keyPair = keyPair;
			this.valuePair = valuePair;
		}
	}

	/**
	 * Sets the specified key to this pair.
	 * 
	 * @param keyPair
	 *            the specified key Pair
	 */
	public synchronized void setKey(T keyPair) {

		this.keyPair = keyPair;
	}

	/**
	 * Sets the specified value to this pair.
	 * 
	 * @param valuePair
	 *            the specified value Pair
	 */
	public synchronized void setValue(V valuePair) {

		this.valuePair = valuePair;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.core.reflection.metadata.MetadataImpl#getKey()
	 */
	@Override
	public synchronized Object getKey() {

		return this.getKeyPair();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.MetadataImpl#getValue()
	 */
	@Override
	public synchronized Object getValue() {

		return this.getValuePair();
	}

	/**
	 * Returns an array containing key and value of this pair.
	 * 
	 * @return the object[]
	 */
	public synchronized Object[] toArray() {

		return new Object[] { keyPair, valuePair };
	}
}
