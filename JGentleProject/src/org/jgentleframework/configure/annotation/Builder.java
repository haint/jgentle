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
package org.jgentleframework.configure.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation Builder chỉ định thông tin một method sẽ là một builder của một
 * dependency được outject đến container. Khi một method được chỉ định sẽ là
 * builder của một outjector, nếu dữ liệu outject là null (và thuộc tính
 * required trên {@link Outject} bằng true), tự động container sẽ tìm kiếm
 * builder tương ứng của của thực thể outject chỉ định để khởi tạo thông tin dữ
 * liệu, đối tượng trước khi outject.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 17, 2007
 * @see Outject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Builder {
	/**
	 * Thuộc tính chỉ định tên định danh của outject tương ứng với builder.
	 */
	String value() default "";
}
