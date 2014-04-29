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
	mysql(
			"com.mysql.jdbc.Driver",
			"jdbc:mysql://{host}:{port}/{schemaName}",
			"?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}",
			"SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}'",
			"SELECT ORDINAL_POSITION COLUMN_ID,COLUMN_NAME,IS_NULLABLE NULLABLE,COLUMN_KEY,DATA_TYPE,COLUMN_TYPE DATA_LENGTH,CHARACTER_SET_NAME CHARACTER_SET,EXTRA,COLUMN_DEFAULT DEFAULT_VALUE,COLUMN_COMMENT COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{tableName}'",
			"show index from {schemaName}.{tableName}"),

	oracle(
			"oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@{host}:{port}:{schemaName}",
			"",
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM USER_TABLES T1, USER_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME",
			"SELECT a.COLUMN_ID,a.COLUMN_NAME,a.NULLABLE,decode(b.CONSTRAINT_TYPE,null,'NO','YES') COLUMN_KEY,a.DATA_TYPE,a.DATA_LENGTH,'' CHARACTER_SET,a.DATA_SCALE EXTRA,a.DATA_DEFAULT DEFAULT_VALUE,c.COMMENTS COMMENT FROM USER_TAB_COLUMNS a, ( SELECT a.TABLE_NAME,a.COLUMN_NAME,b.CONSTRAINT_TYPE FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b WHERE a.TABLE_NAME = b.TABLE_NAME AND a.CONSTRAINT_NAME = b.CONSTRAINT_NAME AND b.CONSTRAINT_TYPE='P') b, USER_COL_COMMENTS c  WHERE a.TABLE_NAME = b.TABLE_NAME (+) AND a.COLUMN_NAME = b.COLUMN_NAME (+) AND a.TABLE_NAME = c.TABLE_NAME (+) AND a.COLUMN_NAME = c.COLUMN_NAME (+) AND a.TABLE_NAME = '{tableName}' ORDER BY a.COLUMN_ID",
			"SELECT a.OWNER, a.INDEX_NAME, a.INDEX_TYPE, a.UNIQUENESS, a.NUM_ROWS, b.COLUMN_NAME, b.COLUMN_POSITION,b.DESCEND FROM ALL_INDEXES a, ALL_IND_COLUMNS b WHERE a.index_name = b.index_name  AND a.table_name='{tableName}'");

	private String driverName;
	private String url;
	private String parameter;
	private String tableListQuery;
	private String fieldListQuery;
	private String indexListQuery;

	public String getDriverName() {
		return driverName;
	}

	public String getUrl() {
		return url;
	}

	public String getParameter() {
		return parameter;
	}

	public String getTableListQuery() {
		return tableListQuery;
	}

	public String getFieldListQueryQuery() {
		return fieldListQuery;
	}

	public String getIndexListQuery() {
		return indexListQuery;
	}

	DatabaseDriver(String drivaerName, String url, String parameter,
			String tableListQuery, String fieldListQuery, String indexListQuery) {
		this.driverName = drivaerName;
		this.url = url;
		this.parameter = parameter;
		this.tableListQuery = tableListQuery;
		this.fieldListQuery = fieldListQuery;
		this.indexListQuery = indexListQuery;
	}
}