package com.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.util.QueryResult;

public class InfoTeacherBasic extends Model<InfoTeacherBasic> implements
		Comparable<InfoTeacherBasic> {

	private static final long serialVersionUID = 1L;
	public static final InfoTeacherBasic dao = new InfoTeacherBasic();

	private static void setVolunteer(InfoTeacherBasic teacherBasic, String s_id) {
		if (s_id != null && !s_id.equals("")) {
			teacherBasic.put("t_number_copy",teacherBasic.getInt("t_number"));
			LogicStudentVolunteer volunteer = LogicStudentVolunteer
					.getVolunteerByWorkIdAndSId(s_id,
							teacherBasic.getStr("t_work_id"));
			if (volunteer != null) {
				String s_t_volunteer = volunteer.getStr("s_t_volunteer");

				teacherBasic.put("s_t_volunteer", s_t_volunteer);
			} else {
				teacherBasic.put("s_t_volunteer", "");
			}
		} else {
			teacherBasic.put("s_t_volunteer", "");
		}
	}

	private static void setTotalNumber(InfoTeacherBasic teacherBasic,
			String s_id) {
		String t_work_id = teacherBasic.getStr("t_work_id");

		int rest_number = getTeacherStudentRestNumberByWorkId(t_work_id);

		teacherBasic.put("rest_number", rest_number);

		LogicStudentVolunteer volunteer = LogicStudentVolunteer
				.getVolunteerByWorkIdAndSId(s_id, t_work_id);

		if (volunteer != null) {
			teacherBasic.put("s_t_status", "待定");
		} else {
			teacherBasic.put("s_t_status", "");
		}
	}

	public static QueryResult<InfoTeacherBasic> getTeacherBaseResult(int page,
			int rows, String s_id) {
		String sql = "from info_teacher_basic";
		try {
			List<InfoTeacherBasic> list = null;

			if (page == 0 && rows == 0) {
				list = dao.find("select * " + sql);
			} else {
				Page<InfoTeacherBasic> pageList = dao.paginate(page, rows,
						"Select * ", sql);

				list = pageList.getList();
			}

			if (s_id != null && !s_id.equals("")) {
				for (int i = 0; i < list.size(); i++) {
					setVolunteer(list.get(i), s_id);
					setTotalNumber(list.get(i), s_id);
				}

			}

			long count = Db.queryLong("select count(1) " + sql).longValue();

			return new QueryResult<InfoTeacherBasic>(count, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResult<InfoTeacherBasic>(0,
				new ArrayList<InfoTeacherBasic>());
	}

	public static InfoTeacherBasic getTmsTeacher(String t_work_id) {

		return dao.findById(t_work_id);

	}

	public static int getTeacherStudentRestNumberByWorkId(String t_work_id) {
		InfoTeacherBasic infoTeacherBasic = dao.findById(t_work_id);

		int t_number = infoTeacherBasic.getInt("t_number");

		int number = 0;

		if (t_number != 0) {
			number = t_number - getTeacherStudentNumberByWorkId(t_work_id);
		}

		return number;
	}

	public static int getTeacherStudentNumberByWorkId(String t_work_id) {
		String sql = "select count(1) from logic_teacher_student where t_work_id =  "
				+ t_work_id;

		Long count = Db.queryLong(sql);

		return count.intValue();
	}

	public int compareTo(InfoTeacherBasic o) {
		if (getStr("s_t_volunteer").equals("")) {
			if (o.getStr("s_t_volunteer").equals("")) {
				return 0;
			}
			return 1;
		}

		if (o.getStr("s_t_volunteer").equals("")) {
			return -1;
		}
		return getStr("s_t_volunteer").compareTo(o.getStr("s_t_volunteer"));
	}

	public static List<InfoTeacherBasic> getInfoTeacherBasicList(String s_id) {
		LogicTeacherStudent logicTeacherStudent = LogicTeacherStudent
				.getLogicTeacherStudentBySId(s_id);
		String t_work_id = "";
		if (logicTeacherStudent != null) {
			t_work_id = (String) logicTeacherStudent.get("t_work_id");
		}

		if (t_work_id == "") {
			return null;
		}
		String sql = "select * from info_teacher_basic where t_work_id = "
				+ t_work_id;
		try {
			return dao.find(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}