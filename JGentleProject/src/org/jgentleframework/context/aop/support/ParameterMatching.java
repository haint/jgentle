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
package org.jgentleframework.context.aop.support;

/**
 * Represents a {@link ParameterMatching}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 17, 2008
 * @see Matching
 * @see ClassMatching
 */
public interface ParameterMatching<T> extends Matching, ClassMatching {
	/**
	 * Returns the candidate method or constructor
	 */
	T getElement();

	/**
	 * Returns arguments to the method or constructor (may be <b>null</b>, in
	 * which case of statically checking)
	 */
	Object[] getArgs();

	/**
	 * Returns the index position of candidate parameter of given method or
	 * constructor (returned value of
	 * {@link MethodConstructorMatching#getElement()})
	 */
	int getIndex();
}
