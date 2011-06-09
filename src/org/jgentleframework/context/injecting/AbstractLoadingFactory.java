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
package org.jgentleframework.context.injecting;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.reflection.DefinitionPostProcessor;
import org.jgentleframework.reflection.annohandler.AnnotationBeanProcessor;

/**
 * The Class AbstractLoadingFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 12, 2009
 */
public abstract class AbstractLoadingFactory {
	/** The {@link DefinitionManager}. */
	protected DefinitionManager	definitionManager	= null;

	/** The object bean factory. */
	protected ObjectBeanFactory	objectBeanFactory	= null;

	/**
	 * Inject load_ annotation bean processor.
	 * 
	 * @param ABPList
	 *            the aBP list
	 */
	@SuppressWarnings("unchecked")
	public void load_AnnotationBeanProcessor(
			Map<Class, AnnotationBeanProcessor> ABPList) {

		for (Entry<Class, AnnotationBeanProcessor> entry : ABPList.entrySet()) {
			this.definitionManager.addAnnotationBeanProcessor(entry.getKey(),
					entry.getValue());
		}
	}

	/**
	 * Inject load_ bean class list.
	 * 
	 * @param beanClassList
	 *            the bean class list
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            the type
	 */
	public void load_BeanClassList(List<Class<?>> beanClassList,
			List<Object> notLazyList, Types type) {

		load_BeanClassList(beanClassList, notLazyList, type, null);
	}

	/**
	 * Inject load_ bean class list.
	 * 
	 * @param beanClassList
	 *            the bean class list
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            the type
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public void load_BeanClassList(List<Class<?>> beanClassList,
			List<Object> notLazyList, Types type, List<Object> annotateIDList) {

		/*
		 * Nạp thông tin các bean class
		 */
		for (Class<?> objClass : beanClassList) {
			this.objectBeanFactory.load_BeanClass(objClass, notLazyList, type,
					annotateIDList);
		}
	}

	/**
	 * Inject load_ definition post processor.
	 * 
	 * @param DPPList
	 *            the dPP list
	 */
	public void load_DefinitionPostProcessor(
			List<DefinitionPostProcessor> DPPList) {

		for (DefinitionPostProcessor dpp : DPPList) {
			this.definitionManager.getVisitorHandler()
					.addDefinitionPostProcessor(dpp);
		}
	}

	/**
	 * Inject load_ object attach list.
	 * 
	 * @param objAthList
	 *            the obj ath list
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            the type
	 */
	public void load_ObjectAttachList(List<ObjectAttach<?>> objAthList,
			List<Object> notLazyList, Types type) {

		load_ObjectAttachList(objAthList, notLazyList, type, null);
	}

	/**
	 * Inject load_ object attach list.
	 * 
	 * @param objAthList
	 *            the obj ath list
	 * @param notLazyList
	 *            the not lazy list
	 * @param type
	 *            the type
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public void load_ObjectAttachList(List<ObjectAttach<?>> objAthList,
			List<Object> notLazyList, Types type, List<Object> annotateIDList) {

		/*
		 * Nạp thông tin của các ObjectAttach
		 */
		for (ObjectAttach<?> objAth : objAthList) {
			this.objectBeanFactory.load_ObjectAttach(objAth, notLazyList, type,
					annotateIDList);
		}
	}

	/**
	 * Inject load_ object binding constant list.
	 * 
	 * @param objBndCstList
	 *            the obj bnd cst list
	 * @param notLazyList
	 *            the not lazy list
	 * @param bool
	 *            if indicates <b>true</b>, this method will only load
	 *            {@link ObjectBindingConstant}s which are {@link Annotation},
	 *            if indicates <b>false</b>, only {@link ObjectBindingConstant}s
	 *            aren't {@link Annotation} will be load.
	 */
	public void load_ObjectBindingConstantList(
			List<ObjectBindingConstant> objBndCstList,
			List<Object> notLazyList, boolean bool) {

		load_ObjectBindingConstantList(objBndCstList, notLazyList, bool, null);
	}

	/**
	 * Inject load_ object binding constant list.
	 * 
	 * @param objBndCstList
	 *            the obj bnd cst list
	 * @param notLazyList
	 *            the not lazy list
	 * @param bool
	 *            if indicates <b>true</b>, this method will only load
	 *            {@link ObjectBindingConstant}s which are {@link Annotation},
	 *            if indicates <b>false</b>, only {@link ObjectBindingConstant}s
	 *            aren't {@link Annotation} will be load.
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public void load_ObjectBindingConstantList(
			List<ObjectBindingConstant> objBndCstList,
			List<Object> notLazyList, boolean bool, List<Object> annotateIDList) {

		if (bool) {
			for (ObjectBindingConstant objBndCst : objBndCstList) {
				if (Annotation.class.isAssignableFrom(objBndCst.getInClass())) {
					this.objectBeanFactory.load_ObjectBindingConstant(
							objBndCst, notLazyList, annotateIDList);
				}
			}
		}
		else {
			for (ObjectBindingConstant objBndCst : objBndCstList) {
				if (!Annotation.class.isAssignableFrom(objBndCst.getInClass())) {
					this.objectBeanFactory.load_ObjectBindingConstant(
							objBndCst, notLazyList, annotateIDList);
				}
			}
		}
	}

	/**
	 * Inject load_ object constant list.
	 * 
	 * @param objCstList
	 *            the obj cst list
	 */
	public void load_ObjectConstantList(List<ObjectConstant> objCstList) {

		/*
		 * Nạp thông tin các ObjectConstant
		 */
		for (ObjectConstant objCst : objCstList) {
			this.objectBeanFactory.load_ObjectConstant(objCst);
		}
	}
}
