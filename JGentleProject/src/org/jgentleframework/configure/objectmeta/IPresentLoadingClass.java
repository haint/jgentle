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
package org.jgentleframework.configure.objectmeta;

import java.util.ArrayList;

/**
 * Lưu ý <code>interface</code> không được cung cấp để sử dụng như là một
 * <code>public interface</code> để thực thi cài đặt, <code>interface</code>
 * {@link #IPresentLoadingClass} chịu trách nhiệm mô tả method trả về các object
 * class được <code>loading</code> trong một <code>object meta</code>, tự
 * bản thân <code>object meta</code> sẽ hiện thực các cài đặt này, và chỉ định
 * được sử dụng và thực thi bởi <code>container</code>. Xem thêm phần hiện
 * thực của các <code>object meta</code> để biết chi tiết.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 */
interface IPresentLoadingClass {
	/**
	 * Trả về tất cả các <code>object class</code> mà <code>object meta</code>
	 * hiện hành có sử dụng bên trong.
	 * 
	 * @return trả về một danh sách chứa đựng các <code>object class</code>
	 *         nếu có, nếu không, trả về một danh sách {@link ArrayList} rỗng.
	 */
	ArrayList<Class<?>> getPresentLoadingClasses();
}
