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

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.core.reflection.Reflect;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 21, 2009
 */
public class SimpleAnnotating {
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(ConfigAnnotating.class);
		AClass a = (AClass) provider.getBean(AClass.class);
		MYANNOTATION2 b = (MYANNOTATION2) provider.getBean(MYANNOTATION2.class);
		System.out.println(a.b);
		System.out.println(b.name());
		Definition definition = provider.getDefinitionManager().getDefinition(
				"AClassID");
		System.out.println(definition.getMemberDefinitionOfField("b")[0]
				.getAnnotation(MYANNOTATION2.class).name());
	}
}

abstract class ConfigAnnotating implements Configurable {
	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.configure.Configurable#configure()
	 */
	@Override
	public void configure() {

		attachConstant("name").to("Le Quoc Chung 1234");
		bind().annotate(Reflect.fields("*b"), refMapping(MYANNOTATION2.class))
				.in(AClass.class).id("AClassID");
	}
}

@Retention(RetentionPolicy.RUNTIME)
@interface MYANNOTATION2 {
	@Inject("name")
	String name();
}

class AClass {
	@Inject
	BClass	b;
}

class BClass {
}
