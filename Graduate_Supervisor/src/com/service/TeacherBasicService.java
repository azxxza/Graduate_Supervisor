package com.service;

import java.util.ArrayList;
import java.util.List;

import com.bean.MessageBean;
import com.bean.QueryResultBean;
import com.model.InfoTeacherBasic;
import com.model.LogicDoVolunteer;
import com.model.LogicVolunteerResult;

public class TeacherBasicService {

	/*
	 * 添加属性
	 */
	private static void addProperties(InfoTeacherBasic infoTeacherBasic) {

		String t_work_id = infoTeacherBasic.getStr("t_work_id");

		int rest_number = getTeacherRestNumberByWorkId(t_work_id);

		infoTeacherBasic.put("rest_number", rest_number);

		infoTeacherBasic.put("t_number_copy",
				infoTeacherBasic.getInt("t_number"));

	}

	private static void addProperties(InfoTeacherBasic infoTeacherBasic,
			String s_id) {

		String t_work_id = infoTeacherBasic.getStr("t_work_id");

		LogicDoVolunteer doVolunteer = LogicDoVolunteer
				.getVolunteerByWorkIdAndSId(s_id, t_work_id);

		if (doVolunteer != null) {
			String s_t_volunteer = doVolunteer.getStr("s_t_volunteer");
			infoTeacherBasic.put("s_t_volunteer", s_t_volunteer);
			infoTeacherBasic.put("s_t_volunteer_copy", s_t_volunteer);
			infoTeacherBasic.put("s_t_status","待定");
		}

		int rest_number = getTeacherRestNumberByWorkId(t_work_id);

		infoTeacherBasic.put("rest_number", rest_number);

		infoTeacherBasic.put("t_number_copy",
				infoTeacherBasic.getInt("t_number"));

	}

	public static MessageBean saveTeacherNumber(String para) {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String[] pairArray = para.split(";");

		for (int i = 0; i < pairArray.length; i++) {
			String pair = pairArray[i];
			String[] elementArray = pair.split(",");
			String t_work_id = elementArray[0];
			String t_number = elementArray[1];

			InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
					.getTmsTeacher(t_work_id);

			infoTeacherBasic.set("t_number", t_number);

			boolean flag = infoTeacherBasic.update();

			if (flag) {
				sucessCount++;
			}

		}

		int failCount = pairArray.length - sucessCount;

		messageBean.setFlag(true);
		String message = "成功保存:&nbsp;" + sucessCount + "&nbsp条数据";
		if (failCount != 0) {
			message += ",失败:" + failCount + "条";
		}
		messageBean.setMessage(message);

		return messageBean;

	}

	public static QueryResultBean<InfoTeacherBasic> getTeacherBaseResult(
			int page, int rows) {

		QueryResultBean<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows);

		List<InfoTeacherBasic> list = queryResult.getList();

		for (int i = 0; i < list.size(); i++) {

			addProperties(list.get(i));

		}

		return queryResult;
	}

	public static QueryResultBean<InfoTeacherBasic> getTeacherBaseResult(
			int page, int rows, String s_id) {

		QueryResultBean<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows);

		List<InfoTeacherBasic> list = queryResult.getList();

		for (int i = 0; i < list.size(); i++) {

			InfoTeacherBasic infoTeacherBasic = list.get(i);

			addProperties(infoTeacherBasic);

			addProperties(infoTeacherBasic, s_id);

		}

		return queryResult;
	}

	public static int getTeacherRestNumberByWorkId(String t_work_id) {

		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic.dao
				.findById(t_work_id);

		int t_number = infoTeacherBasic.getInt("t_number");

		int number = 0;

		if (t_number != 0) {
			number = t_number
					- InfoTeacherBasic.getSeletedNumberByWorkId(t_work_id);
		}

		return number;
	}

	/**
	 * 
	 * @return
	 */
	public static List<InfoTeacherBasic> getTeacherBaseList() {

		List<InfoTeacherBasic> list = InfoTeacherBasic.getTeacherBaseList();

		for (int i = 0; i < list.size(); i++) {
			addProperties(list.get(i));

		}

		return list;

	}

	public static List<InfoTeacherBasic> getMyTeacherBasicList(String s_id) {

		List<LogicVolunteerResult> logicTeacherStudentList = LogicVolunteerResult
				.getVolunteerResultBySId(s_id);

		String t_work_id = "";
		if (logicTeacherStudentList != null
				&& logicTeacherStudentList.size() > 0) {
			LogicVolunteerResult logicVolunteerResult = logicTeacherStudentList
					.get(0);
			t_work_id = logicVolunteerResult.get("t_work_id");
		}

		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
				.getTmsTeacher(t_work_id);
		List<InfoTeacherBasic> list = new ArrayList<InfoTeacherBasic>();
		list.add(infoTeacherBasic);
		return list;
	}

	public static boolean uploadPDF(String t_work_id, String t_file_path) {
		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
				.getTmsTeacher(t_work_id);

		infoTeacherBasic.set("t_file_path", t_file_path);

		return infoTeacherBasic.update();
	}

}
