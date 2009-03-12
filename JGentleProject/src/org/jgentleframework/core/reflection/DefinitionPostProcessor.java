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

import org.jgentleframework.core.reflection.metadata.AnnoMeta;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * This interface represents a generic {@link DefinitionPostProcessor}.
 * <p>
 * A {@link DefinitionPostProcessor} is responsible for {@link Definition}
 * execution at specified time (before, after or catch exception) during
 * {@link Definition} instantiation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 7, 2007
 */
public interface DefinitionPostProcessor {
	/**
	 * This method shall be invoked before the corresponding {@link Definition}
	 * is instantiated.
	 * 
	 * @param annoArray
	 *            the annotation list of current {@link Definition}
	 * @param annoMeta
	 *            The {@link AnnoMeta} of current {@link Definition}
	 * @throws DefinitionPostException
	 */
	void beforePost(Annotation[] annoArray, AnnoMeta annoMeta)
			throws DefinitionPostException;

	/**
	 * This method shall be invoked after the corresponding {@link Definition}
	 * is instantiated.
	 * 
	 * @param annoArray
	 *            the annotation list of current {@link Definition}
	 * @param annoMeta
	 *            The {@link AnnoMeta} of current {@link Definition}
	 * @throws DefinitionPostException
	 */
	void afterPost(Annotation[] annoArray, AnnoMeta annoMeta)
			throws DefinitionPostException;

	/**
	 * This method will catch all exceptions thrown during the corresponding
	 * {@link Definition} is instantiating, includes thrown exceptions of
	 * {@link #beforePost(Annotation[], AnnoMeta)} method and
	 * {@link #afterPost(Annotation[], AnnoMeta)} method.
	 * 
	 * @param ex
	 *            the exception
	 * @param annoArray
	 *            the annotation list of current {@link Definition}
	 * @param annoMeta
	 *            The {@link AnnoMeta} of current {@link Definition}
	 * @param bool
	 *            this boolean value represents the time of exception catching.
	 *            If <b>true</b>, it is <code>before post</code>,if <b>false</b>,
	 *            it is <code>after post</code>.
	 * @return if this method implementation returns <b>true</b>, the
	 *         instantiation of corresponding {@link Definition} will be
	 *         continued until its completed, otherwise, return <b>false</b>,
	 *         this instantiation will be interupted.
	 */
	boolean catchException(Exception ex, Annotation[] annoArray,
			AnnoMeta annoMeta, boolean bool);
}
