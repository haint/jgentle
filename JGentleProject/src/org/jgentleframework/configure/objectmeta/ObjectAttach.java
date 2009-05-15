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
package org.jgentleframework.configure.objectmeta;

import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.ConfigurationException;

import org.jgentleframework.configure.enums.Scope;
import org.jgentleframework.context.injecting.scope.ScopeInstance;
import org.jgentleframework.core.factory.InOutDependencyException;

/**
 * This interface represents an <code>ObjectBindingConstant</code> which is
 * responsible for mapping management. The specified information in
 * {@link ObjectAttach} includes all information of
 * <code>scope, lazy-init, wiring bean, ...</code> will be used in order to
 * instantiate bean.
 * 
 * @param <U>
 * @author SKYDUNKPRO - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Sep 17, 2007
 */
public interface ObjectAttach<U> extends IPresentLoadingClass {
	/**
	 * The Class ScopeObject.
	 * 
	 * @author LE QUOC CHUNG - mailto: <a
	 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
	 * @date Oct 5, 2007
	 */
	class ScopeObject {
		/** The entry set. */
		private Set<Entry<Class<?>, Class<?>>>	entrySet;

		/** The object attach. */
		private ObjectAttach<?>					objectAttach;

		/**
		 * The constructor.
		 * 
		 * @param objectAttach
		 *            the current {@link ObjectAttach}
		 * @param entrySet
		 *            the entry set
		 */
		public ScopeObject(ObjectAttach<?> objectAttach,
				Set<Entry<Class<?>, Class<?>>> entrySet) {

			this.objectAttach = objectAttach;
			this.entrySet = entrySet;
			for (Entry<Class<?>, Class<?>> element : entrySet) {
				objectAttach.getScopeList().put(element, Scope.SINGLETON);
			}
		}

		/**
		 * Gets the entry set.
		 * 
		 * @return the entrySet
		 */
		public Set<Entry<Class<?>, Class<?>>> getEntrySet() {

			return entrySet;
		}

		/**
		 * Gets the object attach.
		 * 
		 * @return the objectAttach
		 */
		public ObjectAttach<?> getObjectAttach() {

			return objectAttach;
		}

		/**
		 * Scope.
		 * 
		 * @param scope
		 *            the scope
		 */
		public void scope(ScopeInstance scope) {

			for (Entry<Class<?>, Class<?>> element : entrySet) {
				objectAttach.getScopeList().put(element, scope);
			}
		}

		/**
		 * set the scope to this current {@link ObjectAttach}.
		 * 
		 * @param entry
		 *            the entry
		 * @param scope
		 *            the scope
		 */
		public void setScope(Entry<Class<?>, Class<?>> entry,
				ScopeInstance scope) {

			if (entrySet.contains(entry)) {
				this.objectAttach.getScopeList().put(entry, scope);
			}
			else {
				throw new InOutDependencyException(
						"This entry of mapping is not existed.");
			}
		}
	}

	/**
	 * Adds a given class type to current {@link ObjectAttach}
	 * <p>
	 * <b>Note:</b>if current {@link ObjectAttach} is named and has more than
	 * one class type, that name will be setted to empty when this method is
	 * invoked
	 * 
	 * @param clazz
	 *            the object class type
	 * @return <b>true</b>, if success, <b>false</b> otherwise.
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public boolean addClassType(Class<?> clazz) throws ConfigurationException;

	/**
	 * Removes all current class types of current {@link ObjectAttach}
	 * <p>
	 * <b>Note:</b> all mapping of current {@link ObjectAttach} will also be
	 * removed when this method is invoked.
	 */
	public void clearAllClassType();

	/**
	 * Returns <b>true</b> if the given class type is added to current
	 * {@link ObjectAttach}
	 * 
	 * @param clazz
	 *            the class type need to be checked
	 */
	public boolean containsClassType(Class<?> clazz);

	/**
	 * Returns the number of all mapping
	 * 
	 * @return int
	 */
	public int countEntry();

