package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.InfoStudentBasic;
import com.util.QueryResult;

public class StudentBaseController extends BaseController {
	public void candidate_student() {
		render("candidate_student.jsp");
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

	public void volunteer_student() {
		render("volunteer_student.jsp");
	}

	public void getStudentBaseList() {

		QueryResult<InfoStudentBasic> queryResult = null;

		int page = getParaToInt("page");

		int rows = getParaToInt("rows");

		String t_work_id = getId();

		queryResult = InfoStudentBasic.getStudentResultWithVolunteerByWorkId(
				page, rows, t_work_id);

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
		render("detail.jsp");
	}
}