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
package org.jgentleframework.integration.remoting;

import java.lang.reflect.InvocationTargetException;

import org.jgentleframework.reflection.metadata.SerializableMetadata;

/**
 * Description of an remote invocation to a method, given to an interceptor upon
 * method-call. Encapsulates a remote invocation, providing core method
 * invocation properties in a serializable fashion.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 18, 2009
 * @see SerializableMetadata
 */
public interface RemoteInvocation extends SerializableMetadata {
	/**
	 * Returns the invocation's method name.
	 * 
	 * @return the method name
	 */
	public String getMethodName();

	/**
	 * Sets the method name.
	 * 
	 * @param methodName
	 *            the new method name
	 */
	public void setMethodName(String methodName);

	/**
	 * Sets the arguments.
	 * 
	 * @param args
	 *            the new args
	 */
	public void setArgs(Object[] args);

	/**
	 * Gets the args.
	 * 
	 * @return the args
	 */
	public Object[] getArgs();

	/**
	 * Gets the args type.
	 * 
	 * @return the args type
	 */
	public Class<?>[] getArgsType();

	/**
	 * Sets the args type.
	 * 
	 * @param argsType
	 *            the new args type
	 */
	public void setArgsType(Class<?>[] argsType);

	/**
	 * Executes this invocation on the given target object.
	 * 
	 * @param targetObject
	 *            the target object
	 * @return the object
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public Object invoke(Object targetObject) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;
}