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
package org.jgentleframework.core.intercept.support;

/**
 * Returns <b>true</b> or <b>false</b> for a given input.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 2, 2008
 * @param <T>
 *            the type of data need to be matches
 */
public interface Matcher<T> {
	/**
	 * Returns <b>true</b> if this matches <code>obj</code>, <b>false</b>
	 * otherwise.
	 */
	public boolean matches(T obj);

	/**
	 * Returns a new matcher which returns <b>true</b> if both this and the
	 * given matcher return <b>true</b>.
	 */
	Matcher<T> and(Matcher<T> other);

	/**
	 * Returns a new matcher which returns <b>true</b> if either this or the
	 * given matcher return <b>true</b>.
	 */
	Matcher<T> or(Matcher<T> other);
}
