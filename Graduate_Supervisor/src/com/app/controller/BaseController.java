package com.app.controller;

import com.app.model.SysUser;
import com.jfinal.core.Controller;

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
	 * 登录用户
	 * 
	 * @return
	 */
	protected SysUser getLoginUser() {

		return getSessionAttr(LOGINUSER);
	}

	/**
	 * 登录角色
	 * 
	 * @return
	 */
	protected Integer getUserRole() {
		int role = 0;

		SysUser user = getLoginUser();

		if (user != null) {
			role = user.getInt("s_user_role_id");// 对应admin_power的权限id
		} else {
			render("login.jsp");
		}

		return role;
	}

	/**
	 * 登录用户的主键
	 * 
	 * @return
	 */
	protected String getId() {

		SysUser user = getLoginUser();

		return user.getStr("s_foreign_id");

	}
}
