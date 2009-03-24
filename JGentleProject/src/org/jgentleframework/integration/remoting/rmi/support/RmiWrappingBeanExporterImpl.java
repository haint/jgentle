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
package org.jgentleframework.integration.remoting.rmi.support;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.jgentleframework.integration.remoting.Processing;
import org.jgentleframework.integration.remoting.RemoteInvocation;
import org.jgentleframework.integration.remoting.RemotingException;
import org.jgentleframework.integration.remoting.WrappingProcessor;

/**
 * Represents the <code>Wrapping Bean Exporter</code> which is resposible for
 * wrapper of exported bean.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 14, 2008
 * @see RmiWrapperExporter
 * @see RmiExporterImpl
 */
public class RmiWrappingBeanExporterImpl implements Processing,
		RmiWrappingBeanExporter {
	/** The target object. */
	Object				targetObject		= null;

	/** The wrapper exporter. */
	RmiWrapperExporter	wrapperExporter		= null;

	/** The wrapping processor. */
	WrappingProcessor	wrappingProcessor	= null;

	/**
	 * Constructor.
	 * 
	 * @param wrapperExporter
	 *            the wrapper exporter
	 * @param targetObject
	 *            the target object
	 */
	public RmiWrappingBeanExporterImpl(RmiWrapperExporter wrapperExporter,
			Object targetObject) {

		this.targetObject = targetObject;
		this.wrapperExporter = wrapperExporter;
		// creates RmiWrappingProcessor object
		this.wrappingProcessor = new RmiWrappingProcessor();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.RmiWrappingBeanExporter
	 * #invoke(org.jgentleframework.integration.remoting.RemoteInvocationImpl)
	 */
	public Object invoke(RemoteInvocation invocation) throws RemoteException,
			RemotingException {

		Object result = null;
		try {
			result = this.invoke(this.wrappingProcessor, invocation);
		}
		catch (NoSuchMethodException e) {
			throw new RemotingException("NoSuchMethodException: "
					+ e.getMessage());
		}
		catch (IllegalAccessException e) {
			throw new RemotingException("IllegalAccessException: "
					+ e.getMessage());
		}
		catch (InvocationTargetException e) {
			throw new RemotingException("InvocationTargetException: "
					+ e.getMessage());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.integration.remoting.rmi.support.Processing#invoke
	 * (org.jgentleframework.integration.remoting.rmi.support.WrappingProcessor,
	 * org.jgentleframework.integration.remoting.RemoteInvocation)
	 */
	@Override
	public Object invoke(WrappingProcessor processor,
			RemoteInvocation invocation) throws RemoteException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {

		return processor.invoke(invocation, this.targetObject);
	}
}
