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
package org.jgentleframework.web;

import java.util.List;
import java.util.Map;

import org.jgentleframework.context.ServiceProvider;
import org.jgentleframework.context.ServiceProviderImpl;
import org.jgentleframework.context.services.ServiceHandler;

/**
 * Chỉ định một WebProvider là một cài đặt của interface WebProvider,
 * kế thừa từ {@link ServiceProviderImpl}, chịu trách nhiệm như một chứa
 * đựng tất cả các service containers của {@link ServiceProvider}, đồng
 * thời quản lý các service containers trên nền WEB. Do kế thừa từ
 * {@link ServiceProviderImpl}, ngoài trách nhiệm như một WebProvider, nó
 * còn hoạt động và xử lý tương tự như một {@link ServiceProvider}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 10, 2007
 */
public class WebProvider extends ServiceProviderImpl {
	/**
	 * @param annoObjectHandler
	 * @param OLArray
	 */
	public WebProvider(ServiceHandler annoObjectHandler,
			List<Map<String, Object>> OLArray) {

		super(annoObjectHandler, OLArray);
	}
}
