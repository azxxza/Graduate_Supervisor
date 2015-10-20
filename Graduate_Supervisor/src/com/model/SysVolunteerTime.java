package com.model;

import java.util.List;

import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Model;

public class SysVolunteerTime extends Model<SysVolunteerTime> {

	private static final long serialVersionUID = 1L;

	public static final SysVolunteerTime dao = new SysVolunteerTime();

	private static final String SYS_VOLUNTEER_OPEN_TIME = TableCommom.SYS_VOLUNTEER_OPEN_TIME;

	public static List<SysVolunteerTime> getVolunteerTimeByRound(int r_t_round) {
		String sql = "select * from " + SYS_VOLUNTEER_OPEN_TIME
				+ " where r_t_round = " + r_t_round;

		return dao.find(sql);
	}
	
	

}