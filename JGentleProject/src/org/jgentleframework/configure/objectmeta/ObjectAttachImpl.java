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
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.naming.ConfigurationException;

import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.utils.Assertor;

/**
 * The implementation of {@link ObjectAttach} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 8, 2007
 * @see ObjectAttach
 */
public class ObjectAttachImpl<T> implements ObjectAttach<T> {
	/** The hash list. */
	private HashMap<Class<?>, Class<?>>							hashList	= new HashMap<Class<?>, Class<?>>();

	/** The name. */
	private String												name		= "";

	/** The name bool. */
	private boolean												nameBool	= true;

	/** The scope list. */
	private HashMap<Entry<Class<?>, Class<?>>, ScopeInstance>	scopeList	= new HashMap<Entry<Class<?>, Class<?>>, ScopeInstance>();

	/** The type list. */
	private ArrayList<Class<?>>									typeList	= new ArrayList<Class<?>>();

	/** lazy_init property, default setting is <b>true</b>. */
	private boolean												lazyInit	= true;

	/**
	 * The Constructor.
	 * 
	 * @param classes
	 *            the classes
	 */
	protected ObjectAttachImpl(Class<?>... classes) {

		for (Class<?> obj : classes) {
			this.typeList.add(obj);
		}
		if (typeList.size() != 1)
			this.nameBool = false;
		else
			this.nameBool = true;
	}

