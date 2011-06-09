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
package org.jgentleframework.configure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a block configuration method. Chỉ định một khối cấu hình. Khối cấu
 * hình chịu trách nhiệm ràng buộc tất cả các lời triệu gọi phương thức cấu hình
 * trong một method của configurable class là đồng nhất tương ứng với một hoặc
 * nhiều config instance chỉ định. Các config instance được chỉ định tách biệt
 * bởi kiểu type (interface) của chúng lúc implements của configurable class.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 18, 2008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Block {
	/**
	 * A array containing all configurable instances bound to current block
	 */
	Class<?>[] value() default {};
}
