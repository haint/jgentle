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

import org.jgentleframework.context.aop.support.ClassMatching;
import org.jgentleframework.core.intercept.support.AbstractMatcher;

/**
 * Canonical ClassFilter instance that matches all classes.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 14, 2008
 * @see ClassFilter
 * @see AbstractMatcher
 * @see ClassMatching
 */
class TrueClassFilter extends AbstractMatcher<ClassMatching> implements
		ClassFilter, Serializable {
	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= 4482720683993829803L;
	/** The INSTANCE. */
	private static TrueClassFilter	objInstance			= null;

	/**
	 * Enforce Singleton pattern.
	 */
	private TrueClassFilter() {

	}

	/**
	 * Singleton.
	 * 
	 * @return the true class filter
	 */
	public synchronized static TrueClassFilter singleton() {

		if (TrueClassFilter.objInstance == null) {
			TrueClassFilter.objInstance = new TrueClassFilter();
		}
		return TrueClassFilter.objInstance;
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
	public String toString() {

		return "ClassFilter.TRUE";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.ClassFilter#matches(org.jgentleframework.context.aop.support.ClassMatching)
	 */
	@Override
	public boolean matches(ClassMatching matching) {

		return true;
	}
}
