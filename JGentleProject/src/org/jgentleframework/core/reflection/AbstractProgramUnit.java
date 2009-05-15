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
package org.jgentleframework.core.reflection;

import java.util.HashMap;

import org.aopalliance.reflect.Metadata;
import org.aopalliance.reflect.ProgramUnit;
import org.jgentleframework.utils.Assertor;

/**
 * This class is an abstract class that is represented a {@link ProgramUnit}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 16, 2008
 * @see ProgramUnit
 */
public abstract class AbstractProgramUnit implements ProgramUnit {
	/** The metadata list. */
	HashMap<Object, Metadata>	metadataList	= new HashMap<Object, Metadata>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.reflect.ProgramUnit#addMetadata(org.aopalliance.reflect
	 * .Metadata)
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
	 * @see org.aopalliance.reflect.ProgramUnit#getMetadata(java.lang.Object)
	 */
	@Override
	public Metadata getMetadata(Object key) {

		return this.metadataList.get(key);
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.reflect.ProgramUnit#getMetadatas()
	 */
	@Override
	public Metadata[] getMetadatas() {

		return this.metadataList.values().toArray(
				new Metadata[this.metadataList.values().size()]);
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.reflect.ProgramUnit#removeMetadata(java.lang.Object)
	 */
	@Override
	public void removeMetadata(Object key) {

		this.metadataList.remove(key);
	}
}
