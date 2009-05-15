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
 * This abstract class represents the detector in the chain.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see ExtensionPointsDetector
 * @see FirstDetector
 */
public abstract class AbstractDetector implements Detector {
	protected Provider	provider		= null;

	/**
	 * The next detector of this detector in the chain.
	 */
	Detector			detectModule	= null;

	/**
	 * The Constructor.
	 * 
	 * @param provider
	 *            the provider
	 */
	public AbstractDetector(Provider provider) {

		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#getProvider()
	 */
	public Provider getProvider() {

		return this.provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#getNextDetector
	 * ()
	 */
	public Detector getNextDetector() {

		return detectModule;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#handling(java
	 * .util.List)
	 */
	public abstract void handling(List<Map<String, Object>> OLArray);

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#setProvider
	 * (org.jgentleframework.context.injecting.Provider)
	 */
	public void setProvider(Provider provider) {

		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.Detector#setNextDetector
	 * (org.jgentleframework.context.injecting.autodetect.AbstractDetector)
	 */
	@Override
	public void setNextDetector(Detector detectModule) {

		this.detectModule = detectModule;
	}
}
