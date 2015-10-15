package com.controller;

import com.jfinal.core.Controller;
import com.model.SysUser;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class BaseController extends Controller {

	// 登录用户存放的key值
	public static final String LOGINUSER = "loginUser";

	/**
	 * 用户角色
	 * 
	 * @return
	 */

	protected SysUser getLoginUser() {

		return getSessionAttr(LOGINUSER);
	}

	protected Integer getUserRole() {
		int role = 0;

		SysUser user = getLoginUser();

		if (user != null) {
			role = user.getInt(SysUser.s_user_role_id);// 对应admin_power的权限id
		} else {
			render("login.jsp");
		}

		return role;
	}

	protected String getId() {

		SysUser user = getLoginUser();
		
		return user.getStr("s_foreign_id");

	}
}
