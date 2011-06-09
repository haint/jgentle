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

import org.jgentleframework.services.eventservices.enums.FireEventType;

/**
 * Chỉ định một method sẽ là một publisher method, ném ra một event.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 27, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface fireEvent {
	/**
	 * Chỉ định các tên định danh của event sẽ được ném ra.
	 */
	String[] events();

	/**
	 * Thuộc tính inParallel khi được chỉ định là true tương đương khi các event
	 * được notified, thì event context sẽ tự động ném ra các event cùng lúc với
	 * publisher method, ngược lại nếu thuộc tính có giá trị là false thì các
	 * event sẽ được thực thi lần lượt tuần tự theo thứ tự được chỉ định trong
	 * thuộc tính event, sau khi các event được chỉ định thực thi hoàn tất,
	 * publisher method mới được thực thi hoặc return.
	 */
	boolean inParallel() default false;

	/**
	 * Vị trí sẽ ném ra event lúc run-time, thông tin về thời điểm sẽ ném ra
	 * event được chỉ định trong enum FireEventType.
	 * <p>
	 * Giá trị mặc định là AFTER.
	 */
	FireEventType type() default FireEventType.AFTER;

	/**
	 * Mã chứng thực đính kèm khi fire event, chỉ định cho subscriber sẽ
	 * validate khi subscriber nhận xử lý một event.
	 */
	String authenticationCode() default "";
}
