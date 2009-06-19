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

import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The Interface InterceptConditioner.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 19, 2009
 */
public interface InterceptConditioner {
	
	/**
	 * Checks if is valid definition.
	 * 
	 * @param definition the definition
	 * 
	 * @return <code>true</code> if the corresponding {@link Definition
	 * definition} is valid otherwise returns <code>false</code>.
	 */
	public boolean isValidDefinition(Definition definition);
}
