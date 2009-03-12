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

import java.lang.reflect.Field;

/**
 * This interface represents a field access in the program.
 * <p>
 * A field access is a joinpoint and can be intercepted by a field interceptor.
 * 
 * @see FieldInterceptor
 */
public interface FieldAccess extends Joinpoint {
	/** The read access type (see {@link #getAccessType()}). */
	int	READ	= 0;
	/** The write access type (see {@link #getAccessType()}). */
	int	WRITE	= 1;

	/**
	 * Gets the field being accessed.
	 * <p>
	 * This method is a frienly implementation of the {@link
	 * Joinpoint#getStaticPart()} method (same result).
	 * 
	 * @return the field being accessed.
	 */
	Field getField();

	/**
	 * Gets the value that must be set to the field.
	 * <p>
	 * This value can be intercepted and changed by a field interceptor.
	 */
	Object getValueToSet();

	/**
	 * Returns the access type.
	 * 
	 * @return FieldAccess.READ || FieldAccess.WRITE
	 */
	int getAccessType();
}
