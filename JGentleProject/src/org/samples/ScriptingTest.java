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

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.Location;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.integration.scripting.ScriptingInstantiationInterceptor;
import org.jgentleframework.integration.scripting.annotation.ScriptingInject;
import org.jgentleframework.integration.scripting.enums.ScriptingType;

/**
 * The Class ScriptingTest.
 * 
 * @author Administrator
 */
public class ScriptingTest {
	public static void main(String[] args) {

		Provider provider = JGentle.buildServiceProvider(ConfigSrcipting.class);
		// ScriptingInstantiationInterceptor a2 =
		// (ScriptingInstantiationInterceptor)
		// provider.getBean(ScriptingInstantiationInterceptor.class);
		ScriptTest a = (ScriptTest) provider.getBean(ScriptTest.class);
		System.out.println(a.ia.doSomeThing());
		System.out.println(a.ia.hashCode());
		System.out.println(a.ib.doSomeThing());
		System.out.println(a.ib.hashCode());
	}
}

abstract class ConfigSrcipting implements Configurable {
	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.Configurable#configure()
	 */
	@Override
	public void configure() {

		// bind().in(ScriptingInstantiationInterceptor.class).lazyInit(false);
		intercept(refMapping(ScriptingInstantiationInterceptor.class),
				annotatedWith(Location.TYPE, ScriptingInject.class));
		attach(IA.class).named("ia").to(IA.class);
		// intercept(refMapping(ScriptingInstantiationInterceptor.class),
		// annotatedWith(Location.TYPE, ScriptingInject.class));
	}
}

class ScriptTest {
	@Inject("ia")
	public IA	ia;

	@Inject
	public IB	ib;
}

@ScriptingInject(lang = ScriptingType.JAVASCRIPT, scriptFile = "/org/samples/test.js", pathType = PathType.CLASSPATH)
interface IB {
	public String doSomeThing();
}

@ScriptingInject(lang = ScriptingType.JAVASCRIPT, scriptFile = "/org/samples/test.js", pathType = PathType.CLASSPATH)
interface IA {
	public String doSomeThing();
}
