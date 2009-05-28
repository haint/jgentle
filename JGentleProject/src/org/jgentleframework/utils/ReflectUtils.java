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
package org.jgentleframework.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.aopalliance.reflect.Metadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.metadata.AnnoMeta;
import org.jgentleframework.core.reflection.metadata.MetaDataFactory;

/**
 * Provides some of static methods in order to execute common reflect
 * operations.
 * <p>
 * Mainly for use within the framework, but also useful for application code.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 29, 2007
 */
public final class ReflectUtils {
	/** The Constant log. */
	private final static Log	log	= LogFactory.getLog(ReflectUtils.class);

	/**
	 * Creates {@link AnnoMeta} object.
	 * 
	 * @param annos
	 *            annotation instance need to be create to {@link AnnoMeta}
	 * @param container
	 *            the parrent {@link AnnoMeta} of returned {@link AnnoMeta}
	 * @param definitionManager
	 *            the {@link DefinitionManager} instance
	 * @return AnnoMeta
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public static AnnoMeta buildAnnoMeta(Annotation annos, AnnoMeta container,
			DefinitionManager definitionManager)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		AnnoMeta thisMeta = null;
		Class<?> clazz = annos.annotationType();
		Method[] methods = clazz.getDeclaredMethods();
		thisMeta = MetaDataFactory.createAnnoMeta(clazz, annos, container,
				clazz.toString());
		if (methods.length > 0) {
			for (Method obj : methods) {
				Object value = null;
				obj.setAccessible(true);
				value = obj.invoke(annos);
				Metadata part = null;
				part = MetaDataFactory.createMetaData(obj.getName(), value);
				/*
				 * Đưa list sub annoMeta vừa được tạo vào trong annoMeta hiện
				 * hành
				 */
				thisMeta.putMetaData(part);
			}
		}
		/*
		 * add annoMeta vào container (annoMeta cha chứ nó)
		 */
		container.putMetaData(thisMeta);
		return thisMeta;
	}

	/**
	 * Creates an object instance.
	 * 
	 * @param clazz
	 *            the object class type
	 * @param args
	 *            the arguments of the constructor need to be use to instantiate
	 *            bean. instance.
	 * @return trả về đối tượng Object vừa được khởi tạo.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createInstance(Class<T> clazz, Object... args) {

		T result = null;
		try {
			Constructor<T> constructor = null;
			if (args == null) {
				constructor = clazz.getDeclaredConstructor();
			}
			else {
				List<Class<?>> argsType = new LinkedList<Class<?>>();
				for (Object arg : args) {
					argsType.add(arg.getClass());
				}
				constructor = clazz.getDeclaredConstructor(argsType
						.toArray(new Class<?>[argsType.size()]));
			}
			constructor.setAccessible(true);
			result = args == null ? (T) constructor.newInstance() : constructor
					.newInstance(args);
		}
		catch (InstantiationException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create instance basing on target class ['"
						+ clazz + "']", e);
			}
		}
		catch (IllegalAccessException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create instance basing on target class ['"
						+ clazz + "']", e);
			}
		}
		catch (SecurityException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create instance basing on target class ['"
						+ clazz + "']", e);
			}
		}
		catch (NoSuchMethodException e) {
			if (args != null) {
				for (Constructor<?> cons : clazz.getDeclaredConstructors()) {
					cons.setAccessible(true);
					try {
						result = (T) cons.newInstance(args);
					}
					catch (IllegalArgumentException e1) {
						continue;
					}
					catch (InstantiationException e1) {
						if (log.isFatalEnabled()) {
							log.fatal(
									"Could not create instance basing on target class ['"
											+ clazz + "']", e1);
						}
					}
					catch (IllegalAccessException e1) {
						if (log.isFatalEnabled()) {
							log.fatal(
									"Could not create instance basing on target class ['"
											+ clazz + "']", e1);
						}
					}
					catch (InvocationTargetException e1) {
						if (log.isFatalEnabled()) {
							log.fatal(
									"Could not create instance basing on target class ['"
											+ clazz + "']", e1);
						}
					}
				}
				if (result == null) {
					if (log.isFatalEnabled()) {
						log.fatal(
								"Could not create instance basing on target class ['"
										+ clazz + "']", e);
					}
				}
			}
			else {
				if (log.isFatalEnabled()) {
					log.fatal(
							"Could not create instance basing on target class ['"
									+ clazz + "']", e);
				}
			}
		}
		catch (IllegalArgumentException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create instance basing on target class ['"
						+ clazz + "']", e);
			}
		}
		catch (InvocationTargetException e) {
			if (log.isFatalEnabled()) {
				log.fatal("Could not create instance basing on target class ['"
						+ clazz + "']", e);
			}
		}
		return result;
	}

	/**
	 * Returns <code>true</code> if two given {@link Field} are equalled. Two
	 * {@link Field fields} are considered equal if and only if their names are
	 * equalled and their declared types are equalled too.
	 * 
	 * @param source
	 *            the source field
	 * @param target
	 *            the target field
	 * @return true, if two given field are equalled
	 */
	public static boolean equals(Field source, Field target) {

		if (!source.getType().equals(target.getType())) {
			return false;
		}
		if (!source.getName().equals(target.getName())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns <code>true</code> if two given {@link Method} are equalled. Two
	 * {@link Method methods} are considered equal if and only if their names
	 * are equalled and their declared parameter types are equalled too.
	 * 
	 * @param source
	 *            the source method
	 * @param target
	 *            the target method
	 * @return true, if two given method are equalled
	 */
	public static boolean equals(Method source, Method target) {

		int check = 0;
		Class<?>[] sourceArgs;
		if (source.getName().equals(target.getName())) {
			check++;
			sourceArgs = source.getParameterTypes();
		}
		else
			return false;
		if (source.getReturnType() != target.getReturnType())
			return false;
		if (check != 0) {
			for (int i = 0; i < check; i++) {
				Class<?>[] targetArgs = target.getParameterTypes();
				if (targetArgs.length != sourceArgs.length) {
					return false;
				}
				else {
					int matchesType = 0;
					for (int j = 0; j < sourceArgs.length; j++) {
						if (targetArgs[j].equals(sourceArgs[j])) {
							matchesType++;
						}
					}
					if (matchesType != sourceArgs.length) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Thực thi trả về các {@link Field} tương ứng với <code>object class</code>
	 * cung cấp tương ứng.
	 * <p>
	 * <i>Method này thực thi tương đương
	 * {@link ReflectUtils#fields(String, Class, boolean)} với</i> giá trị
	 * boolean (<code>'superClass'</code>) <i>chỉ định là <b>false</b></i>. Điều này có nghĩa rằng
	 * danh sách tìm kiếm sẽ chỉ bao gồm các <code>fields</code> được khai báo
	 * trong phạm vi <code>Class</code> tương ứng, không bao gồm
	 * <code>superClass</code> của Class chỉ định .
	 * <p>
	 * Lưu ý rằng phương thức này sẽ chỉ trả về các
	 * <code>non-static fields</code>.
	 * <p>
	 * - Có thể sử dụng <code>Regular Expression</code> để chỉ định tên
	 * {@link Field} trả về.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>field("str*")</code> sẽ chỉ định trả về tất cả các {@link Field}
	 * bắt đầu với chuỗi "str" hoặc
	 * <p>
	 * <code>field("?name")</code> sẽ chỉ định trả về tất cả các {@link Field}
	 * bắt đầu với 1 chữ bất kì và theo sau là chuỗi 'name'.
	 * 
	 * @param name
	 *            tên định danh của field
	 * @param clazz
	 *            object class chỉ định tương ứng.
	 * @return trả về một mảng các {@link Field} tương ứng tìm được nếu có, nếu
	 *         không trả về một danh sách rỗng.
	 */
	public static Field[] fields(String name, Class<?> clazz) {

		return fields(name, clazz, false);
	}

	/**
	 * Thực thi trả về các {@link Field} tương ứng với <code>object class</code>
	 * cung cấp tương ứng.
	 * <p>
	 * Lưu ý rằng phương thức này sẽ chỉ trả về các
	 * <code>non-static fields</code>.
	 * <p>
	 * - Có thể sử dụng <code>Regular Expression</code> để chỉ định tên
	 * {@link Field} trả về.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>field("str*")</code> sẽ chỉ định trả về tất cả các {@link Field}
	 * bắt đầu với chuỗi "str" hoặc
	 * <p>
	 * <code>field("?name")</code> sẽ chỉ định trả về tất cả các {@link Field}
	 * bắt đầu với 1 chữ bất kì và theo sau là chuỗi "name".
	 * 
	 * @param name
	 *            tên định danh của field
	 * @param clazz
	 *            object class tương ứng
	 * @param superClass
	 *            nếu chỉ định là <b>true</b>, danh sách các <code>fields</code>
	 *            tìm kiếm sẽ bao gồm cả các <code>fields</code> được khai báo
	 *            bởi <code>superClass</code> của Class hiện hành. Ngược lại,
	 *            nếu chỉ định là <b>false</b>, danh sách tìm kiếm chỉ nằm trong
	 *            phạm vi các <code>fields</code> được khai báo trong
	 *            <code>Class</code> tương ứng.
	 * @return trả về một mảng các {@link Field} tương ứng tìm được nếu có, nếu
	 *         không trả về một danh sách rỗng.
	 */
	public static Field[] fields(String name, Class<?> clazz, boolean superClass) {

		List<Field> lst = new ArrayList<Field>();
		Field[] fields = null;
		if (superClass) {
			fields = ReflectUtils.getDeclaredFields(clazz, false, true);
		}
		else {
			fields = clazz.getDeclaredFields();
		}
		for (Field field : fields) {
			if (RegularToolkit.matches(name, field.getName())) {
				lst.add(field);
			}
		}
		return lst.toArray(new Field[lst.size()]);
	}

	/**
	 * Returns the all declared fields. The fields are added to the end of the
	 * given list and are guaranteed to be non-static.
	 * 
	 * @param clazz
	 *            the given object class
	 * @return an array containing all declared fields.
	 */
	public static Field[] getAllDeclaredFields(Class<?> clazz) {

		List<Field> result = new LinkedList<Field>();
		result.addAll(Arrays.asList(getDeclaredFields(clazz, false, false)));
		return result.toArray(new Field[result.size()]);
	}

	/**
	 * Returns the all declared methods. The methods are added to the end of the
	 * given list and are guaranteed to be non-static. The returned methods will
	 * be included all the methods occur in the given class, its superclass and
	 * all its implemented interface.
	 * 
	 * @param clazz
	 *            the given object class
	 * @return an array containing all declared methods.
	 */
	public static Method[] getAllDeclaredMethods(Class<?> clazz) {

		List<Method> result = new LinkedList<Method>();
		result.addAll(Arrays.asList(getDeclaredMethods(clazz, false, false)));
		Set<Class<?>> interfaces = getAllInterfaces(clazz, true);
		for (Class<?> interfaze : interfaces) {
			Method[] methods = getDeclaredMethods(interfaze, false, false);
			for (Method target : methods) {
				result.add(target);
			}
		}
		return result.toArray(new Method[result.size()]);
	}

	/**
	 * Returns the all {@link Type Types} representing the interfaces
	 * implemented by the class or interface represented by given object class.
	 * 
	 * @param clazz
	 *            the given object class
	 * @param superClass
	 *            if <b>true</b>, the result will includes all types of super
	 *            classes and super interfaces of given object class.
	 * @return an {@link ArrayList} containing returned types if they exist,
	 *         otherwise, returns an empty {@link ArrayList}.
	 */
	public static ArrayList<Type> getAllGenericInterfaces(Class<?> clazz,
			boolean superClass) {

		Assertor.notNull(clazz);
		ArrayList<Type> result = new ArrayList<Type>();
		for (Type type : clazz.getGenericInterfaces()) {
			if (!result.contains(type)) {
				int i = 0;
				for (Type resultType : result) {
					if (resultType.toString().equals(type.toString())) {
						i++;
					}
				}
				if (i == 0) {
					result.add(type);
				}
			}
		}
		// Tìm trên tất cả các interfaces của clazz hiện hành
		Set<Class<?>> listInterfaces = ReflectUtils.getAllInterfaces(clazz,
				superClass);
		for (Class<?> interfaze : listInterfaces) {
			for (Type type : interfaze.getGenericInterfaces()) {
				if (!result.contains(type)) {
					int i = 0;
					for (Type resultType : result) {
						if (resultType.toString().equals(type.toString())) {
							i++;
						}
					}
					if (i == 0) {
						result.add(type);
					}
				}
			}
		}
		// Tìm trên tất cả các superClass của clazz hiện hành
		List<Class<?>> listClass = ReflectUtils.getAllSuperClass(clazz, false);
		for (Class<?> current : listClass) {
			for (Type type : current.getGenericInterfaces()) {
				if (!result.contains(type)) {
					int i = 0;
					for (Type resultType : result) {
						if (resultType.toString().equals(type.toString())) {
							i++;
						}
					}
					if (i == 0) {
						result.add(type);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns all interfaces implemented by the class or interface represented
	 * by the given class.
	 * <p>
	 * <b>Note:</b> Each interface signature will only occur once, even if it is
	 * implemented in multiple classes or interfaces.
	 * 
	 * @param clazz
	 *            the given class
	 * @param superClass
	 *            if specifies <code>true</code>, returning {@link Set} of
	 *            interfaces will be included all interfaces implemented by
	 *            super class of the given class, otherwise, if specifies
	 *            <code>false</code>, the returned interfaces will be found only
	 *            on the given class.
	 * @return an {@link Set} of interfaces implemented by the given class.
	 */
	public static Set<Class<?>> getAllInterfaces(Class<?> clazz,
			boolean superClass) {

		Set<Class<?>> temp = new LinkedHashSet<Class<?>>();
		temp.addAll(Arrays.asList(clazz.getInterfaces()));
		Set<Class<?>> result = new LinkedHashSet<Class<?>>();
		while (temp.size() != 0) {
			for (Class<?> obj : temp) {
				if (!result.contains(obj)) {
					result.add(obj);
				}
			}
			Set<Class<?>> current = new LinkedHashSet<Class<?>>();
			current.addAll(temp);
			temp.clear();
			for (Class<?> obj : current) {
				temp.addAll(Arrays.asList(obj.getInterfaces()));
			}
		}
		if (superClass == true) {
			List<Class<?>> superClassList = ReflectUtils.getAllSuperClass(
					clazz, false);
			for (Class<?> scl : superClassList) {
				Set<Class<?>> sclInterfaces = ReflectUtils.getAllInterfaces(
						scl, false);
				for (Class<?> sclinterface : sclInterfaces) {
					if (!result.contains(sclinterface)) {
						result.add(sclinterface);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Returns all super class of the given class.
	 * 
	 * @param clazz
	 *            the given object class.
	 * @param includesObjectClass
	 *            if specifies <b>true</b>, the returned {@link List} will be
	 *            inclued {@link Object} class, otherwise, specifies
	 *            <b>false</b>
	 * @return an {@link List} of {@link Class Classes} if they're existed, if
	 *         not, returns an empty {@link List}.
	 */
	public static List<Class<?>> getAllSuperClass(Class<?> clazz,
			boolean includesObjectClass) {

		List<Class<?>> result = new LinkedList<Class<?>>();
		Class<?> temp = clazz.getSuperclass();
		while (temp != null) {
			if (includesObjectClass == false) {
				if (temp == Object.class) {
					break;
				}
			}
			result.add(temp);
			temp = temp.getSuperclass();
		}
		return result;
	}

	/**
	 * Returns the object class types of the given objects.
	 * 
	 * @param objects
	 *            a object or a set of objects need to be taken class type.
	 * @return returns an array containing corresponding class types.
	 */
	public static Class<?>[] getClassTypeOf(Object... objects) {

		Assertor.notNull(objects);
		Class<?>[] result = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; i++) {
			Assertor.notNull(objects[i]);
			result[i] = objects[i].getClass();
		}
		return result;
	}

	/**
	 * Returns all {@link Field fields} occurs in the given class and its super
	 * classes. Each field signature will only occur once, even if it occurs in
	 * multiple classes and the returned array is only included non-static
	 * fields.
	 * 
	 * @param clazz
	 *            the given class
	 * @return an array containing all desired fields.
	 */
	public static Field[] getDeclaredFields(Class<?> clazz) {

		return getDeclaredFields(clazz, false, true);
	}

	/**
	 * Returns all {@link Field fields} occurs in the given class and its super
	 * classes.
	 * 
	 * @param clazz
	 *            the given class
	 * @param includeStatic
	 *            if specifies <b>true</b>, the returned array is only included
	 *            static fields, otherwise, if specifies <b>false</b>, only
	 *            non-static fields are returned.
	 * @param non_multiple
	 *            if specifies <b>true</b>, each field signature will only occur
	 *            once, even if it occurs in multiple classes, otherwise,
	 *            specifies <b>false</b>.
	 * @return an array containing all desired fields.
	 */
	public static Field[] getDeclaredFields(Class<?> clazz,
			boolean includeStatic, boolean non_multiple) {

		List<Field> accum = new LinkedList<Field>();
		while (clazz != null && clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				int modifiers = fields[i].getModifiers();
				if (includeStatic == Modifier.isStatic(modifiers)
						&& !accum.contains(fields[i])) {
					if (non_multiple) {
						int check = 0;
						for (Field field : accum) {
							if (equals(field, fields[i])) {
								check++;
							}
						}
						if (check == 0)
							accum.add(fields[i]);
					}
					else
						accum.add(fields[i]);
				}
			}
			clazz = clazz.getSuperclass();
		}
		Field[] retvalue = new Field[accum.size()];
		return (Field[]) accum.toArray(retvalue);
	}

	/**
	 * Returns all {@link Method methods} occurs in the given class and its
	 * super classes. Each method signature will only occur once, even if it
	 * occurs in multiple classes and the returned array is only included
	 * non-static methods.
	 * 
	 * @param clazz
	 *            the given class
	 * @return an array containing all desired methods
	 */
	public static Method[] getDeclaredMethods(Class<?> clazz) {

		return getDeclaredMethods(clazz, false, true);
	}

	/**
	 * Returns all {@link Method methods} occurs in the given class and its
	 * super classes.
	 * 
	 * @param clazz
	 *            the given class
	 * @param includeStatic
	 *            if specifies <b>true</b>, the returned array is only included
	 *            static methods, otherwise, if specifies <b>false</b>, only
	 *            non-static methods are returned.
	 * @param non_multiple
	 *            if specifies <b>true</b>, each method signature will only
	 *            occur once, even if it occurs in multiple classes, otherwise,
	 *            specifies <b>false</b>.
	 * @return an array containing all desired methods
	 */
	public static Method[] getDeclaredMethods(Class<?> clazz,
			boolean includeStatic, boolean non_multiple) {

		List<Method> accum = new LinkedList<Method>();
		while (clazz != null && clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				int modifiers = methods[i].getModifiers();
				if (includeStatic == Modifier.isStatic(modifiers)
						&& !accum.contains(methods[i])) {
					if (non_multiple) {
						int check = 0;
						for (Method method : accum) {
							if (equals(method, methods[i])) {
								check++;
							}
						}
						if (check == 0)
							accum.add(methods[i]);
					}
					else
						accum.add(methods[i]);
				}
			}
			clazz = clazz.getSuperclass();
		}
		Method[] retvalue = new Method[accum.size()];
		return (Method[]) accum.toArray(retvalue);
	}

	/**
	 * Returns the default {@link ClassLoader}.
	 * 
	 * @return {@link ClassLoader}
	 */
	public static ClassLoader getDefaultClassLoader() {

		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			if (log.isFatalEnabled()) {
				log.fatal("Cannot access thread context ClassLoader "
						+ "- falling back to system class loader", ex);
			}
		}
		if (cl == null) {
			cl = ReflectUtils.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * Returns the first <b>abstract</b> super class of the given class.
	 * 
	 * @param clazz
	 *            the given class
	 * @return returns the object class of the first abstract super class of the
	 *         given class if it exists, if not, reuturns <code>null</code>
	 */
	public static Class<?> getFirstAbstractSuperClass(Class<?> clazz) {

		Class<?> result = clazz.getSuperclass();
		while (result != null) {
			if (!Modifier.isAbstract(result.getModifiers())) {
				result = result.getSuperclass();
			}
			else {
				break;
			}
		}
		return result;
	}

	/**
	 * Tìm kiếm và trả về field tương ứng trong một class chỉ định. <br>
	 * <br>
	 * <b>Lưu ý:</b> Hàm sẽ tự động tìm trong <code>object class</code> chỉ
	 * định, nếu không có sẽ tìm tìm tiếp trong các <code>SuperClass</code> của
	 * nó.
	 * 
	 * @param cls
	 *            đối tượng <code>object class</code> truy vấn
	 *            <code>field</code>.
	 * @param name
	 *            tên của field cần tìm.
	 * @return trả về field tìm được.
	 * @throws NoSuchFieldException
	 *             ném ra ngoại lệ nếu như không có field nào trong object class
	 *             chỉ định ứng với tên name cung cấp.
	 */
	public static Field getSupportedField(Class<?> cls, String name)
			throws NoSuchFieldException {

		if (cls != null) {
			try {
				return cls.getDeclaredField(name);
			}
			catch (NoSuchFieldException e) {
				return getSupportedField(cls.getSuperclass(), name);
			}
		}
		else {
			throw new NoSuchFieldException("Could not find the field ['" + name
					+ "'] !");
		}
	}

	/**
	 * Trả về <code>declared method</code> có support trong <code>clazz</code>
	 * được chỉ định dựa trên tên <code>name</code> và <code>paramTypes</code>.
	 * Nếu <code>class</code> hiện tại không support Method cần tìm thì sẽ tìm
	 * trong <code>SuperClass</code> của <code>class</code> được chỉ định, nếu
	 * như không tồn tại bất kì <code>Method</code> nào tương ứng, một ngoại lệ
	 * {@link NoSuchMethodException} sẽ được ném ra.
	 * 
	 * @param clazz
	 *            Class Object cần truy vấn.
	 * @param name
	 *            tên method cần truy vấn.
	 * @param paramTypes
	 *            mảng array các Class Object Type tham số truyền của method.
	 * @return trả về Method muốn truy vấn
	 * @throws NoSuchMethodException
	 *             ném ra ngoại lệ này nếu như không tìm thấy bất kì method nào
	 *             tương ứng có trong clazz hoặc trong các SuperClass của nó.
	 */
	public static Method getSupportedMethod(Class<?> clazz, String name,
			Class<?>[] paramTypes) throws NoSuchMethodException {

		if (clazz == null) {
			throw new NoSuchMethodException("Method '" + name
					+ "' is not existed.");
		}
		try {
			return clazz.getDeclaredMethod(name, paramTypes);
		}
		catch (NoSuchMethodException ex) {
			return getSupportedMethod(clazz.getSuperclass(), name, paramTypes);
		}
	}

	/**
	 * Invokes the given <code>method</code> basing on the given
	 * <code>target object</code>.
	 * 
	 * @param obj
	 *            the target object
	 * @param methodName
	 *            the method name
	 * @param paramTypes
	 *            the parameter types array
	 * @param args
	 *            the arguments used for the method call
	 * @param superBool
	 *            nếu là <b>true</b> sẽ truy tìm phương thức trên
	 *            <code>Object Class</code> và cả <code>SuperClass</code> của
	 *            <code>object</code> chỉ định tương ứng , ngược lại nếu là
	 *            <b>false</b> sẽ chỉ truy vấn trên <code>Object Class</code>
	 *            chỉ định.
	 * @param noThrows
	 *            nếu là <b>true</b> sẽ không ném ra <code>exception</code> nếu
	 *            như gặp lỗi trong lúc thi hành, ngược lại nếu là <b>false</b>
	 *            sẽ ném ra ngoại lệ nếu gặp lỗi trong lúc thi hành. Lưu ý rằng
	 *            tham số <code>noThrows</code> chỉ có hiệu lực trên các tiến
	 *            trình <code>reflect</code> lúc <code>invoke method</code>, nếu
	 *            bản thân <code>method</code> được <code>invoked</code> ném ra
	 *            <code>exception</code> thì có chỉ định tường minh
	 *            <code>noThrows</code> hay không đều không có tác dụng.
	 * @return trả về <code>Object</code> trả về sau khi invoke method, nếu
	 *         <code>method</code> cần triệu gọi không <code>return</code> thì
	 *         kết quả trả về sẽ là <b>NULL</b>.
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 * @throws IllegalArgumentException
	 */
	public static Object invokeMethod(Object obj, String methodName,
			Class<?>[] paramTypes, Object[] args, boolean superBool,
			boolean noThrows) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {

		Class<?> clazz = null;
		if (ReflectUtils.isAnnotation(obj)) {
			clazz = ((Annotation) obj).annotationType();
		}
		clazz = obj.getClass();
		/*
		 * Kiểm tra thông tin hợp lệ của parameters
		 */
		if (paramTypes != null && args != null) {
			if (paramTypes.length != args.length) {
				throw new IllegalArgumentException("Parameter is invalid ! ");
			}
		}
		try {
			if (superBool == true) {
				Method method = ReflectUtils.getSupportedMethod(clazz,
						methodName, paramTypes);
				method.setAccessible(true);
				return method.invoke(obj, args);
			}
			else {
				return clazz.getDeclaredMethod(methodName, paramTypes).invoke(
						obj, args);
			}
		}
		catch (NoSuchMethodException ex) {
			if (noThrows == false) {
				throw new NoSuchMethodException(clazz.getName()
						+ " does not support method " + methodName);
			}
		}
		catch (IllegalAccessException ex) {
			if (noThrows == false) {
				throw new IllegalAccessException(
						"Insufficient access permissions to call" + methodName
								+ " in class " + clazz.getName());
			}
		}
		catch (InvocationTargetException ex) {
			throw ex;
		}
		return null;
	}

	/**
	 * Returns <b>true</b> if the given object is an annotation implementation.
	 * 
	 * @param obj
	 *            the given object need to be tested.
	 * @return <b>true</b> if the given object is an annotation implementation,
	 *         <b>false</b> otherwise.
	 */
	public static boolean isAnnotation(Object obj) {

		Assertor.notNull(obj);
		try {
			Annotation.class.cast(obj);
		}
		catch (RuntimeException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns <b>true</b> if given object can be casted to the class or
	 * interface type represents by the given Class object. Otherwise returns
	 * <b>false</b>.
	 * 
	 * @param clazz
	 *            the object class
	 * @param obj
	 *            the object need to be checked.
	 * @return true, if checks if is cast
	 */
	public static boolean isCast(Class<?> clazz, Object obj) {

		Assertor.notNull(clazz);
		Assertor.notNull(obj);
		try {
			clazz.cast(obj);
		}
		catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns <b>true</b> if given object can be casted to all object classes
	 * of given list. If has just any object class can not be casted, returns
	 * <b>false</b>.
	 * 
	 * @param obj
	 *            the object need to be checked.
	 * @param classes
	 *            the list of classes
	 * @return true, if checks if is cast
	 */
	public static boolean isCast(Object obj, Class<?>... classes) {

		Assertor.notNull((Object[]) classes);
		Assertor.notNull(obj);
		if (classes.length == 0) {
			Assertor
					.throwRunTimeException("The list of classes need to be casted must not be empty!");
		}
		try {
			for (Class<?> clazz : classes) {
				clazz.cast(obj);
			}
		}
		catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns <b>true</b> if given object is a {@link Class}, <b>false</b>
	 * otherwise.
	 * 
	 * @param obj
	 *            given object need to be tested
	 * @return true, if checks if is class
	 */
	public static boolean isClass(Object obj) {

		Assertor.notNull(obj);
		try {
			Class.class.cast(obj);
		}
		catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns <b>true</b> if given object is a {@link Constructor},
	 * <b>false</b> otherwise.
	 * 
	 * @param obj
	 *            given object need to be tested
	 * @return true, if checks if is constructor
	 */
	public static boolean isConstructor(Object obj) {

		return isCast(Constructor.class, obj);
	}

	/**
	 * Determine whether the given method explicitly declares the given
	 * exception or one of its superclasses, which means that an exception of
	 * that type can be propagated as-is within a reflective invocation.
	 * 
	 * @param method
	 *            <the declaring method
	 * @param exceptionType
	 *            the exception to throw
	 * @return <code>true</code> if the exception can be thrown as-is;
	 *         <code>false</code> if it needs to be wrapped
	 */
	@SuppressWarnings("unchecked")
	public static boolean isDeclaredException(Method method,
			Class<? extends Throwable> exceptionType) {

		Assertor.notNull(method, "Method [" +
				method+"] must not be null.");
		Class<? extends Throwable>[] declaredExceptions = (Class<? extends Throwable>[]) method
				.getExceptionTypes();
		for (int i = 0; i < declaredExceptions.length; i++) {
			Class<? extends Throwable> declaredException = declaredExceptions[i];
			if (declaredException.isAssignableFrom(exceptionType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns <b>true</b> if given object is a {@link Field}, <b>false</b>
	 * otherwise.
	 * 
	 * @param obj
	 *            given object need to be tested
	 * @return true, if checks if is field
	 */
	public static boolean isField(Object obj) {

		if (obj.getClass().equals(Field.class)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns <b>true</b> if given object is a interface, <b>false</b>
	 * otherwise.
	 * 
	 * @param obj
	 *            given object need to be tested
	 * @return true, if checks if is interface
	 */
	public static boolean isInterface(Object obj) {

		Assertor.notNull(obj);
		return obj.getClass().isInterface();
	}

	/**
	 * Returns <b>true</b> if given object is a {@link Method}, <b>false</b>
	 * otherwise.
	 * 
	 * @param obj
	 *            given object need to be tested
	 * @return true, if checks if is method
	 */
	public static boolean isMethod(Object obj) {

		if (obj.getClass().equals(Method.class)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns <b>true</b> if given {@link Field} is <b>public, static,
	 * final</b>, otherwise returns <b>false</b>.
	 * 
	 * @param field
	 *            given {@link Field} need to be tested
	 * @return true, if checks if is public static final
	 */
	public static boolean isPublicStaticFinal(Field field) {

		int modifiers = field.getModifiers();
		return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)
				&& Modifier.isFinal(modifiers);
	}

	/**
	 * Returns <b>true</b> if given {@link Method} is <b>public, static,
	 * final</b>, otherwise returns <b>false</b>.
	 * 
	 * @param method
	 *            given {@link Method} need to be tested
	 * @return true, if checks if is public static final
	 */
	public static boolean isPublicStaticFinal(Method method) {

		int modifiers = method.getModifiers();
		return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier
				.isFinal(modifiers));
	}

	/**
	 * Phương thức sẽ kiểm tra xem <code>annotation</code> chỉ định có
	 * {@link Target} tương ứng hợp lệ với đối tượng <code>object</code> hay
	 * không (đối tượng <code>object</code> có thể là bất kì thực thể chỉ định
	 * nào cho phép annotate annotation như
	 * <code>Class, Interface, Annotation, Enum, Method, Field, Constructor, ...</code>
	 * )
	 * 
	 * @param annotation
	 *            đối tượng <code>annotation</code> cần kiểm tra.
	 * @param obj
	 *            đối tượng <code>object</code> cần kiểm tra
	 * @return trả về <b>true</b> nếu có, nếu không trả về <b>false</b>.
	 */
	public static boolean isValidTarget(Annotation annotation, Object obj) {

		if (annotation.annotationType().isAnnotationPresent(Target.class)) {
			Target target = annotation.annotationType().getAnnotation(
					Target.class);
			if (obj.getClass().isAnnotation()) {
				if (!Arrays.asList(target.value()).contains(ElementType.TYPE)
						|| !Arrays.asList(target.value()).contains(
								ElementType.ANNOTATION_TYPE)) {
					return false;
				}
			}
			else if (ReflectUtils.isCast(Constructor.class, obj)) {
				if (!Arrays.asList(target.value()).contains(
						ElementType.CONSTRUCTOR)) {
					return false;
				}
			}
			else if (ReflectUtils.isField(obj)) {
				if (!Arrays.asList(target.value()).contains(ElementType.FIELD)) {
					return false;
				}
			}
			else if (ReflectUtils.isMethod(obj)) {
				if (!Arrays.asList(target.value()).contains(ElementType.METHOD)) {
					return false;
				}
			}
			else if (ReflectUtils.isClass(obj) || ReflectUtils.isInterface(obj)) {
				if (!Arrays.asList(target.value()).contains(ElementType.TYPE)) {
					return false;
				}
			}
			else {
				if (!obj.getClass().isAnnotation()
						&& !ReflectUtils.isCast(Constructor.class, obj)
						&& !ReflectUtils.isField(obj)
						&& !ReflectUtils.isMethod(obj.getClass())
						&& !ReflectUtils.isClass(obj)
						&& !ReflectUtils.isInterface(obj)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Make the given constructor accessible, explicitly setting it accessible
	 * if necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param ctor
	 *            the constructor to make accessible
	 * @see java.lang.reflect.Constructor#setAccessible(boolean)
	 */
	public static void makeAccessible(Constructor<?> ctor) {

		if (!Modifier.isPublic(ctor.getModifiers())
				|| !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) {
			ctor.setAccessible(true);
		}
	}

	/**
	 * Make the given field accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param field
	 *            the field to make accessible
	 * @see java.lang.reflect.Field#setAccessible(boolean)
	 */
	public static void makeAccessible(Field field) {

		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * Make the given method accessible, explicitly setting it accessible if
	 * necessary. The <code>setAccessible(true)</code> method is only called
	 * when actually necessary, to avoid unnecessary conflicts with a JVM
	 * SecurityManager (if active).
	 * 
	 * @param method
	 *            the method to make accessible
	 * @see java.lang.reflect.Method#setAccessible(boolean)
	 */
	public static void makeAccessible(Method method) {

		if (!Modifier.isPublic(method.getModifiers())
				|| !Modifier
						.isPublic(method.getDeclaringClass().getModifiers())) {
			method.setAccessible(true);
		}
	}

	/**
	 * Returns an array containing method objects that corresponds to the given
	 * name (or regular expression pattern of name)
	 * <p>
	 * <b>Note:</b>
	 * <p>
	 * - This method only returns <code>non-static methods</code>.<br>
	 * - This method result is the same as
	 * {@link #methods(String, Class, boolean)} according to
	 * <code>superClass</code> argument is <code>false</code>
	 * <p>
	 * - <code>Regular expression</code> can be used in order to specify a set
	 * of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>methods("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @param clazz
	 *            the declaring class
	 * @return an array containing method objects if they exist, if not, return
	 *         an empty array.
	 */
	public static Method[] methods(String name, Class<?> clazz) {

		return methods(name, clazz, false);
	}

	/**
	 * Returns an array containing method objects that corresponds to the given
	 * name (or regular expression pattern of name)
	 * <p>
	 * <b>Note:</b>
	 * <p>
	 * - This method only returns <code>non-static methods</code>.
	 * <p>
	 * - <code>Regular expression</code> can be used in order to specify a set
	 * of returned methods.
	 * <p>
	 * <b>EX:</b>
	 * <p>
	 * <code>method("set*")</code> shall specify to return all of setter
	 * methods.
	 * 
	 * @param name
	 *            the name (or regular expression pattern) of method or a set of
	 *            methods.
	 * @param clazz
	 *            the declaring class
	 * @param superClass
	 *            if is specified to be <b>true</b>, the returned method list
	 *            includes method objects are declared in current derived class
	 *            and also in super class of it. Otherwise, if is specified to
	 *            be <b>false</b>, the returned method list only includes method
	 *            objects are declared in current derived class, exclude super
	 *            classes of it.
	 * @return an array containing method objects if they exist, if not, return
	 *         an empty array.
	 */
	public static Method[] methods(String name, Class<?> clazz,
			boolean superClass) {

		List<Method> lst = new ArrayList<Method>();
		Method[] methods = null;
		if (superClass) {
			methods = ReflectUtils.getDeclaredMethods(clazz, false, true);
		}
		else {
			methods = clazz.getDeclaredMethods();
		}
		for (Method method : methods) {
			if (RegularToolkit.matches(name, method.getName())) {
				lst.add(method);
			}
		}
		return lst.toArray(new Method[lst.size()]);
	}
}
