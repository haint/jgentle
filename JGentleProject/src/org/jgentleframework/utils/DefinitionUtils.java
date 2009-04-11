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
 * Project: JGentleProject
 */
package org.jgentleframework.utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.factory.InOutExecutor;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.data.Pair;

/**
 * Miscellaneous {@link Definition definition} utility methods.
 * <p>
 * Mainly for use within the framework.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 1, 2009
 */
public final class DefinitionUtils {
	/**
	 * Finds args of default constructor.
	 * 
	 * @param definition
	 *            the given definition
	 * @param provider
	 *            the given provider
	 */
	public static Pair<Class<?>[], Object[]> findArgsOfDefaultConstructor(
			Definition definition, Provider provider) {

		Class<?>[] argTypes = null;
		Object[] args = null;
		// find default constructor.
		if (definition.isInterpretedOfClass()) {
			HashMap<Constructor<?>, Definition> hash = definition
					.getConstructorDefList();
			Constructor<?> constructor = null;
			if (hash.size() != 0) {
				constructor = Utils.getDefaultConstructor((Class<?>) definition
						.getKey());
			}
			Definition defCons = hash.get(constructor);
			if (defCons != null) {
				argTypes = constructor.getParameterTypes();
				if (argTypes != null && argTypes.length != 0) {
					args = new Object[argTypes.length];
					if (defCons.isAnnotationPresent(Inject.class)) {
						Inject inject = defCons.getAnnotation(Inject.class);
						for (int i = 0; i < args.length; i++) {
							args[i] = InOutExecutor.getInjectedDependency(
									inject, argTypes[i], provider);
						}
					}
					if (defCons
							.isAnnotationPresentAtAnyParameters(Inject.class)) {
						Definition[] defList = defCons.getParameterDefList();
						for (int i = 0; i < defList.length; i++) {
							if (defList[i] != null
									&& defList[i]
											.isAnnotationPresent(Inject.class)) {
								Inject inject = defList[i]
										.getAnnotation(Inject.class);
								args[i] = InOutExecutor.getInjectedDependency(
										inject, argTypes[i], provider);
							}
						}
					}
				}
			}
		}
		Pair<Class<?>[], Object[]> result = null;
		if (argTypes != null && args != null)
			result = new Pair<Class<?>[], Object[]>(argTypes.clone(), args
					.clone());
		else
			result = new Pair<Class<?>[], Object[]>(null, null);
		return result;
	}
}
