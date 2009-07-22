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
package org.jgentleframework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jgentleframework.configure.BindingException;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.TemplateClass;
import org.jgentleframework.configure.annotation.Annotate;
import org.jgentleframework.configure.annotation.Bean;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.Binder;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.injecting.AbstractBeanFactory;
import org.jgentleframework.context.injecting.AnnotatingExecutor;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.injecting.scope.ScopeInstanceImpl;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.UniqueNumberGenerator;
import org.jgentleframework.utils.Utils;
import org.jgentleframework.utils.data.Pair;

/**
 * The implementation of {@link ObjectBeanFactory} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 19, 2008
 * @see ObjectBeanFactory
 * @see Provider
 * @see Context
 */
abstract class ObjectBeanFactoryImpl implements ObjectBeanFactory {
	/** The definition manager. */
	private DefinitionManager							definitionManager	= null;

	/** The provider. */
	private Provider									provider			= null;

	/** The service handler. */
	private ServiceHandler								serviceHandler		= null;

	/** The {@link Map} holds mapping list which has mapping name. */
	protected Map<String, Entry<Class<?>, Class<?>>>	aliasMap			= new HashMap<String, Entry<Class<?>, Class<?>>>();

	/** The {@link Map} holds mapping constants. */
	protected Map<String, Object>						mapDirectList		= new HashMap<String, Object>();

	/** The {@link Map} holds mapping scoped list. */
	protected Map<String, ScopeInstance>				scopeList			= new HashMap<String, ScopeInstance>();

	/** The {@link Map} holds mapping list. */
	protected Map<Class<?>, Class<?>>					mappingList			= new HashMap<Class<?>, Class<?>>();

	/**
	 * Instantiates a new object bean factory impl.
	 * 
	 * @param provider
	 *            the provider
	 */
	public ObjectBeanFactoryImpl(Provider provider) {

		this.provider = provider;
		this.serviceHandler = this.provider.getServiceHandler();
		this.definitionManager = this.provider.getDefinitionManager();
	}

	/**
	 * This method is responsible for {@link Definition} loading and building
	 * depending on the annotating data of specified
	 * {@link ObjectBindingConstant}.
	 * 
	 * @param inClass
	 *            the in class
	 * @param annotatedValueList
	 *            the annotated value list
	 * @param ID
	 *            the ID of current {@link Definition}
	 * @param annotateIDList
	 *            the annotate id list
	 */
	protected void buildDefFormAnnotating(
			Class<?> inClass,
			Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList,
			String ID, List<Object> annotateIDList) {

		for (Entry<Types, List<Pair<Identification<?>, Object>>> entry : annotatedValueList
				.entrySet()) {
			Types type = entry.getKey();
			List<Pair<Identification<?>, Object>> pairList = entry.getValue();
			for (Pair<Identification<?>, Object> pair : pairList) {
				Identification<?> key = pair.getKeyPair();
				Object values = pair.getValuePair();
				AnnotatingExecutor executor = AnnotatingExecutor
						.singleton((AbstractBeanFactory) this.provider);
				executor.loadingPair(type, key, values, ID, inClass,
						annotateIDList);
			}
		}
	}

