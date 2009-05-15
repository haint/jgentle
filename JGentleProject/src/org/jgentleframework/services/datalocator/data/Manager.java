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

import java.util.Comparator;
import java.util.List;

import org.jgentleframework.services.datalocator.enums.DataType;

/**
 * The Interface Manager.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 12, 2007
 */
public interface Manager {
	/**
	 * Creates {@link Key key} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies data type of {@link Key key}.
	 * @param keyName
	 *            key name
	 * @param keyType
	 *            Key type of key which is specified by {@link DataType}
	 * @return the key< t>
	 * @see DataType
	 */
	public <T> Key<T> createKey(Class<T> clazz, String keyName, DataType keyType);

	/**
	 * Creates {@link Key key} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies data type of {@link Key key}.
	 * @param keyName
	 *            key name
	 * @param keyType
	 *            Key type of key which is specified by {@link DataType}
	 * @param keyList
	 *            the array containing child keys of current {@link Key key}
	 * @return the key< t>
	 * @see DataType
	 */
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Key<?>[] keyList);

	/**
	 * Creates {@link Key key} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies data type of {@link Key key}.
	 * @param keyName
	 *            key name
	 * @param keyType
	 *            Key type of key which is specified by {@link DataType}
	 * @param keyList
	 *            the array containing child keys of current {@link Key key}
	 * @param valueList
	 *            the array containing values of current {@link Key key}
	 * @return the key< t>
	 * @see DataType
	 */
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Key<?>[] keyList, Value<T>[] valueList);

	/**
	 * Creates {@link Key key} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies type of {@link Key key}.
	 * @param keyName
	 *            key name
	 * @param keyType
	 *            Key type of key which is specified by {@link DataType}
	 * @param valueList
	 *            the array containing values of current {@link Key key}
	 * @return the key< t>
	 * @see DataType
	 */
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Value<T>[] valueList);

	/**
	 * Creates {@link Value value} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies data type of {@link Value value}.
	 * @param valueName
	 *            value name
	 * @param valueData
	 *            the {@link List list} containing value data.
	 * @param valueType
	 *            Value type of value which is specified by {@link DataType}
	 */
	public <T> Value<T> createValue(Class<T> clazz, String valueName,
			List<T> valueData, DataType valueType);

	/**
	 * Creates {@link Value value} instance, and returns it.
	 * 
	 * @param clazz
	 *            The object class specifies data type of {@link Value value}.
	 * @param valueName
	 *            value name
	 * @param valueData
	 *            the array containing value data.
	 * @param valueType
	 *            Value type of value which is specified by {@link DataType}
	 */
	public <T> Value<T> createValue(Class<T> clazz, String valueName,
			DataType valueType, T... valueData);

	/**
	 * Returns the {@link RepositoryProcessor repository processor}.
	 */
	public RepositoryProcessor getProcessor();

	/**
	 * PATH_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key key} về loại đường dẫn path của {@link Key key}
	 */
	public static final Comparator<Key<?>>		PATH_ORDER_KEY				= new Comparator<Key<?>>() {
																				@Override
																				public int compare(
																						Key<?> o1,
																						Key<?> o2) {

																					return o1
																							.getKeyPath()
																							.compareTo(
																									o2
																											.getKeyPath());
																				}
																			};

	/**
	 * PATH_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Value value} về loại đường dẫn path của {@link Value value}
	 */
	public static final Comparator<Value<?>>	PATH_ORDER_VALUE			= new Comparator<Value<?>>() {
																				@Override
																				public int compare(
																						Value<?> o1,
																						Value<?> o2) {

																					return o1
																							.getValuePath()
																							.compareTo(
																									o2
																											.getValuePath());
																				}
																			};

	/**
	 * REPOSITORY_TYPE_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key key} về loại repository type
	 */
	public static final Comparator<Key<?>>		REPOSITORY_TYPE_ORDER_KEY	= new Comparator<Key<?>>() {
																				@Override
																				public int compare(
																						Key<?> o1,
																						Key<?> o2) {

																					return o1
																							.getKeyType()
																							.name()
																							.compareTo(
																									o2
																											.getKeyType()
																											.name());
																				}
																			};

	/**
	 * REPOSITORY_TYPE_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2
	 * trị {@link Value value} về loại repository type.
	 */
	public static final Comparator<Value<?>>	REPOSITORY_TYPE_ORDER_VALUE	= new Comparator<Value<?>>() {
																				@Override
																				public int compare(
																						Value<?> o1,
																						Value<?> o2) {

																					return o1
																							.getValueType()
																							.name()
																							.compareTo(
																									o2
																											.getValueType()
																											.name());
																				}
																			};

	/**
	 * SENIORITY_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key key} về ngày khởi tạo
	 */
	public static final Comparator<Key<?>>		SENIORITY_ORDER_KEY			= new Comparator<Key<?>>() {
																				@Override
																				public int compare(
																						Key<?> o1,
																						Key<?> o2) {

																					return o1
																							.getCreatedDate()
																							.compareTo(
																									o2
																											.getCreatedDate());
																				}
																			};

	/**
	 * NAME_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key key} về tên của {@link Key key}.
	 */
	public static final Comparator<Key<?>>		NAME_ORDER_KEY				= new Comparator<Key<?>>() {
																				@Override
																				public int compare(
																						Key<?> o1,
																						Key<?> o2) {

																					return o1
																							.getKeyName()
																							.compareTo(
																									o2
																											.getKeyName());
																				}
																			};

	/**
	 * SENIORITY_ORDER_VALUE là một Comparator dùng để so sánh 2 trị Value về
	 * ngày khởi tạo
	 */
	public static final Comparator<Value<?>>	SENIORITY_ORDER_VALUE		= new Comparator<Value<?>>() {
																				@Override
																				public int compare(
																						Value<?> o1,
																						Value<?> o2) {

																					return o1
																							.getCreatedDate()
																							.compareTo(
																									o2
																											.getCreatedDate());
																				}
																			};

	/**
	 * NAME_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Value value} về tên của {@link Value value}.
	 */
	public static final Comparator<Value<?>>	NAME_ORDER_VALUE			= new Comparator<Value<?>>() {
																				@Override
																				public int compare(
																						Value<?> o1,
																						Value<?> o2) {

																					return o1
																							.getValueName()
																							.compareTo(
																									o2
																											.getValueName());
																				}
																			};
}