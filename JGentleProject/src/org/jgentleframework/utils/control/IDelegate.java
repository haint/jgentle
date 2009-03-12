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
package org.jgentleframework.utils.control;

/**
 * Interface implemnted by the object returned by Delegator.bind Use this only
 * when no suitable alternative interface is available
 * 
 * @author Steve Lewis Date: May 9, 2002
 */
public interface IDelegate {
	/**
	 * Thin wrapper in invoke
	 * 
	 * @param args
	 *            possibly null array or args - null says none
	 * @return possibly null return - primitive types are wrapped
	 */
	public Object invoke(Object[] args);

	/**
	 * convenience call for 1 arg case
	 * 
	 * @param arg
	 *            possibly null argument
	 * @return possibly null return - primitive types are wrapped
	 */
	public Object invoke(Object arg);

	/**
	 * convenience call for 2 arg case
	 * 
	 * @param arg1
	 *            possibly null argument
	 * @param arg2
	 *            possibly null argument
	 * @return possibly null return - primitive types are wrapped
	 */
	public Object invoke(Object arg1, Object arg2);

	/**
	 * convenience call for no arg case
	 * 
	 * @return possibly null return - primitive types are wrapped
	 */
	public Object invoke();
}
