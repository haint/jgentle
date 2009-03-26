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
package org.jgentleframework.configure.objectmeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.BindingConfigImpl;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.AbstractInitLoading;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.data.Pair;

/**
 * This class manages all necessary information including the meta objects (
 * {@link ObjectBindingConstant}, {@link ObjectAttach}, {@link ObjectConstant},
 * {@link ObjectBindingInterceptor}, ...) in order to instantiate bean.
 * <p>
 * On the other hand, it looks like similar to abstract configurable class
 * (extension of {@link Configurable} interface or {@link AbstractConfig}) class
 * which provide a few methods in order to configure bean instance, however
 * there is a difference that the configurable class is only runable at startup
 * time (configuration time) while {@link Binder} is only used at run-time.
 * <p>
 * In addition, it provides some static method in order to create object metas (
 * {@link ObjectAttach}, {@link ObjectConstant}, {@link ObjectBindingConstant},
 * {@link ObjectBindingConstant}, ...)
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 9, 2007
 * @see ObjectAttach
 * @see ObjectConstant
 * @see ObjectBindingConstant
 */
public class Binder extends BindingConfigImpl {
	/**
	 * Creates an {@link ObjectAttach} instance.
	 * 
	 * @param classes
	 *            a type or a set of types need to be created attach mapping.
	 * @return the object attach<?>
	 */
	@SuppressWarnings("unchecked")
	public static ObjectAttach<?> createObjectAttach(Class<?>... classes) {

		return new ObjectAttachImpl(classes);
	}

	/**
	 * Creates an {@link ObjectAttach} instance.
	 * 
	 * @param clazz
	 *            a given type
	 * @return the object attach< t>
	 */
	public static <T> ObjectAttach<T> createObjectAttach(Class<T> clazz) {

		return new ObjectAttachImpl<T>(clazz);
	}

	/**
	 * Creates an {@link ObjectBindingConstant} instance.
	 * 
	 * @return the object binding constant
	 */
	public static ObjectBindingConstant createObjectBindingConstant() {

		return new ObjectBindingConstantImpl();
	}

	/**
	 * Creates an {@link ObjectBindingConstant} instance.
	 * 
	 * @param map
	 *            the given map containing pairs of key and value which
	 *            represent name of properties and its value need to be
	 *            injected.
	 * @return the object binding constant
	 */
	public static ObjectBindingConstant createObjectBindingConstant(
			Map<String, Object> map) {

		return new ObjectBindingConstantImpl(map);
	}

	/**
	 * Creates an {@link ObjectBindingConstant} instance.
	 * 
	 * @param values
	 *            an array containing pairs of key and value which represent
	 *            name of properties and its value need to be injected.
	 * @return the object binding constant
	 */
	public static ObjectBindingConstant createObjectBindingConstant(
			Object[]... values) {

		return new ObjectBindingConstantImpl(values);
	}

	/**
	 * Creates an {@link ObjectBindingConstant} instance.
	 * 
	 * @param pairs
	 *            an array containing pairs of key and value which represent
	 *            name of properties and its value need to be injected.
	 * @return the object binding constant
	 */
	public static ObjectBindingConstant createObjectBindingConstant(
			Pair<String, Object>... pairs) {

		return new ObjectBindingConstantImpl(pairs);
	}

	/**
	 * Creates an {@link ObjectBindingConstant} instance.
	 * 
	 * @param properties
	 *            name of properties need to be injected
	 * @return the object binding constant
	 */
	public static ObjectBindingConstant createObjectBindingConstant(
			String... properties) {

		return new ObjectBindingConstantImpl(properties);
	}

	/**
	 * Creates an {@link ObjectBindingInterceptor} instance.
	 * 
	 * @param interceptor
	 *            the interceptor
	 * @param matchers
	 *            a matcher or a list of matchers of given interceptor
	 * @return the object binding interceptor
	 */
	public static ObjectBindingInterceptor createObjectBindingInterceptor(
			Object interceptor, Matcher<Definition>... matchers) {

		return new ObjectBindingInterceptorImpl(interceptor, matchers);
	}

	/**
	 * Creates an {@link ObjectConstant} instance.
	 * 
	 * @param names
	 *            the name of constants.
	 * @return the object constant
	 */
	public static ObjectConstant createObjectConstant(String... names) {

		return new ObjectConstantImpl(names);
	}

	/** The provider. */
	private Provider	provider;

	/**
	 * The Constructor.
	 * 
	 * @param provider
	 *            the provider
	 */
	public Binder(Provider provider) {

		Assertor.notNull(provider, "The given provider must not be null !");
		this.provider = provider;
		setOptionsList(new HashMap<String, Object>(), null);
	}

	/**
	 * Flush all configured binding data.
	 */
	public void flush() {

		ArrayList<Map<String, Object>> OLArray = new ArrayList<Map<String, Object>>();
		OLArray.add(this.getOptionsList());
		AbstractInitLoading.loading(this.provider, OLArray);
		clearAllBinding();
	}
}
