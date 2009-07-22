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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.Interceptor;
import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor;
import org.jgentleframework.context.injecting.AppropriateScopeNameClass;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.context.support.InstantiationSelector;
import org.jgentleframework.context.support.InstantiationSelectorImpl;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.core.intercept.support.AbstractMatcher;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.Pair;

/**
 * This abstract class is an extension of {@link Provider}, is responsible for
 * <code>getBeanInstance</code> method overriding. The
 * {@link #getBeanInstance(Class, Class, String, Definition)} method is
 * overrided in order to customize the bean instantiation according as its
 * configuration data. Moreover, this class provides some methods in order to
 * manage customized {@link Interceptor interceptors}, matcher cache, ...
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 8, 2008
 * @see Provider
 * @see IAbstractServiceManagement
 */
public abstract class AbstractServiceManagement extends ProviderCoreCreator
		implements IAbstractServiceManagement {
	/**
	 * Constructor.
	 * 
	 * @param serviceHandler
	 *            the service handler
	 * @param OLArray
	 *            the oL array
	 */
	public AbstractServiceManagement(ServiceHandler serviceHandler,
			List<Map<String, Object>> OLArray) {

		super(serviceHandler, OLArray);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.AbstractBeanFactory#getBeanInstance
	 * (java.lang.Class, java.lang.Class, java.lang.String, java.lang.String)
	 */
	@Override
	public Object getBeanInstance(AppropriateScopeNameClass asc) {

		Class<?> type = asc.clazz;
		Class<?> targetClass = asc.targetClass;
		Definition definition = asc.definition;
		String mappingName = asc.ref;
		CoreInstantiationSelector coreSelector = new CoreInstantiationSelectorImpl(
				type, targetClass, asc.ref, definition);
		ScopeInstance scope = null;
		synchronized (scopeList) {
			scope = scopeList.get(asc.scopeName);
		}
		try {
			if (scope != null && !scope.equals(Scope.SINGLETON)) {
				Object result = returnsCachingResult(coreSelector);
				if (result != NULL_SHAREDOBJECT) {
					return result;
				}
			}
		}
		catch (Throwable e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not instantiate bean instance!", e);
			}
		}
		synchronized (this) {
			matcherCache = this.matcherCache == null ? new ConcurrentHashMap<Definition, Matcher<Definition>>()
					: matcherCache;
			interceptorList = this.interceptorList == null ? new HashMap<Matcher<Definition>, ArrayList<Object>>()
					: interceptorList;
		}
		/*
		 * find matcher in cache
		 */
		Matcher<Definition> matcher = getCachedMatcherOf(definition);
		if (matcher == null
				|| (matcher != null && !matcher.matches(definition))) {
			refreshMatcherCache(definition);
			matcher = getCachedMatcherOf(definition);
		}
		/*
		 * instantiate bean instance.
		 */
		Object result = null;
		HashMap<Interceptor, Matcher<Definition>> mapMatcherInterceptor;
		mapMatcherInterceptor = new HashMap<Interceptor, Matcher<Definition>>();
		if (matcher != null && matcher.matches(definition)) {
			List<Matcher<Definition>> mList = new ArrayList<Matcher<Definition>>();
			AbstractMatcher.getSuperMatcher(matcher, mList);
			for (Matcher<Definition> obj : mList) {
				List<Interceptor> icptList = new LinkedList<Interceptor>();
				getInterceptorFromMatcher(obj, icptList);
				for (Interceptor inter : icptList) {
					mapMatcherInterceptor.put(inter, obj);
				}
			}
			if (mapMatcherInterceptor.size() != 0) {
				/*
				 * find arguments
				 */
				Pair<Class<?>[], Object[]> pairCons = findArgsOfDefaultConstructor(definition);
				Class<?>[] argTypes = pairCons.getKeyPair();
				Object[] args = pairCons.getValuePair();
				// create InstantiationSelector
				InstantiationSelector selector = new InstantiationSelectorImpl(
						type, targetClass, mappingName, definition, argTypes,
						args, mapMatcherInterceptor);
				selector.setCachingList(cachingList);
				try {
					return super.getBeanInstance(selector);
				}
				catch (Throwable e) {
					if (log.isFatalEnabled()) {
						log.fatal("Could not instantiate bean instance!", e);
					}
				}
			}
			else {
				result = super.getBeanInstance(type, targetClass, mappingName,
						definition);
			}
		}
		else {
			// create InstantiationSelector
			InstantiationSelector selector = new InstantiationSelectorImpl(
					type, targetClass, mappingName, definition, null, null,
					mapMatcherInterceptor);
			selector.setCachingList(cachingList);
			try {
				return super.getBeanInstance(selector);
			}
			catch (Throwable e) {
				if (log.isFatalEnabled()) {
					log.fatal("Could not instantiate bean instance!", e);
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.ProviderCoreCreator#init(java.util.ArrayList
	 * )
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void init(List<Map<String, Object>> OLArray) {

		ArrayList<ObjectBindingInterceptor> obiList = new ArrayList<ObjectBindingInterceptor>();
		for (Map<String, Object> optionsList : OLArray) {
			obiList.addAll((ArrayList<ObjectBindingInterceptor>) optionsList
					.get(AbstractConfig.OBJECT_BINDING_INTERCEPTOR_LIST));
		}
		for (int i = 0; i < obiList.size(); i++) {
			ObjectBindingInterceptor obi = obiList.get(i);
			registers(obi.getMatchers(), obi.getInterceptor());
		}
		super.init(OLArray);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.IAbstractServiceManagement#
	 * getCachedMatcherOf
	 * (org.jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public synchronized Matcher<Definition> getCachedMatcherOf(
			Definition definition) {

		return this.matcherCache.get(definition);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.IAbstractServiceManagement#
	 * getInterceptorFromMatcher
	 * (org.jgentleframework.core.intercept.support.Matcher, java.util.List)
	 */
	@Override
	public synchronized void getInterceptorFromMatcher(
			Matcher<Definition> matcher, List<Interceptor> result) {

		Assertor.notNull(result);
		Assertor.notNull(matcher);
		if (isRegisteredMatcher(matcher)) {
			ArrayList<Object> list = this.interceptorList.get(matcher);
			if (list != null) {
				for (Object icpt : list) {
					Interceptor interceptor = null;
					if (ReflectUtils.isCast(String.class, icpt)) {
						Object obj = getBean((String) icpt);
						if (obj != null
								&& ReflectUtils.isCast(Interceptor.class, obj))
							interceptor = (Interceptor) obj;
						else {
							throw new InterceptionException(
									"The registered object is not an instance of '"
											+ Interceptor.class + "'!");
						}
					}
					else {
						interceptor = (Interceptor) icpt;
					}
					if (interceptor != null && !result.contains(interceptor)) {
						result.add(interceptor);
					}
					else
						throw new InterceptionException(
								"The registered interceptor must not be null !");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.support.IAbstractServiceManagement#
	 * getInterceptorsFromMatcher(java.util.ArrayList)
	 */
	@Override
	public Interceptor[] getInterceptorsFromMatcher(
			ArrayList<Matcher<Definition>> matchers) {

		Assertor.notNull(matchers);
		List<Interceptor> result = new LinkedList<Interceptor>();
		for (Matcher<Definition> matcher : matchers) {
			getInterceptorFromMatcher(matcher, result);
		}
		return result.toArray(new Interceptor[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.support.IAbstractServiceManagement#
	 * getMatcherCache()
	 */
	@Override
	public Map<Definition, Matcher<Definition>> getMatcherCache() {

		return this.matcherCache;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.IAbstractServiceManagement#getMatcherOf
	 * (org.aopalliance.intercept.Interceptor)
	 */
	@Override
	public synchronized List<Matcher<Definition>> getMatcherOf(
			Object interceptor) {

		List<Matcher<Definition>> list = new ArrayList<Matcher<Definition>>();
		for (Entry<Matcher<Definition>, ArrayList<Object>> entry : this.interceptorList
				.entrySet()) {
			if (entry.getValue().contains(interceptor)) {
				list.add(entry.getKey());
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.IAbstractServiceManagement#isRegistered
	 * (org.jgentleframework.core.intercept.support.Matcher,
	 * org.aopalliance.intercept.Interceptor)
	 */
	@Override
	public synchronized boolean isRegistered(Matcher<Definition> matcher,
			Object interceptor) {

		Assertor.notNull(interceptor);
		Assertor.notNull(matcher);
		if (!this.interceptorList.containsKey(matcher)) {
			return false;
		}
		ArrayList<Object> list = this.interceptorList.get(matcher);
		if (list == null) {
			this.interceptorList.remove(matcher);
			return false;
		}
		else {
			if (list.contains(interceptor)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.IAbstractServiceManagement#
	 * isRegisteredMatcher(org.jgentleframework.core.intercept.support.Matcher)
	 */
	@Override
	public synchronized boolean isRegisteredMatcher(Matcher<Definition> matcher) {

		Assertor.notNull(matcher);
		if (this.interceptorList.containsKey(matcher)) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.support.IAbstractServiceManagement#
	 * refreshMatcherCache
	 * (org.jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public synchronized void refreshMatcherCache(Definition definition) {

		Assertor.notNull(definition);
		this.matcherCache.remove(definition);
		// refresh matcher
		for (Matcher<Definition> imatcher : this.interceptorList.keySet()) {
			if (imatcher != null && imatcher.matches(definition)) {
				if (this.matcherCache.containsKey(definition)) {
					Matcher<Definition> current = this.matcherCache
							.get(definition);
					this.matcherCache.put(definition, current.and(imatcher));
				}
				else {
					this.matcherCache.put(definition, imatcher);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.IAbstractServiceManagement#registers(org
	 * .jgentleframework
	 * .core.intercept.support.Matcher<org.jgentleframework.core
	 * .reflection.metadata.Definition>[], java.lang.Object)
	 */
	@Override
	public synchronized void registers(Matcher<Definition>[] matchers,
			Object interceptor) {

		Assertor.notNull(interceptor);
		Assertor.notNull(matchers);
		for (int i = 0; i < matchers.length; i++) {
			Matcher<Definition> matcher = matchers[i];
			if (isRegisteredMatcher(matcher)) {
				if (isRegistered(matcher, interceptor)) {
					throw new InterceptionException(
							"This instantiation interceptor is registered !");
				}
				else {
					this.interceptorList.get(matcher).add(interceptor);
				}
			}
			else {
				ArrayList<Object> list = null;
				list = new ArrayList<Object>();
				list.add(interceptor);
				this.interceptorList.put(matcher, list);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.IAbstractServiceManagement#unregisters
	 * (org.jgentleframework.core.intercept.support.Matcher)
	 */
	@Override
	public synchronized void unregisters(Matcher<Definition> matcher) {

		Assertor.notNull(matcher);
		this.interceptorList.remove(matcher);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.support.IAbstractServiceManagement#unregisters
	 * (org.aopalliance.intercept.Interceptor)
	 */
	@Override
	public synchronized void unregisters(Object interceptor) {

		Assertor.notNull(interceptor);
		for (Entry<Matcher<Definition>, ArrayList<Object>> entry : this.interceptorList
				.entrySet()) {
			if (entry.getValue().contains(interceptor)) {
				entry.getValue().remove(interceptor);
				if (entry.getValue().isEmpty()) {
					this.interceptorList.remove(entry.getKey());
				}
			}
			else
				continue;
		}
	}
}
