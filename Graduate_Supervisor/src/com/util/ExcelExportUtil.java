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

import com.jfinal.plugin.activerecord.Model;
import com.model.InfoTeacherBasic;

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
		cell.setCellValue("职称/职务");

		cell = row.createCell(3);
		cell.setCellValue("最高学历");

		cell = row.createCell(4);
		cell.setCellValue("毕业院校");

		cell = row.createCell(5);
		cell.setCellValue("联系电话");

		cell = row.createCell(6);
		cell.setCellValue("Email");

		cell = row.createCell(7);
		cell.setCellValue("剩余名额(个)");

		for (int i = 0; i < list.size(); i++) {
			InfoTeacherBasic infoTeacherBasic = (InfoTeacherBasic) list.get(i);

			row = sheet.createRow(i + 1);
			row.setHeight((short) 450);

			cell = row.createCell(0);

			if (infoTeacherBasic.get("t_name") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_name").toString());
			}
			cell = row.createCell(1);
			if (infoTeacherBasic.get("t_sex") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_sex").toString());
			}
			cell = row.createCell(2);
			if (infoTeacherBasic.get("t_occupation") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_occupation")
						.toString());
			}
			cell = row.createCell(3);
			if (infoTeacherBasic.get("t_hightest_background") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_hightest_background")
						.toString());
			}

			cell = row.createCell(4);
			if (infoTeacherBasic.get("t_hightest_degree") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_hightest_degree")
						.toString());
			}

			cell = row.createCell(5);
			if (infoTeacherBasic.get("t_gradute_school") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_gradute_school")
						.toString());
			}
			cell = row.createCell(6);
			if (infoTeacherBasic.get("t_tel") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_tel").toString());
			}
			cell = row.createCell(7);
			if (infoTeacherBasic.get("t_email") != null) {
				cell.setCellValue(infoTeacherBasic.get("t_email").toString());
			}

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