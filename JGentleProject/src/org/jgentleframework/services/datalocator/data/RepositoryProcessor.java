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
package org.jgentleframework.services.datalocator.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
 * @author LE QUOC CHUNG
 * @date Aug 3, 2006
 * @see Key
 * @see Value
 */
public interface RepositoryProcessor {
	/**
	 * Creates brand new Repository.
	 * 
	 * @throws RepositoryRuntimeException
	 *             ném ra ngoại lệ nếu việc khởi tạo Registry không thành công.
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
	 * Hàm flush lưu lại tất cả thông tin thay đổi hiện tại vào trong registry.
	 * Khi thay đổi dữ liệu của registry tại thời điểm run-time, registry
	 * context sẽ không thực sự thao tác trên registry mà chỉ thay đổi thông tin
	 * dữ liệu trên một bản copy của registry. Nếu muốn các thay đổi có hiệu lực
	 * trên registry cần phải gọi method flush để thông tin thay đổi được cập
	 * nhật thật sự vào trong registry.
	 * <p>
	 * - Khuyến cáo nên gọi method flush trước khi gọi saveRegistry method.
	 */
	public void flush();

	/**
	 * Hàm getAllSubKeys dò tìm trả về danh sách các Key có trong một Key cho
	 * trước, tính từ đầu nhánh là Key cung cấp cho đến tất cả các nhánh con
	 * theo chiều dài phả hệ.
	 * <p>
	 * 
	 * @param sortedBy
	 *            là comparator chỉ định nguyên tắc việc sắp xếp các Key, nếu
	 *            sortedBy không được chỉ định (equals null) thì danh sách các
	 *            keys sẽ tự động được sắp xếp theo tên.
	 * @param keyFindFrom
	 *            Key sẽ dò tìm các Key child tính từ nó.
	 * @return trả về một danh sách {@link List} nếu có, nếu không có phần tử
	 *         nào trả về NULL.
	 */
	public List<Key<?>> getAllSubKeys(ComparatorKeySortedBy sortedBy,
			Key<?> keyFindFrom);

	/**
	 * Hàm getAllValues dò tìm trả về danh sách các Value có trong một Key cho
	 * trước, tính từ đầu nhánh là Key cung cấp cho đến tất cả các nhánh con
	 * theo chiều dài phả hệ.
	 * 
	 * @param sortedBy
	 *            là comparator chỉ định nguyên tắc việc sắp xếp các Value, nếu
	 *            sortedBy không được chỉ định (equals null) thì danh sách các
	 *            values sẽ tự động được sắp xếp theo tên.
	 * @param keyFindFrom
	 *            Key sẽ dò tìm các value child tính từ nó.
	 * @return trả về một danh sách {@link List} nếu có, nếu không có phần tử
	 *         nào trả về NULL.
	 */
	public List<Value<?>> getAllValues(ComparatorValueSortedBy sortedBy,
			Key<?> keyFindFrom);

	/**
	 * Hàm getKeyFromName trả về một Key dựa trên keyName và Key chứa nó, nếu
	 * keyParents bằng NULL có nghĩa Key cần lấy nằm tại vị trí Root của
	 * repository.
	 * 
	 * @param keyName
	 *            tên của Key cần lấy
	 * @param keyParents
	 *            Key chứa Key cần lấy
	 * @return trả về một Key
	 */
	public Key<?> getKeyFromName(String keyName, Key<?> keyParents);

	/**
	 * Hàm getKeyFromPath trả về một Key dựa trên đường dẫn path cung cấp tính
	 * từ vị trí Root đến vị trí Key cần lấy.
	 * 
	 * @param keyPath
	 *            đường dẫn chỉ định vị trí Key.
	 *            <p>
	 *            Ex: "HKEY_CURRENT_CONFIG/System_Information/RMI_Port" các rẽ
	 *            nhánh key phân chia bằng dấu "/", nhánh cuối cùng phải là tên
	 *            Key chỉ định cần truy xuất.
	 * @return trả về key chỉ định muốn truy xuất.
	 */
	public Key<?> getKeyFromPath(String keyPath);

	/**
	 * Hàm getKeyNames trả về danh sách các tên keys có trong một Key cho trước
	 * 
	 * @param fromKey
	 *            Key chứa danh sách tên các keys cần lấy
	 * @return trả về danh sách các tên keys có trong key chỉ định.
	 */
	public List<String> getKeyNames(Key<?> fromKey);

