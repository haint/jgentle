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
package org.jgentleframework.services.eventservices;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jgentleframework.services.eventservices.annotation.Subscriber;
import org.jgentleframework.services.eventservices.objectmeta.ISubscriber;

/**
 * Là một cài đặt trên Runnable cho phép thực thi trên thread, chịu trách nhiệm
 * nhận các event message được gửi từ publisher đến subscriber thông qua một
 * queue chỉ định subscriber.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 28, 2007
 */
public class SubscriberQueuedThread implements Runnable {
	private ConcurrentLinkedQueue<SubscriberMessages>	queue	= new ConcurrentLinkedQueue<SubscriberMessages>();

	private ISubscriber									subscriber;

	public SubscriberQueuedThread(ISubscriber subscriber) {

		this.subscriber = subscriber;
	}

	boolean	running	= true;

	@Override
	public void run() {

		while (running) {
			if (queue.isEmpty()) {
				try {
					this.wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				SubscriberMessages data = queue.poll();
				Method method = subscriber.getSubscriberMethod();
				Object source = subscriber.getSource();
				Subscriber anno = subscriber.getDefinition().getAnnotation(
						Subscriber.class);
				List<String> authenticationCodes = Arrays.asList(anno
						.authenticationCode());
				if (authenticationCodes.size() == 1
						&& authenticationCodes.get(0).isEmpty()) {
					try {
						method.setAccessible(true);
						method.invoke(source, data.getArgs());
					}
					catch (IllegalArgumentException e) {
					}
					catch (IllegalAccessException e) {
					}
					catch (InvocationTargetException e) {
					}
				}
				else {
					if (data.getAuthenticationCode() != null) {
						if (authenticationCodes.contains(data
								.getAuthenticationCode())) {
							try {
								method.setAccessible(true);
								method.invoke(source, data.getArgs());
							}
							catch (IllegalArgumentException e) {
							}
							catch (IllegalAccessException e) {
							}
							catch (InvocationTargetException e) {
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Add một event message vào queue hiện hành của subscriber.
	 * 
	 * @param mess
	 *            dữ liệu event message cần xử lý.
	 */
	public void addMessage(SubscriberMessages mess) {

		this.queue.add(mess);
		this.notify();
	}

	/**
	 * Dừng thread xử lý nhận event message của subscriber.
	 */
	public void stopReceive() {

		this.running = false;
		this.notify();
	}
}
