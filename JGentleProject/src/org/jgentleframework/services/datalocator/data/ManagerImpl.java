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

import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.services.datalocator.enums.DataType;

/**
 * Cung cấp các phương thức để quản lý repository của hệ thống.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 12, 2007
 */
public class ManagerImpl implements Manager {
	/** The repository processor. */
	private RepositoryProcessor	repositoryProcessor;

	/**
	 * The Constructor.
	 * 
	 * @param enumConfig
	 *            đối tượng chứa thông tin cấu hình registry.
	 */
	public ManagerImpl(Class<?> enumConfig) {

		repositoryProcessor = new RepositoryProcessorImpl(enumConfig);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createKey
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public <T> Key<T> createKey(Class<T> clazz, String keyName, DataType keyType) {

		return new KeyImpl<T>(keyName, keyType, this.repositoryProcessor);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createKey
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.enums.DataType,
	 * org.jgentleframework.services.datalocator.abstracts.KeyType<?>[])
	 */
	@Override
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Key<?>[] keyList) {

		return new KeyImpl<T>(keyName, keyType, this.repositoryProcessor,
				keyList);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createKey
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.enums.DataType,
	 * org.jgentleframework.services.datalocator.abstracts.KeyType<?>[],
	 * org.jgentleframework.services.datalocator.abstracts.ValueType<T>[])
	 */
	@Override
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Key<?>[] keyList, Value<T>[] valueList) {

		return new KeyImpl<T>(keyName, keyType, keyList, valueList,
				this.repositoryProcessor);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createKey
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.enums.DataType,
	 * org.jgentleframework.services.datalocator.abstracts.ValueType<T>[])
	 */
	@Override
	public <T> Key<T> createKey(Class<T> clazz, String keyName,
			DataType keyType, Value<T>[] valueList) {

		return new KeyImpl<T>(keyName, keyType, valueList,
				this.repositoryProcessor);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createValue
	 * (java.lang.Class, java.lang.String, java.util.ArrayList,
	 * org.jgentleframework.services.datalocator.enums.DataType)
	 */
	@Override
	public <T> Value<T> createValue(Class<T> clazz, String valueName,
			List<T> valueData, DataType valueType) {

		return new ValueImpl<T>(valueName, valueData, valueType);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.datalocator.data.Manager#createValue
	 * (java.lang.Class, java.lang.String,
	 * org.jgentleframework.services.datalocator.enums.DataType, T[])
	 */
	@Override
	public <T> Value<T> createValue(Class<T> clazz, String valueName,
			DataType valueType, T... valueData) {

		ArrayList<T> listValue = new ArrayList<T>();
		for (T value : valueData) {
			listValue.add(value);
		}
		return new ValueImpl<T>(valueName, (ArrayList<T>) listValue, valueType);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.datalocator.data.Manager#getProcessor()
	 */
	@Override
	public RepositoryProcessor getProcessor() {

		return repositoryProcessor;
	}
}