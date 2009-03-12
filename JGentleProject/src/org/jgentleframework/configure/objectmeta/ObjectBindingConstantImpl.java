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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.Pair;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 22, 2007
 */
class ObjectBindingConstantImpl implements ObjectBindingConstant {
	/** The ID. */
	private String												ID					= "";

	/** The properties list. */
	private List<String>										propertiesList		= new LinkedList<String>();

	/** The in class. */
	private Class<?>											inClass				= null;

	/** The scope. */
	private ScopeInstance										scope				= Scope.SINGLETON;

	/** Default value is <b>true</b>. */
	private boolean												lazyInit			= true;

	/** The injected value list. */
	private Map<String, Object>									injectedValueList	= new HashMap<String, Object>();

	/** The annotated value list. */
	private Map<Types, List<Pair<Identification<?>, Object>>>	annotatedValueList	= new HashMap<Types, List<Pair<Identification<?>, Object>>>();

	/**
	 * Constructor.
	 */
	public ObjectBindingConstantImpl() {

	}

	/**
	 * Constructor.
	 * 
	 * @param pairs
	 *            the pairs
	 */
	public ObjectBindingConstantImpl(Pair<String, Object>... pairs) {

		Assertor
				.notNull(pairs,
						"[Assertion failed] - this 'values' argument must not be null !");
		init(pairs);
	}

	/**
	 * Constructor.
	 * 
	 * @param values
	 *            an array containing pairs of key and value which represent
	 *            name of properties and its value need to be injected.
	 */
	public ObjectBindingConstantImpl(Object[]... values) {

		Assertor
				.notNull(values,
						"[Assertion failed] - this 'values' argument must not be null !");
		for (Object[] valuePair : values) {
			if (valuePair.length != 2) {
				throw new InOutDependencyException(
						"Size of binding array is not valid !");
			}
			if (!ReflectUtils.isCast(String.class, valuePair[0])) {
				throw new InOutDependencyException(
						"The key of binding array ('" + valuePair[0]
								+ "') must be String type.");
			}
			Pair<String, Object> item = new Pair<String, Object>(
					(String) valuePair[0], valuePair[1]);
			init(item);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the given map containing pairs of key and value which
	 *            represent name of properties and its value need to be
	 *            injected.
	 */
	public ObjectBindingConstantImpl(Map<String, Object> map) {

		Assertor
				.notNull(
						map,
						"[Assertion failed] - the given map containing pairs of key and value must not be null !");
		for (Entry<String, Object> entry : map.entrySet()) {
			Pair<String, Object> item = new Pair<String, Object>(
					entry.getKey(), entry.getValue());
			init(item);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param properties
	 *            the properties
	 */
	public ObjectBindingConstantImpl(String... properties) {

		propertiesList.clear();
		for (String property : properties) {
			if (this.propertiesList.contains(property)) {
				throw new InOutDependencyException("Property " + property
						+ " is duplicated.");
			}
			this.propertiesList.add(property);
		}
	}

	/**
	 * Init method.
	 * 
	 * @param pairs
	 *            the pairs
	 */
	protected void init(Pair<String, Object>... pairs) {

		for (Pair<String, Object> pair : pairs) {
			init(pair);
		}
	}

	/**
	 * Init method.
	 * 
	 * @param pair
	 *            the pair
	 */
	protected void init(Pair<String, Object> pair) {

		if (pair.getKeyPair().isEmpty()) {
			throw new InOutDependencyException(
					"The value name must not be empty.");
		}
		synchronized (injectedValueList) {
			if (injectedValueList.containsKey(pair.getKey())) {
				throw new InOutDependencyException("Value '" + pair.getKey()
						+ "' is duplicated.");
			}
			this.injectedValueList.put(pair.getKeyPair(), pair.getValuePair());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #to(java.lang.Object[])
	 */
	@Override
	public InClass to(Object... values) {

		Assertor
				.notNull(values,
						"[Assertion failed] - this 'values' argument must not be null !");
		InClass result = null;
		if (this.propertiesList.size() != values.length) {
			throw new InOutDependencyException("Values size is invalid.");
		}
		for (int i = 0; i < values.length; i++) {
			this.injectedValueList.put(this.propertiesList.get(i), values[i]);
		}
		result = new InClassImpl(this);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.objectmeta.ObjectBindingConstant#
	 * getPropertiesList()
	 */
	@Override
	public List<String> getPropertiesList() {

		return propertiesList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.objectmeta.ObjectBindingConstant#
	 * setPropertiesList(java.util.List)
	 */
	@Override
	public void setPropertiesList(List<String> propertiesList) {

		this.propertiesList = propertiesList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectMeta.ObjectBindingConstant
	 * #getInClass()
	 */
	@Override
	public Class<?> getInClass() {

		return inClass;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectMeta.ObjectBindingConstant
	 * #setInClass(java.lang.Class)
	 */
	@Override
	public void setInClass(Class<?> inClass) {

		this.inClass = inClass;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectMeta.ObjectBindingConstant
	 * #getID()
	 */
	@Override
	public String getID() {

		return ID;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectMeta.ObjectBindingConstant
	 * #setID(java.lang.String)
	 */
	@Override
	public void setID(String id) {

		ID = id;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #getScope()
	 */
	@Override
	public ScopeInstance getScope() {

		return scope;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #setScope(org.exxlabs.jgentle.context.injecting.scope.ScopeInstance)
	 */
	@Override
	public void setScope(ScopeInstance scope) {

		this.scope = scope;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.objectmeta.IPresentLoadingClass#
	 * getPresentLoadingClasses()
	 */
	@Override
	public ArrayList<Class<?>> getPresentLoadingClasses() {

		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		result.add(this.inClass);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #lazy_init(boolean)
	 */
	@Override
	public ObjectBindingConstant lazyInit(boolean lazyInit) {

		setLazyInit(lazyInit);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #isLazy_init()
	 */
	@Override
	public boolean isLazyInit() {

		return lazyInit;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #setLazy_init(boolean)
	 */
	@Override
	public void setLazyInit(boolean lazyInit) {

		this.lazyInit = lazyInit;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #getInjectedValueList()
	 */
	@Override
	public Map<String, Object> getInjectedValueList() {

		return injectedValueList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.objectmeta.ObjectBindingConstant#
	 * setInjectedValueList(java.util.Map)
	 */
	@Override
	public void setInjectedValueList(Map<String, Object> injectedValueList) {

		this.injectedValueList = injectedValueList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectBindingConstant
	 * #getAnnotatedValueList()
	 */
	@Override
	public Map<Types, List<Pair<Identification<?>, Object>>> getAnnotatedValueList() {

		return annotatedValueList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.objectmeta.ObjectBindingConstant#
	 * setAnnotatedValueList(java.util.Map)
	 */
	@Override
	public void setAnnotatedValueList(
			Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList) {

		this.annotatedValueList = annotatedValueList;
	}
}
