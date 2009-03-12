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
package org.jgentleframework.configure.objectmeta;

import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.ObjectBindingConstant.ScopeObjectBinding;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.core.reflection.MemberIdentification;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.Pair;

/**
 * Chịu trách nhiệm thực hiện việc thiết lập inClass và ID trên một
 * {@link ObjectBindingConstant} chỉ định.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 22, 2007
 * @see InClass
 */
public class InClassImpl extends ObjectAnnotatingImpl implements InClass {
	/**
	 * Constructor
	 * 
	 * @param constant
	 *            {@link ObjectBindingConstant} chỉ định tương ứng.
	 */
	public InClassImpl(ObjectBindingConstant constant) {

		this.constant = constant;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.InClass#in(java.lang
	 * .Class)
	 */
	@Override
	public InClass in(Class<?> clazz) {

		this.constant.setInClass(clazz);
		setDeclaringClassIdentification(clazz);
		return this;
	}

	/**
	 * @param clazz
	 */
	public void setDeclaringClassIdentification(Class<?> clazz) {

		Map<Types, List<Pair<Identification<?>, Object>>> annotatedValueList = this.constant
				.getAnnotatedValueList();
		for (List<Pair<Identification<?>, Object>> arrLst : annotatedValueList
				.values()) {
			for (Pair<Identification<?>, Object> pair : arrLst) {
				if (ReflectUtils.isCast(MemberIdentification.class, pair
						.getKeyPair())) {
					((MemberIdentification<?>) pair.getKeyPair())
							.setDeclaringClass(clazz);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.InClass#id(java.lang
	 * .String)
	 */
	@Override
	public ScopeObjectBinding id(String id) {

		this.constant.setID(id);
		return new ScopeObjectBinding(constant);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.InClass#lazy_init(
	 * boolean)
	 */
	@Override
	public InClass lazyInit(boolean lazyInit) {

		this.constant.setLazyInit(lazyInit);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.exxlabs.jgentle.configure.injecting.objectmeta.ObjectAnnotatingImpl
	 * #getConstant()
	 */
	@Override
	public ObjectBindingConstant getConstant() {

		return constant;
	}
}
