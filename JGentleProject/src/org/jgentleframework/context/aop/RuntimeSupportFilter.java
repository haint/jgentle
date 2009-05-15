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

import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.core.intercept.support.Matcher;

/**
 * Represents a runtime support filter.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 16, 2008
 * @see StaticallySupportFilter
 * @see DynamicallySupportFilter
 */
public interface RuntimeSupportFilter<T extends Matching> extends Filter<T> {
	/**
	 * Is this {@link Matcher} dynamic, that is, must a final call be made on
	 * the {@link Matcher#matches(Object)} method at runtime.
	 * 
	 * @return whether or not a runtime match via the
	 *         {@link Matcher#matches(Object)} method is required.
	 */
	boolean isRuntime();
}
