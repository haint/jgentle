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
package org.jgentleframework.core.handling;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.jgentleframework.core.reflection.AbstractVisitorHandler;
import org.jgentleframework.core.reflection.DefinitionPostProcessor;
import org.jgentleframework.core.reflection.IAnnotationVisitor;
import org.jgentleframework.core.reflection.annohandler.AnnotationBeanProcessor;
import org.jgentleframework.core.reflection.annohandler.AnnotationPostProcessor;
import org.jgentleframework.core.reflection.metadata.Definition;

/**
 * {@link DefinitionManager} is responsible for {@link Definition} management.
 * All {@link Definition} in JGentle system will be managed by it. It provides
 * some tools in order to create {@link Definition}, validate interpreted
 * annotations, ... and register annotations, or annotation validators.
 * Moreover, it also offers additional features to manage some extension-point
 * in JGentle such as {@link AnnotationPostProcessor},
 * {@link DefinitionPostProcessor}, ...
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Oct 10, 2007
 * @see IAbstractDefinitionExceptionCatcher
 */
public interface DefinitionManager extends IAnnotationVisitor,
		IAbstractDefinitionExceptionCatcher {
	/**
	 * Adds a {@link AnnotationBeanProcessor}
	 * 
	 * @param <T>
	 * @param handler
	 *            the {@link AnnotationBeanProcessor} need to be registered.
	 * @return returns previous {@link AnnotationBeanProcessor} if it exists, if
	 *         not, returns <b>null</b>.
	 * @throws ClassNotFoundException
	 *             this exception will be thrown if not found the valid
	 *             annotation class corresponding type T
	 */
	public <T extends Annotation> AnnotationBeanProcessor<?> addAnnotationBeanProcessor(
			AnnotationBeanProcessor<T> handler) throws ClassNotFoundException;

	/**
	 * Creates {@link Definition}
	 * <p>
	 * <b>Note:</b> returned {@link Definition} of this method is not managed by
	 * provider.
	 * 
	 * @param list
	 *            the list of annotations need to be interpreted
	 * @param obj
	 *            the original object of {@link Definition}.
	 */
	public Definition buildDefinition(Annotation[] list, Object obj);

	/**
	 * Returns <b>true</b> if corresponding {@link Definition} of given class is
	 * existed, <b>false</b> otherwise.
	 * 
	 * @param clazz
	 *            the given class need to be tested.
	 */
	public boolean containsDefinition(Class<?> clazz);

	/**
	 * Returns <b>true</b> if {@link Definition} according to given
	 * <code>ID</code> is existed.
	 * 
	 * @param ID
	 *            the given ID of {@link Definition}
	 * @return <b>true</b> if it exists, <b>false</b> otherwise.
	 */
	public boolean containsDefinition(String ID);

	/**
	 * Returns {@link AnnotationRegister} instance of this
	 * {@link DefinitionManager}
	 * 
	 * @return the annotationRegister
	 */
	public AnnotationRegister getAnnotationRegister();

	/**
	 * Returns the interpreted {@link Definition} of given object. In case the
	 * corresponding {@link Definition} is not existed, this method will
	 * automatically create {@link Definition} according to given object.
	 * 
	 * @param obj
	 *            the given object
	 * @return {@link Definition}
	 */
	public Definition getDefinition(Object obj);

	/**
	 * Returns the interpreted {@link Definition} of given object. In case the
	 * corresponding {@link Definition} is not existed, returned value will be
	 * <b>null</b>.
	 * 
	 * @param ID
	 *            the given ID of {@link Definition}
	 * @return a {@link Definition} is it exists, if not, return <b>null</b>.
	 */
	public Definition getDefinition(String ID);

	/**
	 * Returns the {@link HashMap} containing all interpreted {@link Definition}
	 * .
	 */
	public HashMap<Object, Definition> getDefList();

	/**
	 * Returns the {@link HashMap} containing all interpreted {@link Definition}
	 * corresponding to their ID
	 */
	public HashMap<String, Definition> getDefListSub();

	/**
	 * @return the visitorHandler
	 */
	public IAnnotationVisitor getVisitorHandler();

	/**
	 * Creates {@link Definition}
	 * 
	 * @param clazz
	 *            the original object class is used to interpret
	 *            {@link Definition}.
	 */
	public void loadDefinition(Class<?> clazz);

	/**
	 * Creates {@link Definition}
	 * 
	 * @param clazz
	 *            the given original object class is used to interpret
	 *            {@link Definition}
	 * @param ID
	 *            the ID corresponding to {@link Definition}.
	 */
	public void loadDefinition(Class<?> clazz, String ID);

	/**
	 * Creates {@link Definition} from given {@link Field} object.
	 * 
	 * @param field
	 *            the given {@link Field}
	 */
	public void loadDefinition(Field field);

	/**
	 * Creates {@link Definition} from given {@link Constructor} object.
	 * 
	 * @param constructor
	 *            the given {@link Constructor}
	 */
	public void loadDefinition(Constructor<?> constructor);

	/**
	 * Creates {@link Definition} from given {@link Method} object.
	 * 
	 * @param method
	 *            the given method.
	 */
	public void loadDefinition(Method method);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * <p>
	 * <b>Note:</b>
	 * <p>
	 * - In case the given annotation is <b>null</b> and given <b>ID</b> is not
	 * existed, this method will automatically create {@link Definition}
	 * corresponding to given <b>ID</b>.
	 * <p>
	 * - In case given ID is <b>null</b>, given <code>annotation</code> is not
	 * <b>null</b>, this method will automatically creates {@link Definition}
	 * corresponding to object class (given {@link Class}) and given annotation.
	 * 
	 * @param ID
	 *            the ID of {@link Definition}
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Class}.
	 */
	public void loadCustomizedDefinition(Class<?> clazz, Annotation annotation,
			String ID);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * <p>
	 * This performance of method is the same as
	 * {@link #loadCustomizedDefinition(Class, Annotation, String)} according to
	 * argument <code>ID</code>is <b>null</b>.
	 * 
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Class}.
	 */
	public void loadCustomizedDefinition(Class<?> clazz, Annotation annotation);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * <p>
	 * <b>Note:</b> object must be a {@link Field} or {@link Method} is declared
	 * in given class.
	 * 
	 * @param ID
	 *            the ID of {@link Definition}
	 * @param obj
	 *            the given object.
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 *            In case the {@link Definition} according to given ID is not
	 *            existed, {@link DefinitionManager} will automatically create
	 *            corresponding {@link Definition} according to given ID.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            object.
	 * @return Returns previous {@link Definition} according to given object if
	 *         it exists, if not, returns <b>null</b>.
	 */
	public Definition loadCustomizedDefinition(String ID, Object obj,
			Class<?> clazz, Annotation annotation);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * <p>
	 * 
	 * @param field
	 *            the given {@link Field}
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 *            In case the {@link Definition} according to this object class
	 *            is not existed, {@link DefinitionManager} will automatically
	 *            create corresponding {@link Definition}.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Field}.
	 * @return Returns previous {@link Definition} according to given
	 *         {@link Field} if it exists, if not, returns <b>null</b>.
	 */
	public Definition loadCustomizedDefinition(Field field, Class<?> clazz,
			Annotation annotation);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * 
	 * @param ID
	 *            the ID of {@link Definition}
	 * @param field
	 *            the given {@link Field}
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 *            In case the {@link Definition} according to given ID is not
	 *            existed, {@link DefinitionManager} will automatically create
	 *            corresponding {@link Definition} according to given ID.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Field}.
	 * @return Returns previous {@link Definition} according to given
	 *         {@link Field} if it exists, if not, returns <b>null</b>.
	 */
	public Definition loadCustomizedDefinition(String ID, Field field,
			Class<?> clazz, Annotation annotation);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * 
	 * @param method
	 *            the given method
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 *            In case the {@link Definition} according to this object class
	 *            is not existed, {@link DefinitionManager} will automatically
	 *            create corresponding {@link Definition}.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Method}.
	 * @return Returns previous {@link Definition} according to given
	 *         {@link Method} if it exists, if not, returns <b>null</b>.
	 */
	public Definition loadCustomizedDefinition(Method method, Class<?> clazz,
			Annotation annotation);

	/**
	 * Creates a customzied <code>object class</code> {@link Definition}
	 * 
	 * @param ID
	 *            the ID of {@link Definition}
	 * @param method
	 *            the given method
	 * @param clazz
	 *            the original object class corresponding to {@link Definition}.
	 *            In case the {@link Definition} according to given ID is not
	 *            existed, {@link DefinitionManager} will automatically create
	 *            corresponding {@link Definition} according to given ID.
	 * @param annotation
	 *            the specified annotation need to be interpreted of given
	 *            {@link Method}.
	 * @return Returns previous {@link Definition} according to given
	 *         {@link Method} if it exists, if not, returns <b>null</b>.
	 */
	public Definition loadCustomizedDefinition(String ID, Method method,
			Class<?> clazz, Annotation annotation);

	/**
	 * Removes the {@link Definition} according to given object class
	 * 
	 * @param clazz
	 *            the given object class
	 * @return returns the {@link Definition} that was removed, or returns
	 *         <b>null</b> if no {@link Definition} is removed.
	 */
	public Definition removeDefinition(Class<?> clazz);

	/**
	 * Removes the {@link Definition} according to given {@link Field}
	 * 
	 * @param field
	 *            the given field
	 * @return returns the {@link Definition} that was removed, or returns
	 *         <b>null</b> if no {@link Definition} is removed.
	 */
	public Definition removeDefinition(Field field);

	/**
	 * Removes the {@link Definition} according to given {@link Method}
	 * 
	 * @param method
	 *            the given method.
	 * @return returns the {@link Definition} that was removed, or returns
	 *         <b>null</b> if no {@link Definition} is removed.
	 */
	public Definition removeDefinition(Method method);

	/**
	 * Removes the {@link Definition} according to given {@link Constructor}
	 * 
	 * @param constructor
	 *            the given {@link Constructor}.
	 * @return returns the {@link Definition} that was removed, or returns
	 *         <b>null</b> if no {@link Definition} is removed.
	 */
	public Definition removeDefinition(Constructor<?> constructor);

	/**
	 * Removes the definition according to given object.
	 * 
	 * @param obj
	 *            the given object
	 * @return returns the {@link Definition} that was removed, or returns
	 *         <b>null</b> if no {@link Definition} is removed.
	 */
	public Definition removeDefinition(Object obj);

	/**
	 * Sets the visitor handler.
	 * 
	 * @param visitorHandler
	 *            the visitor handler
	 */
	public void setVisitorHandler(AbstractVisitorHandler visitorHandler);

	/**
	 * Validates the interpreting annotation by corresponding registered
	 * validator.
	 * 
	 * @param annotation
	 *            the annotation need to be validated.
	 * @param annoList
	 *            danh sách đối tượng annotation list gốc.
	 * @param object
	 *            the target object that is annotated with given annotation
	 * @param classez
	 *            trong trường hợp object cũng là annotation, thì classez chính
	 *            là đối tượng gốc mà object đính kèm thông tin lên. Còn nếu
	 *            không classez sẽ là null.
	 */
	public void validateAnnnotation(Annotation annotation,
			Annotation[] annoList, Object object, Class<?> classez);
}