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

import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;

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
	 * Thực thi tao tác diễn dịch một annotation chỉ định thành AnnoMeta.
	 * 
	 * @param annotation
	 *            đối tượng Annotation tương ứng cần diễn dịch
	 * @param annoMeta
	 *            đối tượng root_AnnoMeta của definition hiện hành sẽ chứa
	 *            AnnoMeta sắp diễn dịch.
	 * @param defManager
	 *            đối tượng Definition Manager của container hiện hành.
	 * @return trả về đối tượng AnnoMeta được diễn dịch.
	 */
	public AnnoMeta handleVisit(T annotation, AnnoMeta annoMeta,
			DefinitionManager defManager) throws Exception;
}
