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

import java.io.*;

import org.jgentleframework.utils.network.sockets.Recorder;

/**
 * The Class LoggingOutputStream.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class LoggingOutputStream extends FilterOutputStream {
	/** The _recorder. */
	private Recorder	_recorder;

	/**
	 * Instantiates a new logging output stream.
	 * 
	 * @param outputStream
	 *            the output stream
	 * @param fileName
	 *            the file name
	 */
	public LoggingOutputStream(OutputStream outputStream, String fileName) {

		this(outputStream, new Recorder(fileName));
	}

	/**
	 * Instantiates a new logging output stream.
	 * 
	 * @param outputStream
	 *            the output stream
	 * @param recorder
	 *            the recorder
	 */
	public LoggingOutputStream(OutputStream outputStream, Recorder recorder) {

		super(outputStream);
		_recorder = recorder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterOutputStream#write(int)
	 */
	public void write(int b) throws IOException {

		_recorder.incrementCounter(1);
		super.write(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterOutputStream#write(byte[])
	 */
	public void write(byte[] b) throws IOException {

		super.write(b);
		_recorder.incrementCounter(b.length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilterOutputStream#write(byte[], int, int)
	 */
	public void write(byte[] b, int off, int len) throws IOException {

		super.write(b, off, len);
		_recorder.incrementCounter(len);
	}
}
