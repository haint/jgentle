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
package org.jgentleframework.context.aop.support;

import org.jgentleframework.context.aop.Pointcut;
import org.jgentleframework.core.intercept.support.CoreIdentification;
import org.jgentleframework.core.intercept.support.Matcher;

/**
 * Represents an MatcherPointcut which is an combination of a {@link Matcher}
 * and a {@link Pointcut}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 16, 2008
 * @see Matcher
 * @see Pointcut
 */
public interface MatcherPointcut<T, V extends Matching> extends Matcher<T>,
		Pointcut<V>, CoreIdentification {
}
