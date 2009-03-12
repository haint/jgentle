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

import java.lang.reflect.AccessibleObject;

import net.sf.cglib.proxy.MethodProxy;

import org.aopalliance.intercept.MethodInvocation;

/**
 * The Class AbstractMethodInvocation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 26, 2008
 */
public abstract class AbstractMethodInvocation implements MethodInvocation {
	/** The proxy. */
	final Object		proxy;
	/** The arguments. */
	final Object[]		arguments;
	/** The method proxy. */
	final MethodProxy	methodProxy;

	/**
	 * Instantiates a new intercepted method invocation.
	 * 
	 * @param proxy
	 *            the proxy
	 * @param methodProxy
	 *            the method proxy
	 * @param arguments
	 *            the arguments
	 */
	public AbstractMethodInvocation(Object proxy, MethodProxy methodProxy,
			Object[] arguments) {

		this.proxy = proxy;
		this.methodProxy = methodProxy;
		this.arguments = arguments != null ? arguments.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.intercept.Invocation#getArguments()
	 */
	public Object[] getArguments() {

		return arguments != null ? arguments.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.intercept.Joinpoint#getThis()
	 */
	public Object getThis() {

		return proxy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.aopalliance.intercept.Joinpoint#getStaticPart()
	 */
	public AccessibleObject getStaticPart() {

		return getMethod();
	}
}
