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
package org.jgentleframework.integration.remoting;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.core.reflection.metadata.SerializableMetadataControl;

/**
 * Represents an remote invocation implementation providing core method
 * invocation properties in a serializable fashion.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 13, 2008
 * @see SerializableMetadataControl
 * @see RemoteInvocation
 */
public class RemoteInvocationImpl extends SerializableMetadataControl implements
		Serializable, RemoteInvocation {
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -7602491858481290717L;

	/** The method name. */
	String						methodName			= null;

	/** The args. */
	Object[]					args				= null;

	/** The args type. */
	Class<?>[]					argsType			= null;

	/**
	 * Instantiates a new remote invocation.
	 * 
	 * @param methodName
	 *            the method name
	 * @param argsType
	 *            the args type
	 * @param args
	 *            the args
	 */
	public RemoteInvocationImpl(String methodName, Class<?>[] argsType,
			Object[] args) {

		this.methodName = methodName;
		this.argsType = argsType != null ? argsType.clone() : null;
		this.args = args != null ? args.clone() : null;
	}

	/**
	 * Instantiates a new remote invocation for the given AOP method invocation.
	 * 
	 * @param methodInvocation
	 *            the method invocation
	 */
	public RemoteInvocationImpl(MethodInvocation methodInvocation) {

		this.methodName = methodInvocation.getMethod().getName();
		this.argsType = methodInvocation.getMethod().getParameterTypes();
		this.args = methodInvocation.getArguments();
		// if (args != null) {
		// for (Object obj : this.args) {
		// if (ReflectUtils.isCast(Remote.class, obj)) {
		// try {
		// UnicastRemoteObject.exportObject((Remote) obj);
		// // UnicastRemoteObject.exportObject(this,0);
		// }
		// catch (RemoteException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#getMethodName
	 * ()
	 */
	public String getMethodName() {

		return methodName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#setMethodName
	 * (java.lang.String)
	 */
	public void setMethodName(String methodName) {

		this.methodName = methodName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#setArgs(java
	 * .lang.Object[])
	 */
	@Override
	public void setArgs(Object[] args) {

		this.args = args != null ? args.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.integration.remoting.RemoteInvocation#getArgs()
	 */
	@Override
	public Object[] getArgs() {

		return args != null ? args.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#getArgsType()
	 */
	public Class<?>[] getArgsType() {

		return argsType != null ? argsType.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#setArgsType
	 * (java.lang.Class)
	 */
	public void setArgsType(Class<?>[] argsType) {

		this.argsType = argsType != null ? argsType.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.RemoteInvocation#invoke(java
	 * .lang.Object)
	 */
	public Object invoke(Object targetObject) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Method method = targetObject.getClass().getMethod(this.methodName,
				this.argsType);
		return method.invoke(targetObject, this.args);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		String result = "RemoteInvocationImpl: method name '" + this.methodName
				+ "'; parameter types ";
		for (Class<?> para : this.argsType) {
			result += para.getName();
		}
		return result;
	}
}
