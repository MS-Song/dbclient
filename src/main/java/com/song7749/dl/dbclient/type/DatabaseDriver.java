package com.song7749.dl.dbclient.type;

/**
 * <pre>
 * Class Name : DatabaseDriver.java
 * Description : 드라이버 설정, 추가 DBMS 지원을 위해서는 해당 ENUM 에 추가해야 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 28.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 28.
 */
public enum DatabaseDriver {
	mysql("com.mysql.jdbc.Driver",
			"jdbc:mysql://{host}:{port}/{schemaName}",
			"?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}"),
	oracle("oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@{host}:{port}:{schemaName}",
			"");


	private String driverName;
	private String url;
	private String parameter;

	public String getDriverName(){
		return driverName;
	}

	public String getUrl(){
		return url;
	}

	public String getParameter(){
		return parameter;
	}

	DatabaseDriver(String drivaerName,String url,String parameter) {
		this.driverName=drivaerName;
		this.url=url;
		this.parameter=parameter;
	}
}