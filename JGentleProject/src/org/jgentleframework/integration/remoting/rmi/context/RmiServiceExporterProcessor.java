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
package org.jgentleframework.integration.remoting.rmi.context;

import java.lang.annotation.Annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.configure.objectmeta.Binder;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.integration.remoting.RemoteDefaultID;
import org.jgentleframework.integration.remoting.rmi.RmiExportingException;
import org.jgentleframework.integration.remoting.rmi.annotation.RmiExporting;
import org.jgentleframework.reflection.AnnotationBeanException;
import org.jgentleframework.reflection.annohandler.AnnotationPostProcessor;
import org.jgentleframework.reflection.annohandler.PointStatus;
import org.jgentleframework.reflection.metadata.AnnoMeta;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an {@link AnnotationPostProcessor} basing on
 * {@link RmiExporting} annotation. It's responsible for RMI exporter
 * configuration and instantiation.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Feb 26, 2008
 * @see RmiExporting
 * @see ProviderAware
 * @see PointStatus
 */
public class RmiServiceExporterProcessor implements
		AnnotationPostProcessor<RmiExporting>, ProviderAware, PointStatus {
	/** The provider. */
	Provider			provider	= null;

	/** The enable. */
	boolean				enable		= true;

	/** The log. */
	private final Log	log			= LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.context.bean.InjectCreatorAware#setInjectCreator(
	 * org.jgentleframework.context.injecting.Provider)
	 */
	@Override
	public synchronized void setProvider(Provider provider) {

		this.provider = provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.annohandler.AnnotationPostProcessor
	 * #after(java.lang.annotation.Annotation,
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta,
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta,
	 * java.lang.annotation.Annotation[], java.lang.Object)
	 */
	@Override
	public void after(RmiExporting anno, AnnoMeta parents, AnnoMeta annoMeta,
			Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException {

		/**
		 * Thực thi xử lý khởi tạo <code>RMI Service</code>, đồng bộ hóa
		 * <code>RmiServiceExporterProcessor</code> hiện hành đồng thời tạm thời
		 * ngưng kích hoạt (disable) chính nó trong lúc thực thi khởi tạo
		 * <code>service</code>.
		 */
		synchronized (this) {
			this.enable = false;
			Class<?> objectClass = null;
			if (!ReflectUtils.isClass(objConfig)) {
				throw new RmiExportingException(
						"Object annotated with @RmiExporting is not a object class.");
			}
			else {
				objectClass = (Class<?>) objConfig;
			}
			Binder binder = new Binder(this.provider);
			binder.bind("serviceClass").to(objectClass).lazyInit(false).in(
					RmiExporterProxyFactoryBean.class).id(
					RemoteDefaultID.DEFAULT_RMI_EXPORTER_PROXY_FACTORY).scope(
					Scope.SINGLETON);
			binder.flush();
			provider.getBean(RemoteDefaultID.DEFAULT_RMI_EXPORTER_PROXY_FACTORY);
			this.enable = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.annohandler.AnnotationPostProcessor
	 * #before(java.lang.annotation.Annotation,
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta,
	 * java.lang.annotation.Annotation[], java.lang.Object)
	 */
	@Override
	public void before(RmiExporting anno, AnnoMeta parents,
			Annotation[] listAnno, Object objConfig)
			throws AnnotationBeanException {

		return;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.annohandler.AnnotationPostProcessor
	 * #catchException(java.lang.Exception, java.lang.annotation.Annotation,
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta,
	 * org.jgentleframework.core.reflection.metadata.AnnoMeta,
	 * java.lang.annotation.Annotation[], java.lang.Object)
	 */
	@Override
	public void catchException(Exception ex, RmiExporting anno,
			AnnoMeta parents, AnnoMeta annoMeta, Annotation[] listAnno,
			Object objConfig) throws AnnotationBeanException {

		if (ex instanceof RmiExportingException) {
			if (log.isErrorEnabled()) {
				log.error("Could not export RMI service exporter ["
						+ anno.serviceName() + "] at port ["
						+ anno.servicePort() + "]!!", ex);
			}
			throw new RmiExportingException(ex.getMessage());
		}
		else if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		return;
	}

	/**
	 * Returns the current {@link Provider} of this processor.
	 * 
	 * @return {@link Provider}
	 */
	public synchronized Provider getProvider() {

		return provider;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.annohandler.PointStatus#isEnable()
	 */
	@Override
	public synchronized boolean isEnable() {

		return this.enable;
	}
}
