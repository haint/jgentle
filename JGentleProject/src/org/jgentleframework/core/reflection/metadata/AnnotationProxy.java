/*
 * Copyright 2007-2008 the original author or authors.
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
package org.jgentleframework.core.reflection.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.core.IllegalPropertyException;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class represents creating proxied annotation from {@link Definition}
 * data. These proxied annotation are responsible for intermediation between
 * {@link AnnoMeta} data type and object annotation type in order to access,
 * modify configured data easily and simplify operations of using
 * {@link AnnoMeta}. A proxied annotation is a proxy of an annotation instance,
 * taking an annotation interface, but accessible data is its {@link AnnoMeta}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 15, 2007
 * @see SetValueOfAnnotation
 */
class AnnotationProxy implements InvocationHandler, SetValueOfAnnotation {
	/**
	 * Create proxied annotation
	 * 
	 * @param obj
	 *            desired original annotation instance.
	 * @param definition
	 *            the current {@link Definition} holding {@link AnnoMeta} of
	 *            original annotation instance.
	 * @return returns an proxied annotation
	 */
	public static Object createProxy(Object obj, Definition definition) {

		if (!ReflectUtils.isAnnotation(obj)) {
			throw new RuntimeException(
					"Target object of Proxy is not annotation.");
		}
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				new Class[] { ((Annotation) obj).annotationType(),
						SetValueOfAnnotation.class }, new AnnotationProxy(obj,
						definition));
	}

	private AnnoMeta	annoMeta;

	private Definition	definition;

	private Object		target	= null;

	/**
	 * Constructor
	 * 
	 * @param obj
	 * @param definition
	 */
	protected AnnotationProxy(Object obj, Definition definition) {

		this.target = obj;
		this.definition = definition;
		this.annoMeta = (AnnoMeta) definition.getAnnoMeta().getMetadata(
				((Annotation) target).annotationType());
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SetValueOfAnnotation#
	 * getDefinition()
	 */
	@Override
	public Definition getDefinition() {

		return this.definition;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 * java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object result = null;
		method.setAccessible(true);
		// Thực thi việc gọi hàm setValueOfAnnotation
		if (method.getName().equals("setValueOfAnnotation") && args.length == 2
				&& args[0].getClass().equals(String.class)) {
			return this.setValueOfAnnotation((String) args[0], args[1]);
		}
		// Thực thi việc gọi hàm getDefinition
		else if (method.getName().equals("getDefinition")) {
			result = this.getDefinition();
			return result;
		}
		else if (method.getName().equals("hashCode")
				|| method.getName().equals("equals")
				|| method.getName().equals("toString")) {
			return method.invoke(this.target, args);
		}
		else if (method.getName().equals("annotationType")) {
			return method.invoke(this.target, args);
		}
		result = this.annoMeta.getMetadata(method.getName()).getValue();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.SetValueOfAnnotation#
	 * setValueOfAnnotation(java.lang.String, java.lang.Object)
	 */
	@Override
	public Metadata setValueOfAnnotation(String valueName, Object value) {

		Object resource = this.annoMeta.getMetadata(valueName).getValue();
		if (resource.getClass().isArray()) {
			if (!value.getClass().isArray()) {
				throw new IllegalPropertyException("Setting value is invalid.");
			}
			if (!resource.getClass().getComponentType().equals(
					value.getClass().getComponentType())) {
				throw new IllegalPropertyException("Setting value is invalid.");
			}
			return this.annoMeta.putMetaData(MetaDataFactory.createMetaData(
					valueName, value));
		}
		else {
			if (ReflectUtils.isAnnotation(resource)) {
				if (!ReflectUtils.isAnnotation(value)) {
					throw new IllegalPropertyException(
							"Setting value is invalid.");
				}
				if (!((Annotation) resource).annotationType().equals(
						((Annotation) value).annotationType())) {
					throw new IllegalPropertyException(
							"Setting value is invalid.");
				}
				return this.annoMeta.putMetaData(MetaDataFactory
						.createMetaData(valueName, value));
			}
			else {
				if (!resource.getClass().equals(value.getClass())) {
					throw new IllegalPropertyException(
							"Setting value is invalid.");
				}
				return this.annoMeta.putMetaData(MetaDataFactory
						.createMetaData(valueName, value));
			}
		}
	}
}
