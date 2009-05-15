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
package org.jgentleframework.core.factory;

import java.util.Collection;

import org.jgentleframework.context.beans.BeanPostInstantiation;

/**
 * The Interface BeanPostInstantiationSupportInterface.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 7, 2008
 */
public interface BeanPostInstantiationSupportInterface {
	/**
	 * Appends the specified {@link BeanPostInstantiation} to the end of this
	 * processor's managed list.
	 * 
	 * @param instance
	 *            {@link BeanPostInstantiation} to be appended to this list
	 * @return <b>true</b> if the processor's managed list changed as a result
	 *         of the call
	 */
	public boolean addBeanPostInstantiation(Object instance);

	/**
	 * Inserts the specified element at the specified position in this
	 * processor's managed list.
	 * 
	 * @param index
	 *            index at which the specified {@link BeanPostInstantiation} is
	 *            to be inserted
	 * @param instance
	 *            {@link BeanPostInstantiation} instance to be inserted
	 */
	public void addBeanPostInstantiation(int index, Object instance);

	/**
	 * Appends all of the {@link BeanPostInstantiation}s in the specified
	 * collection to the end of this processor's managed list.
	 * 
	 * @param collection
	 *            collection containing {@link BeanPostInstantiation}s to be
	 *            added
	 * @return <b>true</b> if the processor's managed list changed as a result
	 *         of the call
	 */
	public boolean addBeanPostInstantiation(Collection<Object> collection);

	/**
	 * Inserts all of the {@link BeanPostInstantiation}s in the specified
	 * collection into this processor's managed list at the specified position.
	 * 
	 * @param index
	 *            index at which to insert the first element from the specified
	 *            collection
	 * @param collection
	 *            collection containing {@link BeanPostInstantiation}s to be
	 *            added to list
	 * @return <b>true</b> if the processor's managed list changed as a result
	 *         of the call
	 */
	public boolean addBeanPostInstantiation(int index,
			Collection<Object> collection);

	/**
	 * Removes the specified {@link BeanPostInstantiation} at the specified
	 * position.
	 * 
	 * @param index
	 *            the index of the {@link BeanPostInstantiation} to be removed
	 * @return returns the specified {@link BeanPostInstantiation} was removed.
	 */
	public Object removeBeanPostInstantiation(int index);

	/**
	 * Removes the specified {@link BeanPostInstantiation}.
	 * 
	 * @param obj
	 *            the instance of {@link BeanPostInstantiation} to be removed,
	 *            if present.
	 * @return <b>true</b> if this list contained the specified
	 *         {@link BeanPostInstantiation}
	 */
	public boolean removeBeanPostInstantiation(Object obj);
}