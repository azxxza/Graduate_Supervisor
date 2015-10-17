package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class VolunteerTime extends Model<VolunteerTime> {

	private static final long serialVersionUID = 1L;

	public static final VolunteerTime dao = new VolunteerTime();

	public static List<VolunteerTime> getVolunteerTimeByRound(int r_t_round) {
		String sql = "select * from sys_volunteer_open_time where r_t_round = "
				+ r_t_round;

		return dao.find(sql);
	}

}