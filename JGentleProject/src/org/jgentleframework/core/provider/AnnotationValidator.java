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
package org.jgentleframework.core.provider;

import java.lang.annotation.Annotation;

import org.jgentleframework.core.handling.DefinitionManager;

/**
 * Represents the {@link AnnotationValidator annotation validator} that
 * validates annotation information, catch any exceptions thrown by validating
 * process.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 18, 2007
 */
public interface AnnotationValidator<T extends Annotation> {
	/**
	 * Kiểm tra xác nhận thông tin của annotation đính kèm. Để chứng thực 1
	 * thông tin là không đúng, tại bất kì đâu trong hàm validate chỉ cần đơn
	 * giản là ném ra 1 exception (1 extends từ RuntimeException), khi đó hàm
	 * catchException sẽ nhận và xử lý thông tin ngoại lệ được ném ra.
	 * 
	 * @param annotation
	 *            annotation được validate
	 * @param annoList
	 *            danh sách annotation gốc chỉ định trong đối tượng gốc object.
	 * @param object
	 *            đối tượng gốc được chỉ định annotation. <br>
	 * <br>
	 *            <b>Lưu ý:</b> trong trường hợp annotation được validate lại
	 *            đính thông tin chính nó lên một parameter của một method nào
	 *            đó thì đối tượng gốc object lúc đó sẽ chính là method có chứa
	 *            parameter đó.
	 * @param clazz
	 *            đối tượng object class của Class chứa đối tượng gốc object nếu
	 *            có (object là Method, hoặc Field).Trong trường hợp đối tượng
	 *            gốc object đã là Class, tham số clazz sẽ chính là object.
	 *            Ngoài ra nếu như đối tượng gốc object lại là annotation thì
	 *            clazz chính là đối tượng gốc object class (annotation type) mà
	 *            object (annotation) được chỉ định đính kèm thông tin lên.
	 * @param definitionManager
	 *            đối tượng quản lý, cất trữ thông tin các definition hiện hành.
	 */
	void validate(T annotation, Annotation[] annoList, Object object,
			Class<?> clazz, DefinitionManager definitionManager);

	/**
	 * Bắt các exception được ném ra bởi validate
	 * 
	 * @param exception
	 *            đối tượng Entry bao gồm (annotation và exception) được ném ra
	 *            bởi validate.
	 * @param annotation
	 *            đối tượng annotation tương ứng với exception được ném ra.
	 * @return nếu trả về true, container sẽ bỏ qua các thông tin ngoại lệ được
	 *         ném ra (thông tin annotation xem như hợp lệ và vẫn được diễn dịch
	 *         bình thường),còn nếu trả về false container sẽ ném ngược lại
	 *         ngoại lệ exception được chỉ định.
	 */
	<V extends RuntimeException, U extends Annotation> boolean catchException(
			V exception, U annotation);
}
