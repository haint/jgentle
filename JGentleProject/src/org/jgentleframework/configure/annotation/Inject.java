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

/**
 * Specifies that a dependency should be injected to the annotated field, setter
 * method of a bean instance or also be injected to the attribute element of
 * another {@link Annotation}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 3, 2007
 * @see Outject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface Inject {
	/**
	 * The specified name or reference name of injected instance. If no value
	 * name is explicitly specified, the container will use class type of
	 * annotated field or return type of annotated setter method to indicate
	 * injected object.
	 */
	String value() default "";

	/**
	 * If specifies <b>true</b>, the container will automatically replace the
	 * value of element with new specified injected instance disregard whether
	 * the current value of element is <code>null</code>. Otherwise, if
	 * specifies <b>false</b>, and current value of desired inject element is
	 * not <code>null</code>, the container will bypass current injecting
	 * execution.
	 * <p>
	 * Note that this attribute is not effected in case the {@link Inject}
	 * annotation is marked on setter method or the attribute element of another
	 * {@link Annotation}. In this case, its value is <b>true</b>.
	 * <p>
	 * The default value is <b>true</b>.
	 */
	boolean alwaysInject() default true;

	/**
	 * Mặc định khi <code>inject</code> một <code>dependency</code> vào một thực
	 * thể chỉ định thì <code>container</code> sẽ tự động khởi tạo và thực thi
	 * inject tại thời điểm khởi tạo đối tượng. Nếu thuộc tính
	 * <code>invocation</code> được chỉ định là <b>true</b>, thì khi
	 * <b>inject</b>, <code>container</code> sẽ lựa chọn thời điểm
	 * <code>invocation</code> của <code>instance</code> để thực thi
	 * <code>injecting</code>.
	 * <p>
	 * Hay nói cách khác <code>dependency</code> sẽ được <code>inject</code> vào
	 * <code>instance</code> tại thời điểm một <code>method</code> nào đó của
	 * <code>instance</code> được <code>invoke</code>. Trong trường hợp của
	 * annotation, đó chính là thời điểm lấy ra giá trị thuộc tính tương ứng của
	 * chính <code>annotation</code>.
	 * <p>
	 * <b>Lưu ý:</b>
	 * <p>
	 * - Nếu <code>{@code @Inject}</code> được chỉ định trên tham số của
	 * <code>constructor</code> thì thuộc tính <code>invocation</code> có hay
	 * không được chỉ định đều không có hiệu lực. Tương đương
	 * <code>invocation</code> bằng <b>false</b>.
	 */
	boolean invocation() default false;

	/**
	 * Specifies that the injected value must not be <code>null</code>.
	 * <p>
	 * The default is <b>false</b>;
	 */
	boolean required() default false;
}
