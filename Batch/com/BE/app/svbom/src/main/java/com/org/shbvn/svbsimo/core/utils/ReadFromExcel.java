package com.org.shbvn.svbsimo.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.shbvn.svbsimo.core.exception.ServiceRuntimeException;

import java.io.IOException;
import com.opencsv.exceptions.CsvValidationException;


public class ReadFromExcel {
	public String filepath = null;
	public String fileType;
	public Object[][] data = null;
	final DataFormatter df = new DataFormatter();
	public static Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").setPrettyPrinting().create();
	static SimpleDateFormat DtFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private transient static Environment env;
	/**
	 * 
	 * @param filepath
	 */
	public ReadFromExcel(String filepath) {
		if (CommonUtils.isBlank(filepath) == false) {
			if (filepath.endsWith("xlsx")) {
				fileType = "xlsx";
			} else if (filepath.endsWith("xls")) {
				fileType = "xls";
			}
			else if( filepath.endsWith("csv") || filepath.endsWith("txt")  || filepath.endsWith("TXT")) {
				fileType = "csv";
			} 
		}
		this.filepath = filepath;
	}

	/**
	 * Read Data From Excel
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Map> readDataFromExcel(int sheetIndex) {
		ArrayList<Map> lsData = new ArrayList<>();
		try {
			FileInputStream file = new FileInputStream(getFile());
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			else if (fileType.equalsIgnoreCase("csv")) {
				
				// For now, we will not implement this part
				throw new ServiceRuntimeException("CSV or TXT file reading is not implemented yet.");
			}
			
			lsData = getMappedValues(workbook, sheetIndex);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsData;
	}
	
	/**
	 * Read Data From Excel with configuration
	 * @param sheetIndex, fromRow, headerRow
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> readDataFromExcel(int sheetIndex, int fromRow, int headerRow) {
		List<Map> lsData = new ArrayList<>();
		try (FileInputStream file = new FileInputStream(getFile());){
			if (fileType.equalsIgnoreCase("csv")) {
            lsData = readDataFromCsv(sheetIndex);
        	} else {
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			lsData = getMappedValues(workbook, sheetIndex, fromRow, headerRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsData;
	}
	public String readFromSpecCell(int sheetIndex, int rowIdx, int colIdx) {
		
		String rs = "";
		try (FileInputStream file = new FileInputStream(getFile());){
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			rs = getDataFromSpecCell(workbook, sheetIndex, rowIdx, colIdx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> readDataFromExcel(int sheetIndex, int fromRow, int fromCol, int headerRow) {
		List<Map> lsData = new ArrayList<>();
		try (FileInputStream file = new FileInputStream(getFile());){
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			lsData = getMappedValues(workbook, sheetIndex, fromRow, fromCol, headerRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsData;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map> getMappedValues(Workbook workbook, int sheetIndex, int fromRow, int fromCol, int headerRow) {
		List<Map> mapArray = null;
		try {
			List<String> colNames = null;
			Row row = null;
			Sheet sheet = null;
			int sheetRows = 0;
			int rowCols = 0;
			Map<String, Object> rowMap = null;
			sheet = workbook.getSheetAt(sheetIndex);
			sheetRows = sheet.getPhysicalNumberOfRows();
			mapArray = new ArrayList<>(sheetRows - 1);
			colNames = getColNamesByRowIndex(workbook, sheetIndex, headerRow, fromCol);
			rowCols = colNames.size();
			for (int i = fromRow; i < sheetRows; i++) {
				row = sheet.getRow(i);
				rowMap = new HashMap<>(rowCols);
				if(!isBlankRow(row)) {
					for (int c = fromCol; c < rowCols; c++) {
						String value = getDataCell(workbook, row.getCell(c));
						
						rowMap.put(colNames.get(c), value);
					}
					//System.out.println(CommonUtil.toJson(rowMap) + i);
					mapArray.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapArray;
	}

	private List<String> getColNamesByRowIndex(Workbook workbook, int sheetIndex, int headerRow, int fromCol) {
		List<String> colNames = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Row row = sheet.getRow(headerRow);
			int cols = 0;
			if (row != null) {
				cols = row.getPhysicalNumberOfCells();
				for (int i = fromCol; i < cols; i++) {
					colNames.add(getDataCell(workbook, row.getCell(i)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colNames;
	}

	@SuppressWarnings("null")
	public int countSheetNumberExcel(){
		try {
			FileInputStream file = new FileInputStream(getFile());
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			return workbook.getNumberOfSheets();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@SuppressWarnings("null")
	public ArrayList<String> getListSheetNameExcel(){
		ArrayList<String> sheetNames = new ArrayList<String>();
		try {
			FileInputStream file = new FileInputStream(getFile());
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheetNames.add(workbook.getSheetName(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sheetNames;
	}

	/**
	 * Get file
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public File getFile() throws FileNotFoundException {
		File here = new File(filepath);
		return new File(here.getAbsolutePath());
	}

	/**
	 * Get column names
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @return
	 */
	public ArrayList<String> getColNames(Workbook workbook, int sheetIndex) {
		ArrayList<String> colNames = new ArrayList<String>();
		try {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Row row = sheet.getRow(0);
			int cols = 0;
			if (row != null) {
				cols = row.getPhysicalNumberOfCells();
				for (int i = 0; i < cols; i++) {
					colNames.add(getDataCell(workbook, row.getCell(i)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colNames;
	}
	
	/**
	 * getColNamesByRowIndex
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @return
	 */
	public List<String> getColNamesByRowIndex(Workbook workbook, int sheetIndex, int rowIndex) {
		List<String> colNames = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Row row = sheet.getRow(rowIndex);
			int cols = 0;
			if (row != null) {
				cols = row.getPhysicalNumberOfCells();
				for (int i = 0; i < cols; i++) {
					colNames.add(getDataCell(workbook, row.getCell(i)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colNames;
	}

	/**
	 * Mapping data with column name
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Map> getMappedValues(Workbook workbook, int sheetIndex) {
		ArrayList<Map> mapArray = null;
		try {
			ArrayList<String> colNames = null;
			Row row = null;
			Sheet sheet = null;
			int sheetRows = 0;
			int rowCols = 0;
			Map<String, Object> rowMap = null;
			sheet = workbook.getSheetAt(sheetIndex);
			sheetRows = sheet.getPhysicalNumberOfRows();
			mapArray = new ArrayList<Map>(sheetRows - 1);
			colNames = getColNames(workbook, sheetIndex);
			colNames.trimToSize();
			rowCols = colNames.size();
			
			for (int i = 1; i < sheetRows; i++) {
				row = sheet.getRow(i);
				rowMap = new HashMap<String, Object>(rowCols);
				for (int c = 0; c < rowCols; c++) {
					String value = getDataCell(workbook, row.getCell(c));
					
					/*System.out.println(colNames.get(c) + " - [" + value + "]");*/
					
					rowMap.put(colNames.get(c), value);
				}
				mapArray.add(rowMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapArray;
	}
	
	/**
	 * Mapping data with column name
	 * 
	 * @param workbook
	 * @param sheetIndex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getMappedValues(Workbook workbook, int sheetIndex, int fromRow, int headerRow) {
		List<Map> mapArray = null;
		try {
			List<String> colNames = null;
			Row row = null;
			Sheet sheet = null;
			int sheetRows = 0;
			int rowCols = 0;
			Map<String, Object> rowMap = null;
			sheet = workbook.getSheetAt(sheetIndex);
			sheetRows = sheet.getPhysicalNumberOfRows();
			mapArray = new ArrayList<>(sheetRows - 1);
			colNames = getColNamesByRowIndex(workbook, sheetIndex, headerRow);
			rowCols = colNames.size();
			for (int i = fromRow; i < sheetRows; i++) {
				row = sheet.getRow(i);
				rowMap = new HashMap<>(rowCols);
				if(!isBlankRow(row)) {
					for (int c = 0; c < rowCols; c++) {
						String value = getDataCell(workbook, row.getCell(c));
						
						rowMap.put(colNames.get(c), value);
					}
					//System.out.println(CommonUtil.toJson(rowMap) + i);
					mapArray.add(rowMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapArray;
	}
	public String getDataFromSpecCell(Workbook workbook, int sheetIndex, int rowIdx, int colIdx) {
		String rs = "";
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row row = sheet.getRow(rowIdx);
		rs = getDataCell(workbook, row.getCell(colIdx));
		
		return rs;
	}
	/**
	 * 
	 * @param workbook
	 * @param cell
	 * @return
	 */
	public String getDataCell(Workbook workbook, Cell cell) {
		String cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
				case STRING:
					cellValue = cell.getStringCellValue();
					break;
				case FORMULA:
					cellValue = cell.getCellFormula();
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						Date date= cell.getDateCellValue();
						cellValue = DtFormat.format(date).toString();
					} else {
						BigDecimal valueData = BigDecimal.valueOf(cell.getNumericCellValue());
						if(valueData != null){
							cellValue = valueData.toPlainString();
							cellValue = cellValue.split("\\.")[0];
						}
					}
					break;
				case BLANK:
					cellValue = "";
					break;
				case BOOLEAN:
					cellValue = Boolean.toString(cell.getBooleanCellValue());
					break;
				default:
					break;
			}
		}
		return cellValue;
	}
	
	public ArrayList<String> getHeaderTemplate(String type, String pathTemplate) throws ServiceRuntimeException {
		ArrayList<String> colNames = null;
		try {
			FileInputStream file = new FileInputStream(getTemplateFile(type, pathTemplate));
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			int sheetIndex = 0;
			colNames = getColNames(workbook, sheetIndex);
			colNames.trimToSize();
			file.close();
		} catch (Exception e) {
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
		return colNames;
	}
	
	public List<String> getHeaderTemplateByRow(String type, String pathTemplate, int rowIndex) throws ServiceRuntimeException {
		List<String> colNames = null;
		try (FileInputStream file = new FileInputStream(getTemplateFile(type, pathTemplate));) {
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			int sheetIndex = 0;
			colNames = getColNamesByRowIndex(workbook, sheetIndex, rowIndex);
		} catch (Exception e) {
			throw new ServiceRuntimeException(env.getProperty("MSG_002"));
		}
		return colNames;
	}
	
	public static File getTemplateFile(String type, String pathTemplate){
		String fileTemplateName = null;
		// if upload LMS template
		
		String pathTemplateFile = pathTemplate + fileTemplateName;
		File here = new File(pathTemplateFile);
		
		return here;
	}
	
	public ArrayList<String> getHeaderUpload() {
		ArrayList<String> colNames = null;
		try {
			FileInputStream file = new FileInputStream(getFile());
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			int sheetIndex = 0;
			colNames = getColNames(workbook, sheetIndex);
			colNames.trimToSize();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colNames;
	}
	
	public List<String> getHeaderUploadByRowIndex(int rowIndex) {
		List<String> colNames = null;
		try (FileInputStream file = new FileInputStream(getFile())){
			Workbook workbook = null;
			if (fileType.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file);
			} else if (fileType.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file);
			}
			int sheetIndex = 0;
			colNames = getColNamesByRowIndex(workbook, sheetIndex, rowIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colNames;
	}
	
	public Boolean isCorrectTemplate (String type, String pathTemplate) throws ServiceRuntimeException{
		ArrayList<String> headerTemplateADJ = getHeaderTemplate(type, pathTemplate);
		ArrayList<String> headerADJUpload = getHeaderUpload();
		Boolean isCorrect = true;
		if (headerADJUpload.size() != headerTemplateADJ.size()){
			isCorrect = false;
			return isCorrect;
		}
		for (int i = 0; i < headerTemplateADJ.size(); i++) {
			if(!headerADJUpload.get(i).equals(headerTemplateADJ.get(i))){
				isCorrect = false;
			}
		}
		return isCorrect;
	}
	
	public Boolean isCorrectTemplateHeader (String type, String pathTemplate, int rowIndex) throws ServiceRuntimeException{
		List<String> headerTemplateADJ = getHeaderTemplateByRow(type, pathTemplate, rowIndex);
		List<String> headerADJUpload = getHeaderUploadByRowIndex(rowIndex);
		for (int i = 0; i < headerTemplateADJ.size(); i++) {
			if(!headerADJUpload.get(i).equals(headerTemplateADJ.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public Boolean isBlankRow(Row row) {
		if (row == null) {
			return  true;
	    }
	    if (row.getLastCellNum() <= 0) {
	    	return true;
	    }
	    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
	        Cell cell = row.getCell(cellNum);
	        if (cell != null && cell.getCellType() != CellType.BLANK && !CommonUtils.isBlank(cell.toString())) {
	        	return false;
	        }
	    }
	    return true;
	}
	/**
 * Read Data From CSV
 */
@SuppressWarnings("rawtypes")
private ArrayList<Map> readDataFromCsv(int sheetIndex) throws IOException, CsvValidationException {
    if (sheetIndex != 0) {
        throw new IllegalArgumentException("CSV files only support sheetIndex 0");
    }
    ArrayList<Map> lsData = new ArrayList<>();
    try (CSVReader csvReader = new CSVReader(new FileReader(getFile()))) {
        List<String[]> allRows = csvReader.readAll();
        if (allRows.isEmpty()) {
            return lsData;
        }
        String[] headers = allRows.get(0); // Assume first row is header
        for (int i = 1; i < allRows.size(); i++) { // Start from second row
            String[] row = allRows.get(i);
            if (!isBlankCsvRow(row)) {
                Map<String, Object> rowMap = new HashMap<>();
                for (int c = 0; c < Math.min(headers.length, row.length); c++) {
                    String value = row[c] != null ? row[c] : "";
                    rowMap.put(headers[c], value);
                }
                lsData.add(rowMap);
            }
        }
    } catch (CsvException e) {
        throw new RuntimeException(e);
    }
    return lsData;
}

/**
 * Check if a CSV row is blank
 */
private boolean isBlankCsvRow(String[] row) {
    if (row == null || row.length == 0) {
        return true;
    }
    for (String cell : row) {
        if (cell != null && !CommonUtils.isBlank(cell)) {
            return false;
        }
    }
    return true;
}
}
