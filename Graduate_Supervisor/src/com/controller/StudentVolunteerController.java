package com.controller;

import java.util.HashMap;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicStudentVolunteer;
import com.model.LogicTeacherStudent;
import com.util.MessageBean;
import com.util.QueryResult;

public class StudentVolunteerController extends BaseController {
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
		
		String s_id = getId();
		
		System.out.println(s_id);

		MessageBean messageBean = new MessageBean();

		boolean hasExit = LogicTeacherStudent
				.exitLogicTeacherStudentBySId(getId());

		if (hasExit) {
			messageBean.setFlag(false);
			messageBean.setMessage("您已经有导师了");
		} else {
			String para = getPara("para");

			if ((para != null) && (!para.equals(""))) {
				String[] pairArray = para.split(";");

				for (int i = 0; i < pairArray.length; i++) {
					String pair = pairArray[i];
					String[] elementArray = pair.split(",");
					String t_work_id = elementArray[0];
					String s_t_volunteer = elementArray[1];

					LogicStudentVolunteer volunteer = new LogicStudentVolunteer();
					volunteer.set("s_id", s_id);
					volunteer.set("t_work_id", t_work_id);
					volunteer.set("s_t_volunteer", s_t_volunteer);
					volunteer.save();
				}

				messageBean.setFlag(true);
			}

		}

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

	public void teacherSelect() {
		MessageBean messageBean = new MessageBean();
		String total_id = getPara("total_id");

		if ((total_id != null) && (!total_id.equals(""))) {
			String[] pairArry = total_id.split(";");

			for (int i = 0; i < pairArry.length; i++) {
				String s_id = pairArry[i];

				LogicTeacherStudent logicTeacherStudent = new LogicTeacherStudent();

				logicTeacherStudent.set("t_work_id", getId());

				logicTeacherStudent.set("s_id", s_id);

				MessageBean messageBean2 = LogicTeacherStudent
						.saveLogicTeacherStudent(logicTeacherStudent);

				if (!messageBean2.getFlag()) {
					messageBean.setFlag(false);
					messageBean.setMessage(messageBean2.getMessage());
					break;
				}
				messageBean.setFlag(true);

				LogicStudentVolunteer logicStudentVolunteer = LogicStudentVolunteer
						.getVolunteerByWorkIdAndSId(s_id, getId());

				if (logicStudentVolunteer != null) {
					LogicStudentVolunteer
							.deleteLogicStudentVolunteerBySId(s_id);
				}

				long count = InfoTeacherBasic
						.getTeacherStudentRestNumberByWorkId(getId())
						.longValue();

				if (count == 0L) {
					LogicStudentVolunteer.deleteNotSelectedByWorkId(getId());
				}

			}

		}

		renderJson(messageBean);
	}
}