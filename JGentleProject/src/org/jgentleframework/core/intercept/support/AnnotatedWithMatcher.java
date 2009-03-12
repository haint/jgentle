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
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.context.aop.ClassFilter;
import org.jgentleframework.context.aop.ConstructorFilter;
import org.jgentleframework.context.aop.FieldFilter;
import org.jgentleframework.context.aop.Filter;
import org.jgentleframework.context.aop.MethodFilter;
import org.jgentleframework.context.aop.ParameterFilter;
import org.jgentleframework.context.aop.PointcutOfAll;
import org.jgentleframework.context.aop.StaticallySupportFilter;
import org.jgentleframework.context.aop.support.ClassMatching;
import org.jgentleframework.context.aop.support.FieldMatching;
import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.context.aop.support.ParameterMatching;

/**
 * The Class AnnotatedWithMatcher.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 */
public class AnnotatedWithMatcher extends
		AbstractDefinitionMatcherPointcut<Matching> implements PointcutOfAll {
	/**
	 * @param andor
	 * @param classes
	 */
	public AnnotatedWithMatcher(AND_OR andor,
			Class<? extends Annotation>[] classes) {

		super(andor, classes);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getClassFilter()
	 */
	@Override
	public ClassFilter getClassFilter() {

		return new DefinitionMatcherClassFilter() {
			@Override
			public boolean matches(ClassMatching matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getConstructorFilter()
	 */
	@Override
	public ConstructorFilter getConstructorFilter() {

		/*
		 * Returns a statically constructor filter
		 */
		return new DefinitionMatcherConstructorFilter() {
			@Override
			public boolean matches(
					MethodConstructorMatching<Constructor<?>> matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}

			@Override
			public boolean isRuntime() {

				return false;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getFieldFilter()
	 */
	@Override
	public FieldFilter getFieldFilter() {

		/*
		 * Returns a statically field filter
		 */
		return new DefinitionMatcherFieldFilter() {
			@Override
			public boolean isRuntime() {

				return false;
			}

			@Override
			public boolean matches(FieldMatching matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getMethodFilter()
	 */
	@Override
	public MethodFilter getMethodFilter() {

		/*
		 * Returns a statically method filter
		 */
		return new DefinitionMatcherMethodFilter() {
			@Override
			public boolean matches(MethodConstructorMatching<Method> matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}

			@Override
			public boolean isRuntime() {

				return false;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.PointcutOfParameterFilter#getParameterFilter()
	 */
	@Override
	public ParameterFilter<?> getParameterFilter() {

		/*
		 * Returns a statically parameter filter
		 */
		return new DefinitionMatcherParameterFilter<Object>() {
			@Override
			public boolean matches(ParameterMatching<Object> matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}

			@Override
			public boolean isRuntime() {

				return false;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.Pointcut#getFilter()
	 */
	@Override
	public Filter<Matching> getFilter() {

		return new StaticallySupportFilter<Matching>() {
			@Override
			public boolean matches(Matching matching) {

				return AnnotatedWithMatcher.this.matchesMember(matching);
			}
		};
	}
}
