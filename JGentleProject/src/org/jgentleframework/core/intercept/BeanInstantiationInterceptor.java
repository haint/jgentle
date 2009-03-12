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
package org.jgentleframework.core.intercept;

/**
 * BeanInstantiationInterceptor is an extension of
 * {@link InstantiationInterceptor}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2008
 */
public interface BeanInstantiationInterceptor extends
		InstantiationInterceptor {
//	/**
//	 * Final instantiation denotes that instantiated bean results of this
//	 * {@link InstantiationInterceptor} are not allowed to be input of the
//	 * other. In this case, results of them are called as final results.
//	 * Otherwise, if indicates to return <b>false</b>, all bean results of this
//	 * <code>Instantiation Interceptor</code> may become to be input of the
//	 * others. In this case, the results of this may be a part of final result
//	 * of several <code>Instantiation Interceptors</code> which processed in a
//	 * chain of them.
//	 * 
//	 * @return <b>true</b>, if is final instantiation, <b>false</b> otherwise.
//	 */
//	public boolean isFinalInstantiation();

	/**
	 * Checks if is supported by core.
	 * 
	 * @return <b>true</b>, if is supported by core
	 */
	public boolean isSupportedByCore();
}
