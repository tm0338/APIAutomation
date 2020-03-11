package com.testautomation.APIAutomation.helper.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author tm0338
 * Contains methods that help with logging and reporting functionalities
 */
public class CommonUtil {

	//Report variables
	public static ExtentReports REPORT = null;
	public static ExtentTest REPORT_LOG;
	public static String SUCCESS_MESSAGE="";
	public static String FAILURE_MESSAGE="";
	
	//Test data hashmap
	public static HashMap<String, String[]> testCaseDataMap;

	//Allows to add message at current step in extent report
	public static void appendMsg(Logger log, String msgType, String msg) {
		String logMsg = logFormat(msg);
		String reportMsg = reportFormat(msg);
		switch(msgType.toUpperCase()) {
		case "INFO": case "PASS":
			log.info(logMsg);
			SUCCESS_MESSAGE =reportMsg;
			break;
		case "ERR": case "ERROR": case "FAIL": case "FAILURE": case "FATAL":
			log.error(logMsg);
			FAILURE_MESSAGE =reportMsg;
			break;
		case "WARN": case "WARNING": case "SKIP": case "UNKNOWN": default:
			log.warn(logMsg);
			SUCCESS_MESSAGE =reportMsg;
			FAILURE_MESSAGE =reportMsg;
			break;
		}
		
	}
	
	//Allows to add a test step in extent report
	public static void writeMsg(Logger log,String msgType,String msg){
		LogStatus logStatus = LogStatus.INFO;
		String reportMsg = reportFormat(msg);
		String logMsg = logFormat(msg);
		
		switch(msgType.toUpperCase()){
		case "INFO":
			logStatus = LogStatus.INFO;
			log.info(logMsg);
			break;
		case "WARN": case "WARNING":
			logStatus = LogStatus.WARNING;
			log.warn(logMsg);
			break;
		case "ERR": case "ERROR":
			logStatus = LogStatus.ERROR;
			log.error(logMsg);
			break;
		case "FAIL": case "FAILURE":
			logStatus = LogStatus.FAIL;
			log.error(logMsg);
			break;
		case "PASS":
			logStatus = LogStatus.PASS;
			log.info(logMsg);
			break;
		case "SKIP":
			logStatus = LogStatus.SKIP;
			log.warn(logMsg);
			break;
		case "FATAL":
			logStatus = LogStatus.FATAL;
			log.fatal(logMsg);
			break;
		default:
			logStatus = LogStatus.UNKNOWN;
			log.info(logMsg);
		}
		
		REPORT_LOG.log(logStatus, reportMsg);
	}

	//Makes console log more readable by applying start tabs
	public static String logFormat(String msg){
		return msg.replaceAll("\n", "\n\t\t\t\t\t\t");
	}
	
	//Formats text to be html compatible
	public static String reportFormat(String msg){
		return msg.replaceAll("\n", "<br>").replaceAll("\t", "&emsp;").replaceAll("\\s", "&nbsp;");
	}
	
	
	
	
}
