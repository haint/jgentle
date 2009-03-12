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
package org.jgentleframework.web.controlbean;

/**
 * Chịu trách nhiệm validate thông tin data trong các components được gửi đến từ
 * request, nếu là XMLHttpRequest một phần thông tin sẽ được validate ngay tại
 * phía dưới client, một phần logic validate sẽ được thực hiện ở phía server,
 * nếu là HttpRequest thông thường thì toàn bộ thông tin data sẽ được validate
 * ngay tại phía server.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 20, 2007
 */
public class ProcessValidate extends AbsChainController {
	public ProcessValidate(String name) {

		super(name);
	}

	@Override
	public Object handling(Object... objects) {

		return null;
	}
}
