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
package org.jgentleframework.context.aop;

import org.jgentleframework.context.aop.support.Matching;

/**
 * Represents a dinamically filter.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 17, 2008
 * @see RuntimeSupportFilter
 * @see StaticallySupportFilter
 */
public abstract class DynamicallySupportFilter<T extends Matching> implements
		RuntimeSupportFilter<T> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.RuntimeSupportFilter#isRuntime()
	 */
	@Override
	public boolean isRuntime() {

		return true;
	}
}
