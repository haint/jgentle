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
package org.jgentleframework.core.reflection;

/**
 * The Interface MemberIdentification represents all common methods of an
 * <code>Identification Member</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 21, 2008
 */
public interface MemberIdentification<T> extends Identification<T> {
	/**
	 * Set up foundOnSuperclass boolean, if setting value is <b>true</b>,
	 * returned data of {@link #getMember()} method shall be found on declaring
	 * class of current member and its super class, if <b>false</b>, the returned
	 * data of {@link #getMember()} method shall be only found on declaring
	 * class.
	 * 
	 * @param bool
	 *            the desired boolean data.
	 */
	public void setFoundOnSuperclass(boolean bool);

	/**
	 * Sets the modifiers. Only members have corresponding modifiers are
	 * returned by {@link #getMember()} method. If 'modifiers' is not specified,
	 * the default value of 'modifiers' will be
	 * {@link Identification#NO_MODIFIERS}, it means the {@link Identification}
	 * member is not specified modifiers.
	 * 
	 * @param modifiers
	 *            the modifiers to set
	 */
	public void setModifiers(int modifiers);

	/**
	 * Sets the declaring class, the class declares current member.
	 * 
	 * @param declaringClass
	 *            the declaringClass to set
	 */
	public void setDeclaringClass(Class<?> declaringClass);
}
