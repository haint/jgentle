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
package org.aopalliance.reflect;

/**
 * This represents a method of a class.
 */
public interface Method extends Member {
	/**
	 * This locator contains all the points in the program that call this
	 * method.
	 * <p>
	 * Note that this code locator corresponds to the client-side call event
	 * (equiv. to <code>this.getLocator(USER_SIDE)</code>. To get the
	 * server-side equivalent locator, one must write
	 * <code>this.getBody().getLocator()</code> or
	 * <code>this.getLocator(PROVIDER_SIDE)</code>.
	 * <p>
	 * It is a very invasive feature since it designates all the calling points
	 * in all the classes of the application. To only designate the calling
	 * points in a given client method, one should write
	 * <code>aClientMethod.getBody().getCallLocator(this)</code>.
	 * 
	 * @see Code#getLocator()
	 * @see Code#getCallLocator(Method)
	 */
	CodeLocator getCallLocator();

	/**
	 * A full version of {@link #getCallLocator()}.
	 * 
	 * @param side
	 *            USER_SIDE || PROVIDER_SIDE
	 * @see #getCallLocator()
	 */
	CodeLocator getCallLocator(int side);

	/**
	 * Returns the body of the current method.
	 */
	Code getBody();
}
