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
package org.jgentleframework.core.reflection.annohandler;

import java.lang.annotation.Annotation;

import org.jgentleframework.core.reflection.AnnotationBeanException;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;

/**
 * Mô tả các hành vi sẽ được thực thi trước và sau khi <code>Annotation</code>
 * được chuyển đổi thành <code>metadata</code> đi trong hệ thống.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 6, 2007
 * @see AnnotationBeanProcessor
 * @See AnnotationHandler
 */
public interface AnnotationPostProcessor<T extends Annotation> extends
		AnnotationBeanProcessor<T> {
	/**
	 * Phương thức thực thi trước khi việc chuyển đổi dữ liệu annotation thành
	 * metadata được thực thi, hoặc được thực thi trước khi phương thức
	 * handleVisit (nếu cài đặt có implements AnnotationHandler interface) được
	 * chỉ định thực thi.
	 * 
	 * @param anno
	 *            annotation chỉ định được sử dụng để interpreter
	 * @param parents
	 *            annoMeta cha sẽ chứa annoMeta sắp được interpreter. (có thể là
	 *            root-AnnoMeta của Definition)
	 * @param listAnno
	 *            danh sách annotation gốc được chỉ định cùng với annotation
	 *            hiện hành
	 * @param objConfig
	 *            đối tượng được cấu hình annotation.
	 */
	void before(T anno, AnnoMeta parents, Annotation[] listAnno,
			Object objConfig) throws AnnotationBeanException;

	/**
	 * Phương thức thực thi sau khi việc chuyển đổi dữ liệu annotation thành
	 * metadata được thực thi, hoặc được thực thi sau khi phương thức
	 * handleVisit (nếu cài đặt có implements AnnotationHandler interface) được
	 * chỉ định thực thi.
	 * 
	 * @param anno
	 *            annotation chỉ định được sử dụng để interpreter
	 * @param parents
	 *            annoMeta cha chứa annoMeta được interpreter (có thể là
	 *            root-AnnoMeta của Definition).
	 * @param annoMeta
	 *            annoMeta ứng với annotation sau khi đã được interpreter.
	 * @param listAnno
	 *            danh sách annotation gốc được chỉ định cùng với annotation
	 *            hiện hành
	 * @param objConfig
	 *            đối tượng được chỉ định annotation.
	 */
	void after(T anno, AnnoMeta parents, AnnoMeta annoMeta,
			Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException;

	/**
	 * Hàm xử lý catch các ngoại lệ được ném ra bởi before method hoặc after
	 * method
	 * 
	 * @param ex
	 *            ngoại lệ được ném ra
	 * @param anno
	 *            annotation chỉ định được sử dụng để interpreter
	 * @param parents
	 *            annoMeta cha chứa annoMeta được interpreter (có thể là
	 *            root-AnnoMeta của Definition).
	 * @param annoMeta
	 *            annoMeta ứng với annotation sau khi đã được interpreter nếu có
	 *            (catch exception từ after method), còn nếu annoMeta là null có
	 *            nghĩa rằng đang catch exception được ném ra bởi before method.
	 * @param listAnno
	 *            danh sách annotation gốc được chỉ định cùng với annotation
	 *            hiện hành
	 * @param objConfig
	 *            đối tượng được chỉ định annotation.
	 */
	void catchException(Exception ex, T anno, AnnoMeta parents,
			AnnoMeta annoMeta, Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException;
}
