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

import org.jgentleframework.utils.data.NullClass;

/**
 * Chỉ định kết quả trả về của một thuộc tính của một annotation sẽ là
 * annotation được inject vào một class chỉ định.
 * <p>
 * <b>Lưu ý:</b> ToClass chỉ có thể được bind đến các thuộc tính của annotation
 * có return type là một annotation. Nếu vi phạm một ngoại lệ sẽ được ném ra lúc
 * run-time.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 21, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToClass {
	/**
	 * Đối tượng object class chỉ định sẽ được chỉ định inject annotation. Trong
	 * trường hợp cả clazzName và clazz được chỉ định tường minh thì thuộc tính
	 * clazz được ưu tiên.
	 */
	Class<?> clazz() default NullClass.class;

	/**
	 * Tên định danh của class chỉ định sẽ được inject annotation. Trong trường
	 * hợp cả clazzName và clazz được chỉ định tường minh thì thuộc tính clazz
	 * được ưu tiên.
	 */
	String clazzName() default "";

	/**
	 * Tên định danh ID của definition sẽ chứa đựng thông tin cấu hình hiện
	 * hành.
	 * 
	 * @return String.
	 */
	String ID();
}
