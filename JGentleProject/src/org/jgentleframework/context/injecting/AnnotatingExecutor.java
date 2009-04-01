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
package org.jgentleframework.context.injecting;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;

import org.jgentleframework.configure.AnnotatingRuntimeException;
import org.jgentleframework.configure.TemplateClass;
import org.jgentleframework.configure.annotation.Annotate;
import org.jgentleframework.configure.enums.Types;
import org.jgentleframework.configure.objectmeta.ObjectAnnotating;
import org.jgentleframework.core.handling.DefinitionManager;
import org.jgentleframework.core.reflection.Identification;
import org.jgentleframework.core.reflection.metadata.Definition;
import org.jgentleframework.utils.ReflectUtils;

/**
 * This class represents loading {@link Definition} performance from
 * <code>annotating configuration data</code>.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Jun 27, 2008
 * @See {@link Annotate}
 * @See {@link ObjectAnnotating}
 */
public class AnnotatingExecutor {
	/** The definition manager. */
	DefinitionManager					definitionManager	= null;

	/** The AbstractBeanFactory instance. */
	AbstractBeanFactory					abf					= null;

	/** The obj instance. */
	private static AnnotatingExecutor	objInstance			= null;

	/**
	 * Constructor.
	 * 
	 * @param abf
	 *            the abf
	 */
	private AnnotatingExecutor(AbstractBeanFactory abf) {

		this.abf = abf;
		this.definitionManager = abf.getDefinitionManager();
	}

	/**
	 * Singleton static method.
	 * 
	 * @param abf
	 *            {@link AbstractBeanFactory} object.
	 * @return the annotating executor
	 */
	public synchronized static AnnotatingExecutor singleton(
			AbstractBeanFactory abf) {

		if (objInstance == null) {
			objInstance = new AnnotatingExecutor(abf);
		}
		return objInstance;
	}

	/**
	 * Loading single value.
	 * 
	 * @param singleKey
	 *            the single key
	 * @param singleValue
	 *            the single value
	 * @param ID
	 *            the iD
	 * @param inClass
	 *            the in class
	 * @param annotateIDList
	 *            the annotate id list
	 */
	private void loadingValue(Object singleKey, Object singleValue, String ID,
			Class<?> inClass, List<Object> annotateIDList) {

		Definition defID = this.definitionManager.getDefinition(ID);
		Definition def = defID != null ? defID.getMemberDefinition(singleKey)
				: null;
		if (def == null || !def.isAnnotationPresent(Annotate.class)) {
			this.definitionManager.loadCustomizedDefinition(ID, singleKey,
					inClass, TemplateClass.getAnnotation(Annotate.class));
			defID = this.definitionManager.getDefinition(ID);
			def = defID.getMemberDefinition(singleKey);
		}
		if (ReflectUtils.isCast(String.class, singleValue)) {
			Object objAnno = this.abf.getBean((String) singleValue);
			if (objAnno != null
					&& ReflectUtils.isCast(Annotation.class, objAnno)) {
				this.definitionManager.loadCustomizedDefinition(ID, singleKey,
						inClass, (Annotation) objAnno);
			}
			else {
				Annotate anno = def.getAnnotation(Annotate.class);
				String[] vov = anno.value();
				if (vov == null || (vov.length == 1 && vov[0].isEmpty()))
					vov = new String[] { (String) singleValue };
				else {
					String[] vovNew = new String[vov.length + 1];
					for (int k = 0; k < vovNew.length; k++) {
						if (k != vovNew.length - 1)
							vovNew[k] = vov[k];
						else
							vovNew[k] = (String) singleValue;
					}
					vov = vovNew;
				}
				def.setValueOfAnnotation(Annotate.class, "value", vov);
				if (annotateIDList != null)
					annotateIDList.add(ID);
			}
		}
		else if (ReflectUtils.isCast(Annotation.class, singleValue)) {
			this.definitionManager.loadCustomizedDefinition(ID, singleKey,
					inClass, (Annotation) singleValue);
		}
		else {
			throw new AnnotatingRuntimeException(
					"Object reference is not a annotation instance!");
		}
	}

	/**
	 * Loading pair.
	 * 
	 * @param type
	 *            the type
	 * @param key
	 *            the key
	 * @param values
	 *            the values
	 * @param ID
	 *            the iD
	 * @param inClass
	 *            the in class
	 * @param annotateIDList
	 *            the annotate id list
	 */
	public void loadingPair(Types type, Identification<? extends Object> key,
			Object values, String ID, Class<?> inClass,
			List<Object> annotateIDList) {

		Object[] keyObjLst = key.getMember();
		for (Object singleKey : keyObjLst) {
			if (values.getClass().isArray()) {
				for (int j = 0; j < Array.getLength(values); j++) {
					Object singleValue = Array.get(values, j);
					loadingValue(singleKey, singleValue, ID, inClass,
							annotateIDList);
				}
			}
			else {
				loadingValue(singleKey, values, ID, inClass, annotateIDList);
			}
		}
	}
}
