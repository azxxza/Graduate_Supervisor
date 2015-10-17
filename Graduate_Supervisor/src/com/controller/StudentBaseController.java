package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.JFinal;
import com.model.InfoStudentBasic;
import com.model.InfoStudentScore;
import com.model.InfoTeacherBasic;
import com.model.SysYearTerm;
import com.system.CurrentExcuteVolunteer;
import com.util.MessageBean;
import com.util.QueryResult;

public class StudentBaseController extends BaseController {
	public void candidate_student() {
		render("candidate_student.jsp");
	}

	public void getAllStudentBaseListByAdmin() {
		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoStudentBasic> queryResult = InfoStudentBasic
				.getStudentResult(page, rows);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<InfoStudentBasic> list = queryResult.getList();

		jsonMap.put("rows", list);

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	public void getAllStudentBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoStudentBasic> queryResult = InfoStudentBasic
				.getStudentResultWithVolunteerByWorkId(page, rows, getId());

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<InfoStudentBasic> list = queryResult.getList();

		jsonMap.put("rows", list);

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	public void can_select_student() {
		int count = InfoTeacherBasic
				.getTeacherStudentRestNumberByWorkId(getId());
		if (count <= 0) {
			setAttr("can_select", false);
			setAttr("rest_count", 0);

		} else {
			setAttr("can_select", true);
			setAttr("rest_count", count);
		}
		render("can_select_student.jsp");
	}

	public void getCanSelectStudentBaseList() {
		CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();

		if (currentExcuteVolunteer.isRunning()) {
			render("curr_volun_student.jsp");
		} else {

			String path = JFinal.me().getContextPath()
					+ "/jsp/common/no_open.jsp";

			render(path);
		}
	}

	public void selected_student() {
		String t_work_id = getPara("t_work_id");
		setSessionAttr("t_work_id", t_work_id);
		render("selected_student.jsp");
	}

	public void getStudentBaseListBySelected() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		String t_work_id = getId();

		if ((t_work_id == null) || (t_work_id.equals(""))
				|| (t_work_id.equals("0"))) {
			t_work_id = (String) getSessionAttr("t_work_id");
		}
		QueryResult<InfoStudentBasic> queryResult = InfoStudentBasic
				.getStudentResultWithSelectedByWorkId(page, rows, t_work_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());
		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	public void getUnselectStudentList() {
		QueryResult<InfoStudentBasic> queryResult = null;

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		queryResult = InfoStudentBasic.getNotSelectedStudentResult(page, rows);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<InfoStudentBasic> list = queryResult.getList();

		jsonMap.put("rows", list);

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	public void detail() {

		String s_id = getPara("s_id");

		setAttr("s_id", s_id);

		render("studentScoreList.jsp");

	}

	public void deleteStudent() {

		MessageBean messageBean = new MessageBean();

		String s_id = getPara("s_id");

		InfoStudentBasic infoStudentBasic = InfoStudentBasic.getStudent(s_id);

		boolean flag = infoStudentBasic.delete();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("数据库保存失败");
		}

		renderJson(messageBean);
	}

	public void getSysYearTermList() {

		String s_id = getPara("s_id");

		QueryResult<SysYearTerm> queryResult = SysYearTerm.getYearTermResult(0,
				0);

		List<SysYearTerm> list = queryResult.getList();

		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("s_id", s_id);
		}

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}

	public void getScoreList() {

		String s_id = getPara("s_id");

		String y_t_id = getPara("id");

		List<InfoStudentScore> list = InfoStudentScore
				.getInfoStudentScoreListBySIdAndTime(s_id, y_t_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}
}