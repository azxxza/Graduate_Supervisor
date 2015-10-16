package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

public class LogicTeacherStudent extends Model<LogicTeacherStudent> {

	private static final long serialVersionUID = 1L;
	public static final LogicTeacherStudent dao = new LogicTeacherStudent();

	private static void setName(LogicTeacherStudent logicTeacherStudent) {
		String s_id = logicTeacherStudent.getStr("s_id");

		if ((s_id != null) && (!s_id.equals(""))) {
			InfoStudentBasic infoStudent = InfoStudentBasic.getStudent(s_id);
			if (infoStudent != null) {
				String t_name = infoStudent.getStr("s_name");

				logicTeacherStudent.put("s_name", t_name);
			}
		} else {
			logicTeacherStudent.put("s_name", "");
		}
	}

	public static LogicTeacherStudent getLogicTeacherStudent(String t_work_id,
			String s_id) {
		String sql = "select * from logic_teacher_student where t_work_id = "
				+ t_work_id + " and s_id = " + s_id;
		return dao.findFirst(sql);

	}

	public static QueryResult<LogicTeacherStudent> getLogicTeacherStudentResultByWorkId(
			Integer page, Integer rows, String t_work_id) {
		String sql = "from logic_teacher_student where t_work_id = "
				+ t_work_id;
		try {
			Page<LogicTeacherStudent> pageList = dao.paginate(page.intValue(),
					rows.intValue(), "Select * ", sql);

			List<LogicTeacherStudent> list = pageList.getList();

			for (int i = 0; i < list.size(); i++) {
				setName(list.get(i));
			}

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<LogicTeacherStudent>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean exitLogicTeacherStudentBySId(String s_id) {
		String sql = "select * from logic_teacher_student where s_id = " + s_id;

		List<LogicTeacherStudent> list = dao.find(sql);
		if (list == null || list.size() == 0)
			return false;

		return true;
	}

	public static LogicTeacherStudent getLogicTeacherStudentBySId(String s_id) {
		String sql = "select * from logic_teacher_student where s_id = " + s_id;

		return dao.findFirst(sql);

	}

	
}