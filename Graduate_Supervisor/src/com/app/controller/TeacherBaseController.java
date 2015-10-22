package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.app.bean.ItemBean;
import com.app.bean.MessageBean;
import com.app.bean.QueryResultBean;
import com.app.common.RoleCommon;
import com.app.model.InfoTeacherBasic;
import com.app.service.TeacherBasicService;
import com.app.service.VolunteerResultService;
import com.app.util.ExcelImportUtil;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;

/**
 * 访问教师
 * 
 * @author Administrator
 *
 */
public class TeacherBaseController extends BaseController {

	/*
	 * 教师名额分配
	 */
	public void allocNumber() {
		render("alloc_number.jsp");
	}

	/*
	 * 教师信息管理
	 */
	public void teacherManage() {
		render("teacher_manage.jsp");
	}

	/*
	 * 双选结果
	 */
	public void choiseResult() {
		render("choise_result.jsp");
	}

	/*
	 * 待选教师
	 */
	public void candidateTeacher() {

		String s_id = getId();

		boolean flag = VolunteerResultService.hasSelected(s_id);

		// 返回我的导师列表
		if (flag) {
			render("my_teacher.jsp");
			// 返回志愿列表
		} else {

			render("candidate_teacher.jsp");

		}
	}

	public void getMyTeacherList() {

		String s_id = getId();

		List<InfoTeacherBasic> list = TeacherBasicService
				.getMyTeacherBasicList(s_id);

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", list);

		renderJson(jsonMap);

	}

	/*
	 * 教师基本信息列表json数据
	 */
	public void getTeacherBaseList() {

		QueryResultBean<InfoTeacherBasic> queryResult = null;

		int page = getParaToInt("page");
		int rows = getParaToInt("rows");

		if (getUserRole() == RoleCommon.STUDENT) {
			queryResult = TeacherBasicService.getTeacherBaseResult(page, rows,
					getId());
		} else if (getUserRole() == RoleCommon.ADMIN) {
			queryResult = TeacherBasicService.getTeacherBaseResult(page, rows);
		}

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("rows", queryResult.getList());

		jsonMap.put("total", queryResult.getCount());

		renderJson(jsonMap);
	}

	/*
	 * 查看教师的详细信息，返回到详细信息列表
	 */
	public void teacherDetail() {

		String t_work_id = getPara("t_work_id");

		InfoTeacherBasic infoTeacherBasic = InfoTeacherBasic
				.getTmsTeacher(t_work_id);

		String t_file_path = infoTeacherBasic.get("t_file_path");

		setAttr("t_file_path", t_file_path);

		render("teacher_detail.jsp");

	}

	/*
	 * 保存教师名额
	 */
	public void saveTeacherNumber() {

		String para = getPara("para");

		MessageBean messageBean = TeacherBasicService.saveTeacherNumber(para);

		renderJson(messageBean);
	}

	/*
	 * 教师下拉列表
	 */
	public void getTeacherJson() {

		List<ItemBean> treeList = VolunteerResultService
				.getHasRestTeacherJson();

		renderJson(treeList);

	}

	/*
	 * 教师基本信息导入
	 */

	public void uploadTeacherExcel() {

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/excel/");

		UploadFile uploadFile = getFile("excelTeacher", realpath,
				200 * 1024 * 1024, "UTF-8");

		ExcelImportUtil importUtil = new ExcelImportUtil();

		String path = realpath + "/" + uploadFile.getFileName();

		importUtil.importTeacher(path);

		uploadFile.getFile().delete();

		render("teacher_manage.jsp");

	}

	public void deleteTeacher() {
		String t_work_id = getPara("t_work_id");

		boolean flag = TeacherBasicService.deleteTeacher(t_work_id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("删除失败");
		}

		renderJson(messageBean);
	}

	/*
	 * 设置上传的教职工号
	 */
	public void setUploadWorkId() {

		String t_work_id = getPara("t_work_id");

		setSessionAttr("f_t_work_id", t_work_id);

		MessageBean messageBean = new MessageBean();

		messageBean.setFlag(true);

		renderJson(messageBean);

	}

	/*
	 * 上传pdf 文档
	 */
	public void uploadPDF() {

		String t_work_id = getSessionAttr("f_t_work_id");

		MessageBean messageBean = new MessageBean();

		ServletContext context = JFinal.me().getServletContext();

		String realpath = context.getRealPath("/pdf/");

		UploadFile uploadFile = getFile("pdf", realpath, 200 * 1024 * 1024,
				"UTF-8");

		boolean flag = TeacherBasicService.uploadPDF(t_work_id,
				uploadFile.getFileName());

		messageBean.setFlag(flag);

		if (!flag) {
			messageBean.setMessage("数据库数据更新失败");
		}

		render("teacher_manage.jsp");

	}
}
