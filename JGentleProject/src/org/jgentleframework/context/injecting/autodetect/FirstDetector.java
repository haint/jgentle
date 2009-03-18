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
package org.jgentleframework.context.injecting.autodetect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgentleframework.context.injecting.Provider;

/**
 * This class represents a detector manager. It manages all detector in the
 * chain of its. After the {@link Provider} is created but before it is returned
 * to client, all detectors in the chain will be serial performed in order to
 * detect specified bean or execute some specified task.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 5, 2008
 * @see AbstractDetector
 */
public class FirstDetector extends AbstractDetector {
	/**
	 * The Constructor.
	 * 
	 * @param provider
	 *            the context
	 */
	public FirstDetector(Provider provider) {

		super(provider);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.injecting.autodetect.AbstractDetector#handling
	 * (java.util.ArrayList)
	 */
	@Override
	public void handling(List<Map<String, Object>> OLArray) {

		Detector detector = this.detectModule;
		while (detector != null) {
			detector.handling(OLArray);
			detector = detector.getNextDetector();
		}
	}

	/**
	 * Appends the specified detector to the chain.
	 * 
	 * @param detector
	 *            the detector to be appended.
	 * @param tail
	 *            if <b>true</b>, the specified detector will be added to the
	 *            end of chain, otherwise, if <b>false</b>, the specified
	 *            detector will be added to the begin of chain.
	 */
	public synchronized void addDetector(AbstractDetector detector, boolean tail) {

		if (this.detectModule != null) {
			if (tail == false) {
				Detector currentFirst = this.getNextDetector();
				setNextDetector(detector);
				detector.setNextDetector(currentFirst);
			}
			else {
				Detector tailDetector = this.getNextDetector();
				while (tailDetector.getNextDetector() != null) {
					tailDetector = tailDetector.getNextDetector();
				}
				tailDetector.setNextDetector(detector);
			}
		}
		else {
			setNextDetector(detector);
		}
	}

	/**
	 * Returns a list of managed detector in the chain
	 * 
	 * @return returns an {@link ArrayList} of detector if it exists, if not,
	 *         returns an empty {@link ArrayList}.
	 */
	public ArrayList<Detector> getAllDetectors() {

		ArrayList<Detector> result = new ArrayList<Detector>();
		Detector current = getNextDetector();
		while (current != null) {
			result.add(current);
			current = current.getNextDetector();
		}
		return result;
	}

	/**
	 * Returns the number of all registered detectors.
	 */
	public synchronized int count() {

		int i = 0;
		Detector current = this.getNextDetector();
		while (current != null) {
			i++;
			current = current.getNextDetector();
		}
		return i;
	}
}
