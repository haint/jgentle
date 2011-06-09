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
package org.jgentleframework.services.objectpooling.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jgentleframework.services.objectpooling.context.CanBePooled;
import org.jgentleframework.services.objectpooling.context.PoolScope;

/**
 * This annotation is responsible for method designating in order to allow
 * container to identify one method as <code>CanBePooled method</code>. A
 * <code>CanBePooled method</code> is a method that is designated to be
 * automatically invoked by JGentle container when corresponding
 * {@link PoolScope pool_scoped bean} is returned to the pool. If returned value
 * is <b>false</b>, the component is not returned to the pool.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 11, 2009
 * @see CanBePooled
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CanBePooledMethod {
}
