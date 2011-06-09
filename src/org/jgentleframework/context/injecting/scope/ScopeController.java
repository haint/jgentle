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
package org.jgentleframework.context.injecting.scope;

import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.configure.enums.Scope;

/**
 * Manages all scope instances in container.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 11, 2008
 * @see ScopeImplementation
 * @see ScopeInstance
 * @see Scope
 */
public class ScopeController {
	List<ScopeImplementation>	scopeImplementationList	= null;

	/**
	 * Constructor
	 */
	public ScopeController() {

		scopeImplementationList = new ArrayList<ScopeImplementation>();
	}

	/**
	 * Adds the given scope instance {@link ScopeImplementation} to current
	 * scope controller.
	 * 
	 * @param scopeInstance
	 *            the given scope instance.
	 * @return returns <b>true</b> if success, if not, returns <b>false</b>.
	 */
	public synchronized boolean addScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.add(scopeInstance);
	}

	/**
	 * Returns the number of scope instance which is managed.
	 */
	public synchronized int countScope() {

		return this.scopeImplementationList.size();
	}

	/**
	 * Returns <b>true</b> if the current container contains the given scope.
	 * 
	 * @param scopeInstance
	 *            the given scope instance.
	 */
	public synchronized boolean containsScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.contains(scopeInstance);
	}

	/**
	 * Removes the given scope instance
	 * 
	 * @param scopeInstance
	 *            the scope instance {@link ScopeImplementation} need to be
	 *            removed.
	 * @return returns <b>true</b> if success, if not, returns <b>false</b>.
	 */
	public synchronized boolean removeScope(ScopeImplementation scopeInstance) {

		return this.scopeImplementationList.remove(scopeInstance);
	}
}
