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
package org.jgentleframework.services.objectpooling;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.jgentleframework.context.beans.Disposable;
import org.jgentleframework.context.beans.ProviderAware;
import org.jgentleframework.context.beans.annotation.DisposableMethod;
import org.jgentleframework.context.injecting.Provider;
import org.jgentleframework.context.support.CoreInstantiationSelector;
import org.jgentleframework.context.support.CoreInstantiationSelectorImpl;
import org.jgentleframework.core.factory.BeanCreationProcessor;
import org.jgentleframework.services.objectpooling.annotation.ActivateMethod;
import org.jgentleframework.services.objectpooling.annotation.CanBePooledMethod;
import org.jgentleframework.services.objectpooling.annotation.DeactivateMethod;
import org.jgentleframework.services.objectpooling.annotation.SystemPooling;
import org.jgentleframework.services.objectpooling.annotation.ValidateMethod;
import org.jgentleframework.services.objectpooling.context.Activate;
import org.jgentleframework.services.objectpooling.context.CanBePooled;
import org.jgentleframework.services.objectpooling.context.Deactivate;
import org.jgentleframework.services.objectpooling.context.Validate;
import org.jgentleframework.utils.DefinitionUtils;
import org.jgentleframework.utils.ReflectUtils;
import org.jgentleframework.utils.data.Pair;
import org.jgentleframework.utils.data.TimestampObjectBean;

/**
 * The Class AbstractBaseController.
 * 
 * @author Quoc Chung - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 22, 2009
 * @see AbstractBasePooling
 */
