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

import org.jgentleframework.integration.remoting.httprmi.HTTPWrappingProcessor;
import org.jgentleframework.integration.remoting.rmi.support.RmiWrappingProcessor;

/**
 * <code>Interface</code> này mô tả một <code>WrappingProcessor</code>,
 * chịu trách nhận xử lý các {@link RemoteInvocationImpl} được gửi đến từ phía
 * <code>client</code>. Các cài đặt chi tiết của từng
 * {@link WrappingProcessor} đều cần phải thực thi cài đặt và hiện thực hóa
 * <code>interface</code> này bao gồm {@link RmiWrappingProcessor},
 * {@link HTTPWrappingProcessor}, ...
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 16, 2008
 * @see RmiWrappingProcessor
 * @see HTTPWrappingProcessor
 */
public interface WrappingProcessor {
	/**
	 * Thực thi xử lý một <code>invocation</code> được gửi đến tương ứng với
	 * một <code>target object</code> chỉ định.
	 * 
	 * @param invocation
	 *            đối tượng {@link RemoteInvocationImpl}
	 * @param targetObject
	 *            đối tượng <code>target object</code> tương ứng với
	 *            {@link RemoteInvocationImpl}
	 * @return Trả về kết quả thực thi xử lý từ invocation nếu có, nếu không trả
	 *         về một giá trị <b>null</b>.
	 * @throws RemoteException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object invoke(RemoteInvocation invocation, Object targetObject)
			throws RemoteException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException;
}