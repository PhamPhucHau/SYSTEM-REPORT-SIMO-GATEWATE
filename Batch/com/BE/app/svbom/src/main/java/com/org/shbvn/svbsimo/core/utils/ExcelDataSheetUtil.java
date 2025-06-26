package com.org.shbvn.svbsimo.core.utils;

import java.util.List;

public class ExcelDataSheetUtil {
	private String sheetName;
	private List<Object[]> lsData;

	public ExcelDataSheetUtil(String sheetName, List<Object[]> lsData) {
		super();
		this.sheetName = sheetName;
		this.lsData = lsData;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public List<Object[]> getLsData() {
		return lsData;
	}

	public void setLsData(List<Object[]> lsData) {
		this.lsData = lsData;
	}

}
