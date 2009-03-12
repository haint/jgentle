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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.rmi.registry.Registry;

/**
 * This annotation provides all information about specified
 * <code>RMI service binder</code> in order to bind to RMI service.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 17, 2008
 * @see RmiExporting
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RmiBinding {
	/**
	 * If <b>true</b>, the container will automate cache RMI stub whenever
	 * invoking. This is usually a good thing because the stub will be ensure
	 * that it's always newest. But in this case, this is clearly not desirable
	 * from a performance perspective. In order to increase more performance,
	 * you can disable this feature by set <b>false</b>.
	 * <p>
	 * Default value is <b>true</b>
	 */
	boolean cacheStub() default true;

	/**
	 * To enable lazy lookup of the service stub, set this flag to <b>false</b>.
	 * The RMI stub will then be looked up on first access, that is, when the
	 * first method invocation on the proxy comes in; it will be cached from
	 * then on. The disadvantage is that there will be no validation that the
	 * target service actually exists until the first invocation.
	 * <p>
	 * Specifies <b>true</b> will automate lookup stub on startup.
	 * <p>
	 * Default value is <b>true</b>.
	 */
	boolean lookupStubOnStartup() default true;

	/**
	 * As indicated, RMI stubs are connected to a specific endpoint, rather than
	 * just opening a connection to a given target address for each invocation.
	 * Consequently, if you restart the server that hosts the RMI endpoints, you
	 * need to re-register the stubs — and clients need to look them up again.
	 * While the re-registration of the target service usually happens
	 * automatically on restart, stubs held by clients become stale in such a
	 * scenario. Clients won't notice this unless they try to call a method on
	 * the stub again, which will fail with a connect exception.
	 * <p>
	 * To avoid this scenario, sets this flag to <b>true</b> to enforce
	 * automatic re-lookup of the stub if a call fails with a connect exception.
	 * <p>
	 * Default value is <b>false</b>.
	 */
	boolean refreshStubOnConnectFailure() default false;

	/**
	 * Registry host. Default value is localhost.
	 */
	String registryHost() default "";

	/**
	 * Registry port. Default value is default port <code>1099</code>.
	 */
	int registryPort() default Registry.REGISTRY_PORT;

	/**
	 * Service name.
	 */
	String serviceName();
}
