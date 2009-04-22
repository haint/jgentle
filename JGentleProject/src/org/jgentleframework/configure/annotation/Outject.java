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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.configure.enums.Scope;

/**
 * Specifies that a Jgentle dependency component should be outjected from the
 * annotated field or getter method of a bean instance.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 3, 2007
 * @see Inject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Documented
public @interface Outject {
	/**
	 * The specified name or reference name of outjected instance. If no value
	 * name is explicitly specified, the container will use class type of
	 * outjected object to indicate outject object.
	 */
	String value() default "";

	/**
	 * Specifies that the oujected value must not be <code>null</code>.
	 * <p>
	 * The default is <b>true</b>;
	 */
	boolean required() default true;

	/**
	 * Mặc định khi <code>outject</code> một dependency vào context thì
	 * container sẽ tự động khởi tạo và <code>outject</code> tại thời điểm khởi
	 * tạo đối tượng, tương ứng <code>invocation</code> chỉ định là
	 * <b>false</b>. Nếu thuộc tính <code>invocation</code> được chỉ định là
	 * <b>true</b>, thì khi <code>outject</code> một dependency vào context,
	 * container sẽ lựa chọn thời điểm <b>invocation</b> của instance để outject
	 * dependency chỉ định.
	 * <p>
	 * Hay nói cách khác <code>dependency</code> sẽ được <code>outject</code>
	 * vào <code>context</code> tại thời điểm instance được <code>invoke</code>.
	 * <br>
	 * <br>
	 * <b>Lưu ý:</b> bất kì method nào khi được invoke đều <i>kích hoạt</i> điều
	 * kiện hoạt động của <b>outjection</b> ngoại trừ các method có prefix là
	 * "<b>set</b>" hoặc "<b>get</b>". Hay nói cách khác tất cả các
	 * <code>setter và getter method</code> khi được invoke đều không làm thực
	 * thi tiến trình <b>outjection</b>.
	 */
	boolean invocation() default true;

	/**
	 * Specifies the scope to outject to. If no scope is explicitly specified,
	 * the default scope depends upon the scope value of reference instance
	 * according to <code>'value'</code> attribute if it was existed. In case there is no
	 * existed reference instance, the default scope will be
	 * {@link Scope#SINGLETON}.
	 * 
	 * @return {@link Scope}
	 */
	Scope scope() default Scope.UNSPECIFIED;
}
