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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

/**
 * The Class Serialize.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Aug 31, 2007
 */
public abstract class Serialize {
	/**
	 * Hàm này sẽ thực thi serialize một object chỉ định, chuyển Object thành
	 * kiểu Document của XML (JDom).
	 * 
	 * @param source
	 *            object nguồn chỉ định muốn serialize.
	 * @return Document
	 * @throws Exception
	 *             ném ra ngoại lệ này bất cứ khi nào quá trình serialize gặp
	 *             lỗi.
	 */
	public static Document serializeObject(Object source) throws Exception {

		return serializeHelper(source, new Document(new Element("serialized")),
				new IdentityHashMap<Object, Object>());
	}

	/**
	 * Thực thi việc serialize một object.
	 * 
	 * @param source
	 *            object nguồn chỉ định muốn serialize.
	 * @param target
	 *            Đối tượng Document dùng để lưu trữ thông tin các Element của
	 *            object dưới dạng XML
	 * @param table
	 *            Bảng Map chứa các giá trị thông tin về ID của các Object đã
	 *            được serialize.
	 * @return Trả về đối tượng Document chứa thông tin đã được serialize.
	 * @throws Exception
	 *             ném ra ngoại lệ bất cứ khi nào quá trình serialize gặp lỗi.
	 */
	private static Document serializeHelper(Object source, Document target,
			Map<Object, Object> table) throws Exception {

		String id = Integer.toString(table.size());
		table.put(source, id);
		Class<?> sourceClass = source.getClass();
		Element oElt = new Element("object");
		oElt.setAttribute("class", sourceClass.getName());
		oElt.setAttribute("id", id);
		target.getRootElement().addContent(oElt);
		if (!sourceClass.isArray()) {
			Field[] fields = ReflectUtils.getDeclaredFields(sourceClass, false,
					true);
			for (int i = 0; i < fields.length; i++) {
				if (!Modifier.isPublic(fields[i].getModifiers()))
					fields[i].setAccessible(true);
				Element fElt = new Element("field");
				fElt.setAttribute("name", fields[i].getName());
				Class<?> declClass = fields[i].getDeclaringClass();
				fElt.setAttribute("declaringclass", declClass.getName());
				Class<?> fieldtype = fields[i].getType();
				Object child = fields[i].get(source);
				if (Modifier.isTransient(fields[i].getModifiers())) {
					child = null;
				}
				fElt.addContent(Serialize.serializeVariable(fieldtype, child,
						target, table));
				oElt.addContent(fElt);
			}
		}
		else {
			Class<?> componentType = sourceClass.getComponentType();
			int length = Array.getLength(source);
			oElt.setAttribute("length", Integer.toString(length));
			for (int i = 0; i < length; i++) {
				oElt.addContent(Serialize.serializeVariable(componentType,
						Array.get(source, i), target, table));
			}
		}
		return target;
	}

