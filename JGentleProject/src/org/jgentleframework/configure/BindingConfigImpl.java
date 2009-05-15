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
package org.jgentleframework.configure;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import org.jgentleframework.configure.objectmeta.ObjectAttach;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant;
import org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor;
import org.jgentleframework.configure.objectmeta.ObjectConstant;

/**
 * An implementation of {@link BindingConfig} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jan 16, 2008
 * @see AbstractConfigModule
 * @see BindingConfig
 */
public class BindingConfigImpl extends AbstractBindingConfig implements
		BindingConfig {
	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#getBeanClassList()
	 */
	@Override
	public ArrayList<Class<?>> getBeanClassList() {

		return beanClassList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.jgentle.BindingConfig#
	 * getObjBindingConstantList ()
	 */
	@Override
	public ArrayList<ObjectBindingConstant> getObjBindingConstantList() {

		return objBindingConstantList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#getObjectAttachList
	 * ()
	 */
	@Override
	public ArrayList<ObjectAttach<?>> getObjectAttachList() {

		return objectAttachList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#getObjectConstantList
	 * ()
	 */
	@Override
	public ArrayList<ObjectConstant> getObjectConstantList() {

		return objectConstantList;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.BindingConfig#getOptionsList()
	 */
	@Override
	public Map<String, Object> getOptionsList() {

		return optionsList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#ref(java.lang.String
	 * )
	 */
	@Override
	public String ref(String ID) {

		return REF.ref(ID);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#refConstant(java.
	 * lang.String)
	 */
	@Override
	public String refConstant(String constantName) {

		return REF.refConstant(constantName);
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.jgentle.BindingConfig#refMapping()
	 */
	@Override
	public String refMapping() {

		return REF.refMapping();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#refMapping(java.lang
	 * .Class)
	 */
	@Override
	public String refMapping(Class<?> clazz) {

		return REF.refMapping(clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.jgentle.BindingConfig#refMapping(java.lang
	 * .String)
	 */
	@Override
	public String refMapping(String mappingName) {

		return REF.refMapping(mappingName);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.AbstractConfigModule#setOptionsList(java
	 * .util.Map, java.lang.reflect.Method)
	 */
	@Override
	public void setOptionsList(Map<String, Object> optionsList, Method configure) {

		this.optionsList = optionsList;
		/*
		 * add các danh sách thông tin cấu hình
		 */
		this.beanClassList = new ArrayList<Class<?>>();
		this.optionsList
				.put(AbstractConfig.BEAN_CLASS_LIST, this.beanClassList);
		this.objectAttachList = new ArrayList<ObjectAttach<?>>();
		this.optionsList.put(AbstractConfig.OBJECT_ATTACH_LIST,
				this.objectAttachList);
		this.objectConstantList = new ArrayList<ObjectConstant>();
		this.optionsList.put(AbstractConfig.OBJECT_CONSTANT_LIST,
				this.objectConstantList);
		this.objBindingConstantList = new ArrayList<ObjectBindingConstant>();
		this.optionsList.put(AbstractConfig.OBJECT_BINDING_CONSTANT_LIST,
				this.objBindingConstantList);
		this.objBindingInterceptorList = new ArrayList<ObjectBindingInterceptor>();
		this.optionsList.put(AbstractConfig.OBJECT_BINDING_INTERCEPTOR_LIST,
				this.objBindingInterceptorList);
	}
}
