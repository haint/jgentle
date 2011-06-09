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
package org.jgentleframework.services.objectpooling.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.NoSuchElementException;

import org.jgentleframework.services.objectpooling.Pool;
import org.jgentleframework.services.objectpooling.context.Validate;

/**
 * This annotation provides all the configuration parameters that affect the
 * pooling service.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see Pooling
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SystemPooling {
	/** The Constant DEFAULT_CAN_BE_POOLED. */
	public static final boolean	DEFAULT_CAN_BE_POOLED					= true;

	/** The Constant DEFAULT_CREATION_TIME_OUT. */
	public static final long	DEFAULT_CREATION_TIME_OUT				= -1L;

	/** The Constant DEFAULT_INVOCATION. */
	public static final boolean	DEFAULT_JUST_IN_TIME					= false;

	/** The Constant DEFAULT_LIFO. */
	public static final boolean	DEFAULT_LIFO							= false;

	/** The Constant DEFAULT_MAX_IDLE. */
	public static final int		DEFAULT_MAX_IDLE						= 8;

	/** The Constant DEFAULT_MAX_POOL_SIZE. */
	public static final int		DEFAULT_MAX_POOL_SIZE					= 8;

	/** The Constant DEFAULT_MIN_EVICTABLE_IDLE_TIME. */
	public static final long	DEFAULT_MIN_EVICTABLE_IDLE_TIME			= 1000L * 60L * 30L;

	/** The Constant DEFAULT_MIN_IDLE. */
	public static final int		DEFAULT_MIN_IDLE						= 0;

	/** The Constant DEFAULT_MIN_POOL_SIZE. */
	public static final int		DEFAULT_MIN_POOL_SIZE					= 0;

	/** The Constant DEFAULT_NUM_TESTS_PER_EVICTION_RUN. */
	public static final int		DEFAULT_NUM_TESTS_PER_EVICTION_RUN		= 3;

	/** The Constant DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME. */
	public static final long	DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME	= -1;

	/** The Constant DEFAULT_TEST_ON_OBTAIN. */
	public static final boolean	DEFAULT_TEST_ON_OBTAIN					= true;

	/** The Constant DEFAULT_TEST_WHILE_IDLE. */
	public static final boolean	DEFAULT_TEST_WHILE_IDLE					= false;

	/** The Constant DEFAULT_TIME_BETWEEN_EVICTION_RUNS. */
	public static final long	DEFAULT_TIME_BETWEEN_EVICTION_RUNS		= -1L;

	/** The Constant EXHAUSTED_BLOCK. */
	public static final byte	EXHAUSTED_BLOCK							= 1;

	/** The Constant EXHAUSTED_FAIL. */
	public static final byte	EXHAUSTED_FAIL							= 0;

	/** The Constant EXHAUSTED_GROW. */
	public static final byte	EXHAUSTED_GROW							= 2;

	/**
	 * If the {@link Pooling#MaxPoolSize()} value has been reached for a pool
	 * (no more objects are available to be borrowed), this parameter specifies
	 * the action to be taken when a request for an object is made to the pool.
	 * <p>
	 * - {@link #EXHAUSTED_FAIL} (byte value of 0) - The caller is told that no
	 * objects are available, by throwing the exception
	 * {@link NoSuchElementException}.
	 * <p>
	 * - {@link #EXHAUSTED_BLOCK} (byte value of 1) - The caller waits until an
	 * object is returned to the pool and becomes available. The length of time
	 * the caller waits is specified by the maxWait property. If this property
	 * is negative, the caller waits indefinitely. If no object is available
	 * even after the wait is over, a {@link NoSuchElementException} exception
	 * is thrown.
	 * <p>
	 * - {@link #EXHAUSTED_GROW} (byte value of 2) - The pool automatically
	 * grows (it creates a new object and returns it to the caller).
	 * <p>
	 * The default setting for this parameter is {@link #EXHAUSTED_BLOCK}
	 */
	byte exhaustedActionType() default EXHAUSTED_BLOCK;

	/**
	 * The pool can be configured to behave as a LIFO queue with respect to idle
	 * objects - always returning the most recently used object from the pool,
	 * or as a FIFO queue, where <code>borrowObject</code> always returns the
	 * oldest object in the idle object pool. The lifo determines whether or not
	 * the pool returns idle objects in last-in-first-out order.
	 * <p>
	 * The default setting for this parameter is <b>false</b>.
	 */
	boolean LIFO() default DEFAULT_LIFO;

	/**
	 * The property is used to configure the maximum number of objects that can
	 * be in the pool at any given time. If a caller tries to return an object
	 * to the pool and the pool already contains the maximum number of objects
	 * sitting idle, specified by this property, the returned object is
	 * rejected.
	 * <p>
	 * As with {@link Pooling#MaxPoolSize()}, values should be greater than or
	 * equal to <b>0</b>. A negative value implies no limit on the number of
	 * objects that can stay idle in the pool.
	 * <p>
	 * The default setting for this parameter is <b>8</b>.
	 */
	int maxIdle() default DEFAULT_MAX_IDLE;

	/**
	 * This property specifies the minimum time in milliseconds that objects can
	 * stay in a pool. After this time is up, these objects are eligible to be
	 * destroyed, and will be destroyed, the next time the eviction thread is
	 * run, as specified by the previous property. You can set a negative value
	 * for this property, which stops objects from being destroyed. However,
	 * objects can still be destroyed using the next property. Note that this
	 * value is reset when a borrowed object is returned to the pool.
	 * <p>
	 * This value is in milliseconds and should be greater than 0. A value of 0
	 * or less means that objects aren’t eligible for destruction based on the
	 * time they have spent in the pool alone.
	 * <p>
	 * The default setting for this parameter is <b>1800000 milliseconds</b> or
	 * <b>half an hour</b>.
	 */
	long minEvictableIdleTime() default DEFAULT_MIN_EVICTABLE_IDLE_TIME;

	/**
	 * The default minimum number of "sleeping" instances in the pool before the
	 * evictor thread (if active) spawns new objects.
	 */
	int minIdle() default DEFAULT_MIN_IDLE;

	/**
	 * This property limits the number of objects to examine in each eviction
	 * run, if such a thread is running. This allows you to enforce a kind of
	 * random check on the idle objects, rather than a fullscale check (which,
	 * if the number of idle objects is large, can be processor intensive).
	 * <p>
	 * This value should be <b>0</b> or greater. If it’s <b>0</b>, then of
	 * course, no objects will be examined. However, if you supply a negative
	 * value, you can enable fractional checking. Thus, if you supply a value of
	 * -2, it tells the evictor thread to examine 1/2 objects (roughly).
	 * <p>
	 * The default setting for this parameter is <b>3</b>.
	 */
	int numTestsPerEvictionRun() default DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	/**
	 * The minimum amount of time an object may sit idle in the pool before it
	 * is eligible for eviction by the idle object evictor (if any), with the
	 * extra condition that at least "minIdle" amount of object remain in the
	 * pool. When non-positive, no objects will be evicted from the pool due to
	 * idle time alone.
	 */
	long softMinEvictableIdleTime() default DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME;

	/**
	 * When <tt>true</tt>, object beans will be {@link Validate#validate()
	 * validated} before being returned by the {@link Pool#obtainObject()}
	 * method. If the object fails to validate, it will be dropped from the
	 * pool, and we will attempt to borrow another.
	 */
	boolean testOnObtain() default DEFAULT_TEST_ON_OBTAIN;

	/**
	 * This property indicates to the eviction thread (if such a thread is
	 * running) that objects must be tested when they’re sitting idle in the
	 * pool. They’re tested by calling the validateObject method if it existed.
	 * It’s up to the validating method to decide how objects are validated.
	 * Invalidated objects are destroyed by calling the destroyObject method.
	 * <p>
	 * The default setting for this parameter is <b>false</b>.
	 * 
	 * @see Validate
	 */
	boolean testWhileIdle() default DEFAULT_TEST_WHILE_IDLE;

	/**
	 * This property controls the spawning of a thread and the time between its
	 * active runs. This thread can examine idle objects in the pool and destroy
	 * these objects to conserve system resources. A negative or <b>0</b> value
	 * for this property ensures that no such thread is started. Even if such a
	 * thread is started, your objects can be prevented from being evicted by
	 * using the {@link #minEvictableIdleTime()} property.
	 * <p>
	 * This value is in milliseconds and should be greater than <b>0</b>. A
	 * value of 0 or less ensures that this thread isn’t started.
	 * <p>
	 * The default setting for this parameter is <b>-1</b> (eviction thread
	 * isn’t started by default).
	 */
	long timeBetweenEvictionRuns() default DEFAULT_TIME_BETWEEN_EVICTION_RUNS;
}