	/**
	 * Hàm getKeys trả về một HashMap các Keys có trong một Key đã cho
	 * 
	 * @param fromKey
	 *            đối tượng Key chứa các keys cần lấy
	 * @return trả về đối tượng chứa danh sách các keys nếu có, nếu không sẽ trả
	 *         về một HashMap rỗng.
	 */
	public Map<String, Key<?>> getKeys(Key<?> fromKey);

	/**
	 * Hàm getValueData trả về một danh sách các giá trị của một value cụ thể
	 * dựa trên đường dẫn path tính từ Root đến vị trí value chỉ định.
	 * 
	 * @param valuePath
	 *            đường dẫn cụ thể đến ValueType.VD: <br>
	 * <br>
	 *            "HKEY_CURRENT_CONFIG/System_Information/RMI_Port" các rẽ nhánh
	 *            key phân chia bằng dấu "/", nhánh cuối cùng phải là tên Value.
	 * @return trả về danh sách các giá trị của một value
	 */
	public List<?> getValueDataFromPath(String valuePath);

	/**
	 * Hàm getValueFromName trả về một Value dựa trên valueName và keyParents
	 * 
	 * @param valueName
	 *            tên value cần lấy
	 * @param keyParents
	 *            Key chứa value cần lấy
	 * @return trả về một Value
	 */
	public <T> Value<T> getValueFromName(String valueName, Key<T> keyParents);

	/**
	 * Hàm getValueData trả về một Value cụ thể dựa trên đường dẫn path tính từ
	 * Root đến vị trí của Value chỉ định.
	 * 
	 * @param valuePath
	 *            đường dẫn cụ thể đến Value.VD: <br>
	 * <br>
	 *            "HKEY_CURRENT_CONFIG/System_Information/RMI_Port" các rẽ nhánh
	 *            key phân chia bằng dấu "/", nhánh cuối cùng phải là tên Value.
	 * @return trả về value chỉ định trong đường dẫn valuePath.
	 */
	public Value<?> getValueFromPath(String valuePath);

	/**
	 * Hàm getValueNames trả về 1 danh sách các tên values có trong một Key cho
	 * trước
	 * 
	 * @param fromKey
	 *            đối tượng Key chứa các tên values cần lấy
	 * @return {@code ArrayList<String>}
	 */
	public <T> List<String> getValueNames(Key<T> fromKey);

	/**
	 * Hàm getValues trả về một HashMap chứa các values có trong một Key đã cho
	 * 
	 * @param fromKey
	 *            đối tượng Key chứa các values cần lấy
	 * @return trả về đối tượng chứa danh sách các values nếu có, nếu không sẽ
	 *         trả về một HashMap rỗng.
	 */
	public <T> Map<String, Value<T>> getValues(Key<T> fromKey);

	/**
	 * Hàm loadRepository nạp thông tin dữ liệu của repository được cất giữ
	 * trong file lưu trữ mặc định.
	 * 
	 * @throws IOException
	 */
	public void loadRepository() throws IOException;

	/**
	 * Hàm previousChange trả về registry ở dạng lần thay đổi gần nhất (lần gọi
	 * hàm flush gần nhất).
	 */
	public void previousChange();

	/**
	 * Rmoves a {@link Key key}
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
	 */
	public void restoreBackup(FileInputStream fileBackup) throws IOException;

	/**
	 * Creates the backup file of the current Repository
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
	 */
	public void saveRepository() throws FileNotFoundException, IOException;

	/**
	 * Gets the file path.
	 * 
	 * @return the filePath
	 */
	public String getFilePath();

	/**
	 * Sets the file path.
	 * 
	 * @param filePath
	 *            the file path
	 */
	public void setFilePath(String filePath);

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
	 */
	public Map<String, Key<?>> getRepository();

	/**
	 * Returns the temporary repository.
	 */
	public Map<String, Key<?>> getTemporaryRepository();

	/**
	 * Gets the configurable enum object class.
	 * 
	 * @return the enumConfig
	 */
	public Class<?> getEnumConfig();

	/**
	 * Restores the current key from the given backup file.
	 * 
	 * @param fileInput
	 *            the {@link FileInputStream}
	 * @return the key<?>
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Key<?> restoreKey(FileInputStream fileInput) throws IOException;
}