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
package org.jgentleframework.services.eventservices.servicesmodule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.services.eventservices.EventClass;
import org.jgentleframework.services.eventservices.EventServicesException;
import org.jgentleframework.services.eventservices.annotation.fireEvent;
import org.jgentleframework.services.eventservices.annotation.fireEvents;
import org.jgentleframework.services.eventservices.context.EventServiceContext;
import org.jgentleframework.services.eventservices.enums.FireEventType;

/**
 * The Class EventServiceAopModule.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 1, 2007
 */
public class EventServiceAopModule {
	/**
	 * Instantiates a new event service aop module.
	 * 
	 * @param name
	 *            the name
	 */
	public EventServiceAopModule(String name) {

	}

	/**
	 * Check service provider.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 */
	private void checkServiceProvider(ServiceProvider serviceProvider) {

		if (serviceProvider == null) {
			throw new EventServicesException(
					"This object bean does not run in ServiceProvider.");
		}
	}

	/**
	 * Thực thi fire event.
	 * 
	 * @param anno
	 *            đối tượng annotation fireEvent cất trữ thông tin về cách thức
	 *            fire event.
	 * @param serviceProvider
	 *            đối tượng serviceProvider quản lý tất cả các services.
	 */
	private void executeEvent(fireEvent anno, ServiceProvider serviceProvider) {

		String[] events = anno.events();
		boolean inParallel = anno.inParallel();
		final String authenticationCode = anno.authenticationCode();
		// Nạp các event class
		List<EventClass> eventList = new ArrayList<EventClass>();
		EventServiceContext<?> eventContext = serviceProvider
				.getCSContext(EventServiceContext.class);
		for (String event : events) {
			eventList.add(eventContext.getEvent(event));
		}
		// Thực thi event
		if (inParallel) {
			for (final EventClass event : eventList) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

						event.fireEvent(authenticationCode);
					}
				});
				thread.start();
			}
		}
		else {
			for (EventClass event : eventList) {
				event.fireEvent(authenticationCode);
			}
		}
	}

	/**
	 * Pre execute event.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 * @param serviceMethod
	 *            the service method
	 * @param serviceField
	 *            the service field
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param obj
	 *            the obj
	 * @param type
	 *            the type
	 */
	private void preExecuteEvent(ServiceProvider serviceProvider,
			HashMap<Method, Definition> serviceMethod,
			HashMap<Field, Definition> serviceField, Method method,
			Object[] args, Object obj, FireEventType type) {

		checkServiceProvider(serviceProvider);
		Definition definition = serviceMethod.get(method);
		// Nếu là fireEvent
		if (definition.isAnnotationPresent(fireEvent.class)) {
			// Lấy thông tin nội dung cấu hình
			fireEvent anno = definition.getAnnotation(fireEvent.class);
			FireEventType currentType = anno.type();
			if (currentType.equals(type)) {
				executeEvent(anno, serviceProvider);
			}
		}
		// Nếu là fireEvents
		else if (definition.isAnnotationPresent(fireEvents.class)) {
			fireEvent[] events = definition.getAnnotation(fireEvents.class)
					.value();
			for (fireEvent event : events) {
				if (event.type().equals(type)) {
					executeEvent(event, serviceProvider);
				}
			}
		}
	}

	/**
	 * Handle after module.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 * @param serviceMethod
	 *            the service method
	 * @param serviceField
	 *            the service field
	 * @param returnValue
	 *            the return value
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param obj
	 *            the obj
	 */
	public void handleAfterModule(ServiceProvider serviceProvider,
			HashMap<Method, Definition> serviceMethod,
			HashMap<Field, Definition> serviceField, Object returnValue,
			Method method, Object[] args, Object obj) {

		preExecuteEvent(serviceProvider, serviceMethod, serviceField, method,
				args, obj, FireEventType.AFTER);
	}

	/**
	 * Handle before module.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 * @param serviceMethod
	 *            the service method
	 * @param serviceField
	 *            the service field
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param obj
	 *            the obj
	 */
	public void handleBeforeModule(ServiceProvider serviceProvider,
			HashMap<Method, Definition> serviceMethod,
			HashMap<Field, Definition> serviceField, Method method,
			Object[] args, Object obj) {

		preExecuteEvent(serviceProvider, serviceMethod, serviceField, method,
				args, obj, FireEventType.BEFORE);
	}

	/**
	 * Handle throws module.
	 * 
	 * @param serviceProvider
	 *            the service provider
	 * @param serviceMethod
	 *            the service method
	 * @param serviceField
	 *            the service field
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @param obj
	 *            the obj
	 * @param e
	 *            the e
	 */
	public void handleThrowsModule(ServiceProvider serviceProvider,
			HashMap<Method, Definition> serviceMethod,
			HashMap<Field, Definition> serviceField, Method method,
			Object[] args, Object obj, Throwable e) {

		preExecuteEvent(serviceProvider, serviceMethod, serviceField, method,
				args, obj, FireEventType.THROWEXCEPTION);
	}
}
