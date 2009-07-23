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
package org.jgentleframework.context.support;

import java.util.Map;

import org.aopalliance.intercept.FieldInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.core.intercept.InstantiationInterceptor;
import org.jgentleframework.core.intercept.ObjectInstantiation;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Interface InstantiationSelector is responsible for holding all neccessary
 * data in order to instantiate bean instance.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 29, 2008
 * @see InstantiationInterceptor
 * @see Interceptor
 * @see ObjectInstantiation
 */
public interface InstantiationSelector extends CoreInstantiationSelector {
	/**
	 * Returns an array containing all interceptors present at
	 * 
	 * @return the interceptors
	 */
	public Interceptor[] getInterceptors();

	/**
	 * Returns an array containing all {@link InstantiationInterceptor}s present
	 * at.
	 */
	public InstantiationInterceptor[] getInstantiationInterceptors();

	/**
	 * Returns an array containing all {@link MethodInterceptor} present at.
	 */
	public MethodInterceptor[] getMethodInterceptors();

	/**
	 * Gets the field interceptors.
	 * 
	 * @return the fieldInterceptors
	 */
	public FieldInterceptor[] getFieldInterceptors();

	/**
	 * Gets the map matcher interceptor.
	 * 
	 * @return the mapMatcherInterceptor
	 */
	public Map<Interceptor, Matcher<Definition>> getMapMatcherInterceptor();

	/**
	 * Sets the map matcher interceptor.
	 * 
	 * @param mapMatcherInterceptor
	 *            the mapMatcherInterceptor to set
	 */
	public void setMapMatcherInterceptor(
			Map<Interceptor, Matcher<Definition>> mapMatcherInterceptor);
}