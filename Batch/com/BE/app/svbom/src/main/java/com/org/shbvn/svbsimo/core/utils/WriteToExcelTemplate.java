package com.org.shbvn.svbsimo.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteToExcelTemplate {
	
	private static final Logger logger = LoggerFactory.getLogger(WriteToExcel.class);
	
	private WriteToExcelTemplate() {
		super();
	}
	
	/**
	 * this method will fill the 2-dimensions data into a sheet row by row.
	 * this will skip the header row and start from row
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill Datas to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param fromRow
	 * @param datas
	 * @param isSaved
	 */
	public static Workbook fillDataToSheetTemplate(String fileSource, String fileDestination, String sheetName,
			int fromRow, int shiftRow, List<Object[]> datas, boolean isSaved) {
		Workbook wb = Optional.ofNullable(readWorkbook(fileSource)).orElse(new XSSFWorkbook());
		if(CollectionUtils.isEmpty(datas)) {
			return wb;
		}
		moveFromRowToRowInSheet(wb, sheetName, shiftRow, datas.size());
		fillDataToSheetHandlerTemplate(wb, sheetName, fromRow, datas);
		
		if(isSaved) {
			writeWorkbook(wb, fileDestination);
		}
		return wb;
	}
	
	/**
	 * this method will fill the 2-dimensions data into a sheet row by row.
	 * this will skip the header row and start from row
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill Datas to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param fromRow
	 * @param datas
	 * @param isSaved
	 */
	public static Workbook fillDataToSheetTemplate(Workbook wb, String fileDestination, String sheetName,
			int fromRow, int shiftRow, List<Object[]> datas, boolean isSaved) {
		moveFromRowToRowInSheet(wb, sheetName, shiftRow, datas.size());
		fillDataToSheetHandlerTemplate(wb, sheetName, fromRow, datas);
		
		if(isSaved) {
			writeWorkbook(wb, fileDestination);
		}
		return wb;
	}
	
	/**
	 * this method will fill the 2-dimensions data into a sheet row by row.
	 * this will skip the header row and start from row
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill Datas to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param fromRow
	 * @param datas
	 * @param isSaved
	 */
	public static Workbook fillDataToSheetTemplate(Workbook wb, String sheetName,
			int fromRow, List<Object[]> datas) {
		fillDataToSheetHandlerTemplate(wb, sheetName, fromRow, datas);
		return wb;
	}

	/**
	 * this method will fill the 2-dimensions data into a sheet row by row.
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill Datas to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToSheet(String fileSource, String fileDestination, String sheetName, List<List<Object>> datas) {
		Workbook wb = Optional.ofNullable(readWorkbook(fileSource)).orElse(new XSSFWorkbook());
		fillDataToSheetHandler(wb, sheetName, datas);
		writeWorkbook(wb, fileDestination);
	}
	
	/**
	 * this method will fill the 2-dimensions data into a new sheet row by row.
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToSheet(String fileDestination, String sheetName, List<List<Object>> datas) {
		fillDataToSheet(null, fileDestination, sheetName, datas);
	}
	
	/**
	 * this method will fill a data into a specific cell location by rowIndex and columnIndex
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill data to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToCell(String fileSource, String fileDestination, String sheetName, int rowIndex, int columnIndex, Object data) {
		Workbook wb = Optional.ofNullable(readWorkbook(fileSource)).orElse(new XSSFWorkbook());
		fillDataToCellHandler(wb, sheetName, rowIndex, columnIndex, data);
		writeWorkbook(wb, fileDestination);
	}
	
	/**
	 * this method will create a new "xlsx" file and fill a data into a specific cell location by rowIndex and columnIndex
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToCell(String fileDestination, String sheetName, int rowIndex, int columnIndex, Object data) {
		fillDataToCell(null, fileDestination, sheetName, rowIndex, columnIndex, data);
	}
	
	/**
	 * this method will fill a data into a row
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill data to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToRow(String fileSource, String fileDestination, String sheetName, int rowIndex, List<Object> datas) {
		Workbook wb = Optional.ofNullable(readWorkbook(fileSource)).orElse(new XSSFWorkbook());
		fillDataToRowHandler(wb, sheetName, rowIndex, datas);
		writeWorkbook(wb, fileDestination);
	}
	
	/**
	 * this method will create a new "xlsx" file and fill a data into a row
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToRow(String fileDestination, String sheetName, int rowIndex, List<Object> datas) {
		fillDataToRow(null, fileDestination, sheetName, rowIndex, datas);
	}
	
	/**
	 * this method will fill a data into a column
	 * if a fileSource is not available it will create a new "xlsx" file
	 * if the fileSource and fileDestination is the same it will fill data to the fileSoure
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToColumn(String fileSource, String fileDestination, String sheetName, int columnIndex, List<Object> datas) {
		Workbook wb = Optional.ofNullable(readWorkbook(fileSource)).orElse(new XSSFWorkbook());
		fillDataToColumnHandler(wb, sheetName, columnIndex, datas);
		writeWorkbook(wb, fileDestination);
	}
	
	/**
	 * this method will create a new "xlsx" file and fill a data into a column
	 * @param fileSource
	 * @param fileDestination
	 * @param sheetName
	 * @param datas
	 */
	public static void fillDataToColumn(String fileDestination, String sheetName, int columnIndex, List<Object> datas) {
		fillDataToColumn(null, fileDestination, sheetName, columnIndex, datas);
	}
	
	private static void fillDataToSheetHandler(Workbook wb, String sheetName, List<List<Object>> datas) {
		for (int rowIndex = 0; rowIndex < datas.size(); rowIndex++) {
			fillDataToRowHandler(wb, sheetName, rowIndex, datas.get(rowIndex));
		}
	}
	
	private static void fillDataToSheetHandlerTemplate(Workbook wb, String sheetName
			, int fromRow, List<Object[]> datas) {
		logger.info("Jump in");
		for (int rowIndex = fromRow; rowIndex < datas.size() + fromRow; rowIndex++) {
			fillDataToRowHandlerTemplate(wb, sheetName, fromRow, rowIndex, datas.get(rowIndex - fromRow));
		}
	}
	
	private static void moveFromRowToRowInSheet(Workbook wb, String sheetName
			, int fromRow, int toRow) {
		Sheet sheet = getOrCreateSheet(wb, sheetName);
		sheet.shiftRows(fromRow, sheet.getLastRowNum(), toRow);
	}
	
	private static void fillDataToCellHandler(Workbook wb, String sheetName, int rowIndex, int columnIndex, Object data) {
		Sheet sheet = getOrCreateSheet(wb, sheetName);
		Row row = getOrCreateRow(sheet, rowIndex);
		Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		setCellValue(cell, data);
	}
	
	private static void fillDataToRowHandler(Workbook wb, String sheetName, int rowIndex, List<Object> datas) {
		Sheet sheet = getOrCreateSheet(wb, sheetName);
		Row row = getOrCreateRow(sheet, rowIndex);
		for (int i = 0; i < datas.size(); i++) {
			Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			setCellValue(cell, datas.get(i));
		}
	}
	
	private static void fillDataToRowHandlerTemplate(Workbook wb, String sheetName,int initIndex, int rowIndex, Object[] datas) {
		Sheet sheet = getOrCreateSheet(wb, sheetName);
		Row initRow = sheet.getRow(initIndex);
		Row row = getOrCreateRow(sheet, rowIndex);
		
		for (int i = 0; i < datas.length; i++) {
			Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			setCellValue(cell, datas[i]);
			cell.setCellStyle(initRow.getCell(i).getCellStyle());
		}
	}
	
	private static void fillDataToColumnHandler(Workbook wb, String sheetName, int columnIndex, List<Object> datas) {
		Sheet sheet = getOrCreateSheet(wb, sheetName);
		for (int i = 0; i < datas.size(); i++) {
			Row row = getOrCreateRow(sheet, i);
			Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			setCellValue(cell, datas.get(i));
		}
	}
	
	public static void writeWorkbook(Workbook wb, String path) {
		try {
			FileOutputStream output;
			output = new FileOutputStream(new File(path));
			wb.write(output);
			output.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static Sheet getOrCreateSheet(Workbook wb, String sheetName) {
		return Optional.ofNullable(wb.getSheet(sheetName)).orElseGet(() -> wb.createSheet(sheetName));
	}
	
	private static Row getOrCreateRow(Sheet sheet, int rowIndex) {
		return Optional.ofNullable(sheet.getRow(rowIndex)).orElseGet(() -> sheet.createRow(rowIndex));
	}
	
	private static String getFileType(String filePath) {
		if (filePath.endsWith("xlsx")) {
			return "xlsx";
		} else if (filePath.endsWith("xls")) {
			return "xls";
		}
		return "";
	}
	
	private static void setCellValue(Cell cell, Object data) {
		if (data instanceof Number) {
			if (data instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) data).doubleValue());
			} else if (data instanceof Integer) {
				Integer in = (Integer) data;
				cell.setCellValue(in.doubleValue());
			} else if (data instanceof Long) {
				Long in = (Long) data;
				cell.setCellValue(in.doubleValue());
			} else {
				cell.setCellValue((Double) data);
			}
		} else if (data instanceof NClob) {
			NClob in = (NClob) data;
			try {
				cell.setCellValue(in.getSubString(1, (int) in.length()));
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		} else if (data instanceof String) {
			cell.setCellValue((String) data);
		} else if (data instanceof Date) {
			cell.setCellValue((Date) data);
		} else if (data != null) {
			cell.setCellValue(data.toString());
		}
	}
	
	private static Workbook readWorkbook(String fileSource) {
		if (CommonUtils.isBlank(fileSource))
			return null;
		try (FileInputStream file = new FileInputStream(fileSource)) {
			Workbook workbook = null;
			String fileType = getFileType(fileSource);
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			return workbook;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	

}
	
