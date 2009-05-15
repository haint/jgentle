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
package org.jgentleframework.context.injecting.autodetect;

import java.util.List;
import java.util.Map;

import org.jgentleframework.context.injecting.Provider;

/**
 * The Interface Detector.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 1, 2008
 */
public interface Detector {
	/**
	 * Returns the current {@link Provider}.
	 * 
	 * @return the provider
	 */
	public Provider getProvider();

	/**
	 * Returns the next detector of this detector.
	 * 
	 * @return the next detector
	 */
	public Detector getNextDetector();

	/**
	 * This callback will automatically invoked in order to detect the specify
	 * point to execute something before the {@link Provider} is returned.
	 * 
	 * @param OLArray
	 *            the oL array
	 */
	public void handling(List<Map<String, Object>> OLArray);

	/**
	 * Sets the provider.
	 * 
	 * @param provider
	 *            the provider
	 */
	public void setProvider(Provider provider);

	/**
	 * Sets next <code>detector</code> in the chain to current detector.
	 * 
	 * @param detectModule
	 *            đối tượng <code>detector</code> cần thiết lập.
	 */
	public void setNextDetector(Detector detectModule);
}