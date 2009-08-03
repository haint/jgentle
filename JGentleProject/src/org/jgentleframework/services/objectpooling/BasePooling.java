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

import org.jgentleframework.services.objectpooling.context.CanBePooled;
import org.jgentleframework.services.objectpooling.context.Validate;

/**
 * The Interface BasePooling.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 13, 2009
 * @see Pool
 */
public interface BasePooling extends Pool {
	/**
	 * Throws an <code>IllegalStateException</code> when this pool has been
	 * disabled.
	 * 
	 * @throws IllegalStateException
	 *             when this pool has been disabled.
	 * @see #isEnable()
	 */
	public void assertDisable() throws IllegalStateException;

	/**
	 * Returns the maximum amount of time (in millis) the
	 * {@link Pool#obtainObject()} method should block before throwing an
	 * exception when the pool is exhausted. When less than or equal to 0, the
	 * {@link Pool#obtainObject()} method may block indefinitely.
	 * 
	 * @return the creation time out
	 */
	public long getCreationTimeOut();

	/**
	 * Gets the evictor.
	 * 
	 * @return the evictor
	 */
	public Evictor getEvictor();

	/**
	 * Returns the action to take when the {@link Pool#obtainObject()}
	 * method is invoked when the pool is exhausted (the maximum number of
	 * "active" objects has been reached).
	 * 
	 * @return the exhausted action type
	 */
	public byte getExhaustedActionType();

	/**
	 * Returns the maximum the number of idle instances in the pool.
	 */
	public int getMaxIdle();

	/**
	 * Returns the total number of active instances from the pool.
	 * 
	 * @return the max pool size
	 */
	public int getMaxPoolSize();

	/**
	 * Returns the minimum amount of time an object may sit idle in the pool
	 * before it is eligible for eviction by the idle object evictor (if any).
	 * When non-positive, no objects will be evicted from the pool due to idle
	 * time alone.
	 * 
	 * @return the min evictable idle time
	 */
	public long getMinEvictableIdleTime();

	/**
	 * Returns the minimum number of idle instances in the pool.
	 */
	public int getMinIdle();

	/**
	 * Returns the minimum objects that must be maintained in the pool at all
	 * times.
	 * 
	 * @return the min pool size
	 */
	public int getMinPoolSize();

	/**
	 * Returns the property limits the number of objects to examine in each
	 * eviction run, if such a thread is running.
	 * 
	 * @return the num tests per eviction run
	 */
	public int getNumTestsPerEvictionRun();

	/**
	 * Returns the minimum amount of time an object may sit idle in the pool
	 * before it is eligible for eviction by the idle object evictor (if any),
	 * with the extra condition that at least "minIdle" amount of object remain
	 * in the pool.
	 * 
	 * @return the soft min evictable idle time
	 */
	public long getSoftMinEvictableIdleTime();

	/**
	 * Returns the number of milliseconds to sleep between runs of the idle
	 * object evictor thread. When non-positive, no idle object evictor thread
	 * will be run.
	 * 
	 * @return the time between eviction runs
	 */
	public long getTimeBetweenEvictionRuns();

	/**
	 * When <b>true</b>, objects will be validated by
	 * {@link CanBePooled#canBePooled()} before being returned to the pool
	 * within the {@link Pool#returnObject}.
	 * 
	 * @return <code>true</code> when objects will be validated before returned
	 *         to {@link Pool#returnObject}.
	 */
	public boolean isCanBePooled();

	/**
	 * Checks if is enable.
	 */
	public boolean isEnable();

	/**
	 * Checks if is just in time.
	 * 
	 * @return true, if is just in time
	 */
	public boolean isJustInTime();

	/**
	 * Whether or not the idle object pool acts as a LIFO queue. True means that
	 * {@link Pool#obtainObject()} returns the most recently used
	 * ("last in") idle object in the pool (if there are idle instances
	 * available). False means that the pool behaves as a FIFO queue - objects
	 * are taken from the idle object pool in the order that they are returned
	 * to the pool.
	 */
	public boolean isLifo();

	/**
	 * When <b>true</b>, objects will be {@link Validate#validate() validated}
	 * before being returned by the {@link Pool#obtainObject()} method. If
	 * the object fails to validate, it will be dropped from the pool, and we
	 * will attempt to borrow another.
	 */
	public boolean isTestOnObtain();

	/**
	 * When <b>true</b>, objects will be {@link Validate#validate() validated}
	 * by the idle object evictor (if any). If an object fails to validate, it
	 * will be dropped from the pool.
	 */
	public boolean isTestWhileIdle();

	/**
	 * When <b>true</b>, objects will be validated by
	 * {@link CanBePooled#canBePooled()} before being returned to the pool
	 * within the {@link Pool#returnObject}.
	 * 
	 * @param canBePooled
	 *            <code>true</code> so objects will be
	 *            {@link CanBePooled#canBePooled() validated} after returned to
	 *            {@link Pool#returnObject}.
	 */
	public void setCanBePooled(boolean canBePooled);

