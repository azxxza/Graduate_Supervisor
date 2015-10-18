package com.model;

import java.util.List;

import com.bean.QueryResultBean;
import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class LogicVolunteerResult extends Model<LogicVolunteerResult> {

	private static final long serialVersionUID = 1L;
	public static final LogicVolunteerResult dao = new LogicVolunteerResult();
	private static final String LOGIC_VOLUNTEER_RESULT = TableCommom.LOGIC_VOLUNTEER_RESULT;

	

	public static LogicVolunteerResult getVolunteerResult(String t_work_id,
			String s_id) {
		String sql = "select * from logic_teacher_student where t_work_id = "
				+ t_work_id + " and s_id = " + s_id;
		return dao.findFirst(sql);

	}

	public static QueryResultBean<LogicVolunteerResult> getVolunteerResultByWorkId(
			Integer page, Integer rows, String t_work_id) {
		String sql = "from " + LOGIC_VOLUNTEER_RESULT + " where t_work_id = "
				+ t_work_id;

		Page<LogicVolunteerResult> pageList = dao.paginate(page.intValue(),
				rows.intValue(), "Select * ", sql);

		List<LogicVolunteerResult> list = pageList.getList();

		long count = Db.queryLong("select count(1) " + sql).longValue();

		return new QueryResultBean<LogicVolunteerResult>(count, list);

	}

	public static List<LogicVolunteerResult> getVolunteerResultBySId(String s_id) {
		String sql = "select * from " + LOGIC_VOLUNTEER_RESULT
				+ " where s_id = " + s_id;

		return dao.find(sql);

	}
}