package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.bean.MessageBean;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;
import com.model.InfoTeacherBasic;
import com.model.SysOpenTime;
import com.model.SysVolunteerTime;
import com.service.DoVolunteerService;
import com.service.TeacherBasicService;
import com.service.VolunteerParamService;
import com.util.ExcelExportUtil;

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
	public void setOpenTime() {

		render("set_open_time.jsp");

	}

	/*
	 * 更新轮数列表的时间
	 */
	public void updateOpenTime() {

		String para = getPara("para");

		MessageBean messageBean = VolunteerParamService.updateOpenTime(para);

		renderJson(messageBean);
	}

	/*
	 * 保存新一轮
	 */
	public void saveOpenTime() {

		MessageBean messageBean = new MessageBean();

		SysOpenTime s_round_open_time = getModel(SysOpenTime.class,
				"s_round_open_time");

		boolean flag = volunteerParamService.saveOpenTime(s_round_open_time);

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 轮时间列表
	 */
	public void getOpenTimeList() {

		List<SysOpenTime> list = VolunteerParamService.getOpenTimeList();

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
				.getVolunteerTimeByRound(r_t_round);

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

		List<InfoTeacherBasic> list = InfoTeacherBasic.getTeacherBaseList();

		ExcelExportUtil.exportByRecord(getResponse(), getRequest(),
				"选导师系统报表汇总", list);

		renderNull();
	}

	/*
	 * 设置上传的教职工号
	 */
	public void setUploadWorkId() {

		String t_work_id = getPara("t_work_id");

		setSessionAttr("f_t_work_id", t_work_id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(true);

		renderJson(messageBean);

	}

	/*
	 * 上传pdf 文档
	 */
	public void uploadPDF() {

		String t_work_id = getSessionAttr("f_t_work_id");

		MessageBean messageBean = new MessageBean();

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/pdf/");

		UploadFile uploadFile = getFile("pdf", realpath, 200 * 1024 * 1024,
				"UTF-8");

		boolean flag = TeacherBasicService.uploadPDF(t_work_id,
				uploadFile.getFileName());

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("数据库数据更新失败");
		}

		render("teacherManage.jsp");

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
	 * 删除一轮
	 */
	public void deleteOpenTime() {

		int id = getParaToInt("id");

		boolean flag = VolunteerParamService.deleteOpenTime(id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);

	}

}