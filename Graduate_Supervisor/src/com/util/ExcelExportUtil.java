package com.util;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.bean.QueryResultBean;
import com.jfinal.plugin.activerecord.Model;
import com.model.InfoStudentBasic;
import com.model.InfoTeacherBasic;
import com.service.TeacherBasicService;

public class ExcelExportUtil {

	public static <M extends Model<M>> void exportByRecord(
			HttpServletResponse response, HttpServletRequest request,
			String filename, List<InfoTeacherBasic> list) {
		HSSFWorkbook wb = new HSSFWorkbook();

		CellStyle titleCellStyle = wb.createCellStyle();
		titleCellStyle.setAlignment((short) 2);
		titleCellStyle.setWrapText(true);
		Font font = wb.createFont();
		font.setBoldweight((short) 700);
		font.setFontName("微软雅黑");
		titleCellStyle.setFont(font);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment((short) 1);
		cellStyle.setWrapText(true);
		Font font2 = wb.createFont();
		font2.setFontName("微软雅黑");
		cellStyle.setFont(font2);

		HSSFSheet sheet = wb.createSheet();

		sheet.setDefaultColumnWidth(13);

		HSSFRow row = null;
		HSSFCell cell = null;

		row = sheet.createRow(0);
		row.setHeight((short) 450);
		cell = row.createCell(0);
		cell.setCellValue("姓名");
		cell = row.createCell(1);
		cell.setCellValue("性别");

		cell = row.createCell(2);
		cell.setCellValue("名额总数(个)");

		cell = row.createCell(3);
		cell.setCellValue("剩余名额(个)");

		cell = row.createCell(4);
		cell.setCellValue("学生列表");

		for (int i = 0; i < list.size(); i++) {

			InfoTeacherBasic infoTeacherBasic = list.get(i);

			String t_work_id = infoTeacherBasic.getStr("t_work_id");

			row = sheet.createRow(i + 1);

			row.setHeight((short) 450);

			cell = row.createCell(0);
			String t_name = infoTeacherBasic.getStr("t_name");
			if (t_name != null) {
				cell.setCellValue(t_name);
			}

			cell = row.createCell(1);
			String t_sex = infoTeacherBasic.getStr("t_sex");
			if (t_sex != null) {
				cell.setCellValue(t_sex);
			}

			cell = row.createCell(2);
			int t_number = infoTeacherBasic.getInt("t_number");
			cell.setCellValue(t_number);

			cell = row.createCell(3);
			int rest_number = TeacherBasicService
					.getTeacherRestNumberByWorkId(t_work_id);
			cell.setCellValue(rest_number);

			cell = row.createCell(4);
			QueryResultBean<InfoStudentBasic> queryResult = InfoStudentBasic
					.getStudentResultWithSelectedByWorkId(0, 0, t_work_id);
			List<InfoStudentBasic> studentBasicList = queryResult.getList();
			String s_name_list = "";
			if (studentBasicList != null) {
				for (int j = 0; j < studentBasicList.size(); j++) {
					String s_name = studentBasicList.get(j).getStr("s_name");
					if (j == studentBasicList.size() - 1) {
						s_name_list += s_name;
					} else {
						s_name_list += s_name + ",";
					}

				}
			}
			cell.setCellValue(s_name_list);

		}

		writeStream(filename, wb, response, request);
	}

	private static void writeStream(String filename, HSSFWorkbook wb,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			String agent = request.getHeader("USER-AGENT");

			filename = filename + ".xls";

			filename.replaceAll("/", "-");

			if (agent.toLowerCase().indexOf("firefox") > 0)
				filename = new String(filename.getBytes("utf-8"), "iso-8859-1");
			else {
				filename = URLEncoder.encode(filename, "UTF-8");
			}

			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(
					response.getOutputStream());
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}