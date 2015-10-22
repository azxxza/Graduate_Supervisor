package com.app.model;

import java.util.List;

import com.app.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

public class LogicDoVolunteer extends Model<LogicDoVolunteer> {

	private static final long serialVersionUID = 1L;
	public static final LogicDoVolunteer dao = new LogicDoVolunteer();
	private static final String LOGIC_DO_VOLUNTEER = TableCommom.LOGIC_DO_VOLUNTEER;

	public static LogicDoVolunteer getVolunteerByWorkIdAndSId(String s_id,
			String t_work_id) {

		String sql = "select * from " + LOGIC_DO_VOLUNTEER
				+ " where t_work_id = " + t_work_id + " and s_id = " + s_id;

		return dao.findFirst(sql);

	}

	public static List<LogicDoVolunteer> findVolunteerListBySId(String s_id) {

		String sql = " from " + LOGIC_DO_VOLUNTEER + " where s_id = " + s_id;

		return dao.find("select * " + sql);

	}

	public static int getVolunteerCount(String s_id, String s_t_volunteer) {
		String sql = "select count(1) from " + LOGIC_DO_VOLUNTEER
				+ " where s_id = " + s_id + " and s_t_volunteer = '"
				+ s_t_volunteer + "'";
		Long count = Db.queryLong(sql);

		return count.intValue();
	}

	public static List<LogicDoVolunteer> findNotSelectedByWorkId(String t_work_id) {

		String sql = "select * from " + LOGIC_DO_VOLUNTEER
				+ " where t_work_id = " + t_work_id;

		return dao.find(sql);

	}

}