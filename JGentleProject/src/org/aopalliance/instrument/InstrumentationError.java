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
package org.aopalliance.instrument;

/**
 * The error that is raised when an error occurs during an instrumentation.
 * 
 * @see Instrumentor
 */
public class InstrumentationError extends Error {
	/**
	 * Sets a generic error message for an instrumentation error.
	 */
	public InstrumentationError(Instrumentation instrumentation, Throwable cause) {

		super("Error while instrumenting " + instrumentation, cause);
	}
}
