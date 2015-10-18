package com.model;

import java.util.List;

import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class SysOpenTime extends Model<SysOpenTime> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final SysOpenTime dao = new SysOpenTime();
	private static final String SYS_ROUND_OPEN_TIME = TableCommom.SYS_ROUND_OPEN_TIME;

	public static SysOpenTime getSysTime(int r_t_round) {
		String sql = "select * from " + SYS_ROUND_OPEN_TIME
				+ " where r_t_round = " + r_t_round;
		return dao.findFirst(sql);

	}

	public static List<SysOpenTime> getOpenTimeList() {
		String sql = "select * from " + SYS_ROUND_OPEN_TIME;

		return dao.find(sql);
	}

	public static int getMaxRound() {
		String sql = "select max(r_t_round) from " + SYS_ROUND_OPEN_TIME;

		Integer max = Db.queryInt(sql);

		if (max != null)
			max = max.intValue() + 1;
		else {
			max = 1;
		}

		return max;
	}
}