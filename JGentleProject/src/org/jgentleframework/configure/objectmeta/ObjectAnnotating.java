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

import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.core.reflection.ReflectIdentification;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.data.Pair;

/**
 * This interface represents a {@link ObjectAnnotating} which be responsible for
 * <code>annotating executing process</code> correspongding to an ID of a
 * {@link Definition} which is designated in {@link ObjectBindingConstant} .
 * Information of {@link ObjectAnnotating} shall be used conjointly with
 * configuration data of {@link ObjectBindingConstant} in order to create
 * <code>bean instance</code>, or load the {@link Definition} information.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 6, 2008 4:43:48 PM
 * @see InClass
 */
public interface ObjectAnnotating {
	/**
	 * Executes the <code>annotating action</code> in order to annotate one or
	 * more <b><i>dynamic annotation instances</i></b> at differrent target in
	 * current configuring {@link ObjectBindingConstant}. Hence, annotating data
	 * will be combined with injecting data in order to create the last
	 * {@link Definition}.
	 * 
	 * @param key
	 *            key is the member need to be annotated. Key could be field,
	 *            method or Class of current specified class in
	 *            {@link ObjectBindingConstant}. To specify key, use static
	 *            methods of {@link ReflectIdentification} class.
	 * @param values
	 *            the annotation instance list specified to annotated with key.
	 * @return {@link InClass}
	 */
	public InClass annotate(Identification<?> key, Object... values);

	/**
	 * Executes the <code>annotating action</code> in order to annotate one or
	 * more <b><i>dynamic annotation instances</i></b> at differrent member
	 * target in current configuring {@link ObjectBindingConstant}. Hence,
	 * annotating data will be combined with injecting data in order to create
	 * the last {@link Definition}.
	 * 
	 * @param key
	 *            key is the member need to be annotated. Key could be field,
	 *            method or Class of current specified class in
	 *            {@link ObjectBindingConstant}. To specify key, use static
	 *            methods of {@link ReflectIdentification} class.
	 * @param value
	 *            the annotation instance specified to annotated with key.
	 * @return {@link InClass}
	 */
	public InClass annotate(Identification<?> key, Object value);

	/**
	 * Executes the <code>annotating action</code> in order to annotate one or
	 * more <b><i>dynamic annotation instances</i></b> at differrent member
	 * target in current configuring {@link ObjectBindingConstant}. Hence,
	 * annotating data will be combined with injecting data in order to create
	 * the last {@link Definition}.
	 * <p>
	 * <b>Note:</b> This method will be performed the same way as
	 * {@link #annotate(Types, Object[][])} method with specified <code>'type' argument</code> is
	 * {@link Types#DEFAULT}
	 * 
	 * @param pairs
	 *            a list of array containing the mapping pair. One mapping pair
	 *            is an array which its length is 2 (represents key and value).
	 *            if violate, one exception will be thrown at run-time. Key of
	 *            mapping pair is the member need to be annotated. Key could be
	 *            field, method or Class of current specified class in
	 *            {@link ObjectBindingConstant}. To specify key, use static
	 *            methods of {@link ReflectIdentification} class.
	 * @return {@link InClass}
	 * @See {@link #annotate(Types, Object[][])}
	 */
	public InClass annotate(Object[]... pairs);

	/**
	 * Executes the <code>annotating action</code> in order to annotate one or
	 * more <b><i>dynamic annotation instances</i></b> at differrent member
	 * target in current configuring {@link ObjectBindingConstant}. Hence,
	 * annotating data will be combined with injecting data in order to create
	 * the last {@link Definition}.
	 * <p>
	 * 
	 * @param pairs
	 *            the list of pairs need to be executed.
	 * @return {@link InClass}
	 */
	public InClass annotate(Pair<Identification<?>, Object>... pairs);

	/**
	 * Executes the <code>annotating action</code> in order to annotate one or
	 * more <b><i>dynamic annotation instances</i></b> at differrent member
	 * target in current configuring {@link ObjectBindingConstant}. Hence,
	 * annotating data will be combined with injecting data in order to create
	 * the last {@link Definition}.
	 * <p>
	 * 
	 * @param type
	 *            the type of key of mapping pairs, only valid key corresponding
	 *            to type value is supported when method is performed. If
	 *            violate, one exception will be thrown at run-time.
	 * @param pairs
	 *            a list of array containing the mapping pair. One mapping pair
	 *            is an array which its length is 2 (represents key and value).
	 *            if violate, one exception will be thrown at run-time. Key of
	 *            mapping pair is the member need to be annotated. Key could be
	 *            field, method or Class of current specified class in
	 *            {@link ObjectBindingConstant}. To specify key, use static
	 *            methods of {@link ReflectIdentification} class.
	 *            <p>
	 *            - Note: <i> In case mapping pairs have variety types, type
	 *            argument must be {@link Types#DEFAULT}.</i>
	 * @return {@link InClass}
	 */
	public InClass annotate(Types type, Object[]... pairs);

	/**
	 * Returns the current {@link ObjectBindingConstant}.
	 * 
	 * @return {@link ObjectBindingConstant}
	 */
	public ObjectBindingConstant getConstant();
}