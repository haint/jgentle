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
 * Project: JGentleFrameworkExample
 */
package org.samples;

import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.configure.AbstractConfig;
import org.jgentleframework.configure.REF;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.aopweaving.annotation.After;
import org.jgentleframework.configure.aopweaving.annotation.Before;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.aop.advice.AfterReturning;
import org.jgentleframework.context.aop.advice.MethodBeforeAdvice;
import org.jgentleframework.context.injecting.Provider;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 25, 2008
 */
public class SimpleAOP {
	public static void main(String[] args) {

		Provider provider = JGentle.buildServiceProvider(configAop.class);
		KnightHuman knight = (KnightHuman) provider.getBean(KnightHuman.class);
		knight.showInfo();
	}
}

abstract class configAop extends AbstractConfig {
	@Override
	public void configure() {

		attach(KnightType.class).to(DarkAvenger.class);
		attachConstant("level").to(79);
		bind().in(Advice.class).id("advice");
	}
}

class Advice implements MethodBeforeAdvice, AfterReturning {
	@Override
	public void before(MethodInvocation invocation) throws Throwable {

		System.out.println("Hahaha !! This is my power point !");
		System.out.println("----------------------------------");
	}

	@Override
	public void afterReturning(Object returnValue, MethodInvocation invocation)
			throws Throwable {

		System.out.println("----------------------------------");
		System.out.println("The end  !");
	}
}

class KnightHuman {
	@Inject(invocation = true)
	KnightType	type;

	@Inject(REF.REF_CONSTANT + "level")
	int			level	= 0;

	public KnightType getType() {

		return type;
	}

	public int getLevel() {

		return level;
	}

	@After("advice")
	@Before("advice")
	public void showInfo() {

		System.out.println("Class type: " + type.getName());
		System.out.println("Level: " + level + " <---");
		System.out.println("Defence: " + type.getDef());
		System.out.println("Strength: " + type.getStr());
	}
}

class DarkAvenger implements KnightType {
	String	name	= "Dark Avenger";

	int		def		= 800;

	int		str		= 600;

	public String getName() {

		return name;
	}

	public int getDef() {

		return def;
	}

	public int getStr() {

		return str;
	}
}
