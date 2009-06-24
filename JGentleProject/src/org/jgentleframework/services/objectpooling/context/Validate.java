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
 * The implementation of {@link #validate()} method is a useful method to ensure
 * that the pool always returns instances that are in a valid state.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2009
 * @see CanBePooled
 * @see Deactivate
 */
public interface Validate {
	/**
	 * Ensures that the instance is safe to be returned by the pool. Returns
	 * <code>false</code> if <code>instance</code> should be destroyed.
	 * 
	 * @return <code>false</code> if <code>the instance</code> is not valid and
	 *         should be dropped from the pool, <code>true</code> otherwise.
	 */
	boolean validate();
}
