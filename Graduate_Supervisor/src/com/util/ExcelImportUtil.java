package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.bean.QueryResultBean;
import com.model.InfoStudentBasic;
import com.model.InfoStudentScore;
import com.model.SysUser;
import com.model.SysYearTerm;

public class ExcelImportUtil {

	private Sheet getSheet(String path) {

		File file = null;
		InputStream input = null;
		Workbook workBook = null;
		Sheet sheet = null;

		if (path != null && path.length() > 7) {
			String suffix = path
					.substring(path.lastIndexOf("."), path.length());
			if ((".xls".equals(suffix)) || (".xlsx".equals(suffix))) {
				file = new File(path);
				try {
					input = new FileInputStream(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!input.markSupported())
					input = new PushbackInputStream(input, 8);
				try {
					if ((POIFSFileSystem.hasPOIFSHeader(input))
							|| (POIXMLDocument.hasOOXMLHeader(input))) {
						workBook = WorkbookFactory.create(input);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		if (workBook != null) {
			int numbersheet = workBook.getNumberOfSheets();
			if (numbersheet > 0) {
				sheet = workBook.getSheetAt(0);
			}
		}

		return sheet;
	}

	public boolean importBasic(String path) {

		Sheet sheet = getSheet(path);

		if (sheet != null) {

			QueryResultBean<InfoStudentBasic> queryResult = InfoStudentBasic
					.getStudentResult(0, 0);

			List<InfoStudentBasic> dataBaseList = queryResult.getList();

			List<InfoStudentBasic> excelList = getBasicContent(sheet);

			if (excelList == null || excelList.size() == 0) {

				return false;
			} else if (dataBaseList == null || dataBaseList.size() == 0) {

				for (int i = 0; i < excelList.size(); i++) {

					InfoStudentBasic infoStudentBasic = excelList.get(i);

					infoStudentBasic.save();

					SysUser sysUser = new SysUser();

					sysUser.set("s_user_name", infoStudentBasic.get("s_id"));

					sysUser.set("s_user_password", infoStudentBasic.get("s_id"));

					sysUser.set("s_user_role_id", "1");

					sysUser.set("s_foreign_id", infoStudentBasic.get("s_id"));

					sysUser.save();

				}

			} else {
				for (int i = 0; i < excelList.size(); i++) {
					InfoStudentBasic infoStudentBasic = excelList.get(i);
					boolean flag = false;
					for (int j = 0; j < dataBaseList.size(); j++) {
						if (infoStudentBasic.getStr("s_id").equals(
								dataBaseList.get(j).getStr("s_id"))) {
							flag = true;
							break;
						}

					}

					if (!flag) {

						infoStudentBasic.save();

						SysUser sysUser = new SysUser();

						sysUser.set("s_user_name", infoStudentBasic.get("s_id"));

						sysUser.set("s_user_password",
								infoStudentBasic.get("s_id"));

						sysUser.set("s_user_role_id", "1");

						sysUser.set("s_foreign_id",
								infoStudentBasic.get("s_id"));

						sysUser.save();

					} else {

						infoStudentBasic.update();

					}
				}
			}

		}

		return true;
	}

	public List<InfoStudentBasic> getBasicContent(Sheet sheet) {

		List<InfoStudentBasic> list = new ArrayList<InfoStudentBasic>();

		Row row = null;

		int lastRow = sheet.getLastRowNum();

		for (int i = 4; i <= lastRow; i++) {

			row = sheet.getRow(i);

			InfoStudentBasic infoStudent = new InfoStudentBasic();

			String s_id = getCellValue(row.getCell(1));

			String s_name = getCellValue(row.getCell(2));

			String s_sex = getCellValue(row.getCell(3));

			infoStudent.set("s_id", s_id);

			infoStudent.set("s_name", s_name);

			infoStudent.set("s_sex", s_sex);

			list.add(infoStudent);
		}

		return list;
	}

	public boolean importScoreExcel(String path) {

		Sheet sheet = getSheet(path);

		Row topRow = sheet.getRow(0);

		Cell topCell = topRow.getCell(0);

		String topValue = getCellValue(topCell);

		int first = topValue.indexOf("大学");

		String year = topValue.substring(first + 3, first + 14);

		int second = topValue.indexOf("第");

		String term = topValue.substring(second, second + 4);

		SysYearTerm sysYearTerm = SysYearTerm.getSysYearTermByYearAndTerm(
				year.trim(), term.trim());

		if (sysYearTerm == null) {

			sysYearTerm = new SysYearTerm();

			sysYearTerm.set("year", year);

			sysYearTerm.set("term", term);

			sysYearTerm.save();
		}

		Row titleRow = sheet.getRow(3);

		Row dataRow = null;

		// 课程名称集合
		List<String> titleList = new ArrayList<String>();
		for (int i = 1; i < titleRow.getPhysicalNumberOfCells(); i++) {
			Cell cell = titleRow.getCell(i);
			String value = getCellValue(cell);
			if (i == 2 || i == 3) {

				continue;

			} else if (value.equals("")) {

				continue;

			} else {

				titleList.add(value);

			}

		}

		// 学号集合
		List<String> idList = new ArrayList<String>();

		for (int i = 4; i < sheet.getLastRowNum() + 1; i++) {
			dataRow = sheet.getRow(i);
			Cell cell = dataRow.getCell(1);
			String id = getCellValue(cell);
			idList.add(id);
		}

		for (int i = 0; i < idList.size(); i++) {
			String id = idList.get(i);
			dataRow = sheet.getRow(i + 4);
			for (int j = 0; j < titleList.size(); j++) {
				String title = titleList.get(j);
				InfoStudentScore infoStudentScore = new InfoStudentScore();

				infoStudentScore.set("s_id", id);
				infoStudentScore.set("s_c_name", title);

				Cell cell = dataRow.getCell(j + 4);

				String value = getCellValue(cell);

				infoStudentScore.set("s_c_score", value);

				infoStudentScore.set("y_t_id", sysYearTerm.getInt("id"));

				infoStudentScore.save();
			}
		}
		return true;

	}

	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if ((row >= firstRow) && (row <= lastRow)) {
				if ((column >= firstColumn) && (column <= lastColumn)) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	public String getCellValue(Cell cell) {

		if (cell == null) {

			return "";

		} else {

			int cellType = cell.getCellType();
			switch (cellType) {
			case 0:

				return String.valueOf(cell.getNumericCellValue());

			case 1:

				return cell.getStringCellValue();

			case 2:

				return cell.getCellFormula();

			case 4:

				return String.valueOf(cell.getBooleanCellValue());

			default:
				return "";
			}
		}

	}
}
