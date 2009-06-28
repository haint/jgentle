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
import java.util.ArrayList;
import java.util.HashMap;

import org.jgentleframework.core.provider.AnnotationValidator;

/**
 * The Interface IAbstractDefinitionExceptionCatcher.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 28, 2008 7:32:28 AM
 */
public interface IAbstractDefinitionExceptionCatcher {
	/**
	 * Removes all validator which are registered to catch exception of
	 * annotation validating process.
	 */
	public void clearRegistryCatchList();

	/**
	 * Gets the registered catch exception list.
	 * 
	 * @return the registeredCatchExceptionList
	 */
	public HashMap<Class<? extends Annotation>, ArrayList<AnnotationValidator<? extends Annotation>>> getRegisteredCatchExceptionList();

	/**
	 * Returns <b>true</b> if given annotation is registered in order to catch
	 * exception of any annotation validating, <b>false</b> otherwise.
	 * 
	 * @param annotation
	 *            the object class of annotation.
	 * @return true, if checks if is catch exception registered
	 * @see AnnotationValidator
	 */
	public boolean isCatchExceptionRegistered(
			Class<? extends Annotation> annotation);

	/**
	 * Returns <b>true</b> if given annotation is registered in order to catch
	 * exception of given annotation validating, <b>false</b> otherwise.
	 * 
	 * @param annotation
	 *            the object class of annotation.
	 * @param validator
	 *            the {@link AnnotationValidator}
	 * @return true, if checks if is catch exception registered
	 */
	public boolean isCatchExceptionRegistered(
			Class<? extends Annotation> annotation,
			AnnotationValidator<? extends Annotation> validator);

	/**
	 * Registerd an validator in order to catch exception which is thrown by
	 * annotation validating of given specified annotation.
	 * 
	 * @param annotation
	 *            the object class of annotation
	 * @param validator
	 *            the {@link AnnotationValidator}
	 * @return returns <b>true</b> if register process is successful,
	 *         <b>false</b> otherwise.
	 */
	public boolean registerCatchException(
			Class<? extends Annotation> annotation,
			AnnotationValidator<? extends Annotation> validator);

	/**
	 * Unregisters specified {@link AnnotationValidator} which is registered in
	 * order to catch exception of annotation validating of given annotation.
	 * 
	 * @param annotation
	 *            the object class of annotation
	 * @param validator
	 *            the {@link AnnotationValidator} need to be unregistered.
	 * @return returns <b>true</b> if register process is successful,
	 *         <b>false</b> otherwise.
	 */
	public <T extends Annotation> boolean unregisteredCatchException(
			Class<T> annotation, AnnotationValidator<T> validator);
}