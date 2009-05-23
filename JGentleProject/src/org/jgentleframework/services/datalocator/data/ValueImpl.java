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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.enums.DataType;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ObjectUtils;

/**
 * Lưu trữ thông tin value, một Value phải được cất trữ trong một Key, Value
 * không thể tồn tại riêng rẽ một mình ngay tại ROOT của registry. Thông tin về
 * kiểu dữ liệu mà value có thể cất giữ được chỉ định bởi kiểu chỉ định của Key
 * chứa value. Một value được khởi tạo với 1 kiểu chỉ định chỉ có thể được add
 * vào trong một Key có chỉ định kiểu tương ứng phù hợp.
 * 
 * @param <T>
 *            kiểu dữ liệu mà valueData của Value có thể cất giữ.
 * @author LE QUOC CHUNG
 * @date Aug 28, 2006
 * @See KeyType
 * @see Value
 */
class ValueImpl<T> implements Serializable, Value<T> {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1303652107673025138L;

	/** The created date. */
	private Date				createdDate;

	/** The modified date. */
	private Date				modifiedDate;

	/** The parents key. */
	private Key<T>				parentsKey			= null;

	/** The value data. */
	private List<T>				valueData			= new LinkedList<T>();

	/** The value name. */
	private String				valueName;

	/** The value type. */
	private DataType			valueType;

	/**
	 * Hàm constructor khởi tạo đối tượng Value.
	 * 
	 * @param valueName
	 *            tên của value
	 * @param valueData
	 *            giá trị trong value
	 * @param valueType
	 *            kiểu Type của value được xác định bởi MBRegistryType
	 * @see DataType
	 */
	public ValueImpl(String valueName, List<T> valueData, DataType valueType) {

		this.createdDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.modifiedDate = this.createdDate;
		Assertor.notNull(valueName, "Value name must not be null!");
		Assertor.notNull(valueData, "Value data must not be null!");
		Assertor.notNull(valueType, "Value type must not be null!");
		this.valueName = valueName;
		this.valueData = valueData;
		this.valueType = valueType;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#addValueData(int,
	 * java.lang.Object)
	 */
	@Override
	public synchronized void addValueData(int index, T valueData) {

		Assertor.notNull(valueData, "valueData must not be null.");
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.valueData.add(index, valueData);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#addValueData(java
	 * .lang.Object)
	 */
	@Override
	public synchronized void addValueData(T valueData) {

		addValueData(this.valueData.size(), valueData);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#clear()
	 */
	@Override
	public synchronized void clear() {

		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.valueData.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#getDateCreated()
	 */
	@Override
	public Date getCreatedDate() {

		return (Date) ObjectUtils.deepCopy(createdDate);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#getDateModified()
	 */
	@Override
	public Date getModifiedDate() {

		return (Date) ObjectUtils.deepCopy(this.modifiedDate);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#getKeyParents()
	 */
	@Override
	public Key<T> getParentsKey() {

		return parentsKey;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#getSize()
	 */
	@Override
	public int getSize() {

		return this.valueData.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#getValueData()
	 */
	@Override
	public List<T> getValueData() {

		return valueData;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#getValueData(int)
	 */
	@Override
	public T getValueData(int index) {

		if (index < 0 || index > this.valueData.size()) {
			throw new RepositoryRuntimeException(
					"The index number is invalid !");
		}
		return this.valueData.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.abstracts.ValueType#getValueName
	 * ()
	 */
	@Override
	public String getValueName() {

		return valueName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#getValuePath()
	 */
	@Override
	public String getValuePath() {

		StringBuilder data = new StringBuilder();
		data.insert(0, "/" + this.getValueName());
		data.insert(0, "/" + this.getParentsKey().getKeyName());
		ArrayList<Key<?>> pathList = new ArrayList<Key<?>>();
		pathList.add(this.getParentsKey());
		Key<?> keyCurrent = null;
		boolean forTrue = true;
		while (forTrue) {
			keyCurrent = pathList.get(pathList.size() - 1).getParentsKey();
			if (keyCurrent == null)
				forTrue = false;
			else {
				data.insert(0, "/" + keyCurrent.getKeyName());
			}
			pathList.add(keyCurrent);
		}
		String result = data.toString();
		if (result.startsWith("/")) {
			result = data.substring(1);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#getValueType()
	 */
	@Override
	public DataType getValueType() {

		return valueType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return this.valueData.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#iterator()
	 */
	@Override
	public Iterator<T> iterator() {

		return this.valueData.iterator();
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
			this.modifiedDate = Calendar.getInstance(Locale.getDefault())
					.getTime();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#removeValueData(int)
	 */
	@Override
	public synchronized void removeValueData(int index) {

		if (index < 0 || index > this.valueData.size()) {
			throw new RepositoryRuntimeException("index number is invalid.");
		}
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.valueData.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setCreatedDate(java
	 * .util.Date)
	 */
	@Override
	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setModifiedDate(
	 * java.util.Date)
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setParentsKey(org
	 * .jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public void setParentsKey(Key<T> parentsKey) {

		this.parentsKey = parentsKey;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setValueData(int,
	 * java.lang.Object)
	 */
	@Override
	public synchronized void setValueData(int index, T value) {

		Assertor.notNull(value);
		if (this.valueData == null) {
			valueData = new ArrayList<T>();
		}
		if (index < 0 || index > this.valueData.size()) {
			throw new RepositoryRuntimeException("index number is invalid.");
		}
		this.valueData.set(index, value);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setValueName(java
	 * .lang.String)
	 */
	@Override
	public void setValueName(String valueName) {

		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.valueName = valueName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Value#setValueType(org
	 * .jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public void setValueType(DataType valueType) {

		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.valueType = valueType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Value#toArray()
	 */
	@Override
	public Object[] toArray() {

		return this.valueData.toArray();
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
}
