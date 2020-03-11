package com.testautomation.APIAutomation.test;

import static com.testautomation.APIAutomation.helper.constants.TestData.CITY_NAME;
import static com.testautomation.APIAutomation.helper.constants.TestData.CITY_NAME_EN;
import static com.testautomation.APIAutomation.helper.constants.TestData.CITY_NAME_FR;
import static com.testautomation.APIAutomation.helper.util.CommonUtil.REPORT;
import static com.testautomation.APIAutomation.helper.util.CommonUtil.REPORT_LOG;
import static com.testautomation.APIAutomation.helper.util.RestUtilities.createQueryParam;
import static com.testautomation.APIAutomation.helper.util.RestUtilities.getRequestSpecification;
import static com.testautomation.APIAutomation.helper.util.RestUtilities.getResponseSpecification;
import static com.testautomation.APIAutomation.helper.util.RestUtilities.setContentType;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.testautomation.APIAutomation.helper.constants.Authentication;
import com.testautomation.APIAutomation.helper.constants.URLPath;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * @author tm0338
 * TestNG test class that is used to execute Weather API test cases for getting current weather data for city of London
 * 1. Get weather data in JSON format
 * 2. Get weather data in XML format
 * 3. Get weather data in imperial units
 * 4. Get weather data in metric units
 * 5. Get weather data in French language
 */

public class WeatherAPITest extends BaseTest {
	public static final Logger LOG = Logger.getLogger(WeatherAPITest.class);
	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
	//This method runs before each test is executed, it sets up request and response spec objects 
	// which allow to configure common parameters for GET call viz. name of the city and appid (API Key)
	@BeforeMethod
	public void setupTestCases(Method method,Object[] testArgs) {
		
		String testName = method.getName();
		REPORT_LOG = REPORT.startTest(testName,TestDataMap.get(testName)[1]);
		
		reqSpec = getRequestSpecification();
		reqSpec.queryParam("q", "london");
		reqSpec.queryParam("appid", Authentication.API_KEY);
		reqSpec.basePath(URLPath.CURRENT_WEATHER);
		
		resSpec = getResponseSpecification();
		
		//Set content type for verification based on test being run
		if (testName.equals("GetWeatherInXMLFormatTest")) { setContentType(ContentType.XML); }
		else { setContentType(ContentType.JSON); }

		//Set city name for French language test
		if (testName.equals("GetWeatherInDiffLangTest")) { CITY_NAME = CITY_NAME_FR; } 
		else { CITY_NAME = CITY_NAME_EN; }
	}
		
	
	@Test
	public void GetWeatherInJSONFormatTest() {
		assertTrue(getAPIValidatableResponse(reqSpec, resSpec, LOG, "getting weather data in JSON format"));
	}
	
	@Test
	public void GetWeatherInXMLFormatTest() {
		assertTrue(getAPIValidatableResponse(createQueryParam(reqSpec, "mode", "xml"), resSpec, LOG, "getting weather data in XML format"));
	}

	
	@Test
	public void GetTempInFahrenheitUnitTest() {
		assertTrue(getAPIValidatableResponse(createQueryParam(reqSpec, "units", "imperial"), resSpec, LOG, "getting temperature data in imperial units"));
	}
	
	@Test
	public void GetTempInCelsiusUnitTest() {
		assertTrue(getAPIValidatableResponse(createQueryParam(reqSpec, "units", "metric"), resSpec, LOG, "getting temperature data in metric units"));
	}
	
	@Test
	public void GetWeatherInDiffLangTest() {
		assertTrue(getAPIValidatableResponse(createQueryParam(reqSpec, "lang", "fr"), resSpec, LOG, "getting weather data in french language"));
	}
	
	//This method runs after each test method and allows to report test results
	@AfterMethod
	public void	StopTest(ITestResult result, Object[] testArgs) {
			reportTestResults(result);
		}
}
