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

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * This abstract class represents a service method interceptor in a chain of
 * interceptor.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 29, 2007
 */
public abstract class AbstractInterceptHandler {
	/** The successor. */
	private AbstractInterceptHandler	successor	= null;

	/**
	 * Handle method.
	 * 
	 * @param mi
	 *            the {@link MethodInvocation}
	 * @param definition
	 *            the definition
	 * @return the object
	 * @throws Throwable
	 *             the throwable
	 */
	public abstract Object handleMethod(MethodInvocation mi,
			Definition definition) throws Throwable;

	/**
	 * Returns the current successor of this handler.
	 * 
	 * @return the successor
	 */
	public AbstractInterceptHandler getSuccessor() {

		return successor;
	}

	/**
	 * Set the successor of this handler.
	 * 
	 * @param successor
	 *            the successor to set
	 */
	public void setSuccessor(AbstractInterceptHandler successor) {

		this.successor = successor;
	}
}
