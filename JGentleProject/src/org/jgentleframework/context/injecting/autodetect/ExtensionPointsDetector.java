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
package org.jgentleframework.context.injecting.autodetect;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.annotation.Bean;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.context.beans.BeanPostInstantiation;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.core.factory.BeanCreationProcessor;
import org.jgentleframework.core.factory.BeanPostInstantiationSupportInterface;
import org.jgentleframework.core.provider.Domain;
import org.jgentleframework.core.reflection.DefinitionPostProcessor;
import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * It represents a detector which is responsible for automatically system bean
 * detecting includes {@link DefinitionPostProcessor},
 * {@link AnnotationBeanProcessor}, {@link BeanPostInstantiation} or
 * {@link ScopeImplementation}, ... and then auto creates and registers them to
 * corresponding {@link Provider}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see AbstractDetector
 * @see FirstDetector
 */
public class ExtensionPointsDetector extends AbstractDetector {
	/**
	 * Constructor.
	 * 
	 * @param provider
	 *            the provider
	 */
	public ExtensionPointsDetector(Provider provider) {

		super(provider);
	}

	/**
	 * Returns the ID ({@link Definition}) of the given bean class.
	 * 
	 * @param clazz
	 *            the given object class corresponding to specified bean.
	 */
	private String getIDFromBeanClass(Class<?> clazz) {

		Definition def = provider.getDefinitionManager().getDefinition(clazz);
		String ID = "";
		if (def.isAnnotationPresent(Bean.class)) {
			String value = def.getAnnotation(Bean.class).value();
			ID = value == null || value.isEmpty() ? clazz.getName() : def
					.getAnnotation(Bean.class).value();
		}
		else
			ID = clazz.getName();
		return ID;
	}

	/**
	 * Detects bean class.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	private void checkBeanClassList(Class<?> clazz) {

		// Detects BeanPostInstantiation
		if (BeanPostInstantiation.class.isAssignableFrom(clazz)) {
			String ID = getIDFromBeanClass(clazz);
			Domain domain = this.provider.getServiceHandler().getDomain(
					BeanServices.DEFAULT_DOMAIN);
			BeanPostInstantiationSupportInterface bcp = domain
					.getServiceInstance(BeanCreationProcessor.class);
			bcp.addBeanPostInstantiation(REF.ref(ID));
		}
		// Detects DefinitionPostProcessor
		else if (DefinitionPostProcessor.class.isAssignableFrom(clazz)) {
			DefinitionPostProcessor dpp = (DefinitionPostProcessor) provider
					.getBeanBoundToDefinition(getIDFromBeanClass(clazz));
			provider.getDefinitionManager().addDefinitionPostProcessor(dpp);
		}
		// Detects AnnotationBeanProcessor
		else if (AnnotationBeanProcessor.class.isAssignableFrom(clazz)) {
			AnnotationBeanProcessor<?> abp = (AnnotationBeanProcessor<?>) provider
					.getBeanBoundToDefinition(getIDFromBeanClass(clazz));
			try {
				provider.getDefinitionManager().addAnnotationBeanProcessor(abp);
			}
			catch (ClassNotFoundException e) {
				throw new AutoDetectException(e.getMessage());
			}
		}
		// Detects scope implementation
		else if (ScopeImplementation.class.isAssignableFrom(clazz)) {
			if (clazz.isEnum()) {
				throw new AutoDetectException(
						"Does not support enum type to mapping class.");
			}
			else {
				Definition def = provider.getDefinitionManager().getDefinition(
						clazz);
				String ID = getIDFromBeanClass(clazz);
				if (def.getAnnotation(Bean.class).scope().equals(
						Scope.SINGLETON)) {
					throw new AutoDetectException(
							"Invalid scope type for ScopeImplementation: "
									+ clazz
									+ ", only SINGLETON scope is permitted");
				}
				ScopeImplementation scopeInstance = (ScopeImplementation) provider
						.getBeanBoundToDefinition(ID);
				synchronized (provider.getScopeController()) {
					provider.getScopeController().addScope(scopeInstance);
				}
			}
		}
	}

	/**
	 * Detects {@link ObjectBindingConstant}.
	 * 
	 * @param obc
	 *            the obc
	 */
	private void checkObjectBindingConstant(ObjectBindingConstant obc) {

		Class<?> clazz = obc.getInClass();
		// Detects BeanPostInstantiation
		if (BeanPostInstantiation.class.isAssignableFrom(clazz)) {
			String ID = obc.getID();
			Domain domain = this.provider.getServiceHandler().getDomain(
					BeanServices.DEFAULT_DOMAIN);
			BeanPostInstantiationSupportInterface bcp = domain
					.getServiceInstance(BeanCreationProcessor.class);
			bcp.addBeanPostInstantiation(REF.ref(ID));
		}
		// Detects DefinitionPostProcessor
		else if (DefinitionPostProcessor.class.isAssignableFrom(clazz)) {
			DefinitionPostProcessor dpp = (DefinitionPostProcessor) provider
					.getBeanBoundToDefinition(obc.getID());
			provider.getDefinitionManager().addDefinitionPostProcessor(dpp);
		}
		// Detects AnnotationBeanProcessor
		else if (AnnotationBeanProcessor.class.isAssignableFrom(clazz)) {
			AnnotationBeanProcessor<?> abp = (AnnotationBeanProcessor<?>) provider
					.getBeanBoundToDefinition(obc.getID());
			try {
				provider.getDefinitionManager().addAnnotationBeanProcessor(abp);
			}
			catch (ClassNotFoundException e) {
				throw new AutoDetectException(e.getMessage());
			}
		}
		// Detects ScopeImplementation
		else if (ScopeImplementation.class.isAssignableFrom(clazz)) {
			if (!obc.getScope().equals(Scope.SINGLETON)) {
				throw new AutoDetectException(
						"Invalid scope type for ScopeImplementation: " + clazz
								+ ", only SINGLETON scope is permitted");
			}
			// Nếu class là enum
			if (clazz.isEnum()) {
				synchronized (provider.getScopeController()) {
					for (Object scope : clazz.getEnumConstants()) {
						provider.getScopeController().addScope(
								(ScopeImplementation) scope);
					}
				}
			}
			// Nếu class không phải enum
			else {
				ScopeImplementation scopeInstance = (ScopeImplementation) provider
						.getBeanBoundToDefinition(obc.getID());
				synchronized (provider.getScopeController()) {
					provider.getScopeController().addScope(scopeInstance);
				}
			}
		}
	}

