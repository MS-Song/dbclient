package com.song7749.dl.dbclient.type;

import static com.song7749.util.LogMessageFormatter.format;

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
 *
 *
 *
 */
@ApiModel
public enum DatabaseDriver {

	@ApiModelProperty
	mysql(
			// dbms
			"mysql",
			// driverName
			"com.mysql.jdbc.Driver",
			// connect url
			"jdbc:mysql://{host}:{port}/{schemaName}?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}",
			// table list
			"SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_TYPE='BASE TABLE'",
			// field list
			"SELECT ORDINAL_POSITION COLUMN_ID,COLUMN_NAME,IS_NULLABLE NULLABLE,COLUMN_KEY,DATA_TYPE,COLUMN_TYPE DATA_LENGTH,CHARACTER_SET_NAME CHARACTER_SET,EXTRA,COLUMN_DEFAULT DEFAULT_VALUE,COLUMN_COMMENT COMMENTS FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
			// index list
			"SELECT TABLE_NAME OWNER, INDEX_NAME, INDEX_TYPE, if(NON_UNIQUE=0,'UNIQUE','NOT_UNIQUE') as UNIQUENESS, CARDINALITY, COLUMN_NAME, SEQ_IN_INDEX COLUMN_POSITION, 'ASC' as DESCEND FROM information_schema.statistics WHERE table_name='{name}' AND TABLE_SCHEMA='{schemaName}'",
			// explain
			null,
			// view list
			"SELECT TABLE_NAME AS NAME, 'view' AS COMMENTS, '' as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view detail
			"SELECT TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,VIEW_DEFINITION,CHECK_OPTION,IS_UPDATABLE,DEFINER,SECURITY_TYPE,CHARACTER_SET_CLIENT,COLLATION_CONNECTION FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view source
			"show create view {name}",
			// procedure list
			"SELECT SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}'",
			// procedure detail
			"SELECT SPECIFIC_NAME,ROUTINE_CATALOG,ROUTINE_SCHEMA,ROUTINE_NAME,ROUTINE_TYPE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,DTD_IDENTIFIER,ROUTINE_BODY,ROUTINE_DEFINITION,EXTERNAL_NAME,EXTERNAL_LANGUAGE,PARAMETER_STYLE,IS_DETERMINISTIC,SQL_DATA_ACCESS,SQL_PATH,SECURITY_TYPE,CREATED,LAST_ALTERED,SQL_MODE,ROUTINE_COMMENT,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// procedure source
			"show create procedure {name}",
			// function list
			"SELECT SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='FUNCTION' AND ROUTINE_SCHEMA='{schemaName}'",
			// function detail
			"SELECT SPECIFIC_NAME,ROUTINE_CATALOG,ROUTINE_SCHEMA,ROUTINE_NAME,ROUTINE_TYPE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,DTD_IDENTIFIER,ROUTINE_BODY,ROUTINE_DEFINITION,EXTERNAL_NAME,EXTERNAL_LANGUAGE,PARAMETER_STYLE,IS_DETERMINISTIC,SQL_DATA_ACCESS,SQL_PATH,SECURITY_TYPE,CREATED,LAST_ALTERED,SQL_MODE,ROUTINE_COMMENT,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION  FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='FUNCTION' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// function source
			"show create function {name}",
			// trigger list
			"SELECT  TRIGGER_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as  STATUS FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}'",
			// trigger detail
			"SELECT TRIGGER_CATALOG,TRIGGER_SCHEMA,TRIGGER_NAME,EVENT_MANIPULATION,EVENT_OBJECT_CATALOG,EVENT_OBJECT_SCHEMA,EVENT_OBJECT_TABLE,ACTION_ORDER,ACTION_CONDITION,ACTION_ORIENTATION,ACTION_TIMING,ACTION_REFERENCE_OLD_TABLE,ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW,ACTION_REFERENCE_NEW_ROW,                   CREATED,SQL_MODE,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION  FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// trigger source
			"show create trigger {name}",
			// sequence list
			"SELECT c.table_name as NAME, t.AUTO_INCREMENT as LAST_VALUE, '1' as MIN_VALUE, COLUMN_TYPE as MAX_VALUE, '1' as INCREMENT_BY from information_schema.columns c join information_schema.tables t on(c.table_schema=t.table_schema and c.table_name=t.table_name) where c.table_schema='{schemaName}' and c.extra='auto_increment'",
			// sequence detail
			"SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_TYPE='BASE TABLE'  AND TABLE_NAME='{name}'",
			// process list
			"SELECT ID, info as SQL_TEXT FROM information_schema.processlist",
			// kill connection
			"KILL CONNECTION {id}",
			// create table query
			"show create table {name}",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT TABLE_NAME, COLUMN_NAME, COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}'",
			// 한정자 추가
			"{sqlBody} \n Limit {start},{end}"),

	@ApiModelProperty
	oracle(
			// dbms
			"oracle",
			// driverName
			"oracle.jdbc.driver.OracleDriver",
			// url
			"jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST={host})(PORT={port})))(CONNECT_DATA=(SERVICE_NAME={schemaName})))",
			// table list
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM USER_TABLES T1, USER_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME order by TABLE_NAME asc",
			// field list
			"SELECT a.COLUMN_ID,a.COLUMN_NAME,a.NULLABLE,decode(b.CONSTRAINT_TYPE,null,'NO','YES') COLUMN_KEY,a.DATA_TYPE,a.DATA_LENGTH,'' CHARACTER_SET,a.DATA_SCALE EXTRA,a.DATA_DEFAULT DEFAULT_VALUE,c.COMMENTS COMMENTS FROM USER_TAB_COLUMNS a, ( SELECT a.TABLE_NAME,a.COLUMN_NAME,b.CONSTRAINT_TYPE FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b WHERE a.TABLE_NAME = b.TABLE_NAME AND a.CONSTRAINT_NAME = b.CONSTRAINT_NAME AND b.CONSTRAINT_TYPE='P') b, USER_COL_COMMENTS c  WHERE a.TABLE_NAME = b.TABLE_NAME (+) AND a.COLUMN_NAME = b.COLUMN_NAME (+) AND a.TABLE_NAME = c.TABLE_NAME (+) AND a.COLUMN_NAME = c.COLUMN_NAME (+) AND a.TABLE_NAME = '{name}' ORDER BY a.COLUMN_ID",
			// index list
			"SELECT a.OWNER, a.INDEX_NAME, a.INDEX_TYPE, a.UNIQUENESS, a.NUM_ROWS CARDINALITY , b.COLUMN_NAME, b.COLUMN_POSITION,b.DESCEND FROM ALL_INDEXES a, ALL_IND_COLUMNS b WHERE a.index_name = b.index_name  AND a.table_name='{name}'",
			// explain
			"SELECT * from table(dbms_xplan.display('plan_table',null,'typical',null))",
			// view list
			"SELECT uv.VIEW_NAME as NAME, utc.COMMENTS AS COMMENTS, uo.LAST_DDL_TIME AS LAST_UPDATE_TIME, uo.STATUS FROM  USER_VIEWS uv LEFT JOIN USER_TAB_COMMENTS utc ON (uv.VIEW_NAME=utc.TABLE_NAME and utc.TABLE_TYPE='VIEW') JOIN USER_OBJECTS uo ON (uv.VIEW_NAME=uo.OBJECT_NAME AND uo.object_type = 'VIEW') order by NAME asc",
			// view detail
			"SELECT uv.VIEW_NAME, uv.TEXT_LENGTH, uv.TYPE_TEXT_LENGTH, uv.TYPE_TEXT, uv.OID_TEXT_LENGTH, uv.OID_TEXT, uv.VIEW_TYPE_OWNER, uv.VIEW_TYPE, uv.SUPERVIEW_NAME, uv.EDITIONING_VIEW, uv.READ_ONLY, uo.OBJECT_NAME, uo.SUBOBJECT_NAME, uo.OBJECT_ID, uo.DATA_OBJECT_ID, uo.OBJECT_TYPE, uo.CREATED, uo.LAST_DDL_TIME, uo.TIMESTAMP, uo.STATUS, uo.TEMPORARY, uo.GENERATED, uo.SECONDARY, uo.NAMESPACE, uo.EDITION_NAME FROM USER_VIEWS uv JOIN USER_OBJECTS uo on(uv.VIEW_NAME=uo.OBJECT_NAME) WHERE uv.VIEW_NAME=upper('{name}')",
			// view source
			"SELECT uv.VIEW_NAME as NAME, uv.TEXT FROM USER_VIEWS uv JOIN USER_OBJECTS uo ON (uv.VIEW_NAME=uo.OBJECT_NAME AND uo.object_type = 'VIEW') WHERE  uv.VIEW_NAME=upper('{name}')",
			// procedure list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from user_objects uo where uo.object_type = 'PROCEDURE' order by NAME asc",
			// procedure detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from user_objects uo where OBJECT_NAME = upper('{name}')",
			// procedure source
			"SELECT us.NAME as NAME, SUBSTR(XMLAgg(XMLElement(x, '$^$', us.text) ORDER BY us.line).Extract('//text()').getClobVal(), 1) as text from user_source us where us.NAME = upper('{name}') GROUP BY us.NAME",
			// function list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from user_objects uo where uo.object_type = 'FUNCTION' order by NAME asc",
			// function detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from user_objects uo where OBJECT_NAME = upper('{name}')",
			// function source
			"SELECT us.NAME as NAME, SUBSTR(XMLAgg(XMLElement(x, '$^$', us.text) ORDER BY us.line).Extract('//text()').getClobVal(), 1) as text from user_source us where us.NAME = upper('{name}') GROUP BY us.NAME",
			// trigger list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from user_objects uo where uo.object_type = 'TRIGGER' order by NAME asc",
			// trigger detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from user_objects uo where OBJECT_NAME = upper('{name}')",
			// trigger source
			"SELECT TRIGGER_NAME as NAME, TRIGGER_TYPE, TRIGGERING_EVENT, TABLE_OWNER, BASE_OBJECT_TYPE, TABLE_NAME, COLUMN_NAME, REFERENCING_NAMES, WHEN_CLAUSE, STATUS, DESCRIPTION, ACTION_TYPE, TRIGGER_BODY as TEXT, CROSSEDITION, BEFORE_STATEMENT, BEFORE_ROW, AFTER_ROW,AFTER_STATEMENT, INSTEAD_OF_ROW, FIRE_ONCE, APPLY_SERVER_ONLY from user_triggers where TRIGGER_NAME=upper('{name}')",
			//sequence list
			"SELECT SEQUENCE_NAME as NAME, LAST_NUMBER as LAST_VALUE, MIN_VALUE, MAX_VALUE, INCREMENT_BY from user_sequences order by NAME asc",
			// sequence detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from user_objects uo where OBJECT_NAME = upper('{name}')",
			// process list
			"SELECT concat(concat(s.sid , ','), s.serial#) as ID, sql.sql_text as SQL_TEXT from v$session s join v$sql sql on s.sql_id = sql.sql_id where s.program='dbClient'",
			// kill process
			"alter system kill session '{id}'",
			// create table query
			"select dbms_metadata.get_ddl( 'TABLE', '{name}', '{account}' ) as CREATE_TALBE from dual",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT UTC.TABLE_NAME AS TABLE_NAME, UTC.COLUMN_NAME AS COLUMN_NAME, UCC.COMMENTS AS COLUMN_COMMENT FROM USER_TAB_COLUMNS UTC , USER_COL_COMMENTS UCC WHERE UTC.TABLE_NAME = UCC.TABLE_NAME (+) AND UTC.COLUMN_NAME = UCC.COLUMN_NAME (+)",
			// 한정자 추가
			"SELECT * FROM ( SELECT ROWNUM AS RNUM , A.* FROM ( \n {sqlBody} \n ) A WHERE  ROWNUM <= {end} ) WHERE  RNUM > {start}");

	/*
	@ApiModelProperty
	sqlite(
			// dbms
			"sqlite",
			// driverName
			"org.sqlite.JDBC",
			// connect url
			"jdbc:sqlite:{host}",
			// table list
			"SELECT name as TABLE_NAME, '' as TABLE_COMMENT FROM sqlite_master WHERE type='table'",
			// field list
			"PRAGMA table_info('{name}')",
			// index list
			"SELECT TABLE_NAME OWNER, INDEX_NAME, INDEX_TYPE, if(NON_UNIQUE=0,'UNIQUE','NOT_UNIQUE') as UNIQUENESS, CARDINALITY, COLUMN_NAME, SEQ_IN_INDEX COLUMN_POSITION, 'ASC' as DESCEND FROM information_schema.statistics WHERE table_name='{name}' AND TABLE_SCHEMA='{schemaName}'",
			// explain
			null,
			// view list
			"SELECT TABLE_NAME AS NAME, 'view' AS COMMENTS, '' as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view detail
			"SELECT TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,VIEW_DEFINITION,CHECK_OPTION,IS_UPDATABLE,DEFINER,SECURITY_TYPE,CHARACTER_SET_CLIENT,COLLATION_CONNECTION FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view source
			"SELECT TABLE_NAME AS NAME, VIEW_DEFINITION AS TEXT FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// procedure list
			"SELECT SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}'",
			// procedure detail
			"SELECT SPECIFIC_NAME,ROUTINE_CATALOG,ROUTINE_SCHEMA,ROUTINE_NAME,ROUTINE_TYPE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,DTD_IDENTIFIER,ROUTINE_BODY,ROUTINE_DEFINITION,EXTERNAL_NAME,EXTERNAL_LANGUAGE,PARAMETER_STYLE,IS_DETERMINISTIC,SQL_DATA_ACCESS,SQL_PATH,SECURITY_TYPE,CREATED,LAST_ALTERED,SQL_MODE,ROUTINE_COMMENT,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// procedure source
			"SELECT SPECIFIC_NAME as NAME, ROUTINE_DEFINITION as TEXT FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='PROCEDURE' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// function list
			"SELECT SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='FUNCTION' AND ROUTINE_SCHEMA='{schemaName}'",
			// function detail
			"SELECT SPECIFIC_NAME,ROUTINE_CATALOG,ROUTINE_SCHEMA,ROUTINE_NAME,ROUTINE_TYPE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,DATETIME_PRECISION,CHARACTER_SET_NAME,COLLATION_NAME,DTD_IDENTIFIER,ROUTINE_BODY,ROUTINE_DEFINITION,EXTERNAL_NAME,EXTERNAL_LANGUAGE,PARAMETER_STYLE,IS_DETERMINISTIC,SQL_DATA_ACCESS,SQL_PATH,SECURITY_TYPE,CREATED,LAST_ALTERED,SQL_MODE,ROUTINE_COMMENT,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION  FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='FUNCTION' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// function source
			"SELECT SPECIFIC_NAME as NAME, ROUTINE_DEFINITION as TEXT FROM INFORMATION_SCHEMA.ROUTINES  WHERE ROUTINE_TYPE='FUNCTION' AND ROUTINE_SCHEMA='{schemaName}' AND SPECIFIC_NAME='{name}'",
			// trigger list
			"SELECT  TRIGGER_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as  STATUS FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}'",
			// trigger detail
			"SELECT TRIGGER_CATALOG,TRIGGER_SCHEMA,TRIGGER_NAME,EVENT_MANIPULATION,EVENT_OBJECT_CATALOG,EVENT_OBJECT_SCHEMA,EVENT_OBJECT_TABLE,ACTION_ORDER,ACTION_CONDITION,ACTION_ORIENTATION,ACTION_TIMING,ACTION_REFERENCE_OLD_TABLE,ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW,ACTION_REFERENCE_NEW_ROW,                   CREATED,SQL_MODE,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION  FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// trigger source
			"SELECT TRIGGER_NAME AS NAME, ACTION_STATEMENT AS TEXT,ACTION_TIMING,EVENT_MANIPULATION,EVENT_OBJECT_TABLE,ACTION_ORIENTATION  FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// sequence list
			"SELECT c.table_name as NAME, t.AUTO_INCREMENT as LAST_VALUE, '1' as MIN_VALUE, COLUMN_TYPE as MAX_VALUE, '1' as INCREMENT_BY from information_schema.columns c join information_schema.tables t on(c.table_schema=t.table_schema and c.table_name=t.table_name) where c.table_schema='{schemaName}' and c.extra='auto_increment'",
			// sequence detail
			"SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_TYPE='BASE TABLE'  AND TABLE_NAME='{name}'",
			// process list
			"SELECT ID, info as SQL_TEXT FROM information_schema.processlist",
			// kill connection
			"KILL CONNECTION {id}",
			// create table query
			"show create table {name}",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT TABLE_NAME, COLUMN_NAME, COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}'",
			// 한정자
			"{sqlBody} \n Limit {start},{end}");
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
	private String viewDetailQuery;
	private String viewSourceQuery;
	private String procedureListQuery;
	private String procedureDetailQuery;
	private String procedureSourceQuery;
	private String functionListQuery;
	private String functionDetailQuery;
	private String functionSourceQuery;
	private String triggerListQuery;
	private String triggerDetailQuery;
	private String triggerSourceQuery;
	private String sequenceListQuery;
	private String sequenceDetailQuery;
	private String processListQuery;
	private String killProcessQuery;
	private String showCreateQuery;
	private String autoCompleteQuery;
	private String addRangeOperator;

	DatabaseDriver(
		String dbms,
		String driverName,
		String url,
		String tableListQuery,
		String fieldListQuery,
		String indexListQuery,
		String explainQuery,
		String viewListQuery,
		String viewDetailQuery,
		String viewSourceQuery,
		String procedureListQuery,
		String procedureDetailQuery,
		String procedureSourceQuery,
		String functionListQuery,
		String functionDetailQuery,
		String functionSourceQuery,
		String triggerListQuery,
		String triggerDetailQuery,
		String triggerSourceQuery,
		String sequenceListQuery,
		String sequenceDetailQuery,
		String processListQuery,
		String killProcessQuery,
		String showCreateQuery,
		String autoCompleteQuery,
		String addRangeOperator) {

		this.dbms					= dbms;
		this.driverName				= driverName;
		this.url					= url;
		this.tableListQuery			= tableListQuery;
		this.fieldListQuery			= fieldListQuery;
		this.indexListQuery			= indexListQuery;
		this.explainQuery			= explainQuery;
		this.viewListQuery			= viewListQuery;
		this.viewDetailQuery		= viewDetailQuery;
		this.viewSourceQuery		= viewSourceQuery;
		this.procedureListQuery		= procedureListQuery;
		this.procedureDetailQuery 	= procedureDetailQuery;
		this.procedureSourceQuery 	= procedureSourceQuery;
		this.functionListQuery		= functionListQuery;
		this.functionDetailQuery	= functionDetailQuery;
		this.functionSourceQuery	= functionSourceQuery;
		this.triggerListQuery		= triggerListQuery;
		this.triggerDetailQuery		= triggerDetailQuery;
		this.triggerSourceQuery		= triggerSourceQuery;
		this.sequenceListQuery		= sequenceListQuery;
		this.sequenceDetailQuery	= sequenceDetailQuery;
		this.processListQuery		= processListQuery;
		this.killProcessQuery		= killProcessQuery;
		this.showCreateQuery		= showCreateQuery;
		this.autoCompleteQuery		= autoCompleteQuery;
		this.addRangeOperator		= addRangeOperator;
	}

	/**
	 * DBMS 정보 조회
	 * @return String
	 */
	public String getDbms() {
		return dbms;
	}

	/**
	 * JDBC Driver Name 조회
	 * @return String
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * Connect url 정보 조회
	 * @param serverInfo
	 * @return String
	 */
	public String getUrl(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, url);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * table list Query 조회
	 * @param serverInfo
	 * @return
	 */
	public String getTableListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, tableListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	/**
	 * field list Query 조회
	 * @param serverInfo
	 * @return String
	 */
	public String getFieldListQueryQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, fieldListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	/**
	 * index list Query 조회
	 * @param serverInfo
	 * @return String
	 */
	public String getIndexListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, indexListQuery);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

	}

	/**
	 * view list Query 조회
	 * @param serverInfo
	 * @return String
	 */
	public String getViewListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, viewListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * view detail Query 조회
	 * @param serverInfo
	 * @param name
	 * @return String
	 */
	public String getViewDetailQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, viewDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * view source Query 조회
	 * @param serverInfo
	 * @param name
	 * @return String
	 */
	public String getViewSourceQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, viewSourceQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure list query
	 * @param serverInfo
	 * @return String
	 */
	public String getProcedureListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, procedureListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure detail query
	 * @param serverInfo
	 * @return String
	 */
	public String getProcedureDetailQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, procedureDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure source query
	 * @param serverInfo
	 * @return String
	 */
	public String getProcedureSourceQuery(ServerInfo serverInfo){
		try{
			String replacement = StringUtils.replace(procedureSourceQuery,"$^$","");
			return repalceServerInfo(serverInfo, replacement);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, functionListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionDetailQuery(ServerInfo serverInfo){
		try {
			return repalceServerInfo(serverInfo, functionDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionSourceQuery(ServerInfo serverInfo){
		try{
			String replacement = StringUtils.replace(functionSourceQuery,"$^$","");
			return repalceServerInfo(serverInfo, replacement);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, triggerListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerDetailQuery(ServerInfo serverInfo){
		try {
			return repalceServerInfo(serverInfo, triggerDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerSourceQuery(ServerInfo serverInfo){
		try{
			return repalceServerInfo(serverInfo, triggerSourceQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}


	public String getSequenceListQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, sequenceListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getSequenceListDetailQuery(ServerInfo serverInfo) {
		try {
			return repalceServerInfo(serverInfo, sequenceDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}


	public String getProcessListQuery(){
		return processListQuery;
	}

	public String getProcessKillQuery(String id){
		return StringUtils.replacePatten("\\{id\\}",id, killProcessQuery);
	}

	public String getExplainQuery() {
		return explainQuery;
	}

	public String getShowCreateQuery(ServerInfo serverInfo){
		try {
			return repalceServerInfo(serverInfo, showCreateQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getAutoCompleteQuery(ServerInfo serverInfo){
		try {
			return repalceServerInfo(serverInfo, autoCompleteQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getAddRangeOperator(String sqlBody, Long start, Long end){

		boolean isExecuteAble = true;

//		이미  CUD 에 대한 처리는 완료가 되어 있어 추가 검증이 불필요 하다.
//		if(sqlBody.toLowerCase().indexOf("insert")>=0
//				|| sqlBody.toLowerCase().indexOf("update")>=0
//				|| sqlBody.toLowerCase().indexOf("delete")>=0){
//			isExecuteAble = false;
//		}

		if(isExecuteAble){
			if(this.dbms.toLowerCase().equals("mysql")){
				// mysql의 경우 start=offset, limit=end 인 경우 100,100 이면, 100부터 100개 이다.
				if(sqlBody.toLowerCase().indexOf("limit") >=0){
					isExecuteAble = false;
				}
			} else if(this.dbms.toLowerCase().equals("oracle")){
				// oracle 의 경우 offset 과 limit 의 관계가 시작과 끝의 관계임으로 100,200 으로 표시해야 한다.
				end = start+end;
			}
		}

		if(isExecuteAble){
			sqlBody=StringUtils.replacePatten("\\{sqlBody\\}",sqlBody, addRangeOperator);
			sqlBody=StringUtils.replacePatten("\\{start\\}",start.toString(), sqlBody);
			sqlBody=StringUtils.replacePatten("\\{end\\}",end.toString(), sqlBody);
			logger.trace(format("{}","Query add limit complete"),"쿼리에 한정자를 포함하였습니다.");
		}
		return sqlBody;
	}

	private String repalceServerInfo(ServerInfo serverInfo, String str)
			throws IllegalArgumentException, IllegalAccessException {

		for (Field f : serverInfo.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if(null!=f.get(serverInfo)){
				str = StringUtils.replacePatten("\\{" + f.getName() + "\\}",f.get(serverInfo).toString(), str);
			}
		}
		return str;
	}
}