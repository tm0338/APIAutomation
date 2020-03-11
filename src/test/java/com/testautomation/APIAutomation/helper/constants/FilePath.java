package com.testautomation.APIAutomation.helper.constants;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author tm0338
 * Acts as a repository for File Paths used throughout project
 */
public class FilePath {

	//Directory List
	public static final String TEST_RESULT_DIR = "test-output/extent-reports";
	public static final String SCREENSHOT_DIR = "test-output/extent-reports/screenshots";
	
	//Reort file
	public static final String HTML_REPORT = Paths.get("").toAbsolutePath().toString() + "\\src\\test\\resources\\results\\GetWeatherTestResults.html";
	
	//Test data file
	public static final String TEST_DATA_FILE = new File("src/test/resources").getAbsolutePath() + "/API_Test_Case_Data_Maps.xlsx";
}
