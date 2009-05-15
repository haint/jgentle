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
package org.jgentleframework.configure.objectmeta;

import org.jgentleframework.core.intercept.support.Matcher;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.Assertor;

/**
 * The Class ObjectBindingInterceptorImpl.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 14, 2008
 */
class ObjectBindingInterceptorImpl implements ObjectBindingInterceptor {
	/** The matcher. */
	Matcher<Definition>[]	matchers;
	/** The interceptor. */
	Object					interceptor;

	/**
	 * Instantiates a new object binding interceptor impl.
	 * 
	 * @param matchers
	 *            the list of matchers
	 * @param interceptor
	 *            the interceptor
	 */
	public ObjectBindingInterceptorImpl(Object interceptor,
			Matcher<Definition>... matchers) {

		if (matchers == null || (matchers != null && matchers.length == 0)) {
			throw new IllegalArgumentException("Matchers must not be null.");
		}
		Assertor
				.notNull(interceptor,
						"The given interceptor need to be registered must not be null !!");
		this.matchers = matchers.clone();
		this.interceptor = interceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor#getMatchers()
	 */
	@Override
	public Matcher<Definition>[] getMatchers() {

		return matchers != null ? matchers.clone() : matchers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor#setMatchers(org.jgentleframework.core.intercept.support.Matcher)
	 */
	@Override
	public void setMatchers(Matcher<Definition>[] matchers) {

		this.matchers = matchers != null ? matchers.clone() : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor#getInterceptor()
	 */
	@Override
	public Object getInterceptor() {

		return interceptor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.objectmeta.ObjectBindingInterceptor#setInterceptor(java.lang.Object)
	 */
	@Override
	public void setInterceptor(Object interceptor) {

		this.interceptor = interceptor;
	}
}
