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
package org.samples;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.Location;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;


/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 27, 2008
 */
public class SimpleIntercept {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Provider provider = JGentle.buildServiceProvider(ConfigIntercept.class);
		Animal obj = (Animal) provider.getBean(Animal.class);
		obj.run();
	}
}

abstract class ConfigIntercept implements Configurable {
	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.Configurable#configure()
	 */
	@Override
	public void configure() {

		intercept(refMapping(Interceptor.class), annotatedWith(Location.METHOD,
				intercept.class));
	}
}

class Animal {
	@intercept
	public void run() {

		System.out.println("method invoked ! ...");
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface intercept {
}

class Interceptor implements MethodInterceptor {
	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		System.out.println("Begin invoking ...");
		Object result = invocation.proceed();
		System.out.println("End invoking ...");
		return result;
	}
}
