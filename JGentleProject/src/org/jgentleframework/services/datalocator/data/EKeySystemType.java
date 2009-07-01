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
package org.jgentleframework.services.datalocator.data;

import java.util.LinkedList;

import org.jgentleframework.services.datalocator.enums.DataType;

/**
 * <code> enum EKeySystemType</code> miêu tả nhánh các <code>key system</code>
 * tồn tại trong hệ thống, đây là các <code>HKEY</code> cơ bản bắt buộc tồn tại
 * trong mọi hệ thống JGentle container.
 * 
 * @author LE QUOC CHUNG - mailto: <a
 *         href="mailto:skydunkpro@yahoo.com">skydunkpro@yahoo.com</a>
 * @date Mar 15, 2008
 */
public enum EKeySystemType {
	/***************************************************************************
	 * KHỞI TẠO HKEY_COMPONENTS_ROOT <br>
	 * <br>
	 * HKEY_COMPONENTS_ROOT chứa các thông tin về các thành phần hệ thống sẽ
	 * thực thi trong hệ thống, hoặc các thành phần hoạt động cần thiết (remote,
	 * ...) khi server hoạt động.
	 **************************************************************************/
	HKEY_COMPONENTS_ROOT (new Key[] {
			new KeyImpl<Object>("Incoming_Module", DataType.SYSTEM),
			new KeyImpl<Object>("Outgoing_Module", DataType.SYSTEM),
			new KeyImpl<Object>("Filter_Module", DataType.SYSTEM),
			new KeyImpl<Object>("Command_System", DataType.SYSTEM),
			new KeyImpl<Object>("Queue_List", DataType.SYSTEM),
			new KeyImpl<Object>("System_Module", DataType.SYSTEM),
			new KeyImpl<Object>("GroupChatbotActive", DataType.SYSTEM),
			new KeyImpl<Object>("MBServicesManager_Module", DataType.SYSTEM),
			new KeyImpl<Object>("Full_Flow_Module", DataType.SYSTEM),
			new KeyImpl<Object>("Startup_Full_Flow_Module", DataType.SYSTEM) }),
	/***************************************************************************
	 * KHỞI TẠO HKEY_CURRENT_USER <br>
	 * <br>
	 * HKEY_CURRENT_USER chứa các thông tin về cấu hình của các Users khi đăng
	 * nhập vào hệ thống, các thông tin cá nhân của user khi login bằng Admin
	 * Control Panel.
	 **************************************************************************/
	HKEY_CURRENT_USER (new Key[] {
			new KeyImpl<Object>("Administrator", DataType.SYSTEM),
			new KeyImpl<Object>("Moderator", DataType.SYSTEM),
			new KeyImpl<Object>("User", DataType.SYSTEM) }),
	/***************************************************************************
	 * KHỞI TẠO HKEY_SYSTEM_MACHINE <br>
	 * <br>
	 * HKEY_SYSTEM_MACHINE chứa các thông tin về hệ thống, scripting file,
	 * database, hoặc tất cả thông tin liên quan đến hệ thống cần cất trữ trên
	 * mbRegistry
	 **************************************************************************/
	HKEY_SYSTEM_MACHINE (
			new Key[] {
					new KeyImpl<String>("System_Information", DataType.SYSTEM,
							new Key[] { new KeyImpl<String>(
									"Command_Services_Files", DataType.USER) },
							new Value[] {
									new ValueImpl<String>("RMI_Port",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("COMMAND_Port",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>(
											"UserName_Authentication_Remote",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>(
											"Password_Authetication_Remote",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>(
											"Authentication_Active",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>(
											"Max_Connection_CommandServer",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>(
											"Max_Connection_RMIServer",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Services_Port",
											new LinkedList<String>(),
											DataType.SYSTEM) }),
					new KeyImpl<String>("Scripting_File", DataType.SYSTEM,
							new Value[] {
									new ValueImpl<String>("Service_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Service_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Chatbot_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Chatbot_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Server_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Server_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM) }),
					new KeyImpl<String>(
							"Database_Information",
							DataType.SYSTEM,
							new Key[] {
									new KeyImpl<String>("SAPDB",
											DataType.SYSTEM),
									new KeyImpl<String>("MySQL",
											DataType.SYSTEM),
									new KeyImpl<String>("Microsoft SQL Server",
											DataType.SYSTEM),
									new KeyImpl<String>(
											"Microsoft SQL Server 2005",
											DataType.SYSTEM),
									new KeyImpl<String>("PostgreSQL",
											DataType.SYSTEM),
									new KeyImpl<String>("Oracle",
											DataType.SYSTEM),
									new KeyImpl<String>("Firebird",
											DataType.SYSTEM),
									new KeyImpl<String>("DB2", DataType.SYSTEM),
									new KeyImpl<String>("Derby",
											DataType.SYSTEM),
									new KeyImpl<String>("ThinkSQL",
											DataType.SYSTEM),
									new KeyImpl<String>("Sybase",
											DataType.SYSTEM) }) }),
	/***************************************************************************
	 * KHỞI TẠO HKEY_CURRENT_CONFIG <br>
	 * <br>
	 * HKEY_CURRENT_CONFIG chứa các thông tin thông số cấu hình của hệ thống
	 * hiện tại đang thực thi. Các thông tin tương tự HKEY_SYSTEM_MACHINE nhưng
	 * chỉ lưu các thông tin đang thực sự thực thi trong thời điểm hiện tại.
	 **************************************************************************/
	HKEY_CURRENT_CONFIGURATION (
			new Key[] {
					new KeyImpl("System_Information", DataType.SYSTEM,
							new Key[] { new KeyImpl(
									"Service_Status_Information",
									DataType.SYSTEM) }, new Value[] {
									new ValueImpl("RMI_Port",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl("COMMAND_Port",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl(
											"UserName_Authentication_Remote",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl(
											"Password_Authetication_Remote",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl(
											"Max_Connection_CommandServer",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl("Max_Connection_RMIServer",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl("Authentication_Active",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl("Services_Port",
											new LinkedList<String>(),
											DataType.SYSTEM) }),
					new KeyImpl<String>("Scripting_File", DataType.SYSTEM,
							new Value[] {
									new ValueImpl<String>("Service_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Service_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Chatbot_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Chatbot_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Server_Startup",
											new LinkedList<String>(),
											DataType.SYSTEM),
									new ValueImpl<String>("Server_Stop",
											new LinkedList<String>(),
											DataType.SYSTEM) }),
					new KeyImpl<Object>(
							"Database_Information",
							DataType.SYSTEM,
							new Key[] {
									new KeyImpl<Object>("SAPDB",
											DataType.SYSTEM),
									new KeyImpl<Object>("MySQL",
											DataType.SYSTEM),
									new KeyImpl<Object>("Microsoft SQL Server",
											DataType.SYSTEM),
									new KeyImpl<Object>(
											"Microsoft SQL Server 2005",
											DataType.SYSTEM),
									new KeyImpl<Object>("PostgreSQL",
											DataType.SYSTEM),
									new KeyImpl<Object>("Oracle",
											DataType.SYSTEM),
									new KeyImpl<Object>("Firebird",
											DataType.SYSTEM),
									new KeyImpl<Object>("DB2", DataType.SYSTEM),
									new KeyImpl<Object>("Derby",
											DataType.SYSTEM),
									new KeyImpl<Object>("ThinkSQL",
											DataType.SYSTEM),
									new KeyImpl<Object>("Sybase",
											DataType.SYSTEM) }) }),
	/***************************************************************************
	 * KHỞI TẠO HKEY_PERFORMANCE_DATA <br>
	 * <br>
	 * HKEY_PERFORMANCE_DATA chứa tất cả các thông tin về hiệu năng của hệ
	 * thống, được ghi nhận trong quá trình hệ thống thực thi.
	 **************************************************************************/
	HKEY_PERFORMANCE_DATA (
			new Key[] {
					new KeyImpl<Object>("Current_Microbot_Server ...",
							DataType.SYSTEM),
					new KeyImpl<Object>("Modules_Performance", DataType.SYSTEM) }),
	/***************************************************************************
	 * KHỞI TẠO HKEY_VOLATILE_DATA <br>
	 * <br>
	 * HKEY_VOLATILE_DATA chứa các dữ liệu tổng quát trong khi hệ thống thực
	 * thi, hoặc các dữ liệu volatile nào đó cần cất trữ trên mbRegistry.
	 **************************************************************************/
	HKEY_VOLATILE_DATA (new Key[] {});
	private Key<?>[]	subKeys	= null;

	EKeySystemType(Key<?>[] psubkeys) {

		subKeys = psubkeys;
	}

	/**
	 * Hàm getSubKeys trả về subKeys trong key Root
	 * 
	 * @return MBKey[]
	 */
	public Key<?>[] getSubKeys() {

		return subKeys;
	}
}
