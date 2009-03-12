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
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 29, 2008
 */
public class SingletonCycleTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider();
		A a = (A) provider.getBean(A.class);
		System.out.println(a == a.b.c.a);
		System.out.println(a.b.c.a);
		System.out.println(a);
	}
}

class A {
	@Inject
	public B	b;
}

class B {
	@Inject
	public C	c;
}

class C {
	@Inject
	public A	a;
}
