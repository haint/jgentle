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
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.jgentleframework.configure.enums.AND_OR;
import org.jgentleframework.configure.enums.MetadataKey;
import org.jgentleframework.context.aop.ClassFilter;
import org.jgentleframework.context.aop.ConstructorFilter;
import org.jgentleframework.context.aop.FieldFilter;
import org.jgentleframework.context.aop.MethodFilter;
import org.jgentleframework.context.aop.ParameterFilter;
import org.jgentleframework.context.aop.Pointcut;
import org.jgentleframework.context.aop.support.ClassMatching;
import org.jgentleframework.context.aop.support.FieldMatching;
import org.jgentleframework.context.aop.support.MatcherPointcut;
import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.context.aop.support.ParameterMatching;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class AbstractDefinitionMatcherPointcut.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 2, 2008
 * @see Matcher
 * @see AbstractMatcher
 * @see MatcherPointcut
 * @see Pointcut
 * @see Definition
 * @see AbstractDefinitionMatcher
 */
public abstract class AbstractDefinitionMatcherPointcut<T extends Matching>
		extends AbstractDefinitionMatcher implements
		MatcherPointcut<Definition, T> {
	/** The andor. */
	final AND_OR						andor;

	/** The annotation list. */
	final Class<? extends Annotation>[]	annotationList;

	/** The Constant thisMatcher. */
	public final static int				thisMatcher	= 1;

	/** The Constant classFilter. */
	public final static int				typeFilter	= 2;

	/**
	 * Instantiates a new annotated with matcher.
	 * 
	 * @param andor
	 *            the andor
	 * @param classes
	 *            the classes
	 */
	public AbstractDefinitionMatcherPointcut(AND_OR andor,
			Class<? extends Annotation>... classes) {

		Assertor.notNull(andor);
		if (classes == null || (classes != null && classes.length == 0)) {
			throw new RuntimeException(
					"Annotation list need to instantiate Matcher is invalid !");
		}
		this.annotationList = classes.clone();
		this.andor = andor;
	}

	/**
	 * Checking def condition.
	 * 
	 * @param definition
	 *            the definition
	 * @param clazz
	 *            the clazz
	 * @param requestor
	 *            the requestor
	 * @return true, if checking def condition
	 */
	protected boolean checkingDefCondition(Definition definition,
			Class<? extends Annotation> clazz, int requestor) {

		Assertor.notNull(definition);
		switch (requestor) {
		case thisMatcher:
			if (ReflectUtils.isCast(AnnotatedWithMatcher.class, this)) {
				return definition.isAnnotationPresentAtAnyWhere(clazz);
			}
			else if (ReflectUtils.isCast(ConstructorAnnotatedWithMatcher.class,
					this)) {
				return definition.isAnnotationPresentAtAnyConstructors(clazz);
			}
			else if (ReflectUtils.isCast(FieldAnnotatedWithMatcher.class, this)) {
				return definition.isAnnotationPresentAtAnyFields(clazz);
			}
			else if (ReflectUtils
					.isCast(MethodAnnotatedWithMatcher.class, this)) {
				return definition.isAnnotationPresentAtAnyMethods(clazz);
			}
			else if (ReflectUtils.isCast(ParameterAnnotatedWithMatcher.class,
					this)) {
				return definition.isAnnotationPresentAtAnyParameters(clazz);
			}
			else if (ReflectUtils.isCast(TypeAnnotatedWithMatcher.class, this)) {
				return definition.isAnnotationPresent(clazz);
			}
			else {
				throw new IllegalArgumentException("Dont know the requestor !");
			}
		case typeFilter:
			return definition.isAnnotationPresent(clazz);
		default:
			throw new IllegalArgumentException("Dont know the requestor !");
		}
	}

	/**
	 * Returns an array containing all object classes of current annotations are
	 * present at this {@link Matcher}.
	 * 
	 * @return the annotation list
	 */
	public Class<? extends Annotation>[] getAnnotationList() {

		return annotationList != null ? annotationList.clone() : null;
	}

	/**
	 * Matches member.
	 * 
	 * @param memberMatching
	 *            the member matching
	 * @return true, if matches member
	 */
	protected boolean matchesMember(Matching memberMatching) {

		Definition current = (Definition) memberMatching.getMetadata(
				MetadataKey.DEFINITION).getValue();
		Class<?> targetClass;
		if (current == null)
			throw new InterceptionException(
					"The definition metadata or specified element of matching is null !");
		if (current.getKey() != null)
			targetClass = (Class<?>) current.getKey();
		else
			throw new InterceptionException(
					"Key of current definition is null !! ");
		if (!current.isInterpretedOfClass())
			return false;
		if (ReflectUtils.isCast(ClassMatching.class, memberMatching)) {
			if (targetClass != ((ClassMatching) memberMatching)
					.getTargetClass())
				return false;
		}
		else {
			throw new InterceptionException("Invalid member matching!");
		}
		Object element = null;
		int parameterIndex = -1;
		if (ReflectUtils.isCast(FieldMatching.class, memberMatching)) {
			element = ((FieldMatching) memberMatching).getField();
		}
		else if (ReflectUtils.isCast(MethodConstructorMatching.class,
				memberMatching)) {
			element = ((MethodConstructorMatching<?>) memberMatching)
					.getElement();
		}
		else if (ReflectUtils.isCast(ParameterMatching.class, memberMatching)) {
			element = ((ParameterMatching<?>) memberMatching).getElement();
			parameterIndex = ((ParameterMatching<?>) memberMatching).getIndex();
		}
		else {
			element = targetClass;
		}
		if (current != null && element != null) {
			Definition def = element != targetClass ? current
					.getMemberDefinition(element) : current;
			if (parameterIndex != -1
					&& ReflectUtils.isCast(ParameterMatching.class,
							memberMatching)) {
				ParameterMatching<?> pm = (ParameterMatching<?>) memberMatching;
				Definition pmDef = def.getParameterDefList()[parameterIndex];
				if (pm.getElement() != def.getKey()) {
					return false;
				}
				def = pmDef;
			}
			return this.matches(def, typeFilter);
		}
		else {
			throw new InterceptionException(
					"The definition metadata or specified element of matching is null !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.intercept.support.AnnotatedWithMatcher#matches
	 * (org.jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public boolean matches(Definition definition) {

		return matches(definition, thisMatcher);
	}

	/**
	 * Matches.
	 * 
	 * @param definition
	 *            the definition
	 * @param requestor
	 *            the requestor
	 * @return true, if successful
	 */
	protected boolean matches(Definition definition, int requestor) {

		boolean result = true;
		for (Class<? extends Annotation> clazz : annotationList) {
			if (andor.equals(AND_OR.OR)) {
				if (checkingDefCondition(definition, clazz, requestor)) {
					return true;
				}
				else
					result = false;
			}
			else if (andor.equals(AND_OR.AND)) {
				if (!checkingDefCondition(definition, clazz, requestor)) {
					return false;
				}
				else
					result = true;
			}
			else {
				throw new InterceptionException();
			}
		}
		return result;
	}

	/**
	 * The Class DefinitionMatcherClassFilter.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Aug 17, 2008
	 */
	public abstract class DefinitionMatcherClassFilter extends
			AbstractMatcher<ClassMatching> implements ClassFilter {
	}

	/**
	 * The Class DefinitionMatcherMethodFilter.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Aug 17, 2008
	 */
	public abstract class DefinitionMatcherMethodFilter extends
			AbstractMatcher<MethodConstructorMatching<Method>> implements
			MethodFilter {
	}

	/**
	 * The Class DefinitionMatcherFieldFilter.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Aug 17, 2008
	 */
	public abstract class DefinitionMatcherFieldFilter extends
			AbstractMatcher<FieldMatching> implements FieldFilter {
	}

	/**
	 * The Class DefinitionMatcherConstructorFilter.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Aug 17, 2008
	 */
	public abstract class DefinitionMatcherConstructorFilter extends
			AbstractMatcher<MethodConstructorMatching<Constructor<?>>>
			implements ConstructorFilter {
	}

	/**
	 * The Class DefinitionMatcherParameterFilter.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Aug 17, 2008
	 * @param <T>
	 */
	@SuppressWarnings("hiding")
	public abstract class DefinitionMatcherParameterFilter<T> extends
			AbstractMatcher<ParameterMatching<T>> implements ParameterFilter<T> {
	}
}
