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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.core.factory.InOutDependencyException;
import org.jgentleframework.core.provider.AnnotationValidator;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.core.reflection.metadata.MetaDataFactory;
import org.jgentleframework.utils.AnnotationUtils;
import org.jgentleframework.utils.Assertor;
import org.jgentleframework.utils.ReflectUtils;

/**
 * An implementation of {@link DefinitionManager}
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 9, 2007
 * @see DefinitionManager
 * @see AbstractDefinitionController
 */
public class DefinitionManagerImpl extends AbstractDefinitionController
		implements DefinitionManager {
	/**
	 * Inner class quản lý thông tin của một entity chỉ định, chứa đựng thông
	 * tin về bản thân entity và đối tượng cha của nó đang chứa đựng thông tin
	 * của chính entity.
	 * 
	 * @author LE QUOC CHUNG
	 * @date Sep 28, 2007
	 */
	static class ObjectAnno {
		Object		object		= null;
		Class<?>	rawObject	= null;

		public ObjectAnno(Object object, Class<?> rawObject) {

			this.object = object;
			this.rawObject = rawObject;
		}

		/**
		 * @return the object
		 */
		public Object getObject() {

			return object;
		}

		/**
		 * @return the rawObject
		 */
		public Class<?> getRawObject() {

			return rawObject;
		}

		/**
		 * @param object
		 *            the object to set
		 */
		public void setObject(Object object) {

			this.object = object;
		}

		/**
		 * @param rawObject
		 *            the rawObject to set
		 */
		public void setRawObject(Class<?> rawObject) {

			this.rawObject = rawObject;
		}
	}

	/**
	 * contains all created {@link Definition}
	 */
	private HashMap<Object, Definition>	defList		= new HashMap<Object, Definition>();
	/**
	 * The {@link HashMap} containing all created {@link Definition}s according
	 * to ID
	 */
	private HashMap<String, Definition>	defListSub	= new HashMap<String, Definition>();

	/**
	 * Constructor
	 * 
	 * @param annotationRegister
	 */
	public DefinitionManagerImpl(AnnotationRegister annotationRegister) {

		this.annotationRegister = annotationRegister;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#buildDefinition(java.lang.annotation.Annotation[],
	 *      java.lang.Object)
	 */
	@Override
	public Definition buildDefinition(Annotation[] list, Object obj) {

		Definition def = MetaDataFactory.createDefinition(obj,
				this.visitorHandler);
		def.setOriginalAnnotations(list);
		return def;
	}

	/**
	 * Create {@link Definition}.<br>
	 * <br>
	 * <b>Note:</b> obj must be object class, {@link Method} or {@link Field}
	 * 
	 * @param obj
	 *            the given object.
	 * @return returns the {@link Definition}.
	 */
	private Definition buildDefinition(Object obj) {

		Annotation[] list = AnnotationUtils.getAnnotations(obj, Types.DEFAULT);
		Definition def = buildDefinition(list, obj);
		return def;
	}

	/**
	 * @param object
	 * @param lists
	 */
	private void checkLoopValidation(Object object, Annotation[] lists) {

		Assertor.notNull(object);
		HashMap<ObjectAnno, Annotation[]> temp = new HashMap<ObjectAnno, Annotation[]>();
		Class<?> clazz = null;
		if (ReflectUtils.isMethod(object)) {
			clazz = ((Method) object).getDeclaringClass();
		}
		else if (ReflectUtils.isField(object)) {
			clazz = ((Field) object).getDeclaringClass();
		}
		else if (ReflectUtils.isConstructor(object)) {
			clazz = ((Constructor<?>) object).getDeclaringClass();
		}
		else if (ReflectUtils.isClass(object)) {
			clazz = (Class<?>) object;
		}
		temp.put(new ObjectAnno(object, clazz), lists);
		do {
			HashMap<ObjectAnno, Annotation[]> remain = new HashMap<ObjectAnno, Annotation[]>();
			try {
				for (Entry<ObjectAnno, Annotation[]> entry : temp.entrySet()) {
					ObjectAnno obj = entry.getKey();
					Annotation[] list = entry.getValue();
					for (Annotation anno : list) {
						// Nếu anno đã được đăng kí trong AnnotationRegister
						if (this.annotationRegister.isRegisteredAnnotation(anno
								.annotationType())) {
							// kiểm tra thông tin annotation
							this.validateAnnnotation(anno, list, obj.object,
									obj.rawObject);
							// nếu anno hiện hành cũng có đính kèm thông tin
							// annotation khác.
							if (AnnotationUtils.getAnnotations(anno,
									Types.ANNOTATION).length > 0) {
								if (ReflectUtils.isAnnotation(obj.object)) {
									remain.put(new ObjectAnno(anno,
											((Annotation) obj.object)
													.annotationType()),
											AnnotationUtils.getAnnotations(
													anno, Types.ANNOTATION));
								}
								else {
									if (ReflectUtils.isClass(obj.object)) {
										remain
												.put(
														new ObjectAnno(
																anno,
																(Class<?>) obj.object),
														AnnotationUtils
																.getAnnotations(
																		anno,
																		Types.ANNOTATION));
									}
									else {
										remain
												.put(
														new ObjectAnno(
																anno,
																obj.object
																		.getClass()),
														AnnotationUtils
																.getAnnotations(
																		anno,
																		Types.ANNOTATION));
									}
								}
							}
						}
					}
				}
			}
			finally {
				temp.clear();
				temp.putAll(remain);
				remain.clear();
			}
		}
		while (temp.size() != 0);
	}

	/**
	 * Kiểm tra loop validate thông qua hệ thống cây annotation.
	 * 
	 * @param object
	 *            đối tượng cần kiểm tra loop validate annotation.
	 * @return Trả về <b>true</b> nếu object có chứa annotation đính kèm, ngược
	 *         lại trả về <b>false</b>.
	 */
	private boolean checkLoopValidation(Object object) {

		// validate dữ liệu thông tin annotation trước khi diễn dịch thành
		// definition.
		if (AnnotationUtils.getAnnotations(object, Types.DEFAULT).length > 0) {
			checkLoopValidation(object, AnnotationUtils.getAnnotations(object,
					Types.DEFAULT));
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#containsDefinition(java.lang.Class)
	 */
	@Override
	public boolean containsDefinition(Class<?> clazz) {

		Assertor.notNull(clazz);
		return this.defList.containsKey(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#containsDefinition(java.lang.String)
	 */
	@Override
	public boolean containsDefinition(String ID) {

		Assertor.notNull(ID);
		return this.defListSub.containsKey(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#getDefinition(java.lang.Object)
	 */
	@Override
	public Definition getDefinition(Object obj) {

		Assertor.notNull(obj);
		Definition result = null;
		if (!this.defList.containsKey(obj) || this.defList.get(obj) == null) {
			if (ReflectUtils.isMethod(obj))
				this.loadDefinition((Method) obj);
			else if (ReflectUtils.isField(obj))
				this.loadDefinition((Field) obj);
			else if (ReflectUtils.isConstructor(obj)) {
				this.loadDefinition((Constructor<?>) obj);
			}
			else
				this.putDefinition(obj, this.loadDefinition(obj));
		}
		result = this.defList.get(obj);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#getDefinition(java.lang.String)
	 */
	@Override
	public Definition getDefinition(String ID) {

		return this.defListSub.get(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#getDefList()
	 */
	@Override
	public HashMap<Object, Definition> getDefList() {

		return defList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#getDefListSub()
	 */
	@Override
	public HashMap<String, Definition> getDefListSub() {

		return defListSub;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#loadDefinition(java.lang.Class)
	 */
	@Override
	public void loadDefinition(Class<?> clazz) {

		Assertor.notNull(clazz);
		Definition definition = loadDefinition((Object) clazz);
		this.putDefinition(clazz, definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadDefinition(java.lang.Class,
	 *      java.lang.String)
	 */
	@Override
	public void loadDefinition(Class<?> clazz, String ID) {

		Assertor.notNull(clazz);
		Definition definition = loadDefinition((Object) clazz);
		this.putDefinition(ID, definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#loadDefinition(java.lang.reflect.Field)
	 */
	@Override
	public void loadDefinition(Field field) {

		Assertor.notNull(field);
		Definition definition = loadDefinition((Object) field);
		this.putDefinition(field, definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#loadDefinition(java.lang.reflect.Method)
	 */
	@Override
	public void loadDefinition(Method method) {

		Assertor.notNull(method);
		Definition definition = loadDefinition((Object) method);
		this.putDefinition(method, definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.defhandling.DefinitionManager#loadDefinition(java.lang.reflect.Constructor)
	 */
	@Override
	public void loadDefinition(Constructor<?> constructor) {

		Assertor.notNull(constructor);
		Definition definition = loadDefinition((Object) constructor);
		this.putDefinition(constructor, definition);
	}

	/**
	 * Creates {@link Definition}
	 * 
	 * @param object
	 */
	private synchronized Definition loadDefinition(Object object) {

		// validate thông tin annotation trước khi
		// diễn dịch thành definition.
		checkLoopValidation(object);
		Definition result = buildDefinition(object);
		// Nếu đối tượng truyền vào là một method.
		if (ReflectUtils.isMethod(object)) {
			Method method = (Method) object;
			/*
			 * Kiểm tra thông tin parameter nếu có
			 */
			if (method.getParameterTypes().length > 0) {
				Definition[] paraDefLst = new Definition[method
						.getParameterTypes().length];
				for (int i = 0; i < paraDefLst.length; i++) {
					Annotation[] list = method.getParameterAnnotations()[i];
					if (list.length != 0) {
						// validate thông tin annotation trước khi
						// diễn dịch thành definition.
						checkLoopValidation(method, list);
						Definition def = buildDefinition(list, method);
						paraDefLst[i] = def;
					}
				}
				result.setParameterDefList(paraDefLst);
			}
		}
		else if (ReflectUtils.isConstructor(object)) {
			Constructor<?> constructor = (Constructor<?>) object;
			if (constructor.getParameterTypes().length > 0) {
				Definition[] paraDefLst = new Definition[constructor
						.getParameterTypes().length];
				for (int i = 0; i < paraDefLst.length; i++) {
					Annotation[] list = constructor.getParameterAnnotations()[i];
					if (list.length != 0) {
						// validate thông tin annotation trước khi
						// diễn dịch thành definition.
						checkLoopValidation(constructor, list);
						Definition def = buildDefinition(list, constructor);
						paraDefLst[i] = def;
					}
				}
				result.setParameterDefList(paraDefLst);
			}
		}
		// Nếu đối tượng truyền vào là một Class
		else if (ReflectUtils.isClass(object)) {
			Class<?> clazz = (Class<?>) object;
			// Khởi tạo definition cho field của class nếu có
			Field[] fieldList = ReflectUtils.getAllDeclaredFields(clazz);
			if (fieldList != null && fieldList.length != 0) {
				for (Field field : fieldList) {
					int check = 0;
					for (Field fil : result.getFieldDefList().keySet()) {
						if (ReflectUtils.equals(field, fil)) {
							check++;
						}
					}
					if (check == 0 && checkLoopValidation(field)) {
						result.getFieldDefList().put(field,
								getDefinition(field));
					}
				}
			}
			// creates definition of constructors if they exist.
			Constructor<?>[] constructorList = clazz.getDeclaredConstructors();
			if (constructorList != null && constructorList.length != 0) {
				for (Constructor<?> constructor : constructorList) {
					Definition def = getDefinition(constructor);
					result.getConstructorDefList().put(constructor, def);
				}
			}
			// creates definition of all methods and their parameters in this
			// declaring class if they exist.
			Method[] methodList = ReflectUtils.getAllDeclaredMethods(clazz);
			if (methodList != null && methodList.length != 0) {
				for (Method method : methodList) {
					int check = 0;
					for (Method met : result.getMethodDefList().keySet()) {
						if (ReflectUtils.equals(met, method)) {
							check++;
						}
					}
					if (check == 0 && checkLoopValidation(method)) {
						result.getMethodDefList().put(method,
								getDefinition(method));
					}
				}
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadCustomizedDefinition(java.lang.Class,
	 *      java.lang.annotation.Annotation)
	 */
	@Override
	public synchronized void loadCustomizedDefinition(Class<?> clazz,
			Annotation annotation) {

		String IDNULL = null;
		loadCustomizedDefinition(clazz, annotation, IDNULL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadDefinition(java.lang.String,
	 *      java.lang.Class, java.lang.annotation.Annotation[])
	 */
	@Override
	public synchronized void loadCustomizedDefinition(Class<?> clazz,
			Annotation annotation, String ID) {

		Definition def = null;
		List<Annotation> annotationList = new LinkedList<Annotation>();
		// Kiểm tra điều kiện các annotation
		if (annotation != null) {
			Definition current = ID != null ? this.getDefinition(ID) : this
					.getDefinition(clazz);
			Annotation[] rawList;
			if (current != null)
				rawList = current.getOriginalAnnotations();
			else
				throw new InOutDependencyException(
						"Does not found Definition with ID '" + ID + "'");
			for (Annotation anno : rawList) {
				if (anno.annotationType().equals(annotation.annotationType())) {
					annotationList.add(annotation);
				}
				else {
					annotationList.add(anno);
				}
			}
			// checks target
			if (!ReflectUtils.isValidTarget(annotation, clazz)) {
				throw new InOutDependencyException("Annotation "
						+ annotation.toString() + " has invalid target.");
			}
			if (!annotationList.contains(annotation))
				annotationList.add(annotation);
			checkLoopValidation(clazz, annotationList
					.toArray(new Annotation[annotationList.size()]));
			// creates definition
			def = this.buildDefinition(annotationList
					.toArray(new Annotation[annotationList.size()]), clazz);
			if (ID != null)
				this.putDefinition(ID, def);
			else
				this.putDefinition(clazz, def);
		}
		else {
			if (ID != null
					&& (!this.containsDefinition(ID) || this.getDefinition(ID) == null)) {
				def = this.buildDefinition(annotationList
						.toArray(new Annotation[annotationList.size()]), clazz);
				this.putDefinition(ID, def);
			}
			else {
				throw new InOutDependencyException(
						"Annotation need to be loaded is null but ID '" + ID
								+ "' is existed !");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadDefinition(java.lang.String,
	 *      java.lang.Object, java.lang.Class, java.lang.annotation.Annotation)
	 */
	@Override
	public Definition loadCustomizedDefinition(String ID, Object obj,
			Class<?> clazz, Annotation annotation) {

		if (ReflectUtils.isCast(Field.class, obj)) {
			return loadCustomizedDefinition(ID, (Field) obj, clazz, annotation);
		}
		else if (ReflectUtils.isCast(Method.class, obj)) {
			return loadCustomizedDefinition(ID, (Method) obj, clazz, annotation);
		}
		else {
			throw new InOutDependencyException(
					"Invalid type '"
							+ obj.getClass()
							+ "' for arguments 'obj' of loadDefinition method! Only Field '"
							+ Field.class + "' or Method '" + Method.class
							+ "' are permitted !");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadCustomizedDefinition(java.lang.reflect.Field,
	 *      java.lang.Class, java.lang.annotation.Annotation)
	 */
	@Override
	public synchronized Definition loadCustomizedDefinition(Field field,
			Class<?> clazz, Annotation annotation) {

		return loadCustomizedDefinition(null, field, clazz, annotation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadDefinition(java.lang.String,
	 *      java.lang.reflect.Field, java.lang.annotation.Annotation[])
	 */
	@Override
	public synchronized Definition loadCustomizedDefinition(String ID,
			Field field, Class<?> clazz, Annotation annotation) {

		if (ID != null && this.getDefinition(ID) == null)
			this.loadCustomizedDefinition(clazz, null, ID);
		else
			this.loadDefinition(clazz);
		if (!ReflectUtils.isValidTarget(annotation, field)) {
			throw new InOutDependencyException("Annotation "
					+ annotation.toString() + " has invalid target.");
		}
		List<Annotation> annotationList = new LinkedList<Annotation>();
		// Kiểm tra điều kiện các annotation
		Definition classDef = ID != null ? this.getDefinition(ID) : this
				.getDefinition(clazz);
		Definition result = null;
		synchronized (classDef.getFieldDefList()) {
			if (classDef.getFieldDefList().containsKey(field)) {
				Annotation[] rawList = classDef.getFieldDefList().get(field)
						.getOriginalAnnotations();
				for (Annotation anno : rawList) {
					if (anno.annotationType().equals(
							annotation.annotationType())) {
						annotationList.add(annotation);
					}
					else {
						annotationList.add(anno);
					}
				}
			}
			if (!annotationList.contains(annotation))
				annotationList.add(annotation);
			checkLoopValidation(field, annotationList
					.toArray(new Annotation[annotationList.size()]));
			// creates definition
			Definition def = this.buildDefinition(annotationList
					.toArray(new Annotation[annotationList.size()]), field);
			result = classDef.getFieldDefList().put(field, def);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadCustomizedDefinition(java.lang.reflect.Method,
	 *      java.lang.Class, java.lang.annotation.Annotation)
	 */
	@Override
	public synchronized Definition loadCustomizedDefinition(Method method,
			Class<?> clazz, Annotation annotation) {

		return loadCustomizedDefinition(null, method, clazz, annotation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#loadDefinition(java.lang.String,
	 *      java.lang.reflect.Method, java.lang.annotation.Annotation[])
	 */
	@Override
	public synchronized Definition loadCustomizedDefinition(String ID,
			Method method, Class<?> clazz, Annotation annotation) {

		if (ID != null)
			this.loadCustomizedDefinition(clazz, null, ID);
		else
			this.loadDefinition(clazz);
		if (!ReflectUtils.isValidTarget(annotation, method)) {
			throw new InOutDependencyException("Annotation "
					+ annotation.toString() + " has invalid target.");
		}
		List<Annotation> annotationList = new LinkedList<Annotation>();
		// Kiểm tra điều kiện các annotation
		Definition classDef = ID != null ? this.getDefinition(ID) : this
				.getDefinition(clazz);
		Definition result = null;
		synchronized (classDef.getMethodDefList()) {
			if (classDef.getMethodDefList().containsKey(method)) {
				Annotation[] rawList = classDef.getMethodDefList().get(method)
						.getOriginalAnnotations();
				for (Annotation anno : rawList) {
					if (anno.annotationType().equals(
							annotation.annotationType())) {
						annotationList.add(annotation);
					}
					else {
						annotationList.add(anno);
					}
				}
			}
			if (!annotationList.contains(annotation))
				annotationList.add(annotation);
			// Khởi tạo definition
			checkLoopValidation(method, annotationList
					.toArray(new Annotation[annotationList.size()]));
			Definition def = this.buildDefinition(annotationList
					.toArray(new Annotation[annotationList.size()]), method);
			result = classDef.getMethodDefList().put(method, def);
		}
		return result;
	}

	/**
	 * @param sourceObject
	 * @param definition
	 */
	private synchronized void putDefinition(Object sourceObject,
			Definition definition) {

		this.defList.put(sourceObject, definition);
	}

	/**
	 * @param ID
	 * @param definition
	 */
	private synchronized void putDefinition(String ID, Definition definition) {

		this.defListSub.put(ID, definition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#removeDefinition(java.lang.Class)
	 */
	@Override
	public Definition removeDefinition(Class<?> clazz) {

		return this.defList.remove((Object) clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#removeDefinition(java.lang.reflect.Field)
	 */
	@Override
	public Definition removeDefinition(Field field) {

		return this.defList.remove((Object) field);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#removeDefinition(java.lang.reflect.Method)
	 */
	@Override
	public Definition removeDefinition(Method method) {

		return this.defList.remove((Object) method);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.defhandling.DefinitionManager#removeDefinition(java.lang.reflect.Constructor)
	 */
	@Override
	public Definition removeDefinition(Constructor<?> constructor) {

		return this.defList.remove((Object) constructor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.IDefinitionManager#removeDefinition(java.lang.Object)
	 */
	public Definition removeDefinition(Object obj) {

		return this.defList.remove(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgentleframework.core.metadatahandling.aohhandling.defhandling.DefinitionManager#validateAnnnotation(java.lang.annotation.Annotation,
	 *      java.lang.annotation.Annotation[], java.lang.Object,
	 *      java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void validateAnnnotation(Annotation annotation,
			Annotation[] annoList, Object object, Class<?> classez) {

		synchronized (this.annotationRegister.getValidatorlist()) {
			if (this.annotationRegister.getValidatorlist().containsKey(
					annotation.annotationType())) {
				AnnotationValidator<Annotation> validator = null;
				validator = (AnnotationValidator<Annotation>) this.annotationRegister
						.getValidatorlist().get(annotation.annotationType());
				// validate dữ liệu annotation
				do {
					try {
						validator.validate(annotation, annoList, object,
								classez, this);
					}
					catch (RuntimeException e) {
						if (validator.catchException(e, annotation) == false) {
							// Nếu object gốc là annotation
							if (ReflectUtils.isAnnotation(object)) {
								AnnotationValidator<Annotation> thisValidator = null;
								if (this.annotationRegister.getValidatorlist()
										.containsKey(
												((Annotation) object)
														.annotationType())) {
									thisValidator = (AnnotationValidator<Annotation>) this.annotationRegister
											.getValidatorlist().get(
													((Annotation) object)
															.annotationType());
									/*
									 * Kiểm tra xem validator hiện hành của
									 * object gốc có đăng kí catch exception cho
									 * annotation hiện hành hay không
									 */
									if (isCatchExceptionRegistered(annotation
											.annotationType(), thisValidator)) {
										thisValidator.catchException(e,
												annotation);
									}
									else {
										throw e;
									}
								}
							}
						}
						else {
							continue;
						}
					}
					break;
				}
				while (true);
			}
		}
	}
}
