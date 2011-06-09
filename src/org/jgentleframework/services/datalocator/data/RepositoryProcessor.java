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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.services.datalocator.RepositoryRuntimeException;
import org.jgentleframework.services.datalocator.annotation.Repository;
import org.jgentleframework.services.datalocator.enums.ComparatorKeySortedBy;
import org.jgentleframework.services.datalocator.enums.ComparatorValueSortedBy;
import org.jgentleframework.services.datalocator.enums.DataType;

/**
 * <code>interface</code> này mô tả các chức năng của một
 * <code>Registry RepositoryProcessor</code>, các <code>methods</code> chính
 * trong việc quản lý <code>Repository</code>, bao gồm việc khởi tạo, thêm bớt
 * từ khóa <code>key, value, backup</code> thông tin, ...
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 3, 2006
 * @see Key
 * @see Value
 */
public interface RepositoryProcessor {
	/**
	 * Creates brand new Repository.
	 * 
	 * @throws RepositoryRuntimeException
	 *             throws this exception if can not create brand new repository
	 */
	public void createBrandNew();

	/**
	 * Creates {@link Key key} instance appropriate to the given key name, key
	 * type, and parents key ... In case parents key is <code>null</code>, the
	 * creating key will be located on the Root of Repository.
	 * 
	 * @param clazz
	 *            the object class specifies the type of {@link Key key}.
	 * @param keyName
	 *            the given key name
	 * @param keyParents
	 *            the parrents key
	 * @param subKeys
	 *            an array containing all sub keys of creating {@link Key key}.
	 * @param keyType
	 *            the key type
	 * @see DataType
	 */
	public <T> void createKey(Class<T> clazz, String keyName, Key<?>[] subKeys,
			DataType keyType, Key<?> keyParents);

	/**
	 * Creates {@link Value value} instance appropriate to the given value name,
	 * value type, parents key, ... the {@link Value value} must be created in
	 * one {@link Key key} so the parents key must not be <code>null</code>.
	 * 
	 * @param valueName
	 *            the given name of {@link Value value}
	 * @param valueData
	 *            the {@link List list} containing all value data of creating
	 *            {@link Value value}.
	 * @param parentsKey
	 *            the {@link Key key} holding the creating {@link Value value}
	 * @param valueType
	 *            the value type
	 * @see DataType
	 */
	public <T> void createValue(String valueName, List<T> valueData,
			Key<T> parentsKey, DataType valueType);

	/**
	 * Creates {@link Value value} instance appropriate to the given value name,
	 * value type, parents key, ... the {@link Value value} must be created in
	 * one {@link Key key} so the parents key must not be <code>null</code>.
	 * 
	 * @param valueName
	 *            the given name of {@link Value value}
	 * @param keyParents
	 *            the {@link Key key} holding the creating {@link Value value}
	 * @param valueType
	 *            the value type
	 * @param valueData
	 *            the {@link List list} containing all value data of creating
	 *            {@link Value value}.
	 * @see DataType
	 */
	public <T> void createValue(String valueName, Key<T> keyParents,
			DataType valueType, T... valueData);

	/**
	 * Flushes the Repository. This will write any changes to current
	 * repository.
	 */
	public void flush();

	/**
	 * Returns the {@link List list} containing all sub {@link Key keys} of the
	 * given {@link Key key} and all keys of all sub keys existing in whole its
	 * hierarchical system.
	 * 
	 * @param sortedBy
	 *            the {@link Comparator} represents sorting rule corresponding
	 *            to all sub keys in returned {@link List list}. The
	 *            <code>null</code> value equals to
	 *            {@link ComparatorKeySortedBy#NAME_ORDER_KEY}
	 * @param key
	 *            the given key
	 * @return returns the {@link List list} containing all sub keys of the
	 *         given {@link Key key} if they exist, otherwise returns
	 *         <code>null</code>.
	 * @see ComparatorKeySortedBy
	 */
	public List<Key<?>> getAllSubKeys(ComparatorKeySortedBy sortedBy, Key<?> key);

	/**
	 * Returns the {@link List list} containing all {@link Value values} of the
	 * given {@link Key key} and all values of all sub keys existing in whole
	 * its hierarchical system.
	 * 
	 * @param sortedBy
	 *            the {@link Comparator} represents sorting rule corresponding
	 *            to all {@link Value values} in returned {@link List list}. The
	 *            <code>null</code> value equals to
	 *            {@link ComparatorKeySortedBy#NAME_ORDER_KEY}
	 * @param key
	 *            the given key
	 * @return returns the {@link List list} containing all {@link Value values}
	 *         of current {@link Key key} if they exist, otherwise returns null.
	 * @see ComparatorValueSortedBy
	 */
	public List<Value<?>> getAllValues(ComparatorValueSortedBy sortedBy,
			Key<?> key);

	/**
	 * Returns the sub {@link Key key} of current {@link Key key} appropriate to
	 * the given key name.
	 * 
	 * @param keyName
	 *            the given key name
	 * @param parentsKey
	 *            the given paretns {@link Key key}
	 * @return returns the {@link Key key} appropriate to the given name if it
	 *         existed, otherwise returns <code>null</code>
	 */
	public Key<?> getKeyFromName(String keyName, Key<?> parentsKey);

