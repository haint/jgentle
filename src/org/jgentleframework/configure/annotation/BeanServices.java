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
 * This <code>annotation</code> provides some information about the specified
 * <code>service bean</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 11, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BeanServices {
	/**
	 * The alias name of <code>service</code>
	 */
	String alias();

	/**
	 * Sets <b>true</b> in order to indicate this current processor instance is
	 * a singleton. Default value is <b>true</b>.
	 */
	boolean singleton() default true;

	/**
	 * Thuộc tính <b>lazy_init</b> chỉ định cách thức khởi tạo của các
	 * <code>service</code> được định nghĩa bởi <code>user</code>. Mặc định
	 * <code>Context</code> sẽ tự động khởi tạo các
	 * <code>services instance</code> khi và chỉ khi <code>instance bean</code>
	 * được get ra từ <code>Context</code> hoặc hàm <code>createService</code>
	 * được <code>invoked</code>, tương đương thuộc tính <b>lazy_init</b> là
	 * <b>true</b>. Ngược lại nếu thuộc tính <b>lazy_init</b> là <b>false</b>,
	 * <code>service instance</code> của <code>definition</code> đang xét sẽ tự
	 * động được khởi tạo ngay khi <code>definition</code> được
	 * <code>register</code> vào <code>Context</code>.<br>
	 * <br>
	 * <b>Chú ý:</b> Giá trị mặc định của thuộc tính là <b>true</b>.
	 * 
	 * @return boolean
	 */
	boolean lazy_init() default true;

	/**
	 * The specified domain corrsponding to this <b>service class</b>.
	 */
	String domain() default BeanServices.DEFAULT_DOMAIN;

	/**
	 * <code>DEFAULT_DOMAIN</code> name.
	 */
	public static final String	DEFAULT_DOMAIN	= "DEFAULT_DOMAIN";
}
