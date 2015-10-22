package com.app.authority;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.app.system.CurrentExcuteVolunteer;

public class SysOpenTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();	

		boolean isOpen = currentExcuteVolunteer.isRunning();

//		if (isOpen) {
//			return EVAL_PAGE;
//		}
		return EVAL_PAGE;

	}

	@Override
	public int doEndTag() throws JspException {

		return EVAL_PAGE;

	}

}
