/**
 * 
 */
package RMI;

import org.jgentleframework.integration.remoting.annotation.Remote;
import org.jgentleframework.integration.remoting.enums.RemoteType;
import org.jgentleframework.integration.remoting.rmi.annotation.RmiBinding;

/**
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 12, 2008
 */
@Remote(type = RemoteType.RMI)
@RmiBinding(serviceName = "TestRMI", refreshStubOnConnectFailure = true, cacheStub = true)
public interface RemoteInterface {
	public String helloWorld();
}
