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
package org.jgentleframework.services.datalocator.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.enums.ComparatorKeySortedBy;
import org.jgentleframework.services.datalocator.enums.ComparatorValueSortedBy;
import org.jgentleframework.services.datalocator.enums.DataType;
import org.jgentleframework.services.datalocator.enums.EComparator;

/**
 * {@link Key} instance represents all information of its includes key name,
 * created date, modified date, parent key, ... and contains sub keys, values if
 * they exist.
 * <p>
 * <b>Note:</b> Generic Type <T> effect only to its {@link Value values} but no
 * effect to its sub keys.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 13, 2007
 * @param <T>
 */
public interface Key<T> {
	/**
	 * Add the given {@link Key key} to current {@link Key key}.
	 * 
	 * @param key
	 *            the given {@link Key key}.
	 * @return returns the previous {@link Key key} if its key name equals to
	 *         the name of given {@link Key} need to be added, otherwise returns
	 *         null.
	 */
	public Key<?> addKey(Key<?> key);

	/**
	 * Add the given {@link Value value} to current {@link Key key}.
	 * 
	 * @param value
	 *            the given {@link Value value} need to be added.
	 * @return returns the prevous {@link Value value} if its value name equals
	 *         to the name of given {@link Value value} need to be added,
	 *         otherwise returns null.
	 */
	public Value<T> addValue(Value<T> value);

	/**
	 * Creates the backup file of the current {@link Key key}.
	 * 
	 * @param fileOutput
	 *            the {@link FileOutputStream}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void backup(FileOutputStream fileOutput) throws IOException;

	/**
	 * Removes all sub {@link Key keys} of current {@link Key key}.
	 */
	public void clearSubKeys();

	/**
	 * Removes all {@link Value values} of current {@link Key key}.
	 */
	public void clearValues();

	/**
	 * Returns the {@link Collection} containing all sub {@link Key keys} of
	 * current {@link Key key}.
	 */
	public Collection<Key<?>> collectionSubKeys();

	/**
	 * Returns the {@link Collection} containing all {@link Value values} of
	 * current {@link Key key}.
	 */
	public Collection<Value<T>> collectionValues();

	/**
	 * Returns <code>true</code> if the current {@link Key key} contains the
	 * {@link Key key} appropriate to the given key name, otherwise returns
	 * <code>false</code>.
	 * 
	 * @param keyName
	 *            the given key name
	 */
	public boolean containsKey(String keyName);

	/**
	 * Returns <code>true</code> if current {@link Key key} contains the
	 * {@link Value value} appropriate to the given value name, otherwise return
	 * <code>false</code>.
	 * 
	 * @param valueName
	 *            the given value name
	 */
	public boolean containsValue(String valueName);

	/**
	 * Returns all sub keys in hierarchical system of current {@link Key key}
	 * appropriate to the given key type ({@link DataType}).
	 * 
	 * @param keyType
	 *            the key type
	 * @return returns the {@link List list} containing keys if they exist,
	 *         otherwise return null.
	 * @see DataType
	 */
	public List<Key<?>> findKey(DataType keyType);

	/**
	 * Returns all sub keys in hierarchical system of current {@link Key key}.
	 * 
	 * @param begin
	 *            begin date
	 * @param end
	 *            end date
	 * @param dateType
	 *            if <code>true</code>, find keys basing on created date,
	 *            otherwise basing on modified date.
	 * @return returns the {@link List list} containing keys if they exist,
	 *         otherwise return null.
	 * @throws RepositoryRuntimeException
	 *             throws this exception if begin date or end date is invalid.
	 */
	public List<Key<?>> findKey(Date begin, Date end, boolean dateType);

	/**
	 * Returns all sub keys in hierarchical system of current {@link Key key}
	 * appropriate to the created date or modified date of its.
	 * 
	 * @param givenDate
	 *            the given date
	 * @param comparator
	 *            specified the sorting rule
	 * @param dateType
	 *            if <code>true</code>, find keys basing on created date,
	 *            otherwise basing on modified date.
	 * @return returns the {@link List list} containing keys if they exist,
	 *         otherwise return null.
	 * @see EComparator
	 */
	public List<Key<?>> findKey(Date givenDate, EComparator comparator,
			boolean dateType);

