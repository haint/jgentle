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
package org.jgentleframework.configure;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 17, 2008
 */
public interface ConfigModule {
	/**
	 * Trả về danh sách chứa đựng các thông tin cấu hình.
	 * 
	 * @return Hashmap
	 */
	HashMap<String, ArrayList<?>> getOptionsList();

	/**
	 * Trả về target class của proxy hiện hành.
	 * 
	 * @return Class
	 */
	Class<? extends Configurable> getTargetClass();

	/**
	 * Trả về đối tượng thực thể của config module tương ứng với kiểu type chỉ
	 * định.
	 * 
	 * @param type
	 *            kiểu type của config module class tương ứng.
	 * @return Object
	 */
	Object getConfigInstance(Class<?> type);
}
