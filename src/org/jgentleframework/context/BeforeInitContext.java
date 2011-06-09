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

import org.jgentleframework.configure.Configurable;

/**
 * Chỉ định các xử lý sau khi <code>services context</code> được khởi tạo,
 * nhưng trước khi các <code>Component Service Context</code> chỉ định của
 * <code>services context</code> hiện hành được khởi tạo. Nếu trong trường hợp
 * <code>beforeInitContext</code> có thực thi các xử lý khởi tạo CSC thì sau
 * khi khởi tạo CSC cần phải được <code>add</code> vào
 * <code>services context</code> thông qua <code>addCSContext method</code>
 * của <code>serviceProvider</code>. Các xử lý có thể truy vấn thông tin cấu
 * hình thông qua <code>configInstances argument</code> của
 * <code>beforeInitContext method</code> để thực thi xử lý.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 23, 2007
 */
public interface BeforeInitContext {
	/**
	 * Thực thi các xử lý trước khi các <code>Component Service Context</code>
	 * chỉ định của <code>service context</code> hiện hành được khởi tạo. Nếu
	 * như trong xử lý của <code>beforeInitContext</code> có khởi tạo CSC, thì
	 * sau khi khởi tạo CSC cần phải được <code>add</code> vào
	 * <code>services context</code> thông qua
	 * <code>addCSContext method</code> của <code>serviceProvider</code>.
	 * <p>
	 * <b>Lưu ý:</b> việc triệu gọi <code>init method</code> của
	 * <code>ComponentServiceContext interface</code> trong xử lý của
	 * <code>beforeInitContext method</code> nếu như có khởi tạo CSC là không
	 * cần thiết. Vì ngay sau khi <code>beforeInitContext method</code> thực
	 * thi kết thúc <code>JGentle context</code> sẽ tự động triệu gọi
	 * <code>init method</code>.
	 * 
	 * @param serviceProvider
	 *            đối tượng <code>serviceProvider</code> hiện hành.
	 * @param configInstances
	 *            danh sách các <code>configInstances</code> của
	 *            <code>services context</code> hiện hành.
	 */
	public void beforeInitContext(ServiceProvider serviceProvider,
			Configurable[] configInstances);
}
