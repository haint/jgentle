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

import org.jgentleframework.context.aop.support.ParameterMatching;
import org.jgentleframework.core.intercept.support.AbstractMatcher;

/**
 * Canonical {@link ParameterFilter} instance that matches all parameters of all
 * methods.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 13, 2008
 */
public class TrueParameterFilter extends
		AbstractMatcher<ParameterMatching<Method>> implements
		ParameterFilter<Method>, Serializable {
	/** The Constant serialVersionUID. */
	private static final long			serialVersionUID	= 295147615984769185L;
	/** The obj instance. */
	private static TrueParameterFilter	objInstance			= null;

	/**
	 * Enforce Singleton pattern.
	 */
	private TrueParameterFilter() {

	}

	/**
	 * Singleton.
	 * 
	 * @return the true constructor filter
	 */
	public synchronized static TrueParameterFilter singleton() {

		if (TrueParameterFilter.objInstance == null) {
			TrueParameterFilter.objInstance = new TrueParameterFilter();
		}
		return TrueParameterFilter.objInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.ParameterFilter#matches(org.exxlabs.jgentle.context.aop.support.ParameterMatching)
	 */
	@Override
	public boolean matches(ParameterMatching<Method> matching) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.exxlabs.jgentle.context.aop.RuntimeSupportFilter#isRuntime()
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
}
