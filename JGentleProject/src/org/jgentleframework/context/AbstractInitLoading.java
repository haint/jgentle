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
package org.jgentleframework.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.injecting.AbstractBeanFactory;
import org.jgentleframework.context.injecting.AbstractLoadingFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.reflection.DefinitionPostProcessor;
import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class AbstractInitLoading.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 12, 2009
 */
public abstract class AbstractInitLoading {
	/** The Constant log. */
	private static final Log	log	= LogFactory
											.getLog(AbstractInitLoading.class);

	/**
	 * Loading.
	 * 
	 * @param OLArray
	 *            the oL array
	 */
	@SuppressWarnings("unchecked")
	public static void loading(Provider provider,
			List<Map<String, Object>> OLArray) {

		AbstractLoadingFactory loadingFactory = (AbstractLoadingFactory) (ReflectUtils
				.isCast(AbstractLoadingFactory.class, provider) ? provider
				: null);
		// query option list
		List<DefinitionPostProcessor> dppList = new ArrayList<DefinitionPostProcessor>();
		HashMap<Class, AnnotationBeanProcessor> abpHash = new HashMap<Class, AnnotationBeanProcessor>();
		List<ObjectAttach<?>> oaList = new ArrayList<ObjectAttach<?>>();
		List<Class<?>> beanClassList = new ArrayList<Class<?>>();
		List<ObjectBindingConstant> obcList = new ArrayList<ObjectBindingConstant>();
		List<ObjectConstant> ocList = new ArrayList<ObjectConstant>();
		for (Map<String, Object> optionsList : OLArray) {
			/***************/
			List<DefinitionPostProcessor> dpp = (ArrayList<DefinitionPostProcessor>) optionsList
					.get(AbstractConfig.DEFINITION_POST_PROCESSOR);
			if (dpp != null)
				dppList.addAll(dpp);
			/***************/
			Map<Class, AnnotationBeanProcessor> abp = (HashMap<Class, AnnotationBeanProcessor>) optionsList
					.get(AbstractConfig.ANNOTATION_BEAN_PROCESSOR_LIST);
			if (abp != null)
				abpHash.putAll(abp);
			/***************/
			List<ObjectAttach<?>> oa = (ArrayList<ObjectAttach<?>>) optionsList
					.get(AbstractConfig.OBJECT_ATTACH_LIST);
			if (oa != null)
				oaList.addAll(oa);
			/***************/
			List<Class<?>> bc = (ArrayList<Class<?>>) optionsList
					.get(AbstractConfig.BEAN_CLASS_LIST);
			if (bc != null)
				beanClassList.addAll(bc);
			/***************/
			List<ObjectBindingConstant> obc = (ArrayList<ObjectBindingConstant>) optionsList
					.get(AbstractConfig.OBJECT_BINDING_CONSTANT_LIST);
			if (obc != null)
				obcList.addAll(obc);
			/***************/
			List<ObjectConstant> oc = (ArrayList<ObjectConstant>) optionsList
					.get(AbstractConfig.OBJECT_CONSTANT_LIST);
			if (oc != null)
				ocList.addAll(oc);
		}
		/*
		 * Registers all Extension-Points
		 */
		if (loadingFactory != null) {
			loadingFactory.load_DefinitionPostProcessor(dppList);
			loadingFactory.load_AnnotationBeanProcessor(abpHash);
			/*
			 * configured annotation bean loading
			 */
			List<Object> notLazyList = new ArrayList<Object>();
			List<Object> annotateIDList = new ArrayList<Object>();
			loadingFactory.load_ObjectAttachList(oaList, notLazyList,
					Types.ANNOTATION, annotateIDList);
			loadingFactory.load_BeanClassList(beanClassList, notLazyList,
					Types.ANNOTATION, annotateIDList);
			loadingFactory.load_ObjectBindingConstantList(obcList, notLazyList,
					true, annotateIDList);
			AbstractBeanFactory.buildDefBeanAnnotate(provider, annotateIDList);
			/*
			 * configured bean loading
			 */
			loadingFactory.load_ObjectConstantList(ocList);
			loadingFactory.load_ObjectAttachList(oaList, notLazyList,
					Types.NON_ANNOTATION);
			loadingFactory.load_BeanClassList(beanClassList, notLazyList,
					Types.NON_ANNOTATION);
			loadingFactory.load_ObjectBindingConstantList(obcList, notLazyList,
					false);
			provider.getDetectorController().handling(OLArray);
			/*
			 * Creates object beans is non lazy-init.
			 */
			AbstractBeanFactory.buildObjectBeanFromInfo(provider, notLazyList);
		}
		else {
			if (log.isFatalEnabled()) {
				log.fatal("Could not load the binding information !!",
						new JGentleException());
			}
		}
	}
}
