package com.app.model;

import java.util.List;

import com.app.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class SysTeacherRoundTime extends Model<SysTeacherRoundTime> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final SysTeacherRoundTime dao = new SysTeacherRoundTime();
	private static final String SYS_TEACHER_ROUND_TIME = TableCommom.SYS_TEACHER_ROUND_TIME;

	public static SysTeacherRoundTime getTeacherRoundTime(int r_t_round) {
		String sql = "select * from " + SYS_TEACHER_ROUND_TIME
				+ " where r_t_round = " + r_t_round;
		return dao.findFirst(sql);

	}

	public static List<SysTeacherRoundTime> findTeacherRoundTimeList() {
		String sql = "select * from " + SYS_TEACHER_ROUND_TIME;

		return dao.find(sql);
	}

	public static int getMaxRound() {
		String sql = "select max(r_t_round) from " + SYS_TEACHER_ROUND_TIME;

		Integer max = Db.queryInt(sql);

		if (max != null)
			max = max.intValue() + 1;
		else {
			max = 1;
		}

		return max;
	}
}