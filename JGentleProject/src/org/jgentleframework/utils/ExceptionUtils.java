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
package org.jgentleframework.utils;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.integration.remoting.RemoteAccessException;
import org.jgentleframework.integration.remoting.RemoteFailingConnectionException;

/**
 * General utilities for handling exception.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 2, 2009
 */
public final class ExceptionUtils {
	private static final Log	log	= LogFactory.getLog(ExceptionUtils.class);

	/**
	 * Fill the current client-side stack trace into the given exception.
	 * <p>
	 * The given exception is typically thrown on the server and serialized
	 * as-is, with the client wanting it to contain the client-side portion of
	 * the stack trace as well. What we can do here is to update the
	 * <code>StackTraceElement</code> array with the current client-side stack
	 * trace, provided that we run on JDK 1.5+.
	 * 
	 * @param ex
	 *            the exception to update
	 * @see java.lang.Throwable#getStackTrace()
	 * @see java.lang.Throwable#setStackTrace(StackTraceElement[])
	 */
	public static void fillInClientStackTraceIfPossible(Throwable ex) {

		if (ex != null) {
			StackTraceElement[] clientStack = new Throwable().getStackTrace();
			Set<Throwable> visitedExceptions = new HashSet<Throwable>();
			Throwable exToUpdate = ex;
			while (exToUpdate != null
					&& !visitedExceptions.contains(exToUpdate)) {
				StackTraceElement[] serverStack = exToUpdate.getStackTrace();
				StackTraceElement[] combinedStack = new StackTraceElement[serverStack.length
						+ clientStack.length];
				System.arraycopy(serverStack, 0, combinedStack, 0,
						serverStack.length);
				System.arraycopy(clientStack, 0, combinedStack,
						serverStack.length, clientStack.length);
				exToUpdate.setStackTrace(combinedStack);
				visitedExceptions.add(exToUpdate);
				exToUpdate = exToUpdate.getCause();
			}
		}
	}

	/**
	 * Wrap the given arbitrary exception that happened during remote access in
	 * either a RemoteException or a {@link RemoteAccessException} (if the
	 * method signature does not support RemoteException).
	 * <p>
	 * Only call this for remote access exceptions, not for exceptions thrown by
	 * the target service itself!
	 * 
	 * @param method
	 *            the invoked method
	 * @param ex
	 *            the exception that happened, to be used as cause for the
	 *            RemoteAccessException or RemoteException
	 * @param message
	 *            the message for the RemoteAccessException respectively
	 *            RemoteException
	 * @return the exception to be thrown to the caller
	 */
	public static Exception convertRmiAccessException(Method method,
			Throwable ex, String message) {

		if (log.isDebugEnabled()) {
			log.debug(message, ex);
		}
		if (ReflectUtils.isDeclaredException(method, RemoteException.class)) {
			return new RemoteException(message, ex);
		}
		else {
			return new RemoteAccessException(message, ex);
		}
	}

	/**
	 * Convert the given RemoteException that happened during remote access to
	 * {@link RemoteAccessException} if the method signature does not support
	 * RemoteException. Else, return the original RemoteException.
	 * 
	 * @param method
	 *            the invoked method
	 * @param ex
	 *            the RemoteException that happened
	 * @param isConnectFailure
	 *            whether the given exception should be considered a connect
	 *            failure
	 * @param serviceName
	 *            the name of the service (for debugging purposes)
	 * @return the exception to be thrown to the caller
	 */
	public static Exception convertRmiAccessException(Method method,
			RemoteException ex, boolean isConnectFailure, String serviceName) {

		if (log.isDebugEnabled()) {
			log.debug("Remote service [" + serviceName + "] threw exception",
					ex);
		}
		if (ReflectUtils.isDeclaredException(method, ex.getClass())) {
			return ex;
		}
		else {
			if (isConnectFailure) {
				return new RemoteFailingConnectionException(
						"Could not connect to remote service [" + serviceName
								+ "]", ex);
			}
			else {
				return new RemoteAccessException(
						"Could not access remote service [" + serviceName + "]",
						ex);
			}
		}
	}
}
