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
package org.jgentleframework.services.datalocator.enums;

import java.util.Comparator;

import org.jgentleframework.services.datalocator.data.Key;
import org.jgentleframework.services.datalocator.data.Manager;

/**
 * The Enum ComparatorKeySortedBy.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 12, 2009
 */
public enum ComparatorKeySortedBy {
	/**
	 * PATH_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key} về loại đường dẫn path của Key.
	 */
	PATH_ORDER_KEY (Manager.PATH_ORDER_KEY),
	/**
	 * REPOSITORY_TYPE_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key} về loại repository type.
	 */
	REPOSITORY_TYPE_ORDER_KEY (Manager.REPOSITORY_TYPE_ORDER_KEY),
	/**
	 * SENIORITY_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key} về ngày khởi tạo.
	 */
	SENIORITY_ORDER_KEY (Manager.SENIORITY_ORDER_KEY),
	/**
	 * NAME_ORDER_KEY là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Key} về tên của {@link Key key}.
	 */
	NAME_ORDER_KEY (Manager.NAME_ORDER_KEY);
	/** The comparator. */
	Comparator<Key<?>>	comparator;

	/**
	 * Instantiates a new comparator key sorted by.
	 * 
	 * @param comparator
	 *            the comparator
	 */
	private ComparatorKeySortedBy(Comparator<Key<?>> comparator) {

		this.comparator = comparator;
	}

	/**
	 * Returns the {@link Comparator comparator}.
	 * 
	 * @return the comparator
	 */
	public Comparator<Key<?>> getComparator() {

		return comparator;
	}
}
