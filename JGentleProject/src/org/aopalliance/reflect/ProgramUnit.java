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
package org.aopalliance.reflect;

/**
 * An abstract program unit.
 * <p>
 * Any structural unit of a base program (class, member, ...). Units exclude the
 * code.
 * <p>
 * When referencing a program unit, the client can retrieve unit locators using
 * {@link #getLocator()} or more specific methods. This locators shall be used
 * conjointly whith an {@link org.aopalliance.instrument.Instrumentor}
 * implementation in order to perform aspetual transformations of the program
 * (e.g. type merging, parameters adding,...).
 * <p>
 * Program units also supports metadatas, i.e. the ability to add extra
 * information on the base program so that the client can extends its meaning
 * very easily.
 * 
 * @see org.aopalliance.instrument.Instrumentor
 * @see UnitLocator
 * @see Class
 * @see Method
 * @see Field
 */
public interface ProgramUnit {
	/**
	 * Returns the locator that corresponds to this unit.
	 */
	UnitLocator getLocator();

	/**
	 * Returns the metadata that is associated to this unit from its key.
	 */
	Metadata getMetadata(Object key);

	/**
	 * Returns all the metadatas that are associated to the current unit.
	 */
	Metadata[] getMetadatas();

	/**
	 * Associates a metadata to the current unit.
	 * <p>
	 * If a metadata already exists with the same key, then its value is
	 * replaced by the newly given one.
	 */
	void addMetadata(Metadata metadata);

	/**
	 * Removes a metadata from its key.
	 * <p>
	 * If none metadata having the given key exists, then this method has no
	 * effect.
	 */
	void removeMetadata(Object key);
}
