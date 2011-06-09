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

import org.jgentleframework.services.datalocator.data.Manager;
import org.jgentleframework.services.datalocator.data.Value;

/**
 * The Enum ComparatorValueSortedBy.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 12, 2009
 */
public enum ComparatorValueSortedBy {
	/**
	 * PATH_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Value value} về loại đường dẫn path của Value.
	 */
	PATH_ORDER_VALUE (Manager.PATH_ORDER_VALUE),
	/**
	 * REPOSITORY_TYPE_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2
	 * trị {@link Value value} về loại repository type.
	 */
	REPOSITORY_TYPE_ORDER_VALUE (Manager.REPOSITORY_TYPE_ORDER_VALUE),
	/**
	 * SENIORITY_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Value value} về ngày khởi tạo.
	 */
	SENIORITY_ORDER_VALUE (Manager.SENIORITY_ORDER_VALUE),
	/**
	 * NAME_ORDER_VALUE là một {@link Comparator} dùng để so sánh 2 trị
	 * {@link Value value} về tên của {@link Value value}.
	 */
	NAME_ORDER_VALUE (Manager.NAME_ORDER_VALUE);
	/** The comparator. */
	Comparator<Value<?>>	comparator;

	/**
	 * Instantiates a new comparator value sorted by.
	 * 
	 * @param comparator
	 *            the comparator
	 */
	private ComparatorValueSortedBy(Comparator<Value<?>> comparator) {

		this.comparator = comparator;
	}

	/**
	 * Returns the {@link Comparator comparator}.
	 * 
	 * @return the comparator
	 */
	public Comparator<Value<?>> getComparator() {

		return comparator;
	}
}
