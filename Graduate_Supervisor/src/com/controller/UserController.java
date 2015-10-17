package com.controller;

import com.model.SysUser;
import com.util.MessageBean;

/**
 * 普通教师，系主任，办公室，领导登录
 * 
 * @author azx
 * 
 */
public class UserController extends BaseController {

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
				messageBean.setMessage("数据库保存失败");
			}
		}
		
		renderJson(messageBean);

	}

}