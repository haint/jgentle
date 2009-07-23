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
 * Project: JGentleProject
 */
package org.jgentleframework.context.injecting;

/**
 * The Class SingletonInstanceScopeName.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 23, 2009
 */
public final class SingletonInstanceScopeName {
	/** The scope name. */
	public String	scopeName;

	/** The shared singleton. */
	public Object	sharedSingleton;

	/**
	 * Instantiates a new singleton instance scope name.
	 * 
	 * @param scopeName
	 *            the scope name
	 * @param sharedSingleton
	 *            the shared singleton
	 */
	public SingletonInstanceScopeName(String scopeName, Object sharedSingleton) {

		this.scopeName = scopeName;
		this.sharedSingleton = sharedSingleton;
	}
}
