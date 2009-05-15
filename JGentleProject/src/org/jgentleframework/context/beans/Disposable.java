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
package org.jgentleframework.context.beans;

import org.jgentleframework.context.beans.annotation.DisposableMethod;
import org.jgentleframework.context.beans.annotation.InitializingMethod;

/**
 * Interface to be implemented by beans that need to be destroyed when owning
 * container is detroyed. A {@link #destroy()} method is
 * <code>disposable method</code> that is designated to be auto invoked by
 * JGentle container when this container is destroyed.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 29, 2008
 * @see Initializing
 * @see DisposableMethod
 * @see InitializingMethod
 */
public interface Disposable {
	/**
	 * Invoked by the owning container container before it is destroyed.
	 * 
	 * @throws Exception
	 */
	public void destroy() throws Exception;
}
