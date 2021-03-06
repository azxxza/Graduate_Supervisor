package com.app.model;

import java.util.List;

import com.app.common.TableCommom;
import com.jfinal.plugin.activerecord.Model;

public class InfoStudentScore extends Model<InfoStudentScore> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final InfoStudentScore dao = new InfoStudentScore();
	public static final String INFO_STUDENT_SCORE = TableCommom.INFO_STUDENT_SCORE;

	public static List<InfoStudentScore> findInfoStudentScoreListBySIdAndTime(
			String s_id, int y_t_id) {
		String sql = "select * from " + INFO_STUDENT_SCORE + " where s_id = "
				+ s_id + " and y_t_id = " + y_t_id;

		return dao.find(sql);
	}

	public static List<InfoStudentScore> getInfoStudentScoreListByTime(
			int y_t_id) {
		String sql = "select * from " + INFO_STUDENT_SCORE + " where y_t_id = "
				+ y_t_id;
		return dao.find(sql);
	}
}