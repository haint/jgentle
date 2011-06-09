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
package org.jgentleframework.context.beans;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.injecting.scope.ScopeImplementation;

/**
 * Interface to be implemented by object beans used within a JGentle container
 * which are themselves factories. If a bean implements this interface, it is
 * used as a factory for an object bean to expose, not directly as a bean
 * instance that will be exposed itself.
 * <p>
 * A bean that implements this interface cannot be used as a normal bean. A
 * FactoryBean is defined in a bean style, but the object exposed for bean
 * references (getObject() is always the object that it creates.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 2, 2008 3:07:34 PM
 */
public interface FactoryBean {
	/**
	 * Trả về một thực thể <code>instance</code> được quản lý bởi
	 * <code>factory</code> hiện hành. <code>Instance bean</code> được quản
	 * lý có thể được chỉ định mẫu Singleton design pattern (trả về 1 và chỉ một
	 * reference đến một bean duy nhất chỉ định). Trong trường hợp này giá trị
	 * trả về của cài đặt của phương thức {@link #isSingleton()} nên trả về
	 * <b>true</b>.
	 * <p>
	 * <i><b>Lưu ý rằng:</b></i>
	 * <p> - Theo mặc định <code>object bean</code> trả về thông qua
	 * {@link #getBean()} không nhận được sự hỗ trợ về scope vd như
	 * {@link Scope} hoặc <code>custome scope</code> ({@link ScopeImplementation})
	 * từ phía container. Việc chỉ định scope trên khai báo định nghĩa cấu hình
	 * của hiện thực {@link FactoryBean} chỉ có ý nghĩa trên chính bản thân
	 * {@link FactoryBean} đó, và không có hiệu lực trên các object bean được
	 * trả về từ FactoryBean chỉ định.
	 * <p> - Để các object bean trả về thực sự có thể nhận được sự hỗ trợ về
	 * scope, cũng như các <code>services</code> được cung cấp bởi
	 * <code>JGentle container</code> như IoC, AOP, ... FactoryBean có thể chỉ
	 * định implements {@link ProviderAware} interface để có thể triệu gọi các
	 * phương thức getBean() từ {@link Provider} container.
	 * <p> - Trong trường hợp, cài đặt của {@link FactoryBean} có chỉ định
	 * implements {@link ProviderAware} và thực hiện việc khởi tạo object bean
	 * thông qua đối tượng {@link Provider} container thì {@link #isSingleton()}
	 * nhất thiết phải trả về là <b>false</b> dù trong bất kì trường hợp nào
	 * (kể cả khi <code>bean</code> chỉ định truy vấn ra từ
	 * <code>container</code> được cấu hình là <code>Singleton scope</code>
	 * do các beans này đã được container quản lý scope từ thành phần lõi). Điều
	 * này giúp đảm bảo cho container không quản lý dư thừa các thông tin của
	 * các beans tại <code>singleton cache</code>.
	 * <p> - Cài đặt của phương thức {@link #getBean()} không được phép trả về
	 * một giá trị <b>null</b>, nếu vi phạm ngoại lệ
	 * {@link FactoryBeanProcessException} sẽ được ném ra tại thời điểm
	 * run-time.
	 * 
	 * @return trả về một {@link Object} là object bean hiện thực hoá của
	 *         {@link FactoryBean} hiện hành.
	 * @throws Exception
	 * @throws FactoryBeanProcessException
	 */
	Object getBean() throws Exception;

	/**
	 * Trong trường hợp {@link FactoryBean} hiện hành luôn trả về 1 và chỉ 1
	 * reference duy nhất đến 1 object bean chỉ định <i>(<b>Singleton</b>
	 * design pattern)</i> hoặc được quản lý bởi hệ thống
	 * <code>singleton cache</code> của riêng FactoryBean, việc chỉ định
	 * {@link #isSingleton()} trả về <b>true</b> có thể giúp container quản lý
	 * object bean trả về dựa trên hệ thống <code>singleton cache</code> của
	 * chính <code>container</code>.
	 * <p>
	 * <b><i>Lưu ý rằng:</i></b>
	 * <p> - Việc chỉ định {@link #isSingleton()} trả về <b>false</b> không có
	 * nghĩa rằng bắt buộc mỗi khi triệu gọi {@link #getBean()} luôn phải trả về
	 * một <code>object bean</code> mới, độc lập.
	 * <p> - Chỉ nên trả về cài đặt {@link #isSingleton()} trả về <b>true</b>
	 * khi và chỉ khi {@link #getBean()} luôn trả về một <code>reference</code>
	 * duy nhất.
	 * 
	 * @return trả về <b>true</b> nếu cài đặt của phương thức
	 *         {@link #getBean()} luôn trả về một và chỉ một
	 *         <code>reference</code> đến 1 bean duy nhất chỉ định.
	 */
	boolean isSingleton();
}
