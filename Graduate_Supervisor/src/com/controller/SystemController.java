package com.controller;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class SystemController extends BaseController {

	/*
	 * 首页面
	 */
	public void index() {
		if (getSessionAttr(LOGINUSER) == null)
			redirect("/login/login");
		else
			render("index.jsp");
	}

	public void welcome() {
		
		render("welcome.jsp");
	}

}
