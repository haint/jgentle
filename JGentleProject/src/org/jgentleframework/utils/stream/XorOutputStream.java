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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The Class XorOutputStream.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 19, 2009
 */
public class XorOutputStream extends FilterOutputStream {
	/*
	 * The byte used to "encrypt" each byte of data.
	 */
	/** The pattern. */
	private final byte	pattern;

	/*
	 * Constructs an output stream that uses the specified pattern to "encrypt"
	 * each byte of data.
	 */
	/**
	 * Instantiates a new xor output stream.
	 * 
	 * @param out
	 *            the out
	 * @param pattern
	 *            the pattern
	 */
	public XorOutputStream(OutputStream out, byte pattern) {

		super(out);
		this.pattern = pattern;
	}

	/*
	 * XOR's the byte being written with the pattern and writes the result.
	 */
	/*
	 * (non-Javadoc)
	 * @see java.io.FilterOutputStream#write(int)
	 */
	public void write(int b) throws IOException {

		out.write((b ^ pattern) & 0xFF);
	}
}
