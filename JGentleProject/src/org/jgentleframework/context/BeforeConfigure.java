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
package org.jgentleframework.context;

import java.util.List;

import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.services.ServiceHandler;
import org.jgentleframework.core.handling.AnnotationRegister;
import org.jgentleframework.core.handling.DefinitionManager;

/**
 * Chỉ định một cài đặt sẽ là một BeforeConfigure Bean, chịu trách nhiệm thực
 * thi các xử lý trước khi thông tin về configuration được thực thi (trước khi
 * hàm cấu hình configure method được invoked).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 22, 2007
 */
public interface BeforeConfigure {
	/**
	 * Thực thi tiền xử lý, cấu hình trước khi hàm cấu hình configure method
	 * được chỉ định trong {@link AbstractConfig} được invoked.
	 * 
	 * @param aoh
	 *            đối tượng <code>Annotation Object Handler</code> sẽ được chỉ
	 *            định trong {@link Provider} hoặc <code>service context</code>.
	 *            <p>
	 * @param defManager
	 *            đối tượng <code>Definition Manager</code> sẽ được chỉ định
	 *            trong {@link Provider} hoặc <code>service context</code>. Đối
	 *            tượng này tương đương object được get ra từ
	 *            <code>Annotation Object Handler (aoh)</code> thông qua
	 *            <code> getDefManager method</code>.
	 *            <p>
	 * @param annoRegister
	 *            đối tượng <code>Annotation Register</code> sẽ được chỉ định
	 *            trong {@link Provider} hoặc <code>service context</code>. Đối
	 *            tượng này tương đương object được get ra từ
	 *            <code>Definition Manager (defManager)</code> thông qua
	 *            <code>getAnnoListRegistered method</code>.
	 *            <p>
	 * @param configList
	 *            đối tượng {@link List} chứa danh sách các object class
	 *            configuration ( {@link AbstractConfig})
	 */
	public void doProcessing(ServiceHandler aoh, DefinitionManager defManager,
			AnnotationRegister annoRegister,
			List<Class<? extends Configurable>> configList);
}
