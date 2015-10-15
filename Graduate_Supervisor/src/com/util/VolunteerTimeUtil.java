package com.util;

import java.util.Date;

import com.model.SysOpenTime;

public class VolunteerTimeUtil {
	private static VolunteerTimeUtil volunteerTime;
	private Date date;

	private VolunteerTimeUtil() {
		
		SysOpenTime sysOpenTime = SysOpenTime.getSysTime(1);

		Date r_t_end_time = sysOpenTime.getDate("r_t_start_time");

		this.date = r_t_end_time;
	}

	public static VolunteerTimeUtil getVolunteerTime() {
		if (volunteerTime == null) {
			volunteerTime = new VolunteerTimeUtil();
		}

		return volunteerTime;
	}

	public Date getDate() {
		return this.date;
	}
}