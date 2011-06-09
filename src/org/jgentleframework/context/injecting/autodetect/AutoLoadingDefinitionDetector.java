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
package org.jgentleframework.context.injecting.autodetect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectConstant;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.reflection.metadata.Definition;

/**
 * Là một <b>detector</b> chịu trách nhiệm tự động diễn dịch thông tin tất cả
 * các <code> object class</code> có chỉ định trong <code>configuration</code>
 * thành <code>definition</code> ngay sau khi <code>JGentle container</code>
 * được khởi tạo. <b>AutoLoadingDefinition detector</b> sẽ tự động nhận biết tất
 * cả các object class có chỉ định trong khi cấu hình trong
 * <code>configurable class</code>, tìm và nhận dạng các
 * <code>object class</code>, sau đó tự động diễn dịch thông tin
 * <code>annotation</code> trên các <code>object classes</code> chỉ định thành
 * {@link Definition}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see AbstractDetector
 * @see Provider
 */
public class AutoLoadingDefinitionDetector extends AbstractDetector {
	/**
	 * The Constructor.
	 * 
	 * @param provider
	 *            the provider
	 */
	public AutoLoadingDefinitionDetector(Provider provider) {

		super(provider);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#handling(java
	 * .util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void handling(List<Map<String, Object>> OLArray) {

		List<Class<?>> list = new ArrayList<Class<?>>();
		for (Map<String, Object> optionsList : OLArray) {
			/*
			 * Lấy ra danh sách các đối tượng cất trữ thông tin cấu hình.
			 */
			List<ObjectAttach<?>> othList = (List<ObjectAttach<?>>) optionsList
					.get(AbstractConfig.OBJECT_ATTACH_LIST);
			List<ObjectConstant> ocstList = (List<ObjectConstant>) optionsList
					.get(AbstractConfig.OBJECT_CONSTANT_LIST);
			List<Class<?>> bclist = (List<Class<?>>) optionsList
					.get(AbstractConfig.BEAN_CLASS_LIST);
			List<ObjectBindingConstant> obcList = (List<ObjectBindingConstant>) optionsList
					.get(AbstractConfig.OBJECT_BINDING_CONSTANT_LIST);
			/*
			 * Truy vấn thông tin các object class có chỉ định
			 */
			for (ObjectAttach<?> oth : othList) {
				if (oth.getPresentLoadingClasses() != null)
					list.addAll(oth.getPresentLoadingClasses());
			}
			for (ObjectConstant ocst : ocstList) {
				if (ocst.getPresentLoadingClasses() != null)
					list.addAll(ocst.getPresentLoadingClasses());
			}
			list.addAll(bclist);
			for (ObjectBindingConstant obc : obcList) {
				if (obc.getPresentLoadingClasses() != null)
					list.addAll(obc.getPresentLoadingClasses());
			}
		}
		// Khởi nạp diễn dịch thông tin Definition
		DefinitionManager defManager = provider.getDefinitionManager();
		synchronized (defManager) {
			for (Class<?> clazz : list) {
				if (!defManager.containsDefinition(clazz)) {
					defManager.loadDefinition(clazz);
				}
			}
		}
	}
}
