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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.BindingConfig;
import org.jgentleframework.configure.BindingConfigImpl;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.ConfigurableImporter;
import org.jgentleframework.configure.ConfigurationProxy;
import org.jgentleframework.configure.SystemConfig;
import org.jgentleframework.configure.SystemConfigImpl;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.aopweaving.enums.RegisterAnnotationAOP;
import org.jgentleframework.context.annotation.ComponentServiceContext;
import org.jgentleframework.context.enums.RegisterAnnotationContext;
import org.jgentleframework.context.enums.RegisterAnnotationInjecting;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.context.services.ServiceHandlerImpl;
import org.jgentleframework.core.JGentleRuntimeException;
import org.jgentleframework.core.JGentleException;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.AnnotationRegisterImpl;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.handling.DefinitionManagerImpl;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.core.provider.ServiceClass;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.NullClass;

/**
 * This class is responsible for management and instantiation of container in
 * JGentle system. It provides some static methods in order to configure JGentle
 * system and instantiate container.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 10, 2007
 */
public abstract class JGentle {
	/** The before config bean list. */
	private static List<BeforeConfigure>	beforeConfigBeanList	= new ArrayList<BeforeConfigure>();

	/** The before init context list. */
	private static List<BeforeInitContext>	beforeInitContextList	= new ArrayList<BeforeInitContext>();

	/** The config obj class list. */
	private static Map<Class<?>, Class<?>>	configObjClassList		= new HashMap<Class<?>, Class<?>>();

	public static Log						log						= LogFactory
																			.getLog(JGentle.class);

	/**
	 * Adds a {@link BeforeConfigure} or a group of {@link BeforeConfigure}
	 * instances.
	 * 
	 * @param beans
	 *            the given instances
	 */
	public static void addBeforeConfig(BeforeConfigure... beans) {

		Assertor
				.notEmpty(beans,
						"Object beans must not be null and must have at least one element.");
		for (BeforeConfigure result : beans) {
			if (!JGentle.getBeforeConfigBeanList().contains(result)) {
				JGentle.getBeforeConfigBeanList().add(result);
			}
			else {
				throw new JGentleRuntimeException(
						"This BeforeConfigure Bean is existed.");
			}
		}
	}

	/**
	 * Adds {@link BeforeConfigure} instances
	 * 
	 * @param classes
	 *            the object classes of {@link BeforeConfigure} need to be
	 *            added.
	 */
	public static void addBeforeConfig(
			Class<? extends BeforeConfigure>... classes) {

		Assertor
				.notEmpty(classes,
						"Object class must not be null and must have at least one element.");
		for (Class<? extends BeforeConfigure> clazz : classes) {
			BeforeConfigure result = ReflectUtils.createInstance(clazz);
			addBeforeConfig(result);
		}
	}

	/**
	 * Adds {@link BeforeInitContext} <code>instances</code>
	 * 
	 * @param beforeInitContexts
	 *            the before init contexts
	 */
	public static void addBeforeInitContext(
			BeforeInitContext... beforeInitContexts) {

		Assertor
				.notEmpty(beforeInitContexts,
						"Object beans must not be null and must have at least one element.");
		for (BeforeInitContext obj : beforeInitContexts) {
			if (!JGentle.beforeInitContextList.contains(obj)) {
				JGentle.beforeInitContextList.add(obj);
			}
			else {
				throw new JGentleRuntimeException(
						"This BeforeInitContext bean is existed.");
			}
		}
	}

	/**
	 * Adds {@link BeforeInitContext} <code>instances</code>.
	 * 
	 * @param classes
	 *            the classes
	 */
	public static void addBeforeInitContext(
			Class<? extends BeforeInitContext>... classes) {

		Assertor
				.notEmpty(classes,
						"Object class must not be null and must have at least one element.");
		for (Class<? extends BeforeInitContext> clazz : classes) {
			BeforeInitContext result = ReflectUtils.createInstance(clazz);
			addBeforeInitContext(result);
		}
	}

