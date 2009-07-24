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
package org.jgentleframework.configure;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.annotation.Block;
import org.jgentleframework.configure.objectmeta.ObjectBlock;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This proxy class is responsible for configurable object instantiation
 * containing all information of configured data.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 14, 2008
 * @see MethodInterceptor
 */
public class ConfigurationProxy implements MethodInterceptor {
	/** The target class. */
	Class<? extends Configurable>	targetClass		= null;

	/** The options list. */
	Map<String, Object>				optionsList		= null;

	/** The config obj list. */
	Map<Class<?>, Object>			configObjList	= null;

	/** The obj block list. */
	List<ObjectBlock>				objBlockList	= new LinkedList<ObjectBlock>();

	public static final Log			log				= LogFactory
															.getLog(ConfigurationProxy.class);

	/**
	 * Constructor.
	 * 
	 * @param targetClass
	 *            object-class của target class.
	 * @param optionsList
	 *            the options list
	 * @param configObjList
	 *            the config obj list
	 */
	public ConfigurationProxy(Class<? extends Configurable> targetClass,
			Map<String, Object> optionsList, Map<Class<?>, Object> configObjList) {

		this.targetClass = targetClass;
		this.optionsList = optionsList;
		this.configObjList = configObjList;
	}

	/**
	 * Creates proxy basing on the given configurable target class.
	 * 
	 * @param targetClass
	 *            the given configurable target class.
	 * @param argsType
	 *            the args type
	 * @param args
	 *            the args
	 * @return returns the <code>proxy instance</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Configurable> T createProxy(Class<T> targetClass,
			Class<?>[] argsType, Object[] args) {

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setInterfaces(new Class[] { ConfigModule.class });
		// Kiểm tra interface cài đặt
		Set<Class<?>> interfaceList = ReflectUtils.getAllInterfaces(
				targetClass, true);
		if (AbstractConfig.class.isAssignableFrom(targetClass)) {
			interfaceList.remove(SystemConfig.class);
		}
		HashMap<String, Object> optionsList = new HashMap<String, Object>();
		HashMap<Class<?>, Object> configObjList = new HashMap<Class<?>, Object>();
		for (Class<?> interfaze : interfaceList) {
			if (JGentle.containsConfigClass(interfaze)) {
				Class<?> clazz = JGentle.getConfigClass(interfaze);
				Object obj = null;
				obj = ReflectUtils.createInstance(clazz);
				// Nếu config class extends từ AbstractConfigModule
				if (AbstractConfigModule.class.isAssignableFrom(clazz)) {
					try {
						((AbstractConfigModule) obj).setOptionsList(
								optionsList, ReflectUtils.getSupportedMethod(
										targetClass, "configure", null));
					}
					catch (NoSuchMethodException e) {
						if (log.isFatalEnabled()) {
							log.fatal(
									"Could not found 'configure' method on configurable class ["
											+ targetClass + "]", e);
						}
					}
				}
				configObjList.put(interfaze, obj);
			}
		}
		enhancer.setCallback(new ConfigurationProxy(targetClass, optionsList,
				configObjList));
		Object result = null;
		if (argsType != null && args != null) {
			result = enhancer.create(argsType, args);
		}
		else {
			result = enhancer.create();
		}
		// thiết lập optionsList trong trường hợp targetClass kế thừa từ
		// AbstractConfig
		if (AbstractConfig.class.isAssignableFrom(targetClass)) {
			try {
				((AbstractConfig) result).setOptionsList(optionsList,
						ReflectUtils.getSupportedMethod(targetClass,
								"configure", null));
			}
			catch (NoSuchMethodException e) {
				if (log.isFatalEnabled()) {
					log.fatal(
							"Could not found 'configure' method on configurable class ["
									+ targetClass + "]", e);
				}
			}
		}
		return (T) result;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[],
	 * net.sf.cglib.proxy.MethodProxy)
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		if (Modifier.isAbstract(method.getModifiers())) {
			if (method.isAnnotationPresent(Block.class)) {
				Block anno = method.getAnnotation(Block.class);
				Class<?>[] clazzList = anno.value();
				ObjectBlock objBlock = new ObjectBlock(method, clazzList);
				this.objBlockList.add(objBlock);
			}
			// thực thi hàm getOptionsList trên ConfigModule interface
			if (method.getName().equals("getOptionsList")
					&& method.getParameterTypes().length == 0) {
				checkBlock(method);
				return this.optionsList;
			}
			else if (method.getName().equals("getTargetClass")
					&& method.getParameterTypes().length == 0) {
				checkBlock(method);
				return this.targetClass;
			}
			else if (method.getName().equals("getConfigInstance")
					&& method.getParameterTypes().length == 1) {
				checkBlock(method);
				return this.configObjList.get(args[0]);
			}
			else {
				if (!this.objBlockList.isEmpty()) {
					ObjectBlock objb = this.objBlockList.get(this.configObjList
							.size() - 1);
					List<Class<?>> clazzList = null;
					clazzList = objb.getBlockList();
					for (Class<?> clazz : clazzList) {
						Object objConfig = this.configObjList.get(clazz);
						List<Method> methodList = Arrays.asList(objConfig
								.getClass().getMethods());
						if (methodList.contains(method)) {
							checkBlock(method);
							return method.invoke(objConfig, args);
						}
						else {
							continue;
						}
					}
				}
				// Nếu không có
				for (Class<?> clazz : this.configObjList.keySet()) {
					Object objConfig = this.configObjList.get(clazz);
					List<Method> methodList = Arrays.asList(ReflectUtils
							.getAllDeclaredMethods(clazz));
					if (methodList.contains(method)) {
						checkBlock(method);
						return method.invoke(objConfig, args);
					}
					else {
						continue;
					}
				}
				throw new NoSuchMethodException("Could not found " + method
						+ " method!");
			}
		}
		else {
			checkBlock(method);
			return proxy.invokeSuper(obj, args);
		}
	}

	/**
	 * Checks the current block data corresponding to the given method.
	 * 
	 * @param method
	 *            the given method need to be checked.
	 */
	private void checkBlock(Method method) {

		if (!this.objBlockList.isEmpty()) {
			ObjectBlock objb = this.objBlockList
					.get(this.objBlockList.size() - 1);
			if (objb.getMethod().equals(method)) {
				this.objBlockList.remove(objb);
			}
		}
	}
}
