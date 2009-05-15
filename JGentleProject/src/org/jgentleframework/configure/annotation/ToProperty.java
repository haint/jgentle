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
 * Chỉ định một thuộc tính của một annotation sẽ là thông tin annotation được
 * inject thêm vào cho một hoặc nhiều properties của một class chỉ định.
 * <p>
 * <b>Lưu ý:</b> ToProperty chỉ có thể được bind đến các thuộc tính của
 * annotation có return type là một annotation và không được phép là 1 array.
 * Nếu vi phạm một ngoại lệ sẽ được ném ra lúc run-time.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 21, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ToProperty {
	/**
	 * Danh sách tên các properties của clazz chỉ định. Có thể chỉ định một nhóm
	 * các properties bằng kí tự đại diện * hoặc ?
	 */
	String[] properties();

	/**
	 * Đối tượng object class của class chỉ định sẽ inject annotation.
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
