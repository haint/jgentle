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

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;

/**
 * Represents the class is a JGentle class which can be used to instantiate
 * JGentle bean. Chỉ định một Class sẽ được dùng để khởi tạo bean (JGentle
 * Bean).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 5, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {
	/**
	 * Thuộc tính chỉ định tên định danh sẽ được dùng cho <code>bean</code> nếu
	 * có.<br>
	 * <br>
	 * <b>Lưu ý:</b><br>
	 * - tên định danh sử dụng phải là duy nhất, do đó sẽ không thể tồn tại 2
	 * tên định danh giống nhau của 2 bean khác nhau trong cùng 1 context. <br>
	 * - Nếu trong trường hợp tên định danh không được chỉ định tường minh, mặc
	 * định sẽ là tên của <code>class</code>, <code>annotation</code> hoặc
	 * <code>enum</code> được chỉ định.
	 * 
	 * @return String
	 */
	String value();

	/**
	 * Thuộc tính scope chỉ định <code>scope</code> của <code>bean</code> sẽ
	 * được trả về.
	 * <p>
	 * Lưu ý rằng đối với các bean được chỉ định khởi tạo dựa trên thông tin cấu
	 * hình bởi thuộc tính này sẽ không thể chỉ định các Scope nằm ngoài sự hỗ
	 * trợ của JGentle container. Hay nói cách khác, các JGentle bean này không
	 * thể chỉ định <code>custom scope</code>. Thông tin về việc khởi tạo và
	 * định nghĩa <code>custom scope</code> vui lòng xem tại phần mô tả của
	 * {@link ScopeImplementation} <code>interface</code>.
	 * 
	 * @return {@link Scope}
	 * @see Scope
	 */
	Scope scope() default Scope.SINGLETON;

	/**
	 * Thuộc tính <b>lazy_init</b> chỉ định cách thức khởi tạo của các bean được
	 * định nghĩa bởi <code>configuration</code>. Mặc định <code>Context</code>
	 * sẽ tự động khởi tạo các instance bean khi và chỉ khi instance bean được
	 * get ra từ Context , tương đương thuộc tính <b>lazy_init</b> là
	 * <b>true</b>. Ngược lại nếu thuộc tính <b>lazy-init</b> là <b>false</b>,
	 * instance bean của <code>definition</code> đang xét sẽ tự động được khởi
	 * tạo ngay khi definition được nạp vào Context (JGentle container).<br>
	 * <br>
	 * <b>Chú ý</b> Giá trị mặc định của thuộc tính là <b>true</b> và thuộc tính
	 * thực sự không có hiệu lực với các bean được chỉ định <b>scope</b> là
	 * <code>Prototype</code>.
	 * 
	 * @return boolean
	 * @see Scope
	 */
	boolean lazy_init() default false;
}
