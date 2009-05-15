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
package org.jgentleframework.integration.remoting.rmi.support;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.jgentleframework.integration.remoting.RemoteInvocation;
import org.jgentleframework.integration.remoting.WrappingProcessor;
import org.jgentleframework.utils.Assertor;

/**
 * The Class RmiWrappingProcessor.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 * href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 16, 2008
 */
public class RmiWrappingProcessor implements WrappingProcessor {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.integration.remoting.rmi.support.WrappingProcessor#invoke(org.jgentleframework.integration.remoting.RemoteInvocation,
	 *      java.lang.Object)
	 */
	@Override
	public Object invoke(RemoteInvocation invocation, Object targetObject)
			throws RemoteException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Assertor.notNull(invocation, "The remote invocation must not be null!");
		Assertor.notNull(targetObject, "The target object must not be null!");
		Object result = null;
		result = invocation.invoke(targetObject);
		return result;
	}
}
