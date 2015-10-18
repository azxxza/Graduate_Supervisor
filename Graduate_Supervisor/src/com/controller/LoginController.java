package com.controller;

import com.bean.MessageBean;
import com.model.SysUser;

/**
 * 学生，教师，管理员
 * 
 * @author azx
 * 
 */
public class LoginController extends BaseController {

	/**
	 * 访问登录页面
	 */
	public void login() {

		render("login.jsp");
	}

	/**
	 * 登录验证
	 */
	public void doLogin() {

		MessageBean messageBean = new MessageBean();
		// 获取数据
		String username = getPara("username");
		String password = getPara("password");
		String role = getPara("role");
		// 数据库查找
		SysUser loginUser = SysUser.getUser(username, password,
				Integer.parseInt(role));

		if (loginUser != null) {

			setSessionAttr(LOGINUSER, loginUser);// 设置当前登录用户

			messageBean.setFlag(true);

		} else {
			messageBean.setFlag(false);
			messageBean.setMessage("用户名或者密码不正确！");
		}

		renderJson(messageBean);// 转化成json数据
	}

	public void logout() {
		if (getSessionAttr(LOGINUSER) != null) {
			removeSessionAttr(LOGINUSER);
		}
		redirect("/login");
	}

}
