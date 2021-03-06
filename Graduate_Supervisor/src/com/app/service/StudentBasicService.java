package com.app.service;

import java.util.List;

import com.app.model.InfoStudentBasic;
import com.app.model.SysYearTerm;

public class StudentBasicService {
	/*
	 * 成绩学年学期列表
	 */
	public static List<SysYearTerm> getSysYearTermList(String s_id) {

		List<SysYearTerm> list = SysYearTerm.getYearTermList();

		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("s_id", s_id);
		}

		return list;

	}

	/*
	 * 删除学生
	 */
	public static boolean deleteStudent(String s_id) {

		InfoStudentBasic infoStudentBasic = InfoStudentBasic.getStudent(s_id);

		return infoStudentBasic.delete();

	}
}
