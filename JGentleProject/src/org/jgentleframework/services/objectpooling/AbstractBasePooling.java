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

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.context.beans.Initializing;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.reflection.metadata.Definition;
import org.jgentleframework.services.objectpooling.annotation.Pooling;
import org.jgentleframework.services.objectpooling.annotation.SystemPooling;
import org.jgentleframework.utils.Assertor;

/**
 * The Class AbstractBasePooling.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 1, 2009
 */
public abstract class AbstractBasePooling implements Pool, Initializing,
		BasePooling {
	/** The can be pooled. */
	protected boolean		canBePooled					= SystemPooling.DEFAULT_CAN_BE_POOLED;

	/** The creation time out. */
	protected long			creationTimeOut				= SystemPooling.DEFAULT_CREATION_TIME_OUT;

	/** The enable. */
	protected boolean		enable						= true;

	/** My idle object eviction {@link TimerTask}, if any. */
	protected Evictor		evictor						= null;

	/** The exhausted action type. */
	protected byte			exhaustedActionType			= SystemPooling.EXHAUSTED_BLOCK;

	/** The invocation. */
	protected boolean		justInTime					= SystemPooling.DEFAULT_JUST_IN_TIME;

	/** The lifo. */
	protected boolean		lifo						= SystemPooling.DEFAULT_LIFO;

	/** The max idle. */
	protected int			maxIdle						= SystemPooling.DEFAULT_MAX_IDLE;

	/** The max pool size. */
	protected int			maxPoolSize					= SystemPooling.DEFAULT_MAX_POOL_SIZE;

	/** The min evictable idle time. */
	protected long			minEvictableIdleTime		= SystemPooling.DEFAULT_MIN_EVICTABLE_IDLE_TIME;

	/** The min idle. */
	protected int			minIdle						= SystemPooling.DEFAULT_MIN_IDLE;

	/** The min pool size. */
	protected int			minPoolSize					= SystemPooling.DEFAULT_MIN_POOL_SIZE;

	/** The num tests per eviction run. */
	protected int			numTestsPerEvictionRun		= SystemPooling.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	/** The soft min evictable idle time. */
	protected long			softMinEvictableIdleTime	= SystemPooling.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME;

	/** The test on obtain. */
	protected boolean		testOnObtain				= SystemPooling.DEFAULT_TEST_ON_OBTAIN;

	/** The test while idle. */
	protected boolean		testWhileIdle				= SystemPooling.DEFAULT_TEST_WHILE_IDLE;

	/** The time between eviction runs. */
	protected long			timeBetweenEvictionRuns		= SystemPooling.DEFAULT_TIME_BETWEEN_EVICTION_RUNS;

	/** The config. */
	protected Pooling		config						= null;

	/** The definition. */
	protected Definition	definition					= null;

	/** The selector. */
	protected Selector		selector					= null;

	/** The log. */
	protected final Log		log							= LogFactory
																.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#getCreationTimeOut
	 * ()
	 */
	@Override
	public synchronized long getCreationTimeOut() {

		return creationTimeOut;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.BasePooling#getEvictor()
	 */
	@Override
	public synchronized Evictor getEvictor() {

		return evictor;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * getExhaustedActionType()
	 */
	@Override
	public synchronized byte getExhaustedActionType() {

		return exhaustedActionType;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.BasePooling#getMaxIdle()
	 */
	@Override
	public synchronized int getMaxIdle() {

		return maxIdle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#getMaxPoolSize()
	 */
	@Override
	public synchronized int getMaxPoolSize() {

		return maxPoolSize;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * getMinEvictableIdleTime()
	 */
	@Override
	public synchronized long getMinEvictableIdleTime() {

		return minEvictableIdleTime;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.BasePooling#getMinIdle()
	 */
	@Override
	public synchronized int getMinIdle() {

		return minIdle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#getMinPoolSize()
	 */
	@Override
	public synchronized int getMinPoolSize() {

		return minPoolSize;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * getNumTestsPerEvictionRun()
	 */
	@Override
	public synchronized int getNumTestsPerEvictionRun() {

		return numTestsPerEvictionRun;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * getSoftMinEvictableIdleTime()
	 */
	@Override
	public synchronized long getSoftMinEvictableIdleTime() {

		return softMinEvictableIdleTime;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * getTimeBetweenEvictionRuns()
	 */
	@Override
	public synchronized long getTimeBetweenEvictionRuns() {

		return timeBetweenEvictionRuns;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#isCanBePooled()
	 */
	@Override
	public synchronized boolean isCanBePooled() {

		return canBePooled;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.BasePooling#isEnable()
	 */
	@Override
	public final synchronized boolean isEnable() {

		return enable;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#isJustInTime()
	 */
	@Override
	public synchronized boolean isJustInTime() {

		return this.justInTime;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.BasePooling#isLifo()
	 */
	@Override
	public synchronized boolean isLifo() {

		return lifo;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#isTestOnObtain()
	 */
	@Override
	public synchronized boolean isTestOnObtain() {

		return testOnObtain;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#isTestWhileIdle()
	 */
	@Override
	public synchronized boolean isTestWhileIdle() {

		return testWhileIdle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setCanBePooled
	 * (boolean)
	 */
	@Override
	public synchronized void setCanBePooled(boolean canBePooled) {

		this.canBePooled = canBePooled;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setCreationTimeOut
	 * (long)
	 */
	@Override
	public synchronized void setCreationTimeOut(long creationTimeOut) {

		this.creationTimeOut = creationTimeOut;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setEnable(boolean
	 * )
	 */
	@Override
	public synchronized void setEnable(boolean enable) {

		this.enable = enable;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setEvictor(org
	 * .jgentleframework.services.objectpooling.Evictor)
	 */
	@Override
	public synchronized void setEvictor(Evictor evictor) {

		this.evictor = evictor;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * setExhaustedActionType(byte)
	 */
	@Override
	public synchronized void setExhaustedActionType(byte exhaustedActionType) {

		this.exhaustedActionType = exhaustedActionType;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setJustInTime
	 * (boolean)
	 */
	@Override
	public synchronized void setJustInTime(boolean justInTime) {

		this.justInTime = justInTime;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setLifo(boolean)
	 */
	@Override
	public synchronized void setLifo(boolean lifo) {

		this.lifo = lifo;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setMaxIdle(int)
	 */
	@Override
	public synchronized void setMaxIdle(int maxIdle) {

		this.maxIdle = maxIdle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setMaxPoolSize
	 * (int)
	 */
	@Override
	public synchronized void setMaxPoolSize(int maxPoolSize) {

		this.maxPoolSize = maxPoolSize;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * setMinEvictableIdleTime(long)
	 */
	@Override
	public synchronized void setMinEvictableIdleTime(long minEvictableIdleTime) {

		this.minEvictableIdleTime = minEvictableIdleTime;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setMinIdle(int)
	 */
	@Override
	public synchronized void setMinIdle(int minIdle) {

		this.minIdle = minIdle;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setMinPoolSize
	 * (int)
	 */
	@Override
	public synchronized void setMinPoolSize(int minPoolSize) {

		this.minPoolSize = minPoolSize;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * setNumTestsPerEvictionRun(int)
	 */
	@Override
	public synchronized void setNumTestsPerEvictionRun(
			int numTestsPerEvictionRun) {

		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * setSoftMinEvictableIdleTime(long)
	 */
	@Override
	public synchronized void setSoftMinEvictableIdleTime(
			long softMinEvictableIdleTime) {

		this.softMinEvictableIdleTime = softMinEvictableIdleTime;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setTestOnObtain
	 * (boolean)
	 */
	@Override
	public synchronized void setTestOnObtain(boolean testOnObtain) {

		this.testOnObtain = testOnObtain;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#setTestWhileIdle
	 * (boolean)
	 */
	@Override
	public synchronized void setTestWhileIdle(boolean testWhileIdle) {

		this.testWhileIdle = testWhileIdle;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.services.objectpooling.BasePooling#
	 * setTimeBetweenEvictionRuns(long)
	 */
	@Override
	public synchronized void setTimeBetweenEvictionRuns(
			long timeBetweenEvictionRuns) {

		this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.BasePooling#assertDisable()
	 */
	@Override
	public final void assertDisable() throws IllegalStateException {

		if (!isEnable()) {
			throw new IllegalStateException("Pool is not enabled !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.context.beans.Initializing#initialize()
	 */
	@Override
	public synchronized void initialize() {

		Assertor.notNull(config,
				"The given [pooling configuration] must not be null !!");
		SystemPooling systemConfig = config.options();
		this.enable = config.enable();
		this.minPoolSize = config.MinPoolSize();
		this.maxPoolSize = config.MaxPoolSize();
		if (this.minPoolSize > this.maxPoolSize) {
			if (log.isFatalEnabled())
				log
						.fatal(
								"The [max pool size] must be equal or greater than [min pool size] !",
								new PoolConfigurationException());
		}
		this.creationTimeOut = config.creationTimeOut();
		this.justInTime = config.JustInTime();
		this.minIdle = systemConfig.minIdle();
		this.maxIdle = systemConfig.maxIdle();
		this.exhaustedActionType = systemConfig.exhaustedActionType();
		this.testOnObtain = systemConfig.testOnObtain();
		this.testWhileIdle = systemConfig.testWhileIdle();
		this.numTestsPerEvictionRun = systemConfig.numTestsPerEvictionRun();
		this.minEvictableIdleTime = systemConfig.minEvictableIdleTime();
		this.timeBetweenEvictionRuns = systemConfig.timeBetweenEvictionRuns();
		this.softMinEvictableIdleTime = systemConfig.softMinEvictableIdleTime();
		this.lifo = systemConfig.LIFO();
	}
}
