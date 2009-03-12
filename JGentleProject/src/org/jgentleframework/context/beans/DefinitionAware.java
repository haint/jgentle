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

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Interface to be implemented by beans that wish to be aware of their owning
 * {@link Definition}. After bean instantiation, container will be automatic
 * invoke {@link #setDefinition(Definition)} method and pass its owning
 * {@link Definition} to the argument of method.
 * <p>
 * <b>Note:</b>
 * <p>- The {@link #setDefinition(Definition)} method will be invoked before
 * the {@link Initializing#afterBeanCreated()} method is invoked.
 * <p> - For a list of all bean lifecycle methods, see the {@link Initializing}
 * and {@link Disposable}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 28, 2008
 */
public interface DefinitionAware {
	/**
	 * Callback that supplies the owning Definition to a bean instance. Invoked
	 * after the population of normal bean properties but before an
	 * initialization callback such as {@link Initializing#afterBeanCreated()}
	 * or a custom init-method.
	 * 
	 * @param def
	 *            owning {@link Definition} (never <code>null</code>).
	 */
	public void setDefinition(Definition def);
}
