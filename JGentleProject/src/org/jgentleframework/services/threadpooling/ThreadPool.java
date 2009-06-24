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

import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.services.queuedbeans.BlockingQueue;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 27, 2008
 */
public class ThreadPool {
	private BlockingQueue		taskQueue	= null;

	private List<PoolThread>	threads		= new ArrayList<PoolThread>();

	private boolean				isStopped	= false;

	public ThreadPool(int noOfThreads, int maxNoOfTasks) {

		taskQueue = new BlockingQueue(maxNoOfTasks);
		for (int i = 0; i < noOfThreads; i++) {
			threads.add(new PoolThread(taskQueue));
		}
		for (PoolThread thread : threads) {
			thread.start();
		}
	}

	public synchronized void execute(Runnable task) throws InterruptedException {

		if (this.isStopped)
			throw new IllegalStateException("ThreadPool is stopped");
		this.taskQueue.enqueue(task);
	}

	public synchronized void stop() {

		this.isStopped = true;
		for (PoolThread thread : threads) {
			// thread.stop();
			thread.interrupt();
		}
	}
}
