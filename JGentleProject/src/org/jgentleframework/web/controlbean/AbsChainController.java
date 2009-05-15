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
package org.jgentleframework.web.controlbean;

/**
 * Lớp abstract mô tả một abstract handler đóng vai trò như một mắt xích trong
 * xâu xử lý request đến từ client.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Nov 20, 2007
 */
public abstract class AbsChainController {
	String				name;
	AbsChainController	successor;

	public AbsChainController(String name) {

		this.name = name;
	}

	/**
	 * Hàm xử lý yêu cầu request
	 * 
	 * @param objects
	 *            danh sách các objects tham số nếu có.
	 * @return trả về đối tượng object yêu cầu cần trong thực thi hệ thống nếu
	 *         có, nếu không trả về null.
	 */
	public abstract Object handling(Object... objects);

	/**
	 * Trả về tên định danh name của Controller hiện hành.
	 * 
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Thiết lập tên định danh name cho Controller hiện hành.
	 * 
	 * @param name
	 *            tên định danh cần thiết lập.
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Trả về đối tượng xử lý handler kế tiếp trong chuỗi chain cung ứng.
	 * 
	 * @return the successor
	 */
	public AbsChainController getSuccessor() {

		return successor;
	}

	/**
	 * Thiết lập đối tương xử lý handler kế tiếp trong chuỗi chain cung ứng.
	 * 
	 * @param successor
	 *            đối tượng cần thiết lập.
	 */
	public void setSuccessor(AbsChainController successor) {

		this.successor = successor;
	}
}
