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
package org.jgentleframework.reflection.annohandler;

import java.lang.annotation.Annotation;

import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.reflection.metadata.AnnotationMetadata;

/**
 * Chỉ định một processor thực thi việc diễn dịch.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 6, 2007
 * @see AnnotationBeanProcessor
 * @see AnnotationPostProcessor
 */
public interface AnnotationHandler<T extends Annotation> extends
		AnnotationBeanProcessor<T> {
	/**
	 * Thực thi tao tác diễn dịch một annotation chỉ định thành
	 * AnnotationMetadata.
	 * 
	 * @param annotation
	 *            đối tượng Annotation tương ứng cần diễn dịch
	 * @param annotationMetadata
	 *            đối tượng root_AnnoMeta của definition hiện hành sẽ chứa
	 *            AnnotationMetadata sắp diễn dịch.
	 * @param definitionManager
	 *            đối tượng Definition Manager của container hiện hành.
	 * @return trả về đối tượng AnnotationMetadata được diễn dịch.
	 */
	public AnnotationMetadata handleVisit(T annotation,
			AnnotationMetadata annotationMetadata,
			DefinitionManager definitionManager) throws Exception;
}
