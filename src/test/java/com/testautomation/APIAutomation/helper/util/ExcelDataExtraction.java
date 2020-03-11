package com.testautomation.APIAutomation.helper.util;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.testautomation.APIAutomation.helper.constants.FilePath;
/**
 * @author tm0338
 * Contains methods to help with pulling test related data from excel files
 */
public class ExcelDataExtraction {

	private static final Logger LOG = Logger.getLogger(ExcelDataExtraction.class);

	@SuppressWarnings("resource") //Workbook is closed by calling CloseWorkbook method
	private static XSSFSheet OpenWorkbook(String wkbookName, String sheetName) {
		XSSFWorkbook wkbook = null;

		// Open workbook
		try {
			wkbook = new XSSFWorkbook(wkbookName);
		} catch (IOException ioe) {
			LOG.error("Could not open excel file for reading data.\nFile " + wkbookName + " could not be opened.");
			return null;
		}

		// Get sheet, if no sheet name provided, get first sheet
		if (sheetName.equals("")) {
			sheetName = wkbook.getSheetName(0);
		}
		return wkbook.getSheet(sheetName);
	}

	private static void CloseWorkbook(XSSFSheet wksheet) {
		// Close workbook
		try {
			wksheet.getWorkbook().close();
		} catch (IOException ioe) {
			LOG.error("Could not close workbook after trying to read element locator data.\n" + ioe.getMessage());
		}
	}

	private static HashMap<String, String[]> PopulateMap(String xlMapFile, String xlSheetName,int keyCol, int[] valColInOrder) {
		HashMap<String, String[]> dataMap = new HashMap<String, String[]>();
		XSSFSheet wksheet = OpenWorkbook(xlMapFile, xlSheetName);
		
		// Iterate over each of the rows in ElementMap sheet to extract data for stored
		// elements
		for (Row r : wksheet) {
			
			String[] valArray = new String[valColInOrder.length];
			String keyVal = "" ;
			try {
				// Skip heading row
				if (r.getRowNum() == 0) { continue; }
				// If empty cell, break
				if (r.getCell(0).getStringCellValue().equals("")) { break; }
				// Collect data from rest of the rows
				keyVal = r.getCell(keyCol).getStringCellValue();
				String val = "" ;
				for (int i=0;i<valColInOrder.length; i++) {
					valArray[i] = r.getCell(valColInOrder[i]).getStringCellValue();
					val = val + r.getCell(valColInOrder[i]).getStringCellValue();
				}
				LOG.debug("Adding data: \n" + keyVal + ":" + val);
				dataMap.put(keyVal, valArray);
			} catch(Exception e) {
				LOG.error("Error encountered while populating data from " + xlSheetName + " sheet.\n" + e.getMessage());
				CloseWorkbook(wksheet);
				return null;
			}
		}

		CloseWorkbook(wksheet);
		return dataMap;
	}
	

	public static HashMap<String, String[]> GetTestCaseDataMap() {
		return PopulateMap(FilePath.TEST_DATA_FILE, "APITestCaseData", 0 , new int[] {1,2,3,4});
	}
}
