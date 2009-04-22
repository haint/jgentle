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
package org.jgentleframework.context.beans;

import org.jgentleframework.context.beans.annotation.InitializingMethod;
import org.jgentleframework.context.injecting.Provider;

/**
 * Interface to be implemented by beans that wish to be aware of their owning
 * {@link Provider}. After bean instantiation, container will automate invoke
 * {@link #setProvider(Provider)} method and pass the current {@link Provider}
 * to the argument of method.
 * <p>
 * Generally, beans don’t know (or even need to know) their names, how they are
 * instantiated or even that they are running within a JGentle container. This
 * is usually a good thing because if a bean is aware of the container, then it
 * becomes coupled with JGentle and may not be able to exist outside of the
 * container. But sometimes, beans need to know more. Sometimes they need to
 * know the truth who they are and where they are running.
 * <p>
 * For example, beans can look up collaborating beans, or definitions via the
 * {@link Provider} (Dependency Lookup). Note that most beans will choose to
 * receive references to collaborating beans via corresponding bean properties
 * or constructor arguments (Dependency Injection).
 * <p>
 * <b>Note:</b>
 * <p>
 * - The {@link #setProvider(Provider)} method will be invoked before the
 * {@link Initializing#activate()} method is invoked.
 * <p>
 * - For a list of all bean lifecycle methods, see the {@link Initializing} and
 * {@link Disposable}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 29, 2008
 * @see Initializing
 * @see InitializingMethod
 */
public interface ProviderAware {
	/**
	 * Callback that supplies the owning factory to a bean instance. Invoked
	 * after the population of normal bean properties but before an
	 * initialization callback such as {@link Initializing#activate()} or a
	 * custom init-method.
	 * 
	 * @param provider
	 *            owning {@link Provider} (never <code>null</code>).
	 */
	public void setProvider(Provider provider);
}
