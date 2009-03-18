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
package org.jgentleframework.context.aop;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.jgentleframework.context.aop.support.MethodConstructorMatching;
import org.jgentleframework.core.intercept.support.AbstractMatcher;

/**
 * Canonical {@link MethodFilter} instance that matches all {@link Method}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 16, 2008
 * @see MethodFilter
 */
public class TrueMethodFilter extends
		AbstractMatcher<MethodConstructorMatching<Method>> implements
		MethodFilter, Serializable {
	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= -429920506221381577L;
	/** The obj instance. */
	private static TrueMethodFilter	objInstance			= null;

	/**
	 * Enforce Singleton pattern.
	 */
	private TrueMethodFilter() {

	}

	/**
	 * Singleton.
	 * 
	 * @return the true constructor filter
	 */
	public synchronized static TrueMethodFilter singleton() {

		if (TrueMethodFilter.objInstance == null) {
			TrueMethodFilter.objInstance = new TrueMethodFilter();
		}
		return TrueMethodFilter.objInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.RuntimeSupportFilter#isRuntime()
	 */
	@Override
	public boolean isRuntime() {

		return false;
	}

	/**
	 * Required to support serialization. Replaces with canonical instance on
	 * deserialization, protecting Singleton pattern. Alternative to overriding
	 * <code>equals()</code>.
	 * 
	 * @return the object
	 */
	private Object readResolve() {

		return objInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "TrueMethodFilter.TRUE";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.MethodFilter#matches(org.jgentleframework.context.aop.support.MethodConstructorMatching)
	 */
	@Override
	public boolean matches(MethodConstructorMatching<Method> matching) {

		return true;
	}
}
