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
package org.jgentleframework.core.intercept.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.context.aop.Filter;
import org.jgentleframework.context.aop.ParameterFilter;
import org.jgentleframework.context.aop.PointcutOfParameterFilter;
import org.jgentleframework.context.aop.support.AbstractMatching;
import org.jgentleframework.context.aop.support.ParameterMatching;

/**
 * The Class ParameterAnnotatedWithMatcher.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 * @see AbstractDefinitionMatcherPointcut
 * @see ParameterMatching
 * @see PointcutOfParameterFilter
 * @see AbstractMatching
 */
public class ParameterAnnotatedWithMatcher extends
		AbstractDefinitionMatcherPointcut<ParameterMatching<Method>> implements
		PointcutOfParameterFilter<ParameterMatching<Method>> {
	ParameterFilter<Method>	filter	= new DefinitionMatcherParameterFilter<Method>() {
										@Override
										public boolean matches(
												ParameterMatching<Method> matching) {

											return ParameterAnnotatedWithMatcher.this
													.matchesMember(matching);
										}

										@Override
										public boolean isRuntime() {

											return false;
										}
									};

	/**
	 * The Constructor.
	 * 
	 * @param andor
	 *            the andor
	 * @param classes
	 *            the classes
	 */
	public ParameterAnnotatedWithMatcher(AND_OR andor,
			Class<? extends Annotation>[] classes) {

		super(andor, classes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.PointcutOfParameterFilter#getParameterFilter()
	 */
	@Override
	public ParameterFilter<Method> getParameterFilter() {

		/*
		 * Returns a statically parameter filter
		 */
		return this.filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getFilter()
	 */
	@Override
	public Filter<ParameterMatching<Method>> getFilter() {

		/*
		 * Returns a statically parameter filter
		 */
		return this.filter;
	}
}