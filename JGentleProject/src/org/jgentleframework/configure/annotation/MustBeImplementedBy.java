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

import org.jgentleframework.core.MustBeImplementedByException;

/**
 * Chỉ định thông tin cho một annotation rằng khi áp dụng cho một thực thể Class
 * nào thì cần phải implements các interface được chỉ định trong thuộc tính
 * interfaces của {@link MustBeImplementedBy}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @see MustBeExtendedBy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface MustBeImplementedBy {
	/**
	 * Thuộc tính chỉ định các interfaces phải implements.
	 * 
	 * @return Class[]
	 */
	Class<?>[] interfaces();

	/**
	 * Khi số lượng interfaces chỉ định lớn hơn 1, thuộc tính chỉ định nếu là
	 * true thì tương đương với phép toán AND, ngược lại nếu là false tương
	 * đương phép toán OR. Mặc định là true. <br>
	 * <br>
	 * EX: {@link @MustBeImplementedBy} (interfaces={AClass.class,
	 * BClass.class}, logicalBool = false)<br>
	 * nghĩa là thực thể được chỉ định annotation phải implements AClass hoặc
	 * BClass.
	 * 
	 * @return boolean
	 */
	boolean logicalBool() default true;

	/**
	 * Thuộc tính chỉ định ngoại lệ sẽ được ném lúc run-time nếu thông tin chỉ
	 * định bị vi phạm.
	 * 
	 * @return <code>Class<? extends RuntimeException></code>
	 */
	Class<? extends RuntimeException> throwsException() default MustBeImplementedByException.class;
}
