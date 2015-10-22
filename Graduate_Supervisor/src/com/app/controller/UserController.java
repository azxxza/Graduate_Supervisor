package com.app.controller;

import com.app.bean.MessageBean;
import com.app.model.SysUser;

/**
 * 
 * @author Administrator
 *
 */
public class UserController extends BaseController {

	/*
	 * 更新密码
	 */
	public void updatePassword() {
		MessageBean messageBean = new MessageBean();

		SysUser sysUser = getLoginUser();

		String oginal = getPara("oginal");

		String s_user_password = sysUser.getStr("s_user_password");

		if (!oginal.equals(s_user_password)) {
			messageBean.setFlag(false);
			messageBean.setMessage("原始密码不正确");
		} else {

			String password = getPara("password");

			sysUser.set("s_user_password", password);

			boolean flag = sysUser.update();

			if (flag) {
				messageBean.setFlag(true);
			} else {
				messageBean.setFlag(false);
				messageBean.setMessage("保存失败");
			}
		}

		renderJson(messageBean);

	}

}
