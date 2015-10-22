package com.app.service;

import java.util.ArrayList;
import java.util.List;

import com.app.bean.ItemBean;
import com.app.bean.MessageBean;
import com.app.model.LogicDoVolunteer;
import com.app.model.LogicVolunteerResult;

public class DoVolunteerService {
	public static List<ItemBean> getVolunteerJson(String s_id) {

		List<ItemBean> treeList = new ArrayList<ItemBean>();

		List<LogicDoVolunteer> list = LogicDoVolunteer
				.findVolunteerListBySId(s_id);

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

		return treeList;

	}

	public static MessageBean doTeacherVolunteer(String total_id,
			String t_work_id) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArry = total_id.split(";");

		for (int i = 0; i < pairArry.length; i++) {

			String s_id = pairArry[i];

			LogicVolunteerResult logicTeacherStudent = new LogicVolunteerResult();

			LogicDoVolunteer logicDoVolunteer = LogicDoVolunteer
					.getVolunteerByWorkIdAndSId(s_id, t_work_id);

			String s_t_remark = logicDoVolunteer.getStr("s_t_remark");

			logicTeacherStudent.set("t_work_id", t_work_id);

			logicTeacherStudent.set("s_id", s_id);

			logicTeacherStudent.set("s_t_remark", s_t_remark);

			boolean flag1 = logicTeacherStudent.save();

			boolean flag2 = false;

			boolean flag3 = false;

			if (logicDoVolunteer != null) {
				flag2 = deleteVolunteerBySId(s_id);
			}

			long count = TeacherBasicService
					.getTeacherRestNumberByWorkId(t_work_id);

			// 教师选完学生，释放所有未被选择的学生
			if (count == 0) {
				flag3 = deleteNotSelectedByWorkId(t_work_id);
			} else {
				flag3 = true;
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

	public static MessageBean doStudentVolunteer(String para, String s_id) {

		List<LogicDoVolunteer> list = new ArrayList<LogicDoVolunteer>();

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		int failCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {

			String pair = pairArray[i];

			String[] elementArray = pair.split(",");

			String t_work_id = elementArray[0];

			String s_t_volunteer = elementArray[1];

			String s_t_remark = elementArray[2];

			LogicDoVolunteer volunteer = new LogicDoVolunteer();

			volunteer.set("s_id", s_id);

			volunteer.set("t_work_id", t_work_id);

			volunteer.set("s_t_volunteer", s_t_volunteer);

			volunteer.set("s_t_remark", s_t_remark);

			list.add(volunteer);

		}

		for (int i = 0; i < list.size(); i++) {
			LogicDoVolunteer firstVolunteer = list.get(i);
			for (int j = 0; j < list.size(); j++) {
				if (i != j) {
					LogicDoVolunteer secondVolunteer = list.get(j);
					if (firstVolunteer.getStr("s_t_volunteer").equals(
							secondVolunteer.getStr("s_t_volunteer"))) {
						messageBean.setFlag(false);
						messageBean.setMessage("存在重复的志愿");
						return messageBean;
					}
				}
			}
		}

		for (int i = 0; i < list.size(); i++) {
			boolean flag = list.get(i).save();
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

	public static MessageBean doAdminVolunteer(String para) {

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
				.findNotSelectedByWorkId(t_work_id);
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
				.findVolunteerListBySId(s_id);
		if (list != null && list.size() > 0) {
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
