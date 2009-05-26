/**
 * 
 */
package org.samples;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.Location;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.configure.enums.PathType;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.integration.scripting.ScriptingInstantiationInterceptor;
import org.jgentleframework.integration.scripting.annotation.ScriptingInject;
import org.jgentleframework.integration.scripting.enums.ScriptingType;

/**
 * @author Administrator
 * 
 */
public class ScriptingTest {
	public static void main(String[] args) {
		Provider provider = JGentle.buildServiceProvider(ConfigSrcipting.class);
		// ScriptingInstantiationInterceptor a2 =
		// (ScriptingInstantiationInterceptor)
		// provider.getBean(ScriptingInstantiationInterceptor.class);
		ScriptTest a = (ScriptTest) provider.getBean(ScriptTest.class);
		System.out.println(a.ia.doSomeThing());
		System.out.println(a.ia.hashCode());
		System.out.println(a.ib.doSomeThing());
		System.out.println(a.ib.toString());

	}

}

abstract class ConfigSrcipting implements Configurable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.configure.Configurable#configure()
	 */
	@Override
	public void configure() {
		bind().in(ScriptingInstantiationInterceptor.class).lazyInit(false);
		intercept(refMapping(ScriptingInstantiationInterceptor.class),
				annotatedWith(Location.TYPE, ScriptingInject.class));
		// intercept(refMapping(ScriptingInstantiationInterceptor.class),
		// annotatedWith(Location.TYPE, ScriptingInject.class));

	}

}

class ScriptTest {
	@Inject
	public IA ia;
	@Inject
	public IB ib;
	
}

@ScriptingInject(lang = ScriptingType.JAVASCRIPT, scriptFile = "/org/samples/tets.js", pathType = PathType.CLASSPATH)
interface IB {
	public String doSomeThing();
}

@ScriptingInject(lang = ScriptingType.JAVASCRIPT, scriptFile = "/org/samples/tets.js", pathType = PathType.CLASSPATH)
interface IA {
	public String doSomeThing();
}