	/**
	 * Returns all sub keys in hierarchical system of current {@link Key key}
	 * appropriate to the given key name.
	 * 
	 * @param keyName
	 *            the given name
	 * @return returns the {@link List list} containing all keys appropriate to
	 *         the given key name if they exist, otherwise returns null.
	 * @throws RepositoryRuntimeException
	 *             throws this exception if key name is invalid.
	 */
	public List<Key<?>> findKey(String... keyName);

	/**
	 * Returns all {@link Value values} in hierarchical system of current
	 * {@link Key key} appropriate to value type ({@link DataType}).
	 * 
	 * @param valueType
	 *            the value type
	 * @return returns the {@link List list} containing values if they exist,
	 *         otherwise return null.
	 * @see DataType
	 */
	public List<Value<?>> findValue(DataType valueType);

	/**
	 * Returns all {@link Value values} in hierarchical system of current
	 * {@link Key key}.
	 * 
	 * @param begin
	 *            the begin date
	 * @param end
	 *            the end date.
	 * @param dateType
	 *            if <code>true</code>, find keys basing on created date,
	 *            otherwise basing on modified date.
	 * @return returns the {@link List list} containing values if they exist,
	 *         otherwise return null.
	 * @throws RepositoryRuntimeException
	 *             throws this exception if begin date or end date is invalid.
	 */
	public List<Value<?>> findValue(Date begin, Date end, boolean dateType);

	/**
	 * Returns all {@link Value values} in hierarchical system of current
	 * {@link Key key} appropriate to the given date.
	 * 
	 * @param givenDate
	 *            the given date.
	 * @param comparator
	 *            specified the sorting rule
	 * @param dateType
	 *            if <code>true</code>, find keys basing on created date,
	 *            otherwise basing on modified date.
	 * @return returns the {@link List list} containing values if they exist,
	 *         otherwise return null.
	 * @see EComparator
	 */
	public List<Value<?>> findValue(Date givenDate, EComparator comparator,
			boolean dateType);

	/**
	 * Returns all {@link Value values} in hierarchical system of current
	 * {@link Key key} appropriate to the given value name.
	 * 
	 * @param valueName
	 *            the name of values
	 * @return returns the {@link List list} containing values if they exist,
	 *         otherwise return null.
	 * @throws RepositoryRuntimeException
	 *             thrown this exception if value name is null or the name of
	 *             current key is null.
	 */
	public List<Value<?>> findValue(String... valueName);

	/**
	 * Returns the {@link List list} containing all sub {@link Key keys} of
	 * current {@link Key key} and all keys of all sub keys existing in whole
	 * its hierarchical system.
	 * 
	 * @param sortedBy
	 *            the {@link Comparator} represents sorting rule corresponding
	 *            to all sub keys in returned {@link List list}
	 * @return returns the {@link List list} containing all sub keys of current
	 *         {@link Key key} if they exist, otherwise returns
	 *         <code>null</code>.
	 * @see ComparatorKeySortedBy
	 */
	public List<Key<?>> getAllSubKeys(ComparatorKeySortedBy sortedBy);

	/**
	 * Returns the {@link List list} containing all {@link Value values} of
	 * current {@link Key key} and all values of all sub keys existing in whole
	 * its hierarchical system.
	 * 
	 * @param sortedBy
	 *            the {@link Comparator} represents sorting rule corresponding
	 *            to all {@link Value values} in returned {@link List list}.
	 * @return returns the {@link List list} containing all {@link Value values}
	 *         of current {@link Key key} if they exist, otherwise returns
	 *         <code>null</code>.
	 * @see ComparatorValueSortedBy
	 */
	public List<Value<?>> getAllValues(ComparatorValueSortedBy sortedBy);

	/**
	 * Returns the created date of current {@link Key key}
	 */
	public Date getCreatedDate();

	/**
	 * Returns the sub {@link Key key} of current {@link Key key} appropriate to
	 * the given key name
	 * 
	 * @param keyName
	 *            the given key name
	 * @return returns the {@link Key key} appropriate to the given name if it
	 *         existed, otherwise returns <code>null</code>.
	 */
	public Key<?> getKeyFromName(String keyName);

