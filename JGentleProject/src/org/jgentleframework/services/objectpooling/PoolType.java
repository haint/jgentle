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
package org.jgentleframework.services.objectpooling;

import java.util.NoSuchElementException;

import org.jgentleframework.context.beans.Disposable;

/**
 * A pooling interface.
 * <p>
 * {@link PoolType} defines a trivially simple pooling interface.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 7, 2009
 */
public interface PoolType {
	/**
	 * Creates an object using the implementation dependent mechanism, passivate
	 * it, and then place it in the idle object pool. <code>addObject</code> is
	 * useful for "pre-loading" a pool with idle objects. (Optional operation).
	 * 
	 * @throws Throwable
	 *             when creation fails.
	 * @throws UnsupportedOperationException
	 *             when this pool cannot add new idle objects.
	 */
	void addObject() throws UnsupportedOperationException, Throwable;

	/**
	 * Clears any objects sitting idle in the pool, releasing any associated
	 * resources (optional operation). Idle objects cleared must be
	 * {@link Disposable#destroy() destroyed}.
	 * 
	 * @throws UnsupportedOperationException
	 *             if this implementation does not support the operation
	 */
	void clear() throws UnsupportedOperationException, Throwable;

	/**
	 * Close this pool, and free any resources associated with it.
	 * 
	 * @throws Throwable
	 *             implementations should silently fail if not all resources can
	 *             be freed.
	 */
	void close() throws Throwable;

	/**
	 * Returns the number of instances currently borrowed from this pool
	 * (optional operation). Returns a negative value if this information is not
	 * available.
	 * 
	 * @return the number of instances currently borrowed from this pool or a
	 *         negative value if unsupported
	 * @throws UnsupportedOperationException
	 *             if this implementation does not support the operation
	 */
	int getNumActive() throws UnsupportedOperationException;

	/**
	 * Returns the number of instances currently idle in this pool (optional
	 * operation). This may be considered an approximation of the number of
	 * objects that can be {@link #obtainObject() borrowed} without creating any
	 * new instances. Returns a negative value if this information is not
	 * available.
	 * 
	 * @return the number of instances currently idle in this pool or a negative
	 *         value if unsupported
	 * @throws UnsupportedOperationException
	 *             if this implementation does not support the operation
	 */
	int getNumIdle() throws UnsupportedOperationException;

	/**
	 * Invalidates an object from the pool.
	 * <p>
	 * This method should be used when an object that has been borrowed is
	 * determined (due to an exception or other problem) to be invalid.
	 * 
	 * @param obj
	 *            a {@link #obtainObject() borrowed} instance to be disposed.
	 * @throws Throwable
	 *             the throwable
	 */
	void invalidateObject(Object obj) throws Throwable;

	/**
	 * Checks if this pool is empty.
	 * 
	 * @return true, if is empty
	 */
	boolean isEmpty();

	/**
	 * Obtains an instance from this pool.
	 * <p>
	 * The behaviour of this method when the pool has been exhausted is not
	 * strictly specified (although it may be specified by implementations).
	 * This method will return <code>null</code> to indicate exhaustion or throw
	 * a {@link NoSuchElementException}.
	 * 
	 * @return an instance from this pool.
	 * @throws NoSuchElementException
	 *             when the pool is exhausted and cannot or will not return
	 *             another instance.
	 */
	Object obtainObject() throws NoSuchElementException, Throwable;

	/**
	 * Return an instance to the pool.
	 * 
	 * @param obj
	 *            a {@link #obtainObject() borrowed} instance to be returned.
	 */
	void returnObject(Object obj) throws Throwable;
}
