package com.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

public class LogicStudentVolunteer extends Model<LogicStudentVolunteer> {

	private static final long serialVersionUID = 1L;
	public static final LogicStudentVolunteer dao = new LogicStudentVolunteer();

	public static void setName(LogicStudentVolunteer volunteer) {
		String s_id = (String) volunteer.get("s_id");

		if ((s_id != null) && (!s_id.equals(""))) {
			InfoStudentBasic student = InfoStudentBasic.getStudent(s_id);

			if (student != null) {
				String s_name = (String) student.get("s_name");
				volunteer.put("s_name", s_name);

				InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
						.getTmsTeacher((String) volunteer.get("t_work_id"));

				Integer totalCount = (Integer) infoTeacherBasic.get("t_number");

				Long selecedtCount = InfoTeacherBasic
						.getTeacherStudentNumberByWorkId((String) volunteer
								.get("t_work_id"));

				Long count = Long.valueOf(totalCount.longValue()
						- selecedtCount.longValue());

				volunteer.put("count", count);
			}
		} else {
			volunteer.put("s_name", "");
			volunteer.put("count", Integer.valueOf(0));
		}
	}

	public static LogicStudentVolunteer getVolunteerByWorkIdAndSId(String s_id,
			String t_work_id) {
		try {
			List<LogicStudentVolunteer> list = dao
					.find("select * from logic_student_volunteer where t_work_id = "
							+ t_work_id + " and s_id = " + s_id);

			System.out
					.println("select * from l_student_volunteer where t_work_id = "
							+ t_work_id + " and s_id = " + s_id);
			if ((list != null) && (list.size() > 0)) {
				return (LogicStudentVolunteer) list.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static LogicStudentVolunteer getVolunteerBySId(String s_id) {

		String sql = "select * from l_student_volunteer where s_id = " + s_id;

		return dao.findFirst(sql);

	}

	public static QueryResult<LogicStudentVolunteer> getVolunteerResultByWorkId(
			Integer page, Integer rows, String t_work_id) {
		String sql = "from l_student_volunteer where t_work_id = '" + t_work_id
				+ "' and s_is_selected = 0";

		System.out.println(sql);
		try {
			Page<LogicStudentVolunteer> pageList = dao.paginate(
					page.intValue(), rows.intValue(), "Select * ", sql);

			List<LogicStudentVolunteer> list = pageList.getList();

			for (int i = 0; i < list.size(); i++) {
				setName((LogicStudentVolunteer) list.get(i));
			}

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<LogicStudentVolunteer>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static QueryResult<LogicStudentVolunteer> getVolunteerResultBySId(
			String s_id) {

		String sql = " from logic_student_volunteer where s_id = " + s_id;

		List<LogicStudentVolunteer> list = dao.find("select * " + sql);

		long count = 0;
		if (list != null) {
			count = list.size();
		}

		return new QueryResult<LogicStudentVolunteer>(count, list);
	}

	public static boolean exitVolunteer(String s_id, String s_t_volunteer) {
		String sql = "select count(1) from l_student_volunteer where s_id = "
				+ s_id + " and s_t_volunteer = '" + s_t_volunteer + "'";
		Long count = Db.queryLong(sql);

		if (count.longValue() > 0) {
			return true;
		}
		return false;
	}

	public static List<LogicStudentVolunteer> getNotSelectedByWorkId(
			String t_work_id) {
		String sql = "select * from l_student_volunteer where t_work_id = "
				+ t_work_id;

		return dao.find(sql);

	}

	public static boolean deleteNotSelectedByWorkId(String t_work_id) {
		List<LogicStudentVolunteer> list = getNotSelectedByWorkId(t_work_id);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				boolean flag = ((LogicStudentVolunteer) list.get(i)).delete();
				if (!flag) {
					return false;
				}
			}
		}

		return true;
	}

	public static List<LogicStudentVolunteer> getLogicStudentVolunteerListBySId(
			String s_id) {
		String sql = "select * from l_student_volunteer where s_id = " + s_id;

		return dao.find(sql);
	}

	public static boolean deleteVolunteerBySId(String s_id) {
		boolean flag = true;
		List<LogicStudentVolunteer> list = getLogicStudentVolunteerListBySId(s_id);
		if ((list != null) && (list.size() > 0)) {
			for (int i = 0; i < list.size(); i++) {
				flag = ((LogicStudentVolunteer) list.get(i)).delete();
				if (!flag) {
					return false;
				}
			}

		}

		return true;
	}
}