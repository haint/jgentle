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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.jgentleframework.context.beans.ProviderAware;

/**
 * The Class WeakPool.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 10, 2009
 */
public class WeakPool extends AbstractBaseController implements ProviderAware {
	/** The pool. */
	Queue<SoftReference<Object>>			pool		= null;

	/**
	 * Queue of broken references that might be able to be removed from
	 * <code>_pool</code>. This is used to help {@link #getNumIdle()} be more
	 * accurate with minimial performance overhead.
	 */
	private final ReferenceQueue<Object>	refQueue	= new ReferenceQueue<Object>();

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.AbstractBasePooling#activate
	 * ()
	 */
	@Override
	public synchronized void activate() {

		super.activate();
		this.pool = new ArrayBlockingQueue<SoftReference<Object>>(
				this.maxPoolSize, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#addObject()
	 */
	@Override
	public synchronized void addObject() throws Throwable {

		assertDisable();
		Object obj = this.createsBean();
		boolean success = true;
		if (!this.canBePooled(obj)) {
			success = false;
		}
		else {
			deactivateObject(obj);
		}
		synchronized (this) {
			if (isEnable() && success) {
				pool.add(new SoftReference<Object>(obj, refQueue));
				notifyAll();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#close()
	 */
	@Override
	public void close() throws Throwable {

		this.enable = false;
		clear();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#clear()
	 */
	@Override
	public synchronized void clear() throws Throwable {

		Iterator<SoftReference<Object>> iter = pool.iterator();
		while (iter.hasNext()) {
			try {
				Object obj = ((SoftReference<Object>) iter.next()).get();
				if (null != obj) {
					this.destroyObject(obj);
				}
			}
			catch (Exception e) {
				// ignore error, keep destroying the rest
			}
		}
		pool.clear();
		pruneClearedReferences();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.AbstractBaseFactory#getNumIdle
	 * ()
	 */
	@Override
	public synchronized int getNumIdle() {

		pruneClearedReferences();
		return pool.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#obtainObject()
	 */
	@Override
	public Object obtainObject() throws NoSuchElementException {

		assertDisable();
		Object obj = null;
		try {
			while (null == obj) {
				if (this.pool.isEmpty()) {
					obj = this.createsBean();
				}
				else {
					SoftReference<Object> ref = (SoftReference<Object>) (this.pool
							.poll());
					obj = ref.get();
					ref.clear();
				}
				if (obj != null) {
					this.activatesObject(obj);
				}
			}
		}
		catch (Throwable e) {
			obj = null;
		}
		numActive++;
		return obj;
	}

	/**
	 * If any idle objects were garbage collected, remove their
	 * {@link Reference} wrappers from the idle object pool.
	 */
	private void pruneClearedReferences() {

		Reference<? extends Object> ref;
		while ((ref = refQueue.poll()) != null) {
			try {
				pool.remove(ref);
			}
			catch (UnsupportedOperationException uoe) {
				// ignored
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.PoolType#returnObject(java
	 * .lang.Object)
	 */
	@Override
	public void returnObject(Object obj) throws Throwable {

		boolean success = !isEnable();
		if (!this.canBePooled(obj)) {
			success = false;
		}
		else {
			this.deactivateObject(obj);
		}
		numActive--;
		if (success) {
			this.pool.add(new SoftReference<Object>(obj, refQueue));
		}
		notifyAll();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return pool.isEmpty();
	}
}
