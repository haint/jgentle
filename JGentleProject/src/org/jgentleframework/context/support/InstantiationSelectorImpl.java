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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.FieldInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.jgentleframework.core.intercept.InstantiationInterceptor;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an implementation of {@link InstantiationSelector} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 17, 2008
 * @see InstantiationSelector
 */
public class InstantiationSelectorImpl extends CoreInstantiationSelectorImpl
		implements InstantiationSelector {
	/**
	 * The Constructor.
	 * 
	 * @param type
	 *            the type
	 * @param targetClass
	 *            the target class
	 * @param mappingName
	 *            the mapping name
	 * @param definition
	 *            the definition
	 */
	public InstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition) {

		super(type, targetClass, mappingName, definition);
	}

	/**
	 * The Constructor.
	 * 
	 * @param type
	 *            the clazz
	 * @param targetClass
	 *            the class instance
	 * @param mappingName
	 *            the mapping name
	 * @param definition
	 *            the definition
	 * @param mapMatcherInterceptor
	 *            the map matcher interceptor
	 */
	public InstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition,
			Map<Interceptor, Matcher<Definition>> mapMatcherInterceptor) {

		super(type, targetClass, mappingName, definition);
		this.mapMatcherInterceptor = mapMatcherInterceptor;
	}

	/**
	 * The Constructor.
	 * 
	 * @param type
	 *            the type
	 * @param targetClass
	 *            the target class
	 * @param mappingName
	 *            the mapping name
	 * @param definition
	 *            the definition
	 * @param argTypes
	 *            the arg types
	 * @param args
	 *            the args
	 * @param mapMatcherInterceptor
	 *            the map matcher interceptor
	 */
	public InstantiationSelectorImpl(Class<?> type, Class<?> targetClass,
			String mappingName, Definition definition, Class<?>[] argTypes,
			Object[] args,
			HashMap<Interceptor, Matcher<Definition>> mapMatcherInterceptor) {

		super(type, targetClass, mappingName, argTypes, args, definition);
		this.mapMatcherInterceptor = mapMatcherInterceptor;
	}

	/** The map matcher interceptor. */
	Map<Interceptor, Matcher<Definition>>	mapMatcherInterceptor	= null;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.InstantiationSelector#getInterceptors
	 * ()
	 */
	@Override
	public Interceptor[] getInterceptors() {

		if (this.mapMatcherInterceptor == null)
			return null;
		return this.mapMatcherInterceptor.keySet().toArray(
				new Interceptor[this.mapMatcherInterceptor.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.InstantiationSelector#
	 * getInstantiationInterceptors()
	 */
	@Override
	public InstantiationInterceptor[] getInstantiationInterceptors() {

		if (this.mapMatcherInterceptor == null)
			return null;
		List<InstantiationInterceptor> result = new ArrayList<InstantiationInterceptor>();
		for (Interceptor icpt : this.mapMatcherInterceptor.keySet()) {
			if (ReflectUtils.isCast(InstantiationInterceptor.class, icpt)) {
				if (!result.contains(icpt))
					result.add((InstantiationInterceptor) icpt);
			}
		}
		return result.toArray(new InstantiationInterceptor[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.InstantiationSelector#
	 * getMethodInterceptors()
	 */
	@Override
	public MethodInterceptor[] getMethodInterceptors() {

		if (this.mapMatcherInterceptor == null)
			return null;
		List<MethodInterceptor> result = new ArrayList<MethodInterceptor>();
		for (Interceptor icpt : this.mapMatcherInterceptor.keySet()) {
			if (ReflectUtils.isCast(MethodInterceptor.class, icpt)) {
				if (!result.contains(icpt))
					result.add((MethodInterceptor) icpt);
			}
		}
		return result.toArray(new MethodInterceptor[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.InstantiationSelector#
	 * getFieldInterceptors()
	 */
	@Override
	public FieldInterceptor[] getFieldInterceptors() {

		if (this.mapMatcherInterceptor == null)
			return null;
		List<FieldInterceptor> result = new ArrayList<FieldInterceptor>();
		for (Interceptor icpt : this.mapMatcherInterceptor.keySet()) {
			if (ReflectUtils.isCast(FieldInterceptor.class, icpt)) {
				if (!result.contains(icpt))
					result.add((FieldInterceptor) icpt);
			}
		}
		return result.toArray(new FieldInterceptor[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.InstantiationSelector#
	 * getMapMatcherInterceptor()
	 */
	@Override
	public Map<Interceptor, Matcher<Definition>> getMapMatcherInterceptor() {

		return mapMatcherInterceptor;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.InstantiationSelector#
	 * setMapMatcherInterceptor(java.util.Map)
	 */
	@Override
	public void setMapMatcherInterceptor(
			Map<Interceptor, Matcher<Definition>> mapMatcherInterceptor) {

		Assertor.notNull(mapMatcherInterceptor);
		this.mapMatcherInterceptor = mapMatcherInterceptor;
	}
}
