package com.model;

import java.util.List;

import com.bean.QueryResultBean;
import com.common.TableCommom;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class LogicDoVolunteer extends Model<LogicDoVolunteer> {

	private static final long serialVersionUID = 1L;
	public static final LogicDoVolunteer dao = new LogicDoVolunteer();
	private static final String LOGIC_DO_VOLUNTEER = TableCommom.LOGIC_DO_VOLUNTEER;

	public static void setName(LogicDoVolunteer volunteer) {
		String s_id = volunteer.getStr("s_id");

		if (s_id != null && !s_id.equals("")) {
			InfoStudentBasic student = InfoStudentBasic.getStudent(s_id);

			if (student != null) {
				String s_name = student.getStr("s_name");
				volunteer.put("s_name", s_name);

				InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
						.getTmsTeacher(volunteer.getStr("t_work_id"));

				Integer totalCount = infoTeacherBasic.getInt("t_number");

				int selecedtCount = InfoTeacherBasic
						.getSeletedNumberByWorkId(volunteer.getStr("t_work_id"));

				int count = totalCount - selecedtCount;

				volunteer.put("count", count);
			}
		} else {
			volunteer.put("s_name", "");
			volunteer.put("count", 0);
		}
	}

	public static LogicDoVolunteer getVolunteerByWorkIdAndSId(String s_id,
			String t_work_id) {

		String sql = "select * from " + LOGIC_DO_VOLUNTEER
				+ " where t_work_id = " + t_work_id + " and s_id = " + s_id;

		return dao.findFirst(sql);

	}

	public static LogicDoVolunteer getVolunteerBySId(String s_id) {

		String sql = "select * from " + LOGIC_DO_VOLUNTEER + " where s_id = "
				+ s_id;

		return dao.findFirst(sql);

	}

	public static QueryResultBean<LogicDoVolunteer> getVolunteerResultByWorkId(
			int page, int rows, String t_work_id) {

		String sql = "from " + LOGIC_DO_VOLUNTEER + " where t_work_id = '"
				+ t_work_id + "' and s_is_selected = 0";

		Page<LogicDoVolunteer> pageList = dao.paginate(page, rows, "Select * ",
				sql);

		List<LogicDoVolunteer> list = pageList.getList();

		for (int i = 0; i < list.size(); i++) {
			setName(list.get(i));
		}

		long count = Db.queryLong("select count(1) " + sql).longValue();

		return new QueryResultBean<LogicDoVolunteer>(count, list);

	}

	public static QueryResultBean<LogicDoVolunteer> getVolunteerResultBySId(
			String s_id) {

		String sql = " from " + LOGIC_DO_VOLUNTEER + " where s_id = " + s_id;

		List<LogicDoVolunteer> list = dao.find("select * " + sql);

		long count = 0;
		if (list != null) {
			count = list.size();
		}

		return new QueryResultBean<LogicDoVolunteer>(count, list);
	}

	public static int getVolunteer(String s_id, String s_t_volunteer) {
		String sql = "select count(1) from logic_student_volunteer where s_id = "
				+ s_id + " and s_t_volunteer = '" + s_t_volunteer + "'";
		Long count = Db.queryLong(sql);

		return count.intValue();
	}

	public static List<LogicDoVolunteer> getNotSelectedByWorkId(String t_work_id) {

		String sql = "select * from logic_student_volunteer where t_work_id = "
				+ t_work_id;

		return dao.find(sql);

	}

	public static List<LogicDoVolunteer> getLogicStudentVolunteerListBySId(
			String s_id) {
		String sql = "select * from logic_student_volunteer where s_id = "
				+ s_id;

		return dao.find(sql);
	}

}