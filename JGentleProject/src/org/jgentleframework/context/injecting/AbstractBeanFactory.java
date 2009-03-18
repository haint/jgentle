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
package org.jgentleframework.context.injecting;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.AnnotatingRuntimeException;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Annotate;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.beans.FactoryBean;
import org.jgentleframework.context.injecting.scope.ScopeController;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.factory.InOutExecutor;
import org.jgentleframework.core.factory.support.CommonFactory;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;
import org.jgentleframework.utils.data.Pair;

/**
 * This is an abstract class, an implementation of {@link IAbstractBeanFactory}
 * interface. This class is responsible for configured data management of
 * JGentle container. It provides some methods in order to check, manage and
 * access to configured data.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 10, 2008
 * @see IAbstractBeanFactory
 * @see AbstractLoadingFactory
 * @see ObjectBeanFactory
 * @see Provider
 */
public abstract class AbstractBeanFactory extends AbstractLoadingFactory
		implements IAbstractBeanFactory, Provider {
	/** The log. */
	protected final Log			log			= LogFactory.getLog(getClass());

	private final static Log	staticLog	= LogFactory
													.getLog(AbstractBeanFactory.class);

	/**
	 * This method is responsible for annotating {@link Definition}
	 * instantiation.
	 * 
	 * @param provider
	 *            the current {@link Provider}
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public static void buildDefBeanAnnotate(Provider provider,
			ArrayList<Object> annotateIDList) {

		DefinitionManager defManager = provider.getDefinitionManager();
		for (Object obj : annotateIDList) {
			Definition def = ReflectUtils.isCast(String.class, obj) ? defManager
					.getDefinition((String) obj)
					: defManager.getDefinition(obj);
			// checking class
			if (def.isAnnotationPresent(Annotate.class)) {
				Annotate anno = def.getAnnotation(Annotate.class);
				for (String str : anno.value()) {
					Object bean = ((AbstractBeanFactory) provider).getBean(str);
					if (bean != null
							&& ReflectUtils.isCast(Annotation.class, bean)) {
						if (ReflectUtils.isCast(String.class, obj))
							defManager.loadCustomizedDefinition((Class<?>) def
									.getKey(), (Annotation) bean, (String) obj);
						else if (ReflectUtils.isCast(Class.class, obj))
							defManager.loadCustomizedDefinition((Class<?>) obj,
									(Annotation) bean, null);
						else {
							if (staticLog.isErrorEnabled()) {
								staticLog
										.error(
												"Could not build annotating definition !! ",
												new AnnotatingRuntimeException());
							}
						}
					}
					else {
						if (staticLog.isErrorEnabled()) {
							staticLog
									.error(
											"Could not build annotating definition !! ",
											new AnnotatingRuntimeException());
						}
					}
				}
			}
			// checking fields
			if (def.isAnnotationPresentAtAnyFields(Annotate.class)) {
				ArrayList<Field> fieldList = def
						.getFieldsAnnotatedWith(Annotate.class);
				for (Field field : fieldList) {
					Definition defChild = def.getMemberDefinition(field);
					Annotate anno = defChild.getAnnotation(Annotate.class);
					for (String str : anno.value()) {
						Object bean = ((AbstractBeanFactory) provider)
								.getBean(str);
						if (bean != null
								&& ReflectUtils.isCast(Annotation.class, bean)) {
							if (ReflectUtils.isCast(String.class, obj))
								defManager.loadCustomizedDefinition(
										(String) obj, field, (Class<?>) def
												.getKey(), (Annotation) bean);
							else if (ReflectUtils.isCast(Class.class, obj))
								defManager.loadCustomizedDefinition(field,
										(Class<?>) obj, (Annotation) bean);
							else if (staticLog.isErrorEnabled()) {
								staticLog
										.error(
												"Could not build annotating definition !! ",
												new AnnotatingRuntimeException());
							}
						}
						else {
							if (staticLog.isErrorEnabled()) {
								staticLog
										.error(
												"Could not build annotating definition !! ",
												new AnnotatingRuntimeException());
							}
						}
					}
				}
			}
			// checking methods
			if (def.isAnnotationPresentAtAnyMethods(Annotate.class)) {
				ArrayList<Method> methodList = def
						.getMethodsAnnotatedWith(Annotate.class);
				for (Method method : methodList) {
					Definition defChild = def.getMemberDefinition(method);
					Annotate anno = defChild.getAnnotation(Annotate.class);
					for (String str : anno.value()) {
						Object bean = ((AbstractBeanFactory) provider)
								.getBean(str);
						if (bean != null
								&& ReflectUtils.isCast(Annotation.class, bean)) {
							if (ReflectUtils.isCast(String.class, obj))
								defManager.loadCustomizedDefinition(
										(String) obj, method, (Class<?>) def
												.getKey(), (Annotation) bean);
							else if (ReflectUtils.isCast(Class.class, obj))
								defManager.loadCustomizedDefinition(method,
										(Class<?>) obj, (Annotation) bean);
							else if (staticLog.isErrorEnabled()) {
								staticLog
										.error(
												"Could not build annotating definition !! ",
												new AnnotatingRuntimeException());
							}
						}
						else {
							if (staticLog.isErrorEnabled()) {
								staticLog
										.error(
												"Could not build annotating definition !! ",
												new AnnotatingRuntimeException());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method is responsible for object bean instatiation.
	 * 
	 * @param provider
	 *            the {@link Provider}
	 * @param beanList
	 *            the reference string of beans.
	 */
	public static void buildObjectBeanFromInfo(Provider provider,
			ArrayList<Object> beanList) {

		for (Object obj : beanList) {
			if (ReflectUtils.isCast(String.class, obj)) {
				String info = (String) obj;
				if (info.indexOf(":") == -1) {
					throw new InOutDependencyException(
							"Could not create object bean from information '"
									+ info + "'");
				}
				else {
					String[] infoArray = info.split(":");
					if (infoArray[0].equals(Configurable.REF_MAPPING)) {
						provider.getBeanBoundToMapping(infoArray[1]);
					}
					else if (infoArray[0].equals(Configurable.REF_ID)) {
						provider.getBeanBoundToDefinition(infoArray[1]);
					}
					else {
						if (staticLog.isErrorEnabled()) {
							staticLog.error(
									"Could not create object bean from information '"
											+ info + "'",
									new InOutDependencyException());
						}
					}
				}
			}
			else if (ReflectUtils.isCast(Class.class, obj)) {
				provider.getBean((Class<?>) obj);
			}
			else {
				if (staticLog.isErrorEnabled()) {
					staticLog
							.error(
									"Could not create object bean from information '"
											+ obj + "'",
									new InOutDependencyException());
				}
			}
		}
	}

	/** The {@link ScopeController}. */
	protected ScopeController	scopeController	= new ScopeController();

	/** The {@link ServiceHandler}. */
	protected ServiceHandler	serviceHandler	= null;

	/**
	 * Find args of default constructor.
	 * 
	 * @param definition
	 *            the definition
	 * @return the pair< class<?>[], object[]>
	 */
	protected Pair<Class<?>[], Object[]> findArgsOfDefaultConstructor(
			Definition definition) {

		Class<?>[] argTypes = null;
		Object[] args = null;
		// find default constructor.
		if (definition.isInterpretedOfClass()) {
			HashMap<Constructor<?>, Definition> hash = definition
					.getConstructorDefList();
			Constructor<?> constructor = null;
			if (hash.size() != 0) {
				constructor = Utils.getDefaultConstructor((Class<?>) definition
						.getKey());
			}
			Definition defCons = hash.get(constructor);
			if (defCons != null) {
				argTypes = constructor.getParameterTypes();
				if (argTypes != null && argTypes.length != 0) {
					args = new Object[argTypes.length];
					if (defCons.isAnnotationPresent(Inject.class)) {
						Inject inject = defCons.getAnnotation(Inject.class);
						for (int i = 0; i < args.length; i++) {
							args[i] = InOutExecutor.getInjectedDependency(
									inject, argTypes[i], this);
						}
					}
					if (defCons
							.isAnnotationPresentAtAnyParameters(Inject.class)) {
						Definition[] defList = defCons.getParameterDefList();
						for (int i = 0; i < defList.length; i++) {
							if (defList[i] != null
									&& defList[i]
											.isAnnotationPresent(Inject.class)) {
								Inject inject = defList[i]
										.getAnnotation(Inject.class);
								args[i] = InOutExecutor.getInjectedDependency(
										inject, argTypes[i], this);
							}
						}
					}
				}
			}
		}
		Pair<Class<?>[], Object[]> result = null;
		if (argTypes != null && args != null)
			result = new Pair<Class<?>[], Object[]>(argTypes.clone(), args
					.clone());
		else
			result = new Pair<Class<?>[], Object[]>(null, null);
		return result;
	}

	/**
	 * Returns bean instance bound to the given ID.
	 * 
	 * @param ID
	 *            the given ID
	 * @return returns the bean instance if existed, otherwise returns null.
	 */
	public abstract Object getBeanBoundToDefinition(String ID);

	/**
	 * Returns object bean of specified scope.
	 * 
	 * @param scopeImple
	 *            the scope implementation.
	 * @param nameScope
	 *            the string represents scope name.
	 * @param selector
	 *            the selector
	 * @return the bean from scope
	 */
	protected Object getBeanFromScope(ScopeImplementation scopeImple,
			Selector selector, String nameScope) {

		Object result = null;
		// Thực thi lấy ra bean từ ScopeImplementation nếu bean đã được khởi tạo
		// trong scope hoặc khởi tạo bean tương ứng với scope chỉ định.
		synchronized (this.scopeController) {
			if (!this.scopeController.containsScope(scopeImple)) {
				this.scopeController.addScope(scopeImple);
			}
			synchronized (scopeImple) {
				result = scopeImple.getBean(selector, nameScope, this
						.getObjectBeanFactory());
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#getBeanInstance
	 * (java.lang.Class, java.lang.Class, java.lang.String, java.lang.String)
	 */
	@Override
	public Object getBeanInstance(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition) {

		Assertor.notNull(type);
		targetClass = targetClass == null ? this.objectBeanFactory
				.getMappingList().get(type) : targetClass;
		targetClass = targetClass == null ? type : targetClass;
		if (definition == null) {
			definition = this.defManager.getDefinition(targetClass);
		}
		Pair<Class<?>[], Object[]> pairCons = findArgsOfDefaultConstructor(definition);
		Class<?>[] argTypes = pairCons.getKeyPair();
		Object[] args = pairCons.getValuePair();
		CoreInstantiationSelector coreSelector = new CoreInstantiationSelectorImpl(
				type, targetClass, mappingName, argTypes, args, definition);
		return getBeanInstance(coreSelector);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#getBeanInstance
	 * (java.lang.Class,
	 * org.jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public Object getBeanInstance(Class<?> type, Definition definition) {

		return getBeanInstance(type, null, null, definition);
	}

	/**
	 * Gets the bean instance.
	 * 
	 * @param selector
	 *            the selector
	 * @return the bean instance
	 */
	protected Object getBeanInstance(Selector selector) {

		Object result = null;
		if (ReflectUtils.isCast(CoreInstantiationSelector.class, selector)) {
			CoreInstantiationSelector coreSelector = (CoreInstantiationSelector) selector;
			Class<?> type = coreSelector.getType();
			Class<?> targetClass = coreSelector.getTargetClass();
			Definition definition = coreSelector.getDefinition();
			String mappingName = coreSelector.getMappingName();
			String scopeName = null;
			// validate
			definition = definition == null ? this.defManager
					.getDefinition(targetClass) : definition;
			Class<?> defClass = (Class<?>) definition.getKey();
			if (!defClass.equals(targetClass)) {
				if (log.isErrorEnabled()) {
					log.error(
							"The given definition is not corresponding to class '"
									+ targetClass.toString() + "'!",
							new InOutDependencyException());
				}
			}
			scopeName = Utils.createScopeName(type, targetClass, definition,
					mappingName);
			// creates scope info, default is SINGLETON
			HashMap<String, ScopeInstance> scopeList = this.objectBeanFactory
					.getScopeList();
			synchronized (scopeList) {
				if (!scopeList.containsKey(scopeName)) {
					scopeList.put(scopeName, Scope.SINGLETON);
				}
			}
			ScopeImplementation scopeImple = null;
			scopeImple = this.objectBeanFactory.createScopeInstance(scopeName);
			result = getBeanFromScope(scopeImple, selector, scopeName);
			// Checking FactoryBean
			if (FactoryBean.class.isAssignableFrom(targetClass)) {
				FactoryBean factoryBean = (FactoryBean) result;
				String nameScopeFac = scopeName + ":"
						+ FactoryBean.class.toString();
				if (factoryBean.isSingleton()) {
					synchronized (this.objectBeanFactory.getMapDirectList()) {
						if (this.objectBeanFactory.getMapDirectList()
								.containsKey(nameScopeFac)) {
							return this.objectBeanFactory.getMapDirectList()
									.get(nameScopeFac);
						}
						else {
							Object resultFac = CommonFactory.singleton()
									.executeFactoryBean(factoryBean,
											targetClass);
							this.objectBeanFactory.getMapDirectList().put(
									nameScopeFac, resultFac);
							return resultFac;
						}
					}
				}
				else {
					return CommonFactory.singleton().executeFactoryBean(
							factoryBean, targetClass);
				}
			}
		}
		else
			Assertor.throwRunTimeException("The selector is invalid !!");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.injecting.IAbstractBeanFactory#
	 * getDefinitionManager()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return this.defManager;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#getScopeController
	 * ()
	 */
	@Override
	public ScopeController getScopeController() {

		return this.scopeController;
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
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isContainsConstant
	 * (java.lang.String)
	 */
	@Override
	public boolean isContainsConstant(String name) {

		synchronized (this.objectBeanFactory.getMapDirectList()) {
			if (this.objectBeanFactory.getMapDirectList().containsKey(name)) {
				return true;
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.injecting.IAbstractBeanFactory#
	 * isContainsMappingName(java.lang.String)
	 */
	@Override
	public boolean isContainsMappingName(String name) {

		synchronized (this.objectBeanFactory.getAliasMap()) {
			if (this.objectBeanFactory.getAliasMap().containsKey(name)) {
				return true;
			}
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isPrototype
	 * (java.lang.Class)
	 */
	@Override
	public boolean isPrototype(Class<?> clazz) {

		Definition def = this.objectBeanFactory.getMappingList().containsKey(
				clazz) ? this.defManager.getDefinition(this.objectBeanFactory
				.getMappingList().get(clazz)) : this.defManager
				.getDefinition(clazz);
		return isScope(def, Scope.PROTOTYPE);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isPrototype
	 * (java.lang.String)
	 */
	@Override
	public boolean isPrototype(String ID) {

		Definition def = this.getDefinitionManager().getDefinition(ID);
		if (def == null) {
			if (log.isErrorEnabled()) {
				log.error("The Definition ID '" + ID + "' is not existed !",
						new NoSuchElementException());
			}
		}
		return isScope(def, Scope.PROTOTYPE);
	}

	/**
	 * Checks if is scope.
	 * 
	 * @param def
	 *            the definition
	 * @param sc
	 *            the scope instance
	 * @return true, if checks if is scope
	 */
	protected boolean isScope(Definition def, ScopeInstance sc) {

		if (def != null) {
			String scopeName = def.getKey().toString() + ":" + def.toString();
			ScopeInstance scope = this.objectBeanFactory.getScopeList().get(
					scopeName);
			if (ReflectUtils.isCast(sc.getClass(), scope)) {
				if (scope.equals(sc)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isSingleton
	 * (java.lang.Class)
	 */
	@Override
	public boolean isSingleton(Class<?> clazz) {

		Definition def = this.objectBeanFactory.getMappingList().containsKey(
				clazz) ? this.defManager.getDefinition(this.objectBeanFactory
				.getMappingList().get(clazz)) : this.defManager
				.getDefinition(clazz);
		return isScope(def, Scope.SINGLETON);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isSingleton
	 * (java.lang.Object)
	 */
	@Override
	public boolean isSingleton(Object bean) {

		synchronized (this.objectBeanFactory.getMapDirectList()) {
			for (Object obj : this.objectBeanFactory.getMapDirectList()
					.values()) {
				if (bean == obj) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.IAbstractBeanFactory#isSingleton
	 * (java.lang.String)
	 */
	@Override
	public boolean isSingleton(String ID) {

		Definition def = this.getDefinitionManager().getDefinition(ID);
		if (def == null) {
			if (log.isErrorEnabled()) {
				log.error("The Definition ID '" + ID + "' is not existed !",
						new NoSuchElementException());
			}
		}
		return isScope(def, Scope.SINGLETON);
	}
}
