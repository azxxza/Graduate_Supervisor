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

		String sql = "select * from " + INFO_TEACHER_BASIC;

		return dao.find(sql);

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

		List<InfoTeacherBasic> list = null;

		long count = 0;

		Page<InfoTeacherBasic> pageList = dao.paginate(page, rows,
				"Select *  ", sql);

		list = pageList.getList();

		count = Db.queryLong("select count(1) " + sql);

		return new QueryResultBean<InfoTeacherBasic>(count, list);

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

}