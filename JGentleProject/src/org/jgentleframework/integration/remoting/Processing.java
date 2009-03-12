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
package org.jgentleframework.integration.remoting;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * Chỉ định một processing cơ bản, chịu trách nhiệm thực thi xử lý một
 * <code>remote invocation</code> dựa trên thông tin một
 * {@link WrappingProcessor} cung cấp. Tất cả
 * <code>wrapping bean exporter</code> đều cần thiết phải
 * <code>implements {@link Processing} interface</code>, đồng thời hiện thực
 * cài đặt cho <code>invoke method</code> nhằm xử lý cho các
 * {@link RemoteInvocationImpl} được gửi đến từ phía <code>client</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 16, 2008
 */
public interface Processing {
	/**
	 * Handles the <code>remote invocation</code>.
	 * 
	 * @param processor
	 *            the processor
	 * @param invocation
	 *            the invocation
	 * @return the object
	 * @throws RemoteException
	 *             the remote exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public Object invoke(WrappingProcessor processor,
			RemoteInvocation invocation) throws RemoteException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException;
}
