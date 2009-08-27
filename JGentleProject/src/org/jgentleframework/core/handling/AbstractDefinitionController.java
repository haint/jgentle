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
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.reflection.AbstractVisitorHandler;
import org.jgentleframework.reflection.DefinitionPostProcessor;
import org.jgentleframework.reflection.IAnnotationVisitor;
import org.jgentleframework.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.reflection.annohandler.AnnotationHandler;
import org.jgentleframework.reflection.annohandler.AnnotationPostProcessor;
import org.jgentleframework.reflection.metadata.AnnotationMetadata;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * Quản lý các phương thức điều khiển các <i>module</i> là các
 * <i>extension-point</i> trong khi thực thi xử lý annotation, bao gồm việc diễn
 * dịch <code>annotation</code> thành {@link AnnotationMetadata}, quản lý
 * {@link DefinitionPostProcessor}, {@link AnnotationBeanProcessor}, ...
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 9, 2007 12:34:12 AM
 */
public abstract class AbstractDefinitionController extends
		AbstractDefinitionExceptionCatcher implements DefinitionManager {
	/** The annotation register. */
	protected AnnotationRegister	annotationRegister	= null;

	/** The visitor handler. */
	protected IAnnotationVisitor	visitorHandler		= new AbstractVisitorHandler(
																this) {
															@Override
															protected AnnotationMetadata build(
																	Annotation element,
																	AnnotationMetadata containter,
																	DefinitionManager defManager)
																	throws Exception {

																return ReflectUtils
																		.buildAnnoMeta(
																				element,
																				containter,
																				defManager);
															}
														};

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * addAnnotationBeanProcessor
	 * (org.jgentleframework.core.reflection.annohandler
	 * .AnnotationBeanProcessor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Annotation> AnnotationBeanProcessor<?> addAnnotationBeanProcessor(
			AnnotationBeanProcessor<T> handler) throws ClassNotFoundException {

		Assertor.notNull(handler);
		Class<?> clazz = handler.getClass();
		List<Type> typeList = ReflectUtils.getAllGenericInterfaces(clazz, true);
		for (Type type : typeList) {
			if (ReflectUtils.isCast(ParameterizedType.class, type)) {
				ParameterizedType pType = (ParameterizedType) type;
				if (pType.getRawType().equals(AnnotationPostProcessor.class)
						|| pType.getRawType().equals(AnnotationHandler.class)) {
					String annoStrClass = pType.getActualTypeArguments()[0]
							.toString().split(" ")[1];
					Class<T> annoClass = (Class<T>) Class.forName(annoStrClass);
					if (annoClass == null)
						throw new InOutDependencyException(
								"Does not found valid annotation class.");
					return this.visitorHandler.addAnnotationBeanProcessor(
							annoClass, handler);
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * addAnnotationBeanProcessor(java.lang.Class,
	 * org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor)
	 */
	@Override
	public <T extends Annotation> AnnotationBeanProcessor<?> addAnnotationBeanProcessor(
			Class<T> key, AnnotationBeanProcessor<T> handler) {

		return this.visitorHandler.addAnnotationBeanProcessor(key, handler);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * addDefinitionPostProcessor(java.lang.Class)
	 */
	@Override
	public void addDefinitionPostProcessor(
			Class<? extends DefinitionPostProcessor> dpp) {

		this.visitorHandler.addDefinitionPostProcessor(dpp);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * addDefinitionPostProcessor
	 * (org.jgentleframework.core.reflection.aohreflect.DefinitionPostProcessor)
	 */
	@Override
	public void addDefinitionPostProcessor(DefinitionPostProcessor dpp) {

		this.visitorHandler.addDefinitionPostProcessor(dpp);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * DefinitionManager#getAnnotationRegister()
	 */
	@Override
	public AnnotationRegister getAnnotationRegister() {

		return annotationRegister;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * getDefinitionPostProcessor(int)
	 */
	@Override
	public DefinitionPostProcessor getDefinitionPostProcessor(int index) {

		return this.visitorHandler.getDefinitionPostProcessor(index);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * DefinitionManager#getVisitorHandler()
	 */
	@Override
	public IAnnotationVisitor getVisitorHandler() {

		return visitorHandler;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * removeAnnotationBeanProcessor(java.lang.Class)
	 */
	@Override
	public <T extends Annotation> AnnotationBeanProcessor<?> removeAnnotationBeanProcessor(
			Class<T> key) {

		return this.visitorHandler.removeAnnotationBeanProcessor(key);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * removeDefinitionPostProcessor
	 * (org.jgentleframework.core.reflection.aohreflect.DefinitionPostProcessor)
	 */
	@Override
	public boolean removeDefinitionPostProcessor(DefinitionPostProcessor dpp) {

		return this.visitorHandler.removeDefinitionPostProcessor(dpp);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * removeDefinitionPostProcessor(int)
	 */
	@Override
	public DefinitionPostProcessor removeDefinitionPostProcessor(int index) {

		return this.visitorHandler.removeDefinitionPostProcessor(index);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#
	 * replaceAnnotationBeanProcessor
	 * (org.jgentleframework.core.reflection.annohandler
	 * .AnnotationBeanProcessor,
	 * org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor)
	 */
	@Override
	public <T extends Annotation> void replaceAnnotationBeanProcessor(
			AnnotationBeanProcessor<T> oldHandler,
			AnnotationBeanProcessor<T> newHandler) {

		this.visitorHandler.replaceAnnotationBeanProcessor(oldHandler,
				newHandler);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.metadatahandling.aohhandling.defhandling.
	 * IDefinitionManager
	 * #setVisitorHandler(org.jgentleframework.core.reflection.
	 * aohreflect.AbsVisitorHandler)
	 */
	@Override
	public void setVisitorHandler(AbstractVisitorHandler visitorHandler) {

		this.visitorHandler = visitorHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor#visit
	 * (java.lang.annotation.Annotation[],
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta)
	 */
	@Override
	public void visit(Annotation[] annoArray, AnnotationMetadata rootAnnoMeta) {

		this.visitorHandler.visit(annoArray, rootAnnoMeta);
	}
}
