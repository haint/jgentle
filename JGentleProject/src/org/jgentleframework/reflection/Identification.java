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
package org.jgentleframework.reflection;

import java.lang.reflect.Field;

/**
 * Represents an {@link Identification} instance.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 22, 2008
 */
public interface Identification<T> {
	public static final int	NO_MODIFIERS	= Identification.class.hashCode();

	/**
	 * Sets the name. You can use regular expression to specify a set of names.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>setName("str*")</code> will specify all of identification
	 * instance have "str" prefix.
	 * <p>
	 * <b>Note:</b> As concerns using {@link SingleClassIdentification} (an
	 * extension of {@link Identification}), the regular espression is not
	 * supported and the returned data array of {@link #getMember()} method
	 * always has one element.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name);

	/**
	 * Returns a member or a set of members.
	 * 
	 * @return returns an array containing {@link Field} objects if perform
	 *         success, if not, returns an empty list.
	 */
	public T[] getMember();
}
