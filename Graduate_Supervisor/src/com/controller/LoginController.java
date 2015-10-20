package com.controller;

import java.util.Date;

import com.bean.MessageBean;
import com.common.RoleCommon;
import com.model.InfoTeacherBasic;
import com.model.SysUser;
import com.model.SysUserLog;
import com.service.UserLogService;
import com.util.DateUtil;

/**
 * 学生，教师，管理员
 * 
 * @author azx
 * 
 */
public class LoginController extends BaseController {

	/*
	 * 访问登录页面
	 */
	public void login() {

		render("login.jsp");
	}

	/*
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

			SysUserLog sysUserLog = UserLogService
					.saveOrUpdateSysUserLog(loginUser);

			loginUser.put("loginCount", sysUserLog.getInt("login_count"));

			Date date = sysUserLog.getDate("login_time");

			loginUser.put("loginTime", DateUtil.dateToStr(date));

			if (loginUser.getInt("s_user_role_id") == RoleCommon.TEACHER) {
				String t_work_id = loginUser.getStr("s_foreign_id");
				InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
						.getTmsTeacher(t_work_id);
				int t_number = infoTeacherBasic.getInt("t_number");
				int selected_number = InfoTeacherBasic
						.getSeletedNumberByWorkId(t_work_id);
				
				loginUser.put("t_number", t_number);
				loginUser.put("selected_number", selected_number);
			}

			setSessionAttr(LOGINUSER, loginUser);// 设置当前登录用户

			messageBean.setFlag(true);

		} else {
			messageBean.setFlag(false);
			messageBean.setMessage("用户名或者密码不正确");
		}

		renderJson(messageBean);// 转化成json数据
	}

	/*
	 * 退出登录
	 */
	public void logout() {

		if (getSessionAttr(LOGINUSER) != null) {

			removeSessionAttr(LOGINUSER);

		}
		redirect("/login");
	}

}
