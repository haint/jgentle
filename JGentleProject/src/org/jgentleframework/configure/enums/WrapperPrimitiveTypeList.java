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
package org.jgentleframework.configure.enums;

/**
 * The Enum WrapperPrimitiveTypeList.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 18, 2008
 */
public enum WrapperPrimitiveTypeList {
	/** The _byte. */
	_byte(byte.class, Byte.class),
	/** The _char. */
	_char(char.class, Character.class),
	/** The _double. */
	_double(double.class, Double.class),
	/** The _float. */
	_float(float.class, Float.class),
	/** The _int. */
	_int(int.class, Integer.class),
	/** The _long. */
	_long(long.class, Long.class),
	/** The _short. */
	_short(short.class, Short.class),
	/** The _boolean. */
	_boolean(boolean.class, Boolean.class);
	/** The primitive. */
	Class<?>	primitive;
	/** The wrapper. */
	Class<?>	wrapper;

	/**
	 * @return the primitive
	 */
	public Class<?> getPrimitive() {
	
		return primitive;
	}

	/**
	 * @return the wrapper
	 */
	public Class<?> getWrapper() {
	
		return wrapper;
	}

	/**
	 * @param primitive
	 *            the primitive
	 * @param wrapper
	 *            the wrapper
	 */
	WrapperPrimitiveTypeList(Class<?> primitive, Class<?> wrapper) {

		this.primitive = primitive;
		this.wrapper = wrapper;
	}
}
