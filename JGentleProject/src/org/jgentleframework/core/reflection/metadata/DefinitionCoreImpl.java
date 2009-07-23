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
package org.jgentleframework.core.reflection.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.reflect.Metadata;
import org.jgentleframework.core.reflection.IAnnoVisitable;
import org.jgentleframework.core.reflection.IAnnotationVisitor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class is an abstract class that is represented a {@link DefinitionCore}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 24, 2008
 * @see MetadataController
 * @see DefinitionCore
 * @see Definition
 */
abstract class DefinitionCoreImpl extends MetadataController implements
		IAnnoVisitable, DefinitionCore {
	
	/** The Constant serialVersionUID. */
	private static final long			serialVersionUID	= -4913719040130848081L;

	/**
	 * The {@link AnnoMeta} of current {@link Definition}. Its key is key of
	 * {@link Definition} but its value is this {@link Definition}.
	 * <p>
	 * <b>Note:</b> This {@link AnnoMeta} object may be <b>null</b> if it is a
	 * empty {@link AnnoMeta}. In case of this, this {@link Definition} is
	 * interpreted of <code>object class</code> but corresponding class is not
	 * annotated with any annotation.
	 */
	AnnoMeta							annoMeta			= null;

	/**
	 * containing {@link Definition} of {@link Constructor} objects in case this
	 * {@link Definition} is interpreted of object class.
	 */
	Map<Constructor<?>, Definition>	constructorDefList	= new HashMap<Constructor<?>, Definition>();

	/**
	 * containing {@link Definition} of {@link Field} objects in case this
	 * {@link Definition} is interpreted of object class.
	 */
	Map<Field, Definition>			fieldDefList		= new HashMap<Field, Definition>();

	/**
	 * containing {@link Definition} of {@link Method} objects in case this
	 * {@link Definition} is interpreted of object class.
	 */
	Map<Method, Definition>			methodDefList		= new HashMap<Method, Definition>();

	/**
	 * The array containing all original annotations of this {@link Definition}
	 */
	Annotation[]						originalAnnotations;

	/**
	 * This array contains all {@link Definition} of parameters of method which
	 * this {@link Definition} is interpreted of.
	 */
	Definition[]						parameterDefList	= null;

	/**
	 * Constructor
	 * 
	 * @param key
	 */
	public DefinitionCoreImpl(Object key) {

		super(key);
	}

	/**
	 * Constructor
	 * 
	 * @param key
	 * @param value
	 */
	public DefinitionCoreImpl(Object key, Object value) {

		super(key, value);
	}

	/**
	 * Executes all interpreter actions, converts annotation data to
	 * {@link Metadata} in order to be used in system.
	 */
	@Override
	public void accept(IAnnotationVisitor visitor) {

		if (this.originalAnnotations == null) {
			throw new NullPointerException("Annotation List must not be null !");
		}
		else {
			this.annoMeta = MetaDataFactory.createAnnoMeta(this.key, this);
			this.value = this.annoMeta;
			visitor.visit(this.originalAnnotations, this.annoMeta);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#containsMeta
	 * (org.jgentleframework.core.reflection.metadata.AnnoMeta)
	 */
	@Override
	public boolean containsMeta(AnnoMeta annoMeta) {

		return this.getAnnoMeta().contains(annoMeta.getKey());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#getAnnoMeta()
	 */
	@Override
	public AnnoMeta getAnnoMeta() {

		return annoMeta;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * getConstructorDefList()
	 */
	@Override
	public Map<Constructor<?>, Definition> getConstructorDefList() {

		return constructorDefList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#getFieldDefList
	 * ()
	 */
	@Override
	public Map<Field, Definition> getFieldDefList() {

		return fieldDefList;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jgentleframework.core.reflection.metadata.DefinitionCore#getMethodDefList
	 * ()
	 */
	@Override
	public Map<Method, Definition> getMethodDefList() {

		return methodDefList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * getOriginalAnnotation(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Annotation> T getOriginalAnnotation(Class<T> annotation) {

		for (Annotation obj : this.originalAnnotations) {
			if (obj.annotationType().equals(annotation)) {
				return (T) obj;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * getOriginalAnnotations()
	 */
	@Override
	public Annotation[] getOriginalAnnotations() {

		return originalAnnotations;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * getParameterDefList()
	 */
	@Override
	public Definition[] getParameterDefList() {

		if (!isInterpretedOfMethod()) {
			return null;
		}
		return parameterDefList;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * isInterpretedOfClass()
	 */
	@Override
	public boolean isInterpretedOfClass() {

		if (ReflectUtils.isClass(this.getKey())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * isInterpretedOfConstructor()
	 */
	@Override
	public boolean isInterpretedOfConstructor() {

		if (ReflectUtils.isConstructor(this.getKey())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * isInterpretedOfField()
	 */
	@Override
	public boolean isInterpretedOfField() {

		if (ReflectUtils.isField(this.getKey())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * isInterpretedOfMethod()
	 */
	@Override
	public boolean isInterpretedOfMethod() {

		if (ReflectUtils.isMethod(this.getKey())) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * setOriginalAnnotations(java.lang.annotation.Annotation[])
	 */
	@Override
	public void setOriginalAnnotations(Annotation[] originalAnnotations) {

		this.originalAnnotations = originalAnnotations;
		buildAnnoMeta();
	}

	/*
	 * (non-Javadoc)
	 * @seeorg.jgentleframework.core.reflection.metadata.DefinitionCore#
	 * setParameterDefList
	 * (org.jgentleframework.core.reflection.metadata.Definition[])
	 */
	@Override
	public void setParameterDefList(Definition[] parameterDefList) {

		if (!ReflectUtils.isMethod(this.getKey())
				&& !ReflectUtils.isConstructor(this.getKey())) {
			throw new RuntimeException(
					"This definition is not interpreted of a method or constructor!");
		}
		this.parameterDefList = parameterDefList;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuffer string = new StringBuffer();
		string.append("JGentle definition of '");
		string.append(this.getKey().toString());
		string.append("'#");
		string.append(super.toString());
		return string.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((annoMeta == null) ? 0 : annoMeta.hashCode());
		result = prime
				* result
				+ ((constructorDefList == null) ? 0 : constructorDefList
						.hashCode());
		result = prime * result
				+ ((fieldDefList == null) ? 0 : fieldDefList.hashCode());
		result = prime * result
				+ ((methodDefList == null) ? 0 : methodDefList.hashCode());
		result = prime * result + Arrays.hashCode(originalAnnotations);
		result = prime * result + Arrays.hashCode(parameterDefList);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof DefinitionCoreImpl))
			return false;
		final DefinitionCoreImpl other = (DefinitionCoreImpl) obj;
		if (annoMeta == null) {
			if (other.annoMeta != null)
				return false;
		}
		else if (!annoMeta.equals(other.annoMeta))
			return false;
		if (constructorDefList == null) {
			if (other.constructorDefList != null)
				return false;
		}
		else if (!constructorDefList.equals(other.constructorDefList))
			return false;
		if (fieldDefList == null) {
			if (other.fieldDefList != null)
				return false;
		}
		else if (!fieldDefList.equals(other.fieldDefList))
			return false;
		if (methodDefList == null) {
			if (other.methodDefList != null)
				return false;
		}
		else if (!methodDefList.equals(other.methodDefList))
			return false;
		if (!Arrays.equals(originalAnnotations, other.originalAnnotations))
			return false;
		if (!Arrays.equals(parameterDefList, other.parameterDefList))
			return false;
		return true;
	}
}
