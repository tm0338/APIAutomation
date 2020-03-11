package com.testautomation.APIAutomation.test;


import static com.testautomation.APIAutomation.helper.constants.TestData.CITY_NAME;
import static com.testautomation.APIAutomation.helper.util.CommonUtil.writeMsg;
import static com.testautomation.APIAutomation.helper.util.RestUtilities.CONTENT_TYPE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.testautomation.APIAutomation.helper.constants.FilePath;
import com.testautomation.APIAutomation.helper.util.CommonUtil;
import com.testautomation.APIAutomation.helper.util.ExcelDataExtraction;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * @author tm0338
 * Contains common methods that can be used by test classes extending this super class.
 */
public class BaseTest {

	protected HashMap<String, String[]> TestDataMap = new HashMap<String, String[]>(); //Hashmap to hold test case meta data such as - 
																					   //TEST_CASE_ID, TEST_CASE_DESCRIPTION, SUCCESS_MESSAGE and FAILURE_MESSAGE
	//Variable to hold ValidatableResponse
	protected ValidatableResponse  vr = null;
	//Variable to hold response body - used for reporting
	protected String responseAsString = "" ;  

	
	@BeforeClass
	public void testStarter(){
		CommonUtil.REPORT = new ExtentReports(FilePath.HTML_REPORT); //Initialize extent report
		TestDataMap = ExcelDataExtraction.GetTestCaseDataMap(); //Populate test case meta data map

	}
	
	//Method to get API reponse for given request specifications
	public boolean getAPIValidatableResponse(RequestSpecification reqSpec, ResponseSpecification resSpec, Logger LOG, String msg) {
		try { 
			vr = given().spec(reqSpec).when().get().then().spec(resSpec);
			responseAsString = vr.log().all().extract().response().prettyPrint();
			if (! (runCommonChecks(LOG))) { return false; }
		} catch(Exception e) {
			writeMsg(LOG, "ERROR", "Error occured while making API request for "+ msg + ". " + e.getMessage());
			return false;
		}
		return true;
	}

	//Method to run common checks on all API responses
	public boolean runCommonChecks(Logger LOG) {
		//Verify status code is 200
		try {
			vr.assertThat().statusCode(200);
			writeMsg(LOG,"INFO", "Status Code: 200");
		} catch(AssertionError ae) {
			writeMsg(LOG, "ERROR", "Unexpected Status Code: " + vr.log().status().extract().asString());
			return false;
		}
		
		try {
			if (CONTENT_TYPE.equals("xml")) {
				vr.assertThat().body("current.city.@name", equalTo(CITY_NAME),"current.city.country", equalTo("GB"), "current.weather.value", anything());
			} else {
				vr.assertThat().body("name", equalTo(CITY_NAME), "sys.country", equalTo("GB"), "weather.main", anything(), "weather.description", anything());
			}
			writeMsg(LOG, "INFO", "Response contains weather data for " + CITY_NAME + ", GB");
		} catch(AssertionError ae) {
			writeMsg(LOG, "ERROR", "Common fields in the response could not be verified.");
			return false;
		}
		return true;
	}

	
	//Method to populate extent report with test result and response body
	public void finishTestReporting(int testResultStatus, String resultMsg) {
		if (!CONTENT_TYPE.equals("xml")) { resultMsg += "<br><br>" + CommonUtil.reportFormat(responseAsString) + "<br>"; }
		if (testResultStatus == ITestResult.SUCCESS) { CommonUtil.REPORT_LOG.log(LogStatus.PASS, resultMsg); }
		if (testResultStatus == ITestResult.FAILURE) { CommonUtil.REPORT_LOG.log(LogStatus.FAIL, resultMsg); }
		CommonUtil.REPORT.endTest(CommonUtil.REPORT_LOG);
		CommonUtil.REPORT.flush();
	}
	
	//Method to report test result
	public void reportTestResults(ITestResult result) {
		finishTestReporting(result.getStatus(), (result.getStatus() == ITestResult.SUCCESS) ?
								(TestDataMap.get(result.getMethod().getMethodName())[2]) : 
								(TestDataMap.get(result.getMethod().getMethodName())[3]) );
	}
	
	
}
