package com.song7749.log.type;

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
public enum LogType {
	// 로그 정의
	LOG("QUERY", "LOGIN","MEMBER","DATABASE","INCIDENT_ALARM");

	// 테이블 명칭에 사용됨
	public static final String QUERY 	= "QUERY";
	public static final String LOGIN 	= "LOGIN";
	public static final String MEMBER 	= "MEMBER";
	public static final String DATABASE = "DATABASE";
	public static final String INCIDENT_ALARM = "INCIDENT_ALARM";

	private String query;
	private String login;
	private String member;
	private String database;
	private String incidentAlarm;

	LogType(String query, String login, String member, String database, String incidentAlarm) {
		this.query=query;
		this.login=login;
		this.member=member;
		this.database=database;
		this.database=incidentAlarm;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getIncidentAlarm() {
		return incidentAlarm;
	}

	public void setIncidentAlarm(String incidentAlarm) {
		this.incidentAlarm = incidentAlarm;
	}
}