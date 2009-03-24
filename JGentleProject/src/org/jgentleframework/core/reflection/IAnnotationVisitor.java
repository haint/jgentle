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
package org.jgentleframework.core.reflection;

import java.lang.annotation.Annotation;

import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Provides some method in order to manage extension points in course interpret
 * annotations or {@link Definition definitions}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 4, 2007
 */
public interface IAnnotationVisitor {
	/**
	 * Registers an {@link AnnotationBeanProcessor}
	 * 
	 * @param <T>
	 * @param key
	 *            the object class of corresponding annotation need to be
	 *            registered to given {@link AnnotationBeanProcessor}
	 * @param handler
	 *            the given {@link AnnotationBeanProcessor} need to be
	 *            registered.
	 * @return returns the previous registered {@link AnnotationBeanProcessor}
	 *         if it exists, otherwise returns <b>null</b>.
	 */
	public <T extends Annotation> AnnotationBeanProcessor<?> addAnnotationBeanProcessor(
			Class<T> key, AnnotationBeanProcessor<T> handler);

	/**
	 * Registers a given {@link DefinitionPostProcessor}.
	 * <p>
	 * <b>Note:</b> if given {@link DefinitionPostProcessor} is existed, this
	 * method does not thing.
	 * 
	 * @param dpp
	 *            the object class of given {@link DefinitionPostProcessor} need
	 *            to be registered.
	 */
	public void addDefinitionPostProcessor(
			Class<? extends DefinitionPostProcessor> dpp);

	/**
	 * Registers a given {@link DefinitionPostProcessor}.
	 * <p>
	 * <b>Note:</b> if given {@link DefinitionPostProcessor} is existed, this
	 * method does not thing.
	 * 
	 * @param dpp
	 *            the given {@link DefinitionPostProcessor} instance need to be
	 *            registered.
	 */
	public void addDefinitionPostProcessor(DefinitionPostProcessor dpp);

	/**
	 * Returns the {@link DefinitionPostProcessor} at the specified position.
	 * 
	 * @param index
	 *            index of the {@link DefinitionPostProcessor} to return.
	 */
	public DefinitionPostProcessor getDefinitionPostProcessor(int index);

	/**
	 * Removes the specififed {@link AnnotationBeanProcessor}.
	 * 
	 * @param <T>
	 * @param key
	 *            the annotation type of corresponding annotation of the
	 *            specified {@link AnnotationBeanProcessor} need to be removed.
	 * @return returns the {@link AnnotationBeanProcessor} was removed, or
	 *         returns <b>null</b> if no {@link AnnotationBeanProcessor} is
	 *         removed.
	 */
	public <T extends Annotation> AnnotationBeanProcessor<?> removeAnnotationBeanProcessor(
			Class<T> key);

	/**
	 * Removes the specified {@link DefinitionPostProcessor}
	 * 
	 * @param dpp
	 *            the specified {@link DefinitionPostProcessor} need to be
	 *            removed.
	 * @return returns <b>true</b> if success, <b>false</b> otherwise.
	 */
	public boolean removeDefinitionPostProcessor(DefinitionPostProcessor dpp);

	/**
	 * Removes the specified {@link DefinitionPostProcessor} at the specified
	 * position.
	 * 
	 * @param index
	 *            the index of {@link DefinitionPostProcessor} to remove.
	 * @return returns the specified {@link DefinitionPostProcessor} was
	 *         removed.
	 */
	public DefinitionPostProcessor removeDefinitionPostProcessor(int index);

	/**
	 * Replaces the specified {@link AnnotationBeanProcessor} to new
	 * {@link AnnotationBeanProcessor}
	 * 
	 * @param <T>
	 *            type of corresponding annotation.
	 */
	public <T extends Annotation> void replaceAnnotationBeanProcessor(
			AnnotationBeanProcessor<T> oldHandler,
			AnnotationBeanProcessor<T> newHandler);

	/**
	 * Interprets annotation
	 * 
	 * @param annoArray
	 * @param rootAnnoMeta
	 */
	public void visit(Annotation[] annoArray, AnnoMeta rootAnnoMeta);
}