	/**
	 * Detects {@link ObjectAttach}.
	 * 
	 * @param oa
	 *            the given {@link ObjectAttach} instance.
	 */
	private void checkObjectAttach(ObjectAttach<?> oa) {

		Set<Entry<Class<?>, Class<?>>> set = oa.getMappingEntrySet();
		for (Entry<Class<?>, Class<?>> entry : set) {
			/*
			 * Detects BeanPostInstantiation
			 */
			if (BeanPostInstantiation.class.isAssignableFrom(entry.getValue())) {
				Domain domain = this.provider.getServiceHandler().getDomain(
						BeanServices.DEFAULT_DOMAIN);
				BeanPostInstantiationSupportInterface bcp = domain
						.getServiceInstance(BeanCreationProcessor.class);
				if (oa.getName() != null && !oa.getName().isEmpty()
						&& set.size() == 1)
					bcp.addBeanPostInstantiation(REF.refMapping(oa.getName()));
				else
					bcp
							.addBeanPostInstantiation(REF.refMapping(entry
									.getKey()));
			}
			/*
			 * Detects DefinitionPostProcessor
			 */
			else if (DefinitionPostProcessor.class.isAssignableFrom(entry
					.getValue())) {
				DefinitionPostProcessor dpp = oa.getName() != null
						&& !oa.getName().isEmpty() && set.size() == 1 ? (DefinitionPostProcessor) provider
						.getBeanBoundToMapping(oa.getName())
						: (DefinitionPostProcessor) provider.getBean(entry
								.getKey());
				provider.getDefinitionManager().addDefinitionPostProcessor(dpp);
			}
			/*
			 * Detects AnnotationBeanProcessor
			 */
			else if (AnnotationBeanProcessor.class.isAssignableFrom(entry
					.getValue())) {
				AnnotationBeanProcessor<?> abp = oa.getName() != null
						&& !oa.getName().isEmpty() && set.size() == 1 ? (AnnotationBeanProcessor<?>) provider
						.getBeanBoundToMapping(oa.getName())
						: (AnnotationBeanProcessor<?>) provider.getBean(entry
								.getKey());
				try {
					provider.getDefinitionManager().addAnnotationBeanProcessor(
							abp);
				}
				catch (ClassNotFoundException e) {
					throw new AutoDetectException(e.getMessage());
				}
			}
			/*
			 * Detects ScopeImplementation
			 */
			else if (ScopeImplementation.class.isAssignableFrom(entry
					.getValue())) {
				ScopeInstance scope = oa.getScopeList().get(entry);
				if (!scope.equals(Scope.SINGLETON)) {
					throw new AutoDetectException(
							"Invalid scope type for ScopeImplementation: "
									+ entry.getValue()
									+ ", only SINGLETON scope is permitted");
				}
				// Nếu class là enum
				if (entry.getValue().isEnum()) {
					synchronized (provider.getScopeController()) {
						for (Object objScope : entry.getValue()
								.getEnumConstants()) {
							provider.getScopeController().addScope(
									(ScopeImplementation) objScope);
						}
					}
				}
				// Nếu class không phải enum
				else {
					ScopeImplementation scopeInstance = oa.getName() != null
							&& !oa.getName().isEmpty() && set.size() == 1 ? (ScopeImplementation) provider
							.getBeanBoundToMapping(oa.getName())
							: (ScopeImplementation) provider.getBean(entry
									.getKey());
					synchronized (provider.getScopeController()) {
						provider.getScopeController().addScope(scopeInstance);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.AbstractDetector#handling
	 * (java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handling(List<Map<String, Object>> OLArray) {

		List<ObjectBindingConstant> obcList = new LinkedList<ObjectBindingConstant>();
		List<ObjectAttach<?>> objectAttachList = new LinkedList<ObjectAttach<?>>();
		List<Class<?>> beanClassList = new LinkedList<Class<?>>();
		for (Map<String, Object> optionsList : OLArray) {
			obcList
					.addAll((Collection<? extends ObjectBindingConstant>) optionsList
							.get(AbstractConfig.OBJECT_BINDING_CONSTANT_LIST));
			objectAttachList
					.addAll((Collection<? extends ObjectAttach<?>>) optionsList
							.get(AbstractConfig.OBJECT_ATTACH_LIST));
			beanClassList.addAll((Collection<? extends Class<?>>) optionsList
					.get(AbstractConfig.BEAN_CLASS_LIST));
		}
		/*
		 * detect Object Binding Constant
		 */
		for (ObjectBindingConstant obc : obcList) {
			checkObjectBindingConstant(obc);
		}
		/*
		 * detect Object Attach
		 */
		for (ObjectAttach<?> oa : objectAttachList) {
			checkObjectAttach(oa);
		}
		/*
		 * detect Bean Class
		 */
		for (Class<?> clazz : beanClassList) {
			checkBeanClassList(clazz);
		}
	}
}
