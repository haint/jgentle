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
package org.jgentleframework.configure.enums;

import java.util.Map;
import java.util.TooManyListenersException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.InvalidRemovingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.injecting.scope.ScopeInstanceImpl;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.InvalidOperationException;
import org.jgentleframework.core.factory.BeanCreationProcessor;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.NullClass;
import org.jgentleframework.web.WebProvider;

/**
 * This enum contains some constants representing all common supported scopes of
 * core JGentle provider. Among of them are only available corresponding to a
 * specified {@link Provider}, but others are supported by other
 * {@link Provider} type, for example, core {@link Provider} only supports
 * {@link #SINGLETON} and {@link #PROTOTYPE} scope but {@link WebProvider} not
 * only supports {@link #SINGLETON}, {@link #PROTOTYPE} scope, but also supports
 * {@link #REQUEST}, {@link #SESSION} and {@link #APPLICATION} scope.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 1, 2007
 * @see ScopeImplementation
 */
public enum Scope implements ScopeImplementation {
	/**
	 * Indicates one bean is a singleton-scoped bean. When a bean is a
	 * singleton, only one shared instance of the bean will be managed, and all
	 * requests for beans bound to an id of bean's {@link Definition} or its
	 * mapping type will result in that one specific bean instance being
	 * returned by the JGentle provider.
	 */
	SINGLETON,
	/**
	 * Indicates one bean is a protoype_scoped bean. Prototype scope of bean
	 * deployment results in the creation of a new bean instance every time a
	 * request for that specific bean is made.
	 */
	PROTOTYPE,
	/**
	 * The request scope.
	 */
	REQUEST,
	/**
	 * The session scope.
	 */
	SESSION,
	/**
	 * The application scope (Servlet context)
	 */
	APPLICATION,
	/**
	 * The unspecified scope
	 */
	UNSPECIFIED;
	private static final Log	log	= LogFactory.getLog(Scope.class);

