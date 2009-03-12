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
package org.jgentleframework.integration.dao.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chỉ định cho một <code>method</code> như là một <i>'Executing Method'</i>,
 * cho phép truy xuất cơ sở dữ liệu thực thi một thao tác <i>JDBC data
 * access</i>. Phương thức được chỉ định <code>annotation</code> này sẽ
 * được <code>JDBC environment</code> của <b>JGentle</b> hiện thực cài
 * đặt truy xuất <b>DB</b> tương ứng với chuỗi chỉ định <b>SQL</b>
 * tương ứng. Điều này có nghĩa rằng <code>JGentle JDBC environment</code>
 * sẽ quản lý <code>transactions</code>, tự động xử lý các
 * <code>JDBC SQLExceptions</code> nếu có đồng thời tự động tìm và
 * khởi tạo <code>connection</code> dựa trên <code>datasource</code> phù
 * hợp.
 * <p>
 * Kết quả trả về của <code>method</code> chỉ định sẽ là dữ liệu
 * trả về khi thực thi câu lệnh <i>SQL</i>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 15, 2008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Execute {
	/**
	 * Chuỗi chỉ định câu lệnh <b>SQL</b> cần <i>execute</i>.
	 * 
	 * @return String
	 */
	String value();
}
