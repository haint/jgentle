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
 * Project: JGentleProject
 */
package org.jgentleframework.services.objectpooling.context;

/**
 * An idle object must be activated before it can be borrowed. Activation allows
 * an object to be ready for the real world. For example, if your objects hold
 * connections to a remote server, activation may involve letting the remote
 * server know that the connection is now live. The implementation of
 * {@link #activate()} method is a useful method to ensure the idle object is
 * activated before it can be borrowed.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 13, 2009
 */
public interface Activate {
	/**
	 * This method is invoked when you want to obtain an object from the pool.
	 * Just before the pool gives an object to the application code, this method
	 * is invoked; you can also set any properties at this stage.
	 * 
	 * @param obj
	 *            the instance to be activated
	 * @throws Exception
	 *             if there is a problem activating <code>obj</code>, this
	 *             exception may be swallowed by the pool.
	 */
	void activate() throws Exception;
}
