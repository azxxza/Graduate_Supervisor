package com.app.model;

import com.app.common.TableCommom;
import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class SysUser extends Model<SysUser> {
	public final static SysUser dao = new SysUser();

	// adminUser 用户表
	public final static String SYS_USER = TableCommom.SYS_USER;

	// 查找用户
	public static SysUser getUser(String username, String password, int role) {

		// 设置查找语句
		String sql = "Select * From " + SYS_USER + " Where s_user_name = '"
				+ username + "' and s_user_password = '" + password + "'";

		if (role == 1) {
			sql += " and s_user_role_id = 1";
		} else if (role == 2) {
			sql += " and s_user_role_id = 2";
		} else if (role == 3) {
			sql += " and s_user_role_id = 3";
		}

		return dao.findFirst(sql);

	}

}
