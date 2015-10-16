package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class SysUser extends Model<SysUser> {
	public final static SysUser dao = new SysUser();

	// adminUser 用户表
	public final static String s_user_role_id = "s_user_role_id";

	// 查找用户
	public static SysUser getUser(String username, String password, int role) {

		// 设置查找语句
		String sql = "Select * From sys_user Where s_user_name = '" + username
				+ "' and s_user_password = '" + password + "'";

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
