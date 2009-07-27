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
package org.jgentleframework.core.intercept;

import org.aopalliance.intercept.Joinpoint;
import org.jgentleframework.core.reflection.metadata.MetadataControl;

/**
 * The Interface ObjectInstantiation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 29, 2008
 * @see Joinpoint
 * @see MetadataControl
 */
public interface ObjectInstantiation extends Joinpoint, MetadataControl {
	/**
	 * Returns target class object is used to instantiate instance.
	 */
	public Class<?> getTargetClass();

	/**
	 * Returns the previous result
	 */
	public Object getPreviousResult();

	/**
	 * Sets the previous result.
	 * 
	 * @param previousResult
	 *            the previous result
	 */
	public void setPreviousResult(Object previousResult);

	/**
	 * Returns an array containing object classes represents interfaces are used
	 * to instantiate instance.
	 */
	public Class<?>[] getInterfaces();

	/**
	 * Returns an array containing the class objects in order to identify
	 * constructor's formal parameter types.
	 */
	public Class<?>[] argTypes();

	/**
	 * Returns the arguments used for the suitable constructor call which has
	 * given argument types.
	 */
	public Object[] args();

	/**
	 * Gets the requestor.
	 * 
	 * @return the requestor
	 */
	public Object getRequestor();
}
