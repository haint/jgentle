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

import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.utils.data.Pair;

/**
 * This interface represents an <code>ObjectBindingConstant</code> which is
 * responsible for mapping management and injected data controller. An
 * <code>ObjectBindingConstant</code> holds configuration data of one bean that
 * corresponds to one unique<b>ID</b>. The configuration data is held in
 * {@link ObjectBindingConstant} shall be used in order to instantiate bean. The
 * configuration includes information of {@link ObjectBindingConstant} ... and
 * all <code>specified wiring bean information</code> corresponding to
 * <code>bean</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 22, 2007
 */
public interface ObjectBindingConstant extends IPresentLoadingClass {
	/**
	 * The Class ScopeObjectBinding.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Oct 30, 2007
	 */
	class ScopeObjectBinding {
		/** The constant. */
		private ObjectBindingConstant	constant;

		/**
		 * The Constructor.
		 * 
		 * @param constant
		 *            the constant
		 */
		public ScopeObjectBinding(ObjectBindingConstant constant) {

			this.constant = constant;
		}

		/**
		 * Scope.
		 * 
		 * @param scope
		 *            the specified scope.
		 */
		public void scope(ScopeInstance scope) {

			this.constant.setScope(scope);
		}
	}

	/**
	 * Returns the current ID of {@link ObjectBindingConstant}
	 * 
	 * @return String
	 */
	public String getID();

	/**
	 * Returns the object class of specified class
	 * 
	 * @return the inClass
	 */
	public Class<?> getInClass();

	/**
	 * Returns an {@link List} of name of all specified attributes in current
	 * {@link ObjectBindingConstant}
	 * 
	 * @return the propertiesList
	 */
	public List<String> getPropertiesList();

	/**
	 * Returns the scope of this {@link ObjectBindingConstant}
	 * 
	 * @return the scope
	 * @see Scope
	 * @see ScopeImplementation
	 */
	public ScopeInstance getScope();

	/**
	 * Set the ID to {@link ObjectBindingConstant}
	 * 
	 * @param id
	 *            the desired ID
	 */
	public void setID(String id);

	/**
	 * Set the target object class.
	 * 
	 * @param clazz
	 *            the specified object class.
	 */
	public void setInClass(Class<?> clazz);

	/**
	 * Set the {@link List} of properties need to be map.
	 * 
	 * @param propertiesList
	 *            the specified {@link List} of string name need to be set.
	 */
	public void setPropertiesList(List<String> propertiesList);

	/**
	 * Set the scope to this {@link ObjectBindingConstant}
	 * 
	 * @param scope
	 *            the specified scope.
	 * @see ScopeImplementation
	 * @see Scope
	 */
	public void setScope(ScopeInstance scope);

	/**
	 * Set the list of object values.
	 * 
	 * @param values
	 * @return the in class
	 */
	public InClass to(Object... values);

	/**
	 * Returns <b>true</b> if current {@link ObjectBindingConstant} is lazy
	 * init.
	 * 
	 * @return the lazy_init
	 */
	public boolean isLazyInit();

	/**
	 * Set the <code>lazy-init</code> value to current
	 * {@link ObjectBindingConstant}. The <code>lazy-init attribute</code>
	 * indicates the bean instantiation by container. As default, the container
	 * will automatic insntantiate bean at time bean is invoked. If the
	 * <code>lazy-init</code> value is set be <b>false</b>, container
	 * implementations will eagerly pre-instantiate bean at startup.
	 * <p>
	 * <b>Note:</b> the default value of <code>lazy-init</code> is <b>true</b>
	 * and the <code>lazy-init attribute</code> is not supported to
	 * <b>prototype-scoped bean</b>.
	 * 
	 * @param lazy_init
	 *            the specified lazy-init value.
	 * @return {@link ObjectBindingConstant}
	 */
	public ObjectBindingConstant lazyInit(boolean lazy_init);

	/**
	 * Sets the lazy_init.
	 * 
	 * @param lazy_init
	 *            the lazy_init
	 */
	public void setLazyInit(boolean lazy_init);

	/**
	 * Gets the injected value list.
	 * 
	 * @return the injectedValueList
	 */
	public Map<String, Object> getInjectedValueList();

	/**
	 * Sets the injected value list.
	 * 
	 * @param injectedValueList
	 *            the injected value list
	 */
	public void setInjectedValueList(Map<String, Object> injectedValueList);

	/**
	 * Gets the annotated value list.
	 * 
	 * @return the annotatedValueList
	 */
	public Map<Types, List<Pair<Identification<?>, Object>>> getAnnotatedValueList();

	/**
	 * Sets the annotated value list.
	 * 
	 * @param annotatedValueList
	 *            the annotated value list
	 * @see Types
	 */
	public void setAnnotatedValueList(
			Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList);
}