	/**
	 * Builds the {@link Definition} of an instance based on
	 * {@link ObjectBindingConstant}.
	 * 
	 * @param inClass
	 *            the object class is specified in current
	 *            {@link ObjectBindingConstant}
	 * @param injectedValueList
	 *            the injected value list
	 * @param ID
	 *            the ID of {@link Definition}
	 * @param lazy_init
	 *            the lazy_init
	 * @param scope
	 *            the scope
	 * @param notLazyList
	 *            the not lazy list
	 * @param bool
	 *            the bool
	 * @param annotateIDList
	 *            the annotate id list
	 * @param annotatedValueList
	 *            the annotated value list
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	protected void buildDefObjectBindingConstant(
			Class<?> inClass,
			Map<String, Object> injectedValueList,
			Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList,
			String ID, boolean lazy_init, ScopeInstance scope,
			List<Object> notLazyList, boolean bool, List<Object> annotateIDList)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		List<?> list = null;
		if (bool) {
			list = Arrays.asList(ReflectUtils.getDeclaredMethods(inClass));
		}
		else {
			list = Arrays.asList(ReflectUtils.getDeclaredFields(inClass));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		for (String name : injectedValueList.keySet()) {
			for (Object obj : list) {
				String objName = (String) ReflectUtils.invokeMethod(obj,
						"getName", null, null, true, false);
				if (objName.equals(name)) {
					result.put(objName, obj);
				}
			}
		}
		if (result.size() != injectedValueList.size()) {
			throw new InOutDependencyException(
					"Properties were configured in '" + inClass
							+ "' is invalid!");
		}
		// Thiết lập thông tin dữ liệu definition từ dữ liệu inject
		for (Entry<String, Object> entry : injectedValueList.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			// Nếu value là String
			if (value != null && value.getClass().equals(String.class)) {
				String valueStr = (String) value;
				Definition def = null;
				// create definition
				if (bool) {
					this.definitionManager.loadCustomizedDefinition(ID,
							(Method) result.get(name), inClass, TemplateClass
									.getAnnotation(Inject.class));
					def = this.definitionManager.getDefinition(ID)
							.getMethodDefList().get(result.get(name));
				}
				else {
					this.definitionManager.loadCustomizedDefinition(ID,
							(Field) result.get(name), inClass, TemplateClass
									.getAnnotation(Inject.class));
					def = this.definitionManager.getDefinition(ID)
							.getFieldDefList().get(result.get(name));
				}
				// identify string value
				if (valueStr.equals(Configurable.REF_MAPPING)) {
					String unique = UniqueNumberGenerator.getNextUID();
					def.setValueOfAnnotation(Inject.class, "value", unique);
				}
				else if (valueStr.startsWith(REF.REF_CONSTANT)
						|| valueStr.startsWith(REF.REF_MAPPING)
						|| valueStr.startsWith(REF.REF_ID)) {
					def.setValueOfAnnotation(Inject.class, "value", valueStr);
				}
				else {
					String unique = UniqueNumberGenerator.getNextUID();
					this.mapDirectList.put(REF.refConstant(unique), value);
					def.setValueOfAnnotation(Inject.class, "value", REF
							.refConstant(unique));
				}
			}
			// nếu khác String (object)
			else {
				if (bool)
					this.definitionManager.loadCustomizedDefinition(ID,
							(Method) result.get(name), inClass, TemplateClass
									.getAnnotation(Inject.class));
				else
					this.definitionManager.loadCustomizedDefinition(ID,
							(Field) result.get(name), inClass, TemplateClass
									.getAnnotation(Inject.class));
				Definition def = bool ? this.definitionManager
						.getDefinition(ID).getMethodDefList().get(
								result.get(name)) : this.definitionManager
						.getDefinition(ID).getFieldDefList().get(
								result.get(name));
				String unique = UniqueNumberGenerator.getNextUID();
				def.setValueOfAnnotation(Inject.class, "value", REF
						.refConstant(unique));
				this.mapDirectList.put(REF.refConstant(unique), value);
			}
		}
		// Creates Definition from annotating information
		buildDefFormAnnotating(inClass, annotatedValueList, ID, annotateIDList);
		// Creates object bean if lazy_init attribute is false
		if (lazy_init == false) {
			if (notLazyList != null && !scope.equals(Scope.PROTOTYPE)) {
				notLazyList.add(REF.ref(ID));
			}
			else if (!scope.equals(Scope.PROTOTYPE)) {
				this.provider.getBeanBoundToDefinition(ID);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getDefinitionManager
	 * ()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return this.definitionManager;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getProvider()
	 */
	@Override
	public Provider getProvider() {

		return this.provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getServiceHandler
	 * ()
	 */
	@Override
	public ServiceHandler getServiceHandler() {

		return this.serviceHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_BeanClass
	 * (java.lang.Class, java.util.List)
	 */
	@Override
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList) {

		load_BeanClass(objClass, notLazyList, Types.ALL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_BeanClass
	 * (java.lang.Class, java.util.List,
	 * org.jgentleframework.configure.enums.Types)
	 */
	@Override
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList,
			Types type) {

		load_BeanClass(objClass, notLazyList, type, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_BeanClass
	 * (java.lang.Class, java.util.List,
	 * org.jgentleframework.configure.enums.Types, java.util.List)
	 */
	@Override
	public void load_BeanClass(Class<?> objClass, List<Object> notLazyList,
			Types type, List<Object> annotateIDList) {

		if (type.getClassType() == null && type != Types.ALL
				&& type != Types.NON_ANNOTATION) {
			throw new InOutDependencyException(
					"Invalid value for 'type' argument! " + type.name()
							+ " is not permitted !");
		}
		if ((type != Types.ALL && type != Types.NON_ANNOTATION && type
				.getClassType().isAssignableFrom(objClass))
				|| type.equals(Types.ALL)
				|| (type.equals(Types.NON_ANNOTATION) && !Annotation.class
						.isAssignableFrom(objClass))) {
			String ID = "";
			Definition def = this.definitionManager.getDefinition(objClass);
			if (def.isAnnotationPresent(Bean.class)) {
				Bean annoBean = def.getAnnotation(Bean.class);
				ID = annoBean.value();
				ID = ID.isEmpty() ? objClass.getName() : ID;
				ScopeInstance scope = annoBean.scope();
				boolean lazy_init = annoBean.lazy_init();
				ObjectBindingConstant obc = Binder
						.createObjectBindingConstant();
				obc.lazyInit(lazy_init).to().in(objClass).id(ID).scope(scope);
				load_ObjectBindingConstant(obc, notLazyList, annotateIDList);
			}
			else {
				ID = objClass.getName();
				ObjectBindingConstant obc = Binder
						.createObjectBindingConstant();
				obc.to().in(objClass).id(ID).scope(Scope.SINGLETON);
				load_ObjectBindingConstant(obc, notLazyList, annotateIDList);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_ObjectAttach
	 * (org.jgentleframework.configure.objectmeta.ObjectAttach, java.util.List)
	 */
	@Override
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList) {

		load_ObjectAttach(objAth, notLazyList, Types.ALL);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_ObjectAttach
	 * (org.jgentleframework.configure.objectmeta.ObjectAttach, java.util.List,
	 * org.jgentleframework.configure.enums.Types)
	 */
	@Override
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList, Types type) {

		load_ObjectAttach(objAth, notLazyList, type, null);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#load_ObjectAttach
	 * (org.jgentleframework.configure.objectmeta.ObjectAttach, java.util.List,
	 * org.jgentleframework.configure.enums.Types, java.util.List)
	 */
	@Override
	public void load_ObjectAttach(ObjectAttach<?> objAth,
			List<Object> notLazyList, Types type, List<Object> annotateIDList) {

		if (type.getClassType() == null && type != Types.ALL
				&& type != Types.NON_ANNOTATION) {
			throw new InOutDependencyException(
					"Invalid value for 'type' argument! " + type.name()
							+ " is not permitted !");
		}
		// Nếu objAth có chỉ định name (alias)
		if (objAth.isNameBool() == true && !objAth.getName().isEmpty()) {
			// Kiểm tra thông tin cặp ánh xạ.
			if (objAth.countEntry() != 1) {
				throw new InOutDependencyException(
						"Mapping information is invalid !");
			}
			else {
				String name = objAth.getName();
				@SuppressWarnings("unchecked")
				Entry<Class<?>, Class<?>> entry = (Entry<Class<?>, Class<?>>) objAth
						.getMappingEntrySet().toArray()[0];
				if ((type != Types.ALL && type != Types.NON_ANNOTATION && type
						.getClassType().isAssignableFrom(entry.getValue()))
						|| type.equals(Types.ALL)
						|| (type.equals(Types.NON_ANNOTATION) && !Annotation.class
								.isAssignableFrom(entry.getValue()))) {
					if (this.aliasMap.containsKey(name)) {
						throw new InOutDependencyException("This name: " + name
								+ " is existed.");
					}
					else {
						this.aliasMap.put(name, entry);
						if (annotateIDList != null) {
							Definition defKey = this.definitionManager
									.getDefinition(entry.getKey());
							Definition defValue = this.definitionManager
									.getDefinition(entry.getValue());
							if (defKey
									.isAnnotationPresentAtAnyWhere(Annotate.class))
								annotateIDList.add(entry.getKey());
							if (defValue
									.isAnnotationPresentAtAnyWhere(Annotate.class))
								annotateIDList.add(entry.getValue());
						}
					}
				}
			}
		}
		else { // nếu objAth không chỉ định name (alias)
			Set<Entry<Class<?>, Class<?>>> entryList = objAth
					.getMappingEntrySet();
			for (Entry<Class<?>, Class<?>> entry : entryList) {
				if ((type != Types.ALL && type != Types.NON_ANNOTATION && (type != null && type
						.getClassType().isAssignableFrom(entry.getValue())))
						|| type.equals(Types.ALL)
						|| (type.equals(Types.NON_ANNOTATION) && !Annotation.class
								.isAssignableFrom(entry.getValue()))) {
					this.mappingList.put(entry.getKey(), entry.getValue());
					if (annotateIDList != null) {
						Definition defKey = this.definitionManager
								.getDefinition(entry.getKey());
						Definition defValue = this.definitionManager
								.getDefinition(entry.getValue());
						if (defKey
								.isAnnotationPresentAtAnyWhere(Annotate.class))
							annotateIDList.add(entry.getKey());
						if (defValue
								.isAnnotationPresentAtAnyWhere(Annotate.class))
							annotateIDList.add(entry.getValue());
					}
				}
			}
		}
		// load scope information
		for (Entry<Entry<Class<?>, Class<?>>, ScopeInstance> enScope : objAth
				.getScopeList().entrySet()) {
			String scopeName = null;
			Class<?> typeClass = enScope.getKey().getKey();
			Class<?> targetClass = enScope.getKey().getValue();
			if ((type != Types.ALL && type != Types.NON_ANNOTATION && (type != null && type
					.getClassType().isAssignableFrom(targetClass)))
					|| type.equals(Types.ALL)
					|| (type.equals(Types.NON_ANNOTATION) && !Annotation.class
							.isAssignableFrom(targetClass))) {
				Definition definition = definitionManager
						.getDefinition(targetClass);
				scopeName = Utils.createScopeName(typeClass, targetClass,
						definition, objAth.getName());
				ScopeInstance scopeIns = enScope.getValue();
				this.scopeList.put(scopeName, scopeIns);
				// Instantiates bean instance if lazy-init attribute is false.
				if (!objAth.isLazyInit() && !scopeIns.equals(Scope.PROTOTYPE)) {
					if (notLazyList != null) {
						if (objAth.isNameBool() == true
								&& !objAth.getName().isEmpty()) {
							notLazyList.add(REF.refMapping(objAth.getName()));
						}
						else {
							notLazyList.add(typeClass);
						}
					}
					else {
						if (objAth.isNameBool() == true
								&& !objAth.getName().isEmpty()) {
							this.provider.getBeanBoundToMapping(objAth
									.getName());
						}
						else {
							this.provider.getBean(typeClass);
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.injecting.ObjectBeanFactory#
	 * load_ObjectBindingConstant
	 * (org.jgentleframework.configure.objectmeta.ObjectBindingConstant,
	 * java.util.List)
	 */
	@Override
	public void load_ObjectBindingConstant(ObjectBindingConstant objBndCst,
			List<Object> notLazyList) {

		load_ObjectBindingConstant(objBndCst, notLazyList, null);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.injecting.ObjectBeanFactory#
	 * load_ObjectBindingConstant
	 * (org.jgentleframework.configure.objectmeta.ObjectBindingConstant,
	 * java.util.List, java.util.List)
	 */
	@Override
	public void load_ObjectBindingConstant(ObjectBindingConstant objBndCst,
			List<Object> notLazyList, List<Object> annotateIDList) {

		String ID = objBndCst.getID();
		Map<String, Object> injectedValueList = objBndCst
				.getInjectedValueList();
		Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList = objBndCst
				.getAnnotatedValueList();
		Class<?> inClass = objBndCst.getInClass();
		boolean lazyInit = objBndCst.isLazyInit();
		ScopeInstance scope = objBndCst.getScope();
		if (injectedValueList.size() == 0 && annotatedValueList.size() == 0) {
			this.definitionManager.loadCustomizedDefinition(inClass, null, ID);
			return;
		}
		// Creates the Definition
		try {
			this.buildDefObjectBindingConstant(inClass, injectedValueList,
					annotatedValueList, ID, lazyInit, scope, notLazyList,
					inClass.isAnnotation(), annotateIDList);
		}
		catch (NoSuchMethodException e) {
			InOutDependencyException exception = new InOutDependencyException(
					"Could not load ObjectBindingConstant according to ID '"
							+ ID + "'");
			exception.initCause(e);
			throw exception;
		}
		catch (IllegalAccessException e) {
			InOutDependencyException exception = new InOutDependencyException(
					"Could not load ObjectBindingConstant according to ID '"
							+ ID + "'");
			exception.initCause(e);
			throw exception;
		}
		catch (InvocationTargetException e) {
			InOutDependencyException exception = new InOutDependencyException(
					"Could not load ObjectBindingConstant according to ID '"
							+ ID + "'");
			exception.initCause(e);
			throw exception;
		}
		/* Sets scope */
		String scopeName = Utils.createScopeName(inClass, inClass,
				this.definitionManager.getDefinition(ID), null);
		this.scopeList.put(scopeName, objBndCst.getScope());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#load_ObjectConstant(org
	 * .exxlabs.jgentle.configure.injecting.objectmeta.ObjectConstant)
	 */
	@Override
	public void load_ObjectConstant(ObjectConstant objCst) {

		synchronized (this.scopeList) {
			ScopeInstance scope = objCst.getScope();
			for (Entry<String, Object> obj : objCst.getHashDirectSet()) {
				try {
					String scopeName = Utils.createScopeName(obj.getKey());
					this.scopeList.put(scopeName, scope);
					ScopeImplementation scopeImpl = createScopeInstance(scopeName);
					scopeImpl.putBean(scopeName, obj.getValue(), this);
				}
				catch (InvalidAddingOperationException e) {
					BindingException ex = new BindingException();
					ex.initCause(e);
					throw ex;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#createScopeInstance
	 * (java.lang.String)
	 */
	public ScopeImplementation createScopeInstance(String scopeName) {

		ScopeImplementation result = null;
		synchronized (this.scopeList) {
			ScopeInstance scopeInstance = this.scopeList.get(scopeName);
			if (scopeInstance == null) {
				throw new InOutDependencyException(
						"Does not found the scope instance !");
			}
			else {
				/*
				 * Nếu scopeInstance chưa thật sự được khởi tạo.
				 */
				if (ReflectUtils.isCast(ScopeInstanceImpl.class, scopeInstance)) {
					ScopeInstanceImpl scopeImpl = (ScopeInstanceImpl) scopeInstance;
					result = (ScopeImplementation) getBean(scopeImpl.getScope());
					// Đưa đối tượng scope Implementation vừa khởi tạo vào trong
					// scopeList.
					this.scopeList.put(scopeName, result);
				}
				else {
					result = (ScopeImplementation) scopeInstance;
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getBean(java
	 * .lang.String)
	 */
	public abstract Object getBean(String refer);

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getRefInstance
	 * (java.lang.String)
	 */
	public abstract Object getRefInstance(String refInstance);

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getAliasMap()
	 */
	@Override
	public Map<String, Entry<Class<?>, Class<?>>> getAliasMap() {

		return this.aliasMap;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getMapDirectList
	 * ()
	 */
	@Override
	public Map<String, Object> getMapDirectList() {

		return this.mapDirectList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getMappingList()
	 */
	@Override
	public Map<Class<?>, Class<?>> getMappingList() {

		return this.mappingList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.ObjectBeanFactory#getScopeList()
	 */
	@Override
	public Map<String, ScopeInstance> getScopeList() {

		return this.scopeList;
	}
}