	/**
	 * Adds a config class
	 * 
	 * @param interfaze
	 *            type (<code>interface</code>) of <code>config class</code>.
	 * @param clazz
	 *            the <code>object class</code> of <code>config class</code>
	 *            need to be added.
	 * @return returns the previous <code>object class</code> of
	 *         <code>config class</code> appropriate to <b><i>interfaze</i></b>
	 *         if it existed, if not, returns <b>null</b>.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T addConfigClass(Class<T> interfaze,
			Class<? extends T> clazz) {

		Assertor.notNull(interfaze);
		Assertor.notNull(clazz);
		if (interfaze.equals(clazz)) {
			throw new JGentleRuntimeException("invalid arguments !");
		}
		if (!interfaze.isInterface()) {
			throw new JGentleRuntimeException(interfaze.toString()
					+ " must be a interface.");
		}
		return (T) JGentle.configObjClassList.put(interfaze, clazz);
	}

	/**
	 * Builds the context.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 * @param argsType
	 *            the args type
	 * @param args
	 *            the args
	 * @param configClasses
	 *            the config classes
	 * @return the context
	 */
	private static Context buildContext(boolean serviceProvider,
			Class<?>[] argsType, Object[] args,
			Class<? extends Configurable>... configClasses) {

		Assertor.notNull((Object[]) configClasses);
		if ((argsType == null && args != null)
				|| (argsType != null && args == null)) {
			throw new JGentleRuntimeException(
					"Property 'argsType' or 'args' is invalid !");
		}
		if (argsType != null && args != null) {
			if (argsType.length != args.length) {
				throw new JGentleRuntimeException(
						"Property 'argsType' or 'args' is invalid !");
			}
			if (configClasses.length != 1) {
				throw new JGentleRuntimeException(
						"Lenght of array property 'configClasses' must be not greater than"
								+ " one if property 'argsType' and 'args' are not null.");
			}
		}
		/*
		 * Khởi tạo các config object
		 */
		configObjClassList.put(BindingConfig.class, BindingConfigImpl.class);
		configObjClassList.put(SystemConfig.class, SystemConfigImpl.class);
		/*
		 * Khởi tạo core services
		 */
		AnnotationRegister annoRegister = new AnnotationRegisterImpl();
		DefinitionManager defManager = new DefinitionManagerImpl(annoRegister);
		ServiceHandler serviceHandler = new ServiceHandlerImpl(defManager);
		JGentle.registerAnnotations(serviceHandler);
		/*
		 * Thực thi các tiền xử lý trước khi thực thi cấu hình (invoke configure
		 * methods).
		 */
		List<Class<? extends Configurable>> absCfgList = new LinkedList<Class<? extends Configurable>>();
		for (Class<? extends Configurable> clazz : configClasses) {
			absCfgList.add(clazz);
		}
		for (BeforeConfigure bcBean : JGentle.getBeforeConfigBeanList()) {
			bcBean.doProcessing(serviceHandler, defManager, annoRegister,
					absCfgList);
		}
		// Khởi tạo config instance
		List<Configurable> objectList = new LinkedList<Configurable>();
		for (Class<? extends Configurable> targetClass : absCfgList) {
			Configurable result = ConfigurationProxy.createProxy(targetClass,
					argsType, args);
			result.configure();
			// Tìm các imported configurable class nếu có
			List<ConfigurableImporter> importingList = result
					.getImportsCfgLst();
			List<Configurable> allResults = new LinkedList<Configurable>();
			if (importingList != null && importingList.size() != 0) {
				for (ConfigurableImporter ci : importingList) {
					allResults.add(ConfigurationProxy.createProxy(ci
							.getConfigurableClass(), ci.argsType(), ci.args()));
				}
			}
			allResults.add(result);
			for (Configurable objResult : allResults) {
				// Nếu context khởi tạo là một Services Context
				if (serviceProvider) {
					// Thực thi đăng kí annotation nếu trong config instance có
					// tồn tại một hoặc nhiều binding CSC
					List<Class<? extends ComponentServiceContextType<?>>> list;
					list = objResult.getCscClassList();
					for (Class<? extends ComponentServiceContextType<?>> cscClass : list) {
						Definition def = defManager.getDefinition(cscClass);
						ComponentServiceContext anno = null;
						if (def != null
								&& def
										.isAnnotationPresent(ComponentServiceContext.class)) {
							anno = def
									.getAnnotation(ComponentServiceContext.class);
						}
						if (anno != null) {
							if (anno.beforeConfigure()) {
								// Thực thi đăng kí.
								JGentle.registerAnnotationInCSC(serviceHandler,
										anno, false, null);
							}
						}
					}
				}
				// invoke configure method
				if (objResult != result)
					objResult.configure();
				objectList.add(objResult);
			}
		}
		Context result = null;
		result = buildContext(serviceHandler, serviceProvider, objectList
				.toArray(new Configurable[objectList.size()]));
		/*
		 * Clear toàn bộ config object
		 */
		configObjClassList.clear();
		return result;
	}

