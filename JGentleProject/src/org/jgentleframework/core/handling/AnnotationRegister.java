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
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgentleframework.core.provider.AnnotationValidator;

/**
 * Provides some methods in order to manage registered annotations and their
 * validators ({@link AnnotationValidator}).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 10, 2007
 */
public interface AnnotationRegister {
	/**
	 * Adds an {@link AnnotationValidator annotation validator} according to
	 * specified registered annotation
	 * <p>
	 * <b>Note:</b> if previous {@link AnnotationValidator validator} of
	 * annotation is existed, it will be replaced by new
	 * {@link AnnotationValidator validator}.
	 * 
	 * @param anno
	 *            the object class according to given annotation
	 * @param validator
	 *            the validator
	 */
	public <T extends Annotation> void addValidator(Class<T> anno,
			AnnotationValidator<T> validator);

	/**
	 * Removes all registered annotations.
	 */
	public void clearAllAnnotationRegistered();

	/**
	 * Returns a {@link Map map} containing all registered
	 * {@link AnnotationValidator annotation validators}
	 */
	public Map<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>> getValidatorlist();

	/**
	 * Returns <b>true</b> if registered annotation list is empty, <b>false</b>
	 * otherwise.
	 */
	public boolean isAnnotationListEmpty();

	/**
	 * Returns <b>true</b> if given annotation is registered, <b>false</b>
	 * otherwise.
	 * 
	 * @param anno
	 *            the object class of annotation need to be tested.
	 */
	public boolean isRegisteredAnnotation(Class<? extends Annotation> anno);

	/**
	 * Returns an {@link Iterator} of object classes of all registered
	 * annotations.
	 * 
	 * @return <code>{@code Iterator<Class<? extends Annotation>>}</code>
	 */
	public Iterator<Class<? extends Annotation>> iteratorRegisteredAnno();

	/**
	 * Registers an annotation
	 * 
	 * @param anno
	 *            the object class of annotation need to be registered.
	 */
	public void registerAnnotation(Class<? extends Annotation> anno);

	/**
	 * Registers an annotation and its validator.
	 * 
	 * @param anno
	 *            the object class of annotation need to be registered.
	 * @param validator
	 *            the {@link AnnotationValidator} need to be registered.
	 */
	public <T extends Annotation> void registerAnnotation(Class<T> anno,
			AnnotationValidator<T> validator);

	/**
	 * Removes a specified {@link AnnotationValidator annotation validate}
	 * 
	 * @param validator
	 *            the {@link AnnotationValidator annotation validator} need to
	 *            be removed.
	 */
	public <T extends Annotation> void removeValidator(
			AnnotationValidator<T> validator);

	/**
	 * Removes a specified {@link AnnotationValidator annotation validator} of
	 * given annotation.
	 * 
	 * @param anno
	 *            the object class of annotation.
	 */
	public <T extends Annotation> void removeValidator(Class<T> anno);

	/**
	 * Returns a sub list of registered annotations.
	 * 
	 * @param fromIndex
	 *            the begin if index
	 * @param toIndex
	 *            the end of index
	 * @return the list< class<? extends annotation>>
	 */
	public List<Class<? extends Annotation>> subListAnnoRegistered(
			int fromIndex, int toIndex);

	/**
	 * Unregisters given annotation.
	 * <p>
	 * By default, if an registered annotation is unregistered, its validator
	 * will also be unregistered.
	 * 
	 * @param anno
	 *            the object class of annotation need to be unregistered
	 */
	public void unregisterAnnotation(Class<? extends Annotation> anno);

	/**
	 * Returns the number of all registered annotations.
	 * 
	 * @return int
	 */
	public int countAnnotationRegistered();

	/**
	 * Returns the number of all {@link AnnotationValidator}s present.
	 */
	public int countValidator();

	/**
	 * Returns an {@link List list} of registered annotation classes
	 */
	public List<Class<? extends Annotation>> getAnnotationRegistered();
}