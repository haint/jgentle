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
package org.jgentleframework.context.services;

/**
 * Chỉ định thông tin listener của service context, ServiceContextListener sẽ
 * nắm hết tất cả thông tin event được ném ra khi có bất kì một service nào được
 * thêm vào hoặc gỡ bỏ ra khỏi context.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 1, 2007
 */
public interface ServiceContextListener {
	/**
	 * Chịu trách nhiệm thực thi khi có bất kì một service nào được add thêm vào
	 * trong context.
	 * 
	 * @param serviceClass
	 *            đối tượng object class của service class.
	 */
	public void serviceAvailable(Class<?> serviceClass);

	/**
	 * Chịu trách nhiệm thực thi khi có bất kì một service nào bị remove ra khỏi
	 * context.
	 * 
	 * @param serviceClass
	 *            đối tượng object class của service class.
	 */
	public void serviceRevoked(Class<?> serviceClass);
}
