package com.app;

import com.app.controller.AdminController;
import com.app.controller.LoginController;
import com.app.controller.StudentBaseController;
import com.app.controller.StudentVolunteerController;
import com.app.controller.SystemController;
import com.app.controller.TeacherBaseController;
import com.app.controller.UserController;
import com.app.model.InfoStudentBasic;
import com.app.model.InfoStudentScore;
import com.app.model.InfoTeacherBasic;
import com.app.model.LogicDoVolunteer;
import com.app.model.LogicVolunteerResult;
import com.app.model.SysStudentRoundTime;
import com.app.model.SysTeacherRoundTime;
import com.app.model.SysUser;
import com.app.model.SysUserLog;
import com.app.model.SysVolunteerTime;
import com.app.model.SysYearTerm;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.pluins.quarz.AppQuarzPlugin;

public class AppConfig extends JFinalConfig {

	public void configConstant(Constants me) {

		PropKit.use("a_little_config.txt");

		me.setDevMode(true);

		me.setViewType(ViewType.JSP);

		me.setBaseViewPath("/jsp");
	}

	public void configRoute(Routes me) {

		me.add("/teacherBase", TeacherBaseController.class);

		me.add("/studentBase", StudentBaseController.class);

		me.add("/studentVolunteer", StudentVolunteerController.class);

		me.add("/admin", AdminController.class);

		me.add("/", SystemController.class, "system");

		me.add("/login", LoginController.class, "/system");

		me.add("/user", UserController.class);
	}

	public void configPlugin(Plugins me) {
		
		C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"),
				PropKit.get("user"), PropKit.get("password").trim());
		me.add(c3p0Plugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);

		arp.addMapping("info_teacher_basic", "t_work_id",
				InfoTeacherBasic.class);

		arp.addMapping("info_student_basic", "s_id", InfoStudentBasic.class);

		arp.addMapping("info_student_score", "id", InfoStudentScore.class);

		arp.addMapping("logic_volunteer_result", "id",
				LogicVolunteerResult.class);

		arp.addMapping("logic_do_volunteer", "id", LogicDoVolunteer.class);

		arp.addMapping("sys_user", "id", SysUser.class);

		arp.addMapping("sys_teacher_round_time", "id",
				SysTeacherRoundTime.class);

		arp.addMapping("sys_student_round_time", "id",
				SysStudentRoundTime.class);

		arp.addMapping("sys_volunteer_time", "id", SysVolunteerTime.class);

		arp.addMapping("sys_year_term", "id", SysYearTerm.class);

		arp.addMapping("sys_user_log", "id", SysUserLog.class);

		AppQuarzPlugin quartzPlugin = new AppQuarzPlugin();

		me.add(quartzPlugin);

	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

	public void configInterceptor(Interceptors me) {

	}

	public void configHandler(Handlers me) {

	}
}