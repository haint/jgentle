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
package org.jgentleframework.core.reflection.metadata;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.aopalliance.reflect.Metadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.core.IllegalPropertyException;
import org.jgentleframework.core.reflection.IAnnotationVisitor;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an implement of {@link Definition} interface.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 4, 2007
 * @see Definition
 * @see Metadata
 */
class DefinitionImpl extends DefinitionCoreImpl implements Metadata, Definition {
	/** The Constant serialVersionUID. */
	private static final long		serialVersionUID	= -4259116314930030126L;

	/** The log. */
	transient protected Log			log					= LogFactory
																.getLog(getClass());

	/** The visitor. */
	transient IAnnotationVisitor	visitor				= null;

	/**
	 * Instantiates a new definition impl.
	 * 
	 * @param key
	 *            the key
	 * @param annoList
	 *            the anno list
	 * @param visitor
	 *            the visitor
	 */
	public DefinitionImpl(Object key, Annotation[] annoList,
			IAnnotationVisitor visitor) {

		super(key);
		this.visitor = visitor;
		this.setOriginalAnnotations(annoList);
	}

	/**
	 * Instantiates a new definition impl.
	 * 
	 * @param key
	 *            the key
	 * @param visitor
	 *            the visitor
	 */
	public DefinitionImpl(Object key, IAnnotationVisitor visitor) {

		super(key);
		this.visitor = visitor;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#buildAnnoMeta
	 * ()
	 */
	@Override
	public void buildAnnoMeta() {

		Assertor.notNull(this.visitor, "Visitor must not be null !");
		accept(this.visitor);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getAllAnnotatedConstructors()
	 */
	@Override
	public Set<Constructor<?>> getAllAnnotatedConstructors() {

		if (!isAnnotationPresentAtAnyMethods()) {
			return null;
		}
		return this.constructorDefList.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getAllAnnotatedFields ()
	 */
	@Override
	public Set<Field> getAllAnnotatedFields() {

		if (!isAnnotationPresentAtAnyFields()) {
			return null;
		}
		return this.fieldDefList.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getAllAnnotatedMethods()
	 */
	@Override
	public Set<Method> getAllAnnotatedMethods() {

		if (!isAnnotationPresentAtAnyMethods()) {
			return null;
		}
		return this.methodDefList.keySet();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getAllConstructorsAnnotatedParameter()
	 */
	@Override
	public ArrayList<Constructor<?>> getAllConstructorsAnnotatedParameter() {

		ArrayList<Constructor<?>> result = null;
		if (isInterpretedOfClass()) {
			result = new ArrayList<Constructor<?>>();
			for (Entry<Constructor<?>, Definition> entry : this
					.getConstructorDefList().entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters()) {
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getAllMethodsAnnotatedParameter()
	 */
	@Override
	public ArrayList<Method> getAllMethodsAnnotatedParameter() {

		ArrayList<Method> result = null;
		if (isInterpretedOfClass()) {
			result = new ArrayList<Method>();
			for (Entry<Method, Definition> entry : this.getMethodDefList()
					.entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters()) {
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.AnnotatedElement#getAnnotation(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotation) {

		Annotation obj = null;
		for (Annotation anno : this.originalAnnotations) {
			if (anno.annotationType().equals(annotation)) {
				obj = anno;
				break;
			}
		}
		if (obj == null) {
			throw new NullPointerException("Annotation '"
					+ annotation.getName() + "' is not annotated .");
		}
		return (T) AnnotationProxy.createProxy(obj, this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.AnnotatedElement#getAnnotations()
	 */
	@Override
	public Annotation[] getAnnotations() {

		List<Annotation> result = new LinkedList<Annotation>();
		for (Annotation anno : this.getOriginalAnnotations()) {
			result.add((Annotation) AnnotationProxy.createProxy(anno, this));
		}
		return result.toArray(new Annotation[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getArgsMemberDefinitions(java.lang.reflect.Method)
	 */
	@Override
	public Definition[] getArgsMemberDefinitions(Method method) {

		Assertor.notNull(method);
		if (isInterpretedOfClass()) {
			Definition def = this.methodDefList.get(method);
			return def.getParameterDefList();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getConstructorsAnnotatedWith(java.lang.Class)
	 */
	@Override
	public ArrayList<Constructor<?>> getConstructorsAnnotatedWith(
			Class<? extends Annotation> annotationClass) {

		ArrayList<Constructor<?>> result = null;
		if (isInterpretedOfClass()) {
			for (Entry<Constructor<?>, Definition> entry : getConstructorDefList()
					.entrySet()) {
				if (entry.getValue().isAnnotationPresent(annotationClass)) {
					if (result == null)
						result = new ArrayList<Constructor<?>>();
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getConstructorsAnnotatedWith(java.lang.Class<? extends
	 * java.lang.annotation.Annotation>[])
	 */
	@Override
	public ArrayList<Constructor<?>> getConstructorsAnnotatedWith(
			Class<? extends Annotation>... annotationClasses) {

		ArrayList<Constructor<?>> result = null;
		if (!isInterpretedOfClass())
			return result;
		for (Class<? extends Annotation> clazz : annotationClasses) {
			ArrayList<Constructor<?>> res = getConstructorsAnnotatedWith(clazz);
			if (res != null) {
				if (result == null)
					result = new ArrayList<Constructor<?>>();
				result.addAll(res);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.reflect.AnnotatedElement#getDeclaredAnnotations()
	 */
	@Override
	public Annotation[] getDeclaredAnnotations() {

		return this.getAnnotations();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getFieldsAnnotatedWith(java.lang.Class)
	 */
	@Override
	public ArrayList<Field> getFieldsAnnotatedWith(
			Class<? extends Annotation> annotationClass) {

		ArrayList<Field> result = null;
		if (isInterpretedOfClass()) {
			for (Entry<Field, Definition> entry : getFieldDefList().entrySet()) {
				if (entry.getValue().isAnnotationPresent(annotationClass)) {
					if (result == null)
						result = new ArrayList<Field>();
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getFieldsAnnotatedWith(java.lang.Class<? extends
	 * java.lang.annotation.Annotation>[])
	 */
	@Override
	public ArrayList<Field> getFieldsAnnotatedWith(
			Class<? extends Annotation>... annotationClasses) {

		ArrayList<Field> result = null;
		if (!isInterpretedOfClass())
			return result;
		for (Class<? extends Annotation> clazz : annotationClasses) {
			ArrayList<Field> res = getFieldsAnnotatedWith(clazz);
			if (res != null) {
				if (result == null)
					result = new ArrayList<Field>();
				result.addAll(res);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#getMemberDefinition
	 * (java.lang.reflect.Constructor)
	 */
	@Override
	public Definition getMemberDefinition(Constructor<?> constructor) {

		Assertor.notNull(constructor);
		if (!this.isInterpretedOfClass()) {
			throw new IllegalPropertyException(
					"This definition is not object-class definition.");
		}
		return this.getConstructorDefList().get(constructor);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#getMemberDefinition
	 * (java.lang.reflect.Field)
	 */
	@Override
	public Definition getMemberDefinition(Field field) {

		Assertor.notNull(field);
		if (!this.isInterpretedOfClass()) {
			throw new IllegalPropertyException(
					"This definition is not object-class definition.");
		}
		return this.getFieldDefList().get(field);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#getMemberDefinition
	 * (java.lang.reflect.Method)
	 */
	@Override
	public Definition getMemberDefinition(Method method) {

		Assertor.notNull(method);
		if (!this.isInterpretedOfClass()) {
			throw new IllegalPropertyException(
					"This definition is not object-class definition.");
		}
		return this.getMethodDefList().get(method);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#getMemberDefinition
	 * (java.lang.Object)
	 */
	@Override
	public Definition getMemberDefinition(Object obj) {

		if (ReflectUtils.isCast(Field.class, obj)) {
			return getMemberDefinition((Field) obj);
		}
		else if (ReflectUtils.isCast(Method.class, obj)) {
			return getMemberDefinition((Method) obj);
		}
		else if (ReflectUtils.isCast(Constructor.class, obj)) {
			return getMemberDefinition((Constructor<?>) obj);
		}
		else {
			throw new IllegalPropertyException(
					"Invalid type '"
							+ obj.getClass()
							+ "' for arguments 'obj' of getDefinitionChild method! Only Field '"
							+ Field.class + "' or Method '" + Method.class
							+ "' or Constructor '" + Constructor.class
							+ "' are permitted !");
		}
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getMemberDefinitionOfField(java.lang.String)
	 */
	@Override
	public Definition[] getMemberDefinitionOfField(String fieldName) {

		Assertor.notNull(fieldName);
		if (!this.isInterpretedOfClass()) {
			throw new IllegalPropertyException(
					"This definition is not object-class definition.");
		}
		List<Definition> result = new LinkedList<Definition>();
		for (Entry<Field, Definition> entry : this.getFieldDefList().entrySet()) {
			if (entry.getKey().getName().equals(fieldName)) {
				result.add(entry.getValue());
			}
		}
		if (result.size() == 0)
			return null;
		return result.toArray(new Definition[result.size()]);
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getMemberDefinitionOfMethod(java.lang.String, java.lang.Class<?>[])
	 */
	@Override
	public Definition[] getMemberDefinitionOfMethod(String methodName,
			Class<?>[] args) {

		Assertor.notNull(methodName);
		if (!this.isInterpretedOfClass()) {
			throw new IllegalPropertyException(
					"This definition is not object-class definition.");
		}
		List<Method> methodResult = new LinkedList<Method>();
		for (Method method : this.getMethodDefList().keySet()) {
			if (method.getName().equals(methodName)) {
				Class<?>[] argsTemp = method.getParameterTypes();
				if (args != null) {
					if (args.length != argsTemp.length) {
						continue;
					}
					else {
						int matchesType = 0;
						for (int i = 0; i < argsTemp.length; i++) {
							if (args[i].equals(argsTemp[i])) {
								matchesType++;
							}
						}
						if (matchesType == argsTemp.length) {
							methodResult.add(method);
						}
						else {
							continue;
						}
					}
				}
				else {
					if (argsTemp.length == 0) {
						methodResult.add(method);
					}
				}
			}
		}
		if (methodResult.size() == 0)
			return null;
		Definition[] result = new Definition[methodResult.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = this.getMethodDefList().get(methodResult.get(i));
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getMethodsAnnotatedWith(java.lang.Class)
	 */
	@Override
	public ArrayList<Method> getMethodsAnnotatedWith(
			Class<? extends Annotation> annotationClass) {

		ArrayList<Method> result = null;
		if (isInterpretedOfClass()) {
			for (Entry<Method, Definition> entry : getMethodDefList()
					.entrySet()) {
				if (entry.getValue().isAnnotationPresent(annotationClass)) {
					if (result == null)
						result = new ArrayList<Method>();
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * getMethodsAnnotatedWith(java.lang.Class<? extends
	 * java.lang.annotation.Annotation>[])
	 */
	@Override
	public ArrayList<Method> getMethodsAnnotatedWith(
			Class<? extends Annotation>... annotationClasses) {

		ArrayList<Method> result = null;
		if (!isInterpretedOfClass())
			return result;
		for (Class<? extends Annotation> clazz : annotationClasses) {
			ArrayList<Method> res = getMethodsAnnotatedWith(clazz);
			if (res != null) {
				if (result == null)
					result = new ArrayList<Method>();
				result.addAll(res);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#getOwnerClass()
	 */
	@Override
	public Class<?> getOwnerClass() {

		if (isInterpretedOfClass()) {
			return (Class<?>) this.getKey();
		}
		else if (isInterpretedOfConstructor()) {
			Constructor<?> constructor = (Constructor<?>) this.getKey();
			return constructor.getDeclaringClass();
		}
		else if (isInterpretedOfField()) {
			Field field = (Field) this.getKey();
			return field.getDeclaringClass();
		}
		else if (isInterpretedOfMethod()) {
			Method method = (Method) this.getKey();
			return method.getDeclaringClass();
		}
		else {
			if (log.isWarnEnabled()) {
				log.warn("This definition is not interpreted from anything");
			}
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#isAnnotationPresent
	 * ()
	 */
	@Override
	public boolean isAnnotationPresent() {

		return this.originalAnnotations == null
				|| this.getOriginalAnnotations().length == 0 ? false : true;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * java.lang.reflect.AnnotatedElement#isAnnotationPresent(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {

		int i = 0;
		for (Annotation obj : this.getOriginalAnnotations()) {
			if (obj.annotationType().equals(annotation)) {
				i++;
			}
		}
		if (i >= 1) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyConstructor()
	 */
	@Override
	public boolean isAnnotationPresentAtAnyConstructors() {

		if (isInterpretedOfClass()) {
			synchronized (this.constructorDefList) {
				return this.constructorDefList.size() != 0 ? true : false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyConstructor(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresentAtAnyConstructors(
			Class<? extends Annotation> annotationClass) {

		if (isInterpretedOfClass()) {
			for (Definition def : getConstructorDefList().values()) {
				if (def.isAnnotationPresent(annotationClass)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyField()
	 */
	@Override
	public boolean isAnnotationPresentAtAnyFields() {

		if (isInterpretedOfClass()) {
			synchronized (this.fieldDefList) {
				return this.fieldDefList.size() != 0 ? true : false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyField(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresentAtAnyFields(
			Class<? extends Annotation> annotationClass) {

		if (isInterpretedOfClass()) {
			for (Definition def : getFieldDefList().values()) {
				if (def.isAnnotationPresent(annotationClass)) {
					return true;
				}
			}
		}
		else {
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyMethod()
	 */
	@Override
	public boolean isAnnotationPresentAtAnyMethods() {

		if (isInterpretedOfClass()) {
			synchronized (this.methodDefList) {
				return this.methodDefList.size() != 0 ? true : false;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyMethod(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresentAtAnyMethods(
			Class<? extends Annotation> annotationClass) {

		if (isInterpretedOfClass()) {
			for (Definition def : getMethodDefList().values()) {
				if (def.isAnnotationPresent(annotationClass)) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyParameter()
	 */
	@Override
	public boolean isAnnotationPresentAtAnyParameters() {

		if (isInterpretedOfClass()) {
			for (Entry<Method, Definition> entry : this.getMethodDefList()
					.entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters()) {
					return true;
				}
			}
			for (Entry<Constructor<?>, Definition> entry : this
					.getConstructorDefList().entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters()) {
					return true;
				}
			}
		}
		else if (isInterpretedOfMethod() || isInterpretedOfConstructor()) {
			if (this.parameterDefList == null) {
				return false;
			}
			synchronized (this.parameterDefList) {
				return this.parameterDefList.length == 0 ? false : true;
			}
		}
		else {
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyParameter(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresentAtAnyParameters(
			Class<? extends Annotation> annotationClass) {

		if (isInterpretedOfMethod() || isInterpretedOfConstructor()) {
			if (this.parameterDefList == null) {
				return false;
			}
			synchronized (this.parameterDefList) {
				if (this.parameterDefList.length == 0) {
					return false;
				}
				else {
					for (Definition def : this.parameterDefList) {
						if (def.isAnnotationPresent(annotationClass)) {
							return true;
						}
					}
					return false;
				}
			}
		}
		else if (isInterpretedOfClass()) {
			for (Entry<Method, Definition> entry : this.getMethodDefList()
					.entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters(annotationClass)) {
					return true;
				}
			}
			for (Entry<Constructor<?>, Definition> entry : this
					.getConstructorDefList().entrySet()) {
				Definition def = entry.getValue();
				if (def.isAnnotationPresentAtAnyParameters(annotationClass)) {
					return true;
				}
			}
		}
		else {
			return false;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyWhere()
	 */
	@Override
	public boolean isAnnotationPresentAtAnyWhere() {

		return isAnnotationPresent() || isAnnotationPresentAtAnyFields()
				|| isAnnotationPresentAtAnyMethods()
				|| isAnnotationPresentAtAnyConstructors()
				|| isAnnotationPresentAtAnyParameters();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.Definition#
	 * isAnnotationPresentAtAnyWhere(java.lang.Class)
	 */
	@Override
	public boolean isAnnotationPresentAtAnyWhere(
			Class<? extends Annotation> annotationClass) {

		return isAnnotationPresent(annotationClass)
				|| isAnnotationPresentAtAnyFields(annotationClass)
				|| isAnnotationPresentAtAnyMethods(annotationClass)
				|| isAnnotationPresentAtAnyParameters(annotationClass)
				|| isAnnotationPresentAtAnyConstructors(annotationClass);
	}

	/**
	 * Overrides default <code>readObject method</code>.
	 * 
	 * @param stream
	 *            the stream
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {

		stream.defaultReadObject();
		log = LogFactory.getLog(getClass());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.Definition#setValueOfAnnotation
	 * (java.lang.Class, java.lang.String, java.lang.Object)
	 */
	@Override
	public Object setValueOfAnnotation(Class<? extends Annotation> annotation,
			String valueName, Object value) {

		return ((SetValueOfAnnotation) getAnnotation(annotation))
				.setValueOfAnnotation(valueName, value).getValue();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#setVisitor
	 * (org.jgentleframework.core.reflection.aohreflect.IAnnotationVisitor)
	 */
	@Override
	public void setVisitor(IAnnotationVisitor visitor) {

		this.visitor = visitor;
	}
}
