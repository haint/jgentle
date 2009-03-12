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

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jgentleframework.utils.Assertor;

/**
 * Represents the Block instance.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 18, 2008
 */
public class ObjectBlock {
	/** The block list. */
	ArrayList<Class<?>>	blockList	= null;
	/** The method. */
	Method				method		= null;

	/**
	 * Instantiates a new object block.
	 * 
	 * @param method
	 *            the method
	 * @param interfazes
	 *            the interfazes
	 */
	public ObjectBlock(Method method, Class<?>... interfazes) {

		Assertor.notNull(interfazes);
		Assertor.notNull(method);
		this.method = method;
		this.blockList = new ArrayList<Class<?>>();
		for (Class<?> interfaze : interfazes) {
			if (!this.blockList.contains(interfaze)) {
				this.blockList.add(interfaze);
			}
		}
	}

	/**
	 * Gets the block list.
	 * 
	 * @return the blockList
	 */
	public ArrayList<Class<?>> getBlockList() {

		return blockList;
	}

	/**
	 * Gets the method.
	 * 
	 * @return the method
	 */
	public Method getMethod() {

		return method;
	}

	/**
	 * Sets the block list.
	 * 
	 * @param blockList
	 *            the blockList to set
	 */
	public void setBlockList(ArrayList<Class<?>> blockList) {

		this.blockList = blockList;
	}

	/**
	 * Sets the method.
	 * 
	 * @param method
	 *            the method to set
	 */
	public void setMethod(Method method) {

		this.method = method;
	}
}
