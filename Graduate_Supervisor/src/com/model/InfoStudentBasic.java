package com.model;

import java.util.List;

import com.bean.QueryResultBean;
import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class InfoStudentBasic extends Model<InfoStudentBasic> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final InfoStudentBasic dao = new InfoStudentBasic();
	private static final String INFO_STUDENT_BASIC = TableCommom.INFO_STUDENT_BASIC;
	private static final String LOGIC_DO_VOLUNTEER = TableCommom.LOGIC_DO_VOLUNTEER;
	private static final String LOGIC_VOLUNTEER_RESULT = TableCommom.LOGIC_VOLUNTEER_RESULT;

	public static InfoStudentBasic getStudent(String s_id) {

		return dao.findById(s_id);

	}

	public static QueryResultBean<InfoStudentBasic> getStudentResult(int page,
			int rows) {

		QueryResultBean<InfoStudentBasic> queryResult = new QueryResultBean<InfoStudentBasic>();

		String sql = "from " + INFO_STUDENT_BASIC;

		Page<InfoStudentBasic> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<InfoStudentBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1)  " + sql);

		queryResult = new QueryResultBean<InfoStudentBasic>(count, list);

		return queryResult;

	}

	public static QueryResultBean<InfoStudentBasic> getStudentResultWithVolunteerByWorkId(
			int page, int rows, String t_work_id) {

		QueryResultBean<InfoStudentBasic> queryResult = new QueryResultBean<InfoStudentBasic>();

		String sql = "from " + INFO_STUDENT_BASIC + " i ," + LOGIC_DO_VOLUNTEER
				+ " l where  l.t_work_id = " + t_work_id
				+ " and i.s_id = l.s_id";

		Page<InfoStudentBasic> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<InfoStudentBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql).longValue();

		queryResult = new QueryResultBean<InfoStudentBasic>(count, list);

		return queryResult;
	}

	public static QueryResultBean<InfoStudentBasic> getStudentResultWithSelectedByWorkId(
			int page, int rows, String t_work_id) {

		QueryResultBean<InfoStudentBasic> queryResult = new QueryResultBean<InfoStudentBasic>();

		String sql = "from " + INFO_STUDENT_BASIC + " i ,"
				+ LOGIC_VOLUNTEER_RESULT + " l where l.t_work_id = "
				+ t_work_id + " and i.s_id = l.s_id";

		Page<InfoStudentBasic> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<InfoStudentBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql);

		queryResult = new QueryResultBean<InfoStudentBasic>(count, list);

		return queryResult;
	}

	public static QueryResultBean<InfoStudentBasic> getNotSelectedStudentResult(
			int page, int rows) {

		QueryResultBean<InfoStudentBasic> queryResult = new QueryResultBean<InfoStudentBasic>();

		String sql = " from " + INFO_STUDENT_BASIC
				+ " i where s_id not in (select s_id from "
				+ LOGIC_VOLUNTEER_RESULT + ")";

		Page<InfoStudentBasic> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<InfoStudentBasic> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql).longValue();

		queryResult = new QueryResultBean<InfoStudentBasic>(count, list);

		return queryResult;
	}
}