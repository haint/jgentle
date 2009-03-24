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
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.jgentleframework.context.ComponentServiceContextType;
import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.DefinitionPostProcessor;
import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;

/**
 * Provides some system methods in order to execute some system configurations
 * including {@link DefinitionPostProcessor}, {@link AnnotationBeanProcessor},
 * ... {@link ComponentServiceContextType} and ... some others.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 15, 2008
 * @see AnnotationBeanProcessor
 * @see ComponentServiceContextType
 * @see DefinitionPostProcessor
 */
public interface SystemConfig {
	/**
	 * Adds the given {@link AnnotationBeanProcessor}
	 * 
	 * @param <T>
	 * @param clazz
	 *            object class represents the corresponding annotation type
	 *            appropriate to {@link AnnotationBeanProcessor} instance.
	 * @param app
	 *            the {@link AnnotationBeanProcessor} need to be added.
	 * @return returns the previous {@link AnnotationBeanProcessor} instance if
	 *         it existed, if not, returns null.
	 */
	public <T extends Annotation> AnnotationBeanProcessor<T> addAnnotationBeanProcessor(
			Class<T> clazz, AnnotationBeanProcessor<T> app);

	/**
	 * Adds the given {@link AnnotationBeanProcessor}
	 * 
	 * @param <T>
	 * @param clazz
	 *            object class represents the corresponding annotation type
	 *            appropriate to {@link AnnotationBeanProcessor} instance.
	 * @param appClass
	 *            The object class type of {@link AnnotationBeanProcessor} need
	 *            to be added. object class của APP chỉ định cần add.
	 * @return returns the previous {@link AnnotationBeanProcessor} instance if
	 *         it existed, if not, returns null.
	 */
	public <T extends Annotation> AnnotationBeanProcessor<T> addAnnotationBeanProcessor(
			Class<T> clazz, Class<? extends AnnotationBeanProcessor<T>> appClass);

	/**
	 * Adds an instance or a group of instances of
	 * {@link DefinitionPostProcessor}.
	 * 
	 * @param classes
	 *            the object classes represent class type of
	 *            {@link DefinitionPostProcessor} instances need to be added.
	 */
	public void addDefinitionPostProcessor(
			Class<? extends DefinitionPostProcessor>... classes);

	/**
	 * Adds an instance or a group of instances of
	 * {@link DefinitionPostProcessor}.
	 * 
	 * @param dpps
	 *            the list of instances of {@link DefinitionPostProcessor} need
	 *            to be added
	 */
	public void addDefinitionPostProcessor(DefinitionPostProcessor... dpps);

	/**
	 * Binds a specified {@link ComponentServiceContextType}.
	 * <p>
	 * <b>Note:</b> It will be only effected if the current container is
	 * {@link ServiceProvider}
	 * 
	 * @param cscClass
	 *            the object class of {@link ComponentServiceContextType}.
	 */
	public void bindComponentServiceContext(
			Class<? extends ComponentServiceContextType<?>> cscClass);

	/**
	 * Returns the annotation config list.
	 */
	public List<Annotation> getAnnotationConfigList();

	/**
	 * Returns the current {@link AnnotationRegister}
	 */
	public AnnotationRegister getAnnotationRegister();

	/**
	 * Returns a {@link List} containing all binding
	 * {@link ComponentServiceContextType}
	 */
	public List<Class<? extends ComponentServiceContextType<?>>> getCscClassList();

	/**
	 * Returns the current {@link DefinitionManager} instance.
	 */
	public DefinitionManager getDefinitionManager();

	/**
	 * Returns the list of instances of {@link DefinitionPostProcessor} are
	 * added to container.
	 */
	public List<DefinitionPostProcessor> getDPPList();

	/**
	 * Gets the list containing imported configurable classes.
	 * 
	 * @return the importsCfgLst
	 */
	public List<ConfigurableImporter> getImportsCfgLst();

	/**
	 * Returns the OptionsList
	 */
	public Map<String, Object> getOptionsList();

	/**
	 * Imports the specified configurable classes.
	 * 
	 * @param classes
	 *            the configurable object classes
	 */
	public void imports(Class<?>... classes);

	/**
	 * Imports the specified configurable class.
	 * 
	 * @param clazz
	 *            the configurable object class
	 */
	public void imports(Class<? extends Configurable> clazz);

	/**
	 * Imports an configurable class.
	 * 
	 * @param clazzConfig
	 *            the configurable object class
	 * @param parameterTypes
	 *            the given array containing parameter types.
	 * @param args
	 *            the arguments used for the constructor call.
	 */
	public void imports(Class<? extends Configurable> clazzConfig,
			Class<?>[] parameterTypes, Object[] args);

	public void initAnnotationConfig() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClassNotFoundException;

	/**
	 * Sets the given {@link DefinitionManager} to current configurable
	 * instance. The invoking of this method will also execute the
	 * {@link AnnotationRegister} setting to current configurable instance.
	 * 
	 * @param definitionManager
	 *            the given {@link DefinitionManager} need to be setted.
	 */
	public void setDefinitionManager(DefinitionManager definitionManager);

	/**
	 * Sets the list of imported configurable classes
	 * 
	 * @param importsCfgLst
	 *            the imported configurable classes to set
	 */
	public void setImportsCfgLst(List<ConfigurableImporter> importsCfgLst);

	/**
	 * Unbinds the given {@link ComponentServiceContextType} object class
	 * 
	 * @param cscClass
	 *            the given {@link ComponentServiceContextType}.
	 */
	public void unbindCSC(
			Class<? extends ComponentServiceContextType<?>> cscClass);

	/**
	 * Adds the given configurable annotation.
	 * 
	 * @param annotation
	 *            the configurable annotation need to be added.
	 */
	public void usingConfig(Annotation annotation);
}
