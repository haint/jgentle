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
package org.jgentleframework.integration.remoting.rmi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.rmi.registry.Registry;

import org.jgentleframework.integration.remoting.enums.SSLCipherSuites;
import org.jgentleframework.integration.remoting.rmi.support.RmiExecutor;
import org.jgentleframework.integration.remoting.rmi.support.RmiExporter;

/**
 * This annotation provides all information about specified
 * <code>RMI service exporter</code> in order to export RMI service.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 22, 2008
 * @see RmiBinding
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RmiExporting {
	/**
	 * In case of <b>true</b>, the current container will automate create
	 * <code>registry service</code> basing on the specified
	 * <code>registry port</code> if it can not find corresponding
	 * <code>registry service</code>
	 * <p>
	 * Default value is <b>true</b>.
	 */
	boolean autoCreateRegistry() default true;

	/**
	 * In case of <b>true</b>, the <code>RMI service</code> will be automate
	 * exported when the configured data loading. Otherwise, if <b>false</b>,
	 * you must use {@link RmiExecutor} in order to export
	 * <code>RMI service</code>.
	 * <P>
	 * Default value is <b>true</b>.
	 */
	boolean autorun() default true;

	/**
	 * Specifies information about corresponding {@link RmiExporter}
	 */
	RmiExporterConfig rmiExporter() default @RmiExporterConfig;

	/**
	 * Compressing support. If specify <b>true</b>, the data is transfered
	 * between client and server will be automate compressed .
	 * <p>
	 * Default value is <b>false</b>
	 */
	boolean compressing() default false;

	/**
	 * Registry host. Default value is localhost.
	 */
	String registryHost() default "";

	/**
	 * Registry port. Default value is default port <code>1099</code>.
	 */
	int registryPort() default Registry.REGISTRY_PORT;

	/**
	 * Replace existing binding.
	 * <p>
	 * Default value is <b>true</b>.
	 */
	boolean replaceExistingBinding() default true;

	/**
	 * Service interface.
	 */
	Class<?> serviceInterface();

	/**
	 * Service name.
	 */
	String serviceName() default "default";

	/**
	 * Service port.
	 */
	int servicePort() default 0;

	/**
	 * SSL support.
	 */
	SSLCipherSuites[] SSLCipherSuites() default { SSLCipherSuites.NONE };
}
