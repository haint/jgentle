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
package org.jgentleframework.configure.objectmeta;

import java.util.Set;
import java.util.Map.Entry;

import javax.naming.ConfigurationException;

import org.jgentleframework.configure.BindingConfig;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;

/**
 * Represents an {@link ObjectConstant}. Nắm giữ thông tin thể hiện của một
 * ObjectConstant. Chịu trách nhiệm quản lý thông tin ánh xạ trực tiếp vào một
 * dependency ứng với tên name chỉ định. Đối tượng {@link ObjectConstant} chứa
 * đựng thông tin cấu hình của một thực thể tương ứng với một tên định danh
 * name. Một {@link ObjectConstant} có thể là một
 * <code>instance (object bean)</code> đã được khởi tạo, nhưng cũng có thể là
 * một mô tả tương ứng tới một {@link ObjectAttach} hoặc một
 * {@link ObjectBindingConstant}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 8, 2007
 * @see BindingConfig
 */
public interface ObjectConstant extends IPresentLoadingClass {
	/**
	 * The Class ScopeObjectConstant.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Oct 30, 2007
	 */
	class ScopeObjectConstant {
		/** The constant. */
		private ObjectConstant	constant;

		/**
		 * The Constructor.
		 * 
		 * @param constant
		 *            the constant
		 */
		public ScopeObjectConstant(ObjectConstant constant) {

			this.constant = constant;
		}

		/**
		 * Scope.
		 * 
		 * @param scope
		 *            the specified scope.
		 */
		public void scope(ScopeInstance scope) {

			this.constant.setScope(scope);
		}
	}

	/**
	 * Adds a source name to current {@link ObjectConstant}
	 * 
	 * @param name
	 * @return <b>true</b> if success, <b>false</b> otherwise
	 * @throws ConfigurationException
	 *             throws this exception if the given name is existed.
	 */
	public boolean addSourceName(String name) throws ConfigurationException;

	/**
	 * Removes all source names of current {@link ObjectConstant}
	 * <p>
	 * <b>Note:</b> all the configured binding will also be removed when this
	 * method is invoked.
	 */
	public void clearAllSourceName();

	/**
	 * Returns <b>true</b> if the given name is existed in current
	 * {@link ObjectConstant}
	 * 
	 * @param name
	 *            the name need to be checked.
	 */
	public boolean containsSourceName(String name);

	/**
	 * Gets the hash direct set.
	 * 
	 * @return Set
	 */
	public Set<Entry<String, Object>> getHashDirectSet();

	/**
	 * Returns an array containing all current name of current
	 * {@link ObjectConstant} Trả về danh sách chứa đựng thông tin các source
	 * name
	 */
	public String[] getStrNameList();

	/**
	 * Checks if source name list is empty.
	 * 
	 * @return <b>true</b>, if empty, <b>false</b> otherwise
	 */
	public boolean isEmptySourceNameList();

	/**
	 * Removes a specified given source name
	 * 
	 * @param name
	 *            the given source name
	 * @return returns <b>true</b> if success, if not, returns <b>false</b>.
	 * @throws ConfigurationException
	 *             throws this exception if the given name is not existed.
	 */
	public boolean removeSourceName(String name) throws ConfigurationException;

	/**
	 * Binds to object constant.
	 * 
	 * @param values
	 *            the values
	 */
	ScopeObjectConstant to(Object... values);

	/**
	 * Binds a specified source name to an object value
	 * 
	 * @param name
	 *            the specified name
	 * @param object
	 *            the value need to be setted.
	 */
	public void toDestinationObject(String name, Object object);

	/**
	 * Returns the scope of this {@link ObjectConstant}
	 * 
	 * @return the scope
	 * @see Scope
	 * @see ScopeImplementation
	 */
	public ScopeInstance getScope();

	/**
	 * Set the scope to this {@link ObjectConstant}
	 * 
	 * @param scope
	 *            the specified scope.
	 * @see ScopeImplementation
	 * @see Scope
	 */
	public void setScope(ScopeInstance scope);
}
