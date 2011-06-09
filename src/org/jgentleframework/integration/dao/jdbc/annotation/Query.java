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
package org.jgentleframework.integration.dao.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chỉ định cho một <code>method</code> như là một <i>'Query Method'</i>,
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
 * <i>Lưu ý rằng:</i> khác với {@link Execute} <code>annotation</code>,
 * kết quả trả về chính là dữ liệu trả về khi thực thi câu lệnh SQL,
 * dữ liệu trả về của <code>Query annotation</code> sẽ được
 * <code>encapsulate</code> trong một <code>collection</code> tương ứng
 * với kiểu <code>generic</code> chỉ định bởi <code>return type</code>
 * của <code>method</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 15, 2008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {
	/**
	 * Chuỗi chỉ định câu lệnh <b>SQL</b> cần <i>query</i>.
	 * 
	 * @return String
	 */
	String value();
}
