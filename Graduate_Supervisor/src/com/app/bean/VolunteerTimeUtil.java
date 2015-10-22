package com.app.bean;

import java.util.Date;

import com.app.model.SysTeacherRoundTime;

public class VolunteerTimeUtil {

	private static VolunteerTimeUtil volunteerTime;
	private Date date;

	private VolunteerTimeUtil(int t_round) {

		SysTeacherRoundTime sysOpenTime = SysTeacherRoundTime.getTeacherRoundTime(t_round);

		Date r_t_end_time = sysOpenTime.getDate("r_t_start_time");

		this.date = r_t_end_time;
	}

	public static VolunteerTimeUtil getVolunteerTime() {
		if (volunteerTime == null) {
			volunteerTime = new VolunteerTimeUtil(1);
		}

		return volunteerTime;
	}

	public Date getDate() {
		return this.date;
	}
}