package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicTeacherStudent;
import com.util.QueryResult;

public class TeacherStudentController extends BaseController {
	public void selectedStudentList() {
		render("selected_student.jsp");
	}

	public void myTeacher() {
		render("myTeacher.jsp");
	}

	public void getTeacherStudentListByWorkId() {
		System.out.println(getId());

		QueryResult<LogicTeacherStudent> queryResult = null;

		int page = getParaToInt("page").intValue();
		int rows = getParaToInt("rows").intValue();

		String t_work_id = getId();

		queryResult = LogicTeacherStudent.getLogicTeacherStudentResultByWorkId(
				Integer.valueOf(page), Integer.valueOf(rows), t_work_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());
		jsonMap.put("total", Long.valueOf(queryResult.getCount()));

		renderJson(jsonMap);
	}

	public void getMyTeacher() {
		String s_id = getId();

		List<InfoTeacherBasic> list = InfoTeacherBasic
				.getInfoTeacherBasicList(s_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}
}