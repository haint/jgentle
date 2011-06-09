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
package org.jgentleframework.services.threadpooling;

import org.jgentleframework.services.queuedbeans.BlockingQueue;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 27, 2008
 */
public class PoolThread extends Thread {
	private BlockingQueue	taskQueue	= null;
	private boolean			isStopped	= false;

	public PoolThread(BlockingQueue queue) {

		taskQueue = queue;
	}

	public void run() {

		while (!isStopped()) {
			try {
				Runnable runnable = (Runnable) taskQueue.dequeue();
				runnable.run();
			}
			catch (Exception e) {
				// log or otherwise report exception,
				// but keep pool thread alive.
			}
		}
	}

	public synchronized void stops() {

		isStopped = true;
		this.interrupt(); // break pool thread out of dequeue() call.
	}

	public synchronized boolean isStopped() {

		return this.isStopped;
	}
}
