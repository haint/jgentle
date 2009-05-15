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
package org.jgentleframework.configure;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.aopalliance.intercept.Interceptor;
import org.jgentleframework.configure.annotation.Bean;
import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.configure.objectmeta.InClass;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.aop.support.MatcherPointcut;
import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.data.Pair;

/**
 * Provides some methods in order to configure binding and mapping.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 12, 2009
 * @see BindingConfig
 */
public interface CoreBinding {
	/**
	 * Creates a {@link MatcherPointcut}
	 * 
	 * @param location
	 *            the location is specified {@link Location}
	 * @param andor
	 *            if equals {@link AND_OR#AND}, the returned value of
	 *            {@link Matcher#matches(Object)} of matcher is only <b>true</b>
	 *            when all annotations are annotated, otherwise, if equals
	 *            {@link AND_OR#OR}, the returned value of
	 *            {@link Matcher#matches(Object)} of matcher will be <b>true</b>
	 *            as long as there is at least one annotation is annotated.
	 * @param classes
	 *            the list of object class of annotations
	 */
	public MatcherPointcut<Definition, ? extends Matching> annotatedWith(
			int location, AND_OR andor, Class<?>... classes);

	/**
	 * Creates a {@link MatcherPointcut}.
	 * <p>
	 * This method performance is the same as
	 * {@link #annotatedWith(int, AND_OR, Class...)} according to
	 * <code>AND_OR</code> argument is {@link AND_OR#AND}
	 * 
	 * @param location
	 *            the location is specified {@link Location}
	 * @param clazz
	 *            the object class of {@link Annotation}
	 */
	public MatcherPointcut<Definition, ? extends Matching> annotatedWith(
			int location, Class<?> clazz);

	/**
	 * Creates an attach mapping bound to a type or a set of types
	 * 
	 * @param classes
	 *            a type or a set of types need to be created attach mapping.
	 */
	public ObjectAttach<?> attach(Class<?>... classes);

	/**
	 * Creates an attach mapping bound to a type
	 * 
	 * @param <T>
	 * @param clazz
	 *            a given type
	 */
	public <T> ObjectAttach<T> attach(Class<T> clazz);

	/**
	 * Creates an attach mapping bound to a given name or set of names of
	 * {@link Class}.
	 * 
	 * @param clazzNames
	 *            the given name of class type.
	 */
	public ObjectAttach<?> attach(String... clazzNames);

	/**
	 * Creates an attach mapping constant bound to given unique name.
	 * 
	 * @param names
	 *            the name of constant.
	 */
	public ObjectConstant attachConstant(String... names);

	/**
	 * Creates a binding of bean
	 */
	public InClass bind();

	/**
	 * Creates a binding of bean
	 * 
	 * @param map
	 *            the given {@link Map} containing pairs of key and value which
	 *            represent name of properties and its value need to be
	 *            injected.
	 */
	public InClass bind(Map<String, Object> map);

	/**
	 * Creates a binding of bean
	 * 
	 * @param values
	 *            an array containing pairs of key and value which represent
	 *            name of properties and its value need to be injected.
	 */
	public InClass bind(Object[]... values);

	/**
	 * Creates a binding of bean
	 * 
	 * @param pairs
	 *            an array containing pairs of key and value which represent
	 *            name of properties and its value need to be injected.
	 */
	public InClass bind(Pair<String, Object>... pairs);

	/**
	 * Creates a binding of bean
	 * 
	 * @param properties
	 *            name of properties need to be injected
	 */
	public ObjectBindingConstant bind(String... properties);

	/**
	 * Removes all binding configuration data.
	 */
	public void clearAllBinding();

	/**
	 * Removes all current bean class binding.
	 */
	public void clearBeanClassList();

	/**
	 * Removes all current attach mapping.
	 */
	public void clearObjectAttachList();

	/**
	 * Removes all current object binding constants.
	 */
	public void clearObjectBindingConstantList();

	/**
	 * Removes all current object binding interceptors.
	 */
	public void clearObjectBindingInterceptorList();

	/**
	 * Removes all current object constant binding.
	 */
	public void clearObjectConstantList();

	/**
	 * Creates a binding to an {@link Interceptor} or a group of
	 * {@link Interceptor}.
	 * 
	 * @param matcherPointcuts
	 *            a {@link MatcherPointcut} or a list of matchers of given
	 *            interceptor
	 * @param interceptor
	 *            the interceptor.
	 */
	public void intercept(Object interceptor,
			MatcherPointcut<?, ? extends Matching>... matcherPointcuts);

	/**
	 * Creates a binding to an {@link Interceptor}.
	 * 
	 * @param interceptor
	 *            the given interceptor.
	 * @param matcherPointcut
	 *            a {@link MatcherPointcut} of given {@link Interceptor}
	 */
	public void intercept(Object interceptor,
			MatcherPointcut<?, ? extends Matching> matcherPointcut);

	/**
	 * Creates a mapping bean to a given type or a set of types. These classes
	 * should be annotated with {@link Bean} annotation in order to provides
	 * some additional information to {@link Provider} to instantiate bean. If
	 * be not annotated with {@link Bean}, these info will be
	 * <code>default</code>.
	 * 
	 * @see Bean
	 * @param clazz
	 *            a given type or set of types
	 */
	public void mappingBean(Class<?>... clazz);
}
