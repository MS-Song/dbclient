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
			"?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}",
			"SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}'",
			"",
			""),
	oracle("oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@{host}:{port}:{schemaName}",
			"",
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM USER_TABLES T1, USER_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME",
			"",
			"");


	private String driverName;
	private String url;
	private String parameter;
	private String tableListQuery;
	private String fieldListQuery;
	private String indexListQuery;

	public String getDriverName(){
		return driverName;
	}

	public String getUrl(){
		return url;
	}

	public String getParameter(){
		return parameter;
	}

	public String getTableListQuery(){
		return tableListQuery;
	}

	public String getFieldListQueryQuery(){
		return fieldListQuery;
	}
	public String getIndexListQuery(){
		return indexListQuery;
	}

	DatabaseDriver(String drivaerName,String url,String parameter,String tableListQuery,String fieldListQuery, String indexListQuery) {
		this.driverName=drivaerName;
		this.url=url;
		this.parameter=parameter;
		this.tableListQuery=tableListQuery;
		this.fieldListQuery=fieldListQuery;
		this.indexListQuery=indexListQuery;
	}
}