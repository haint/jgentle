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

/**
 * The idle object evictor {@link TimerTask}.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 13, 2009
 */
public class Evictor extends TimerTask {
	/** The base pool. */
	private final AbstractBaseFactory	basePool;

	private final boolean				lifo;

	/**
	 * Instantiates a new evictor.
	 * 
	 * @param basePool
	 *            the base pool
	 */
	public Evictor(AbstractBaseFactory basePool, boolean lifo) {

		this.basePool = basePool;
		this.lifo = lifo;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run() {

		try {
			PoolStaticUtils.evict(this.basePool, this.lifo);
		}
		catch (Exception e) {
			// ignored
		}
		try {
			PoolStaticUtils.ensureMinIdle(this.basePool);
		}
		catch (Throwable e) {
			// ignored
		}
	}
}
