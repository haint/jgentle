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
package org.jgentleframework.context.injecting.scope;

import java.util.ArrayList;

import org.jgentleframework.configure.enums.Scope;

/**
 * Chịu trách nhiệm quản lý các <code>scope implementation</code> trong
 * <code>container</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 11, 2008
 * @see ScopeImplementation
 * @see ScopeInstance
 * @see Scope
 */
public class ScopeController {
	ArrayList<ScopeImplementation>	scopeImplementationList	= null;

	/**
	 * Constructor
	 */
	public ScopeController() {

		scopeImplementationList = new ArrayList<ScopeImplementation>();
	}

	/**
	 * Add một {@link ScopeImplementation} vào trong hệ thống.
	 * 
	 * @param scopeInstance
	 *            đối tượng hiện thực cài đặt {@link ScopeImplementation}
	 * @return boolean trả về <b>true</b> nếu thực thi thành công, ngược lại
	 *         trả về <b>false</b>.
	 */
	public synchronized boolean addScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.add(scopeInstance);
	}

	/**
	 * Trả về số lượng {@link ScopeImplementation} hiện đang được quản lý.
	 * 
	 * @return int
	 */
	public synchronized int countScope() {

		return this.scopeImplementationList.size();
	}

	/**
	 * Kiểm tra một {@link ScopeImplementation} hiện có đang được chỉ định trong
	 * hệ thống hay không.
	 * 
	 * @param scopeInstance
	 *            đối tượng {@link ScopeImplementation} chỉ định cần kiểm tra.
	 * @return boolean trả về <b>true</b> nếu có, ngược lại trả về <b>false</b>.
	 */
	public synchronized boolean containsScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.contains(scopeInstance);
	}

	/**
	 * Gỡ bỏ một hiện thực cài đặt <code>scope</code> (
	 * {@link ScopeImplementation} ) ra khỏi hệ thống.
	 * 
	 * @param scopeInstance
	 *            đối tượng {@link ScopeImplementation} chỉ định cần kiểm tra.
	 * @return boolean trả về <b>true</b> nếu việc gỡ bỏ thực thi thành công,
	 *         ngược lại trả về <b>false</b>.
	 */
	public synchronized boolean removeScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.remove(scopeInstance);
	}
}
