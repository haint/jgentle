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
package org.jgentleframework.core.intercept.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.enums.MetadataKey;
import org.jgentleframework.context.aop.support.ClassMatching;
import org.jgentleframework.context.aop.support.FieldMatching;
import org.jgentleframework.context.aop.support.Matching;
import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.core.intercept.MatchingException;
import org.jgentleframework.core.reflection.FieldIdentification;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.core.reflection.MethodIdentification;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * The Class StaticMatcher.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 28, 2009
 */
public class StaticMatcher {
	/** The Constant log. */
	private final static Log	log	= LogFactory.getLog(StaticMatcher.class);

	/**
	 * Static method field matching.
	 * 
	 * @param identification
	 *            the identification
	 * @param clazz
	 *            the clazz
	 * @param matching
	 *            the matching
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")
	public static boolean staticMethodFieldMatching(
			Identification<?> identification, Class<?> clazz, Matching matching) {

		boolean result = false;
		if (!ReflectUtils.isCast(clazz, identification)) {
			throw new MatchingException(
					"The identification can not be cast to [" + clazz + "]");
		}
		Definition def = (Definition) matching.getMetadata(
				MetadataKey.DEFINITION).getValue();
		/*
		 * Xử lý kiểm tra identification trên method identification
		 */
		if (MethodIdentification.class == clazz) {
			MethodIdentification mi = (MethodIdentification) identification;
			if (mi.getDeclaringClass() == null)
				mi.setDeclaringClass(def.getOwnerClass());
			for (Method method : mi.getMember()) {
				if (ReflectUtils.isCast(MethodConstructorMatching.class,
						matching)) {
					if (method.equals(((MethodConstructorMatching) matching)
							.getElement())) {
						result = true;
						break;
					}
				}
				else if (ReflectUtils.isCast(ClassMatching.class, matching)) {
					if (method.equals(matching.getMetadata(MetadataKey.METHOD)
							.getValue())) {
						result = true;
						break;
					}
				}
				else {
					if (log.isErrorEnabled())
						log.error("The 'matching' can not be cast to ["
								+ MethodConstructorMatching.class + "]",
								new MatchingException());
				}
			}
		}
		/*
		 * Xử lý kiểm tra identification trên field identification
		 */
		else if (FieldIdentification.class == clazz) {
			FieldIdentification mi = (FieldIdentification) identification;
			if (mi.getDeclaringClass() == null)
				mi.setDeclaringClass(def.getOwnerClass());
			for (Field field : mi.getMember()) {
				if (ReflectUtils.isCast(FieldMatching.class, matching)) {
					if (field.equals(((FieldMatching) matching).getField())) {
						result = true;
						break;
					}
				}
				else if (ReflectUtils.isCast(ClassMatching.class, matching)) {
					if (field.equals(matching.getMetadata(MetadataKey.FIELD)
							.getValue())) {
						result = true;
						break;
					}
				}
				else {
					if (log.isErrorEnabled())
						log.error("The 'matching' can not be cast to ["
								+ FieldMatching.class + "]",
								new MatchingException());
				}
			}
		}
		return result;
	}

	/**
	 * Static method field matching.
	 * 
	 * @param identification
	 *            the identification
	 * @param matching
	 *            the matching
	 * @return true, if successful
	 */
	public static boolean staticMethodFieldMatching(
			Identification<?> identification, Matching matching) {

		boolean result = false;
		if (ReflectUtils.isCast(MethodIdentification.class, identification)) {
			result = StaticMatcher.staticMethodFieldMatching(identification,
					MethodIdentification.class, matching);
		}
		else if (ReflectUtils.isCast(FieldIdentification.class, identification)) {
			result = StaticMatcher.staticMethodFieldMatching(identification,
					FieldIdentification.class, matching);
		}
		return result;
	}
}
