package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicStudentVolunteer;
import com.util.ItemBean;
import com.util.MessageBean;
import com.util.QueryResult;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class TeacherBaseController extends BaseController {

	public void candidateTeacherList() {
		render("candidate_teacher.jsp");
	}

	/**
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows, "");

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

	public void getVolunteerJson() {

		List<ItemBean> treeList = new ArrayList<ItemBean>();

		QueryResult<LogicStudentVolunteer> queryResult = LogicStudentVolunteer
				.getStudentVolunteerResult(getId());

		List<LogicStudentVolunteer> list = null;

		if (queryResult != null) {
			list = queryResult.getList();
		}

		List<String> volunteerList = new ArrayList<String>();

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LogicStudentVolunteer logicStudentVolunteer = list.get(i);
				String s_t_volunteer = logicStudentVolunteer
						.get("s_t_volunteer");

				volunteerList.add(s_t_volunteer);

			}
		}

		for (int i = 1; i <= 5; i++) {
			if (volunteerList.contains(i + "")) {
				continue;
			}
			treeList.add(new ItemBean(i + "", "第 " + i + " 志愿"));

		}

		renderJson(treeList);

	}

	public void doVolunteer() {
		String para = getPara("para");

		if (para != null && !para.equals("")) {
			String[] pairArray = para.split(";");

			System.out.println(pairArray.length);

			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String t_work_id = elementArray[0];
				String select = elementArray[1];
				InfoTeacherBasic tmsTeacher = InfoTeacherBasic
						.getTmsTeacher(t_work_id);
				tmsTeacher.set("t_select", select);
				tmsTeacher.update();
				MessageBean messageBean = new MessageBean();
				messageBean.setFlag(true);
				renderJson(messageBean);
			}
		}

		renderNull();

	}
}
