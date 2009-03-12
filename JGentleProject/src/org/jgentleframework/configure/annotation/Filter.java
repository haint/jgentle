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
 * Annotation chỉ định thông tin một method sẽ là filter cho một dữ liệu
 * dependency đầu vào.
 * <p>
 * <b>Lưu ý:</b><br>- Nếu annotation Inject được chỉ định trên tham số của
 * constructor thì thuộc tính filter có hay không được chỉ định đều không có
 * hiệu lực. Tương đương filter bằng false.
 * <p> - Nếu annotation inject tương ứng với filter chỉ định là invocation bằng
 * true, filter chỉ có hiệu lực nếu annotaiton inject được chỉ định trực tiếp
 * trên field. Có nghĩa là nếu annotation inject tương ứng có invocation bằng
 * true và chỉ định trên setter method, thì filter sẽ không có hiệu lực.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 17, 2007
 * @see Inject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Filter {
	/**
	 * Thuộc tính chỉ định tên định danh tương ứng với inject.
	 */
	String value() default "";

	/**
	 * Chỉ định thứ tự ưu tiên của filter hiện hành sẽ được thực thi.
	 * <p>
	 * Mặc định là 0.
	 */
	int order() default 0;
}
