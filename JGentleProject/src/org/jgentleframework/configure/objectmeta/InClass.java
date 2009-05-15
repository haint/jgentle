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
package org.jgentleframework.configure.objectmeta;

import org.jgentleframework.configure.objectmeta.ObjectBindingConstant.ScopeObjectBinding;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * Represents the Class which is 'target class' of current binding bean.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 6, 2008 4:41:04 PM
 * @see ObjectAnnotating
 */
public interface InClass extends ObjectAnnotating {
	/**
	 * Sets the Class
	 * 
	 * @param clazz
	 *            the object class need to be setted.
	 */
	public InClass in(Class<?> clazz);

	/**
	 * Sets the ID
	 * 
	 * @param id
	 *            the ID
	 * @return {@link ScopeObjectBinding}
	 */
	public ScopeObjectBinding id(String id);

	/**
	 * Chỉ định thuộc tính <b>lazy-init</b> cho <code>object bean</code> hiện
	 * hành. Thuộc tính <b>lazy_init</b> chỉ định cách thức khởi tạo của bean
	 * được định nghĩa bởi <code>configuration</code>. Mặc định
	 * <code>Context</code> sẽ tự động khởi tạo các <code>instance bean</code>
	 * khi và chỉ khi<code> instance bean</code> được <i>get</i> ra từ container
	 * , tương đương thuộc tính <b>lazy_init</b> là <b>true</b>. Ngược lại nếu
	 * thuộc tính <b>lazy-init</b> là <b>false</b>, instance bean của
	 * <code>definition</code> đang xét sẽ tự động được khởi tạo ngay khi
	 * {@link Definition} của bean được nạp vào Context (JGentle container) hoặc
	 * khi điều kiện scope chỉ định khởi tạo tương ứng đã được kích hoạt.<br>
	 * <br>
	 * <b>Chú ý</b> Giá trị mặc định khi không chỉ định tường minh của thuộc
	 * tính là <b>true</b> và thuộc tính thực sự không có hiệu lực với các bean
	 * được chỉ định <b>scope</b> là <code>Prototype</code>.
	 * 
	 * @param lazyInit
	 *            giá trị boolean cần chỉ định.
	 * @return {@link InClassImpl}
	 */
	public InClass lazyInit(boolean lazyInit);
}