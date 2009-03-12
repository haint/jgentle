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
package org.jgentleframework.core.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;

/**
 * The Reflect class is an <code>abstract class</code> that represents some of
 * static methods designating to be used conjointly with
 * {@link ObjectBindingConstant} in order to perform
 * <code>annotating operations.</code>
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 14, 2008
 */
public abstract class Reflect {
	/**
	 * Returns a class identification that corresponds to configuration data of
	 * current {@link ObjectBindingConstant}.
	 * 
	 * @return {@link Identification}
	 */
	public static Identification<Class<?>> clazz() {

		return new SingleClassIdentificationImpl();
	}

	/**
	 * Returns a {@link FieldIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * Performance of this method is the same as
	 * {@link #fields(String, boolean)} with boolean value of
	 * 'foundOnSuperclass' is specified to be <b>false</b>. This denotes that
	 * the returned field list only includes field objects are declared in
	 * current derived class, exclude super classes of it.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static fields</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of return fields.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>fields("str*")</code> shall specify all of returned fields must
	 * have prefix is 'str' or
	 * <p>
	 * <code>fields("?method")</code> shall specify all of returned field
	 * objects must have name which begun with one word and <i>'method'</i>
	 * string at last.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of field or a set of
	 *            fields.
	 * @return returns an {@link Identification} object containing identified
	 *         data of fields.
	 */
	public static Identification<Field> fields(String name) {

		return fields(name, false);
	}

	/**
	 * Returns a {@link FieldIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static fields</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of return fields.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>fields("str*")</code> shall specify all of returned fields must
	 * have prefix is 'str' or
	 * <p>
	 * <code>fields("?method")</code> shall specify all of returned field
	 * objects must have name which begun with one word and <i>'method'</i>
	 * string at last.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of field or a set of
	 *            fields.
	 * @param foundOnSuperclass
	 *            if is specified to be <b>true</b>, the returned field list
	 *            will includes field objects are declared in current derived
	 *            class and also in super class of it. Otherwise, if is
	 *            specified to be <b>false</b>, the returned field list only
	 *            includes field objects are declared in current derived class,
	 *            exclude super classes of it.
	 * @return returns an {@link Identification} object containing identified
	 *         data of fields.
	 */
	public static Identification<Field> fields(String name,
			boolean foundOnSuperclass) {

		return new FieldIdentificationImpl(name, foundOnSuperclass);
	}

	/**
	 * Returns a {@link MethodIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * This method performance is the same as {@link #methods(String, boolean)}
	 * with boolean value of 'foundOnSuperclass' is specified to be <b>false</b>.
	 * This denotes that the returned fields list only includes field objects
	 * are declared in current <code>derived class</code>, exclude
	 * <code>super classes</code> of it.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static methods</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>method("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @return returns an {@link Identification} object containing identified
	 *         data of methods.
	 */
	public static Identification<Method> methods(String name) {

		return methods(name, false);
	}

	/**
	 * Returns a {@link MethodIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static methods</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>method("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @param foundOnSuperclass
	 *            if is specified to be <b>true</b>, the returned method list
	 *            includes method objects are declared in current derived class
	 *            and also in super class of it. Otherwise, if is specified to
	 *            be <b>false</b>, the returned method list only includes
	 *            method objects are declared in current derived class, exclude
	 *            super classes of it.
	 * @return returns an {@link Identification} object containing identified
	 *         data of methods.
	 */
	public static Identification<Method> methods(String name,
			boolean foundOnSuperclass) {

		return new MethodIdentificationImpl(name, foundOnSuperclass);
	}

	/**
	 * Returns a {@link MethodIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static methods</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>method("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param foundOnSuperclass
	 *            if is specified to be <b>true</b>, the returned method list
	 *            includes method objects are declared in current derived class
	 *            and also in super class of it. Otherwise, if is specified to
	 *            be <b>false</b>, the returned method list only includes
	 *            method objects are declared in current derived class, exclude
	 *            super classes of it.
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @param parameterTypes
	 *            the parameter types of desired methods.
	 * @return returns an {@link Identification} object containing identified
	 *         data of methods.
	 */
	public static Identification<Method> methods(boolean foundOnSuperclass,
			String name, Class<?>... parameterTypes) {

		return new MethodIdentificationImpl(name, parameterTypes,
				foundOnSuperclass);
	}

	/**
	 * Returns a {@link MethodIdentification} that corresponds to the object
	 * class which is specified in current {@link ObjectBindingConstant}.
	 * <p>
	 * <b>Note:</b>
	 * <p> - This method only returns <code>non-static methods</code>.
	 * <p> - <code>Regular expression</code> can be used in order to specify a
	 * set of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>method("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @param parameterTypes
	 *            the parameter types of desired methods.
	 * @return returns an {@link Identification} object containing identified
	 *         data of methods.
	 */
	public static Identification<Method> methods(String name,
			Class<?>... parameterTypes) {

		return new MethodIdentificationImpl(name, parameterTypes);
	}
}
