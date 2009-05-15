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
package org.jgentleframework.core.reflection.annohandler;

import java.lang.annotation.Annotation;

/**
 * Chỉ định một <code>processor</code> của một <code>annotation</code> cụ
 * thể. Để có thể sử dụng như một <code>annotation processor</code>, hiện
 * thực các tác vụ thực thi việc chuyển đổi <code>annotation</code> thành
 * <code>metadata</code> đi trong hệ thống, một <code>bean</code> hoặc
 * <code>class</code> cần phải <code>implements</code> các
 * <code>sub interface</code> của <code>interface</code> này.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 7, 2007
 * @see AnnotationPostProcessor
 * @see AnnotationHandler
 */
public interface AnnotationBeanProcessor<T extends Annotation> {
}
