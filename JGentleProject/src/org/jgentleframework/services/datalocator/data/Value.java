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

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.enums.DataType;

/**
 * The Interface Value.
 * 
 * @param <T>
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 1, 2007
 */
public interface Value<T> {
	/**
	 * Adds one value data to the current {@link Value value}.
	 * 
	 * @param index
	 *            index at which the specified element is to be inserted
	 * @param valueData
	 *            the given value data need to added.
	 * @throws IndexOutOfBoundsException
	 *             - if the index is out of range (index < 0 || index > size())
	 * @throws NullPointerException
	 *             - if the specified element (value data) is <code>null</code>.
	 */
	public void addValueData(int index, T valueData);

	/**
	 * Adds one value data to the current {@link Value value}
	 * 
	 * @param valueData
	 *            the given value data need to be added
	 */
	public void addValueData(T valueData);

	/**
	 * Clear all value data of current {@link Value value}.
	 */
	public void clear();

	/**
	 * Returns the created date of current {@link Value value}
	 */
	public Date getCreatedDate();

	/**
	 * Return the modified date of current {@link Value value}
	 */
	public Date getModifiedDate();

	/**
	 * Returns the parents key of current {@link Key key}
	 */
	public Key<?> getParentsKey();

	/**
	 * Returns the number of value data existing in current {@link Value value}
	 */
	public int getSize();

	/**
	 * Returns the list containing all value data existing in current
	 * {@link Value value}
	 */
	public List<T> getValueData();

	/**
	 * Returns the value data appropriate to the given index
	 * 
	 * @param index
	 *            index at which the specified element is to be returned.
	 * @throws RepositoryRuntimeException
	 *             - if the index is out of range
	 */
	public T getValueData(int index);

	/**
	 * Returns the {@link Value value} name.
	 */
	public String getValueName();

	/**
	 * Returns the {@link Value value} path
	 */
	public String getValuePath();

	/**
	 * Returns the {@link Value value} type
	 * 
	 * @see DataType
	 */
	public DataType getValueType();

	/**
	 * Checks if is empty.
	 * 
	 * @return <code>true</code>, if is empty
	 */
	public boolean isEmpty();

	/**
	 * Returns the {@link Iterator} containing all value data existing in
	 * current {@link Value value}
	 * 
	 * @return iterator
	 */
	public Iterator<T> iterator();

	/**
	 * Removes a value data appropriate to the given index from current
	 * {@link Value value}.
	 * 
	 * @param index
	 *            index at which the specified element is to be removed.
	 * @throws RepositoryRuntimeException
	 *             - if the index is out of range
	 */
	public void removeValueData(int index);

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate
	 *            the created date
	 */
	public void setCreatedDate(Date createdDate);

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
	public void setParentsKey(Key<T> parentsKey);

	/**
	 * Sets the value data.
	 * 
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @throws RepositoryRuntimeException
	 *             - if the index is out of range
	 */
	public void setValueData(int index, T value);

	/**
	 * Sets the value name.
	 * 
	 * @param valueName
	 *            the value name
	 */
	public void setValueName(String valueName);

	/**
	 * Sets the value type.
	 * 
	 * @param valueType
	 *            the value type
	 * @see DataType
	 */
	public void setValueType(DataType valueType);

	/**
	 * To array.
	 */
	public Object[] toArray();
}
