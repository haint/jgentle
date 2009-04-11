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

import java.util.Arrays;
import java.util.Collection;

import org.jgentleframework.utils.Assertor;

/**
 * A bounded {@linkplain BasicObjectQueue } is blocking queue backed by an array.
 * This queue orders elements FIFO (first-in-first-out). The <em>head</em> of
 * the queue is that element that has been on the queue the longest time. The
 * <em>tail</em> of the queue is that element that has been on the queue the
 * shortest time. New elements are inserted at the tail of the queue, and the
 * queue retrieval operations obtain elements at the head of the queue.
 * <p>
 * This is a classic "bounded buffer", in which a fixed-sized array holds
 * elements inserted by producers and extracted by consumers. Once created, the
 * capacity cannot be increased. Attempts to put an element into a full queue
 * will result in the operation blocking; attempts to take an element from an
 * empty queue will similarly block.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 11, 2009
 * @see ObjectQueue
 */
public class BasicObjectQueue<T> implements ObjectQueue<T> {
	/** The inner queue. */
	private T[]	innerQueue;

	/** The tail. */
	private int	head, tail;

	/**
	 * The Constructor.
	 * 
	 * @param maxSize
	 *            the capacity of this queue
	 */
	@SuppressWarnings("unchecked")
	public BasicObjectQueue(int maxSize) {

		this.innerQueue = (T[]) new Object[maxSize];
		head = tail = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#sendToQueue(T)
	 */
	@Override
	public void sendToQueue(T obj) {

		sendToQueue(obj, -1L);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#sendToQueue(T, long)
	 */
	@Override
	public void sendToQueue(T obj, long timeOut) {

		Assertor
				.notNull(obj, "The object sending to queue must not be null !!");
		Object lock = new Object();
		synchronized (lock) {
			if ((tail == head) && innerQueue[head] == null) {
				innerQueue[tail] = obj;
				lock.notify();
			}
			else {
				int newTail = tail + 1;
				if (newTail >= innerQueue.length)
					newTail = 0;
				if (newTail == head) {
					try {
						if (timeOut == -1L)
							throw new IllegalStateException(
									"The current queue is full !");
						else {
							lock.wait(timeOut);
							if (newTail == head)
								throw new IllegalStateException(
										"The current queue is full !");
							else
								innerQueue[newTail] = obj;
						}
					}
					catch (InterruptedException e) {
						if (head == newTail)
							throw new IllegalStateException(
									"The current queue is full !");
					}
				}
				else {
					innerQueue[newTail] = obj;
				}
				tail = newTail;
				lock.notify();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#sendToQueueNoTimeOut(T)
	 */
	@Override
	public void sendToQueueNoTimeOut(T obj) {

		sendToQueue(obj, 0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#receiveFromQueue()
	 */
	@Override
	public T receiveFromQueue() {

		return receiveFromQueue(-1L);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#receiveFromQueue(long)
	 */
	@Override
	public T receiveFromQueue(long timeOut) {

		T result = null;
		Object lock = new Object();
		synchronized (lock) {
			if (head == tail) {
				if (innerQueue[head] != null) {
					result = innerQueue[head];
					innerQueue[head] = null;
					lock.notify();
					return result;
				}
				try {
					if (timeOut == -1L)
						throw new IllegalStateException(
								"The current queue is empty !");
					else {
						lock.wait(timeOut);
						if (head == tail)
							throw new IllegalStateException(
									"The current queue is empty !");
					}
				}
				catch (InterruptedException e) {
					if (head == tail)
						throw new IllegalStateException(
								"The current queue is empty !");
				}
			}
			result = innerQueue[head];
			innerQueue[head] = null;
			++head;
			if (head >= innerQueue.length)
				head = 0;
			lock.notify();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.utils.data.ObjectQueue#receiveFromQueueNoTimeOut()
	 */
	@Override
	public T receiveFromQueueNoTimeOut() {

		return receiveFromQueue(0);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#reset()
	 */
	@Override
	public void reset() {

		head = tail = 0;
		innerQueue[head] = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#isEmptyQueue()
	 */
	@Override
	public boolean isEmptyQueue() {

		if (tail == head && innerQueue[head] == null) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#size()
	 */
	@Override
	public int size() {

		return this.innerQueue.length;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.utils.data.ObjectQueue#count()
	 */
	@Override
	public int count() {

		int count = 0;
		for (int i = 0; i < this.innerQueue.length; i++) {
			if (innerQueue[i] != null) {
				count++;
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.utils.data.ObjectQueue#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(T obj) {

		Assertor.notNull(obj, "The given object must not be null !!");
		return Arrays.asList(this.innerQueue).contains(obj);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.utils.data.ObjectQueue#drainTo(java.util.Collection)
	 */
	@Override
	public int drainTo(Collection<? super T> collection) {

		Assertor.notNull(collection, "The given collection must not be null !");
		Object lock = new Object();
		synchronized (lock) {
			int n = 0;
			for (int i = 0; i < this.innerQueue.length; i++) {
				if (this.innerQueue[i] != null) {
					collection.add(this.innerQueue[i]);
					n++;
				}
			}
			return n;
		}
	}
}
