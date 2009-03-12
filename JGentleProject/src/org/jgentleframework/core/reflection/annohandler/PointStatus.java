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
package org.jgentleframework.core.reflection.annohandler;

/**
 * Chỉ định một thành phần <code>components</code> có khả năng trả về trạng
 * thái kích hoạt của chính nó. Một <code>component</code> có thể là những
 * <code>extension-points</code> trong <code>JGentle</code>, cũng có thể là
 * những <code>components</code> đặc biệt chỉ định cho phép JGentle truy vấn
 * và sử dụng các chức năng như là một trong những thành phần cấu trúc hệ thống
 * cục bộ của <code>JGentle</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 13, 2008
 */
public interface PointStatus {
	/**
	 * Trả về trạng thái hoạt động của <code>component</code> hiện hành.
	 * 
	 * @return trả về true nếu <code>component</code> hiện hành đang được
	 *         <code>enable</code>, ngược lại trả về false.
	 */
	boolean isEnable();
}
