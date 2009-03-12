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
package org.jgentleframework.core.intercept.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.context.aop.Filter;
import org.jgentleframework.context.aop.MethodFilter;
import org.jgentleframework.context.aop.PointcutOfMethodFilter;
import org.jgentleframework.context.aop.support.MethodConstructorMatching;

/**
 * The Class MethodAnnotatedWithMatcher.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 * @see AbstractDefinitionMatcherPointcut
 * @see PointcutOfMethodFilter
 */
public class MethodAnnotatedWithMatcher extends
		AbstractDefinitionMatcherPointcut<MethodConstructorMatching<Method>>
		implements PointcutOfMethodFilter<MethodConstructorMatching<Method>> {
	MethodFilter	filter	= new DefinitionMatcherMethodFilter() {
								@Override
								public boolean matches(
										MethodConstructorMatching<Method> matching) {

									return MethodAnnotatedWithMatcher.this
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
	public MethodAnnotatedWithMatcher(AND_OR andor,
			Class<? extends Annotation>[] classes) {

		super(andor, classes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.PointcutOfMethodFilter#getMethodFilter()
	 */
	@Override
	public MethodFilter getMethodFilter() {

		/*
		 * Returns a statically method filter
		 */
		return this.filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getFilter()
	 */
	@Override
	public Filter<MethodConstructorMatching<Method>> getFilter() {

		/*
		 * Returns a statically method filter
		 */
		return this.filter;
	}
}
