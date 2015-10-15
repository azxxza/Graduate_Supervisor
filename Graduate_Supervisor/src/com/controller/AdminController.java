package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.common.XlsMain;
import com.jfinal.core.JFinal;
import com.model.InfoTeacherBasic;
import com.model.LogicTeacherStudent;
import com.model.SysOpenTime;
import com.model.VolunteerTime;
import com.util.ExcelExportUtil;
import com.util.MessageBean;
import com.util.QueryResult;

public class AdminController extends BaseController {
	public void openTime() {
		SysOpenTime sysTime = SysOpenTime.getSysTime(1);

		setSessionAttr("sysTime", sysTime);

		render("editOpenTime.jsp");
	}

	public void saveOpenTime() {
		SysOpenTime sysTime = (SysOpenTime) getModel(SysOpenTime.class,
				"sysTime");

		MessageBean messageBean = new MessageBean();

		boolean flag = sysTime.save();

		messageBean.setFlag(flag);

		renderJson(messageBean);
	}

	public void updateVolunteersTime() {
		MessageBean messageBean = new MessageBean();

		String para = getPara("para");

		if ((para != null) && (!para.equals(""))) {
			String[] pairArray = para.split(";");

			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String v_t_id = elementArray[0];
				String v_t_start_time = elementArray[1];
				String v_t_end_time = elementArray[2];

				if ((v_t_end_time != null) && (v_t_start_time != null)) {
					VolunteerTime volunteerTime = (VolunteerTime) VolunteerTime.dao
							.findById(Integer.valueOf(Integer.parseInt(v_t_id)));

					System.out.println(v_t_end_time + "\t" + v_t_start_time);

					volunteerTime.set("v_t_start_time", v_t_start_time);
					volunteerTime.set("v_t_end_time", v_t_end_time);

					volunteerTime.update();
				}

			}

			messageBean.setFlag(true);
		} else {
			messageBean.setFlag(false);
			messageBean.setMessage("提交参数为空");
		}

		renderJson(messageBean);
	}

	public void updateOpenTime() {
		MessageBean messageBean = new MessageBean();

		String para = getPara("para");

		if ((para != null) && (!para.equals(""))) {
			String[] pairArray = para.split(";");

			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String r_t_id = elementArray[0];
				String r_t_start_time = elementArray[1];
				String r_t_end_time = elementArray[2];

				SysOpenTime sysOpenTime = (SysOpenTime) SysOpenTime.dao
						.findById(Integer.valueOf(Integer.parseInt(r_t_id)));

				sysOpenTime.set("r_t_start_time", r_t_start_time);
				sysOpenTime.set("r_t_end_time", r_t_end_time);

				sysOpenTime.update();
			}

			messageBean.setFlag(true);
		} else {
			messageBean.setFlag(false);
			messageBean.setMessage("提交参数为空");
		}

		renderJson(messageBean);
	}

	public void openTimeList() {
		render("openTimeList.jsp");
	}

	public void getOpenTimeList() {
		List<SysOpenTime> list = SysOpenTime.getOpenTimeList();

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}

	public void allocNumberList() {
		render("allocNumberList.jsp");
	}

	public void getAllocNumberList() {
		QueryResult<InfoTeacherBasic> queryResult = null;

		int page = getParaToInt("page").intValue();
		int rows = getParaToInt("rows").intValue();

		String s_id = getId();

		queryResult = InfoTeacherBasic.getTeacherBaseResult(page, rows, s_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());
		jsonMap.put("total", Long.valueOf(queryResult.getCount()));

		renderJson(jsonMap);
	}

	public void teacherProcessList() {
		render("teacherProcessList.jsp");
	}

	public void studentProcessList() {
		render("unselectStudentList.jsp");
	}

	public void importStudent() {
		render("importStudent.jsp");

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/Excel/12计算机1.xls");

		boolean flag = new XlsMain().parseExcel(realpath);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(true);

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	public void importTeacher() {
		render("teacherBaseList.jsp");
	}

	public void saveTeacherStudent() {
		MessageBean messageBean = new MessageBean();
		String para = getPara("para");
		if ((para != null) && (!para.equals(""))) {
			String[] pairArray = para.split(";");
			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String s_id = elementArray[0];
				String t_work_id = elementArray[1];
				LogicTeacherStudent logicTeacherStudent = LogicTeacherStudent
						.getLogicTeacherStudent(t_work_id, s_id);
				if (logicTeacherStudent == null) {
					logicTeacherStudent = new LogicTeacherStudent();
				}

				logicTeacherStudent.set("t_work_id", t_work_id);
				logicTeacherStudent.set("s_id", s_id);

				MessageBean messageBean2 = LogicTeacherStudent
						.saveLogicTeacherStudent(logicTeacherStudent);

				messageBean.setFlag(messageBean2.getFlag());
				messageBean.setMessage(messageBean2.getMessage());
			}

		}

		renderJson(messageBean);
	}

	public void exportAll() {
		QueryResult<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(0, 0, "");

		List<InfoTeacherBasic> list = queryResult.getList();
		try {
			ExcelExportUtil.exportByRecord(getResponse(), getRequest(), "验收信息",
					list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		renderNull();
	}

	public void uploadExcel() {
		MessageBean messageBean = new MessageBean();

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");
		try {
			getFile("excel", realpath, Integer.valueOf(209715200), "UTF-8");

			messageBean.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
			messageBean.setFlag(false);
			messageBean.setMessage("文件上传失败");
		}

		renderNull();
	}

	public void addOpenTime() {
		MessageBean messageBean = new MessageBean();

		SysOpenTime s_round_open_time = (SysOpenTime) getModel(
				SysOpenTime.class, "s_round_open_time");

		int r_t_round = SysOpenTime.getMaxRound().intValue();

		s_round_open_time.set("r_t_round", Integer.valueOf(r_t_round));

		setSessionAttr("r_t_round", Integer.valueOf(r_t_round));

		boolean flag = s_round_open_time.save();

		messageBean.setFlag(flag);

		if (flag) {
			for (int i = 0; i < 5; i++) {
				VolunteerTime volunteerTime = new VolunteerTime();
				volunteerTime.set("r_t_round", Integer.valueOf(r_t_round));
				volunteerTime.set("v_volunteer", Integer.valueOf(i + 1));
				volunteerTime.save();
			}

		}

		if (!flag) {
			messageBean.setMessage("操作失败");
		}

		renderJson(messageBean);
	}

	public void addVolunteerTimeList() {
		String r_t_round = getPara("r_t_round");

		setSessionAttr("r_t_round", r_t_round);

		List<VolunteerTime> volunteerTimeList = VolunteerTime.dao
				.find("select * from s_volunteer_open_time where r_t_round = "
						+ r_t_round);

		setSessionAttr("volunteerTimeList", volunteerTimeList);

		render("volunteerTimeList.jsp");
	}

	public void getVolunteerTimeList() {
		String r_t_round = (String) getSessionAttr("r_t_round");

		List<VolunteerTime> list = VolunteerTime.dao
				.find("select * from s_volunteer_open_time where r_t_round = "
						+ r_t_round);

		System.out
				.println("select * from s_volunteer_open_time where r_t_round = "
						+ r_t_round);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);
	}
}