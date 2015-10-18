package com.authority;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.common.RoleCommon;
import com.model.SysUser;

public class PrivilegeTag extends TagSupport {

	private static final String MENU_STUDENT = "menu_student";
	private static final String MENU_TEACHER = "menu_teacher";
	private static final String MENU_ADMIN = "menu_admin";

	/**
	 * 添加用户，
	 */
	private static final long serialVersionUID = 1L;
	private String powerName;

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session = pageContext.getSession();
		SysUser user = (SysUser) session.getAttribute("loginUser");
		if (user != null) {
			int s_user_role_id = user.getInt("s_user_role_id");

			if ((powerName.equals(MENU_STUDENT) && s_user_role_id == RoleCommon.STUDENT)) {
				return EVAL_PAGE;

			}

			if ((powerName.equals(MENU_TEACHER) && s_user_role_id == RoleCommon.TEACHER)) {
				return EVAL_PAGE;

			}

			if ((powerName.equals(MENU_ADMIN) && s_user_role_id == RoleCommon.ADMIN)) {
				return EVAL_PAGE;

			}

		}

		return SKIP_BODY;

	}

	@Override
	public int doEndTag() throws JspException {

		return EVAL_PAGE;

	}

}