	/**
	 * Returns the {@link Key key} appropriate to the given key path.
	 * 
	 * @param keyPath
	 *            the given key path.
	 *            <p>
	 *            Ex: "HKEY_CURRENT_CONFIG/System_Information/RMI_Port"
	 * @return the key from path
	 */
	public Key<?> getKeyFromPath(String keyPath);

	/**
	 * Returns all key names of all sub keys existing in the given {@link Key
	 * key}.
	 * 
	 * @param key
	 *            the given {@link Key key}
	 * @return the key names
	 */
	public List<String> getKeyNames(Key<?> key);

	/**
	 * Returns the {@link Map map} containing all sub keys existing in the given
	 * {@link Key key}.
	 * 
	 * @param Key
	 *            the given {@link Key key}.
	 * @return the keys
	 */
	public Map<String, Key<?>> getKeys(Key<?> Key);

	/**
	 * Returns the {@link List list} containing all value data existing in a
	 * {@link Value value} appropriate to the given value path.
	 * 
	 * @param valuePath
	 *            the value path.
	 *            <p>
	 *            <b>ex:</b> "HKEY_CURRENT_CONFIG/System_Information/RMI_Port"
	 *            các rẽ nhánh key phân chia bằng dấu "/", nhánh cuối cùng phải
	 *            là tên Value.
	 * @return the value data from path
	 */
	public List<?> getValueDataFromPath(String valuePath);

	/**
	 * Returns a {@link Value value} appropriate to the given value name.
	 * 
	 * @param valueName
	 *            the given value name.
	 * @param parentsKey
	 *            the given parents {@link Key key}
	 * @return the value from name
	 */
	public <T> Value<T> getValueFromName(String valueName, Key<T> parentsKey);

	/**
	 * Returns a {@link Key value} appropriate to the given value path.
	 * 
	 * @param valuePath
	 *            the given value path.
	 *            <p>
	 *            <b>ex:</b> "HKEY_CURRENT_CONFIG/System_Information/RMI_Port"
	 * @return the value from path
	 */
	public Value<?> getValueFromPath(String valuePath);

	/**
	 * Returns the {@link List list} containing all {@link Value values}
	 * existing in the given {@link Key key}.
	 * 
	 * @param key
	 *            the given {@link Key key}
	 * @return the value names
	 */
	public <T> List<String> getValueNames(Key<T> key);

	/**
	 * Returns the {@link Map map} containing all {@link Value values} existing
	 * in the given {@link Key key}.
	 * 
	 * @param key
	 *            the given {@link Key key}
	 * @return returns the {@link Map map} containing all {@link Value values}
	 *         if they exist, if not returns an empty {@link Map map}.
	 */
	public <T> Map<String, Value<T>> getValues(Key<T> key);

	/**
	 * Reloads all repository information basing on the default backup file.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void loadRepository() throws IOException;

	/**
	 * Previous change.
	 */
	public void previousChange();

	/**
	 * Rmoves a {@link Key key}.
	 * 
	 * @param keyName
	 *            the given key name
	 * @param keyParents
	 *            the parents key of {@link Key key} need to be removed. In case
	 *            the parents key is <code>null</code>, container will find the
	 *            removing key in root of Repository.
	 * @return returns the removed {@link Key key}.
	 */
	public Key<?> removeKey(String keyName, Key<?> keyParents);

	/**
	 * Removes a {@link Value value}.
	 * 
	 * @param valueName
	 *            the given value name
	 * @param parentsKey
	 *            the {@link Key key} holding the {@link Value value} need to be
	 *            removed.
	 * @return returns the removed {@link Value value}.
	 */
	public <T> Value<T> removeValue(String valueName, Key<T> parentsKey);

	/**
	 * Restores the current Repository from the given backup file.
	 * 
	 * @param fileBackup
	 *            the {@link FileInputStream}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void restoreBackup(FileInputStream fileBackup) throws IOException;

	/**
	 * Creates the backup file of the current Repository.
	 * 
	 * @param fileBackup
	 *            the {@link FileOutputStream}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveBackup(FileOutputStream fileBackup) throws IOException;

	/**
	 * Back up all information of Repository to default file name which is
	 * specified in {@link Repository#saveFile()}. This method will automate
	 * invoke {@link #flush()} method before it process back-up task.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void saveRepository() throws FileNotFoundException, IOException;

	/**
	 * Gets the save file name.
	 */
	public String getSaveFile();

	/**
	 * Sets the save file.
	 * 
	 * @param saveFile
	 *            the save file name.
	 */
	public void setSaveFile(String saveFile);

	/**
	 * Gets the path type.
	 * 
	 * @return PathType
	 * @see PathType
	 */
	public PathType getPathType();

	/**
	 * Sets the path type.
	 * 
	 * @param pathType
	 *            the path type
	 * @see PathType
	 */
	public void setPathType(PathType pathType);

	/**
	 * Returns the repository.
	 * 
	 * @return the repository
	 */
	public Map<String, Key<?>> getRepository();

	/**
	 * Returns the temporary repository.
	 * 
	 * @return the temporary repository
	 */
	public Map<String, Key<?>> getTemporaryRepository();

	/**
	 * Gets the configurable enum object class.
	 * 
	 * @return the enumConfig
	 */
	public Class<?> getEnumConfig();

	/**
	 * Restores the current {@link Key key} from the given backup file.
	 * 
	 * @param fileInput
	 *            the {@link FileInputStream}
	 * @return the key<?>
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Key<?> restoreKey(FileInputStream fileInput) throws IOException;
}