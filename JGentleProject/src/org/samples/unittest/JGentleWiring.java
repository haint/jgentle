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
package org.samples.unittest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 30, 2008
 */
public class JGentleWiring extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public JGentleWiring(String testName) {

		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {

		return new TestSuite(JGentleWiring.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testSingtonLoopInner() {

		Provider provider = JGentle.buildProvider(Config.class);
		A2 a = (A2) provider.getBean(A2.class);
		assertEquals(true, a == a.getB().getC().getA());
	}

	public void testSingtonLoop1() {

		Provider provider = JGentle.buildProvider(Config.class);
		A2 a = (A2) provider.getBean(A2.class);
		assertEquals(true, a == a.getB().getC().getA());
	}

	public void testSingtonLoop2() {

		Provider provider = JGentle.buildProvider(Config2.class);
		A2 a = (A2) provider.getBean(A2.class);
		assertEquals(false, a == a.getB().getC().getA());
		assertEquals(true, a.getB() == a.getB().getC().getA().getB());
	}

	public void testFinal() {

		Provider provider = JGentle.buildProvider(Config3.class);
		A1 a = (A1) provider.getBean(A1.class);
		System.out.println(a.getB1());
		assertEquals(false, a == a.getB1().getC1().getA1());
		assertEquals(true, a.getB1() == a.getB1().getC1().getA1().getB1());
	}
}

abstract class Config implements Configurable {
	@Override
	public void configure() {

		attach(B2.class).to(B2.class).scope(Scope.SINGLETON);
		attach(A2.class).to(A2.class).scope(Scope.SINGLETON);
		attach(C2.class).to(C2.class).scope(Scope.SINGLETON);
	}
}

abstract class Config2 implements Configurable {
	@Override
	public void configure() {

		attach(B2.class).to(B2.class).scope(Scope.SINGLETON);
		attach(A2.class).to(A2.class).scope(Scope.PROTOTYPE);
		attach(C2.class).to(C2.class).scope(Scope.SINGLETON);
	}
}

abstract class Config3 implements Configurable {
	@Override
	public void configure() {

		attach(B1.class).to(B1.class).scope(Scope.SINGLETON);
		attach(A1.class).to(A1.class).scope(Scope.PROTOTYPE);
		attach(C1.class).to(C1.class).scope(Scope.SINGLETON);
	}
}

class C1 {
	@Inject(alwaysInject = true)
	A1	a1;

	public A1 getA1() {

		return a1;
	}

	public void setA1(A1 a1) {

		this.a1 = a1;
	}
}

class B1 {
	@Inject(alwaysInject = true)
	C1	c1;

	public C1 getC1() {

		return c1;
	}

	public void setC1(C1 c1) {

		this.c1 = c1;
	}
}

class A1 {
	@Inject
	B1	b1;

	public B1 getB1() {

		return b1;
	}

	public void setB1(B1 b1) {

		this.b1 = b1;
	}
}

class C2 {
	@Inject(alwaysInject = true)
	A2	a;

	public A2 getA() {

		return a;
	}

	public void setA(A2 a) {

		this.a = a;
	}
}

class B2 {
	@Inject(alwaysInject = true)
	C2	c;

	public C2 getC() {

		return c;
	}

	public void setC(C2 c) {

		this.c = c;
	}
}

class A2 {
	@Inject(alwaysInject = true)
	B2	b;

	public B2 getB() {

		return b;
	}

	public void setB(B2 b) {

		this.b = b;
	}
}
