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
package org.jgentleframework.configure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.reflection.metadata.Definition;

/**
 * {@link BindingConfig} interface provides all methods need to perform binding.
 * It offers some tools in order to bind a class type to an implementation
 * class, and some programmatic tools to create a customized {@link Definition}
 * of bean.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 17, 2007
 */
public interface BindingConfig extends CoreBinding {
	/**
	 * Returns an {@link ArrayList} containing all object classes of mapping
	 * bean.
	 */
	public List<Class<?>> getBeanClassList();

	/**
	 * Returns an {@link ArrayList} containing all current binding of beans (
	 * {@link ObjectBindingConstant}).
	 */
	public List<ObjectBindingConstant> getObjBindingConstantList();

	/**
	 * Returns an {@link ArrayList} containing all current attach mappings (
	 * {@link ObjectAttach})
	 */
	public List<ObjectAttach<?>> getObjectAttachList();

	/**
	 * Returns an {@link ArrayList} containing all current attach constant
	 * mappings ({@link ObjectConstant})
	 */
	public List<ObjectConstant> getObjectConstantList();

	/**
	 * Returns option list
	 * 
	 * @return the optionsList
	 */
	public Map<String, Object> getOptionsList();

	/**
	 * Refers to an instance bound to given ID of {@link Definition}
	 * 
	 * @param ID
	 *            the ID of specified {@link Definition}
	 */
	public String ref(String ID);

	/**
	 * Refers to an constant instance bound to given name
	 * 
	 * @param constantName
	 *            name of constant
	 */
	public String refConstant(String constantName);

	/**
	 * Refers to an instance bound to mapping type. This method is only used
	 * when binding bean by {@link CoreBinding#bind()} method and its
	 * overloadings. In other cases, using this method is the cause of
	 * unforeseen exceptions.
	 */
	public String refMapping();

	/**
	 * Refers to an instance bound to the given type.
	 * 
	 * @param clazz
	 *            the given type
	 */
	public String refMapping(Class<?> clazz);

	/**
	 * Refers to an instance bound to given mapping name.
	 * 
	 * @param mappingName
	 *            name of specified mapping.
	 */
	public String refMapping(String mappingName);
}
