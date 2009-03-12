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

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jgentleframework.core.AnnotatedWithException;
import org.jgentleframework.utils.data.NullAnno;

/**
 * Chỉ định thông tin của một annotation khi áp dụng vào một thực thể yêu cầu
 * cần phải apply một annotation cụ thể và với một giá trị thuộc tính cụ thể.
 * Annotation AnnotatedWithValue tương tự {@link AnnotatedWith} nhưng vừa yêu
 * cầu đính kèm Annotation ngoại lai vừa yêu cầu annotation ngoại lai có thuộc
 * tính chỉ định phải có value như trong AnnotatedWithValue yêu cầu.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 5, 2007
 * @see AnnotatedWith
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotatedWithValue {
	/**
	 * Chỉ định thông tin annotation cần thiết sử dụng kèm theo phải có.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return Class
	 */
	Class<? extends Annotation> annotation() default NullAnno.class;

	/**
	 * Chỉ định thông tin annotation cần thiết sử dụng kèm theo phải có thông
	 * qua đường dẫn class path.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return String[]
	 */
	String annoClassPath() default "";

	/**
	 * Chỉ định thông tin annotation cần thiết sử dụng kèm theo phải có thông
	 * qua URL.<br>
	 * <br>
	 * <b>Chú ý:</b> <br>- Nếu trong trường hợp các thuộc tính annotation,
	 * annoClassPath, URL đều được chỉ định thì các thuộc tính sẽ tuân theo độ
	 * ưu tiên sau: 1. annotation , 2. annoClassPath, 3. URL<br>
	 * <br> - Nếu tất cả các thuộc tính đều không được chỉ định tường minh thì
	 * một ngoại lệ AnnotatedWithException sẽ được ném ra lúc Run-Time.
	 * 
	 * @return String[]
	 */
	/* String URL() default ""; */
	/**
	 * Chỉ định cặp thông tin (thuộc tính-giá trị) cần yêu cầu.<br>
	 * <br> - Cặp thông tin (thuộc tính-giá trị) object_value_pair phải có cấu
	 * trúc dạng "name:=value" (cho thuộc tính đơn) hoặc "name:={value1,value2}"
	 * (cho thuộc tính dạng array).<br>
	 * <br>
	 * <b>EX:</b><br>
	 * <code>
	 {@Code @AnnotatedWithValue}(annotation=Target.class,object_value_pair = { "value:=FIELD,TYPE" })
	 </code><br>
	 * <br>
	 * Ví dụ trên sẽ yêu cầu annotation chỉ định phải được đính kèm thông tin
	 * của <b>annotation Target</b> đồng thời value của Target phải là FIELD và
	 * TYPE. Hay nói cách khác ví dụ trên chỉ định annotation được cấu hình chỉ
	 * được áp dụng cho thực thể dạng Field hoặc Type.<br>
	 * <br>
	 * <b>Lưu ý:</b><br> - Thuộc tính này không áp dụng được cho kiểu
	 * annotation, nếu vi phạm một ngoại lệ sẽ được ném ra tương ứng chỉ định
	 * trong thuộc tính throwsException lúc validate.<br> - Nếu như value là 1
	 * array thì thuộc tính này chỉ quan tâm đến các thành phần bên trong array
	 * chứ không quan tâm đến thứ tự chỉ định.
	 * 
	 * @return String[]
	 */
	String[] object_value_pair();

	/**
	 * Thuộc tính chỉ định ngoại lệ sẽ được ném lúc run-time nếu thông tin chỉ
	 * định bị vi phạm.
	 * 
	 * @return <code>Class<? extends RuntimeException></code>
	 */
	Class<? extends RuntimeException> throwsException() default AnnotatedWithException.class;
}
