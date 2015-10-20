package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.bean.MessageBean;
import com.bean.QueryResultBean;
import com.common.RoleCommon;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;
import com.model.InfoStudentBasic;
import com.model.InfoStudentScore;
import com.model.SysYearTerm;
import com.service.StudentBasicService;
import com.service.TeacherBasicService;
import com.util.ExcelImportUtil;

/**
 * 访问学生
 * 
 * @author Administrator
 *
 */
public class StudentBaseController extends BaseController {

	/*
	 * 学生信息管理
	 */
	public void studentManage() {

		render("student_manage.jsp");

	}

	/*
	 * 未完成选择管理员分配
	 */
	public void allocStudent() {
		render("alloc_student.jsp");
	}

	/*
	 * 未完成选择学生列表
	 */
	public void getUnselectStudentList() {
		QueryResultBean<InfoStudentBasic> queryResult = null;

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		queryResult = InfoStudentBasic.getNotSelectedStudentResult(page, rows);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<InfoStudentBasic> list = queryResult.getList();

		jsonMap.put("rows", list);

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/*
	 * 详细学生
	 */
	public void detailStudent() {
		String t_work_id = getPara("t_work_id");
		setAttr("t_work_id", t_work_id);
		render("detailStudent.jsp");
	}

	/*
	 * 待选学生
	 */
	public void candidateStudent() {
		render("candidate_student.jsp");
	}

	/*
	 * 学生列表
	 */
	public void getAllStudentBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResultBean<InfoStudentBasic> queryResult = null;

		if (getUserRole() == RoleCommon.TEACHER) {
			queryResult = InfoStudentBasic
					.getStudentResultWithVolunteerByWorkId(page, rows, getId());
		} else {
			queryResult = InfoStudentBasic.getStudentResult(page, rows);
		}

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<InfoStudentBasic> list = queryResult.getList();

		jsonMap.put("rows", list);

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/*
	 * 可供选择学生
	 */
	public void canSelectStudent() {
		int count = TeacherBasicService.getTeacherRestNumberByWorkId(getId());
		if (count <= 0) {
			setAttr("can_select", false);
			setAttr("rest_count", 0);

		} else {
			setAttr("can_select", true);
			setAttr("rest_count", count);
		}
		render("can_select_student.jsp");
	}

	/*
	 * 已选学生
	 */
	public void selectedStudent() {
		String t_work_id = getPara("t_work_id");
		setSessionAttr("t_work_id", t_work_id);
		render("selected_student.jsp");
	}

	/*
	 * 已选学生列表
	 */
	public void getStudentBaseListBySelected() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		String t_work_id = "";

		if (getUserRole() == RoleCommon.TEACHER) {
			t_work_id = getId();
		} else if (getUserRole() == RoleCommon.ADMIN) {
			t_work_id = getPara("t_work_id");
		}

		QueryResultBean<InfoStudentBasic> queryResult = InfoStudentBasic
				.getStudentResultWithSelectedByWorkId(page, rows, t_work_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());
		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/*
	 * 学生成绩
	 */
	public void studentScore() {

		String s_id = getPara("s_id");

		setAttr("s_id", s_id);

		render("student_score.jsp");

	}

	/*
	 * 成绩学年学期列表
	 */
	public void getSysYearTermList() {

		String s_id = getPara("s_id");

		List<SysYearTerm> list = StudentBasicService.getSysYearTermList(s_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}

	/*
	 * 学年学期成绩列表
	 */
	public void getScoreList() {

		String s_id = getPara("s_id");

		int y_t_id = getParaToInt("id");

		List<InfoStudentScore> list = InfoStudentScore
				.getInfoStudentScoreListBySIdAndTime(s_id, y_t_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}

	/*
	 * 删除学生
	 */
	public void deleteStudent() {

		MessageBean messageBean = new MessageBean();

		String s_id = getPara("s_id");

		boolean flag = StudentBasicService.deleteStudent(s_id);

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 导入学生基本信息
	 */
	public void uploadBasicExcel() {

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");

		UploadFile uploadFile = getFile("excelBasic", realpath,
				200 * 1024 * 1024, "UTF-8");

		ExcelImportUtil importUtil = new ExcelImportUtil();

		String path = realpath + "/" + uploadFile.getFileName();

		importUtil.importBasic(path);

		uploadFile.getFile().delete();

		render("student_manage.jsp");

	}

	/*
	 * 导入学生成绩信息
	 */
	public void uploadScoreExcel() {

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");

		UploadFile uploadFile = getFile("excelScore", realpath,
				200 * 1024 * 1024, "UTF-8");

		ExcelImportUtil importUtil = new ExcelImportUtil();

		String path = realpath + "/" + uploadFile.getFileName();

		importUtil.importScoreExcel(path);

		uploadFile.getFile().delete();

		render("student_manage.jsp");
	}

}