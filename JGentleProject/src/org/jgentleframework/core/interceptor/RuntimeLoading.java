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
package org.jgentleframework.core.interceptor;

import org.aopalliance.intercept.Interceptor;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Represents <code>runtime loading</code> status of an {@link Interceptor}
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 20, 2008
 */
public interface RuntimeLoading {
	/**
	 * Returns <b>true</b> if the current {@link Interceptor} instance
	 * implements this interface always reloads the corresponding
	 * {@link Definition} before invoking and <b>false</b> otherwise.
	 */
	public boolean isRuntimeLoading();

	/**
	 * Sets the runtime loading.
	 * 
	 * @param runtimeLoading
	 *            the runtimeLoading to set
	 */
	public void setRuntimeLoading(boolean runtimeLoading);
}
