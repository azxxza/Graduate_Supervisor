package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

public class InfoStudentBasic extends Model<InfoStudentBasic> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final InfoStudentBasic dao = new InfoStudentBasic();

	public static InfoStudentBasic getStudent(String s_id) {

		return dao.findById(s_id);

	}

	public static QueryResult<InfoStudentBasic> getStudentResult(Integer page,
			Integer rows) {

		QueryResult<InfoStudentBasic> queryResult = new QueryResult<InfoStudentBasic>();

		List<InfoStudentBasic> list = null;

		String sql = "from info_student_basic";

		long count = 0;

		try {

			if (page == 0 && rows == 0) {
				list = dao.find("select * " + sql);

			} else {
				Page<InfoStudentBasic> pageList = dao.paginate(page.intValue(),
						rows.intValue(), "Select * ", sql);

				list = pageList.getList();

				count = Db.queryLong("select count(1) " + sql).longValue();
			}

			queryResult = new QueryResult<InfoStudentBasic>(count, list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResult;
	}

	public static QueryResult<InfoStudentBasic> getStudentResultWithVolunteerByWorkId(
			int page, int rows, String t_work_id) {

		QueryResult<InfoStudentBasic> queryResult = new QueryResult<InfoStudentBasic>();

		String sql = "from info_student_basic i ,logic_student_volunteer l where  l.t_work_id = "
				+ t_work_id + " and i.s_id = l.s_id";
		try {
			Page<InfoStudentBasic> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentBasic> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			queryResult = new QueryResult<InfoStudentBasic>(count, list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResult;
	}

	public static QueryResult<InfoStudentBasic> getStudentResultWithSelectedByWorkId(
			int page, int rows, String t_work_id) {

		QueryResult<InfoStudentBasic> queryResult = new QueryResult<InfoStudentBasic>();

		String sql = "from info_student_basic i ,logic_teacher_student l where  l.t_work_id = "
				+ t_work_id + " and i.s_id = l.s_id";

		System.out.println(sql);
		try {
			Page<InfoStudentBasic> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentBasic> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			queryResult = new QueryResult<InfoStudentBasic>(count, list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResult;
	}

	public static QueryResult<InfoStudentBasic> getNotSelectedStudentResult(
			int page, int rows) {

		QueryResult<InfoStudentBasic> queryResult = new QueryResult<InfoStudentBasic>();

		String sql = " from info_student_basic i where s_id not in (select s_id from logic_teacher_student)";
		try {
			Page<InfoStudentBasic> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentBasic> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			queryResult = new QueryResult<InfoStudentBasic>(count, list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryResult;
	}
}