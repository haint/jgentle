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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.jgentleframework.context.beans.Initializing;
import org.jgentleframework.context.beans.annotation.InitializingMethod;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.TimestampObjectBean;

/**
 * The Class PoolUtils.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 9, 2009
 */
public final class PoolStaticUtils {
	// private final static Log log = LogFactory.getLog(PoolUtils.class);
	/**
	 * Provides a shared idle object eviction timer for all pools. This class
	 * wraps the standard {@link Timer} and keeps track of how many pools are
	 * using it. If no pools are using the timer, it is canceled. This prevents
	 * a thread being left running which, in application server environments,
	 * can lead to memory leads and/or prevent applications from shutting down
	 * or reloading cleanly.
	 * <p>
	 * This class has package scope to prevent its inclusion in the pool public
	 * API. The class declaration below should *not* be changed to public.
	 */
	static class EvictionTimer {
		/** The _timer. */
		private static Timer	timer;

		/** The _usage count. */
		private static int		usageCount;

		/**
		 * Remove the specified eviction task from the timer.
		 * 
		 * @param task
		 *            Task to be scheduled
		 */
		static synchronized void cancel(TimerTask task) {

			task.cancel();
			usageCount--;
			if (usageCount == 0) {
				timer.cancel();
				timer = null;
			}
		}

		/**
		 * Add the specified eviction task to the timer. Tasks that are added
		 * with a call to this method *must* call {@link #cancel(TimerTask)} to
		 * cancel the task to prevent memory and/or thread leaks in application
		 * server environments.
		 * 
		 * @param task
		 *            Task to be scheduled
		 * @param delay
		 *            Delay in milliseconds before task is executed
		 * @param period
		 *            Time in milliseconds between executions
		 */
		static synchronized void schedule(TimerTask task, long delay,
				long period) {

			if (null == timer) {
				timer = new Timer(true);
			}
			usageCount++;
			timer.schedule(task, delay, period);
		}

		/**
		 * Instantiates a new eviction timer.
		 */
		private EvictionTimer() {

		}
	}

	/**
	 * Calculates deficit.
	 * 
	 * @param basePool
	 *            the base pool
	 * @return the int
	 */
	private synchronized static int calculateDeficit(BasePooling basePool) {

		int objectDeficit = basePool.getMinIdle() - basePool.getNumIdle();
		if (basePool.getMaxPoolSize() > 0) {
			int growLimit = Math.max(0, basePool.getMaxPoolSize()
					- basePool.getNumActive() - basePool.getNumIdle());
			objectDeficit = Math.min(objectDeficit, growLimit);
		}
		return objectDeficit;
	}

	/**
	 * Check to see if we are below our minimum number of objects if so enough
	 * to bring us back to our minimum.
	 * 
	 * @param basePool
	 *            the base pool
	 * @throws Exception
	 *             when {@link PoolType#addObject()} fails.
	 */
	public static void ensureMinIdle(BasePooling basePool) throws Throwable {

		// this method isn't synchronized so the
		// calculateDeficit is done at the beginning
		// as a loop limit and a second time inside the loop
		// to stop when another thread already returned the
		// needed objects
		int objectDeficit = calculateDeficit(basePool);
		for (int j = 0; j < objectDeficit && calculateDeficit(basePool) > 0; j++) {
			basePool.addObject();
		}
	}

	/**
	 * Gets the num tests.
	 * 
	 * @param basePool
	 *            the base pool
	 * @return the num tests
	 */
	private static int getNumTests(AbstractBasePooling basePool) {

		int numTestsPerEvictionRun = basePool.getNumTestsPerEvictionRun();
		if (numTestsPerEvictionRun >= 0) {
			return Math.min(numTestsPerEvictionRun, basePool.getNumIdle());
		}
		else {
			return (int) (Math.ceil((double) basePool.getNumIdle()
					/ Math.abs((double) numTestsPerEvictionRun)));
		}
	}

