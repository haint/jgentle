/**
 * 
 */
package org.jgentleframework.integration.scripting.enums;

/**
 * @author Administrator
 * 
 */
public enum ScriptingType {
	JAVASCRIPT("javascript"), GROOVY("groovy"), RUBY("ruby"), BEANSHELL(
			"beanshell"), AWK("awk"), FREEMARKER("freemarker"), JACL("jacl"), JASKELL(
			"jaskell"), JELLY("jelly"), JEP("jep"), JEXL("jexl"), JUDOSCRIPT(
			"judoscript"), JUEL("juel"), OGNL("ognl"), PNUT("pnut"), PYTHON(
			"python"), SCHEME("scheme"), SLEEP("sleep"), TCL("tcl"), VELOCITY(
			"velocity"), XPATH("xpath"), XSLT("xslt");
	private String type;

	/**
	 * 
	 */
	private ScriptingType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
