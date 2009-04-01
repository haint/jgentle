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
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.Interceptor;
import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Provides some methods in order to manage {@link Interceptor} and
 * {@link Matcher}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 20, 2008
 */
public interface IAbstractServiceManagement {
	/**
	 * Returns <b>true</b> if given {@link Interceptor} is registered,
	 * <b>false</b> otherwise.
	 * 
	 * @param matcher
	 *            the matcher of specified interceptor
	 * @param interceptor
	 *            the interceptor need to be checked.
	 */
	public boolean isRegistered(Matcher<Definition> matcher, Object interceptor);

	/**
	 * Returns <b>true</b> if given matcher is registered. Otherwise, returns
	 * <b>false</b>.
	 * 
	 * @param matcher
	 *            the matcher
	 */
	public boolean isRegisteredMatcher(Matcher<Definition> matcher);

	/**
	 * Unregisters an specified registered {@link Interceptor}.
	 */
	public void unregisters(Object interceptor);

	/**
	 * Unregisters all {@link Interceptor} according to given matcher
	 * 
	 * @param matcher
	 *            the matcher.
	 */
	public void unregisters(Matcher<Definition> matcher);

	/**
	 * Registers an {@link Interceptor} to factory of container
	 * 
	 * @param matchers
	 *            the corresponding matchers of interceptor.
	 * @param interceptor
	 *            the interceptor need to be registered.
	 */
	public void registers(Matcher<Definition>[] matchers, Object interceptor);

	/**
	 * Returns the matcher of given {@link Definition} which is cached in.
	 * 
	 * @param definition
	 *            the given definition
	 * @return returns a corresponding {@link Matcher} if it exists, if not,
	 *         returns <b>null</b>.
	 */
	public Matcher<Definition> getCachedMatcherOf(Definition definition);

	/**
	 * Returns a list containing all matchers according to given registered
	 * {@link Interceptor}
	 * 
	 * @param interceptor
	 *            the interceptor
	 * @return returns a list of corresponding {@link Matcher} if it exists, if
	 *         not, returns an empty list.
	 */
	public List<Matcher<Definition>> getMatcherOf(Object interceptor);

	/**
	 * Refresh matcher cache, if does not found any corresponding matcher
	 * existed in registered interceptor list, this method will automatically
	 * remove all current matcher according to given {@link Definition}.
	 * 
	 * @param definition
	 *            the definition
	 */
	public void refreshMatcherCache(Definition definition);

	/**
	 * Gets the matcher cache.
	 */
	public Map<Definition, Matcher<Definition>> getMatcherCache();

	/**
	 * Gets all interceptors bound to the given {@link Matcher}
	 * 
	 * @param matcher
	 *            the given {@link Matcher}
	 * @param result
	 *            the {@link List} will contains returned result of
	 *            interceptors.
	 */
	public void getInterceptorFromMatcher(Matcher<Definition> matcher,
			List<Interceptor> result);

	/**
	 * Gets the interceptors from matcher.
	 * 
	 * @param matchers
	 *            the matchers
	 * @return the interceptors from matcher
	 */
	public Interceptor[] getInterceptorsFromMatcher(
			ArrayList<Matcher<Definition>> matchers);
}