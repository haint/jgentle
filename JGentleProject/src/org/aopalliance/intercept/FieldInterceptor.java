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
package org.aopalliance.intercept;

/**
 * Intercepts field access on a target object.
 * <p>
 * The user should implement the {@link #set(FieldAccess)} and
 * {@link #get(FieldAccess)} methods to modify the original behavior. E.g. the
 * following class implements a tracing interceptor (traces the accesses to the
 * intercepted field(s)):
 * 
 * <pre class=code>
 * class TracingInterceptor implements FieldInterceptor {
 * 	Object set(FieldAccess fa) throws Throwable {
 * 
 * 		System.out.println(&quot;field &quot; + fa.getField() + &quot; is set with value &quot;
 * 				+ fa.getValueToSet());
 * 		Object ret = fa.proceed();
 * 		System.out.println(&quot;field &quot; + fa.getField() + &quot; was set to value &quot;
 * 				+ ret);
 * 		return ret;
 * 	}
 * 
 * 	Object get(FieldAccess fa) throws Throwable {
 * 
 * 		System.out.println(&quot;field &quot; + fa.getField() + &quot; is about to be read&quot;);
 * 		Object ret = fa.proceed();
 * 		System.out.println(&quot;field &quot; + fa.getField() + &quot; was read; value is &quot;
 * 				+ ret);
 * 		return ret;
 * 	}
 * }
 * </pre>
 */
public interface FieldInterceptor extends Interceptor {
	/**
	 * Do the stuff you want to do before and after the field is getted.
	 * <p>
	 * Polite implementations would certainly like to call
	 * {@link Joinpoint#proceed()}.
	 * 
	 * @param fieldRead
	 *            the joinpoint that corresponds to the field read
	 * @return the result of the field read {@link Joinpoint#proceed()}, might
	 *         be intercepted by the interceptor.
	 * @throws Throwable
	 *             if the interceptors or the target-object throws an exception.
	 */
	Object get(FieldAccess fieldRead) throws Throwable;

	/**
	 * Do the stuff you want to do before and after the field is setted.
	 * <p>
	 * Polite implementations would certainly like to implement
	 * {@link Joinpoint#proceed()}.
	 * 
	 * @param fieldWrite
	 *            the joinpoint that corresponds to the field write
	 * @return the result of the field set {@link Joinpoint#proceed()}, might be
	 *         intercepted by the interceptor.
	 * @throws Throwable
	 *             if the interceptors or the target-object throws an exception.
	 */
	Object set(FieldAccess fieldWrite) throws Throwable;
}
