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
package org.jgentleframework.services.objectpooling.support;

import org.jgentleframework.core.intercept.support.InterceptConditioner;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.services.objectpooling.annotation.Pooling;

/**
 * The Class ObjectPoolCondition.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 22, 2009
 */
public class ObjectPoolCondition implements InterceptConditioner {
	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.intercept.support.InterceptConditioner#
	 * isValidDefinition
	 * (org.jgentleframework.core.reflection.metadata.Definition)
	 */
	@Override
	public boolean isValidDefinition(Definition definition) {

		if (definition.isAnnotationPresent(Pooling.class)) {
			Pooling pooling = definition.getAnnotation(Pooling.class);
			if (pooling.enable() == true && pooling.JustInTime() == true) {
				return true;
			}
		}
		return false;
	}
}
