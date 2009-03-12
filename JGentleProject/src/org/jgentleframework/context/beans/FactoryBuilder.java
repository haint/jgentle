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

import java.lang.reflect.Field;

import org.jgentleframework.configure.annotation.Outject;

/**
 * Interface to be implemented by beans that wish to build owning outjected
 * instance.
 * <p>
 * <b>Note:</b> the implementation of this interface will be only effected to
 * fields which is annotated with {@link Outject} and not effected to others
 * (includes all setters of fields).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 24, 2008
 * @see FactoryFilter
 */
public interface FactoryBuilder {
	/**
	 * Invoked by the container before it outject an instance of current bean
	 * property back to container. This method allows the bean instance to
	 * customize the outjected instance when the specified property need to be
	 * outjected.
	 * 
	 * @param field
	 *            the specified field is outjecting.
	 * @return returns the outjected instance according to the specified field.
	 */
	public Object getOutjectValue(Field field);
}