public abstract class AbstractBaseController extends AbstractBasePooling
		implements ProviderAware {
	/** The can be pooled method lst. */
	protected List<FastMethod>	canBePooledMethodLst	= null;

	/** The deactivate method lst. */
	protected List<FastMethod>	deactivateMethodLst		= null;

	/** The disposable method lst. */
	protected List<FastMethod>	disposableMethodLst		= null;

	/** The init method lst. */
	protected List<FastMethod>	activateMethodLst		= null;

	/** The num active. */
	protected int				numActive				= 0;

	/** The current {@link Provider}. */
	protected Provider			provider				= null;

	/** The validate method lst. */
	protected List<FastMethod>	validateMethodLst		= null;

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.services.objectpooling.AbstractBasePooling#initialize
	 * ()
	 */
	@Override
	public synchronized void initialize() {

		super.initialize();
		// Find activate methods
		if (this.definition
				.isAnnotationPresentAtAnyMethods(ActivateMethod.class)) {
			List<Method> methodList = this.definition
					.getMethodsAnnotatedWith(ActivateMethod.class);
			for (Method method : methodList) {
				if (!Modifier.isPublic(method.getModifiers())) {
					if (log.isErrorEnabled()) {
						log.error("The initializing method [" + method
								+ "] must be public !!",
								new UnsupportedOperationException());
					}
				}
				FastClass fclass = FastClass.create(method.getDeclaringClass());
				FastMethod fmethod = fclass.getMethod(method);
				if (this.activateMethodLst == null)
					this.activateMethodLst = new ArrayList<FastMethod>();
				this.activateMethodLst.add(fmethod);
			}
		}
		// Find canBePooled methods
		if (this.definition
				.isAnnotationPresentAtAnyMethods(CanBePooledMethod.class)) {
			List<Method> methodList = this.definition
					.getMethodsAnnotatedWith(CanBePooledMethod.class);
			for (Method method : methodList) {
				if (!Modifier.isPublic(method.getModifiers())) {
					if (log.isErrorEnabled()) {
						log.error("The CanBePooled method [" + method
								+ "] must be public !!",
								new UnsupportedOperationException());
					}
				}
				if (method.getReturnType() != Boolean.class
						&& method.getReturnType() != boolean.class) {
					if (log.isErrorEnabled()) {
						log.error("The 'return type' of CanBePooled method ["
								+ method + "] must be boolean !!",
								new UnsupportedOperationException());
					}
				}
				FastClass fclass = FastClass.create(method.getDeclaringClass());
				FastMethod fmethod = fclass.getMethod(method);
				if (this.canBePooledMethodLst == null)
					this.canBePooledMethodLst = new ArrayList<FastMethod>();
				this.canBePooledMethodLst.add(fmethod);
			}
		}
		// Find deactivate methods
		if (this.definition
				.isAnnotationPresentAtAnyMethods(DeactivateMethod.class)) {
			List<Method> methodList = this.definition
					.getMethodsAnnotatedWith(DeactivateMethod.class);
			for (Method method : methodList) {
				if (!Modifier.isPublic(method.getModifiers())) {
					if (log.isErrorEnabled()) {
						log.error("The deactivate method [" + method
								+ "] must be public !!",
								new UnsupportedOperationException());
					}
				}
				FastClass fclass = FastClass.create(method.getDeclaringClass());
				FastMethod fmethod = fclass.getMethod(method);
				if (this.deactivateMethodLst == null)
					this.deactivateMethodLst = new ArrayList<FastMethod>();
				this.deactivateMethodLst.add(fmethod);
			}
		}
		// Find disposable methods
		if (this.definition
				.isAnnotationPresentAtAnyMethods(DisposableMethod.class)) {
			List<Method> methodList = this.definition
					.getMethodsAnnotatedWith(DisposableMethod.class);
			for (Method method : methodList) {
				if (!Modifier.isPublic(method.getModifiers())) {
					if (log.isErrorEnabled()) {
						log.error("The disposable method [" + method
								+ "] must be public !!",
								new UnsupportedOperationException());
					}
				}
				FastClass fclass = FastClass.create(method.getDeclaringClass());
				FastMethod fmethod = fclass.getMethod(method);
				if (this.disposableMethodLst == null)
					this.disposableMethodLst = new ArrayList<FastMethod>();
				this.disposableMethodLst.add(fmethod);
			}
		}
		// Find validate methods
		if (this.definition
				.isAnnotationPresentAtAnyMethods(ValidateMethod.class)) {
			List<Method> methodList = this.definition
					.getMethodsAnnotatedWith(ValidateMethod.class);
			for (Method method : methodList) {
				if (!Modifier.isPublic(method.getModifiers())) {
					if (log.isErrorEnabled()) {
						log.error("The validate method [" + method
								+ "] must be public !!",
								new UnsupportedOperationException());
					}
				}
				if (method.getReturnType() != Boolean.class
						&& method.getReturnType() != boolean.class) {
					if (log.isErrorEnabled()) {
						log.error("The 'return type' of validate method ["
								+ method + "] must be boolean !!",
								new UnsupportedOperationException());
					}
				}
				FastClass fclass = FastClass.create(method.getDeclaringClass());
				FastMethod fmethod = fclass.getMethod(method);
				if (this.validateMethodLst == null)
					this.validateMethodLst = new ArrayList<FastMethod>();
				this.validateMethodLst.add(fmethod);
			}
		}
	}

	/**
	 * Activates object.
	 * 
	 * @param obj
	 *            the obj
	 * @throws Exception
	 *             the exception
	 */
	protected void activatesObject(Object obj) throws Exception {

		synchronized (obj) {
			if (obj != null) {
				if (ReflectUtils.isCast(Activate.class, obj))
					((Activate) obj).activate();
				else if (this.activateMethodLst != null) {
					for (FastMethod method : activateMethodLst) {
						method.invoke(obj, null);
					}
				}
			}
		}
	}

	/**
	 * Can be pooled.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if can be pooled
	 * @throws Exception
	 *             the throwable
	 */
	protected boolean canBePooled(Object obj) throws Exception {

		synchronized (obj) {
			try {
				if (obj != null) {
					if (this.isCanBePooled()
							&& ReflectUtils.isCast(CanBePooled.class, obj)) {
						return ((CanBePooled) obj).canBePooled();
					}
					else if (this.canBePooledMethodLst != null) {
						for (FastMethod method : canBePooledMethodLst) {
							return (Boolean) method.invoke(obj, null);
						}
					}
				}
			}
			catch (Exception e) {
				// object cannot be activated or is invalid
				destroyObject(obj);
				synchronized (this) {
					this.numActive--;
					notifyAll();
				}
			}
			// return default value of CanBePooled attribute.
			return SystemPooling.DEFAULT_CAN_BE_POOLED;
		}
	}

	/**
	 * Creates bean.
	 * 
	 * @return the object
	 * @throws Exception
	 *             the exception
	 * @throws Exception
	 *             the throwable
	 */
	protected Object createsBean() throws Exception {

		synchronized (this) {
			this.numActive++;
		}
		// create new object when needed
		boolean newlyCreated = false;
		Object result = null;
		TimestampObjectBean<Object> pair = null;
		try {
			CoreInstantiationSelector coreSelector = null;
			if (selector instanceof CoreInstantiationSelectorImpl) {
				coreSelector = (CoreInstantiationSelector) selector;
				Pair<Class<?>[], Object[]> pairCons = DefinitionUtils
						.findArgsOfDefaultConstructor(selector.getDefinition(),
								provider);
				Class<?>[] argTypes = pairCons.getKeyPair();
				Object[] args = pairCons.getValuePair();
				coreSelector.setArgTypes(argTypes);
				coreSelector.setArgs(args);
			}
			try {
				result = this.provider.getServiceHandler().getService(this,
						BeanCreationProcessor.class, selector);
			}
			catch (TooManyListenersException e) {
				if (log.isFatalEnabled()) {
					log.fatal("Could not get service !", e);
				}
			}
			if (result != null) {
				pair = new TimestampObjectBean<Object>(result);
				newlyCreated = true;
			}
		}
		catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(
						"The object bean can not be activated or created !! ",
						e);
			}
		}
		finally {
			if (!newlyCreated) {
				synchronized (this) {
					this.numActive--;
					notifyAll();
				}
			}
		}
		return pair != null ? pair.getValue() : null;
	}

	/**
	 * Deactivate object.
	 * 
	 * @param obj
	 *            the obj
	 * @throws Exception
	 *             the throwable
	 */
	protected void deactivateObject(Object obj) throws Exception {

		synchronized (obj) {
			try {
				if (obj != null) {
					if (ReflectUtils.isCast(Deactivate.class, obj)) {
						((Deactivate) obj).deactivate();
					}
					else if (this.deactivateMethodLst != null) {
						for (FastMethod method : deactivateMethodLst) {
							method.invoke(obj, null);
						}
					}
				}
			}
			catch (Exception e) {
				// object cannot be activated or is invalid
				destroyObject(obj);
				synchronized (this) {
					this.numActive--;
					notifyAll();
				}
			}
		}
	}

	/**
	 * Destroy object bean.
	 * 
	 * @param obj
	 *            the given object need to be destroyed.
	 * @throws Exception
	 *             the throwable
	 */
	protected void destroyObject(Object obj) throws Exception {

		synchronized (obj) {
			try {
				if (obj != null) {
					if (ReflectUtils.isCast(Disposable.class, obj))
						((Disposable) obj).destroy();
					else if (this.disposableMethodLst != null) {
						for (FastMethod method : disposableMethodLst) {
							method.invoke(obj, null);
						}
					}
				}
			}
			catch (Exception e) {
				obj = null;
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.Pool#getNumActive()
	 */
	@Override
	public synchronized int getNumActive() {

		return this.numActive;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jgentleframework.services.objectpooling.Pool#invalidateObject
	 * (java.lang.Object)
	 */
	@Override
	public void invalidateObject(Object obj) throws Exception {

		try {
			destroyObject(obj);
		}
		finally {
			synchronized (this) {
				this.numActive--;
				notifyAll();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.context.beans.ProviderAware#setProvider(org.
	 * jgentleframework.context.injecting.Provider)
	 */
	@Override
	public void setProvider(Provider provider) {

		this.provider = provider;
	}

	/**
	 * validates object bean.
	 * 
	 * @param obj
	 *            the given object need to be validated.
	 * @throws Exception
	 *             the exception
	 * @throws Exception
	 *             the throwable
	 */
	protected void validatesObject(Object obj) throws Exception {

		synchronized (obj) {
			try {
				if (obj != null) {
					if (this.isTestOnObtain()
							&& ReflectUtils.isCast(Validate.class, obj)) {
						if (!((Validate) obj).validate())
							throw new Exception("Validate failed !!");
					}
					else if (this.validateMethodLst != null) {
						for (FastMethod method : validateMethodLst) {
							if (!(Boolean) method.invoke(obj, null))
								throw new Exception("Validate failed !!");
						}
					}
				}
			}
			catch (Exception e) {
				destroyObject(obj);
				synchronized (this) {
					this.numActive--;
					notifyAll();
				}
			}
		}
	}
}
