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

import java.lang.reflect.Constructor;

import org.jgentleframework.context.aop.support.MethodConstructorMatching;

/**
 * Filter that restricts matching of a pointcut or introduction to a given set
 * of target {@link Constructor}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 15, 2008
 */
public interface ConstructorFilter extends
		RuntimeSupportFilter<MethodConstructorMatching<Constructor<?>>> {
	/**
	 * Perform static or dynamic checking whether the given method matches.If
	 * the {@link #isRuntime()} method returns <b>true</b>, a runtime check on
	 * this method call) will be made.
	 * 
	 * @param matching
	 *            the candidate matching
	 * @return whether or not this constructor matches.
	 */
	boolean matches(MethodConstructorMatching<Constructor<?>> matching);

	/**
	 * Canonical instance of a {@link ConstructorFilter} that matches all
	 * constructors.
	 */
	ConstructorFilter	TRUE	= TrueConstructorFilter.singleton();
}
