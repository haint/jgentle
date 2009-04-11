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
package org.jgentleframework.context.injecting.scope;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.ObjectBeanFactory;
import org.jgentleframework.context.support.Selector;
import org.jgentleframework.core.InvalidOperationException;

/**
 * {@link ScopeImplementation} specifies an interface for the installation of
 * customized scope. All custom scopes are specified in use of container in the
 * JGentle necessarily need to implement this interface settings.
 * <p>
 * Note that object implements {@link ScopeImplementation} when enforcement
 * called (invoked) necessarily <b>thread-safe</b>. This needs to be strictly in
 * compliance with to ensure that the return and creation of object beans on
 * different threads of the same implementation scope are done properly. In
 * other words, invoking <code>callback methods</code> of the {@link ScopeImplementation}
 * instance needs to ensure the <b>thread-safe</b> and this is dependant on
 * scope implementation and the invoking of caller.
 * <p> *******************************************
 * <p>
 * {@link ScopeImplementation} chỉ định một interface cho các cài đặt của các
 * <b>custom scope</b> tự tạo. Các <code>custom scope</code> chỉ định muốn được
 * sử dụng bên trong lòng JGentle <i>container</i> nhất thiết cần phải thực thi
 * cài đặt <code>interface</code> này.
 * <p>
 * Lưu ý rằng đối tượng thực thi cài đặt thể hiện {@link ScopeImplementation}
 * khi được thực thi triệu gọi <code>(invoke)</code> nhất thiết phải
 * <b>thread-safe</b>. Điều này cần phải được tuân thủ chặt chẽ, để đảm bảo các
 * <code>object beans</code> được trả về hoặc được chỉ định khởi tạo trên nhiều
 * <b>thread</b> khác nhau của cùng một <code>hiện thực scope</code> được thực
 * thi đúng đắn. Hay nói cách khác, việc chủ động triệu gọi các
 * <code>callback methods</code> trên hiện thực của {@link ScopeImplementation}
 * cần đảm bảo được việc an toàn tuyến đoạn và công việc này được JGentle giao
 * lại hoàn toàn cho người thực thi hiện thực cài đặt <b>Scope</b> trên
 * {@link ScopeImplementation} <code>interface</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 10, 2008
 * @see Scope
 * @see ScopeInstance
 */
public interface ScopeImplementation extends ScopeInstance {
	/**
	 * <code>Callback method</code> này sẽ chịu trách nhiệm khởi tạo
	 * <code>object bean</code>, nếu <code>object bean</code> hiện tại chưa được
	 * khởi tạo trong <code>scope</code>. Ngược lại nếu <code>scope</code> hiện
	 * hành đã tồn tại <code>object bean</code> tương ứng với
	 * <code>nameScope</code> cung cấp, <code>scope implementation</code> cần
	 * phải thực thi cài đặt trả về <code>object bean</code> từ
	 * <code>scope</code> được hiện thực.
	 * <p>
	 * <code>Method</code> này có thể ném ra {@link InvalidOperationException}
	 * là một <code>run-time exception</code> nếu như <code>method</code> không
	 * thể thực thi.
	 * 
	 * @param selector
	 * @param scopeName
	 *            tên định danh của <code>scope</code>, chuỗi tên định danh này
	 *            sẽ được <code>container</code> phát sinh.
	 * @param objFactory
	 *            đối tượng {@link ObjectBeanFactory} được sử dụng để khởi tạo
	 *            <code>bean</code>.
	 * @return {@link Object}.
	 */
	Object getBean(Selector selector, String scopeName,
			ObjectBeanFactory objFactory);

	/**
	 * Gỡ bỏ một <code>object Bean</code> ứng với tên định danh
	 * <code>scopeName</code> cung cấp ra khỏi <b>Scope</b> hiện hành. Trong
	 * trường hợp không tìm thấy <code>object Bean</code> tương ứng, kết quả trả
	 * về sẽ là một giá trị <b>null</b>. Lưu ý rằng, hiện thực cài đặt trên
	 * <code>callback method</code> này sẽ được <code>container</code> tự động
	 * triệu gọi tại thời điểm <code>run-time</code> khi cần thiết hoặc cũng có
	 * thể được chủ động triệu gọi từ phía <b>Caller</b>.
	 * 
	 * @param scopeName
	 *            tên định danh <code>scopeName</code> của
	 *            <code>object bean</code>.
	 * @param objFactory
	 *            đối tượng {@link ObjectBeanFactory} được sử dụng để khởi tạo
	 *            <code>bean</code>.
	 * @return trả về <code>object bean</code> vừa bị <code>remove</code> nếu
	 *         có, nếu không trả về giá trị <b>null</b>.
	 * @throws InvalidRemovingOperationException
	 *             ngoại lệ này có thể được ném ra trong trường hợp không thể
	 *             thực thi việc gỡ bỏ <code>object bean</code> ra khỏi
	 *             <code>scope</code>.
	 */
	Object remove(String scopeName, ObjectBeanFactory objFactory)
			throws InvalidRemovingOperationException;

	/**
	 * Cài đặt của <code>method</code> này sẽ chỉ định thực thi việc
	 * <code>add</code> một <code>object bean</code> vào trong
	 * <code>scope</code> hiện hành. Method này sẽ được <code>container</code>
	 * tự động triệu gọi khi có một hành vi <code>outject</code> một
	 * <code>object bean</code> vào <code>scope</code>.
	 * 
	 * @param scopeName
	 *            chuỗi định danh <code>scopeName</code> của
	 *            <code>object bean</code>.
	 * @param bean
	 *            đối tượng <code>object</code> chỉ định cần <code>add</code>
	 *            vào <code>scope</code>.
	 * @param objFactory
	 *            đối tượng {@link ObjectBeanFactory} được sử dụng để khởi tạo
	 *            <code>bean</code>.
	 * @return trả về đối tượng <code>object bean</code> tương ứng với chuỗi
	 *         định danh <code>nameScope</code> nếu trong <code>scope</code>
	 *         hiện hành đã tồn tại một <code>object bean</code> tương ứng.
	 * @throws InvalidAddingOperationException
	 *             ngoại lệ này được ném ra trong trường hợp không thể thực thi
	 *             việc <code>add object bean</code> vào <code>scope</code> hiện
	 *             hành.
	 */
	Object putBean(String scopeName, Object bean, ObjectBeanFactory objFactory)
			throws InvalidAddingOperationException;
}
