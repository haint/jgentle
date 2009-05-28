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

import org.jgentleframework.context.aop.support.ClassMatching;

/**
 * Filter that restricts matching of a pointcut or introduction to a given set
 * of target classes.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 14, 2008
 */
public interface ClassFilter extends Filter<ClassMatching> {
	/**
	 * Should the pointcut apply to the given interface, target class or
	 * annotation?
	 * 
	 * @param matching
	 *            the candidate {@link ClassMatching}
	 * @return whether the interceptor should apply to the given
	 *         {@link ClassMatching}
	 */
	boolean matches(ClassMatching matching);

	/**
	 * Canonical instance of a ClassFilter that matches all classes.
	 */
	ClassFilter	TRUE	= TrueClassFilter.singleton();
}
