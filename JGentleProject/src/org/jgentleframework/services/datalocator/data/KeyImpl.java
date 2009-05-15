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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.enums.ComparatorKeySortedBy;
import org.jgentleframework.services.datalocator.enums.ComparatorValueSortedBy;
import org.jgentleframework.services.datalocator.enums.DataType;
import org.jgentleframework.services.datalocator.enums.EComparator;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ObjectUtils;

/**
 * The Class KeyImpl.
 * 
 * @param <T>  *
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 29, 2006
 * @see Value
 * @See Key
 */
class KeyImpl<T> implements Serializable, Key<T> {
	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= -5093153955473668345L;

	/** created date. */
	private Date					createdDate;

	/** The key list. */
	private Map<String, Key<?>>		keyList;

	/** key name. */
	private String					keyName;

	/** The key type. */
	private DataType				keyType;

	/** modified date. */
	private Date					modifiedDate;

	/** parents key. */
	private Key<?>					parentsKey			= null;

	/** The current {@link RepositoryProcessor}. */
	private RepositoryProcessor		repositoryProcessor	= null;

	/** values of current key. */
	private Map<String, Value<T>>	valueList;

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 */
	public KeyImpl(String keyName, DataType keyType) {

		Assertor.notNull(keyName, "The key name must not be null !");
		Assertor.notNull(keyType, "The key type must not be null !");
		RepositoryProcessor obj = null;
		new KeyImpl<T>(keyName, keyType, obj);
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param keyList
	 *            the key list
	 */
	public KeyImpl(String keyName, DataType keyType, Key<?>... keyList) {

		new KeyImpl<T>(keyName, keyType, null, keyList);
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param keyList
	 *            the key list
	 * @param valueList
	 *            the value list
	 */
	public KeyImpl(String keyName, DataType keyType, Key<?>[] keyList,
			Value<T>[] valueList) {

		new KeyImpl<T>(keyName, keyType, keyList, valueList, null);
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param keyList
	 *            the key list
	 * @param valueList
	 *            the value list
	 * @param repositoryProcessor
	 *            the repositoryProcessor
	 */
	public KeyImpl(String keyName, DataType keyType, Key<?>[] keyList,
			Value<T>[] valueList, RepositoryProcessor repositoryProcessor) {

		Assertor.notNull(keyName, "The key name must not be null !");
		Assertor.notNull(keyType, "The key type must not be null !");
		this.createdDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.modifiedDate = this.createdDate;
		this.keyName = keyName;
		this.valueList = new HashMap<String, Value<T>>();
		this.keyList = new HashMap<String, Key<?>>();
		this.keyType = keyType;
		if (keyList != null)
			for (Key<?> element : keyList) {
				element.setParentsKey(this);
				this.keyList.put(element.getKeyName(), element);
			}
		if (valueList != null)
			for (Value<T> element : valueList) {
				element.setParentsKey(this);
				this.valueList.put(element.getValueName(), element);
			}
		this.repositoryProcessor = repositoryProcessor;
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param repositoryProcessor
	 *            the repositoryProcessor
	 */
	public KeyImpl(String keyName, DataType keyType,
			RepositoryProcessor repositoryProcessor) {

		this.createdDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.modifiedDate = this.createdDate;
		Assertor.notNull(keyName, "The key name must not be null !");
		Assertor.notNull(keyType, "The key type must not be null !");
		this.keyName = keyName;
		this.valueList = new HashMap<String, Value<T>>();
		this.keyList = new HashMap<String, Key<?>>();
		this.keyType = keyType;
		this.repositoryProcessor = repositoryProcessor;
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param keyList
	 *            the key list
	 * @param repositoryProcessor
	 *            the repositoryProcessor
	 */
	public KeyImpl(String keyName, DataType keyType,
			RepositoryProcessor repositoryProcessor, Key<?>... keyList) {

		Assertor.notNull(keyName, "The key name must not be null !");
		Assertor.notNull(keyType, "The key type must not be null !");
		this.createdDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.modifiedDate = this.createdDate;
		this.keyName = keyName;
		this.valueList = new HashMap<String, Value<T>>();
		this.keyList = new HashMap<String, Key<?>>();
		this.keyType = keyType;
		if (keyList != null)
			for (Key<?> element : keyList) {
				element.setParentsKey(this);
				this.keyList.put(element.getKeyName(), element);
			}
		this.repositoryProcessor = repositoryProcessor;
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param valueList
	 *            the value list
	 */
	public KeyImpl(String keyName, DataType keyType, Value<T>[] valueList) {

		new KeyImpl<T>(keyName, keyType, valueList, null);
	}

	/**
	 * Instantiates a new key.
	 * 
	 * @param keyName
	 *            the key name
	 * @param keyType
	 *            the key type
	 * @param valueList
	 *            the value list
	 * @param repositoryProcessor
	 *            the repositoryProcessor
	 */
	public KeyImpl(String keyName, DataType keyType, Value<T>[] valueList,
			RepositoryProcessor repositoryProcessor) {

		Assertor.notNull(keyName, "The key name must not be null !");
		Assertor.notNull(keyType, "The key type must not be null !");
		this.createdDate = Calendar.getInstance(Locale.getDefault()).getTime();
		this.modifiedDate = this.createdDate;
		this.keyName = keyName;
		this.valueList = new HashMap<String, Value<T>>();
		this.keyList = new HashMap<String, Key<?>>();
		this.keyType = keyType;
		if (valueList != null)
			for (Value<T> element : valueList) {
				element.setParentsKey(this);
				this.valueList.put(element.getValueName(), element);
			}
		this.repositoryProcessor = repositoryProcessor;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#addKey(org.
	 * jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public synchronized Key<?> addKey(Key<?> key) {

		Assertor.notNull(key, "key must not be null.");
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		key.setParentsKey(this);
		key.setModifiedDate(this.modifiedDate);
		return this.keyList.put(key.getKeyName(), key);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#addValue(org.
	 * jgentleframework.services.datalocator.data.Value)
	 */
	@Override
	public synchronized Value<T> addValue(Value<T> value) {

		Assertor.notNull(value);
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		value.setParentsKey(this);
		value.setModifiedDate(this.modifiedDate);
		return this.valueList.put(value.getValueName(), value);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#backup(java.io.
	 * FileOutputStream)
	 */
	@Override
	public synchronized void backup(FileOutputStream fileOutput)
			throws IOException {

		ObjectOutputStream out = null;
		out = new ObjectOutputStream(fileOutput);
		out.writeObject(this);
		out.flush();
		out.close();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#clearSubKeys()
	 */
	@Override
	public synchronized void clearSubKeys() {

		for (Key<?> key : this.keyList.values()) {
			key.setParentsKey(null);
			this.modifiedDate = Calendar.getInstance(Locale.getDefault())
					.getTime();
			key.setModifiedDate(this.modifiedDate);
		}
		this.keyList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#clearValues()
	 */
	@Override
	public synchronized void clearValues() {

		for (Value<T> element : this.valueList.values()) {
			element.setParentsKey(null);
			this.modifiedDate = Calendar.getInstance(Locale.getDefault())
					.getTime();
			element.setModifiedDate(this.modifiedDate);
		}
		this.valueList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#collectionSubKeys()
	 */
	@Override
	public Collection<Key<?>> collectionSubKeys() {

		return this.keyList.values();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#collectionValues()
	 */
	@Override
	public Collection<Value<T>> collectionValues() {

		return this.valueList.values();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#containsKey(java.lang
	 * .String)
	 */
	@Override
	public boolean containsKey(String keyName) {

		return this.keyList.containsKey(keyName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#containsValue(java
	 * .lang.String)
	 */
	@Override
	public boolean containsValue(String valueName) {

		return this.valueList.containsKey(valueName);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#findKey(org.
	 * jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public List<Key<?>> findKey(DataType keyType) {

		List<Key<?>> result = new ArrayList<Key<?>>();
		List<Key<?>> list = getAllSubKeys(ComparatorKeySortedBy.PATH_ORDER_KEY);
		for (Object obj : list.toArray()) {
			if (((Key<?>) obj).getKeyType().equals(keyType)) {
				result.add(((Key<?>) obj));
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findKey(java.util.
	 * Date, java.util.Date, boolean)
	 */
	@Override
	public List<Key<?>> findKey(Date dateFrom, Date dateTo, boolean dateType) {

		if (!dateTo.before(dateFrom)) {
			throw new RepositoryRuntimeException("Parameter is invalid !!");
		}
		List<Key<?>> result = new ArrayList<Key<?>>();
		List<Key<?>> list = getAllSubKeys(ComparatorKeySortedBy.PATH_ORDER_KEY);
		for (Object obj : list.toArray()) {
			Date date = null;
			if (dateType)
				date = ((Key<?>) obj).getCreatedDate();
			else
				date = ((Key<?>) obj).getModifiedDate();
			if (date.after(dateFrom) && date.before(dateTo))
				result.add((Key<?>) obj);
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findKey(java.util.
	 * Date, org.jgentleframework.services.datalocator.enums.EComparator,
	 * boolean)
	 */
	@Override
	public List<Key<?>> findKey(Date givenDate, EComparator comparator,
			boolean dateType) {

		List<Key<?>> result = new ArrayList<Key<?>>();
		List<Key<?>> list = getAllSubKeys(ComparatorKeySortedBy.PATH_ORDER_KEY);
		for (Object obj : list.toArray()) {
			Date date = null;
			if (dateType)
				date = ((Key<?>) obj).getCreatedDate();
			else
				date = ((Key<?>) obj).getModifiedDate();
			switch (comparator) {
			case AFTER:
				if (date.after(givenDate))
					result.add((Key<?>) obj);
				break;
			case BEFORE:
				if (date.before(givenDate))
					result.add((Key<?>) obj);
				break;
			case EQUALS:
				if (date.equals(givenDate))
					result.add((Key<?>) obj);
				break;
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findKey(java.lang.
	 * String[])
	 */
	@Override
	public List<Key<?>> findKey(String... keyName) {

		if (keyName == null || keyName.length == 0) {
			throw new RepositoryRuntimeException("keyName is invalid !!");
		}
		List<Key<?>> result = new ArrayList<Key<?>>();
		List<Key<?>> list = getAllSubKeys(ComparatorKeySortedBy.PATH_ORDER_KEY);
		for (Object obj : list.toArray()) {
			for (String name : keyName) {
				if (((Key<?>) obj).getKeyName().equals(name)) {
					result.add((Key<?>) obj);
				}
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#findValue(org.
	 * jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public List<Value<?>> findValue(DataType valueType) {

		List<Value<?>> result = new ArrayList<Value<?>>();
		List<Value<?>> list = getAllValues(ComparatorValueSortedBy.PATH_ORDER_VALUE);
		for (Object obj : list.toArray()) {
			if (((Value<?>) obj).getValueType().equals(valueType)) {
				result.add((Value<?>) obj);
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findValue(java.util
	 * .Date, java.util.Date, boolean)
	 */
	@Override
	public List<Value<?>> findValue(Date dateFrom, Date dateTo, boolean dateType) {

		if (!dateTo.before(dateFrom)) {
			throw new RepositoryRuntimeException("Parameter is invalid !!");
		}
		List<Value<?>> result = new ArrayList<Value<?>>();
		List<Value<?>> list = getAllValues(ComparatorValueSortedBy.PATH_ORDER_VALUE);
		for (Object obj : list.toArray()) {
			Date date = null;
			if (dateType)
				date = ((Value<?>) obj).getCreatedDate();
			else
				date = ((Value<?>) obj).getModifiedDate();
			if (date.after(dateFrom) && date.before(dateTo))
				result.add((Value<?>) obj);
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findValue(java.util
	 * .Date,
	 * org.jgentleframework.services.datalocator.data.ManagerImpl.EComparator,
	 * boolean)
	 */
	@Override
	public List<Value<?>> findValue(Date dateFind, EComparator comparator,
			boolean dateType) {

		List<Value<?>> result = new ArrayList<Value<?>>();
		List<Value<?>> list = getAllValues(ComparatorValueSortedBy.PATH_ORDER_VALUE);
		for (Object obj : list.toArray()) {
			Date date = null;
			if (dateType)
				date = ((Value<?>) obj).getCreatedDate();
			else
				date = ((Value<?>) obj).getCreatedDate();
			switch (comparator) {
			case AFTER:
				if (date.after(dateFind))
					result.add((Value<?>) obj);
				break;
			case BEFORE:
				if (date.before(dateFind))
					result.add((Value<?>) obj);
				break;
			case EQUALS:
				if (date.equals(dateFind))
					result.add((Value<?>) obj);
				break;
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#findValue(java.lang
	 * .String[])
	 */
	@Override
	public List<Value<?>> findValue(String... valueName) {

		synchronized (this.keyName) {
			if (this.keyName == null || valueName.length == 0) {
				throw new RepositoryRuntimeException("keyName is invalid !!");
			}
		}
		List<Value<?>> result = new ArrayList<Value<?>>();
		List<Value<?>> list = getAllValues(ComparatorValueSortedBy.PATH_ORDER_VALUE);
		for (Object obj : list.toArray()) {
			for (String name : valueName) {
				if (((Value<?>) obj).getValueName().equals(name)) {
					result.add((Value<?>) obj);
				}
			}
		}
		if (result.size() == 0) {
			return null;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#getAllSubKeys(org.
	 * jgentleframework.services.datalocator.enums.ComparatorKeySortedBy)
	 */
	@Override
	public List<Key<?>> getAllSubKeys(ComparatorKeySortedBy sortedBy) {

		List<Key<?>> result = this.repositoryProcessor.getAllSubKeys(sortedBy,
				this);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#getAllValues(org.
	 * jgentleframework.services.datalocator.enums.ComparatorValueSortedBy)
	 */
	@Override
	public List<Value<?>> getAllValues(ComparatorValueSortedBy sortedBy) {

		List<Value<?>> result = this.repositoryProcessor.getAllValues(sortedBy,
				this);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getCreatedDate()
	 */
	@Override
	public Date getCreatedDate() {

		return (Date) ObjectUtils.deepCopy(this.createdDate);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#getKeyFromName(java
	 * .lang.String)
	 */
	@Override
	public Key<?> getKeyFromName(String keyName) {

		Assertor.notNull(keyName, "Key name must not be null.");
		return this.keyList.get(keyName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getKeyList()
	 */
	@Override
	public Map<String, Key<?>> getKeyList() {

		return this.keyList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getKeyName()
	 */
	@Override
	public String getKeyName() {

		return this.keyName;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getKeyPath()
	 */
	@Override
	public String getKeyPath() {

		StringBuilder data = new StringBuilder();
		data.insert(0, "/" + this.getKeyName());
		List<Key<?>> pathList = new ArrayList<Key<?>>();
		pathList.add(this);
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
	 * @see org.jgentleframework.services.datalocator.data.Key#getKeyType()
	 */
	@Override
	public DataType getKeyType() {

		return keyType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getModifiedDate()
	 */
	@Override
	public Date getModifiedDate() {

		return (Date) ObjectUtils.deepCopy(this.modifiedDate);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getParentsKey()
	 */
	@Override
	public Key<?> getParentsKey() {

		return parentsKey;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#getRepositoryProcessor
	 * ()
	 */
	@Override
	public RepositoryProcessor getRepositoryProcessor() {

		return repositoryProcessor;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#getValueFromName(java
	 * .lang.String)
	 */
	@Override
	public Value<T> getValueFromName(String valueName) {

		Assertor.notNull(valueName, "valueName must not be null.");
		return this.valueList.get(valueName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#getValueList()
	 */
	@Override
	public Map<String, Value<T>> getValueList() {

		return this.valueList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#isEmptySubKeysList()
	 */
	@Override
	public boolean isEmptySubKeysList() {

		if (this.keyList.size() > 0) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#isEmptyValuesList()
	 */
	@Override
	public boolean isEmptyValuesList() {

		if (this.valueList.size() > 0) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#iteratorSubKeys()
	 */
	@Override
	public Iterator<Key<?>> iteratorSubKeys() {

		return this.keyList.values().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Key#iteratorValues()
	 */
	@Override
	public Iterator<Value<T>> iteratorValues() {

		return this.valueList.values().iterator();
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
			throw new IOException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#removeKey(java.lang
	 * .String)
	 */
	@Override
	public synchronized Key<?> removeKey(String keyName) {

		Key<?> returnKey = this.keyList.remove(keyName);
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		returnKey.setParentsKey(null);
		returnKey.setModifiedDate(this.modifiedDate);
		return returnKey;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#removeValue(java.lang
	 * .String)
	 */
	@Override
	public synchronized Value<T> removeValue(String valueName) {

		Value<T> returnValue = this.valueList.remove(valueName);
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
		returnValue.setParentsKey(null);
		returnValue.setModifiedDate(this.modifiedDate);
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#setCreatedDate(java
	 * .util.Date)
	 */
	@Override
	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#setKeyName(java.lang
	 * .String)
	 */
	@Override
	public void setKeyName(String keyName) {

		this.keyName = keyName;
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.Key#setKeyType(org.
	 * jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public void setKeyType(DataType keyType) {

		this.keyType = keyType;
		this.modifiedDate = Calendar.getInstance(Locale.getDefault()).getTime();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#setModifiedDate(java
	 * .util.Date)
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Key#setParentsKey(org.
	 * jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public void setParentsKey(Key<?> parentsKey) {

		this.parentsKey = parentsKey;
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
