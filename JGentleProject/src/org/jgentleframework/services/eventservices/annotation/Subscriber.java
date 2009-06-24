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
package org.jgentleframework.services.eventservices.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chỉ định một method sẽ là một subscriber, một event receiver xử lý các sự
 * kiện được ném ra bởi event context.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscriber {
	/**
	 * Tên định danh của subscriber.
	 */
	String value();

	/**
	 * Chỉ định subcriber hiện thi khi receive event từ event context sẽ thực
	 * thi tuần tự các event torng một queue chỉ định, một cơ chế bảo đảm tính
	 * ổn định khi có nhiều publisher cùng ném ra cùng một event tương ứng đến
	 * cùng subscriber cùng lúc.
	 */
	boolean queued() default false;

	/**
	 * Chỉ định một hoặc nhiều authenticationCode, khi thuộc tính này được chỉ
	 * định tường minh, thì subcriber sẽ chỉ xử lý event khi và chỉ khi
	 * publisher được chỉ định bởi annotation {@link fireEvent} có chỉ định
	 * tương ứng authenticationCode tương ứng. Nếu authenticationCode so sánh
	 * không trùng khớp, subscriber sẽ tự chối xử lý event. Ngoài ra, nếu
	 * authenticationCode không được chỉ định tường minh, hoặc là rỗng,
	 * subscriber sẽ xử lý mọi event được ném ra bởi các publisher bất kể
	 * publisher tương ứng có chỉ định authenticationCode hay không.
	 */
	String[] authenticationCode() default "";
}
