package com.service;

import java.util.ArrayList;
import java.util.List;

import com.bean.ItemBean;
import com.bean.MessageBean;
import com.bean.QueryResultBean;
import com.model.LogicDoVolunteer;
import com.model.LogicVolunteerResult;

public class DoVolunteerService {
	public List<ItemBean> getVolunteerJson(String s_id) {

		List<ItemBean> treeList = new ArrayList<ItemBean>();

		QueryResultBean<LogicDoVolunteer> queryResult = LogicDoVolunteer
				.getVolunteerResultBySId(s_id);

		if (queryResult != null) {

			List<LogicDoVolunteer> list = queryResult.getList();

			List<String> volunteerList = new ArrayList<String>();

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					LogicDoVolunteer logicStudentVolunteer = list.get(i);
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
		}

		return treeList;

	}

	public MessageBean doTeacherVolunteer(String total_id, String t_work_id) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArry = total_id.split(";");

		for (int i = 0; i < pairArry.length; i++) {
			String s_id = pairArry[i];

			LogicVolunteerResult logicTeacherStudent = new LogicVolunteerResult();

			logicTeacherStudent.set("t_work_id", t_work_id);

			logicTeacherStudent.set("s_id", s_id);

			boolean flag1 = logicTeacherStudent.save();

			boolean flag2 = false;

			boolean flag3 = false;

			LogicDoVolunteer logicStudentVolunteer = LogicDoVolunteer
					.getVolunteerByWorkIdAndSId(s_id, t_work_id);

			if (logicStudentVolunteer != null) {
				flag2 = deleteVolunteerBySId(s_id);
			}

			long count = TeacherBasicService
					.getTeacherRestNumberByWorkId(t_work_id);

			// 教师选完学生，释放所有未被选择的学生
			if (count == 0) {
				flag3 = deleteNotSelectedByWorkId(t_work_id);
			}

			// 成功个数
			if (flag1 && flag2 && flag3) {
				sucessCount++;
			}

		}

		int failCount = pairArry.length - sucessCount;

		String message = "成功选择:&nbsp;" + sucessCount + "&nbsp;个学生";

		if (failCount != 0) {
			message += ";失败：" + failCount + "&nbsp;个";
		}

		if (failCount == pairArry.length) {
			messageBean.setFlag(false);
			messageBean.setMessage("数据库保存失败");
		} else {
			messageBean.setFlag(true);

			messageBean.setMessage(message);
		}

		return messageBean;

	}

	public MessageBean doStudentVolunteer(String para, String s_id) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		int failCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {

			String pair = pairArray[i];

			String[] elementArray = pair.split(",");

			String t_work_id = elementArray[0];

			String s_t_volunteer = elementArray[1];

			LogicDoVolunteer volunteer = new LogicDoVolunteer();

			volunteer.set("s_id", s_id);

			volunteer.set("t_work_id", t_work_id);

			volunteer.set("s_t_volunteer", s_t_volunteer);

			boolean flag = volunteer.save();

			if (flag) {
				sucessCount++;
			}
		}

		messageBean.setFlag(true);

		failCount = pairArray.length - sucessCount;

		String message = "成功选择:&nbsp;" + sucessCount + "&nbsp;个志愿";

		if (failCount != 0) {
			message += ";失败：" + failCount + "&nbsp;个";
		}

		if (failCount == pairArray.length) {

			messageBean.setFlag(false);
			messageBean.setMessage("数据库保存失败");

		} else {

			messageBean.setFlag(true);

			messageBean.setMessage(message);
		}

		return messageBean;

	}

	public MessageBean doAdminVolunteer(String para) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		int failCount = 0;

		String[] pairArray = para.split(";");
		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String s_id = elementArray[0];
			String t_work_id = elementArray[1];
			LogicVolunteerResult logicTeacherStudent = LogicVolunteerResult
					.getVolunteerResult(t_work_id, s_id);
			if (logicTeacherStudent == null) {
				logicTeacherStudent = new LogicVolunteerResult();
			}

			logicTeacherStudent.set("t_work_id", t_work_id);
			logicTeacherStudent.set("s_id", s_id);

			boolean flag = logicTeacherStudent.save();

			if (flag) {
				sucessCount++;
			}
		}

		messageBean.setFlag(true);

		failCount = pairArray.length - sucessCount;

		String message = "成功选择:&nbsp;" + sucessCount + "&nbsp;个志愿";

		if (failCount != 0) {
			message += ";失败：" + failCount + "&nbsp;个";
		}

		if (failCount == pairArray.length) {

			messageBean.setFlag(false);
			messageBean.setMessage("数据库保存失败");

		} else {

			messageBean.setFlag(true);

			messageBean.setMessage(message);
		}

		return messageBean;
	}

	public static boolean deleteNotSelectedByWorkId(String t_work_id) {
		List<LogicDoVolunteer> list = LogicDoVolunteer
				.getNotSelectedByWorkId(t_work_id);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				boolean flag = list.get(i).delete();
				if (!flag) {
					return false;
				}
			}
		}

		return true;
	}

	public static boolean deleteVolunteerBySId(String s_id) {
		boolean flag = true;
		List<LogicDoVolunteer> list = LogicDoVolunteer
				.getLogicStudentVolunteerListBySId(s_id);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				flag = list.get(i).delete();
				if (!flag) {
					return false;
				}
			}

		}

		return true;
	}

}