	/**
	 * Returns a {@link Map map} containing all sub {@link Key keys} of current
	 * {@link Key key}.
	 * 
	 * @return returns a {@link Map map} containing all {@link Key keys} if they
	 *         exist, if not, return a empty {@link Map map}.
	 */
	public Map<String, Key<?>> getKeyList();

	/**
	 * Returns the key name of current {@link Key key}.
	 */
	public String getKeyName();

	/**
	 * Returns the string representing the path of current {@link Key key}.
	 * 
	 * @return String
	 */
	public String getKeyPath();

	/**
	 * Returns the key type of current {@link Key key}
	 * 
	 * @see DataType
	 */
	public DataType getKeyType();

	/**
	 * Returns the modified date of current {@link Key key.}
	 */
	public Date getModifiedDate();

	/**
	 * Returns the parents key of current {@link Key key} if it existed, if not
	 * returns <code>null</code>
	 */
	public Key<?> getParentsKey();

	/**
	 * Gets the repository processor.
	 * 
	 * @return the repositoryProcessor
	 */
	public RepositoryProcessor getRepositoryProcessor();

	/**
	 * Returns the {@link Value key} of current {@link Key key}
	 * 
	 * @param valueName
	 *            the given value name
	 * @return returns the {@link Value value} appropriate to the given name if
	 *         it existed, otherwise returns null.
	 */
	public Value<T> getValueFromName(String valueName);

	/**
	 * Returns a {@link Map map} containing all {@link Value values} of current
	 * {@link Key key}.
	 * 
	 * @return returns a {@link Map map} containing all {@link Value values} if
	 *         they exist, if not, return a empty {@link Map map}.
	 */
	public Map<String, Value<T>> getValueList();

	/**
	 * Checks if the current {@link Key key} doesn't have any sub {@link Key
	 * keys}.
	 * 
	 * @return returns <code>true</code> if the current {@link Key key} doesn't
	 *         have any sub {@link Key keys}, otherwise returns
	 *         <code>false</code>.
	 */
	public boolean isEmptySubKeysList();

	/**
	 * Checks if the current {@link Key key} doesn't have any {@link Value
	 * values}.
	 * 
	 * @return returns <code>true</code> if the current {@link Key key} doesn't
	 *         have any {@link Value values}, otherwise returns
	 *         <code>false</code>.
	 */
	public boolean isEmptyValuesList();

	/**
	 * Returns the {@link Iterator} containing all sub {@link Key keys} of
	 * current {@link Key key}
	 */
	public Iterator<Key<?>> iteratorSubKeys();

	/**
	 * Returns the {@link Iterator} containing all {@link Value values} of
	 * current {@link Key keys}
	 */
	public Iterator<Value<T>> iteratorValues();

	/**
	 * Removes one {@link Key key} appropriate to the given key name from
	 * current {@link Key key}
	 * 
	 * @param keyName
	 *            the given key name
	 * @return returns the removed {@link Key key} appropriate to the given name
	 *         if it exist, it not return <code>null</code>.
	 */
	public Key<?> removeKey(String keyName);

	/**
	 * Removes one {@link Value value} appropriate to the given value name from
	 * current {@link Key key}.
	 * 
	 * @param valueName
	 *            the given value name
	 * @return returns the removed {@link Value value} appropriate to the given
	 *         name if it exist, it not return <code>null</code>.
	 */
	public Value<T> removeValue(String valueName);

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate
	 *            the created date
	 */
	public void setCreatedDate(Date createdDate);

	/**
	 * Sets the key name
	 * 
	 * @param keyName
	 *            the given key name
	 */
	public void setKeyName(String keyName);

	/**
	 * Sets the key type.
	 * 
	 * @param keyType
	 *            the key type
	 * @see DataType
	 */
	public void setKeyType(DataType keyType);

	/**
	 * Sets the modified date.
	 * 
	 * @param modifiedDate
	 *            the modified date
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Sets the parents key.
	 * 
	 * @param parentsKey
	 *            the parents key
	 */
	public void setParentsKey(Key<?> parentsKey);
}
