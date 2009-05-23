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
package org.samples;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jgentleframework.configure.annotation.BeanServices;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 4, 2007
 */
public class DefinitionTest {
	public static void main(String[] args) {

		// Khởi tạo Provider container
		Provider provider = JGentle.buildProvider();
		// Lấy ra đối tượng Definition Manager
		DefinitionManager defManager = provider.getDefinitionManager();
		// Chuyển đổi thông tin annotation có trong MyClass class
		defManager.loadDefinition(MyClass.class);
		// Lấy ra thông tin definition
		Definition def = defManager.getDefinition(MyClass.class);
		for (Annotation anno : def.getAnnotations()) {
			System.out.println("annotation type: " + anno.annotationType());
		}
		System.out.println(def.getAnnotation(BeanServices.class));
		System.out.println();
		// Hiển thị thông tin annotation từ definition.
		System.out.println("Current class value: "
				+ def.getAnnotation(MYAnno.class).value());
		// Sửa đổi thông tin annotation.
		def.setValueOfAnnotation(BeanServices.class, "alias", "my new bean");
		System.out.println("New class value: "
				+ def.getAnnotation(BeanServices.class).alias());
		System.out.println("Core class value:"
				+ def.getOriginalAnnotation(BeanServices.class).alias());
		// Lấy ra thông tin annotation từ field
		System.out.println(def.getMemberDefinitionOfField("name")[0]
				.getAnnotation(MYAnno.class).value());
	}
}

@BeanServices(alias = "MYBean")
@MYAnno
class MyClass {
	@MYAnno("field value")
	public String	name;
}

@Retention(RetentionPolicy.RUNTIME)
@interface MYAnno {
	String value() default "my current bean";
}
