package com.song7749.dbclient.drs.type;

import static com.song7749.util.LogMessageFormatter.format;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.dbclient.drs.domain.Database;
import com.song7749.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


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
	MYSQL(
			// dbms
			"mysql",
			// driverName
			"com.mysql.jdbc.Driver",
			// connect url
			"jdbc:mysql://{host}:{port}/{schemaName}?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}&useSSL=false",
			// validate query
			"select 1 ",
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
			"SELECT TABLE_CATALOG,TABLE_SCHEMA,TABLE_NAME,VIEW_DEFINITION,CHECK_OPTION,IS_UPDATABLE,DEFINER,SECURITY_TYPE,CHARACTER_SET_CLIENT,COLLATION_CONNECTION FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
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
			"{sqlBody} \n Limit {start},{end}",
			// Affected Row 를 발생시키는 명령어
			"insert,update,delete,create,drop,truncate,alter,kill"),

	@ApiModelProperty
	ORACLE(
			// dbms
			"oracle",
			// driverName
			"oracle.jdbc.driver.OracleDriver",
			// url
			"jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST={host})(PORT={port})))(CONNECT_DATA=(SERVICE_NAME={schemaName})))",
			// validate query
			"select 1 from dual",
			// table list
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM USER_TABLES T1, USER_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME order by TABLE_NAME asc",
			// field list
			"SELECT a.COLUMN_ID,a.COLUMN_NAME,a.NULLABLE,decode(b.CONSTRAINT_TYPE,null,'NO','YES') COLUMN_KEY,a.DATA_TYPE,a.DATA_LENGTH,'' CHARACTER_SET,a.DATA_SCALE EXTRA,a.DATA_DEFAULT DEFAULT_VALUE,c.COMMENTS COMMENTS FROM USER_TAB_COLUMNS a, ( SELECT a.TABLE_NAME,a.COLUMN_NAME,b.CONSTRAINT_TYPE FROM USER_CONS_COLUMNS a, USER_CONSTRAINTS b WHERE a.TABLE_NAME = b.TABLE_NAME AND a.CONSTRAINT_NAME = b.CONSTRAINT_NAME AND b.CONSTRAINT_TYPE='P') b, USER_COL_COMMENTS c  WHERE a.TABLE_NAME = b.TABLE_NAME (+) AND a.COLUMN_NAME = b.COLUMN_NAME (+) AND a.TABLE_NAME = c.TABLE_NAME (+) AND a.COLUMN_NAME = c.COLUMN_NAME (+) AND a.TABLE_NAME = '{name}' ORDER BY a.COLUMN_ID",
			// index list
			"SELECT a.OWNER, a.INDEX_NAME, a.INDEX_TYPE, a.UNIQUENESS, a.NUM_ROWS CARDINALITY , b.COLUMN_NAME, b.COLUMN_POSITION,b.DESCEND FROM ALL_INDEXES a, ALL_IND_COLUMNS b WHERE a.index_name = b.index_name  AND a.table_name='{name}'",
			// explain
			//"SELECT * from table(dbms_xplan.display('plan_table',null,'typical',null))",
			null,
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
			"SELECT * FROM ( SELECT ROWNUM AS RNUM , A.* FROM ( \n {sqlBody} \n ) A WHERE  ROWNUM <= {end} ) WHERE  RNUM > {start}",
			// Affected Row 를 발생시키는 명령어 -- comment on 의 경우 다른 방법이 필요할 듯.
			"insert,update,delete,create,drop,truncate,alter,comment on,grant,kill"),

	@ApiModelProperty
	H2(
			// dbms
			"h2",
			// driverName
			"org.h2.Driver",
			// url
			"{host}",
			// validate query
			"select 1 ",
			// table list
			"SELECT TABLE_NAME, REMARKS as TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='{schemaName}' AND TABLE_TYPE='TABLE'",
			// field list
			"SELECT ORDINAL_POSITION COLUMN_ID,	COLUMN_NAME, IS_NULLABLE NULLABLE, '' as COLUMN_KEY, TYPE_NAME DATA_TYPE, CHARACTER_OCTET_LENGTH DATA_LENGTH, CHARACTER_SET_NAME CHARACTER_SET,	'' as EXTRA, COLUMN_DEFAULT DEFAULT_VALUE,	REMARKS COMMENTS FROM INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
			// index list
			"SELECT TABLE_NAME OWNER, INDEX_NAME, INDEX_TYPE_NAME INDEX_TYPE, DECODE(NON_UNIQUE,'FALSE','UNIQUE','TRUE','NOT_UNIQUE') UNIQUENESS, CARDINALITY, COLUMN_NAME, ORDINAL_POSITION COLUMN_POSITION, DECODE(ASC_OR_DESC,'A','ASC','D','DESC') DESCEND FROM  INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
			// explain
			null,
			// view list
			"select TABLE_NAME AS NAME, REMARKS AS COMMENTS, '' AS LAST_UPDATE_TIME, STATUS FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view detail
			"select ID, TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, CHECK_OPTION, IS_UPDATABLE, STATUS, REMARKS   FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
			// view source
			"select VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}' AND TABLE_NAME='{name}'",
			// procedure list
			"SELECT ALIAS_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.FUNCTION_ALIASES  WHERE ALIAS_SCHEMA='{schemaName}'",
			// procedure detail
			"SELECT ID, ALIAS_CATALOG, ALIAS_SCHEMA, ALIAS_NAME, JAVA_CLASS, JAVA_METHOD, DATA_TYPE, TYPE_NAME, COLUMN_COUNT, RETURNS_RESULT, REMARKS FROM INFORMATION_SCHEMA.FUNCTION_ALIASES WHERE ALIAS_SCHEMA='{schemaName}' AND ALIAS_NAME='{name}'",
			// procedure source
			"SELECT ALIAS_NAME NAME, SOURCE TEXT FROM INFORMATION_SCHEMA.FUNCTION_ALIASES WHERE ALIAS_SCHEMA='{schemaName}' AND ALIAS_NAME='{name}'",
			// function list
			"SELECT ALIAS_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.FUNCTION_ALIASES  WHERE ALIAS_SCHEMA='{schemaName}'",
			// function detail
			"SELECT ID, ALIAS_CATALOG, ALIAS_SCHEMA, ALIAS_NAME, JAVA_CLASS, JAVA_METHOD, DATA_TYPE, TYPE_NAME, COLUMN_COUNT, RETURNS_RESULT, REMARKS FROM INFORMATION_SCHEMA.FUNCTION_ALIASES WHERE ALIAS_SCHEMA='{schemaName}' AND ALIAS_NAME='{name}'",
			// function source
			"SELECT ALIAS_NAME NAME, SOURCE TEXT FROM INFORMATION_SCHEMA.FUNCTION_ALIASES WHERE ALIAS_SCHEMA='{schemaName}' AND ALIAS_NAME='{name}'",
			// trigger list
			"SELECT TRIGGER_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as  STATUS FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='{schemaName}'",
			// trigger detail
			"SELECT TRIGGER_CATALOG, TRIGGER_SCHEMA, TRIGGER_NAME, TRIGGER_TYPE, TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, BEFORE, JAVA_CLASS, QUEUE_SIZE, NO_WAIT, REMARKS FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// trigger source
			"SELECT TRIGGER_NAME NAME, SQL TEXT FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// sequence list
			"SELECT ID, SEQUENCE_NAME NAME, CURRENT_VALUE LAST_VALUE, MIN_VALUE, MAX_VALUE, INCREMENT INCREMENT_BY FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='{schemaName}'",
			// sequence detail
			"SELECT * FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='{schemaName}' AND SEQUENCE_NAME='{name}'",
			// process list
			"SELECT ID, info as SQL_TEXT FROM information_schema.processlist",
			// kill connection
			"KILL CONNECTION {id}",
			// create table query
			"SELECT SQL CREATE_TALBE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='{name}'",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT TABLE_NAME, COLUMN_NAME, REMARKS COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}'",
			// 한정자 추가
			"{sqlBody} \n Limit {start},{end}",
			// Affected Row 를 발생시키는 명령어
			"insert,update,delete,create,drop,truncate,alter,kill,comment on");


	Logger logger = LoggerFactory.getLogger(getClass());

	private String dbms;
	private String driverName;
	private String url;
	private String validateQuery;
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
	private String affectedRowCommands;

	DatabaseDriver(
		String dbms,
		String driverName,
		String url,
		String validateQuery,
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
		String addRangeOperator,
		String affectedRowCommands) {

		this.dbms					= dbms;
		this.driverName				= driverName;
		this.url					= url;
		this.validateQuery			= validateQuery;
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
		this.affectedRowCommands	= affectedRowCommands;
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
	 * @param database
	 * @return String
	 */
	public String getUrl(Database database) {
		try {
			return repalceDatabase(database, url);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * ValidateQuery
	 * @return String
	 */
	public String getValidateQuery() {
		return this.validateQuery;
	}


	/**
	 * table list Query 조회
	 * @param database
	 * @return
	 */
	public String getTableListQuery(Database database) {
		try {
			return repalceDatabase(database, tableListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * field list Query 조회
	 * @param database
	 * @return String
	 */
	public String getFieldListQueryQuery(Database database) {
		try {
			return repalceDatabase(database, fieldListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * index list Query 조회
	 * @param database
	 * @return String
	 */
	public String getIndexListQuery(Database database) {
		try {
			return repalceDatabase(database, indexListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * view list Query 조회
	 * @param database
	 * @return String
	 */
	public String getViewListQuery(Database database) {
		try {
			return repalceDatabase(database, viewListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * view detail Query 조회
	 * @param database
	 * @param name
	 * @return String
	 */
	public String getViewDetailQuery(Database database) {
		try {
			return repalceDatabase(database, viewDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * view source Query 조회
	 * @param database
	 * @param name
	 * @return String
	 */
	public String getViewSourceQuery(Database database) {
		try {
			return repalceDatabase(database, viewSourceQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure list query
	 * @param database
	 * @return String
	 */
	public String getProcedureListQuery(Database database) {
		if(null==procedureListQuery || "".equals(procedureListQuery)) {
			throw new IllegalArgumentException("Stored Procedure Not Support");
		}
		try {
			return repalceDatabase(database, procedureListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure detail query
	 * @param database
	 * @return String
	 */
	public String getProcedureDetailQuery(Database database) {
		try {
			return repalceDatabase(database, procedureDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	/**
	 * procedure source query
	 * @param database
	 * @return String
	 */
	public String getProcedureSourceQuery(Database database){
		try{
			String replacement = StringUtils.replace(procedureSourceQuery,"$^$","");
			return repalceDatabase(database, replacement);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionListQuery(Database database) {
		try {
			return repalceDatabase(database, functionListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionDetailQuery(Database database){
		try {
			return repalceDatabase(database, functionDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getFunctionSourceQuery(Database database){
		try{
			String replacement = StringUtils.replace(functionSourceQuery,"$^$","");
			return repalceDatabase(database, replacement);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerListQuery(Database database) {
		try {
			return repalceDatabase(database, triggerListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerDetailQuery(Database database){
		try {
			return repalceDatabase(database, triggerDetailQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getTriggerSourceQuery(Database database){
		try{
			return repalceDatabase(database, triggerSourceQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}


	public String getSequenceListQuery(Database database) {
		try {
			return repalceDatabase(database, sequenceListQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getSequenceListDetailQuery(Database database) {
		try {
			return repalceDatabase(database, sequenceDetailQuery);
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

	public String getShowCreateQuery(Database database){
		try {
			return repalceDatabase(database, showCreateQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getAutoCompleteQuery(Database database){
		try {
			return repalceDatabase(database, autoCompleteQuery);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getCause());
		}
	}

	public String getAddRangeOperator(String sqlBody, Long start, Long end){

		boolean isExecuteAble = true;
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


	/**
	 * Affected Row 를 발생하는 명령어를 리턴한다.
	 * @return String[]
	 */
	public String[] getAffectedRowCommands() {
		return this.affectedRowCommands.split(",");
	}

	/**
	 * Query 내에 AffectedRow 를 유발 시키는 내용 (CUD 또는 DML 구문이 들어 있는지 검출 한다.
	 * @param Query
	 * @return boolean
	 */
	public boolean isAffectedRowCommand(String query) {
		String[] queries = query.replace("\t", " ").replace("\n", " ").split(" ");

		for(String keyword : getAffectedRowCommands()) {
			for(int i=0; i < queries.length;i++) {
				String match = queries[i].toLowerCase();
				// 2단어 이상인 키워드 인 경우
				if(keyword.indexOf(" ") >= 0) {
					int wordCount = StringUtils.countMatches(keyword, " ");
					for(int j=1;j<=wordCount && i+j < queries.length; j++) {
						match+= " " + queries[i+j].toLowerCase();
					}
				}

//				logger.trace(format("keyword : {} = match : {}", "affected row 를 발생시키는 키워드 검증"),keyword,match);
				if(keyword.toLowerCase().equals(match)) {
					return true;
				}
			}

		}
		return false;
	}

	private String repalceDatabase(Database database, String str)
			throws IllegalArgumentException, IllegalAccessException {

		for (Field f : database.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if(null!=f.get(database)){
				str = StringUtils.replacePatten("\\{" + f.getName() + "\\}",f.get(database).toString(), str);
			}
		}
		logger.trace(format("{}", "Replace BY Database"), str);
		return str;
	}
}