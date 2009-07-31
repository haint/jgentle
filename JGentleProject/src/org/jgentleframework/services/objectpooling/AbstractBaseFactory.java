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

import java.util.Collection;
import java.util.Iterator;

import org.jgentleframework.utils.data.TimestampObjectBean;

/**
 * The Class AbstractBaseFactory.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 14, 2009
 * @see BasePooling
 * @see AbstractBasePooling
 */
public abstract class AbstractBaseFactory extends AbstractBaseController {
	/** pool. */
	protected Collection<TimestampObjectBean<Object>>	pool	= null;

	/**
	 * Adds the object to pool.
	 * 
	 * @param obj
	 *            the obj
	 * @param decrementNumActive
	 *            the decrement num active
	 * @throws Exception
	 *             the exception
	 */
	protected void addObjectToPool(Object obj, boolean decrementNumActive)
			throws Throwable {

		boolean success = true;
		if (!this.canBePooled(obj)) {
			success = false;
		}
		else {
			deactivateObject(obj);
		}
		if (!success
				|| !isEnable()
				|| ((this.getMaxIdle() >= 0) && (pool.size() >= this
						.getMaxIdle()))) {
			destroyObject(obj);
		}
		synchronized (this) {
			if (isEnable() && success) {
//				if (ReflectUtils.isCast(Stack.class, pool))
//					((Stack<TimestampObjectBean<Object>>) pool)
//							.push(new TimestampObjectBean<Object>(obj));
//				else
					pool.add(new TimestampObjectBean<Object>(obj));
			}
		}
		if (decrementNumActive) {
			synchronized (this) {
				this.numActive--;
				notifyAll();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#clear()
	 */
	@Override
	public void clear() throws UnsupportedOperationException, Throwable {

		for (Iterator<TimestampObjectBean<Object>> iterator = pool.iterator(); iterator
				.hasNext();) {
			this
					.destroyObject(((TimestampObjectBean<Object>) (iterator
							.next())).getValue());
			iterator.remove();
		}
		pool.clear();
		notifyAll();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.PoolType#returnObject(java
	 * .lang.Object)
	 */
	@Override
	public void returnObject(Object obj) throws Throwable {

		addObjectToPool(obj, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#getNumIdle()
	 */
	@Override
	public int getNumIdle() throws UnsupportedOperationException {

		return pool.size();
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		return this.pool.isEmpty();
	}
}