	/**
	 * Sets the maximum amount of time (in millis) the
	 * {@link Pool#obtainObject()} method should block before throwing an
	 * exception when the pool is exhausted. When less than or equal to 0, the
	 * {@link Pool#obtainObject()} method may block indefinitely.
	 * 
	 * @param creationTimeOut
	 *            the creation TimeOut to set
	 */
	public void setCreationTimeOut(long creationTimeOut);

	/**
	 * Sets the enable.
	 * 
	 * @param enable
	 *            the enable to set
	 */
	public void setEnable(boolean enable);

	/**
	 * Sets the evictor.
	 * 
	 * @param evictor
	 *            the evictor to set
	 */
	public void setEvictor(Evictor evictor);

	/**
	 * Sets the exhausted action type.
	 * 
	 * @param exhaustedActionType
	 *            the exhaustedActionType to set
	 * @see #getExhaustedActionType()
	 */
	public void setExhaustedActionType(byte exhaustedActionType);

	/**
	 * Sets the just in time.
	 * 
	 * @param justInTime
	 *            the just in time
	 */
	public void setJustInTime(boolean justInTime);

	/**
	 * Sets the LIFO property of the pool. True means that borrowObject returns
	 * the most recently used ("last in") idle object in the pool (if there are
	 * idle instances available). False means that the pool behaves as a FIFO
	 * queue - objects are taken from the idle object pool in the order that
	 * they are returned to the pool.
	 * 
	 * @param lifo
	 *            the new value for the LIFO property
	 */
	public void setLifo(boolean lifo);

	/**
	 * Sets the maximum the number of idle instances in the pool.
	 * 
	 * @param maxIdle
	 *            The cap on the number of "idle" instances in the pool. Use a
	 *            negative value to indicate an unlimited number of idle
	 *            instances.
	 */
	public void setMaxIdle(int maxIdle);

	/**
	 * Sets the total number of active instances from the pool.
	 * 
	 * @param maxPoolSize
	 *            the maxPoolSize to set
	 */
	public void setMaxPoolSize(int maxPoolSize);

	/**
	 * Sets the minimum amount of time an object may sit idle in the pool before
	 * it is eligible for eviction by the idle object evictor (if any). When
	 * non-positive, no objects will be evicted from the pool due to idle time
	 * alone.
	 * 
	 * @param minEvictableIdleTime
	 *            minimum amount of time an object may sit idle in the pool
	 *            before it is eligible for eviction.
	 */
	public void setMinEvictableIdleTime(long minEvictableIdleTime);

	/**
	 * Sets the minimum number of idle instances in the pool.
	 * 
	 * @param minIdle
	 *            the minIdle to set
	 */
	public void setMinIdle(int minIdle);

	/**
	 * Sets the minimum objects that must be maintained in the pool at all
	 * times.
	 * 
	 * @param minPoolSize
	 *            the minPoolSize to set
	 */
	public void setMinPoolSize(int minPoolSize);

	/**
	 * Sets the max number of objects to examine during each run of the idle
	 * object evictor thread. This property limits the number of objects to
	 * examine in each eviction run, if such a thread is running.
	 * 
	 * @param numTestsPerEvictionRun
	 *            max number of objects to examine during each evictor run.
	 */
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);

	/**
	 * Sets the minimum amount of time an object may sit idle in the pool before
	 * it is eligible for eviction by the idle object evictor (if any), with the
	 * extra condition that at least "minIdle" amount of object remain in the
	 * pool. When non-positive, no objects will be evicted from the pool due to
	 * idle time alone.
	 * 
	 * @param softMinEvictableIdleTime
	 *            minimum amount of time an object may sit idle in the pool
	 *            before it is eligible for eviction.
	 */
	public void setSoftMinEvictableIdleTime(long softMinEvictableIdleTime);

	/**
	 * When <b>true</b>, objects will be {@link Validate#validate() validated}
	 * before being returned by the {@link Pool#obtainObject()} method. If
	 * the object fails to validate, it will be dropped from the pool, and we
	 * will attempt to borrow another.
	 * 
	 * @param testOnObtain
	 *            <b>true</b> if objects should be validated before being
	 *            obtained.
	 */
	public void setTestOnObtain(boolean testOnObtain);

	/**
	 * When <b>true</b>, objects will be {@link Validate#validate() validated}
	 * by the idle object evictor (if any). If an object fails to validate, it
	 * will be dropped from the pool.
	 * 
	 * @param testWhileIdle
	 *            <code>true</code> so objects will be validated by the evictor.
	 */
	public void setTestWhileIdle(boolean testWhileIdle);

	/**
	 * Sets the number of milliseconds to sleep between runs of the idle object
	 * evictor thread. When non-positive, no idle object evictor thread will be
	 * run.
	 * 
	 * @param timeBetweenEvictionRuns
	 *            number of milliseconds to sleep between evictor runs.
	 */
	public void setTimeBetweenEvictionRuns(long timeBetweenEvictionRuns);
}