package com.controller;

import java.util.List;

import com.bean.ItemBean;
import com.bean.MessageBean;
import com.model.LogicDoVolunteer;
import com.service.DoVolunteerService;

public class StudentVolunteerController extends BaseController {

	private DoVolunteerService volunteerService = new DoVolunteerService();

	/*
	 * 提交志愿
	 */
	public void saveStudentVolunteer() {

		String para = getPara("para");

		String s_id = getId();

		MessageBean messageBean = volunteerService.doStudentVolunteer(para,
				s_id);

		renderJson(messageBean);
	}

	/*
	 * 删除志愿
	 */
	public void deleteVolunteer() {

		String t_work_id = getPara("t_work_id");

		MessageBean messageBean = new MessageBean();

		LogicDoVolunteer logicStudentVolunteer = LogicDoVolunteer
				.getVolunteerByWorkIdAndSId(getId(), t_work_id);
		boolean flag = logicStudentVolunteer.delete();
		messageBean.setFlag(flag);
		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);
	}

	/*
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