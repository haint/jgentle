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

import org.jgentleframework.services.objectpooling.annotation.CanBePooledMethod;

/**
 * This interface gives you a good opportunity to check the state of your
 * component. If you determine that your object is no longer in a consistent
 * state, you can return <b>false</b> from {@link #canBePooled()} method, and
 * the object is not returned to the pool.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2009
 * @see Deactivate
 * @see Validate
 * @see CanBePooledMethod
 */
public interface CanBePooled {
	/**
	 * Check the state of the component bean. If returned value is <b>false</b>,
	 * the component is not returned to the pool.
	 * <p>
	 * If this method is not implemented, <code>pooling service</code> assumes
	 * that it returns <b>true</b>.
	 */
	boolean canBePooled();
}
