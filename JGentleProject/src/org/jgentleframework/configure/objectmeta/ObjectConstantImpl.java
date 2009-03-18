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
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.ConfigurationException;

import org.jgentleframework.configure.BindingException;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class represents an {@link ObjectConstant} specifying dependency mapping
 * to the given unique names.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 9, 2007
 * @see ObjectConstant
 */
public class ObjectConstantImpl implements ObjectConstant {
	/** The hash direct list. */
	private HashMap<String, Object>	hashDirectList	= new HashMap<String, Object>();

	/** The str name list. */
	private ArrayList<String>		strNameList		= new ArrayList<String>();

	/** The scope instance. */
	private ScopeInstance			scope			= Scope.SINGLETON;

	/**
	 * Constructor.
	 * 
	 * @param names
	 *            the array containing name need to be specified.
	 */
	protected ObjectConstantImpl(String... names) {

		for (String name : names) {
			init(name);
		}
	}

	/**
	 * Instantiates a new object constant impl.
	 * 
	 * @param name
	 *            the name
	 */
	protected ObjectConstantImpl(String name) {

		init(name);
	}

	/**
	 * Inits {@link ObjectConstant} instance.
	 * 
	 * @param name
	 *            the name
	 */
	protected void init(String name) {

		if (name.isEmpty())
			throw new BindingException("Binding name list must not be empty !");
		if (this.strNameList.contains(name)) {
			throw new BindingException("This name '" + name + "' is existed.");
		}
		else {
			this.strNameList.add(name);
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * addSourceName(java.lang.String)
	 */
	@Override
	public synchronized boolean addSourceName(String name)
			throws ConfigurationException {

		if (this.strNameList.contains(name)) {
			throw new BindingException("This name '" + name + "' is existed !");
		}
		return this.strNameList.add(name);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * clearAllSourceName()
	 */
	@Override
	public void clearAllSourceName() {

		this.strNameList.clear();
		this.hashDirectList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * containsSourceName(java.lang.String)
	 */
	@Override
	public boolean containsSourceName(String name) {

		return this.strNameList.contains(name);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * getHashDirectSet()
	 */
	public Set<Entry<String, Object>> getHashDirectSet() {

		return hashDirectList.entrySet();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * getStrNameList()
	 */
	public String[] getStrNameList() {

		return strNameList.toArray(new String[strNameList.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * isEmptySourceNameList()
	 */
	@Override
	public boolean isEmptySourceNameList() {

		return this.strNameList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * removeSourceName(java.lang.String)
	 */
	@Override
	public synchronized boolean removeSourceName(String name)
			throws ConfigurationException {

		if (!this.strNameList.contains(name)) {
			throw new BindingException("This name '" + name
					+ "' is not existed !");
		}
		synchronized (hashDirectList) {
			if (this.hashDirectList.containsKey(name)) {
				this.hashDirectList.remove(name);
			}
		}
		return this.strNameList.remove(name);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectConstant#to(
	 * java.lang.Object[])
	 */
	@Override
	public ScopeObjectConstant to(Object... objectValues) {

		// Kiểm tra tính hợp lệ
		if (this.strNameList.size() == 0) {
			throw new BindingException("Source name is empty");
		}
		if (this.strNameList.size() != objectValues.length) {
			if (objectValues.length != 1) {
				throw new BindingException("Binding Object is invalid.");
			}
			else {
				for (String name : this.strNameList) {
					this.hashDirectList.put(name, objectValues[0]);
				}
			}
		}
		else {
			for (int i = 0; i < objectValues.length; i++) {
				this.hashDirectList.put(this.strNameList.get(i),
						objectValues[i]);
			}
		}
		return new ScopeObjectConstant(this);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectConstant#
	 * toDestinationObject(java.lang.String, java.lang.Object)
	 */
	@Override
	public synchronized void toDestinationObject(String name, Object object) {

		if (containsSourceName(name)) {
			Assertor.notNull(object);
			this.hashDirectList.put(name, object);
		}
		else {
			throw new BindingException("This name '" + name
					+ "' is not existed !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.IPresentLoadingClass
	 * #getPresentLoadingClasses()
	 */
	@Override
	public ArrayList<Class<?>> getPresentLoadingClasses() {

		ArrayList<Class<?>> result = new ArrayList<Class<?>>();
		for (Object obj : this.hashDirectList.values()) {
			if (ReflectUtils.isCast(Class.class, obj)) {
				if (!result.contains(obj)) {
					result.add((Class<?>) obj);
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.objectmeta.ObjectConstant#getScope()
	 */
	@Override
	public ScopeInstance getScope() {

		return this.scope;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectConstant#setScope(org.
	 * exxlabs.jgentle.context.injecting.scope.ScopeInstance)
	 */
	@Override
	public void setScope(ScopeInstance scope) {

		this.scope = scope;
	}
}
