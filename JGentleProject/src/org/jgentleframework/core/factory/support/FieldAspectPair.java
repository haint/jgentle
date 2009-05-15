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
package org.jgentleframework.core.factory.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.FieldInterceptor;

/**
 * The Class FieldAspectPair.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 15, 2008
 * @see FieldInterceptor
 */
class FieldAspectPair {
	/** The field. */
	final Field				field;
	/** The interceptors. */
	List<FieldInterceptor>	interceptors;

	/**
	 * Instantiates a new field aspect pair.
	 * 
	 * @param field
	 *            the field
	 */
	public FieldAspectPair(Field field) {

		this.field = field;
	}

	/**
	 * Instantiates a new field aspect pair.
	 * 
	 * @param field
	 *            the field
	 * @param interceptors
	 *            the interceptors
	 */
	public FieldAspectPair(Field field, List<FieldInterceptor> interceptors) {

		this.field = field;
		addAll(interceptors);
	}

	/**
	 * Adds all intercepters present at given {@link List}.
	 * 
	 * @param interceptors
	 *            the list containing the interceptors.
	 */
	public void addAll(List<FieldInterceptor> interceptors) {

		if (this.interceptors == null) {
			this.interceptors = new ArrayList<FieldInterceptor>();
		}
		this.interceptors.addAll(interceptors);
	}

	/**
	 * Adds the given interceptor
	 * 
	 * @param index
	 *            the index
	 * @param interceptor
	 *            the interceptor
	 */
	public void add(int index, FieldInterceptor interceptor) {

		if (this.interceptors == null) {
			this.interceptors = new ArrayList<FieldInterceptor>();
		}
		this.interceptors.add(index, interceptor);
	}

	/**
	 * Checks for interceptors.
	 * 
	 * @return true, if successful
	 */
	boolean hasInterceptors() {

		return interceptors != null && !interceptors.isEmpty();
	}
}