	/**
	 * Returns a {@link Set} of all mapping of current {@link ObjectAttach}
	 */
	public Set<Entry<Class<?>, Class<?>>> getMappingEntrySet();

	/**
	 * Returns the specified name of current {@link ObjectAttach} if existed, if
	 * not, returns an empty String.
	 */
	public String getName();

	/**
	 * Gets the scope list.
	 * 
	 * @return the scopeList
	 */
	public HashMap<Entry<Class<?>, Class<?>>, ScopeInstance> getScopeList();

	/**
	 * Returns an array containing all existent class type of current
	 * {@link ObjectAttach}
	 */
	public Class<?>[] getClassTypeArray();

	/**
	 * Returns true if current {@link ObjectAttach} don't have any binding
	 */
	public boolean isEmptySourceList();

	/**
	 * Returns <b>true</b> if current {@link ObjectAttach} can be named, if not
	 * returns <b>false</b>.
	 */
	public boolean isNameBool();

	/**
	 * Removes a specified class type <br>
	 * <b>Note:</b> all binding appropriate to removed class type will also be
	 * removed.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return returns <b>true</b> if success, if not returns <b>false</b>.
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public boolean removeClassType(Class<?> clazz)
			throws ConfigurationException;

	/**
	 * Binds to a target class.
	 * 
	 * @param clazz
	 *            the clazz
	 * @return the scope object
	 */
	ScopeObject to(Class<? extends U> clazz);

	/**
	 * Binds to a specified target classes.
	 * 
	 * @param classes
	 *            the classes
	 * @return the scope object
	 */
	ScopeObject to(Class<?>... classes);

	/**
	 * Binds to a target class name or a group of target class names.
	 * 
	 * @param names
	 *            the names
	 * @return the scope object
	 */
	ScopeObject to(String... names);

	/**
	 * Binds a specified class type to a target class of current
	 * {@link ObjectAttach}.
	 * 
	 * @param type
	 *            the type
	 * @param targetClass
	 *            the target class
	 * @return the scope object
	 */
	public ScopeObject toDestinationClass(Class<?> type, Class<?> targetClass);

	/**
	 * Sets the unique name to the current binding. <br>
	 * <b>Note: </b> only effected when current binding has just only one
	 * mapping, if not, one runtime exception will be thrown
	 * 
	 * @param name
	 *            the name
	 * @return returns the current {@link ObjectAttach}
	 */
	public ObjectAttach<?> named(String name);

	/**
	 * Chỉ định thuộc tính lazy-init cho ánh xạ hiện hành. Thuộc tính
	 * <b>lazy_init</b> chỉ định cách thức khởi tạo của bean được định nghĩa bởi
	 * <code>configuration</code>. Mặc định <code>Context</code> sẽ tự động khởi
	 * tạo các instance bean khi và chỉ khi instance bean được get ra từ Context
	 * , tương đương thuộc tính <b>lazy_init</b> là <b>true</b>. Ngược lại nếu
	 * thuộc tính <b>lazy-init</b> là <b>false</b>, instance bean của
	 * <code>definition</code> đang xét sẽ tự động được khởi tạo ngay khi
	 * definition được nạp vào Context (JGentle container).<br>
	 * <br>
	 * <b>Chú ý:</b> Giá trị mặc định khi không chỉ định tường minh của thuộc
	 * tính là <b>true</b> và thuộc tính thực sự không có hiệu lực với các bean
	 * được chỉ định <b>scope</b> là <code>Prototype</code>.
	 * 
	 * @param lazyInit
	 *            giá trị boolean cần chỉ định.
	 * @return {@link ObjectAttach}
	 */
	public ObjectAttach<?> lazyInit(boolean lazyInit);

	/**
	 * Checks if is lazy_init.
	 * 
	 * @return the lazy_init
	 */
	public boolean isLazyInit();

	/**
	 * Sets the lazy_init.
	 * 
	 * @param lazyInit
	 *            the lazyInit
	 */
	public void setLazyInit(boolean lazyInit);
}
