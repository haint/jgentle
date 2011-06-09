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
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.InvalidRemovingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.support.Selector;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 25, 2008
 */
public abstract class ScopeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(configuration.class);
		ScopeTest obj = (ScopeTest) provider.getBean(ScopeTest.class);
		obj.dohere();
	}

	public abstract void dohere();
}

abstract class configuration implements Configurable {
	@Override
	public void configure() {

		attach(String.class).to(String.class).scope(Scope.in(ref("MYSCOPE")));
		/*
		 * attach(MyScope.class).withName("MYSCOPE").lazy_init(false).to(
		 * MyScope.class).scope(Scope.SINGLETON);
		 */
		// attachConstant("MYSCOPE").to(new MyScope());
		bind().in(MyScope.class).id("MYSCOPE");
	}
}

class MyScope implements ScopeImplementation {
	String[]	array	= new String[10];

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#getBean
	 * (java.lang.Class,
	 * org.jgentleframework.core.reflection.metadata.Definition,
	 * java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object getBean(Selector selector, String nameScope,
			ObjectBeanFactory objFactory) {

		return new ScopeTest() {
			@Override
			public void dohere() {

				System.out.println("running ....");
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#putBean
	 * (java.lang.String, java.lang.Object,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object putBean(String nameScope, Object bean,
			ObjectBeanFactory objFactory)
			throws InvalidAddingOperationException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.scope.ScopeImplementation#remove
	 * (java.lang.String,
	 * org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object remove(String nameScope, ObjectBeanFactory objFactory)
			throws InvalidRemovingOperationException {

		// TODO Auto-generated method stub
		return null;
	}
}