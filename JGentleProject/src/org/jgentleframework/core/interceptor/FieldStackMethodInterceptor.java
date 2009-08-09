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
package org.jgentleframework.core.interceptor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.FieldAccess;
import org.aopalliance.intercept.FieldInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jgentleframework.core.intercept.InterceptionException;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.Utils;

/**
 * The Class FieldStackMethodInterceptor.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Dec 16, 2008
 */
class FieldStackMethodInterceptor implements MethodInterceptor {
	/** The after advices. */
	List<FieldInterceptor>	fieldInterceptors	= new ArrayList<FieldInterceptor>();

	/** The field. */
	final Field				field;

	/**
	 * Instantiates a new field stack method interceptor.
	 * 
	 * @param field
	 *            the field
	 */
	public FieldStackMethodInterceptor(Field field) {

		Assertor.notNull(field, "The given field must not be null!");
		this.field = field;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
	 * .MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Method method = invocation.getMethod();
		Object proxy = invocation.getThis();
		Object result = null;
		if (Utils.isSetter(method)) {
			result = new InterceptedFieldAccess(FieldAccess.WRITE, proxy)
					.proceed();
		}
		else if (Utils.isGetter(method)) {
			result = new InterceptedFieldAccess(FieldAccess.READ, proxy)
					.proceed();
		}
		return result;
	}

	/**
	 * The Class InterceptedFieldAccess.
	 * 
	 * @author Quoc Chung - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Dec 16, 2008
	 */
	class InterceptedFieldAccess implements FieldAccess {
		/** The access type. */
		int					accessType;

		/** The proxy. */
		protected Object	proxy;

		/** The index. */
		int					index	= -1;

		/**
		 * Instantiates a new intercepted field access.
		 * 
		 * @param accessType
		 *            the access type
		 * @param proxy
		 *            the proxy
		 */
		public InterceptedFieldAccess(int accessType, Object proxy) {

			this.accessType = accessType;
			this.proxy = proxy;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.FieldAccess#getAccessType()
		 */
		@Override
		public int getAccessType() {

			return this.accessType;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.FieldAccess#getField()
		 */
		@Override
		public Field getField() {

			return field;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.FieldAccess#getValueToSet()
		 */
		@Override
		public Object getValueToSet() {

			try {
				return proceed();
			}
			catch (Throwable e) {
				throw new InterceptionException(
						"The Field Interceptor can not be proceeded !");
			}
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#getStaticPart()
		 */
		@Override
		public AccessibleObject getStaticPart() {

			return this.getField();
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#getThis()
		 */
		@Override
		public Object getThis() {

			return this.proxy;
		}

		/*
		 * (non-Javadoc)
		 * @see org.aopalliance.intercept.Joinpoint#proceed()
		 */
		@Override
		public Object proceed() throws Throwable {

			Object result = null;
			index++;
			if (index != fieldInterceptors.size()) {
				if (accessType == FieldAccess.READ)
					result = fieldInterceptors.get(index).get(this);
				else if (accessType == FieldAccess.WRITE) {
					result = fieldInterceptors.get(index).set(this);
				}
			}
			return result;
		}
	}
}
