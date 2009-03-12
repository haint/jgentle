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
package org.jgentleframework.integration.remoting.rmi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.integration.remoting.rmi.context.RmiExporterProxyFactoryBean;

/**
 * Provides information about the RmiExporter
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 18, 2009
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RmiExporterConfig {
	/**
	 * The specified ID of RMI service exporter which is responsible for rmi
	 * service exporting. The corresponding {@link Scope scope} of RMI service
	 * exporter is default specified {@link Scope#SINGLETON}.
	 * <p>
	 * The default value will be
	 * {@link RmiExporterProxyFactoryBean#DEFAULT_RMIEXPORTER_ID}
	 */
	String exporterID() default RmiExporterProxyFactoryBean.DEFAULT_RMIEXPORTER_ID;

	/**
	 * Represents the scope of RMI Exporter.
	 * <p>
	 * Default value is {@link Scope#SINGLETON}
	 */
	Scope scope() default Scope.SINGLETON;
}
