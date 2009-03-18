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

import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.utils.Assertor;

/**
 * Quản lý các phương thức xử lý catch exception trong khi thực thi validate các
 * annotation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 28, 2008 7:26:09 AM
 */
public abstract class AbstractDefinitionExceptionCatcher implements
		IAbstractDefinitionExceptionCatcher {
	/**
	 * Danh sách các AnnotationValidator đăng kí bắt exception cho các
	 * annotation chỉ định.
	 */
	protected HashMap<Class<? extends Annotation>, ArrayList<AnnotationValidator<? extends Annotation>>>	registeredCatchExceptionList	= new HashMap<Class<? extends Annotation>, ArrayList<AnnotationValidator<? extends Annotation>>>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#clearRegistryCatchList()
	 */
	@Override
	public void clearRegistryCatchList() {

		this.registeredCatchExceptionList.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#getRegisteredCatchExceptionList()
	 */
	@Override
	public HashMap<Class<? extends Annotation>, ArrayList<AnnotationValidator<? extends Annotation>>> getRegisteredCatchExceptionList() {

		return registeredCatchExceptionList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#isCatchExceptionRegistered(java.lang.Class)
	 */
	@Override
	public boolean isCatchExceptionRegistered(
			Class<? extends Annotation> annotation) {

		Assertor.notNull(annotation);
		synchronized (registeredCatchExceptionList) {
			if (this.registeredCatchExceptionList.containsKey(annotation)) {
				ArrayList<AnnotationValidator<? extends Annotation>> list;
				list = this.registeredCatchExceptionList.get(annotation);
				if (list.size() != 0) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#isCatchExceptionRegistered(java.lang.Class,
	 *      org.jgentleframework.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public boolean isCatchExceptionRegistered(
			Class<? extends Annotation> annotation,
			AnnotationValidator<? extends Annotation> validator) {

		Assertor.notNull(annotation);
		Assertor.notNull(validator);
		if (this.isCatchExceptionRegistered(annotation)) {
			ArrayList<AnnotationValidator<? extends Annotation>> list;
			list = this.registeredCatchExceptionList.get(annotation);
			if (list.contains(validator)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#unregisteredCatchException(java.lang.Class,
	 *      org.jgentleframework.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public <T extends Annotation> boolean unregisteredCatchException(
			Class<T> annotation, AnnotationValidator<T> validator) {

		Assertor.notNull(annotation);
		Assertor.notNull(validator);
		synchronized (registeredCatchExceptionList) {
			if (this.registeredCatchExceptionList.containsKey(annotation)) {
				ArrayList<AnnotationValidator<? extends Annotation>> list = this.registeredCatchExceptionList
						.get(annotation);
				synchronized (list) {
					if (list.contains(validator)) {
						boolean result = list.remove(validator);
						if (list.size() == 0) {
							this.registeredCatchExceptionList
									.remove(annotation);
						}
						return result;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IAbstractDefinitionExceptionCatcher#registerCatchException(java.lang.Class,
	 *      org.jgentleframework.core.metadatahandling.aohhandling.pvdhandler.AnnotationValidator)
	 */
	@Override
	public boolean registerCatchException(
			Class<? extends Annotation> annotation,
			AnnotationValidator<? extends Annotation> validator) {

		Assertor.notNull(annotation);
		Assertor.notNull(validator);
		synchronized (registeredCatchExceptionList) {
			if (!this.registeredCatchExceptionList.containsKey(annotation)) {
				ArrayList<AnnotationValidator<? extends Annotation>> list = new ArrayList<AnnotationValidator<? extends Annotation>>();
				list.add(validator);
				this.registeredCatchExceptionList.put(annotation, list);
			}
			else {
				if (this.registeredCatchExceptionList.get(annotation).contains(
						validator)) {
					return false;
				}
				else {
					this.registeredCatchExceptionList.get(annotation).add(
							validator);
				}
			}
		}
		return true;
	}
}
