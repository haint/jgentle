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
import java.lang.reflect.Field;

import org.jgentleframework.context.aop.support.FieldMatching;
import org.jgentleframework.core.intercept.support.AbstractMatcher;

/**
 * Canonical {@link FieldFilter} instance that matches all {@link Field}.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 16, 2008
 * @see FieldFilter
 */
public class TrueFieldFilter extends AbstractMatcher<FieldMatching> implements
		FieldFilter, Serializable {
	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= 3004353432989224932L;
	/** The obj instance. */
	private static TrueFieldFilter	objInstance			= null;

	/**
	 * Enforce Singleton pattern.
	 */
	private TrueFieldFilter() {

	}

	/**
	 * Singleton.
	 * 
	 * @return the true field filter
	 */
	public synchronized static TrueFieldFilter singleton() {

		if (TrueFieldFilter.objInstance == null) {
			TrueFieldFilter.objInstance = new TrueFieldFilter();
		}
		return TrueFieldFilter.objInstance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.FieldFilter#isRuntime()
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

		return "TrueFieldFilter.TRUE";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.FieldFilter#matches(org.jgentleframework.context.aop.support.FieldMatching)
	 */
	@Override
	public boolean matches(FieldMatching matching) {

		return true;
	}
}
