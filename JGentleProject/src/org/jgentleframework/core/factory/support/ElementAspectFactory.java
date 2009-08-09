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
package org.jgentleframework.core.factory.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.FieldInterceptor;
import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.context.aop.ClassFilter;
import org.jgentleframework.context.aop.FieldFilter;
import org.jgentleframework.context.aop.MethodFilter;
import org.jgentleframework.context.aop.ParameterFilter;
import org.jgentleframework.core.factory.BasicFieldMatchingAspectPair;
import org.jgentleframework.core.factory.BasicMethodConstructorMatchingAspectPair;
import org.jgentleframework.core.factory.BasicParameterMatchingAspectPair;
import org.jgentleframework.core.factory.BasicTypeMatchingAspectPair;
import org.jgentleframework.core.intercept.MatchingException;
import org.jgentleframework.core.intercept.support.AbstractDefinitionMatcherPointcut;
import org.jgentleframework.core.intercept.support.ConstructorAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.FieldAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.intercept.support.MethodAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.ParameterAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.TypeAnnotatedWithMatcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.Utils;

/**
 * The Class ElementAspectFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 11, 2008
 * @see MethodAnnotatedWithMatcher
 * @see FieldAnnotatedWithMatcher
 * @see ParameterAnnotatedWithMatcher
 * @see TypeAnnotatedWithMatcher
 * @see ConstructorAnnotatedWithMatcher
 * @see AbstractDefinitionMatcherPointcut
 */
class ElementAspectFactory {
	private final Log	log	= LogFactory.getLog(getClass());

	/**
	 * Instantiates a new element aspect factory.
	 */
	public ElementAspectFactory() {

	}

