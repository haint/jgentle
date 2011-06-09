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
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.reflection.metadata.Definition;


/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 6, 2009
 */
public class ABCTest {
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(ConfigZ.class);
		X x = (X) provider.getBeanBoundToDefinition("bean1");
		X x2 = (X) provider.getBeanBoundToDefinition("bean2");
		System.out.println(x.y.getHello());
		System.out.println(x2.y.getHello());
		Definition def = provider.getDefinitionManager().getDefinition("bean1");
		System.out.println(def.getMemberDefinitionOfField("y")[0]
				.getAnnotation(Inject.class).value());
	}
}

abstract class ConfigZ implements Configurable {
	@Override
	public void configure() {

		attach(YZ.class).named("ZY").to(Y.class);
		attach(YZ.class).named("ZZ").to(Z.class);
		bind("y").to(refMapping("ZY")).in(X.class).id("bean1");
		bind("y").to(refMapping("ZZ")).in(X.class).id("bean2");
		
	}
}

class X {
	public YZ	y;
}

interface YZ {
	public String getHello();
}

class Y implements YZ {
	public String	hello	= "Y Hello world";

	@Override
	public String getHello() {

		return hello;
	}
}

class Z implements YZ {
	public String	hello	= "Z Hello world";

	@Override
	public String getHello() {

		return hello;
	}
}