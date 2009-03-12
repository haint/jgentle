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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.utils.data.NullClass;

/**
 * Chỉ định một thuộc tính của một annotation sẽ là annotation được inject vào
 * trong các method được chỉ định.
 * <p>
 * <b>Lưu ý:</b> ToMethod chỉ có thể được bind đến các thuộc tính của
 * annotation có return type là một annotation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 21, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToMethod {
	/**
	 * Danh sách tên methods sẽ chỉ định được inject annotation. Có thể chỉ định
	 * một nhóm các method bằng kí tự đại diện * hoặc ?
	 */
	String[] methods();

	/**
	 * Danh sách các Class Type của các arguments của method chỉ định.
	 * <p> - Nếu thuộc tính này được chỉ định tường minh thì bắt buộc method chỉ
	 * cho phép chứa đựng 1 va chỉ 1 value, nếu không một ngoại lệ sẽ được ném
	 * ra lúc run-time.
	 */
	Class<?>[] args() default { NullClass.class };

	/**
	 * Đối tượng object class của class chỉ định sẽ được tiêm annotation.
	 */
	Class<?> clazz();

	/**
	 * Tên định danh ID của definition sẽ chứa đựng thông tin cấu hình hiện
	 * hành.
	 * 
	 * @return String.
	 */
	String ID();
}