	/**
	 * Hàm thực thi việc serialize các child object là variable của một object
	 * được chỉ định serialize.
	 * 
	 * @param fieldType
	 *            kiểu type của object cần serialize
	 * @param child
	 *            object cần serialize
	 * @param target
	 *            đối tượng Document target dùng để cất trữ thông tin object sau
	 *            khi được serialize.
	 * @param table
	 *            bảng Map chứa các giá trị thông tin về ID của các Object đã
	 *            được serialize.
	 * @return trả về một Element tượng trưng cho child object vừa được
	 *         serialize.
	 * @throws Exception
	 *             ném ra ngoại lệ bất cứ khi nào quá trình serialize gặp lỗi.
	 */
	private static Element serializeVariable(Class<?> fieldType, Object child,
			Document target, Map<Object, Object> table) throws Exception {

		if (child == null) {
			return new Element("null");
		}
		/* Nếu fieldType không phải là Primitive Type */
		else if (!fieldType.isPrimitive()) {
			Element reference = new Element("reference");
			/* Nếu child đã được serialize */
			synchronized (table) {
				if (table.containsKey(child)) {
					reference.setText(table.get(child).toString());
				}
				else {
					reference.setText(Integer.toString(table.size()));
					serializeHelper(child, target, table);
				}
			}
			return reference;
		}
		else {
			Element value = new Element("value");
			value.setText(child.toString());
			return value;
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		XMLOutputter out = new XMLOutputter();
		try {
			Document obj = Serialize.serializeObject(Serialize.class);
			out.output(obj, System.out);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deserialize object.
	 * 
	 * @param source
	 *            the source
	 * @return the object
	 * @throws Exception
	 *             the exception
	 */
	public static Object deserializeObject(Document source) throws Exception {

		List<?> objList = source.getRootElement().getChildren();
		Map<Object, Object> table = new HashMap<Object, Object>();
		createInstances(table, objList);
		assignFieldValues(table, objList);
		return table.get("0");
	}

	/**
	 * Creates the instances.
	 * 
	 * @param table
	 *            the table
	 * @param objList
	 *            the obj list
	 * @throws Exception
	 *             the exception
	 */
	private static void createInstances(Map<Object, Object> table,
			List<?> objList) throws Exception {

		for (int i = 0; i < objList.size(); i++) {
			Element oElt = (Element) objList.get(i);
			Class<?> cls = Class.forName(oElt.getAttributeValue("class"));
			Object instance = null;
			if (!cls.isArray()) {
				Constructor<?> c = cls.getDeclaredConstructor();
				if (!Modifier.isPublic(c.getModifiers())) {
					c.setAccessible(true);
				}
				instance = c.newInstance();
			}
			else {
				instance = Array.newInstance(cls.getComponentType(), Integer
						.parseInt(oElt.getAttributeValue("length")));
			}
			table.put(oElt.getAttributeValue("id"), instance);
		}
	}

	/**
	 * Assign field values.
	 * 
	 * @param table
	 *            the table
	 * @param objList
	 *            the obj list
	 * @throws Exception
	 *             the exception
	 */
	private static void assignFieldValues(Map<Object, Object> table,
			List<?> objList) throws Exception {

		for (int i = 0; i < objList.size(); i++) {
			Element oElt = (Element) objList.get(i);
			Object instance = table.get(oElt.getAttributeValue("id"));
			List<?> fElts = oElt.getChildren();
			if (!instance.getClass().isArray()) {
				for (int j = 0; j < fElts.size(); j++) {
					Element fElt = (Element) fElts.get(j);
					String className = fElt.getAttributeValue("declaringclass");
					Class<?> fieldDC = Class.forName(className);
					String fieldName = fElt.getAttributeValue("name");
					Field f = fieldDC.getDeclaredField(fieldName);
					if (!Modifier.isPublic(f.getModifiers())) {
						f.setAccessible(true);
					}
					Element vElt = (Element) fElt.getChildren().get(0);
					f.set(instance, deserializeValue(vElt, f.getType(), table));
				}
			}
			else {
				Class<?> comptype = instance.getClass().getComponentType();
				for (int j = 0; j < fElts.size(); j++) {
					Array.set(instance, j, deserializeValue((Element) fElts
							.get(j), comptype, table));
				}
			}
		}
	}

	/**
	 * Deserialize value.
	 * 
	 * @param vElt
	 *            the v elt
	 * @param fieldType
	 *            the field type
	 * @param table
	 *            the table
	 * @return the object
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private static Object deserializeValue(Element vElt, Class<?> fieldType,
			Map<Object, Object> table) throws ClassNotFoundException {

		String valtype = vElt.getName();
		if (valtype.equals("null")) {
			return null;
		}
		else if (valtype.equals("reference")) {
			return table.get(vElt.getText());
		}
		else {
			if (fieldType.equals(boolean.class)) {
				if (vElt.getText().equals("true")) {
					return Boolean.TRUE;
				}
				else {
					return Boolean.FALSE;
				}
			}
			else if (fieldType.equals(byte.class)) {
				return Byte.valueOf(vElt.getText());
			}
			else if (fieldType.equals(short.class)) {
				return Short.valueOf(vElt.getText());
			}
			else if (fieldType.equals(int.class)) {
				return Integer.valueOf(vElt.getText());
			}
			else if (fieldType.equals(long.class)) {
				return Long.valueOf(vElt.getText());
			}
			else if (fieldType.equals(float.class)) {
				return Float.valueOf(vElt.getText());
			}
			else if (fieldType.equals(double.class)) {
				return Double.valueOf(vElt.getText());
			}
			else if (fieldType.equals(char.class)) {
				return Character.valueOf((vElt.getText().charAt(0)));
			}
			else {
				return vElt.getText();
			}
		}
	}
}
