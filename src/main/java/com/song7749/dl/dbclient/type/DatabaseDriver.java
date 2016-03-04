package com.song7749.dl.dbclient.type;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.util.StringUtils;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

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
@ApiModel
public enum DatabaseDriver {

	@ApiModelProperty
	mysql(
			"mysql",
			"com.mysql.jdbc.Driver",
			"jdbc:mysql://{host}:{port}/{schemaName}?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}",
			"SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_TYPE='BASE TABLE'",
			"SELECT ORDINAL_POSITION COLUMN_ID,COLUMN_NAME,IS_NULLABLE NULLABLE,COLUMN_KEY,DATA_TYPE,COLUMN_TYPE DATA_LENGTH,CHARACTER_SET_NAME CHARACTER_SET,EXTRA,COLUMN_DEFAULT DEFAULT_VALUE,COLUMN_COMMENT COMMENTS FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{tableName}'",
			"SELECT TABLE_NAME OWNER, INDEX_NAME, INDEX_TYPE, if(NON_UNIQUE=0,'UNIQUE','NOT_UNIQUE') as UNIQUENESS, CARDINALITY, COLUMN_NAME, SEQ_IN_INDEX COLUMN_POSITION, 'ASC' as DESCEND FROM information_schema.statistics WHERE table_name='{tableName}' AND TABLE_SCHEMA='{schemaName}'",
			null,
			"SELECT TABLE_NAME AS VIEW_NAME,VIEW_DEFINITION AS TEXT FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			"SELECT * FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}'"),

	@ApiModelProperty
	oracle(
			"oracle",
			"oracle.jdbc.driver.OracleDriver",
			"jdbc:oracle:thin:@{host}:{port}:{schemaName}",
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM USER_TABLES T1, USER_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME",
			"SELECT a.COLUMN_ID,a.COLUMN_NAME,a.NULLABLE,decode(b.CONSTRAINT_TYPE,null,'NO','YES') COLUMN_KEY,a.DATA_TYPE,a.DATA_LENGTH,'' CHARACTER_SET,a.DATA_SCALE EXTRA,a.DATA_DEFAULT DEFAULT_VALUE,c.COMMENTS COMMENTS FROM USER_TAB_COLUMNS a, ( SELECT a.TABLE_NAME,a.COLUMN_NAME,b.CONSTRAINT_TYPE FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b WHERE a.TABLE_NAME = b.TABLE_NAME AND a.CONSTRAINT_NAME = b.CONSTRAINT_NAME AND b.CONSTRAINT_TYPE='P') b, USER_COL_COMMENTS c  WHERE a.TABLE_NAME = b.TABLE_NAME (+) AND a.COLUMN_NAME = b.COLUMN_NAME (+) AND a.TABLE_NAME = c.TABLE_NAME (+) AND a.COLUMN_NAME = c.COLUMN_NAME (+) AND a.TABLE_NAME = '{tableName}' ORDER BY a.COLUMN_ID",
			"SELECT a.OWNER, a.INDEX_NAME, a.INDEX_TYPE, a.UNIQUENESS, a.NUM_ROWS CARDINALITY , b.COLUMN_NAME, b.COLUMN_POSITION,b.DESCEND FROM ALL_INDEXES a, ALL_IND_COLUMNS b WHERE a.index_name = b.index_name  AND a.table_name='{tableName}'",
			"select * from table(dbms_xplan.display('plan_table',null,'typical',null))",
			"SELECT VIEW_NAME,TEXT FROM USER_VIEWS",
			"select * from user_objects uo where object_type = 'PROCEDURE'");

/* SQLite 지원을 위한 테스트 진행 중
	@ApiModelProperty
	sqlite(
			"org.sqlite.JDBC",
			"jdbc:sqlite:{host}",
			"SELECT tbl_name as TABLE_NAME, '' as TABLE_COMMENT FROM sqlite_master",
			"pragma table_info({tableName})",
			"SELECT 'LOCAL' as OWNER, name as INDEX_NAME, '' as INDEX_TYPE, sql as COLUMN_NAME, '' as COLUMN_POSITION, '' as CARDINALITY, '' as UNIQUENESS, 'ASC' as DESCEND  FROM sqlite_master WHERE type = 'index' and tbl_name='{tableName}'",
			"");
*/


	Logger logger = LoggerFactory.getLogger(getClass());

	private String dbms;
	private String driverName;
	private String url;
	private String tableListQuery;
	private String fieldListQuery;
	private String indexListQuery;
	private String explainQuery;
	private String viewListQuery;
	private String procedureListQuery;

	public String getDriverName() {
		return driverName;
	}

	public String getUrl(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, url);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTableListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, tableListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	public String getFieldListQueryQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, fieldListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	public String getIndexListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, indexListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	public String getViewListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, viewListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getProcedureListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, procedureListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getDbms() {
		return dbms;
	}

	public String getExplainQuery() {
		return explainQuery;
	}

	DatabaseDriver(String dbms, String drivaerName, String url, String tableListQuery,
			String fieldListQuery, String indexListQuery,String explainQuery,
			String viewListQuery,String viewTableQuery) {
		this.dbms = dbms;
		this.driverName = drivaerName;
		this.url = url;
		this.tableListQuery = tableListQuery;
		this.fieldListQuery = fieldListQuery;
		this.indexListQuery = indexListQuery;
		this.explainQuery 	= explainQuery;
		this.viewListQuery 	= viewListQuery;
		this.procedureListQuery	= viewTableQuery;
	}

	private String repalceServerInfo(ServerInfo serverInfo, String str)
			throws IllegalArgumentException, IllegalAccessException {

		for (Field f : serverInfo.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if(null!=f.get(serverInfo)){
				str = StringUtils.replace("\\{" + f.getName() + "\\}",f.get(serverInfo).toString(), str);
			}
		}
		return str;
	}
}