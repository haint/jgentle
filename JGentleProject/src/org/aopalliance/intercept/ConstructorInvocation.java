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

import java.lang.reflect.Constructor;

/**
 * Description of an invocation to a constructor, given to an interceptor upon
 * construtor-call.
 * <p>
 * A constructor invocation is a joinpoint and can be intercepted by a
 * constructor interceptor.
 * 
 * @see ConstructorInterceptor
 */
public interface ConstructorInvocation extends Invocation {
	/**
	 * Gets the constructor being called.
	 * <p>
	 * This method is a frienly implementation of the {@link
	 * Joinpoint#getStaticPart()} method (same result).
	 * 
	 * @return the constructor being called.
	 */
	Constructor getConstructor();
}
