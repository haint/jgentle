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
package org.jgentleframework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class RegularToolkit.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 23, 2007
 */
public abstract class RegularToolkit {
	/**
	 * Matches.
	 * 
	 * @param str
	 *            the str
	 * @param source
	 *            the source
	 * @return boolean
	 */
	public static boolean matches(String str, String source) {

		String strReplace = str.replace("?", ".");
		if (strReplace.startsWith("*"))
			strReplace = strReplace.substring(1);
		else
			strReplace = "\\A" + strReplace;
		if (strReplace.endsWith("*"))
			strReplace = strReplace.substring(0, strReplace.length() - 1);
		else
			strReplace = strReplace + "\\Z";
		String[] strArray = null;
		if (strReplace.indexOf("*") != -1) {
			strReplace = strReplace.replace("*", ":");
			strArray = strReplace.split(":");
		}
		else
			strArray = new String[] { strReplace };
		String regEx = "";
		if (strArray != null) {
			int lastIndex = 0;
			for (int i = 0; i < strArray.length; i++) {
				regEx = strArray[i];
				Pattern pattern = Pattern.compile(regEx);
				Matcher m = pattern.matcher(source);
				if (m.find()) {
					if (lastIndex != 0) {
						if (m.start() <= lastIndex)
							return false;
						continue;
					}
					lastIndex = m.end();
					continue;
				}
				else
					return false;
			}
			return true;
		}
		else {
			regEx = strReplace;
			Pattern pattern = Pattern.compile(regEx);
			Matcher m = pattern.matcher(source);
			return m.find();
		}
	}
}
