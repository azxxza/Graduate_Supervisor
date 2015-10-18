package com.service;

import java.util.List;

import com.model.InfoStudentBasic;
import com.model.SysYearTerm;

public class StudentBasicService {
	/*
	 * 成绩学年学期列表
	 */
	public List<SysYearTerm> getSysYearTermList(String s_id) {

		List<SysYearTerm> list = SysYearTerm.getYearTermList();

		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("s_id", s_id);
		}

		return list;

	}

	/*
	 * 删除学生
	 */
	public boolean deleteStudent(String s_id) {

		InfoStudentBasic infoStudentBasic = InfoStudentBasic.getStudent(s_id);

		return infoStudentBasic.delete();

	}
}
