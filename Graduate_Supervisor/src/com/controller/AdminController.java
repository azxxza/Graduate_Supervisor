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
import com.model.VolunteerTime;
import com.service.DoVolunteerService;
import com.service.TeacherBasicService;
import com.service.VolunteerParamService;
import com.util.ExcelExportUtil;
import com.util.ExcelImportUtil;

/**
 * 访问管理员
 * 
 * @author Administrator
 *
 */
public class AdminController extends BaseController {

	private DoVolunteerService volunteerService = new DoVolunteerService();

	private VolunteerParamService volunteerParamService = new VolunteerParamService();

	private TeacherBasicService teacherBasicService = new TeacherBasicService();

	public void updateVolunteersTime() {

		String para = getPara("para");

		MessageBean messageBean = volunteerParamService
				.updateVolunteersTime(para);

		renderJson(messageBean);
	}

	public void updateOpenTime() {

		String para = getPara("para");

		MessageBean messageBean = volunteerParamService.updateOpenTime(para);

		renderJson(messageBean);
	}

	public void setOpenTime() {

		render("set_open_time.jsp");

	}

	public void getOpenTimeList() {

		List<SysOpenTime> list = SysOpenTime.getOpenTimeList();

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	public void uploadBasicExcel() {

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");

		UploadFile uploadFile = getFile("excelBasic", realpath,
				200 * 1024 * 1024, "UTF-8");

		ExcelImportUtil importUtil = new ExcelImportUtil();

		String path = realpath + "/" + uploadFile.getFileName();

		importUtil.importBasic(path);

		uploadFile.getFile().delete();

		render("studentManage.jsp");

	}

	public void uploadScoreExcel() {

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");

		UploadFile uploadFile = getFile("excelScore", realpath,
				200 * 1024 * 1024, "UTF-8");

		ExcelImportUtil importUtil = new ExcelImportUtil();

		String path = realpath + "/" + uploadFile.getFileName();

		importUtil.importScoreExcel(path);

		uploadFile.getFile().delete();

		render("studentManage.jsp");
	}

	public void exportAll() {

		List<InfoTeacherBasic> list = InfoTeacherBasic.getTeacherBaseList();

		ExcelExportUtil.exportByRecord(getResponse(), getRequest(),
				"选导师系统报表汇总", list);

		renderNull();
	}

	public void saveOpenTime() {

		MessageBean messageBean = new MessageBean();

		SysOpenTime s_round_open_time = getModel(SysOpenTime.class,
				"s_round_open_time");

		boolean flag = volunteerParamService.saveOpenTime(s_round_open_time);

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	public void setVolunteerTime() {

		int r_t_round = getParaToInt("r_t_round");

		setSessionAttr("r_t_round", r_t_round);

		List<VolunteerTime> volunteerTimeList = VolunteerTime
				.getVolunteerTimeByRound(r_t_round);

		setSessionAttr("volunteerTimeList", volunteerTimeList);

		render("set_volunteer_time.jsp");
	}

	public void getVolunteerTimeList() {

		String r_t_round = getSessionAttr("r_t_round");

		List<VolunteerTime> list = VolunteerTime
				.getVolunteerTimeByRound(Integer.parseInt(r_t_round));

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	public void setUploadWorkId() {

		String t_work_id = getPara("t_work_id");

		setSessionAttr("f_t_work_id", t_work_id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(true);

		renderJson(messageBean);

	}

	public void uploadPDF() {

		String t_work_id = getSessionAttr("f_t_work_id");

		MessageBean messageBean = new MessageBean();

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/pdf/");

		UploadFile uploadFile = getFile("pdf", realpath, 200 * 1024 * 1024,
				"UTF-8");

		boolean flag = teacherBasicService.uploadPDF(t_work_id,
				uploadFile.getFileName());

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("数据库数据更新失败");
		}

		render("teacherManage.jsp");

	}

	public void doAdminVolunteer() {

		String para = getPara("para");

		MessageBean messageBean = volunteerService.doAdminVolunteer(para);

		renderJson(messageBean);
	}

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

	public void deleteOpenTime() {

		int id = getParaToInt("id");

		boolean flag = volunteerParamService.deleteOpenTime(id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);

	}

}