	/**
	 * Perform <code>numTests</code> idle object eviction tests, evicting
	 * examined objects that meet the criteria for eviction. If
	 * <code>testWhileIdle</code> is true, examined objects are validated when
	 * visited (and removed if invalid); otherwise only objects that have been
	 * idle for more than <code>minEvicableIdletimeMillis</code> are removed.
	 * <p>
	 * Successive activations of this method examine objects in in sequence,
	 * cycling through objects in oldest-to-youngest order.
	 * 
	 * @param pool
	 *            the pool
	 * @throws Exception
	 *             if the pool is closed or eviction fails.
	 */
	public synchronized static void evict(AbstractBaseFactory pool, boolean lifo)
			throws Exception {

		pool.assertDisable();
		if (pool.isEmpty()) {
			int numTest = 0;
			int numTestsPerRun = getNumTests(pool);
			TimestampObjectBean<Object> pair = null;
			if (!lifo) {
				if (ReflectUtils.isCast(Queue.class, pool.pool)) {
					for (Iterator<TimestampObjectBean<Object>> iterator = pool.pool
							.iterator(); iterator.hasNext();) {
						numTest++;
						if (numTest > numTestsPerRun)
							break;
						else {
							try {
								pair = (TimestampObjectBean<Object>) iterator
										.next();
							}
							catch (NoSuchElementException e) {
								break;
							}
							evict(pair, pool);
						}
					}
				}
			}
			else {
				if (ReflectUtils.isCast(Stack.class, pool.pool)) {
					Stack<TimestampObjectBean<Object>> stack = (Stack<TimestampObjectBean<Object>>) pool.pool;
					while (stack.size() > 0) {
						numTest++;
						if (numTest > numTestsPerRun)
							break;
						else {
							try {
								pair = (TimestampObjectBean<Object>) stack
										.get(stack.size() - numTest);
							}
							catch (ArrayIndexOutOfBoundsException e) {
								break;
							}
							evict(pair, pool);
						}
					}
				}
			}
		}
	}

	/**
	 * Evict.
	 * 
	 * @param pair
	 *            the pair
	 * @param pool
	 *            the pool
	 */
	private static void evict(TimestampObjectBean<Object> pair,
			AbstractBaseFactory pool) {

		if (pair != null) {
			boolean removeObject = false;
			final long idleTimeMilis = System.currentTimeMillis()
					- pair.getTstamp();
			// check minimum evictable idle time
			if ((pool.getMinEvictableIdleTime() > 0)
					&& (idleTimeMilis > pool.getMinEvictableIdleTime())) {
				removeObject = true;
			}
			else if ((pool.getSoftMinEvictableIdleTime() > 0)
					&& (idleTimeMilis > pool.getSoftMinEvictableIdleTime())
					&& (pool.getNumIdle() > pool.getMinIdle())) {
				removeObject = true;
			}
			if (pool.isTestWhileIdle() && !removeObject) {
				boolean active = false;
				try {
					if (ReflectUtils
							.isCast(Initializing.class, pair.getValue())) {
						((Initializing) pair.getValue()).activate();
					}
					else if (pool.definition
							.isAnnotationPresentAtAnyMethods(InitializingMethod.class)) {
						List<Method> methods = pool.definition
								.getMethodsAnnotatedWith(InitializingMethod.class);
						for (Method method : methods) {
							method.setAccessible(true);
							method.invoke(pair.getValue());
						}
					}
					active = true;
				}
				catch (SecurityException e1) {
					removeObject = true;
				}
				catch (IllegalArgumentException e1) {
					removeObject = true;
				}
				catch (IllegalAccessException e1) {
					removeObject = true;
				}
				catch (InvocationTargetException e1) {
					removeObject = true;
				}
				if (active) {
					try {
						pool.validatesObject(pair.getValue());
						pool.deactivateObject(pair.getValue());
					}
					catch (Throwable e) {
						removeObject = true;
					}
				}
			}
			if (removeObject) {
				pool.pool.remove(pair);
				try {
					pool.destroyObject(pair.getValue());
				}
				catch (Throwable e) {
				}
			}
		}
	}

	/**
	 * Start the eviction thread or service, or when <i>delay</i> is
	 * non-positive, stop it if it is already running.
	 * 
	 * @param evictor
	 *            the evictor
	 * @param delay
	 *            milliseconds between evictor runs.
	 * @param basePool
	 *            the base pool
	 * @param lifo
	 *            if is <code>'last in first out'</code>
	 */
	public synchronized static void startEvictor(Evictor evictor, long delay,
			AbstractBaseFactory basePool, boolean lifo) {

		if (null != evictor) {
			EvictionTimer.cancel(evictor);
			evictor = null;
		}
		if (delay > 0) {
			evictor = new Evictor(basePool, lifo);
			EvictionTimer.schedule(evictor, delay, delay);
		}
	}
}
