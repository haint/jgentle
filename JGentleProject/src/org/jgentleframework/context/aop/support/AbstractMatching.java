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
package org.jgentleframework.context.aop.support;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jgentleframework.core.reflection.metadata.MetadataController;

/**
 * The Class AbstractMatching.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 13, 2008
 */
public abstract class AbstractMatching extends MetadataController implements
		Matching {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8766737035611474750L;

	/**
	 * The Constructor.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public AbstractMatching(Object key, Object value) {

		super(key, value);
	}

	/**
	 * Write object.
	 * 
	 * @param stream
	 *            the stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {

		stream.defaultWriteObject();
	}

	/**
	 * Read object.
	 * 
	 * @param stream
	 *            the stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void readObject(ObjectInputStream stream) throws IOException {

		try {
			stream.defaultReadObject();
			this.key = null;
			this.value = null;
		}
		catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}
}
