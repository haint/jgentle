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
package org.jgentleframework.services.datalocator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.services.datalocator.data.RepositoryProcessor;

/**
 * This annotation provides configuration information for repository.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date May 5, 2007
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Repository {
	/** The Constant DEFAULT_SAVE_FILE. */
	public static final String	DEFAULT_SAVE_FILE	= "repository.rps";

	/**
	 * The name of specified file which is saved.
	 * <p>
	 * Default value is {@link Repository#DEFAULT_SAVE_FILE} ("repository.rps")
	 */
	String saveFile() default DEFAULT_SAVE_FILE;

	/**
	 * The specified path type of {@link #saveFile()}.
	 * <p>
	 * <b>Note:</b> if specify {@link PathType#CLASSPATH}, only
	 * {@link RepositoryProcessor#loadRepository() loadRepository} method can be
	 * invoked, but {@link RepositoryProcessor#saveRepository() saveRepository}
	 * method will not be able to invoke. In this case, if
	 * {@link RepositoryProcessor#saveRepository() saveRepository} is invoked by
	 * client, one exception will be thrown at runtime.
	 * <p>
	 * Default value is {@link PathType#USERHOME}
	 */
	PathType pathType() default PathType.USERHOME;
}
