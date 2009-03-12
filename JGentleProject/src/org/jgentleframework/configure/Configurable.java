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

/**
 * <i>Interface</i> này chỉ định một class sẽ là một <i>configurable class</i>,
 * <code>class implements interface Configurable</code> này yêu cầu nên là một
 * <code>abstract class</code> và không hiện thực hóa các <code>methods</code>
 * trong <code>Configurable</code>. Công việc chỉ định và hiện thực các
 * <code>methods</code> của interface này sẽ do <code>container</code> đảm
 * nhiệm.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 16, 2008
 * @see AbstractConfig
 */
public interface Configurable extends SystemConfig, BindingConfig {
	/**
	 * Configuration method.
	 */
	public void configure();

	public final static String	REF_MAPPING		= "REF_MAPPING";
	public final static String	REF_ID			= "REF_ID";
	public final static String	REF_CONSTANT	= "REF_CONSTANT";
}