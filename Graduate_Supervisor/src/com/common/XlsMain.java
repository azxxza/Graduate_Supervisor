package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.model.InfoStudentBasic;
import com.model.SysUser;
import com.util.QueryResult;

public class XlsMain {
	public static void main(String[] args) {
		String path2003 = "";
		path2003 = System.getProperties().getProperty("user.dir")
				+ File.separator + "Excel" + File.separator + "12计算机1.xls";

		new XlsMain().parseExcel(path2003);
	}

	public boolean parseExcel(String path) {
		List<InfoStudentBasic> excelList = new ArrayList<InfoStudentBasic>();
		File file = null;
		InputStream input = null;
		Workbook workBook = null;
		Sheet sheet = null;
		if ((path != null) && (path.length() > 7)) {
			String suffix = path
					.substring(path.lastIndexOf("."), path.length());
			if ((".xls".equals(suffix)) || (".xlsx".equals(suffix))) {
				file = new File(path);
				try {
					input = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					System.out.println("未找到指定的文件！");
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("读取Excel文件发生异常！");
					e.printStackTrace();
				}
				if (!input.markSupported())
					input = new PushbackInputStream(input, 8);
				try {
					if ((POIFSFileSystem.hasPOIFSHeader(input))
							|| (POIXMLDocument.hasOOXMLHeader(input)))
						workBook = WorkbookFactory.create(input);
					else
						System.out.println("非法的输入流：当前输入流非OLE2流或OOXML流！");
				} catch (IOException e) {
					System.out.println("创建表格工作簿对象发生IO异常！原因：" + e.getMessage());
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					System.out.println("非法的输入流：当前输入流非OLE2流或OOXML流！");
					e.printStackTrace();
				}
				try {
					if (workBook != null) {
						int numberSheet = workBook.getNumberOfSheets();
						if (numberSheet > 0) {
							sheet = workBook.getSheetAt(0);
							QueryResult<InfoStudentBasic> queryResult = InfoStudentBasic
									.getStudentResult(0, 0);
							List<InfoStudentBasic> dataBaseList = queryResult
									.getList();
							excelList = getExcelContent(sheet);

							if ((dataBaseList == null)
									|| (dataBaseList.size() == 0)) {
								for (int i = 0; i < excelList.size(); i++)
									((InfoStudentBasic) excelList.get(i))
											.save();
							} else if ((excelList == null)
									|| (excelList.size() == 0)) {
								return false;
							}
							for (int i = 0; i < excelList.size(); i++) {
								InfoStudentBasic infoStudentBasic = (InfoStudentBasic) excelList
										.get(i);
								boolean flag = false;
								for (int j = 0; j < dataBaseList.size(); j++) {
									if (infoStudentBasic.getStr("s_id").equals(
											dataBaseList.get(j).getStr("s_id"))) {
										flag = true;
										break;
									}

								}

								if (flag) {
									infoStudentBasic.save();
									SysUser sysUser = new SysUser();
									sysUser.set("u_username",
											infoStudentBasic.get("s_id"));

									sysUser.set("u_password",
											infoStudentBasic.get("s_id"));

									sysUser.set("p_id", Integer.valueOf(1));

									sysUser.set("id",
											infoStudentBasic.get("s_id"));
								} else {
									infoStudentBasic.update();
								}
							}
						} else {
							System.out.println("目标表格工作簿(Sheet)数目为0！");
						}
					}
					input.close();
				} catch (IOException e) {
					System.out.println("关闭输入流异常！" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				System.out.println("非法的Excel文件后缀！");
			}
		} else {
			System.out.println("非法的文件路径!");
		}
		return true;
	}

	public List<InfoStudentBasic> getExcelContent(Sheet sheet) {
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
		}
		if (cell.getCellType() == 1) {
			return cell.getStringCellValue();
		}
		if (cell.getCellType() == 4) {
			return String.valueOf(cell.getBooleanCellValue());
		}
		if (cell.getCellType() == 2) {
			return cell.getCellFormula();
		}
		if (cell.getCellType() == 0) {
			return String.valueOf(cell.getNumericCellValue());
		}

		return "";
	}
}