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
package org.jgentleframework.context.beans;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * The implementation of this interface represents a
 * {@link BeanPostInstantiation} that allows for custom modification of new bean
 * instances. It can modify {@link Definition} according to bean before it is
 * instantiated, or wrapping final bean instance before returning. Moreover, it
 * can be used to execute some tasks before or after bean instantiation as well.
 * <p>
 * {@link Provider} can autodetect {@link BeanPostInstantiation} beans in their
 * bean definitions and apply them to any beans subsequently created.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 20, 2008
 */
public interface BeanPostInstantiation {
	/**
	 * This callback is automatically invoked by container before each bean is
	 * instantiated.
	 * 
	 * @param definition
	 *            the corresponding {@link Definition} of bean will be
	 *            instantiated.
	 * @param selector
	 *            the instance containing all necessary information need to
	 *            instantiate bean.
	 * @throws PostInstantiationException
	 *             in case of errors
	 */
	void BeforeInstantiation(Definition definition,
			CoreInstantiationSelector selector)
			throws PostInstantiationException;

	/**
	 * This callback is automatically invoked by container after each bean is
	 * instantiated.
	 * <p>
	 * The returned bean instance may be a wrapper around the original.
	 * 
	 * @param bean
	 *            the instantiated bean instance.
	 * @param definition
	 *            the corresponding {@link Definition} of bean has been
	 *            instantiated.
	 * @param selector
	 *            the instance containing all necessary information need to
	 *            instantiate bean.
	 * @return the bean instance to use, either the original or a wrapped one
	 * @throws PostInstantiationException
	 *             in case of errors
	 */
	Object AfterInstantiation(Object bean, Definition definition,
			CoreInstantiationSelector selector)
			throws PostInstantiationException;
}
