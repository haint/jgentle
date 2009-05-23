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
package org.jgentleframework.utils.data;

/**
 * A simple "struct" encapsulating an object instance and a timestamp.
 * Implements Comparable, objects are sorted from old to new.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Apr 9, 2009
 */
public class TimestampObjectBean<T> implements Comparable<T> {
	/** The value. */
	T		value;

	/** The tstamp. */
	long	tstamp;

	/**
	 * Instantiates a new timestamp object bean.
	 * 
	 * @param val
	 *            the val
	 */
	public TimestampObjectBean(T val) {

		this(val, System.currentTimeMillis());
	}

	/**
	 * Instantiates a new timestamp object bean.
	 * 
	 * @param val
	 *            the val
	 * @param time
	 *            the time
	 */
	public TimestampObjectBean(T val, long time) {

		value = val;
		tstamp = time;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return value + ":" + tstamp;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(T other) {

		return compareTo((TimestampObjectBean<T>) other);
	}

	/**
	 * Compare to.
	 * 
	 * @param other
	 *            the other
	 * @return the int
	 */
	
	public int compareTo(TimestampObjectBean<T> other) {

		final long tstampdiff = this.tstamp - other.tstamp;
		if (tstampdiff == 0) {
			// make sure the natural ordering is consistent with equals
			// see java.lang.Comparable Javadocs
			return System.identityHashCode(this)
					- System.identityHashCode(other);
		}
		else {
			// handle int overflow
			return (int) Math.min(Math.max(tstampdiff, Integer.MIN_VALUE),
					Integer.MAX_VALUE);
		}
	}

	/**
	 * @return the value
	 */
	public T getValue() {

		return value;
	}

	/**
	 * @return the tstamp
	 */
	public long getTstamp() {

		return tstamp;
	}
}
