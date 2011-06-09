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

import java.io.*;

import org.jgentleframework.utils.network.sockets.Recorder;

/**
 * The Class LoggingInputStream.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class LoggingInputStream extends FilterInputStream {
	/** The _recorder. */
	private Recorder	_recorder;

	/**
	 * Instantiates a new logging input stream.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @param fileName
	 *            the file name
	 */
	public LoggingInputStream(InputStream inputStream, String fileName) {

		this(inputStream, new Recorder(fileName));
	}

	/**
	 * Instantiates a new logging input stream.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @param recorder
	 *            the recorder
	 */
	public LoggingInputStream(InputStream inputStream, Recorder recorder) {

		super(inputStream);
		_recorder = recorder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterInputStream#read()
	 */
	public int read() throws IOException {

		_recorder.incrementCounter(1);
		return super.read();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterInputStream#read(byte[])
	 */
	public int read(byte[] b) throws IOException {

		int numberOfBytes = super.read(b);
		_recorder.incrementCounter(numberOfBytes);
		return numberOfBytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterInputStream#read(byte[], int, int)
	 */
	public int read(byte[] b, int off, int len) throws IOException {

		int numberOfBytes = super.read(b, off, len);
		_recorder.incrementCounter(numberOfBytes);
		return numberOfBytes;
	}
}
