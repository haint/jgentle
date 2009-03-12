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

import org.jgentleframework.core.MustBeExtendedByException;

/**
 * Chỉ định thông tin cho một annotation rằng khi áp dụng cho một thực thể Class
 * nào thì cần phải extends class được chỉ định trong thuộc tính interfaces của
 * {@link MustBeImplementedBy}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @see MustBeImplementedBy
 * @date Aug 3, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MustBeExtendedBy {
	/**
	 * Thuộc tính chỉ định Class cần phải extends. Nếu số lượng class chỉ định
	 * cần extends lớn hơn 1 tương đương ERB Container sẽ tự động kiểm tra Class
	 * hoặc Interface được chỉ định annotation có extends 1 trong số các clazz
	 * (class, interface) đã được cấu hình hay không.<br>
	 * <br>
	 * EX: {@link @MustBeExtendedBy}(clazz = { AClass.class, BClass.class }) có
	 * nghĩa là thực thể được cấu hình phải extends từ class AClass hoặc từ
	 * BClass.
	 * 
	 * @return Class[]
	 */
	Class<?>[] clazz();

	/**
	 * Thuộc tính chỉ định ngoại lệ sẽ được ném lúc run-time nếu thông tin chỉ
	 * định bị vi phạm.
	 * 
	 * @return <code>Class<? extends RuntimeException></code>
	 */
	Class<? extends RuntimeException> throwsException() default MustBeExtendedByException.class;
}
