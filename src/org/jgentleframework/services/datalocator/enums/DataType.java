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
package org.jgentleframework.services.datalocator.enums;

/**
 * Mô tả các kiểu Type trong Registry <br>
 * <br>
 * <b>- SYSTEM</b>: miêu tả các kiểu Type thuộc về Hệ Thống, các key hoặc value
 * được miêu tả Type thuộc kiểu SYSTEM sẽ không thể delete ra khỏi hệ thống.
 * <br>
 * <br>
 * <b>- USER</b>: miêu tả các kiểu Type do người sử dụng khởi tạo, các key hoặc
 * value được miêu tả Type thuộc kiểu USER sẽ không thể khởi tạo tại root của
 * Registry. <br>
 * <br>
 * <b>- REMOTE</b>: miêu tả các kiểu Type cho các key, value cần phải thực thi
 * thông qua remote. Nếu một key được khởi tạo với Type là REMOTE thì bắt buộc
 * các key childs, và value childs của nó cũng phải là REMOTE.
 * 
 * @author LE QUOC CHUNG
 */
public enum DataType {
	/**
	 * <b>- USER</b>: miêu tả các kiểu Type do người sử dụng khởi tạo, các key
	 * hoặc value được miêu tả Type thuộc kiểu USER sẽ không thể khởi tạo tại
	 * root của MBRegistry.
	 */
	USER,
	/**
	 * <b>- SYSTEM</b>: miêu tả các kiểu Type thuộc về Hệ Thống, các key hoặc
	 * value được miêu tả Type thuộc kiểu SYSTEM sẽ không thể delete ra khỏi hệ
	 * thống. Thông thường các key hoặc value được mô tả kiểu SYSTEM sẽ được
	 * khởi tạo và detele tự động bởi hệ thống.
	 */
	SYSTEM,
	/**
	 * <b>- REMOTE</b>: miêu tả các kiểu Type cho các key, value có thể thực
	 * thi thông qua remote. Nếu một key được khởi tạo với Type là REMOTE thì
	 * bắt buộc các key childs, và value childs của nó cũng phải là REMOTE.
	 * Thuộc tính REMOTE mô tả thông tin tương tự thuộc tính USER, nhưng cho
	 * phép key hoặc value có thể truy vấn thông qua các hình thức remote (vd:
	 * RmiBinding, WebServices, ...)
	 */
	REMOTE
}
