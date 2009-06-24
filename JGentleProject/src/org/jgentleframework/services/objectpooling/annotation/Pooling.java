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

import org.jgentleframework.services.objectpooling.PoolType;

/**
 * This annotation provides some basic parameters that affect the pooling
 * service.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 3, 2007
 * @see SystemPooling
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pooling {
	/**
	 * Specifies <b>true</b> to activate the pooling service. Default is
	 * <b>true</b>.
	 */
	boolean enable() default true;

	/**
	 * JustInTime activation (TRUE) allows multiple clients to use your
	 * component seemingly simultaneously. It has an interesting effect on
	 * scalability for a poolable object and allows an object to deactivate
	 * after a method call and to activate upon the next method call. Because a
	 * client does not have control over the component between method calls, the
	 * component is freed back into the pool. Once the component goes back to
	 * the pool, it becomes available for other clients.
	 * <p>
	 * The default setting for this parameter is <b>false</b>.
	 */
	boolean JustInTime() default SystemPooling.DEFAULT_JUST_IN_TIME;

	/**
	 * This property controls the minimum objects that must be maintained in the
	 * pool at all times. If the number of idle objects in the pool falls below
	 * this value, then the evictor thread (if running) starts creating new
	 * objects to rise to this level.
	 * <p>
	 * The values should be greater than or equal to <b>0</b>. The default
	 * setting for this parameter is <b>0</b>.
	 */
	int MinPoolSize() default SystemPooling.DEFAULT_MIN_POOL_SIZE;

	/**
	 * This parameter defines the maximum number of objects that can be obtained
	 * from the pool at a given time. If this value is reached, the pool is
	 * exhausted and can’t give out any more objects. However, this behavior can
	 * be controlled by the {@link SystemPooling#exhaustedActionType()}
	 * property.
	 * <p>
	 * Values should be greater than or equal to 0. If a negative value is
	 * specified, there is no limit on the obtained objects, and theoretically
	 * the pool can never be exhausted.
	 * <p>
	 * The default setting for this parameter is <b>8</b>
	 */
	int MaxPoolSize() default SystemPooling.DEFAULT_MAX_POOL_SIZE;

	/**
	 * The default maximum amount of time (in milliseconds) the
	 * {@link PoolType#obtainObject()} method should block before throwing an
	 * exception when the pool is exhausted and the
	 * {@link SystemPooling#exhaustedActionType() "when exhausted" action} is
	 * {@link SystemPooling#EXHAUSTED_BLOCK}.
	 * <p>
	 * The values should be greater than <b>0</b>. When less than or equal to 0,
	 * the {@link PoolType#obtainObject()} method may block indefinitely.
	 * <p>
	 * The default setting for this parameter is <b>-1</b> (the
	 * {@link PoolType#obtainObject()} method is blocked indefinitely).
	 */
	long creationTimeOut() default SystemPooling.DEFAULT_CREATION_TIME_OUT;

	/**
	 * Provides all the configuration parameters that affect the pooling
	 * service.
	 */
	SystemPooling options() default @SystemPooling;
}
