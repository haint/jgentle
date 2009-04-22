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
package org.jgentleframework.utils.data;

import java.util.Collection;

/**
 * The <em>head</em> of the queue is that element that has been on the queue the
 * longest time. The <em>tail</em> of the queue is that element that has been on
 * the queue the shortest time. New elements are inserted at the tail of the
 * queue, and the queue retrieval operations obtain elements at the head of the
 * queue.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 11, 2009
 * @param <T>
 */
public interface ObjectQueue<T> {
	/**
	 * Inserts the specified element at the tail of this queue if it is possible
	 * to do so immediately without exceeding the queue's capacity, returning
	 * <tt>true</tt> upon success and throwing an <tt>IllegalStateException</tt>
	 * if this queue is full.
	 * 
	 * @param obj
	 *            the element to send
	 * @throws IllegalStateException
	 *             if this queue is full
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	public void sendToQueue(T obj);

	/**
	 * Inserts the specified element at the tail of this queue if it is possible
	 * to do so immediately without exceeding the queue's capacity, returning
	 * <tt>true</tt> upon success and throwing an <tt>IllegalStateException</tt>
	 * if this queue is full.
	 * 
	 * @param obj
	 *            the element to send
	 * @param timeOut
	 *            the time out
	 * @throws IllegalStateException
	 *             if this queue is full
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	public void sendToQueue(T obj, long timeOut);

	/**
	 * Inserts the specified element at the tail of this queue if it is possible
	 * to do so immediately without exceeding the queue's capacity, returning
	 * <tt>true</tt> upon success.If this queue is full, wait until a position
	 * becomes available.
	 * 
	 * @param obj
	 *            the element to send
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	public void sendToQueueNoTimeOut(T obj);

	/**
	 * Retrieves and removes the head of this queue. If no element becomes
	 * available, throw IllegalStateException.
	 */
	public T receiveFromQueue();

	/**
	 * Retrieves and removes the head of this queue, waiting until the time-out
	 * is expired. If the time-out is expired and no element becomes available,
	 * throw IllegalStateException.
	 * 
	 * @param timeOut
	 *            the time-out
	 */
	public T receiveFromQueue(long timeOut);

	/**
	 * Retrieves and removes the head of this queue, waiting if necessary until
	 * an element becomes available.
	 */
	public T receiveFromQueueNoTimeOut();

	/**
	 * Atomically removes all of the elements from this queue. The queue will be
	 * empty after this call returns.
	 */
	public void reset();

	/**
	 * Returns <tt>true</tt> if this queue contains the specified element. More
	 * formally, returns <tt>true</tt> if and only if this queue contains at
	 * least one element <tt>e</tt> such that <tt>o.equals(e)</tt>.
	 * 
	 * @param obj
	 *            object to be checked for containment in this queue
	 * @return <tt>true</tt> if this queue contains the specified element
	 * @throws NullPointerException
	 *             if the specified element is null (optional)
	 */
	public boolean contains(T obj);

	/**
	 * Returns the number of elements in this queue.
	 */
	public int size();

	/**
	 * Removes all available elements from this queue and adds them to the given
	 * collection. This operation may be more efficient than repeatedly polling
	 * this queue.
	 * 
	 * @param collection
	 *            the collection to transfer elements into
	 * @return the number of elements transferred
	 * @throws UnsupportedOperationException
	 *             if addition of elements is not supported by the specified
	 *             collection
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	int drainTo(Collection<? super T> collection);

	/**
	 * Returns the current number of elements existing in this queue.
	 */
	public int count();

	/**
	 * Checks if is empty queue.
	 */
	public boolean isEmptyQueue();
}