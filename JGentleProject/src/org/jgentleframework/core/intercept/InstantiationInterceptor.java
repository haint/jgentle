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
package org.jgentleframework.core.intercept;

import org.aopalliance.intercept.Interceptor;
import org.jgentleframework.configure.enums.MetadataKey;

/**
 * The Interface InstantiationInterceptor.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jul 29, 2008
 * @see Interceptor
 * @see ObjectInstantiation
 */
public interface InstantiationInterceptor extends Interceptor {
	/**
	 * This callback method will be automatically invoked in order to
	 * instantiate bean instance.
	 * <p>
	 * The returned bean instance may be a wrapper around the previous result
	 * (the bean instance was instantiated by previous
	 * {@link InstantiationInterceptor}. In order to get the previous object
	 * instance, access to metadata of given {@link ObjectInstantiation} object.
	 * <p>
	 * ex:
	 * <code><i>ObjectInstantiation</i>.getMetadata(MetadataKey.PREVIOUS_RESULT);</code>
	 * 
	 * @param oi
	 *            the {@link ObjectInstantiation}
	 * @return the object bean
	 * @see MetadataKey
	 */
	public Object instantiate(ObjectInstantiation oi) throws Throwable;
}
