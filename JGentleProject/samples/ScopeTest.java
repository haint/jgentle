import org.jgentleframework.configure.Configurable;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.JGentle;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.InvalidAddingOperationException;
import org.jgentleframework.context.injecting.scope.InvalidRemovingOperationException;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;
import org.jgentleframework.context.support.Selector;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 25, 2008
 */
public abstract class ScopeTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Provider provider = JGentle.buildProvider(configuration.class);
		ScopeTest obj = (ScopeTest) provider.getBean(String.class);
		obj.dohere();
	}

	public abstract void dohere();
}

abstract class configuration implements Configurable {
	@Override
	public void configure() {

		attach(String.class).to(String.class).scope(Scope.in(ref("MYSCOPE")));
		/*
		 * attach(MyScope.class).withName("MYSCOPE").lazy_init(false).to(
		 * MyScope.class).scope(Scope.SINGLETON);
		 */
		// attachConstant("MYSCOPE").to(new MyScope());
		bind().in(MyScope.class).id("MYSCOPE");
	}
}

class MyScope implements ScopeImplementation {
	String[] array = new String[10];
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.injecting.scope.ScopeImplementation#getBean(java.lang.Class,
	 *      org.jgentleframework.core.reflection.metadata.Definition,
	 *      java.lang.String,
	 *      org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object getBean(Selector selector, String nameScope,
			ObjectBeanFactory objFactory) {

		return new ScopeTest() {
			@Override
			public void dohere() {

				System.out.println("running ....");
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.injecting.scope.ScopeImplementation#putBean(java.lang.String,
	 *      java.lang.Object,
	 *      org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object putBean(String nameScope, Object bean,
			ObjectBeanFactory objFactory)
			throws InvalidAddingOperationException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.context.injecting.scope.ScopeImplementation#remove(java.lang.String,
	 *      org.jgentleframework.context.injecting.ObjectBeanFactory)
	 */
	@Override
	public Object remove(String nameScope, ObjectBeanFactory objFactory)
			throws InvalidRemovingOperationException {

		// TODO Auto-generated method stub
		return null;
	}
}