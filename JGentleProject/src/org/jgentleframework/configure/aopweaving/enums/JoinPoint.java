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
package org.jgentleframework.configure.aopweaving.enums;

/**
 * Chỉ định các thông tin <code>join point</code> trong AOP, enum này được sử
 * dụng để chỉ định các trường hợp chèn thông tin xử lý Before, After, Around,
 * ... trong nhiều vị trí khác nhau khi cấu hình hệ thống bằng AOP thông qua
 * <code>annotation</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 */
public enum JoinPoint {
	BEFORE_METHOD, AFTER_METHOD, AROUND_METHOD, THROWS_METHOD
}
