package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class SysOpenTime extends Model<SysOpenTime> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final SysOpenTime dao = new SysOpenTime();

	public static SysOpenTime getSysTime(int r_t_round) {
		String sql = "select * from sys_round_open_time where r_t_round = "
				+ r_t_round;
		return dao.findFirst(sql);

	}

	public static List<SysOpenTime> getOpenTimeList() {
		String sql = "select * from sys_round_open_time";

		return dao.find(sql);
	}

	public static Integer getMaxRound() {
		String sql = "select max(r_t_round) from sys_round_open_time";

		Integer max = Db.queryInt(sql);

		if (max != null)
			max = Integer.valueOf(max.intValue() + 1);
		else {
			max = Integer.valueOf(1);
		}

		return max;
	}
}