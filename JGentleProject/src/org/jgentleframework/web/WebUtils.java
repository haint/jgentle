/**
 * 
 */
package org.jgentleframework.web;

import javax.servlet.ServletContext;

import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.utils.Assertor;

/**
 * @author Tran Tung - mailto: <a
 *         href="mailto:myname74119@gmail.com">myname74119@gmail.com</a>
 * @date Jan 21, 2011
 */
public class WebUtils {
	public static final String WEB_CONTEXT = "JGENTLE_WEB_CONTEXT";
	public static final String WEB_PROVIDER = "JGENTLE_WEB_PROVIDER";
	public static final String WEB_INIT_PARAM = "jgentleConfigClass";

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static Provider getProvider(ServletContext context) {
		Assertor.notNull(context);
		Object object = context.getAttribute(WEB_PROVIDER);
		Provider provider = null;
		if (object instanceof Provider)
			provider = (Provider) object;
		return provider;
	}
}
