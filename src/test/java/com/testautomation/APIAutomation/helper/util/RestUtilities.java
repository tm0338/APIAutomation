package com.testautomation.APIAutomation.helper.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.testautomation.APIAutomation.helper.constants.URLPath;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * @author tm0338
 * Helper class for generating API requests and response specifications and other common RestAssured functionalities
 */
public class RestUtilities {

	public static String CONTENT_TYPE;
	public static RequestSpecBuilder REQUEST_BUILDER;
	public static RequestSpecification REQUEST_SPEC;
	public static ResponseSpecBuilder RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;
	
	public static RequestSpecification getRequestSpecification() {
		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(URLPath.BASE_URL);
		REQUEST_SPEC = REQUEST_BUILDER.build();
		return REQUEST_SPEC;
	}

	public static ResponseSpecification getResponseSpecification() {
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}

	//Creates request specification by adding a single query parameter
	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			String param, String value) {
		return rspec.queryParam(param, value);
	}

	//Creates request specification by adding list of query parameters
	public static RequestSpecification createQueryParam(RequestSpecification rspec,
			Map<String, String> queryMap) {
		return rspec.queryParams(queryMap);
	}
	
	//Sets content type value
	public static void setContentType(ContentType type) {
		given().contentType(type);
		if (type != ContentType.JSON) { CONTENT_TYPE = "xml";	}
		else { CONTENT_TYPE = "json";	}
	}

}
