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
package org.jgentleframework.utils.stream;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class XorInputStream extends FilterInputStream {
	/*
	 * The byte being used to "decrypt" each byte of data.
	 */
	private final byte	pattern;

	/*
	 * Constructs an input stream that uses the specified pattern to "decrypt"
	 * each byte of data.
	 */
	public XorInputStream(InputStream in, byte pattern) {

		super(in);
		this.pattern = pattern;
	}

	/*
	 * Reads in a byte and xor's the byte with the pattern. Returns the byte.
	 */
	public int read() throws IOException {

		int b = in.read();
		// If not end of file or an error, truncate b to one byte
		if (b != -1)
			b = (b ^ pattern) & 0xFF;
		return b;
	}

	/*
	 * Reads up to len bytes
	 */
	public int read(byte b[], int off, int len) throws IOException {

		int numBytes = in.read(b, off, len);
		if (numBytes <= 0)
			return numBytes;
		for (int i = 0; i < numBytes; i++) {
			b[off + i] = (byte) ((b[off + i] ^ pattern) & 0xFF);
		}
		return numBytes;
	}
}
