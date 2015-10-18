package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bean.ItemBean;
import com.bean.MessageBean;
import com.bean.QueryResultBean;
import com.common.RoleCommon;
import com.model.InfoTeacherBasic;
import com.model.LogicVolunteerResult;
import com.service.TeacherBasicService;
import com.service.VolunteerResultService;
import com.system.CurrentExcuteVolunteer;

/**
 * 访问教师
 * 
 * @author Administrator
 *
 */
public class TeacherBaseController extends BaseController {

	private TeacherBasicService teacherBasicService = new TeacherBasicService();

	private VolunteerResultService volunteerResultService = new VolunteerResultService();

	/*
	 * 教师名额分配
	 */
	public void allocNumber() {
		render("alloc_number.jsp");
	}

	/*
	 * 教师信息管理
	 */
	public void teacherManage() {
		render("teacher_manage.jsp");
	}

	/*
	 * 双选结果
	 */
	public void choiseResult() {
		render("choise_result.jsp");
	}

	/*
	 * 待选教师
	 */
	public void candidateTeacher() {

		String s_id = getId();

		boolean flag = VolunteerResultService.hasSelected(s_id);

		// 返回我的导师列表
		if (flag) {
			render("my_teacher.jsp");
			// 返回志愿列表
		} else {
			CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
					.getCurrentExcuteVolunteer();

			boolean isOpen = currentExcuteVolunteer.isRunning();

			setAttr("isOpen", true);

			render("candidate_teacher.jsp");

		}
	}

	public void getMyTeacherList() {

		String s_id = getId();

		List<InfoTeacherBasic> list = teacherBasicService
				.getMyTeacherBasicList(s_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}

	/*
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		QueryResultBean<InfoTeacherBasic> queryResult = null;

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		if (getUserRole() == RoleCommon.STUDENT) {
			queryResult = TeacherBasicService.getTeacherBaseResult(page, rows,
					getId());
		} else if (getUserRole() == RoleCommon.ADMIN) {
			queryResult = InfoTeacherBasic.getTeacherBaseResult(page, rows);
		}

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/*
	 * 查看教师的详细信息，返回到详细信息列表
	 */
	public void teacherDetail() {

		String t_work_id = getPara("t_work_id");

		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
				.getTmsTeacher(t_work_id);

		String t_file_path = infoTeacherBasic.get("t_file_path");

		setAttr("t_file_path", t_file_path);

		render("teacher_detail.jsp");

	}

	/*
	 * 保存教师名额
	 */
	public void saveTeacherNumber() {

		String para = getPara("para");

		MessageBean messageBean = teacherBasicService.saveTeacherNumber(para);

		renderJson(messageBean);
	}

	/*
	 * 教师下拉列表
	 */
	public void getTeacherJson() {

		List<ItemBean> treeList = volunteerResultService
				.getHasRestTeacherJson();

		renderJson(treeList);

	}

}
