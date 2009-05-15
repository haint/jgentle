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

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.core.AnnotatedWithException;
import org.jgentleframework.utils.data.NullAnno;

/**
 * Chỉ định thông tin annotation khi được sử dụng cần phải đính kèm thông tin
 * chú thích từ một hay nhiều annotation khác.<br>
 * <br>
 * <b>Chú ý:</b> annotation này chỉ có thể sử dụng annotate cho
 * Annotation_Type.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @see AnnotatedWithValue
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AnnotatedWith {
	/**
	 * Chỉ định thông tin các annotation cần thiết sử dụng kèm theo phải có.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return Class[]
	 */
	Class<? extends Annotation>[] annotation() default NullAnno.class;

	/**
	 * Chỉ định thông tin các annotation cần thiết sử dụng kèm theo phải có
	 * thông qua đường dẫn class path.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return String[]
	 */
	String[] annoClassPath() default "";

	/**
	 * Chỉ định thông tin các annotation cần thiết sử dụng kèm theo phải có
	 * thông qua URL.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return String[]
	 */
	/*
	 * String[] URL() default "";
	 */
	/**
	 * Thuộc tính chỉ định ngoại lệ sẽ được ném lúc run-time nếu thông tin chỉ
	 * định bị vi phạm.
	 * 
	 * @return <code>Class<? extends RuntimeException></code>
	 */
	Class<? extends RuntimeException> throwsException() default AnnotatedWithException.class;
}
