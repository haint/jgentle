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
package org.jgentleframework.context.aop;

import java.lang.reflect.Method;

import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.core.intercept.support.Matcher;

/**
 * Filter that restricts matching of a pointcut or introduction to a given set
 * of methods.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 15, 2008
 * @see RuntimeSupportFilter
 * @see Matcher
 * @see MethodConstructorMatching
 */
public interface MethodFilter extends
		RuntimeSupportFilter<MethodConstructorMatching<Method>>,
		Matcher<MethodConstructorMatching<Method>> {
	/**
	 * Perform static or dynamic checking whether the given
	 * {@link MethodConstructorMatching} matches.If the {@link #isRuntime()}
	 * method returns <b>true</b>, a runtime check on this method call) will be
	 * made.
	 * 
	 * @param matching
	 *            the candidate matching.
	 * @return whether or not given {@link MethodConstructorMatching} matches.
	 */
	boolean matches(MethodConstructorMatching<Method> matching);
}
