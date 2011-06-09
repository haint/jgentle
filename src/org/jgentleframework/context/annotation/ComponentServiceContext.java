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
package org.jgentleframework.context.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.context.BeforeConfigure;
import org.jgentleframework.context.BeforeInitContext;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.core.provider.ServiceClass;
import org.jgentleframework.utils.data.NullClass;

/**
 * Chỉ định các thông tin của một <b>Component Service Context (CSC)</b>. Các
 * thông tin sẽ được dùng để xác lập dữ liệu <code>config</code> cho CSC khởi
 * động và khởi tạo <code>service class</code> tương ứng của CSC. Trong trường
 * hợp một CSC không được chỉ định <code>annotation</code> này sẽ không được
 * cấu hình tự động mà phải cấu hình hoạt động thông qua
 * {@link BeforeInitContext} hoặc {@link BeforeConfigure} chỉ định trong
 * JGentle.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 24, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentServiceContext {
	/**
	 * Chỉ định kiểu <code>object class type</code> đăng kí cho CSC hiện hành.
	 * Nếu không chỉ định tường minh, mặc định JGentle Container sẽ lấy kiểu
	 * <code>object class</code> của <code>class</code> mà
	 * <code>{@code @ComponentServiceContext annotation}</code> đính kèm sử dụng làm
	 * <code> object class </code> mặc định.
	 * 
	 * @return Class
	 */
	Class<?> value() default NullClass.class;

	/**
	 * Thuộc tính chỉ định các <code>annotation</code> cần phải đăng kí tương
	 * ứng của <code>service class</code>. Nếu <i>không chỉ định tường minh</i>
	 * mặc định thông tin thuộc tính sẽ là một danh sách rỗng.
	 * 
	 * @return Class
	 */
	Class<? extends Annotation>[] annotations() default {};

	/**
	 * Thuộc tính chỉ định các <code>validator</code> tương ứng của các
	 * <code>annotation</code> cần phải đăng kí (được chỉ định thông qua thuộc
	 * tính <code>annotation</code>). Thứ tự chỉ định trong
	 * <code>validator</code> cũng chính là thứ tự chỉ định tương ứng trong
	 * <code>annotationRegister</code>. Do đó, nếu được chỉ định tường minh,
	 * bắt buộc kích thước của <code>validators</code> phải tương ứng bằng
	 * hoặc nhỏ hơn kích thước được chỉ định trong thuộc tính
	 * <code>annotations</code>. Nếu vi phạm một ngoại lệ sẽ được ném ra lúc
	 * <code>run-time</code>.
	 * <p>
	 * <b>Lưu ý:</b> trong trường hợp số lượng phần tử chỉ định trong
	 * <code>validators</code> ít hơn số phần tử trong thuộc tính
	 * <code>annotations</code>, thì theo thứ tự các sẽ chỉ định các
	 * <code>validator</code> tương ứng với các <code>annotation</code>,
	 * còn các <code>annotation</code> còn lại sẽ xem như không được chỉ định
	 * <code>validator</code>.
	 * 
	 * @return {@link Class}
	 */
	Class<? extends AnnotationValidator<Annotation>>[] validators() default {};

	/**
	 * Thuộc tính này chỉ định thông tin các
	 * <code>annotation cần phải register</code> sẽ được đăng kí tại thời
	 * trước khi <code>configure method</code> được <code>invoked</code> hay
	 * không. Nếu là <b>true</b>, các thông tin <code>annotation</code> sẽ
	 * được đăng kí trước khi <code>configure method</code> được triệu gọi,
	 * nếu là <b>false</b>, các thông tin <code>annotation</code> sẽ được
	 * đăng kí sau khi <code>configure method</code> được <code>invoked</code>,
	 * sau khi <i>services context</i> đã được khởi tạo nhưng trước khi khởi
	 * tạo <i>Component Service Context</i> ( {@link BeforeInitContext} ).
	 * <p>
	 * Mặc định thuộc tính là <b>true</b>.
	 * 
	 * @return boolean
	 */
	boolean beforeConfigure() default true;

	/**
	 * Thuộc tính này chỉ định đối tượng
	 * <code>object class của service class</code> tương ứng với CSC hiện
	 * hành. Dựa vào thông tin <code>object class</code> này JGentle sẽ khởi
	 * tạo <code>object bean</code> của <code>service class</code> tương ứng
	 * khi khởi tạo <code>services context.</code>
	 * 
	 * @return {@link Class}
	 */
	Class<? extends ServiceClass> serviceClass();
}
