package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicStudentVolunteer;
import com.model.LogicTeacherStudent;
import com.service.VolunteerService;
import com.util.ItemBean;
import com.util.MessageBean;
import com.util.QueryResult;

public class StudentVolunteerController extends BaseController {

	private VolunteerService volunteerService = new VolunteerService();

	public void getStudentVolunteerList() {
		QueryResult<LogicStudentVolunteer> queryResult = null;

		int page = getParaToInt("page").intValue();
		int rows = getParaToInt("rows").intValue();

		String t_work_id = getId();

		queryResult = LogicStudentVolunteer.getVolunteerResultByWorkId(
				Integer.valueOf(page), Integer.valueOf(rows), t_work_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());
		jsonMap.put("total", Long.valueOf(queryResult.getCount()));

		renderJson(jsonMap);
	}

	public void saveStudentVolunteer() {

		String para = getPara("para");

		String s_id = getId();

		MessageBean messageBean = volunteerService.doStudentVolunteer(para,
				s_id);

		renderJson(messageBean);
	}

	public void deleteVolunteer() {
		String t_work_id = getPara("t_work_id");

		MessageBean messageBean = new MessageBean();

		if (t_work_id != null) {
			LogicTeacherStudent logicTeacherStudent = LogicTeacherStudent
					.getLogicTeacherStudent(t_work_id, getId());

			if (logicTeacherStudent != null) {
				messageBean.setFlag(false);
				InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
						.getTmsTeacher(t_work_id);
				String t_name = (String) infoTeacherBasic.get("t_name");
				messageBean.setMessage("你已经被" + t_name + "选走啦，小伙子");
			} else {
				LogicStudentVolunteer logicStudentVolunteer = LogicStudentVolunteer
						.getVolunteerByWorkIdAndSId(getId(), t_work_id);
				boolean flag = logicStudentVolunteer.delete();
				messageBean.setFlag(flag);
				if (flag == false) {
					messageBean.setMessage("数据库操作失败");
				}

			}

		}

		renderJson(messageBean);
	}

	/**
	 * 教师选择学生
	 */
	public void teacherSelect() {

		String total_id = getPara("total_id");

		MessageBean messageBean = volunteerService.doTeacherVolunteer(total_id,
				getId());

		renderJson(messageBean);
	}

	public void getVolunteerJson() {

		List<ItemBean> treeList = volunteerService.getVolunteerJson(getId());

		renderJson(treeList);

	}
}