	/**
	 * Constructor
	 */
	Scope() {

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#putBean
	 * (java.lang.String, java.lang.Object,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object putBean(String scopeName, Object bean,
			ObjectBeanFactory objFactory)
			throws InvalidAddingOperationException {

		Object result = null;
		Provider provider = objFactory.getProvider();
		Map<String, ScopeInstance> scopeList = objFactory.getScopeList();
		Map<String, Object> mapDirectList = objFactory.getMapDirectList();
		synchronized (scopeList) {
			synchronized (mapDirectList) {
				Scope scope = null;
				if (!scopeList.containsKey(scopeName)) {
					scopeList.put(scopeName, this);
				}
				scope = (Scope) scopeList.get(scopeName);
				// Nếu scope là Singleton
				if (scope.equals(Scope.SINGLETON)) {
					result = mapDirectList.put(scopeName, bean);
				}
				else if (scope.equals(Scope.REQUEST)
						|| scope.equals(Scope.SESSION)
						|| scope.equals(Scope.APPLICATION)) {
					if (!ReflectUtils.isCast(WebProvider.class, provider)) {
						if (log.isErrorEnabled()) {
							log
									.error(
											"This container does not support REQUEST, SESSION or APPLICATION scope.",
											new InvalidOperationException());
						}
					}
					// TODO Thực thi trên các scope khác.
				}
				else {
					if (log.isErrorEnabled()) {
						log.error("The specified scope is invalid !",
								new InvalidOperationException());
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#getBean
	 * (org.jgentleframework.context.support.Selector, java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object getBean(Selector selector, String scopeName,
			ObjectBeanFactory objFactory) {

		Object result = null;
		Provider provider = objFactory.getProvider();
		Map<String, ScopeInstance> scopeList = objFactory.getScopeList();
		Map<String, Object> mapDirectList = objFactory.getMapDirectList();
		// Nếu là singleton
		synchronized (scopeList) {
			synchronized (mapDirectList) {
				Scope scope = (Scope) scopeList.get(scopeName);
				if (scope.equals(Scope.SINGLETON)) {
					if (mapDirectList.containsKey(scopeName)) {
						return mapDirectList.get(scopeName);
					}
					if (selector instanceof CoreInstantiationSelectorImpl) {
						CoreInstantiationSelector coreSelector = (CoreInstantiationSelector) selector;
						String mappingName = coreSelector.getMappingName();
						if (mappingName != null && !mappingName.isEmpty()) {
							if (mappingName.indexOf(":") != -1) {
								if (mapDirectList.containsKey(mappingName
										.replace(":", "_"))) {
									return mapDirectList.get(mappingName
											.replace(":", "_"));
								}
							}
							result = provider.getRefInstance(mappingName);
						}
					}
					if (result == null) {
						try {
							result = provider.getServiceHandler()
									.getService(this,
											BeanCreationProcessor.class,
											selector);
						}
						catch (TooManyListenersException e) {
							if (log.isErrorEnabled()) {
								log.error(e.getMessage(), e);
							}
						}
						if (result == NullClass.class)
							result = null;
						// Đưa object vừa khởi tạo vào danh sách singleton cache
						mapDirectList.put(scopeName, result);
					}
				}
				// if prototype
				else if (scope.equals(Scope.PROTOTYPE)) {
					try {
						result = provider.getServiceHandler().getService(this,
								BeanCreationProcessor.class, selector);
					}
					catch (TooManyListenersException e) {
						if (log.isErrorEnabled()) {
							log.error(e.getMessage(), e);
						}
					}
					if (result == NullClass.class)
						result = null;
					return result;
				}
				else if (scope.equals(Scope.REQUEST)
						|| scope.equals(Scope.SESSION)
						|| scope.equals(Scope.APPLICATION)) {
					if (!ReflectUtils.isCast(WebProvider.class, provider)) {
						if (log.isErrorEnabled()) {
							log
									.error(
											"This container does not support REQUEST, SESSION or APPLICATION scope.",
											new InvalidOperationException());
						}
					}
					// TODO Thực thi trên các scope khác.
				}
				else {
					if (log.isErrorEnabled()) {
						log.error("The specified scope is invalid !",
								new InvalidOperationException());
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#remove
	 * (java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object remove(String scopeName, ObjectBeanFactory objFactory)
			throws InvalidRemovingOperationException {

		Object result = null;
		Provider provider = objFactory.getProvider();
		Map<String, ScopeInstance> scopeList = objFactory.getScopeList();
		Map<String, Object> mapDirectList = objFactory.getMapDirectList();
		synchronized (scopeList) {
			synchronized (mapDirectList) {
				if (scopeList.containsKey(scopeName)) {
					Scope scope = (Scope) scopeList.get(scopeName);
					scopeList.remove(scopeName);
					if (scope.equals(Scope.PROTOTYPE)) {
					}
					else if (scope.equals(Scope.SINGLETON)) {
						result = mapDirectList.remove(scopeName);
						return result;
					}
					else if (scope.equals(Scope.REQUEST)
							|| scope.equals(Scope.SESSION)
							|| scope.equals(Scope.APPLICATION)) {
						if (!ReflectUtils.isCast(WebProvider.class, provider)) {
							if (log.isErrorEnabled()) {
								log
										.error(
												"This container does not support REQUEST, SESSION or APPLICATION scope.",
												new InvalidOperationException());
							}
						}
						// TODO Thực thi trên các scope khác.
					}
				}
				else {
					return null;
				}
			}
		}
		return result;
	}

	/**
	 * Returns a <code>custom scope</code> instance according to indicated
	 * string of specified scope instance.
	 * 
	 * @param scope
	 *            the indicated string
	 * @return {@link ScopeInstance}
	 */
	public static ScopeInstance in(String scope) {

		return new ScopeInstanceImpl(scope);
	}
}
