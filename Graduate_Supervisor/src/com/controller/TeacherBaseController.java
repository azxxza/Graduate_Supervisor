package com.controller;

import java.util.HashMap;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicTeacherStudent;
import com.service.TeacherBasicService;
import com.service.VolunteerService;
import com.system.CurrentExcuteVolunteer;
import com.util.MessageBean;
import com.util.QueryResult;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class TeacherBaseController extends BaseController {

	private TeacherBasicService teacherBasicService = new TeacherBasicService();

	public void candidateTeacherList() {
		String s_id = getId();

		boolean flag = LogicTeacherStudent.exitLogicTeacherStudentBySId(s_id);

		// 返回我的导师列表
		if (flag) {
			render("myTeacher.jsp");
			// 返回志愿列表
		} else {
			CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
					.getCurrentExcuteVolunteer();

			boolean isOpen = currentExcuteVolunteer.isRunning();

			setAttr("isOpen", true);

			render("candidate_teacher.jsp");

		}
	}

	/**
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows, getId());

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/**
	 * 查看教师的详细信息，返回到详细信息列表
	 * 
	 * @return
	 */
	public void detail() {

		String t_work_id = getPara("t_work_id");

		setAttr("t_work_id", t_work_id);

		render("detail.jsp");

	}

	// public void doVolunteer() {
	//
	// String para = getPara("para");
	//
	// MessageBean messageBean = volunteerService.doStudentVolunteer(para);
	//
	// renderJson(messageBean);
	//
	// }

	public void saveTeacherNumber() {

		String para = getPara("para");

		MessageBean messageBean = teacherBasicService.saveTeacherNumber(para);

		renderJson(messageBean);
	}
}
