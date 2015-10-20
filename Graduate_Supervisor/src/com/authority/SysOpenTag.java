package com.authority;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.system.CurrentExcuteVolunteer;

public class SysOpenTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();

		boolean isOpen = currentExcuteVolunteer.isRunning();

		if (isOpen) {
			return EVAL_PAGE;
		}
		return SKIP_BODY;

	}

	@Override
	public int doEndTag() throws JspException {

		return EVAL_PAGE;

	}

}
