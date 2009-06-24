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
package org.jgentleframework.services.objectpooling.context;

/**
 * The implementation of {@link #deactivate()} method is invoked when the
 * application code returns an object it borrowed earlier. You can insert code
 * to free resources used by that instance.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 4, 2009
 * @see CanBePooled
 * @see Validate
 */
public interface Deactivate {
	/**
	 * Uninitializes an instance to be returned to the idle object pool.
	 * 
	 * @param obj
	 *            the instance to be passivated
	 * @throws Exception
	 *             if there is a problem deactivating <code>obj</code>, this
	 *             exception may be swallowed by the pool.
	 */
	void deactivate() throws Exception;
}
