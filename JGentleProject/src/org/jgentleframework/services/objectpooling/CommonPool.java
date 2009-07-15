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
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.jgentleframework.context.beans.Initializing;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.services.objectpooling.annotation.SystemPooling;
import org.jgentleframework.utils.data.TimestampObjectBean;

/**
 * The Class CommonPool.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 8, 2008
 * @see PoolType
 * @see BasePooling
 * @see ProviderAware
 * @see Initializing
 */
public class CommonPool extends AbstractBaseFactory {
	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.AbstractBasePooling#activate
	 * ()
	 */
	@Override
	public void activate() {

		super.activate();
		synchronized (this) {
			this.pool = new ArrayBlockingQueue<TimestampObjectBean<Object>>(
					this.maxPoolSize, true);
		}
		PoolStaticUtils.startEvictor(this.getEvictor(), this
				.getTimeBetweenEvictionRuns(), this, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#addObject()
	 */
	@Override
	public void addObject() throws UnsupportedOperationException, Throwable {

		assertDisable();
		Object obj = this.createsBean();
		addObjectToPool(obj, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#close()
	 */
	@Override
	public void close() throws Throwable {

		this.enable = false;
		synchronized (this) {
			clear();
			PoolStaticUtils.startEvictor(evictor, -1L, this, false);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.PoolType#obtainObject()
	 */
	@Override
	public Object obtainObject() throws NoSuchElementException, Throwable {

		long starttime = System.currentTimeMillis();
		for (;;) {
			TimestampObjectBean<Object> pair = null;
			synchronized (this) {
				assertDisable();
				pair = (TimestampObjectBean<Object>) (((Queue<TimestampObjectBean<Object>>) pool)
						.poll());
				if (pair == null) {
					if (this.maxPoolSize < 0
							|| this.getNumActive() < this.maxPoolSize) {
						return createsBean();
					}
					else {
						switch (this.exhaustedActionType) {
						case SystemPooling.EXHAUSTED_GROW:
							return createsBean();
						case SystemPooling.EXHAUSTED_FAIL:
							throw new NoSuchElementException(
									"Pool exhausted !!");
						case SystemPooling.EXHAUSTED_BLOCK:
							try {
								if (this.creationTimeOut <= 0) {
									wait();
								}
								else {
									final long elapsed = (System
											.currentTimeMillis() - starttime);
									final long waitTime = this.creationTimeOut
											- elapsed;
									if (waitTime > 0) {
										wait(waitTime);
									}
								}
							}
							catch (InterruptedException e) {
								Thread.currentThread().interrupt();
								if (log.isErrorEnabled()) {
									log.error(e.getMessage(), e);
								}
							}
							if (this.creationTimeOut > 0
									&& ((System.currentTimeMillis() - starttime) >= this.creationTimeOut)) {
								throw new NoSuchElementException(
										"Timeout waiting for idle object");
							}
							else {
								continue;
							}
						default:
							throw new IllegalArgumentException(
									"ExhaustedActionType property "
											+ this.exhaustedActionType
											+ " not recognized.");
						}
					}
				}
				else {
					Object result = pair.getValue();
					activatesObject(result);
					validatesObject(result);
					return result;
				}
			}
		}
	}
}
