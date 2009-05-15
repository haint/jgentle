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
package org.jgentleframework.context.aop;

import java.io.Serializable;

import org.jgentleframework.context.aop.support.Matching;

/**
 * Canonical Pointcut instance that always matches.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 14, 2008
 * @see Pointcut
 */
class TruePointcut implements PointcutOfAll, Serializable {
	/** The obj instance. */
	private static TruePointcut	objInstance			= null;
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -2186219057311385360L;

	/**
	 * Singleton.
	 * 
	 * @return the true pointcut
	 */
	public synchronized static TruePointcut singleton() {

		if (TruePointcut.objInstance == null) {
			TruePointcut.objInstance = new TruePointcut();
		}
		return TruePointcut.objInstance;
	}

	/**
	 * Enforce Singleton pattern.
	 */
	private TruePointcut() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getClassFilter()
	 */
	public ClassFilter getClassFilter() {

		return ClassFilter.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getConstructorFilter()
	 */
	@Override
	public ConstructorFilter getConstructorFilter() {

		return ConstructorFilter.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getFieldFilter()
	 */
	@Override
	public FieldFilter getFieldFilter() {

		return TrueFieldFilter.singleton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getMethodFilter()
	 */
	@Override
	public MethodFilter getMethodFilter() {

		return TrueMethodFilter.singleton();
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

		return "Pointcut.TRUE";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.Pointcut#getFilter()
	 */
	@Override
	public Filter<Matching> getFilter() {

		return new Filter<Matching>() {
			@Override
			public boolean matches(Matching matching) {

				return true;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.aop.PointcutOfParameterFilter#getParameterFilter()
	 */
	@Override
	public ParameterFilter<?> getParameterFilter() {

		return TrueParameterFilter.singleton();
	}
}
