package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.bean.MessageBean;
import com.app.model.InfoTeacherBasic;
import com.app.model.SysStudentRoundTime;
import com.app.model.SysTeacherRoundTime;
import com.app.model.SysVolunteerTime;
import com.app.service.DoVolunteerService;
import com.app.service.VolunteerParamService;
import com.app.util.ExcelExportUtil;

/**
 * 访问管理员
 * 
 * @author Administrator
 *
 */
public class AdminController extends BaseController {

	private VolunteerParamService volunteerParamService = new VolunteerParamService();

	/*
	 * 设置轮时间
	 */
	public void setTeacherTime() {

		render("set_teacher_time.jsp");

	}

	public void setStudentTime() {
		render("set_student_time.jsp");
	}

	/*
	 * 更新教师每轮列表的时间
	 */
	public void updateTeacherTime() {

		String para = getPara("para");

		MessageBean messageBean = VolunteerParamService.updateTeacherTime(para);

		renderJson(messageBean);
	}

	/*
	 * 更新学生每轮列表的时间
	 */
	public void updateStudentTime() {

		String para = getPara("para");

		MessageBean messageBean = VolunteerParamService.updateStudentTime(para);

		renderJson(messageBean);
	}

	/*
	 * 保存教师新一轮
	 */
	public void saveTeacherTime() {

		MessageBean messageBean = new MessageBean();

		SysTeacherRoundTime s_round_open_time = getModel(
				SysTeacherRoundTime.class, "s_round_open_time");

		boolean flag = VolunteerParamService
				.saveTeacherOpenTime(s_round_open_time);

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 保存学生新一轮
	 */
	public void saveStudentTime() {

		MessageBean messageBean = new MessageBean();

		SysStudentRoundTime s_round_open_time = getModel(
				SysStudentRoundTime.class, "s_round_open_time");

		boolean flag = volunteerParamService
				.saveStudentOpenTime(s_round_open_time);

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 教师每轮选择时间列表
	 */
	public void getTeacherTimeList() {

		List<SysTeacherRoundTime> list = VolunteerParamService
				.getTeacherTimeList();

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	/*
	 * 学生每轮选择时间列表
	 */

	public void getStudentTimeList() {

		List<SysStudentRoundTime> list = VolunteerParamService
				.getStudentTimeList();

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	/*
	 * 5个志愿时间
	 */
	public void setVolunteerTime() {

		int r_t_round = getParaToInt("r_t_round");

		setSessionAttr("r_t_round", r_t_round);

		List<SysVolunteerTime> volunteerTimeList = SysVolunteerTime
				.findVolunteerTimeByRound(r_t_round);

		setSessionAttr("volunteerTimeList", volunteerTimeList);

		render("set_volunteer_time.jsp");
	}

	/*
	 * 5个志愿时间列表
	 */
	public void getVolunteerTimeList() {

		int r_t_round = getSessionAttr("r_t_round");

		List<SysVolunteerTime> list = VolunteerParamService
				.getVolunteerTimeByRound(r_t_round);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	/*
	 * 更新5个志愿的时间
	 */
	public void updateVolunteersTime() {

		String para = getPara("para");

		MessageBean messageBean = volunteerParamService
				.updateVolunteersTime(para);

		renderJson(messageBean);
	}

	/*
	 * 导出双选结果报表
	 */
	public void exportAll() {

		List<InfoTeacherBasic> list = InfoTeacherBasic.findTeacherBaseList();

		ExcelExportUtil.exportByRecord(getResponse(), getRequest(),
				"选导师系统报表汇总", list);

		renderNull();
	}

	/*
	 * 管理员强制分配学生
	 */
	public void doAdminVolunteer() {

		String para = getPara("para");

		MessageBean messageBean = DoVolunteerService.doAdminVolunteer(para);

		renderJson(messageBean);
	}

	/*
	 * 保存教师
	 */
	public void saveTeacher() {
		MessageBean messageBean = new MessageBean();
		InfoTeacherBasic infoTeacherBasic = getModel(InfoTeacherBasic.class,
				"infoTeacherBasic");
		boolean flag = infoTeacherBasic.save();
		if (flag) {
			messageBean.setFlag(true);
		} else {
			messageBean.setMessage("保存失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 删除教师一轮
	 */
	public void deleteTeacherTime() {

		int id = getParaToInt("id");

		boolean flag = VolunteerParamService.deleteTeacherTime(id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);

	}

	/*
	 * 删除学生一轮
	 */
	public void deleteStudentTime() {

		int id = getParaToInt("id");

		boolean flag = VolunteerParamService.deleteStudentTime(id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);

	}

}