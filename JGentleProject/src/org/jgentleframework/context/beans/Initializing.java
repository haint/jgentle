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
 * Interface to be implemented by beans that need to react once all their
 * properties have been set by a : for example, to perform custom
 * initialization, or merely to check that all mandatory properties have been
 * set.
 * <p>
 * An alternative to implementing Initializing is specifying a custom
 * init-method.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 29, 2008
 * @See Disposable
 * @see DisposableMethod
 * @see InitializingMethod
 */
public interface Initializing {
	/**
	 * Invoked by the container after it has set all bean properties supplied
	 * (and satisfied {@link ProviderAware} and {@link DefinitionAware}). This
	 * method allows the bean instance to perform initialization only possible
	 * when all bean properties have been set.
	 */
	public void activate();
}
