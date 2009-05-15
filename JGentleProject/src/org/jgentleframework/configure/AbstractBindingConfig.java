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
import java.util.ArrayList;
import java.util.Map;

import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.configure.objectmeta.Binder;
import org.jgentleframework.configure.objectmeta.InClass;
import org.jgentleframework.configure.objectmeta.InClassImpl;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.aop.support.MatcherPointcut;
import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.core.intercept.support.AnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.ConstructorAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.FieldAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.intercept.support.MethodAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.ParameterAnnotatedWithMatcher;
import org.jgentleframework.core.intercept.support.TypeAnnotatedWithMatcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.data.Pair;

/**
 * The Class AbstractBindingConfig.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 11, 2009
 */
public abstract class AbstractBindingConfig extends AbstractConfigModule
		implements CoreBinding {
	/** The bean class list. */
	protected ArrayList<Class<?>>					beanClassList				= null;

	/** The object binding constant list. */
	protected ArrayList<ObjectBindingConstant>		objBindingConstantList		= null;

	/** The object binding interceptor list. */
	protected ArrayList<ObjectBindingInterceptor>	objBindingInterceptorList	= null;

	/** The object attach list. */
	protected ArrayList<ObjectAttach<?>>			objectAttachList			= null;

	/** The object constant list. */
	protected ArrayList<ObjectConstant>				objectConstantList			= null;

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#annotatedWith(int,
	 * org.jgentleframework.configure.enums.AND_OR, java.lang.Class<?>[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MatcherPointcut<Definition, ? extends Matching> annotatedWith(
			int location, AND_OR andor, Class<?>... classes) {

		Assertor.notNull(andor);
		MatcherPointcut<Definition, ? extends Matching> result = null;
		if (classes == null || classes.length == 0) {
			throw new IllegalArgumentException(
					"The list of object classes of annotations must not be null or empty");
		}
		try {
			switch (location) {
			case Location.CONSTRUCTOR:
				result = new ConstructorAnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			case Location.FIELD:
				result = new FieldAnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			case Location.METHOD:
				result = new MethodAnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			case Location.PARAMETER:
				result = new ParameterAnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			case Location.TYPE:
				result = new TypeAnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			case Location.ALL:
				result = new AnnotatedWithMatcher(andor,
						(Class<? extends Annotation>[]) classes);
				break;
			default:
				throw new IllegalArgumentException("invalid location !!");
			}
		}
		catch (ClassCastException e) {
			throw new ClassCastException(
					"The object class of annotation is not an extension of '"
							+ Annotation.class.toString() + "'");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#annotatedWith(int,
	 * java.lang.Class)
	 */
	@Override
	public MatcherPointcut<Definition, ? extends Matching> annotatedWith(
			int location, Class<?> clazz) {

		return annotatedWith(location, AND_OR.AND, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#attach(java.lang.Class<?>[])
	 */
	@Override
	public ObjectAttach<?> attach(Class<?>... classes) {

		Assertor
				.notNull(classes,
						"[Assertion failed] - These binding classes must not be null !");
		Assertor.notEmpty(classes);
		ObjectAttach<?> result = Binder.createObjectAttach(classes);
		this.objectAttachList.add(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#attach(java.lang.Class)
	 */
	@Override
	public <T> ObjectAttach<T> attach(Class<T> clazz) {

		Assertor
				.notNull(clazz,
						"[Assertion failed] - These binding classes must not be null !");
		ObjectAttach<T> result = Binder.createObjectAttach(clazz);
		this.objectAttachList.add(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#attach(java.lang.String[])
	 */
	@Override
	public ObjectAttach<?> attach(String... clazzNames) {

		Assertor
				.notNull(clazzNames,
						"[Assertion failed] - this 'clazzNames' argument must not be null !");
		Assertor.notEmpty(clazzNames);
		Class<?>[] classes = new Class<?>[clazzNames.length];
		for (int i = 0; i < clazzNames.length; i++) {
			try {
				Class<?> clazz = Class.forName(clazzNames[i]);
				classes[i] = clazz;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		ObjectAttach<?> result = Binder.createObjectAttach(classes);
		this.objectAttachList.add(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#attachConstant(java.lang.String
	 * [])
	 */
	@Override
	public ObjectConstant attachConstant(String... names) {

		Assertor.notNull(names,
				"[Assertion failed] - this names argument must not be null !");
		Assertor.notEmpty(names);
		ObjectConstant result = Binder.createObjectConstant(names);
		this.objectConstantList.add(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#bind()
	 */
	@Override
	public InClass bind() {

		ObjectBindingConstant obc = Binder.createObjectBindingConstant();
		this.objBindingConstantList.add(obc);
		InClass result = new InClassImpl(obc);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#bind(java.util.Map)
	 */
	@Override
	public InClass bind(Map<String, Object> map) {

		Assertor.notNull(map,
				"[Assertion failed] - The given map must not be null !");
		ObjectBindingConstant obc = Binder.createObjectBindingConstant(map);
		this.objBindingConstantList.add(obc);
		return new InClassImpl(obc);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#bind(java.lang.Object[][])
	 */
	@Override
	public InClass bind(Object[]... values) {

		Assertor
				.notNull(values,
						"[Assertion failed] - The given pair values must not be null !");
		ObjectBindingConstant obc = Binder.createObjectBindingConstant(values);
		this.objBindingConstantList.add(obc);
		return new InClassImpl(obc);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#bind(org.jgentleframework.
	 * utils.data.Pair<java.lang.String,java.lang.Object>[])
	 */
	@Override
	public InClass bind(Pair<String, Object>... pairs) {

		ObjectBindingConstant obc = Binder.createObjectBindingConstant(pairs);
		this.objBindingConstantList.add(obc);
		InClass result = new InClassImpl(obc);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#bind(java.lang.String[])
	 */
	@Override
	public ObjectBindingConstant bind(String... properties) {

		ObjectBindingConstant result = Binder
				.createObjectBindingConstant(properties);
		this.objBindingConstantList.add(result);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#intercept(java.lang.Object,
	 * org.jgentleframework.context.aop.support.MatcherPointcut<?,? extends
	 * org.jgentleframework.context.aop.support.Matching>[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Object interceptor,
			MatcherPointcut<?, ? extends Matching>... matcherPointcuts) {

		Assertor.notNull(matcherPointcuts,
				"The matchers argument must not be null !");
		try {
			this.objBindingInterceptorList.add(Binder
					.createObjectBindingInterceptor(interceptor,
							(Matcher<Definition>[]) matcherPointcuts));
		}
		catch (ClassCastException e) {
			throw new ClassCastException(
					"The matcher must be 'Matcher<Definition>'");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#intercept(java.lang.Object,
	 * org.jgentleframework.context.aop.support.MatcherPointcut)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void intercept(Object interceptor,
			MatcherPointcut<?, ? extends Matching> matcherPointcut) {

		Assertor.notNull(matcherPointcut,
				"The matchers argument must not be null !");
		try {
			this.objBindingInterceptorList.add(Binder
					.createObjectBindingInterceptor(interceptor,
							(Matcher<Definition>) matcherPointcut));
		}
		catch (ClassCastException e) {
			throw new ClassCastException(
					"The matcher must be 'Matcher<Definition>'");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#mappingBean(java.lang.Class
	 * <?>[])
	 */
	@Override
	public void mappingBean(Class<?>... clazz) {

		for (Class<?> objClass : clazz) {
			if (!this.beanClassList.contains(objClass)) {
				this.beanClassList.add(objClass);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#clearAllBinding()
	 */
	@Override
	public synchronized void clearAllBinding() {

		clearObjectAttachList();
		clearBeanClassList();
		clearObjectBindingConstantList();
		clearObjectBindingInterceptorList();
		clearObjectConstantList();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#clearObjectAttachList
	 * ()
	 */
	@Override
	public void clearObjectAttachList() {

		this.objectAttachList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#clearBeanClassList()
	 */
	@Override
	public void clearBeanClassList() {

		this.beanClassList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#clearObjectBindingConstantList
	 * ()
	 */
	@Override
	public void clearObjectBindingConstantList() {

		this.objBindingConstantList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.CoreBinding#clearObjectBindingInterceptorList
	 * ()
	 */
	@Override
	public void clearObjectBindingInterceptorList() {

		this.objBindingInterceptorList.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.CoreBinding#clearObjectConstantList()
	 */
	@Override
	public void clearObjectConstantList() {

		this.objectConstantList.clear();
	}
}
