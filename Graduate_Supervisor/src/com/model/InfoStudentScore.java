package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

public class InfoStudentScore extends Model<InfoStudentScore> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final InfoStudentScore dao = new InfoStudentScore();

	public static InfoStudentScore getStudent(String s_id) {
		try {
			return (InfoStudentScore) dao.findById(s_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryResult<InfoStudentScore> getStudentResult(Integer page,
			Integer rows) {
		String sql = "from i_student";
		try {
			Page<InfoStudentScore> pageList = dao.paginate(page.intValue(),
					rows.intValue(), "Select * ", sql);

			List<InfoStudentScore> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<InfoStudentScore>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryResult<InfoStudentScore> getStudentResultWithVolunteerByWorkId(
			int page, int rows, String t_work_id) {
		String sql = "from i_student i ,l_student_volunteer l where  l.t_work_id = "
				+ t_work_id + " and i.s_id = l.s_id";
		try {
			Page<InfoStudentScore> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentScore> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<InfoStudentScore>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryResult<InfoStudentScore> getStudentResultWithSelectedByWorkId(
			int page, int rows, String t_work_id) {
		String sql = "from i_student i ,l_teacher_student l where  l.t_work_id = "
				+ t_work_id + " and i.s_id = l.s_id";

		System.out.println(sql);
		try {
			Page<InfoStudentScore> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentScore> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<InfoStudentScore>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryResult<InfoStudentScore> getNotSelectedStudentResult(
			int page, int rows) {
		String sql = " from i_student i where s_id not in (select s_id from l_teacher_student)";
		try {
			Page<InfoStudentScore> pageList = dao.paginate(page, rows,
					"Select * ", sql);

			List<InfoStudentScore> list = pageList.getList();

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<InfoStudentScore>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}