	/**
	 * Analyses method.
	 * 
	 * @param interceptors
	 *            the given array containing all interceptors
	 * @param map
	 *            the given map containing all interceptors according to their
	 *            matchers.
	 * @param definition
	 *            the definition of target class declaring the given method.
	 * @param method
	 *            the given method
	 * @return returns the suitable MethodAspectPair if the interceptors
	 *         according to given method are existed, if not returns
	 *         <code>null</code>.
	 */
	public MethodAspectPair analysesMethod(MethodInterceptor[] interceptors,
			Map<Interceptor, Matcher<Definition>> map, Definition definition,
			final Method method) {

		Assertor.notNull(interceptors, "The interceptor must not be null !!");
		Assertor.notNull(map, "The map must not be null !!");
		Assertor.notNull(definition, "The definition must not be null !!");
		Assertor.notNull(method, "The method must not be null !!");
		MethodAspectPair result = new MethodAspectPair(method);
		List<MethodInterceptor> list = new ArrayList<MethodInterceptor>();
		/*
		 * Search on given interceptor array.
		 */
		for (MethodInterceptor interceptor : interceptors) {
			Matcher<Definition> matcher = map.get(interceptor);
			if (matcher != null && matcher.matches(definition)) {
				if (!Utils.validatesInterceptConditioner(matcher, definition,
						interceptor)) {
					continue;
				}
				else {
					TypeAnnotatedWithMatcher pointcutType = (ReflectUtils
							.isCast(TypeAnnotatedWithMatcher.class, matcher) ? (TypeAnnotatedWithMatcher) matcher
							: null);
					MethodAnnotatedWithMatcher pointcutMethod = (ReflectUtils
							.isCast(MethodAnnotatedWithMatcher.class, matcher) ? (MethodAnnotatedWithMatcher) matcher
							: null);
					ParameterAnnotatedWithMatcher pointcutPar = (ReflectUtils
							.isCast(ParameterAnnotatedWithMatcher.class,
									matcher) ? (ParameterAnnotatedWithMatcher) matcher
							: null);
					FieldAnnotatedWithMatcher pointcutField = (ReflectUtils
							.isCast(FieldAnnotatedWithMatcher.class, matcher) ? (FieldAnnotatedWithMatcher) matcher
							: null);
					if (pointcutType != null) {
						ClassFilter classFilter = pointcutType.getClassFilter();
						if (classFilter != null
								&& classFilter
										.matches(new BasicTypeMatchingAspectPair(
												method.getDeclaringClass(),
												method, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else if (pointcutMethod != null) {
						MethodFilter methodFilter = pointcutMethod
								.getMethodFilter();
						if (methodFilter != null
								&& methodFilter
										.matches(new BasicMethodConstructorMatchingAspectPair(
												method, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else if (pointcutPar != null) {
						for (int i = 0; i < method.getParameterTypes().length; i++) {
							ParameterFilter<Method> parameterFilter = pointcutPar
									.getParameterFilter();
							if (parameterFilter != null
									&& parameterFilter
											.matches(new BasicParameterMatchingAspectPair(
													method, i))
									&& list.add(interceptor)) {
								list.add(interceptor);
								break;
							}
						}
					}
					else if (pointcutField != null) {
						FieldFilter fieldFilter = pointcutField
								.getFieldFilter();
						if (fieldFilter != null
								&& fieldFilter
										.matches(new BasicFieldMatchingAspectPair(
												null, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else {
						if (log.isFatalEnabled())
							log
									.fatal(
											"Could not intercept the given interceptor !!"
													+ " The declared matcher is not supported !",
											new MatchingException());
					}
				}
			}
		}
		if (list.size() == 0)
			return result;
		else {
			// creates aspect pair
			result.addAll(list);
		}
		return result;
	}

	/**
	 * Analyses field.
	 * 
	 * @param interceptors
	 *            the given array containing all interceptors
	 * @param map
	 *            the given map containing the interceptors according to their
	 *            matchers.
	 * @param definition
	 *            the definition of target class declaring the given field.
	 * @param field
	 *            the given field
	 * @return returns the corresponding FieldAspectPair if the interceptors
	 *         according to the given field are existed, if not, returns
	 *         <code>null</code>.
	 */
	public FieldAspectPair analysesField(FieldInterceptor[] interceptors,
			Map<Interceptor, Matcher<Definition>> map, Definition definition,
			final Field field) {

		Assertor.notNull(interceptors,
				"The given interceptor must not be null !!");
		Assertor.notNull(map, "The given map must not be null !!");
		Assertor
				.notNull(definition, "The given definition must not be null !!");
		Assertor.notNull(field, "The given field must not be null !!");
		FieldAspectPair result = new FieldAspectPair(field);
		List<FieldInterceptor> list = new ArrayList<FieldInterceptor>();
		/*
		 * Search on given interceptor array.
		 */
		for (FieldInterceptor interceptor : interceptors) {
			Matcher<Definition> matcher = map.get(interceptor);
			if (matcher != null && matcher.matches(definition)) {
				if (!Utils.validatesInterceptConditioner(matcher, definition,
						interceptor)) {
					continue;
				}
				else {
					FieldAnnotatedWithMatcher pointcutField = (ReflectUtils
							.isCast(FieldAnnotatedWithMatcher.class, matcher) ? (FieldAnnotatedWithMatcher) matcher
							: null);
					TypeAnnotatedWithMatcher pointcutType = (ReflectUtils
							.isCast(TypeAnnotatedWithMatcher.class, matcher) ? (TypeAnnotatedWithMatcher) matcher
							: null);
					MethodAnnotatedWithMatcher pointcutMethod = (ReflectUtils
							.isCast(MethodAnnotatedWithMatcher.class, matcher) ? (MethodAnnotatedWithMatcher) matcher
							: null);
					if (pointcutField != null) {
						FieldFilter fieldFilter = pointcutField
								.getFieldFilter();
						if (fieldFilter != null
								&& fieldFilter
										.matches(new BasicFieldMatchingAspectPair(
												field, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else if (pointcutType != null) {
						ClassFilter classFilter = pointcutType.getClassFilter();
						if (classFilter != null
								&& classFilter
										.matches(new BasicTypeMatchingAspectPair(
												field.getDeclaringClass(),
												field, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else if (pointcutMethod != null) {
						MethodFilter methodFilter = pointcutMethod
								.getMethodFilter();
						if (methodFilter != null
								&& methodFilter
										.matches(new BasicMethodConstructorMatchingAspectPair(
												null, definition))
								&& !list.contains(interceptor)) {
							list.add(interceptor);
						}
					}
					else {
						if (log.isFatalEnabled())
							log
									.fatal(
											"Could not intercept the given interceptor !! The declared matcher is not supported !",
											new MatchingException());
					}
				}
			}
		}
		if (list.size() == 0)
			return result;
		else
			result.addAll(list);
		return result;
	}
}
