package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class InfoStudentScore extends Model<InfoStudentScore> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final InfoStudentScore dao = new InfoStudentScore();

	public static List<InfoStudentScore> getInfoStudentScoreListBySIdAndTime(
			String s_id, String y_t_id) {
		String sql = "select * from info_student_score where s_id = " + s_id
				+ " and y_t_id = " + y_t_id;

		return dao.find(sql);
	}
}