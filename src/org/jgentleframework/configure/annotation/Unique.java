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

import org.jgentleframework.core.UniqueException;
import org.jgentleframework.utils.data.NullClass;

/**
 * Chỉ định một annotation là duy nhất (khi áp dụng annotation được chỉ định với
 * cấu hình Unique vào một thực thể chỉ định Class, Method, Variable, ... thì
 * thực thể đó không được phép chỉ định thêm bất kì annotation nào khác).<br>
 * <br>
 * Annotation Unique được sử dụng hạn chế cho các definition của các service yêu
 * cầu khắt khe về tính duy nhất, hoặc chỉ cho phép một tổ hợp annotation được
 * chỉ định thông qua thuộc tính except của Unique.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 10, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Unique {
	/**
	 * Thuộc tính chỉ định các annotation ngoại lệ có thể sử dụng kèm với
	 * annotation đang xét.
	 * 
	 * @return Class[]
	 */
	Class<?>[] except() default NullClass.class;

	/**
	 * Thuộc tính chỉ định ngoại lệ sẽ được ném lúc run-time nếu thông tin chỉ
	 * định bị vi phạm.
	 * 
	 * @return <code>Class<? extends RuntimeException></code>
	 */
	Class<? extends RuntimeException> throwsException() default UniqueException.class;
}
