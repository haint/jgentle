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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This interface represents all methods that are responsible for
 * {@link Definition} instantiation and management.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 24, 2008
 */
public interface DefinitionCore extends MetadataControl {
	/**
	 * Create AnnoMeta content of current {@link Definition}.
	 */
	public void buildAnnoMeta();

	/**
	 * Returns <b>true</b> if this {@link Definition} contains given
	 * {@link AnnoMeta}.
	 * 
	 * @param annoMeta
	 *            the {@link AnnoMeta} whose presence in this {@link Definition}
	 *            is to be tested.
	 * @return <b>true</b> if this {@link Definition} contains a specified
	 *         {@link AnnoMeta}, <b>false</b> otherwise.
	 */
	public boolean containsMeta(AnnoMeta annoMeta);

	/**
	 * Returns current {@link AnnoMeta} of this {@link Definition}
	 * 
	 * @return the annoMeta
	 */
	public AnnoMeta getAnnoMeta();

	/**
	 * Returns a {@link HashMap} containing all member {@link Definition}s
	 * corresponding to {@link Constructor} objects specified in the object
	 * class which this {@link Definition} is interpreted of.
	 * <p>
	 * <b>Note:</b> this method is only performed if current {@link Definition}
	 * is interpreted of object class.
	 */
	public HashMap<Constructor<?>, Definition> getConstructorDefList();

	/**
	 * Returns a {@link HashMap} containing all member {@link Definition}s
	 * corresponding to {@link Field} objects specified in the object class
	 * which this {@link Definition} is interpreted of.
	 * <p>
	 * <b>Note:</b> this method is only performed if current {@link Definition}
	 * is interpreted of object class.
	 */
	public HashMap<Field, Definition> getFieldDefList();

	/**
	 * Returns a {@link HashMap} containing all member {@link Definition}s
	 * corresponding to {@link Method} objects specified in the object class
	 * which this {@link Definition} is interpreted of.
	 * <p>
	 * <b>Note:</b> this method is only performed if current {@link Definition}
	 * is interpreted of object class.
	 */
	public HashMap<Method, Definition> getMethodDefList();

	/**
	 * Returns an instance of original annotation according as its given object
	 * class. <br>
	 * <b>Note:</b> the returned annotation instance is original annotation, not
	 * proxied annotation. To get the proxied annotation, invoke
	 * {@link AnnotatedElement#getAnnotation(Class)}
	 * 
	 * @param <T>
	 *            object class represents type of desired annotation.
	 * @param annotation
	 *            object class of desired annotation.
	 * @return returns an annotation instance is it exists, <b>null</b>
	 *         otherwise.
	 */
	public <T extends Annotation> T getOriginalAnnotation(Class<T> annotation);

	/**
	 * Returns an array containing all original annotations of this
	 * {@link Definition}
	 * 
	 * @return returns an array containing alloriginal annotation, if not
	 *         exists, returns an empty array.
	 */
	public Annotation[] getOriginalAnnotations();

	/**
	 * Returns an array containing all member {@link Definition}s of all
	 * parameters of the method object which this {@link Definition} is
	 * interpreted of.
	 * <p>
	 * <b>Note:</b> this method is only performed if current {@link Definition}
	 * is interpreted of the {@link Method} object.
	 * 
	 * @return an {@link ArrayList} of member {@link Definition}s, if does not
	 *         find any corresponding {@link Definition}, returns an empty
	 *         {@link ArrayList}. In case this {@link Definition} is not
	 *         interpreted of one {@link Method} object, the returned value will
	 *         be <b>null</b>.
	 */
	public Definition[] getParameterDefList();

	/**
	 * Returns <b>true</b> if this {@link Definition} is interpreted of one
	 * object class, otherwise returns <b>false</b>.
	 */
	public boolean isInterpretedOfClass();

	/**
	 * Returns <b>true</b> if this {@link Definition} is interpreted of one
	 * {@link Constructor} object, otherwise returns <b>false</b>.
	 */
	public boolean isInterpretedOfConstructor();

	/**
	 * Returns <b>true</b> if this {@link Definition} is interpreted of one
	 * {@link Field} object, otherwise returns <b>false</b>.
	 */
	public boolean isInterpretedOfField();

	/**
	 * Returns <b>true</b> if this {@link Definition} is interpreted of one
	 * {@link Method} object, otherwise returns <b>false</b>.
	 */
	public boolean isInterpretedOfMethod();

	/**
	 * Sets original annotations
	 * <p>
	 * <b>Note:</b> The invoking of this method will also execute creating of
	 * {@link AnnoMeta} content by automate invoke {@link #buildAnnoMeta()}
	 * method after all original annotations is setted.
	 * 
	 * @param originalAnnotations
	 *            array of original annotations need to be set.
	 */
	public void setOriginalAnnotations(Annotation[] originalAnnotations);

	/**
	 * @param parameterDefList
	 */
	public void setParameterDefList(Definition[] parameterDefList);

	/**
	 * Sets a new value to an attribute of specified annotation in this
	 * {@link Definition}.
	 * 
	 * @param annotation
	 *            <code>object class</code> of specified <code>annotation</code>
	 *            need to be set a new value.
	 * @param valueName
	 *            name of specified attribute of annotation.
	 * @param value
	 *            new object value
	 * @return returns the previous value associated with attribute, or
	 *         <b>null</b> if there was no specified value for attribute. (A
	 *         null return can also indicate that the attribute previously
	 *         associated null with value.)
	 */
	public Object setValueOfAnnotation(Class<? extends Annotation> annotation,
			String valueName, Object value);
}
