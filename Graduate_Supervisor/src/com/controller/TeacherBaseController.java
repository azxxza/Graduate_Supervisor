package com.controller;

import java.util.HashMap;
import java.util.Map;

import com.model.InfoTeacherBasic;
import com.util.MessageBean;
import com.util.QueryResult;

/**
 * 公共的Controller类
 * 
 * @author azx
 * 
 */
public class TeacherBaseController extends BaseController {

	public void candidateTeacherList() {
		render("candidate_teacher.jsp");
	}

	/**
	 * 列表页面或编辑页面
	 */
	public void list() {

		render("list.jsp");

	}

	/**
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		QueryResult<InfoTeacherBasic> queryResult = InfoTeacherBasic
				.getTeacherBaseResult(page, rows, "");

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

	/**
	 * 教师基本信息页面，不可编辑
	 */
	public void base() {
		String t_work_id = getPara("t_work_id");

		setAttr("t_work_id", t_work_id);
		// InfoTeacherBasic tmsTeacher = InfoTeacherBasic
		// .getTeacherModel(t_work_id);
		// setAttr("tmsTeacher", tmsTeacher);
		//
		// render("base.jsp");
	}

	public void doVolunteer() {
		String para = getPara("para");

		if (para != null && !para.equals("")) {
			String[] pairArray = para.split(";");

			System.out.println(pairArray.length);

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
}
