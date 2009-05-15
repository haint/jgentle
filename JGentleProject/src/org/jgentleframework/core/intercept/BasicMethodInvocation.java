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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * This is an implementation of {@link MethodInvocation} interface of
 * AOPAlliance. A method invocation is a joinpoint and can be intercepted by a
 * method interceptor.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 29, 2007
 * @see MethodInvocation
 * @see MethodInterceptor
 */
public class BasicMethodInvocation implements MethodInvocation {
	/** The this object. */
	Object		thisObject	= null;

	/** The method. */
	Method		method		= null;

	/** The arguments. */
	Object[]	arguments	= null;

	/**
	 * Constructor.
	 * 
	 * @param thisObject
	 *            the this object
	 * @param method
	 *            the method
	 * @param arguments
	 *            the arguments
	 */
	public BasicMethodInvocation(Object thisObject, Method method,
			Object[] arguments) {

		this.thisObject = thisObject;
		this.method = method;
		this.arguments = arguments != null ? arguments.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInvocation#getMethod()
	 */
	@Override
	public Method getMethod() {

		return this.method;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.Invocation#getArguments()
	 */
	@Override
	public Object[] getArguments() {

		return this.arguments != null ? this.arguments.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.Joinpoint#getStaticPart()
	 */
	@Override
	public AccessibleObject getStaticPart() {

		method.setAccessible(true);
		return method;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.Joinpoint#getThis()
	 */
	@Override
	public Object getThis() {

		return this.thisObject;
	}

	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.Joinpoint#proceed()
	 */
	@Override
	public Object proceed() throws Throwable {

		return this.method.invoke(this.thisObject, this.arguments);
	}
}
