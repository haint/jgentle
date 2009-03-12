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
package org.jgentleframework.core.reflection.metadata;

import org.aopalliance.reflect.Metadata;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 15, 2007
 */
public interface SetValueOfAnnotation {
	/**
	 * Set a new value to an attribute of specified annotation in this
	 * {@link Definition}.
	 * 
	 * @param valueName
	 *            name of specified attribute of annotation.
	 * @param value
	 *            new object value
	 * @return returns the previous value associated with attribute, or null if
	 *         there was no specified value for attribute. (A null return can
	 *         also indicate that the attribute previously associated null with
	 *         value.)
	 */
	Metadata setValueOfAnnotation(String valueName, Object value);

	/**
	 * Returns current {@link Definition} holding current annotation data.
	 */
	Definition getDefinition();
}
