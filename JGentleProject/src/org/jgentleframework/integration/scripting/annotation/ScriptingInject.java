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
package org.jgentleframework.integration.scripting.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.integration.scripting.enums.ScriptingType;

/**
 * This annotation provides configuration information for Scripting services.
 * 
 * @author gnut
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScriptingInject {
	/**
	 * The specified scripting file
	 */
	String scriptFile();

	/**
	 * The specified path type of {@link #scriptFile()} attribute.
	 * <p>
	 * Default value is {@link PathType#CLASSPATH}
	 */
	PathType pathType() default PathType.CLASSPATH;

	/**
	 * The scripting language
	 * 
	 * @see ScriptingType
	 */
	ScriptingType lang();
}
