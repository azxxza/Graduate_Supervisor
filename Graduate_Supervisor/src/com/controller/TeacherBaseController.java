package com.controller;

import java.util.HashMap;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.model.LogicTeacherStudent;
import com.service.TeacherBasicService;
import com.system.CurrentExcuteVolunteer;
import com.util.MessageBean;
import com.util.QueryResult;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class TeacherBaseController extends BaseController {

	private TeacherBasicService teacherBasicService = new TeacherBasicService();

	public void candidateTeacherList() {
		String s_id = getId();

		boolean flag = LogicTeacherStudent.exitLogicTeacherStudentBySId(s_id);

		// 返回我的导师列表
		if (flag) {
			render("myTeacher.jsp");
			// 返回志愿列表
		} else {
			CurrentExcuteVolunteer currentExcuteVolunteer = CurrentExcuteVolunteer
					.getCurrentExcuteVolunteer();

			boolean isOpen = currentExcuteVolunteer.isRunning();

			setAttr("isOpen", true);

			render("candidate_teacher.jsp");

		}
	}

	/**
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows, getId());

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/**
	 * 查看教师的详细信息，返回到详细信息列表
	 * 
	 * @return
	 */
	public void detail() {

		String t_work_id = getPara("t_work_id");

		setAttr("t_work_id", t_work_id);

		render("detail.jsp");

	}

	public void doVolunteer() {

		String para = getPara("para");

		if (para != null && !para.equals("")) {
			String[] pairArray = para.split(";");

			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String t_work_id = elementArray[0];
				String select = elementArray[1];
				InfoTeacherBasic tmsTeacher = InfoTeacherBasic
						.getTmsTeacher(t_work_id);
				tmsTeacher.set("t_select", select);
				tmsTeacher.update();
				MessageBean messageBean = new MessageBean();
				messageBean.setFlag(true);
				renderJson(messageBean);
			}
		}

		renderNull();

	}

	public void saveTeacherNumber() {

		MessageBean messageBean = new MessageBean();

		int sucessCount = 0;

		String para = getPara("para");

		if (para != null && !para.equals("")) {

			String[] pairArray = para.split(";");

			for (int i = 0; i < pairArray.length; i++) {
				String pair = pairArray[i];
				String[] elementArray = pair.split(",");
				String t_work_id = elementArray[0];
				String t_number = elementArray[1];

				boolean flag = teacherBasicService.saveTeacherNumber(t_work_id,
						Integer.parseInt(t_number));

				if (flag) {
					sucessCount++;
				}

			}

			int failCount = pairArray.length - sucessCount;

			messageBean.setFlag(true);
			String message = "成功保存:&nbsp;" + sucessCount + "&nbsp条数据";
			if (failCount != 0) {
				message += ",失败:" + failCount + "条";
			}
			messageBean.setMessage(message);

		} else {
			messageBean.setFlag(false);
			messageBean.setMessage("参数为空");
		}
		renderJson(messageBean);
	}
}
