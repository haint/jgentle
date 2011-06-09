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

import java.lang.reflect.Field;
import java.util.Map;

import org.jgentleframework.configure.annotation.Inject;

/**
 * Interface to be implemented by beans that wish to filter owning injected
 * properties. After bean properties injection, container will be automatically
 * invoke {@link #filters(Map)} method and pass all of injected properties to
 * the argument of method. The implementation of this method is responsible for
 * filter of all values according to fields which has been injected.
 * <p>
 * <b>Note:</b> the implementation of this interface will be only effected to
 * fields and their setters which are annotated with {@link Inject} and not
 * effected to others such as injected methods.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 24, 2008
 * @see Builder
 */
public interface Filter {
	/**
	 * Callback that supplies all the owning injected fields. Invoked after all
	 * the properties were injected.
	 * 
	 * @param map
	 *            a map containing all specified injected fields corresponding
	 *            to their previous values before injecting .
	 */
	public void filters(Map<Field, Object> map);
}
