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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.annotation.Repository;
import org.jgentleframework.services.datalocator.enums.ComparatorKeySortedBy;
import org.jgentleframework.services.datalocator.enums.ComparatorValueSortedBy;
import org.jgentleframework.services.datalocator.enums.DataType;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ObjectUtils;

/**
 * The Class RepositoryProcessorImpl.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 12, 2007
 */
class RepositoryProcessorImpl implements RepositoryProcessor {
	/** Object class chỉ định cấu hình cấu trúc của repository. */
	private Class<?>			enumConfig	= null;

	/** Đường dẫn file path chỉ định được dùng để cất trữ thông tin repository. */
	private String				filePath	= "";

	/** Chỉ định kiểu đường path prefix của filePath. */
	private PathType			pathType	= null;

	/** Đối tượng lưu trữ thông tin dữ liệu trong repository. */
	private Map<String, Key<?>>	repository;

	/**
	 * Đối tượng lưu trữ thông tin dữ liệu trong temporary repository hiện hành.
	 */
	private Map<String, Key<?>>	temporaryRepository;

	/**
	 * The Constructor.
	 * 
	 * @param config
	 *            the config
	 */
	public RepositoryProcessorImpl(Class<?> config) {

		repository = new HashMap<String, Key<?>>();
		temporaryRepository = new HashMap<String, Key<?>>();
		/**
		 * Kiểm tra thông tin config trên enum.
		 */
		if (!config.isEnum() || !config.isAnnotationPresent(Repository.class)) {
			throw new RepositoryRuntimeException(
					"Config Class Object is not a enum or invalid !");
		}
		else {
			this.enumConfig = config;
			this.filePath = enumConfig.getAnnotation(Repository.class)
					.saveFile();
			this.pathType = enumConfig.getAnnotation(Repository.class)
					.pathType();
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * createBrandNew()
	 */
	@Override
	public synchronized void createBrandNew() {

		temporaryRepository.clear();
		repository.clear();
		for (EKeySystemType rootKey : EKeySystemType.values()) {
			createKey(String.class, rootKey.name(), rootKey.getSubKeys(),
					DataType.SYSTEM, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#createKey
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.data.Key<?>[],
	 * org.jgentleframework.services.datalocator.enums.DataType,
	 * org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public synchronized <T> void createKey(Class<T> clazz, String keyName,
			Key<?>[] childKeys, DataType keyType, Key<?> keyParents) {

		Assertor.notNull(keyName, "keyName must not be null.");
		if (keyParents == null) {
			if (keyType.equals(DataType.USER)) {
				throw new RepositoryRuntimeException("Can not create key "
						+ keyName + "in ROOT.");
			}
			if (childKeys == null) {
				this.temporaryRepository.put(keyName, new KeyImpl<T>(keyName,
						keyType, this));
			}
			else {
				this.temporaryRepository.put(keyName, new KeyImpl<T>(keyName,
						keyType, this, childKeys));
			}
		}
		else {
			if (keyParents.getKeyType().compareTo(DataType.REMOTE) == 0
					&& keyType.compareTo(DataType.REMOTE) != 0) {
				throw new RepositoryRuntimeException("keyType is invalid.");
			}
			if (keyParents.getKeyList().containsKey(keyName)) {
				throw new RepositoryRuntimeException("Key name " + keyName
						+ " is existed.");
			}
			if (childKeys == null) {
				keyParents.getKeyList().put(keyName,
						new KeyImpl<T>(keyName, keyType, this));
			}
			else {
				keyParents.getKeyList().put(keyName,
						new KeyImpl<T>(keyName, keyType, this, childKeys));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * createValue(java.lang.String, java.util.List,
	 * org.jgentleframework.services.datalocator.data.Key,
	 * org.jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public synchronized <T> void createValue(String valueName,
			List<T> valueData, Key<T> keyParents, DataType valueType) {

		if (valueName == null || keyParents == null) {
			throw new RepositoryRuntimeException(
					"valueName and keyParents must not be null.");
		}
		if (keyParents.getKeyType().equals(DataType.REMOTE)
				&& !valueType.equals(DataType.REMOTE)) {
			throw new RepositoryRuntimeException("valueType is invalid.");
		}
		if (keyParents.getValueList().containsKey(valueName)) {
			throw new RepositoryRuntimeException("Value Name " + valueName
					+ " is existed.");
		}
		keyParents.getValueList().put(valueName,
				new ValueImpl<T>(valueName, valueData, valueType));
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * createValue(java.lang.String,
	 * org.jgentleframework.services.datalocator.data.Key,
	 * org.jgentleframework.services.datalocator.enums.DataType, T[])
	 */
	@Override
	public <T> void createValue(String valueName, Key<T> keyParents,
			DataType valueType, T... valueData) {

		ArrayList<T> valueList = new ArrayList<T>();
		for (T value : valueData) {
			valueList.add(value);
		}
		createValue(valueName, valueList, keyParents, valueType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#flush
	 * ()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized void flush() {

		this.repository.clear();
		this.repository = (HashMap<String, Key<?>>) ObjectUtils
				.deepCopy(this.temporaryRepository);
	}

	@Override
	public List<Key<?>> getAllSubKeys(ComparatorKeySortedBy sortedBy,
			Key<?> keyFindFrom) {

		Assertor.notNull(keyFindFrom, "keyFindFrom must not be NULL !");
		ArrayList<Key<?>> listKeyIN = new ArrayList<Key<?>>();
		ArrayList<Key<?>> keyTemp = new ArrayList<Key<?>>();
		boolean flag = false;
		for (Key<?> obj : keyFindFrom.getKeyList().values()) {
			if (obj.getKeyList().isEmpty() == false) {
				keyTemp.add(obj);
			}
			listKeyIN.add(obj);
		}
		if (keyTemp.size() != 0) {
			flag = true;
		}
		else {
			flag = false;
		}
		while (flag) {
			for (Object obj : keyTemp.toArray()) {
				for (Key<?> subObj : ((Key<?>) obj).getKeyList().values()) {
					if (subObj.getKeyList().isEmpty() == false) {
						keyTemp.add(subObj);
					}
					listKeyIN.add(subObj);
				}
				listKeyIN.add((Key<?>) obj);
				keyTemp.remove(obj);
			}
			if (keyTemp.size() != 0) {
				flag = true;
			}
			else {
				flag = false;
			}
		}
		if (listKeyIN.size() == 0) {
			return null;
		}
		// Thực thi việc sort theo tên key trước
		Collections.sort(listKeyIN, ComparatorKeySortedBy.NAME_ORDER_KEY
				.getComparator());
		// Thực thi việc sort theo từ khoá chỉ định.
		if (sortedBy != null)
			Collections.sort(listKeyIN, sortedBy.getComparator());
		return listKeyIN;
	}

	@Override
	public List<Value<?>> getAllValues(ComparatorValueSortedBy sortedBy,
			Key<?> keyFindFrom) {

		if (keyFindFrom == null || sortedBy == null) {
			throw new RuntimeException("KeyType must not be NULL !");
		}
		ArrayList<Value<?>> listValueIN = new ArrayList<Value<?>>();
		ArrayList<Key<?>> keyTemp = new ArrayList<Key<?>>();
		boolean flag = false;
		if (keyFindFrom.getValueList().size() > 0)
			listValueIN.addAll(keyFindFrom.getValueList().values());
		for (Key<?> obj : keyFindFrom.getKeyList().values()) {
			if (!obj.getKeyList().isEmpty()) {
				keyTemp.addAll(obj.getKeyList().values());
			}
			if (obj.getValueList().size() > 0)
				listValueIN.addAll(obj.getValueList().values());
		}
		if (keyTemp.size() != 0) {
			flag = true;
		}
		else {
			flag = false;
		}
		while (flag) {
			for (Key<?> obj : keyTemp) {
				if (((Key<?>) obj).getValueList().size() > 0)
					listValueIN.addAll(obj.getValueList().values());
				for (Key<?> subObj : obj.getKeyList().values()) {
					if (subObj.getKeyList().isEmpty() == false) {
						keyTemp.addAll(subObj.getKeyList().values());
					}
					else {
						if (subObj.getValueList().size() > 0)
							listValueIN.addAll(subObj.getValueList().values());
					}
				}
				keyTemp.remove(obj);
			}
			if (keyTemp.size() != 0) {
				flag = true;
			}
			else {
				flag = false;
			}
		}
		if (listValueIN.size() == 0) {
			return null;
		}
		// Thực thi việc sort theo tên value trước
		Collections.sort(listValueIN, ComparatorValueSortedBy.NAME_ORDER_VALUE
				.getComparator());
		// Thực thi việc sort theo từ khoá chỉ định.
		if (sortedBy != null)
			Collections.sort(listValueIN, sortedBy.getComparator());
		return listValueIN;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getEnumConfig()
	 */
	@Override
	public Class<?> getEnumConfig() {

		return enumConfig;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getFilePath()
	 */
	@Override
	public synchronized String getFilePath() {

		return this.filePath;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getKeyFromName(java.lang.String,
	 * org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public Key<?> getKeyFromName(String keyName, Key<?> keyParents) {

		Assertor.notNull(keyName, "keyName must not be null");
		if (keyParents == null) {
			return this.temporaryRepository.get(keyName);
		}
		else {
			return keyParents.getKeyFromName(keyName);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getKeyFromPath(java.lang.String)
	 */
	@Override
	public Key<?> getKeyFromPath(String keyPath) {

		String[] pathSplit = keyPath.split("/");
		if (pathSplit.length < 1) {
			throw new RepositoryRuntimeException("Path string is invalid.");
		}
		ArrayList<Key<?>> keysList = new ArrayList<Key<?>>();
		for (int i = 0; i < pathSplit.length; i++) {
			Key<?> keyCurrent = null;
			if (i == 0) {
				if (!this.temporaryRepository.containsKey(pathSplit[i])) {
					throw new RepositoryRuntimeException("Key " + pathSplit[i]
							+ " is not existed in Root.");
				}
				keyCurrent = this.getKeyFromName(pathSplit[i], null);
			}
			else {
				if (!keysList.get(i - 1).getKeyList().containsKey(pathSplit[i])) {
					throw new RepositoryRuntimeException("Key " + pathSplit[i]
							+ " is not existed in Key " + pathSplit[i - 1]);
				}
				keyCurrent = this.getKeyFromName(pathSplit[i], keysList
						.get(i - 1));
			}
			keysList.add(keyCurrent);
		}
		return keysList.get(keysList.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getKeyNames(org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public ArrayList<String> getKeyNames(Key<?> fromKey) {

		Assertor.notNull(fromKey, "fromKey must not be null.");
		ArrayList<String> results = new ArrayList<String>();
		for (Key<?> temp : fromKey.getKeyList().values()) {
			results.add(temp.getKeyName());
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#getKeys
	 * (org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public Map<String, Key<?>> getKeys(Key<?> fromKey) {

		Assertor.notNull(fromKey, "fromKey must not be null !");
		return fromKey.getKeyList();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getPathType()
	 */
	@Override
	public synchronized PathType getPathType() {

		return this.pathType;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getRepository()
	 */
	@Override
	public synchronized Map<String, Key<?>> getRepository() {

		return repository;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getTemporaryRepository()
	 */
	@Override
	public synchronized Map<String, Key<?>> getTemporaryRepository() {

		return temporaryRepository;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getValueDataFromPath(java.lang.String)
	 */
	@Override
	public List<?> getValueDataFromPath(String valuePath) {

		String[] pathSplit = valuePath.split("/");
		if (pathSplit.length <= 1) {
			throw new RepositoryRuntimeException("Path string is invalid.");
		}
		Value<?> valueCurrent = this.getValueFromPath(valuePath);
		return valueCurrent.getValueData();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getValueFromName(java.lang.String,
	 * org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public <T> Value<T> getValueFromName(String valueName, Key<T> keyParents) {

		Assertor.notNull(valueName, "valueName must not be null.");
		Assertor.notNull(keyParents, "keyParents must not be null.");
		return keyParents.getValueFromName(valueName);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getValueFromPath(java.lang.String)
	 */
	@Override
	public Value<?> getValueFromPath(String valuePath) {

		String[] pathSplit = valuePath.split("/");
		if (pathSplit.length <= 1) {
			throw new RepositoryRuntimeException("Path string is invalid.");
		}
		String keyPath = valuePath.substring(0, valuePath.lastIndexOf("/"));
		Key<?> current = getKeyFromPath(keyPath);
		if (!current.getValueList()
				.containsKey(pathSplit[pathSplit.length - 1])) {
			throw new RepositoryRuntimeException("Value "
					+ pathSplit[pathSplit.length - 1]
					+ " is not existed in key "
					+ pathSplit[pathSplit.length - 2]);
		}
		return current.getValueFromName(pathSplit[pathSplit.length - 1]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * getValueNames(org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public <T> List<String> getValueNames(Key<T> fromKey) {

		Assertor.notNull(fromKey, "fromKey must not be null.");
		ArrayList<String> results = new ArrayList<String>();
		for (Value<T> temp : fromKey.getValueList().values()) {
			results.add(temp.getValueName());
		}
		return results;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#getValues
	 * (org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public <T> Map<String, Value<T>> getValues(Key<T> fromKey) {

		Assertor.notNull(fromKey, "fromKey must not be null.");
		return fromKey.getValueList();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * loadRepository()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized void loadRepository() throws IOException {

		ObjectInputStream in = null;
		if (pathType.equals(PathType.CLASSPATH)) {
			in = new ObjectInputStream(ClassLoader
					.getSystemResourceAsStream(this.filePath));
		}
		else {
			File file = new File(System.getProperty(pathType.getType()),
					filePath);
			FileInputStream fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
		}
		try {
			this.repository = (HashMap<String, Key<?>>) in.readObject();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
		/*
		 * Nạp lại thông tin backup vào registryCurrent
		 */
		this.temporaryRepository.clear();
		this.temporaryRepository = (HashMap<String, Key<?>>) ObjectUtils
				.deepCopy(this.repository);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * previousChange()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void previousChange() {

		this.temporaryRepository.clear();
		this.temporaryRepository = (HashMap<String, Key<?>>) ObjectUtils
				.deepCopy(this.repository);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#removeKey
	 * (java.lang.String, org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public synchronized Key<?> removeKey(String keyName, Key<?> keyParents) {

		Assertor.notNull(keyName, "keyName must not be null.");
		if (keyParents == null) {
			if (!this.temporaryRepository.containsKey(keyName)) {
				throw new RepositoryRuntimeException(
						"keyName is null or keyName is not existed.");
			}
			if (((Key<?>) this.temporaryRepository.get(keyName)).getKeyType()
					.equals(DataType.SYSTEM)) {
				throw new RepositoryRuntimeException(
						"Could not remove SYSTEM key in Root.");
			}
			return this.temporaryRepository.remove(keyName);
		}
		else {
			if (!keyParents.getKeyList().containsKey(keyName)) {
				throw new RepositoryRuntimeException("Key name " + keyName
						+ " is not existed in key " + keyParents.getKeyPath());
			}
			if (keyParents.getKeyList().get(keyName).getKeyType().equals(
					DataType.SYSTEM)) {
				throw new RepositoryRuntimeException(
						"Could not remove SYSTEM key.");
			}
			return keyParents.getKeyList().remove(keyName);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * removeValue(java.lang.String,
	 * org.jgentleframework.services.datalocator.data.Key)
	 */
	@Override
	public synchronized <T> Value<T> removeValue(String valueName,
			Key<T> keyParents) {

		Assertor.notNull(valueName, "valueName must not be null.");
		Assertor.notNull(keyParents, "keyParents must not be null.");
		if (!keyParents.getValueList().containsKey(valueName)) {
			throw new RepositoryRuntimeException("Value " + valueName
					+ " is not existed in key " + keyParents.getKeyPath());
		}
		if (keyParents.getValueList().get(valueName).getValueType().equals(
				DataType.SYSTEM)) {
			throw new RepositoryRuntimeException(
					"Could not remove SYSTEM value.");
		}
		return keyParents.getValueList().remove(valueName);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * restoreBackup(java.io.FileInputStream)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public synchronized void restoreBackup(FileInputStream fileBackup)
			throws IOException {

		ObjectInputStream in = null;
		in = new ObjectInputStream(fileBackup);
		try {
			this.repository = (HashMap<String, Key<?>>) in.readObject();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
		/*
		 * Nạp lại thông tin backup vào registryCurrent
		 */
		this.temporaryRepository.clear();
		this.temporaryRepository = (HashMap<String, Key<?>>) ObjectUtils
				.deepCopy(this.repository);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#saveBackup
	 * (java.io.FileOutputStream)
	 */
	@Override
	public synchronized void saveBackup(FileOutputStream fileBackup)
			throws IOException {

		ObjectOutputStream out = null;
		out = new ObjectOutputStream(fileBackup);
		out.writeObject(this.repository);
		out.flush();
		out.close();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * saveRepository()
	 */
	@Override
	public synchronized void saveRepository() throws FileNotFoundException,
			IOException {

		this.flush();
		ObjectOutputStream out = null;
		FileOutputStream fis = null;
		File file = null;
		if (pathType.equals(PathType.CLASSPATH)) {
			throw new RepositoryRuntimeException(
					"Could not save registry if path type equals ClassPath.");
		}
		else {
			file = new File(System.getProperty(pathType.getType()), filePath);
			fis = new FileOutputStream(file, true);
			out = new ObjectOutputStream(fis);
		}
		boolean resultCreate = false;
		if (!file.exists()) {
			resultCreate = file.createNewFile();
		}
		if (resultCreate == false)
			throw new RepositoryRuntimeException("Can not create file "
					+ file.toString());
		out.writeObject(this.repository);
		out.flush();
		out.close();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * setFilePath(java.lang.String)
	 */
	@Override
	public synchronized void setFilePath(String filePath) {

		this.filePath = filePath;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.RepositoryProcessor#restoreKey
	 * (java.io.FileInputStream)
	 */
	@Override
	public synchronized Key<?> restoreKey(FileInputStream fileInput)
			throws IOException {

		ObjectInputStream in = null;
		in = new ObjectInputStream(fileInput);
		Key<?> keyRestore = null;
		try {
			keyRestore = (Key<?>) in.readObject();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
		return keyRestore;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.datalocator.data.RepositoryProcessor#
	 * setPathType(org.jgentleframework.configure.enums.PathType)
	 */
	@Override
	public synchronized void setPathType(PathType pathType) {

		this.pathType = pathType;
	}
}
