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
package org.jgentleframework.configure;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import org.jgentleframework.configure.annotation.Annotate;
import org.jgentleframework.configure.annotation.Bean;
import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.configure.annotation.Builder;
import org.jgentleframework.configure.annotation.Filter;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.annotation.Outject;
import org.jgentleframework.configure.aopweaving.annotation.After;
import org.jgentleframework.configure.aopweaving.annotation.Around;
import org.jgentleframework.configure.aopweaving.annotation.Before;
import org.jgentleframework.configure.aopweaving.annotation.Throws;

/**
 * <code>Template class</code> là một <code>abstract class</code> chứa đựng các
 * thể hiện của các <code>annotation</code> sẽ được <code>generate</code> động
 * khi cấu hình một <code>dynamic defnition</code> nhưng không có thông tin cấu
 * hình cụ thể bằng <code>annotation</code> đi kèm.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 24, 2007
 */
@BeanServices(alias = "")
@Bean(value = "")
public abstract class TemplateClass {
	static HashMap<Class<? extends Annotation>, Annotation>	annotationList	= new HashMap<Class<? extends Annotation>, Annotation>();
	/**
	 * static block
	 */
	static {
		synchronized (TemplateClass.annotationList) {
			for (Annotation anno : TemplateClass.class.getAnnotations()) {
				annotationList.put(anno.annotationType(), anno);
			}
			try {
				for (Annotation anno : TemplateClass.class.getField(
						"templateProperty").getAnnotations()) {
					annotationList.put(anno.annotationType(), anno);
				}
				for (Annotation anno : TemplateClass.class.getMethod(
						"templateMethod").getAnnotations()) {
					annotationList.put(anno.annotationType(), anno);
				}
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	@Annotate("")
	@Inject
	@Outject
	public String											templateProperty;

	@Before("")
	@After("")
	@Throws("")
	@Around("")
	@Filter
	@Builder("")
	public void templateMethod() {

	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotation(Class<T> annotation) {

		if (TemplateClass.annotationList.containsKey(annotation))
			return (T) TemplateClass.annotationList.get(annotation);
		else
			throw new RuntimeException("Does not found annotation instance of "
					+ annotation);
	}
}
