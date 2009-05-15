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
package org.jgentleframework.context;

import java.util.ArrayList;

/**
 * Chỉ định một <code>component service context</code> (CSC), các <b>CSC</b>
 * cần <code>implement interface</code> này để được cung cấp chức năng như là
 * một <b>CSC</b>. Các <b>CSC</b> sẽ được khởi tạo cùng lúc ngay sau khi khởi
 * tạo <code>services context</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 23, 2007
 * @param <T>
 *            kiểu type của <code>configurable interface</code> tương ứng của
 *            <b>CSC</b>.
 */
public interface ComponentServiceContextType<T> {
	/**
	 * <code>init method</code> sẽ được thực thi ngay sau khi
	 * <code>Component Service Context (CSC)</code> chỉ định được khởi tạo.
	 * Sau khi <code>init method</code> được <code>invoked</code>, JGenlle
	 * context sẽ tự động <code>add</code> <b>CSC</b> vào trong
	 * <code>serviceProvider</code> hiện hành.
	 * 
	 * @param serviceProvider
	 *            đối tượng <code>serviceProvider</code> của CSC hiện hành
	 * @param configInstances
	 *            danh sách các <code>config instances</code> tương ứng của
	 *            <code>Component Service Context (CSC)</code> hiện hành.
	 */
	public void init(ServiceProvider serviceProvider,
			ArrayList<T> configInstances);

	/**
	 * Trả về <code>object class</code> chỉ định kiểu của
	 * <code>config class</code> tương ứng với
	 * <code>Component Service Context</code> hiện hành.
	 * 
	 * @return {@link Class}
	 */
	public Class<T> returnClassType();
}
