package com.song7749.log.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * Class Name : LogType.java
 * Description : 로그 객체에 기록하는 로그의 타입
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 27.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 27.
*/


@Getter
@AllArgsConstructor
public enum LogType {

	QUERY(Constants.QUERY)
	, LOGIN(Constants.LOGIN)
	, MEMBER(Constants.MEMBER)
	, DATABASE(Constants.DATABASE)
	, INCIDENT_ALARM(Constants.INCIDENT_ALARM);

	private String name;
	
    public static class Constants {
		public static final String QUERY 	= "QUERY";
		public static final String LOGIN 	= "LOGIN";
		public static final String MEMBER 	= "MEMBER";
		public static final String DATABASE = "DATABASE";
		public static final String INCIDENT_ALARM = "INCIDENT_ALARM";
	}
}