	/**
	 * The Constructor.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	protected ObjectAttachImpl(Class<T> clazz) {

		this.typeList.add(clazz);
		if (typeList.size() != 1)
			this.nameBool = false;
		else
			this.nameBool = true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#addClassType(java
	 * .lang.Class)
	 */
	@Override
	public synchronized boolean addClassType(Class<?> clazz)
			throws ConfigurationException {

		if (this.typeList.contains(clazz)) {
			throw new ConfigurationException("Mapping information "
					+ clazz.getName() + " is invalid! ");
		}
		boolean result = this.typeList.add(clazz);
		if (this.typeList.size() > 1) {
			this.name = "";
			this.nameBool = false;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#clearAllClassType
	 * ()
	 */
	@Override
	public synchronized void clearAllClassType() {

		this.hashList.clear();
		this.scopeList.clear();
		this.typeList.clear();
		this.name = "";
		this.nameBool = false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#containsClassType
	 * (java.lang.Class)
	 */
	@Override
	public boolean containsClassType(Class<?> clazz) {

		return this.typeList.contains(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAttach#countEntry
	 * ()
	 */
	@Override
	public int countEntry() {

		return this.hashList.size();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#getName
	 * ()
	 */
	@Override
	public synchronized String getName() {

		return this.name == null || (this.name != null && this.name.isEmpty()) ? ""
				: this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#getScopeList
	 * ()
	 */
	@Override
	public HashMap<Entry<Class<?>, Class<?>>, ScopeInstance> getScopeList() {

		return scopeList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#getClassTypeArray
	 * ()
	 */
	@Override
	public Class<?>[] getClassTypeArray() {

		return typeList.toArray(new Class<?>[typeList.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectAttach#
	 * isEmptySourceList()
	 */
	@Override
	public boolean isEmptySourceList() {

		return this.typeList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#isNameBool
	 * ()
	 */
	@Override
	public synchronized boolean isNameBool() {

		return this.nameBool;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#removeClassType
	 * (java.lang.Class)
	 */
	@Override
	public synchronized boolean removeClassType(Class<?> clazz)
			throws ConfigurationException {

		synchronized (hashList) {
			if (this.hashList.containsKey(clazz)) {
				this.hashList.remove(clazz);
			}
		}
		if (!this.typeList.contains(clazz)) {
			throw new ConfigurationException("This source class: "
					+ clazz.getName() + " is not exisited !");
		}
		boolean result = this.typeList.remove(clazz);
		if (this.typeList.size() == 1)
			this.nameBool = true;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#to(java
	 * .lang.Class)
	 */
	@Override
	public ObjectAttach.ScopeObject to(Class<? extends T> clazz) {

		return to(new Class<?>[] { clazz });
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#to(java
	 * .lang.Class<?>[])
	 */
	@Override
	public ObjectAttach.ScopeObject to(Class<?>... classes) {

		// Kiểm tra tính hợp lệ của "to classes"
		if (classes.length != this.typeList.size()) {
			if (classes.length == 1) {
				for (Class<?> obj : this.typeList) {
					if (!obj.isAssignableFrom(classes[0])) {
						throw new InOutDependencyException(
								"Binding Classes is invalid! '"
										+ classes[0].getName()
										+ "' is not extended from '"
										+ obj.getName() + "'");
					}
					else {
						Assertor
								.notNull(classes[0],
										"[Assertion failed] - Binding Class argument must not be null !");
						this.hashList.put(obj, classes[0]);
					}
				}
				return new ObjectAttach.ScopeObject(this, this.hashList
						.entrySet());
			}
			else {
				throw new InOutDependencyException(
						"Mapping information is invalid !");
			}
		}
		else {
			for (int i = 0; i < classes.length; i++) {
				Assertor
						.notNull(classes[i],
								"[Assertion failed] - Mapping class must not be null !");
				Class<?> from = this.typeList.get(i);
				Class<?> to = classes[i];
				if (from.isAssignableFrom(to)) {
					this.hashList.put(from, to);
				}
				else {
					throw new InOutDependencyException(
							"Mapping information is invalid !");
				}
			}
		}
		return new ObjectAttach.ScopeObject(this, this.hashList.entrySet());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectMeta.ObjectAttach#to(java
	 * .lang.String[])
	 */
	@Override
	public ObjectAttach.ScopeObject to(String... clazzNames) {

		Assertor.notNull(clazzNames);
		Assertor.notEmpty(clazzNames);
		Class<?>[] classes = new Class<?>[clazzNames.length];
		for (String name : clazzNames) {
			try {
				Class.forName(name);
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return to(classes);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectAttach#
	 * toDestinationClass(java.lang.Class, java.lang.Class)
	 */
	@Override
	public ObjectAttach.ScopeObject toDestinationClass(Class<?> sourceClass,
			Class<?> destinationClazz) {

		if (!sourceClass.isAssignableFrom(destinationClazz)) {
			throw new InOutDependencyException("Binding Classes is invalid !");
		}
		if (!this.typeList.contains(sourceClass)) {
			throw new InOutDependencyException("Source class is not existed !");
		}
		this.hashList.put(sourceClass, destinationClazz);
		Set<Entry<Class<?>, Class<?>>> setEntry = new TreeSet<Entry<Class<?>, Class<?>>>();
		for (Entry<Class<?>, Class<?>> element : this.hashList.entrySet()) {
			if (element.getKey() == sourceClass
					&& element.getValue() == destinationClazz) {
				setEntry.add(element);
				break;
			}
		}
		return new ObjectAttach.ScopeObject(this, setEntry);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#named(java.lang
	 * .String)
	 */
	@Override
	public synchronized ObjectAttach<?> named(String name) {

		if (this.typeList.size() > 1) {
			throw new InOutDependencyException(
					"Source list contain more than one value !");
		}
		else {
			if (this.nameBool)
				this.name = name;
			return this;
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectMeta.ObjectAttach#
	 * getMappingEntrySet()
	 */
	@Override
	public Set<Entry<Class<?>, Class<?>>> getMappingEntrySet() {

		return this.hashList.entrySet();
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
		for (Entry<Class<?>, Class<?>> entry : this.hashList.entrySet()) {
			if (entry.getKey() != null && !result.contains(entry.getKey())) {
				result.add(entry.getKey());
			}
			if (entry.getValue() != null && !result.contains(entry.getValue())) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAttach#lazy_init
	 * (boolean)
	 */
	@Override
	public ObjectAttach<?> lazyInit(boolean lazyInit) {

		setLazyInit(lazyInit);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.objectmeta.ObjectAttach#isLazyInit()
	 */
	@Override
	public boolean isLazyInit() {

		return lazyInit;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.objectmeta.ObjectAttach#setLazyInit(boolean
	 * )
	 */
	@Override
	public void setLazyInit(boolean lazyInit) {

		this.lazyInit = lazyInit;
	}
}
