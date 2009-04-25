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
package org.jgentleframework.integration.scripting.annotation;

/**
 * Chứa thông tin cấu hình cho phép inject thông tin của bean từ một scripting
 * langue
 * 
 * @author gnut
 * 
 */
public @interface ScriptingInject {
	/**
	 * 
	 * @return vị trí chứa tập tin sctipt được tính từ classpath
	 */
	String scriptSource() default "";

	/**
	 * 
	 * @return thời gian tiến hành kiểm nội dung scriptSource khi có một sự thay
	 *         đổi của scriptSource nội dung của của bean trong scriptSource sẽ
	 *         được nạp lên lại.
	 */
	long refreshCheckDelay() default -1;

	/**
	 * 
	 * @return chỉ định nội dung của scripting ngay trong code
	 */
	String inlineScript() default "";

	/**
	 * 
	 * @return Ngôn ngữ scripting sử dụng để nạp bean.
	 */
	String lang();
}