	/**
	 * Builds the context.
	 * 
	 * @param serviceHandler
	 *            the aoh
	 * @param serviceProvider
	 *            the service provider
	 * @param configurations
	 *            the configurations
	 * @return the context
	 */
	protected static Context buildContext(ServiceHandler serviceHandler,
			boolean serviceProvider, Configurable... configurations) {

		List<Map<String, Object>> OLArray = new ArrayList<Map<String, Object>>();
		ArrayList<Configurable> cfgInstanceList = new ArrayList<Configurable>();
		for (Configurable config : configurations) {
			try {
				config.setDefinitionManager(serviceHandler
						.getDefinitionManager());
				// Thực thi các xử lý được chỉ định bởi ATC
				config.initAnnotationConfig();
				cfgInstanceList.add(config);
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			OLArray.add(config.getOptionsList());
		}
		Context result = null;
		if (!serviceProvider) {
			result = new ProviderCoreCreator(serviceHandler, OLArray);
		}
		else {
			result = new ServiceProviderImpl(serviceHandler, OLArray);
		}
		((Provider) result).setConfigInstances(cfgInstanceList);
		return result;
	}

	/**
	 * Instantiates an {@link Provider} without startup config.
	 * 
	 * @return the provider
	 */
	@SuppressWarnings("unchecked")
	public static Provider buildProvider() {

		Class<Configurable>[] array = (Class<Configurable>[]) Array
				.newInstance(Class.class, 0);
		return buildProvider(array);
	}

	/**
	 * Instantiates an {@link Provider}.
	 * 
	 * @param configClasses
	 *            a specified configurable class or a set of specified
	 *            configurable classes
	 * @return the provider
	 */
	@SuppressWarnings("unchecked")
	public static Provider buildProvider(Class<?>... configClasses) {

		return (Provider) buildContext(false, null, null,
				(Class<? extends Configurable>[]) configClasses);
	}

	/**
	 * Instantiates an {@link Provider}.
	 * 
	 * @param sh
	 *            the given {@link ServiceHandler} instance.
	 * @param configurations
	 *            the list of {@link Configurable} instance.
	 * @return the provider
	 */
	protected static Provider buildProvider(ServiceHandler sh,
			Configurable... configurations) {

		return (Provider) buildContext(sh, false, configurations);
	}

	/**
	 * Instantiates a {@link ServiceProvider} without startup config.
	 * 
	 * @return the service provider
	 */
	@SuppressWarnings("unchecked")
	public static ServiceProvider buildServiceProvider() {

		Class<Configurable>[] array = (Class<Configurable>[]) Array
				.newInstance(Class.class, 0);
		return JGentle.buildServiceProvider(array);
	}

	/**
	 * Instantiates a {@link ServiceProvider}.
	 * 
	 * @param configClasses
	 *            a specified configurable class or a set of specified
	 *            configurable classes
	 * @return the service provider
	 */
	@SuppressWarnings("unchecked")
	public static ServiceProvider buildServiceProvider(Class... configClasses) {

		return JGentle.buildServiceProvider(null, null, configClasses);
	}

	/**
	 * Instantiates a {@link ServiceProvider}.
	 * 
	 * @param configClasses
	 *            a specified configurable class or a set of specified
	 *            configurable classes
	 * @param argsType
	 *            the argument types of specified constructor of configurable
	 *            class
	 * @param args
	 *            the argument objects need to be passed to constructor of
	 *            configurable class.
	 * @return the service provider
	 */
	@SuppressWarnings("unchecked")
	public static ServiceProvider buildServiceProvider(Class<?>[] argsType,
			Object[] args, Class<? extends Configurable>... configClasses) {

		/*
		 * Khởi tạo services context
		 */
		ServiceProvider result = (ServiceProvider) buildContext(true, argsType,
				args, configClasses);
		/*
		 * Thực thi các xử lý trước khi khởi tạo CSC
		 */
		List<Configurable> configInstances = result.getConfigInstances();
		for (BeforeInitContext obj : JGentle.beforeInitContextList) {
			obj.beforeInitContext(result, configInstances
					.toArray(new Configurable[configInstances.size()]));
		}
		// Thực thi init trên csc
		// Thực thi init method trên mỗi CSC của services context vừa khởi tạo.
		Collection<ComponentServiceContextType<Configurable>> csclist = result
				.getCSCList().values();
		for (ComponentServiceContextType<Configurable> csc : csclist) {
			invokeCSCInit(result, csc, configInstances);
		}
		/*
		 * Thực thi các xử lý khởi tạo CSC
		 */
		List<Class<? extends ComponentServiceContextType<?>>> cscClassList = null;
		cscClassList = new LinkedList<Class<? extends ComponentServiceContextType<?>>>();
		for (Configurable instance : configInstances) {
			cscClassList.addAll(instance.getCscClassList());
		}
		// Duyệt qua từng danh sách Object Class của danh sách các CSC class.
		for (Class<? extends ComponentServiceContextType<?>> clazz : cscClassList) {
			Definition defCSC = result.getDefinitionManager().getDefinition(
					clazz);
			// Khởi tạo CSC và thực thi init
			ComponentServiceContextType<Configurable> comp = null;
			comp = (ComponentServiceContextType<Configurable>) result
					.getBean(clazz);
			invokeCSCInit(result, comp, configInstances);
			/*
			 * Xử lý thông tin chỉ định trong @ComponentServiceContext nếu có
			 */
			ComponentServiceContext anno = null;
			if (defCSC.isAnnotationPresent(ComponentServiceContext.class)) {
				anno = defCSC.getAnnotation(ComponentServiceContext.class);
			}
			if (anno != null) {
				// Thực thi đăng kí annotation nếu có.
				if (!anno.beforeConfigure()) {
					JGentle.registerAnnotationInCSC(result.getServiceHandler(),
							anno, true, result);
				}
				// Khởi tạo và đăng kí service Class
				Class<? extends ServiceClass> scClazz = anno.serviceClass();
				if (!result.getDefinitionManager().containsDefinition(scClazz)) {
					result.getDefinitionManager().loadDefinition(scClazz);
				}
				if (result.getDefinitionManager().getDefinition(scClazz)
						.isAnnotationPresent(BeanServices.class)) {
					throw new JGentleRuntimeException(
							"Service Class "
									+ scClazz.getName()
									+ " must be annotated with @BeanServices annotation !");
				}
				String domain = result.getDefinitionManager().getDefinition(
						scClazz).getAnnotation(BeanServices.class).domain();
				ServiceHandler aoh = result.getServiceHandler();
				if (!aoh.containsDomain(domain)) {
					try {
						aoh.newDomain(domain);
					}
					catch (JGentleException e) {
						e.printStackTrace();
					}
				}
				aoh.addService(result, scClazz, domain);
			}
			// Thực thi add CSC vào ServiceProvider hiện hành.
			result.addCSContext(
					anno == null
							|| (anno != null && anno.value().equals(
									NullClass.class)) ? clazz : anno.value(),
					comp);
		}
		/*
		 * Xóa bỏ toàn bộ các beforeInitContext object
		 */
		beforeInitContextList.clear();
		return result;
	}

	/**
	 * Removes all registered {@link BeforeConfigure} instances.
	 */
	public static void clearAllBeforeConfig() {

		JGentle.getBeforeConfigBeanList().clear();
	}

	/**
	 * Removes all registered {@link BeforeInitContext} instances.
	 */
	public static void clearAllBeforeInitContext() {

		JGentle.beforeInitContextList.clear();
	}

	/**
	 * Returns <b>true</b> if the configurable class bound to the given
	 * interface type is registered.
	 * 
	 * @param interfaze
	 *            the given interface type
	 * @return true, if contains configurable class
	 */
	public static boolean containsConfigClass(Class<?> interfaze) {

		synchronized (JGentle.configObjClassList) {
			return JGentle.configObjClassList.containsKey(interfaze);
		}
	}

	/**
	 * Returns the number of {@link BeforeConfigure} instances.
	 * 
	 * @return int
	 */
	public static int countBeforeConfig() {

		return JGentle.beforeConfigBeanList.size();
	}

	/**
	 * Returns the number of {@link BeforeInitContext} instances.
	 * 
	 * @return int
	 */
	public static int countBeforeInitContext() {

		return JGentle.beforeInitContextList.size();
	}

	/**
	 * Returns the {@link List} containing all current {@link BeforeConfigure}
	 * instances.
	 * 
	 * @return Returns a {@link List} if it existed, if not, returns an empty
	 *         {@link List}
	 */
	public static List<BeforeConfigure> getBeforeConfigBeanList() {

		return beforeConfigBeanList;
	}

	/**
	 * Returns the {@link List} containing all current {@link BeforeInitContext}
	 * instances.
	 * 
	 * @return the beforeInitContextList
	 */
	public static List<BeforeInitContext> getBeforeInitContextList() {

		return beforeInitContextList;
	}

	/**
	 * Returns the configurable class bound to the given interface type.
	 * 
	 * @param interfaze
	 *            the given interface type
	 * @return returns the object class if it existed, if not, one exception
	 *         will be thrown.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> getConfigClass(Class<T> interfaze) {

		Assertor.notNull(interfaze);
		if (!JGentle.containsConfigClass(interfaze)) {
			throw new JGentleRuntimeException(
					"Does not found config class with type " + interfaze);
		}
		return (Class<? extends T>) JGentle.configObjClassList.get(interfaze);
	}

	/**
	 * Executes <code>init method</code> of specified
	 * {@link ComponentServiceContextType}
	 * 
	 * @param provider
	 *            the current {@link ServiceProvider} đối tượng
	 * @param csc
	 *            the specified {@link ComponentServiceContextType}
	 * @param configInstances
	 *            List of all configurable instances of current
	 *            {@link ServiceProvider}. They will be filtered in order to
	 *            correspond to each specified
	 *            {@link ComponentServiceContextType} instance.
	 */
	protected static void invokeCSCInit(ServiceProvider provider,
			ComponentServiceContextType<Configurable> csc,
			List<Configurable> configInstances) {

		Class<? extends Configurable> rct = csc.returnClassType();
		List<Configurable> configList = new ArrayList<Configurable>();
		// Thực thi lọc các config instances phù hợp.
		for (Configurable config : configInstances) {
			if (ReflectUtils.isCast(rct, config)) {
				configList.add(config);
			}
		}
		// Thực thi init method trên CSC
		csc.init(provider, (ArrayList<Configurable>) configList);
	}

	/**
	 * Registers all annotations specifying in {@link ComponentServiceContext}
	 * 
	 * @param serviceHandler
	 *            the {@link ServiceHandler}
	 * @param annotation
	 *            the {@link ComponentServiceContext} annotation.
	 * @param getProvider
	 *            if <b>true</b>, the suitable validator will be gotten from the
	 *            given {@link Provider}, if <b>false</b>, the instance will be
	 *            instantiated basing on <code>default constructor</code>
	 *            specifying in corresponding object class.
	 * @param provider
	 *            the specified {@link Provider} container, if
	 *            <code>getInject argument</code> is <b>true</b> and provider is
	 *            null, one runtime-exception will be thrown.
	 */
	@SuppressWarnings("unchecked")
	private static void registerAnnotationInCSC(ServiceHandler serviceHandler,
			ComponentServiceContext annotation, boolean getProvider,
			Provider provider) {

		if (getProvider == true && provider == null) {
			throw new JGentleRuntimeException(
					"Provider must not be null while getProvider is true.");
		}
		Class<Annotation>[] annoList = (Class<Annotation>[]) annotation
				.annotations();
		Class<?>[] validators = annotation.validators();
		if (annoList.length != 0) {
			if (annoList.length < validators.length) {
				throw new JGentleRuntimeException(
						"invalid validators configuration in annotation: "
								+ annotation);
			}
			for (int i = 0; i < annoList.length; i++) {
				if (i < validators.length) {
					AnnotationValidator<Annotation> validator;
					if (getProvider == false)
						validator = (AnnotationValidator<Annotation>) ReflectUtils
								.createInstance(validators[i]);
					else {
						validator = (AnnotationValidator<Annotation>) provider
								.getBean(validators[i]);
					}
					serviceHandler.registerAnnotation(annoList[i], validator);
				}
				else {
					serviceHandler.registerAnnotation(annoList[i]);
				}
			}
		}
	}

	/**
	 * Registers all system annotations.
	 * 
	 * @param serviceHandler
	 *            the given {@link ServiceHandler}
	 */
	private static void registerAnnotations(ServiceHandler serviceHandler) {

		// Đăng kí thông tin các annotation của core JGentle vào danh sách
		// annotation registered
		serviceHandler.registerAnnotations(RegisterAnnotationInjecting.class);
		// Đăng kí thông tin tất cả các annotation của AOP System vào danh sách
		// annotation registered.
		serviceHandler.registerAnnotations(RegisterAnnotationAOP.class);
		// Đăng kí thông tin tất cả các annotation của JGentle Context vào danh
		// sách annotation registered.
		serviceHandler.registerAnnotations(RegisterAnnotationContext.class);
	}

	/**
	 * Removes a given {@link BeforeConfigure} instance.
	 * 
	 * @param instance
	 *            the instance need to be removed.
	 */
	public static void removeBeforeConfig(BeforeConfigure instance) {

		Assertor.notNull(instance);
		if (JGentle.beforeConfigBeanList.contains(instance)) {
			JGentle.beforeConfigBeanList.remove(instance);
		}
		else {
			throw new JGentleRuntimeException(
					"BeforeConfigure instance is not existed.");
		}
	}

	/**
	 * Removes a given {@link BeforeInitContext} instance.
	 * 
	 * @param instance
	 *            the {@link BeforeInitContext} instance need to be removed
	 */
	public static void removeBeforeInitContext(BeforeInitContext instance) {

		Assertor.notNull(instance);
		if (JGentle.beforeInitContextList.contains(instance)) {
			JGentle.beforeInitContextList.remove(instance);
		}
		else {
			throw new JGentleRuntimeException(
					"BeforeInitContext instance is not existed.");
		}
	}

	/**
	 * Removes a given configurable class from registered list.
	 * 
	 * @param interfaze
	 *            the interface type of configurable class.
	 * @return returns the removed configigurabke class if it existed, otherwise
	 *         returns null.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T removeConfigClass(Class<T> interfaze) {

		Assertor.notNull(interfaze);
		if (!interfaze.isInterface()) {
			throw new JGentleRuntimeException(interfaze.toString()
					+ " must be a interface.");
		}
		return (T) JGentle.configObjClassList.remove(interfaze);
	}
}
