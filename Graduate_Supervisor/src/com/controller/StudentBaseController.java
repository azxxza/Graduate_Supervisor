package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.InfoStudentBasic;
import com.model.VolunteerTime;
import com.system.CurrentExcuteVolunteer;
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
		render("no_open.jsp");
	}

	public void getStudentBaseList() {

		CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
				.getCurrentExcuteVolunteer();

		if (currentExcuteVolunteer.isRunning()) {
			render("curr_volun_student.jsp");
		} else {
			VolunteerTime volunteerTime = currentExcuteVolunteer
					.getVolunteerTime();
			if (volunteerTime != null) {
				int r_t_round = volunteerTime.get("r_t_round");
				int v_volunteer = volunteerTime.get("v_volunteer");
				setSessionAttr("message", "非常遗憾，第 " + r_t_round + " 轮,第 "
						+ v_volunteer + " 志愿录取已经结束，请等待下一志愿录取时间或下一轮录取时间");
			} else {
				setSessionAttr("message", "非常遗憾，系统还未在指定的时间开放");
			}

			render("no_open.jsp");
		}
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