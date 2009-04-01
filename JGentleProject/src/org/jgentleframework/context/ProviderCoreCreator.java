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
package org.jgentleframework.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.AbstractBeanFactory;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.autodetect.AutoLoadingDefinitionDetector;
import org.jgentleframework.context.injecting.autodetect.Detector;
import org.jgentleframework.context.injecting.autodetect.ExtensionPointsDetector;
import org.jgentleframework.context.injecting.autodetect.FirstDetector;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.core.JGentleRuntimeException;
import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.factory.BeanCreationProcessor;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * This is an implementation of {@link Provider} interface, is responsible for
 * core container of JGentle.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 1, 2007
 * @see Provider
 * @see AbstractBeanFactory
 */
class ProviderCoreCreator extends AbstractBeanFactory implements Provider {
	/** The Constant serialVersionUID. */
	private static final long									serialVersionUID	= 330183329296893472L;

	/**
	 * The {@link ArrayList} holds a list of config instance of this
	 * {@link Provider}.
	 */
	private ArrayList<Configurable>								configInstances		= null;

	/** the detector controller. */
	private Detector											detectorController	= null;

	/** registered interceptors. */
	protected HashMap<Matcher<Definition>, ArrayList<Object>>	interceptorList		= null;

	/** The interceptor cacher. */
	protected ConcurrentMap<Definition, Matcher<Definition>>	matcherCache		= null;

