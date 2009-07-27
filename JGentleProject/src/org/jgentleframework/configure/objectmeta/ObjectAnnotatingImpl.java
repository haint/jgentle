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
package org.jgentleframework.configure.objectmeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.configure.AnnotatingRuntimeException;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.Pair;

/**
 * This class is an abstract class which is an implementation of
 * {@link ObjectAnnotating} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 6, 2008 4:35:40 PM
 * @see InClass
 * @see ObjectAnnotating
 */
abstract class ObjectAnnotatingImpl implements InClass, ObjectAnnotating {
	/**
	 * {@link ObjectBindingConstant} object bound to current
	 * {@link ObjectAnnotating}
	 */
	protected ObjectBindingConstant	constant	= null;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * annotate (org.jgentleframework.general.reflection.Identification,
	 * java.lang.Object)
	 */
	@Override
	public InClass annotate(Identification<?> key, Object value) {

		return annotate(new Object[] { key, value });
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * annotate (org.jgentleframework.general.reflection.Identification,
	 * java.lang.Object[])
	 */
	@Override
	public InClass annotate(Identification<?> key, Object... values) {

		Object[][] arg = new Object[values.length][2];
		for (int i = 0; i < arg.length; i++) {
			arg[i][0] = key;
			arg[i][1] = values[i];
		}
		return annotate(arg);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * annotate (java.lang.Object[][])
	 */
	@Override
	public InClass annotate(Object[]... pairs) {

		return annotate(Types.DEFAULT, pairs);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * annotate (
	 * org.jgentleframework.utils.data.Pair<org.jgentleframework.general
	 * .reflection .Identification<?>,java.lang.Object>[])
	 */
	@Override
	public InClass annotate(Pair<Identification<?>, Object>... pairs) {

		Assertor
				.notNull(
						pairs,
						"[Assertion failed] - Value of parameter 'pairs' of 'annotate' method must not be null !");
		Object[] pairsObject = new Object[pairs.length];
		for (int j = 0; j < pairs.length; j++) {
			pairsObject[j] = pairs[j].toArray();
		}
		return annotate(pairsObject);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * annotate (org.jgentleframework.configure.injecting.enums.Types,
	 * java.lang.Object[][])
	 */
	@Override
	public InClass annotate(Types type, Object[]... pairs) {

		Assertor
				.notNull(type,
						"[Assertion failed] - Value of mapping pair must not be null !");
		Assertor
				.notNull(pairs,
						"[Assertion failed] - Value of mapping pair must not be null !");
		if (type != Types.FIELD && type != Types.METHOD && type != Types.CLASS
				&& type != Types.DEFAULT) {
			throw new InOutDependencyException("Type '" + type.name()
					+ "' is not supported !");
		}
		for (Object[] pair : pairs) {
			checkingPair(type, pair);
			Map<Types, List<Pair<Identification<?>, Object>>> avList = this.constant
					.getAnnotatedValueList();
			List<Pair<Identification<?>, Object>> listPair = avList
					.containsKey(type)
					&& avList.get(type) != null ? avList.get(type)
					: new ArrayList<Pair<Identification<?>, Object>>();
			Pair<Identification<?>, Object> putPair = new Pair<Identification<?>, Object>(
					(Identification<?>) pair[0], pair[1]);
			listPair.add(putPair);
			if (!avList.containsKey(type) || avList.get(type) == null
					|| avList.get(type) != listPair)
				avList.put(type, listPair);
		}
		return this;
	}

	/**
	 * Checking the mapping pair
	 * 
	 * @param type
	 * @param pair
	 */
	private void checkingPair(Types type, Object[] pair) {

		if (pair.length != 2) {
			throw new AnnotatingRuntimeException(
					"Size of binding array is not valid !");
		}
		Assertor.notNull(pair[0], "The key of binding array must not be null.");
		Assertor.notNull(pair[1],
				"The value of binding array must not be null.");
		if (!ReflectUtils.isCast(Identification.class, pair[0])) {
			throw new AnnotatingRuntimeException(
					"The key of binding array must be '"
							+ Identification.class.getName() + "' type.");
		}
		if (type != Types.DEFAULT) {
			if (!ReflectUtils.isCast(type.getIdentification(), pair[0])) {
				throw new AnnotatingRuntimeException(
						"The key of binding array must be '"
								+ type.getIdentification().getName()
								+ "' type.");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.configure.injecting.objectmeta.ObjectAnnotating#
	 * getConstant()
	 */
	@Override
	public abstract ObjectBindingConstant getConstant();
}
