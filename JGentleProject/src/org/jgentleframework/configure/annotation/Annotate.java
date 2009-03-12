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
package org.jgentleframework.configure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.configure.AnnotatingRuntimeException;

/**
 * Annotates members of your class (methods, fields, parameters) or Class with
 * which the container should annotate annotations. In all cases, a member can
 * be annotated regardless of its Java access specifier (private, default,
 * protected, public).
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 21, 2008
 * @see Inject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.METHOD, ElementType.FIELD,
		ElementType.CONSTRUCTOR, ElementType.PARAMETER,
		ElementType.ANNOTATION_TYPE })
public @interface Annotate {
	/**
	 * Indicates the set of annotations which are annotated with.
	 */
	String[] value();

	/**
	 * If specified value is <b>true</b>, the annotated annotation must not be
	 * <b>null</b>. If violates, the specified exception of
	 * {@link #throwsException()} will be thrown at <b>run-time</b>.
	 * <p>
	 * The default value is <b>true</b>.
	 */
	boolean required() default true;

	/**
	 * This specified exception shall be thrown if annotating process is not
	 * completed.
	 * <p>
	 * The default value is {@link AnnotatingRuntimeException} class.
	 */
	Class<? extends RuntimeException> throwsException() default AnnotatingRuntimeException.class;
}
