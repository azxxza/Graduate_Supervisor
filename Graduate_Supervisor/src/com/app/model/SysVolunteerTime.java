package com.app.model;

import java.util.List;

import com.app.common.TableCommom;
import com.jfinal.plugin.activerecord.Model;

public class SysVolunteerTime extends Model<SysVolunteerTime> {

	private static final long serialVersionUID = 1L;

	public static final SysVolunteerTime dao = new SysVolunteerTime();

	private static final String SYS_VOLUNTEER_TIME = TableCommom.SYS_VOLUNTEER_TIME;

	public static List<SysVolunteerTime> findVolunteerTimeByRound(int r_t_round) {
		String sql = "select * from " + SYS_VOLUNTEER_TIME
				+ " where r_t_round = " + r_t_round;

		return dao.find(sql);
	}
	
	

}