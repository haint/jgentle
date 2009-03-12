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
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.Assertor;

/**
 * An implementation if {@link AnnotationRegister} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 10, 2007
 * @see AnnotationRegister
 */
public class AnnotationRegisterImpl implements AnnotationRegister {
	private ArrayList<Class<? extends Annotation>>											annotationRegistered	= new ArrayList<Class<? extends Annotation>>();
	/**
	 * Danh sách các annotation validator được đăng kí.
	 */
	private HashMap<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>>	validatorlist			= new HashMap<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>>();

	/**
	 * Constructor
	 */
	public AnnotationRegisterImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#addValidator(java.lang.Class,
	 *      org.exxlabs.jgentle.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public synchronized <T extends Annotation> void addValidator(Class<T> anno,
			AnnotationValidator<T> validator) {

		if (!isRegisteredAnnotation(anno)) {
			throw new RuntimeException("Annotation:" + anno.toString()
					+ " must be registered !");
		}
		Assertor.notNull(validator);
		Assertor.notNull(anno);
		this.validatorlist.put(anno, validator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#clearAllAnnotationRegistered()
	 */
	public void clearAllAnnotationRegistered() {

		this.annotationRegistered.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#countAnnotationRegistered()
	 */
	@Override
	public int countAnnotationRegistered() {

		return this.annotationRegistered.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#countValidator()
	 */
	@Override
	public int countValidator() {

		return this.validatorlist.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#getValidatorlist()
	 */
	public HashMap<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>> getValidatorlist() {

		return validatorlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#isAnnotationListEmpty()
	 */
	public boolean isAnnotationListEmpty() {

		return this.annotationRegistered.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#isRegisteredAnnotation(java.lang.Class)
	 */
	public boolean isRegisteredAnnotation(Class<? extends Annotation> anno) {

		Assertor.notNull(anno);
		return this.annotationRegistered.contains(anno);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#iteratorRegisteredAnno()
	 */
	public Iterator<Class<? extends Annotation>> iteratorRegisteredAnno() {

		return this.annotationRegistered.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#registerAnnotation(java.lang.Class)
	 */
	@Override
	public void registerAnnotation(Class<? extends Annotation> anno) {

		if (isRegisteredAnnotation(anno)) {
			throw new RuntimeException("Annotation:" + anno.toString()
					+ " is registered !");
		}
		else {
			this.annotationRegistered.add(anno);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#registerAnnotation(java.lang.Class,
	 *      org.exxlabs.jgentle.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public <T extends Annotation> void registerAnnotation(Class<T> anno,
			AnnotationValidator<T> validator) {

		registerAnnotation(anno);
		addValidator(anno, validator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#removeValidator(org.exxlabs.jgentle.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public synchronized <T extends Annotation> void removeValidator(
			AnnotationValidator<T> validator) {

		if (this.validatorlist.containsValue(validator)) {
			for (Entry<Class<? extends Annotation>, AnnotationValidator<? extends Annotation>> obj : this.validatorlist
					.entrySet()) {
				if (obj.getValue() == validator) {
					removeValidator(obj.getKey());
				}
			}
		}
		else {
			throw new RuntimeException("Could not remove validator.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#removeValidator(java.lang.Class)
	 */
	@Override
	public synchronized <T extends Annotation> void removeValidator(
			Class<T> anno) {

		if (!isRegisteredAnnotation(anno)) {
			throw new RuntimeException("Annotation '" + anno.toString()
					+ "' must be registered !");
		}
		if (!this.validatorlist.containsKey(anno))
			throw new RuntimeException(
					"Does not found validator of annotation '" + anno + "'");
		else
			this.validatorlist.remove(anno);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#subListAnnoRegistered(int,
	 *      int)
	 */
	public List<Class<? extends Annotation>> subListAnnoRegistered(
			int fromIndex, int toIndex) {

		return this.annotationRegistered.subList(fromIndex, toIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#unregisterAnnotation(java.lang.Class)
	 */
	public void unregisterAnnotation(Class<? extends Annotation> anno) {

		if (!isRegisteredAnnotation(anno)) {
			throw new RuntimeException("Annotation:" + anno.toString()
					+ " must be registered !");
		}
		else {
			this.annotationRegistered.remove(anno);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.core.metadatahandling.aohhandling.defhandling.AnnotationRegister#getAnnotationRegistered()
	 */
	@Override
	public ArrayList<Class<? extends Annotation>> getAnnotationRegistered() {

		return annotationRegistered;
	}
}
