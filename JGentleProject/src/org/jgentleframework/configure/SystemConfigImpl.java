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
package org.jgentleframework.configure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.annotation.Configurations;
import org.jgentleframework.configure.annotation.ToClass;
import org.jgentleframework.configure.annotation.ToMethod;
import org.jgentleframework.configure.annotation.ToProperty;
import org.jgentleframework.context.ComponentServiceContextType;
import org.jgentleframework.core.JGentelIllegalArgumentException;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.DefinitionPostProcessor;
import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.utils.AnnotationUtils;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.RegularToolkit;
import org.jgentleframework.utils.data.NullClass;

/**
 * The implemetation of {@link SystemConfig} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 16, 2008
 * @see SystemConfig
 * @see AbstractConfigModule
 */
public class SystemConfigImpl extends AbstractConfigModule implements
		SystemConfig {
	/** The anno bean processor list. */
	@SuppressWarnings("unchecked")
	private Map<Class, AnnotationBeanProcessor>						annoBeanProcessorList	= null;

	/** The annotation config list. */
	private List<Annotation>										annotationConfigList	= null;

	/** The annotation register. */
	private AnnotationRegister										annotationRegister		= null;

	/** The csc class list. */
	private List<Class<? extends ComponentServiceContextType<?>>>	cscClassList			= null;

	/** The definition manager. */
	private DefinitionManager										definitionManager		= null;

	/** The DPP list. */
	private List<DefinitionPostProcessor>							DPPList					= null;

	/** The imports cfg lst. */
	private List<ConfigurableImporter>								importsCfgLst			= new LinkedList<ConfigurableImporter>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#addAnnotationPostProcessor
	 * (java.lang.Class,
	 * org.exxlabs.jgentle.core.reflection.annohandler.AnnotationPostProcessor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Annotation> AnnotationBeanProcessor<T> addAnnotationBeanProcessor(
			Class<T> clazz, AnnotationBeanProcessor<T> app) {

		Assertor.notNull(clazz);
		Assertor.notNull(app);
		return (AnnotationBeanProcessor<T>) this.annoBeanProcessorList.put(
				clazz, app);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#addAnnotationPostProcessor
	 * (java.lang.Class, java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Annotation> AnnotationBeanProcessor<T> addAnnotationBeanProcessor(
			Class<T> clazz, Class<? extends AnnotationBeanProcessor<T>> appClass) {

		Assertor.notNull(clazz);
		Assertor.notNull(appClass);
		AnnotationBeanProcessor abp = ReflectUtils.createInstance(appClass);
		return (AnnotationBeanProcessor<T>) this.annoBeanProcessorList.put(
				clazz, abp);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#addDefinitionPostProcessor
	 * (java.lang.Class<? extends
	 * org.exxlabs.jgentle.core.reflection.aohreflect.
	 * DefinitionPostProcessor>[])
	 */
	@Override
	public void addDefinitionPostProcessor(
			Class<? extends DefinitionPostProcessor>... classes) {

		if (classes != null && classes.length != 0) {
			for (Class<? extends DefinitionPostProcessor> clazz : classes) {
				DefinitionPostProcessor obj = ReflectUtils
						.createInstance(clazz);
				this.DPPList.add(obj);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#addDefinitionPostProcessor
	 * (
	 * org.exxlabs.jgentle.core.reflection.aohreflect.DefinitionPostProcessor[])
	 */
	@Override
	public void addDefinitionPostProcessor(DefinitionPostProcessor... dpps) {

		if (dpps != null && dpps.length != 0) {
			for (DefinitionPostProcessor obj : dpps) {
				this.DPPList.add(obj);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#bindCSC(java.lang.
	 * Class)
	 */
	@Override
	public void bindComponentServiceContext(
			Class<? extends ComponentServiceContextType<?>> cscClass) {

		Assertor.notNull(cscClass, "cscClass argument must not be null.");
		if (this.cscClassList.contains(cscClass)) {
			throw new JGentelIllegalArgumentException(
					"This CSC object class is existed.");
		}
		this.cscClassList.add(cscClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.SystemConfig#getAnnotationConfigList()
	 */
	@Override
	public List<Annotation> getAnnotationConfigList() {

		return annotationConfigList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#getAnnotationRegister()
	 */
	@Override
	public AnnotationRegister getAnnotationRegister() {

		return annotationRegister;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#getCscClassList()
	 */
	@Override
	public List<Class<? extends ComponentServiceContextType<?>>> getCscClassList() {

		return cscClassList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#getDefinitionManager()
	 */
	@Override
	public DefinitionManager getDefinitionManager() {

		return definitionManager;
	}

	/*
	 * (non-Javadoc)
	 * @see org.exxlabs.jgentle.configure.jgentle.SystemConfig#getDPPList()
	 */
	@Override
	public List<DefinitionPostProcessor> getDPPList() {

		return DPPList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#getImportsCfgLst()
	 */
	@Override
	public List<ConfigurableImporter> getImportsCfgLst() {

		return importsCfgLst;
	}

	/*
	 * (non-Javadoc)
	 * @see org.exxlabs.jgentle.configure.jgentle.SystemConfig#getOptionsList()
	 */
	@Override
	public Map<String, Object> getOptionsList() {

		return optionsList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.SystemConfig#imports(java.lang.Class<?>[])
	 */
	@Override
	public void imports(Class<?>... classes) {

		Assertor.notNull(classes, "The [classes] argument must not be null !");
		Assertor
				.notEmpty(classes, "The [classes] argument must not be empty !");
		for (final Class<?> clazz : classes) {
			int i = 0;
			for (ConfigurableImporter importer : importsCfgLst) {
				if (importer.getConfigurableClass() == (clazz)
						&& importer.args() == null
						&& importer.argsType() == null) {
					i++;
				}
			}
			if (i == 0) {
				this.importsCfgLst.add(new ConfigurableImporter() {
					@Override
					public Object[] args() {

						return null;
					}

					@Override
					public Class<?>[] argsType() {

						return null;
					}

					@SuppressWarnings("unchecked")
					@Override
					public Class<? extends Configurable> getConfigurableClass() {

						return (Class<? extends Configurable>) clazz;
					}
				});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#imports(java.lang.Class)
	 */
	@Override
	public void imports(Class<? extends Configurable> clazz) {

		imports((Class<?>) clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.SystemConfig#imports(java.lang.Class,
	 * java.lang.Class<?>[], java.lang.Object[])
	 */
	@Override
	public void imports(final Class<? extends Configurable> clazz,
			final Class<?>[] argsType, final Object[] args) {

		Assertor.notNull(clazz, "The [clazz] argument must not be null !");
		Assertor
				.notNull(argsType, "The [argsType] argument must not be null !");
		Assertor.notNull(args, "The [args] argument must not be null !");
		this.importsCfgLst.add(new ConfigurableImporter() {
			@Override
			public Object[] args() {

				return args;
			}

			@Override
			public Class<?>[] argsType() {

				return argsType;
			}

			@Override
			public Class<? extends Configurable> getConfigurableClass() {

				return clazz;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#initAnnotationConfig()
	 */
	public void initAnnotationConfig() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {

		for (Annotation anno : this.annotationConfigList) {
			Method[] methods = AnnotationUtils.getMethodsInAnnotation(anno);
			for (Method method : methods) {
				Class<?> returnType = method.getReturnType();
				if (method.isAnnotationPresent(ToClass.class)
						|| method.isAnnotationPresent(ToProperty.class)
						|| method.isAnnotationPresent(ToMethod.class)) {
					method.setAccessible(true);
					// Kiểm tra điều kiện return type
					if (!returnType.isAnnotation()) {
						throw new InOutDependencyException("Return type of '"
								+ method.getName()
								+ "' must be Annotation Type.");
					}
					// Nếu được chỉ định ToClass
					if (method.isAnnotationPresent(ToClass.class)) {
						ToClass toClass = method.getAnnotation(ToClass.class);
						Annotation annoBinding = (Annotation) method
								.invoke(anno);
						Class<?> clazz = null;
						if (toClass.clazz().equals(NullClass.class)) {
							if (toClass.clazzName().isEmpty()) {
								throw new InOutDependencyException(
										"Does not found class information.");
							}
							else {
								clazz = Class.forName(toClass.clazzName());
							}
						}
						else {
							clazz = toClass.clazz();
						}
						this.definitionManager.loadCustomizedDefinition(clazz,
								annoBinding, toClass.ID());
					}
					// Nếu được chỉ định ToProperty
					if (method.isAnnotationPresent(ToProperty.class)) {
						ToProperty toProperty = method
								.getAnnotation(ToProperty.class);
						if (toProperty != null) {
							Annotation annoBinding = (Annotation) method
									.invoke(anno);
							Class<?> clazz = toProperty.clazz();
							String[] properties = toProperty.properties();
							if (properties.length == 0 || properties == null) {
								throw new InOutDependencyException(
										"Properties of " + toProperty
												+ " is invalid.");
							}
							List<Field> fieldList = Arrays.asList(ReflectUtils
									.getAllDeclaredFields(clazz));
							HashMap<String, Field> fieldResult = new HashMap<String, Field>();
							for (String name : properties) {
								for (Field field : fieldList) {
									if (RegularToolkit.matches(name, field
											.getName())) {
										/*
										 * Nếu trong field Result chưa có field
										 * ứng với tên như thế (hay nói cách
										 * khác là ưu tiên cho các field là
										 * override của derived class)
										 */
										if (!Modifier.isStatic(field
												.getModifiers())) {
											if (!fieldResult.containsKey(field
													.getName())) {
												fieldResult.put(
														field.getName(), field);
											}
										}
										else {
											if (field.getDeclaringClass()
													.equals(clazz)) {
												fieldResult.put(
														field.getName(), field);
											}
										}
									}
								}
							}
							for (Field field : fieldResult.values()) {
								this.definitionManager
										.loadCustomizedDefinition(toProperty
												.ID(), field, clazz,
												annoBinding);
							}
						}
					}
					// Nếu được chỉ định ToMethod
					if (method.isAnnotationPresent(ToMethod.class)) {
						ToMethod toMethod = method
								.getAnnotation(ToMethod.class);
						if (toMethod != null) {
							Annotation annoBinding = (Annotation) method
									.invoke(anno);
							Class<?> clazz = toMethod.clazz();
							String[] methodList = toMethod.methods();
							Class<?>[] args = toMethod.args();
							if (!(args != null && args.length == 1 && args[0]
									.equals(NullClass.class))) {
								if (methodList == null
										|| methodList.length != 1) {
									throw new InOutDependencyException(
											"Methods of" + toMethod
													+ " is invalid.");
								}
								try {
									Method met = ReflectUtils
											.getSupportedMethod(clazz,
													methodList[0], args);
									this.definitionManager
											.loadCustomizedDefinition(toMethod
													.ID(), met, clazz,
													annoBinding);
								}
								catch (NoSuchMethodException e) {
									throw new InOutDependencyException(
											"Does not found " + methodList[0]
													+ " method.");
								}
							}
							else {
								if (methodList == null
										|| methodList.length == 0) {
									throw new InOutDependencyException(
											"Methods of" + toMethod
													+ " is invalid.");
								}
								List<Method> methodRawList = Arrays
										.asList(ReflectUtils
												.getAllDeclaredMethods(clazz));
								HashMap<String, Method> methodResult = new HashMap<String, Method>();
								for (String name : methodList) {
									for (Method met : methodRawList) {
										if (RegularToolkit.matches(name, met
												.getName())) {
											/*
											 * Nếu trong method Result chưa có
											 * method ứng với tên như thế (hay
											 * nói cách khác là ưu tiên cho các
											 * override method của derived
											 * class)
											 */
											if (!Modifier.isStatic(met
													.getModifiers())) {
												if (!methodResult
														.containsKey(met
																.getName())) {
													methodResult.put(met
															.getName(), met);
												}
												else {
													Class<?>[] currArgs = methodResult
															.get(met.getName())
															.getParameterTypes();
													Class<?>[] metArgs = met
															.getParameterTypes();
													if (currArgs.length != metArgs.length) {
														methodResult
																.put(
																		met
																				.getName(),
																		met);
													}
													else {
														int matchesType = 0;
														for (int i = 0; i < metArgs.length; i++) {
															if (currArgs[i]
																	.equals(metArgs[i])) {
																matchesType++;
															}
														}
														if (matchesType != metArgs.length) {
															methodResult
																	.put(
																			met
																					.getName(),
																			met);
														}
													}
												}
											}
											else {
												if (met.getDeclaringClass()
														.equals(clazz)) {
													methodResult.put(met
															.getName(), met);
												}
											}
										}
									}
								}
								for (Method injectMet : methodResult.values()) {
									this.definitionManager
											.loadCustomizedDefinition(toMethod
													.ID(), injectMet, clazz,
													annoBinding);
								}
							}
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.SystemConfig#setDefinitionManager(org.
	 * jgentleframework.core.handling.DefinitionManager)
	 */
	@Override
	public void setDefinitionManager(DefinitionManager defManager) {

		this.definitionManager = defManager;
		this.annotationRegister = defManager.getAnnotationRegister();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.SystemConfig#setImportsCfgLst(java.util
	 * .List)
	 */
	@Override
	public void setImportsCfgLst(List<ConfigurableImporter> importsCfgLst) {

		this.importsCfgLst = importsCfgLst;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.AbstractConfigModule#setOptionsList(java
	 * .util.Map, java.lang.reflect.Method)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setOptionsList(Map<String, Object> optionsList, Method configure) {

		this.optionsList = optionsList;
		this.DPPList = new ArrayList<DefinitionPostProcessor>();
		this.optionsList.put(AbstractConfig.DEFINITION_POST_PROCESSOR, DPPList);
		this.cscClassList = new ArrayList<Class<? extends ComponentServiceContextType<?>>>();
		this.optionsList.put(AbstractConfig.CSC_CLASS_LIST, this.cscClassList);
		this.annotationConfigList = new ArrayList<Annotation>();
		this.optionsList.put(AbstractConfig.ANNOTATION_CONFIG_LIST,
				this.annotationConfigList);
		this.annoBeanProcessorList = new HashMap<Class, AnnotationBeanProcessor>();
		this.optionsList.put(AbstractConfig.ANNOTATION_BEAN_PROCESSOR_LIST,
				this.annoBeanProcessorList);
		// Tìm thông tin cấu hình annotation mặc định
		for (Annotation anno : configure.getDeclaredAnnotations()) {
			if (anno.annotationType().isAnnotationPresent(Configurations.class)) {
				this.annotationConfigList.add(anno);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#unbindCSC(java.lang
	 * .Class)
	 */
	@Override
	public void unbindCSC(
			Class<? extends ComponentServiceContextType<?>> cscClass) {

		Assertor.notNull(cscClass, "cscClass argument must not be null.");
		if (!this.cscClassList.contains(cscClass)) {
			throw new JGentelIllegalArgumentException(
					"This CSC object class is not existed.");
		}
		this.cscClassList.remove(cscClass);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.jgentle.SystemConfig#usingConfig(java.lang
	 * .annotation.Annotation)
	 */
	@Override
	public void usingConfig(Annotation annotation) {

		if (!annotation.annotationType().isAnnotationPresent(
				Configurations.class)) {
			throw new InOutDependencyException("This annotation: " + annotation
					+ " must be annotated with Configurations annotation.");
		}
		this.annotationConfigList.add(annotation);
	}
}
