package com.model;

import java.util.List;

import com.bean.QueryResultBean;
import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class InfoTeacherBasic extends Model<InfoTeacherBasic> {

	private static final long serialVersionUID = 1L;
	public static final InfoTeacherBasic dao = new InfoTeacherBasic();
	private static final String INFO_TEACHER_BASIC = TableCommom.INFO_TEACHER_BASIC;
	private static final String LOGIC_VOLUNTEER_RESULT = TableCommom.LOGIC_VOLUNTEER_RESULT;

	/**
	 *
	 * @return
	 */
	public static List<InfoTeacherBasic> getTeacherBaseList() {

		List<InfoTeacherBasic> list = dao.find("select * from "
				+ INFO_TEACHER_BASIC);

		return list;

	}

	/**
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public static QueryResultBean<InfoTeacherBasic> getTeacherBaseResult(
			int page, int rows) {

		String sql = "from " + INFO_TEACHER_BASIC;

		Page<InfoTeacherBasic> pageList = dao.paginate(page, rows,
				"Select *  ", sql);

		List<InfoTeacherBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + INFO_TEACHER_BASIC);

		QueryResultBean<InfoTeacherBasic> queryResult = new QueryResultBean<InfoTeacherBasic>(
				count, list);

		return queryResult;
	}

	public static QueryResultBean<InfoTeacherBasic> getTeacherBaseResult(
			int page, int rows, String s_id) {

		String sql = "from " + INFO_TEACHER_BASIC;

		Page<InfoTeacherBasic> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<InfoTeacherBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql);

		QueryResultBean<InfoTeacherBasic> queryResult = new QueryResultBean<InfoTeacherBasic>(
				count, list);

		return queryResult;
	}

	public static InfoTeacherBasic getTmsTeacher(String t_work_id) {

		return dao.findById(t_work_id);

	}

	public static int getSeletedNumberByWorkId(String t_work_id) {

		String sql = "select count(1) from " + LOGIC_VOLUNTEER_RESULT
				+ " where t_work_id =  " + t_work_id;

		Long count = Db.queryLong(sql);

		return count.intValue();
	}

	// public int compareTo(InfoTeacherBasic o) {
	// if (getStr("s_t_volunteer").equals("")) {
	// if (o.getStr("s_t_volunteer").equals("")) {
	// return 0;
	// }
	// return 1;
	// }
	//
	// if (o.getStr("s_t_volunteer").equals("")) {
	// return -1;
	// }
	// return getStr("s_t_volunteer").compareTo(o.getStr("s_t_volunteer"));
	// }

	
}