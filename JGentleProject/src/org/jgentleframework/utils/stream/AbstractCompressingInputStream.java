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
package org.jgentleframework.utils.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

/**
 * The Class AbstractCompressingInputStream.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 13, 2009
 */
public abstract class AbstractCompressingInputStream extends InputStream {
	/** The _actual input stream. */
	protected InputStream			actualInputStream;

	/** The _delegate. */
	protected InflaterInputStream	delegate;

	/**
	 * Instantiates a new compressing input stream.
	 * 
	 * @param actualInputStream
	 *            the actual input stream
	 */
	public AbstractCompressingInputStream(InputStream actualInputStream) {

		this.actualInputStream = actualInputStream;
	}

	/*
	 * (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void close() throws IOException {

		if (null != delegate) {
			delegate.close();
		}
		else {
			actualInputStream.close();
		}
	}
}
