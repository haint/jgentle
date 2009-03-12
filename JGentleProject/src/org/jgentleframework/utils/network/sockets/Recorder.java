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
package org.jgentleframework.utils.network.sockets;

import java.io.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class Recorder.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 8, 2008
 */
public class Recorder {
	/** The Constant DEFAULT_WRITE_GAP. */
	public static final int	DEFAULT_WRITE_GAP	= 1024;

	/** The _number of bytes. */
	private int				numberOfBytes;

	/** The _last recorded number of bytes. */
	private int				lastRecordedNumberOfBytes;

	/** The _recording differential. */
	private int				recordingDifferential;

	/** The _file name. */
	private String			fileName;

	/** The log. */
	private final Log		log					= LogFactory.getLog(getClass());

	/**
	 * Instantiates a new recorder.
	 * 
	 * @param fileName
	 *            the file name
	 */
	public Recorder(String fileName) {

		this(fileName, DEFAULT_WRITE_GAP);
	}

	/**
	 * Instantiates a new recorder.
	 * 
	 * @param fileName
	 *            the file name
	 * @param recordingDifferential
	 *            the recording differential
	 */
	public Recorder(String fileName, int recordingDifferential) {

		this.recordingDifferential = recordingDifferential;
		numberOfBytes = 0;
		lastRecordedNumberOfBytes = 0;
		this.fileName = fileName;
	}

	/**
	 * Increment counter.
	 * 
	 * @param numberOfbytes
	 *            the number ofbytes
	 */
	public void incrementCounter(int numberOfbytes) {

		numberOfBytes += numberOfbytes;
		if (recordingDifferential < (numberOfBytes - lastRecordedNumberOfBytes)) {
			flush();
		}
	}

	/**
	 * Flush.
	 */
	public void flush() {

		log();
		lastRecordedNumberOfBytes = numberOfBytes;
	}

	/**
	 * Log.
	 */
	private void log() {

		OutputStream outputStream = null;
		PrintWriter writer = null;
		try {
			outputStream = new FileOutputStream(fileName);
			writer = new PrintWriter(outputStream);
			writer
					.println(numberOfBytes
							+ " have been sent through the stream");
			writer.close();
			outputStream.close();
		}
		catch (Exception e) {
			if (log.isFatalEnabled()) {
				log.fatal("Log failure with exception", e);
			}
		}
	}
}