	/**
	 * Constructor.
	 * 
	 * @param serviceHandler
	 *            the {@link ServiceHandler} instance
	 * @param OLArray
	 *            the oL array
	 */
	public ProviderCoreCreator(ServiceHandler serviceHandler,
			List<Map<String, Object>> OLArray) {

		this.serviceHandler = serviceHandler;
		this.defManager = this.serviceHandler.getDefinitionManager();
		this.objectBeanFactory = new ObjectBeanFactoryImpl(this) {
			@Override
			public Object getRefInstance(String refInstance) {

				return ProviderCoreCreator.this.getRefInstance(refInstance);
			}

			@Override
			public Object getBean(String refer) {

				return ProviderCoreCreator.this.getBean(refer);
			}
		};
		this.detectorController = new FirstDetector(this);
		this.matcherCache = new ConcurrentHashMap<Definition, Matcher<Definition>>();
		this.interceptorList = new HashMap<Matcher<Definition>, ArrayList<Object>>();
		// Creates detector
		Detector espDetector = new ExtensionPointsDetector(this);
		Detector aldDetector = new AutoLoadingDefinitionDetector(this);
		// Sets chain
		detectorController.setNextDetector(espDetector);
		espDetector.setNextDetector(aldDetector);
		// executes init
		init(OLArray);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getBean(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> clazz) {

		Class<?> targetClass = null;
		if (this.objectBeanFactory.getMappingList().containsKey(clazz)) {
			targetClass = this.objectBeanFactory.getMappingList().get(clazz);
		}
		else
			targetClass = clazz;
		Definition def = this.defManager.getDefinition(targetClass);
		return (T) getBeanInstance(clazz, targetClass, null, def);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getBean(org.jgentleframework
	 * .core.reflection.metadata.Definition)
	 */
	@Override
	public Object getBean(Definition def) {

		if (def != null) {
			if (def.isInterpretedOfClass()) {
				return this.getBeanInstance((Class<?>) def.getKey(),
						(Class<?>) def.getKey(), null, def);
			}
			else {
				if (log.isErrorEnabled()) {
					log
							.error(
									"The specified definition instance is not object-class definition !",
									new JGentleRuntimeException());
				}
			}
		}
		else {
			if (log.isErrorEnabled()) {
				log.error("The given definition must not be null!",
						new JGentleRuntimeException());
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getBean(java.lang.String)
	 */
	@Override
	public Object getBean(String refer) {

		Object result = getRefInstance(refer);
		if ((result != null && result == refer) || result == null) {
			result = getBeanBoundToDefinition(refer);
			if (result == null)
				result = getBeanBoundToName(refer);
			if (result == null)
				result = getBeanBoundToMapping(refer);
			// if (result == null)
			// result = refer;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.injecting.AbstractBeanFactory#
	 * getBeanBoundToDefinition(java.lang.String)
	 */
	@Override
	public Object getBeanBoundToDefinition(String ID) {

		Definition def = this.defManager.getDefinition(ID);
		if (def != null) {
			if (def.isInterpretedOfClass()) {
				return getBeanInstance((Class<?>) def.getKey(), (Class<?>) def
						.getKey(), null, def);
			}
			else {
				if (log.isErrorEnabled()) {
					log.error("The definition instance corresponds to ID '"
							+ ID + "' is not object-class Definition !",
							new JGentleRuntimeException());
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getBeanBoundToMapping(
	 * java.lang.String)
	 */
	@Override
	public Object getBeanBoundToMapping(String mappingName) {

		if (this.objectBeanFactory.getAliasMap().containsKey(mappingName)) {
			Class<?> type = this.objectBeanFactory.getAliasMap().get(
					mappingName).getKey();
			Class<?> targetClass = this.objectBeanFactory.getAliasMap().get(
					mappingName).getValue();
			Definition def = this.getDefinitionManager().getDefinition(
					this.objectBeanFactory.getAliasMap().get(mappingName)
							.getValue());
			return getBeanInstance(type, targetClass, null, def);
		}
		else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getBeanBoundToName(java
	 * .lang.String)
	 */
	@Override
	public Object getBeanBoundToName(String instanceName) {

		Object result = null;
		Object obj = null;
		String ref = !instanceName.startsWith(REF.REF_CONSTANT) ? REF.REF_CONSTANT
				.substring(0, REF.REF_CONSTANT.length() - 1)
				+ "_" + instanceName
				: instanceName;
		CoreInstantiationSelector coreSelector = new CoreInstantiationSelectorImpl(
				ref);
		// creates scope info, default is SINGLETON
		Map<String, ScopeInstance> scopeList = this.objectBeanFactory
				.getScopeList();
		synchronized (scopeList) {
			if (!scopeList.containsKey(ref)) {
				scopeList.put(ref, Scope.SINGLETON);
			}
		}
		ScopeImplementation scopeImple = this.objectBeanFactory
				.createScopeInstance(ref);
		obj = getBeanFromScope(scopeImple, coreSelector, ref);
		if (obj != null && obj.getClass().equals(String.class)) {
			String objStr = (String) obj;
			// if the returned value is refered to the instance.
			result = getRefInstance(objStr);
		}
		else {
			result = obj;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.injecting.Provider#getConfigInstances()
	 */
	@Override
	public ArrayList<Configurable> getConfigInstances() {

		return this.configInstances;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#getDetectorController()
	 */
	@Override
	public Detector getDetectorController() {

		return detectorController;
	}

	/**
	 * @return the objectBeanFactory
	 */
	public ObjectBeanFactory getObjectBeanFactory() {

		return objectBeanFactory;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.AbstractBeanFactory#getRefInstance
	 * (java.lang.String)
	 */
	@Override
	public Object getRefInstance(String refInstance) {

		Object result = null;
		if (refInstance.equals(Configurable.REF_MAPPING)) {
			if (log.isErrorEnabled()) {
				log.error("Mapping type reference is not supported !",
						new InOutDependencyException());
			}
		}
		else if (refInstance.indexOf(":") != -1) {
			String[] values = refInstance.split(":");
			if (values[0].equals(Configurable.REF_CONSTANT)) {
				result = getBeanBoundToName(values[1]);
			}
			// TODO fix other Configurable.REF_MAPPING
			else if (values[0].equals(Configurable.REF_MAPPING)) {
				Class<?> clazz = null;
				if (values[1].indexOf(" ") != -1) {
					String[] split = values[1].split(" ");
					try {
						clazz = Class.forName(split[1]);
					}
					catch (ClassNotFoundException e) {
						result = this.getBeanBoundToMapping(values[1]);
					}
					result = this.getBean(clazz);
				}
				else
					result = this.getBeanBoundToMapping(values[1]);
			}
			else if (values[0].equals(Configurable.REF_ID)) {
				result = this.getBeanBoundToDefinition(values[1]);
			}
			else {
				return null;
			}
		}
		else {
			result = refInstance;
		}
		return result;
	}

	/**
	 * Inits the system.
	 * 
	 * @param OLArray
	 *            the oL array
	 */
	protected void init(List<Map<String, Object>> OLArray) {

		init_BeanCreatingProcessor();
		// creates system scope
		initSystemScope();
		AbstractInitLoading.loading(this, OLArray);
	}

	/**
	 * This method is responsible for {@link BeanCreationProcessor}
	 * instantiation.
	 */
	private void init_BeanCreatingProcessor() {

		try {
			if (!this.serviceHandler
					.containsDomain(BeanServices.DEFAULT_DOMAIN)) {
				this.serviceHandler.newDomain(BeanServices.DEFAULT_DOMAIN);
			}
		}
		catch (JGentleException e) {
			if (log.isFatalEnabled()) {
				log
						.fatal(
								"Could not create default domain of system instance !!",
								e);
			}
		}
		this.serviceHandler.addService(BeanCreationProcessor.class,
				BeanServices.DEFAULT_DOMAIN, new Class[] { Provider.class },
				new Object[] { this });
	}

	/**
	 * This method is responsible for system scope instantiation.
	 */
	private void initSystemScope() {

		for (Scope scope : Scope.class.getEnumConstants()) {
			synchronized (this.scopeController) {
				this.scopeController.addScope(scope);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.Provider#setConfigInstances(org
	 * .exxlabs.jgentle.configure.AbstractConfig[])
	 */
	@Override
	public void setConfigInstances(ArrayList<Configurable> configInstances) {

		this.configInstances = configInstances;
	}
}
