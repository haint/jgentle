/**
 * 
 */
package org.samples;

import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.annotation.Inject;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.Provider;

/**
 * @author Tran Tung
 *
 */
public class TestBean {
	public static void main(String[] args) {
	       Provider provider = JGentle.buildProvider(Config.class);
	       Bean bean = (Bean) provider.getBean(Bean.class);
	       bean.obj.sayHello();
	   }
}
class Bean {
	   @Inject
	   Hello obj;
	}

	interface Hello {
	   public void sayHello();
	}

	class HelloWorld implements Hello {
	   public void sayHello() {

	      System.out.println("Hello world !");
	   }
	}

	abstract class Config implements Configurable {
	   @Override
	   public void configure() {

	      attach(Hello.class).to(HelloWorld.class);
	   }

	   
	}