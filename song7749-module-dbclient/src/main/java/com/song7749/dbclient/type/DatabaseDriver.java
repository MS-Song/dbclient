package com.song7749.dbclient.type;

import static com.song7749.util.LogMessageFormatter.format;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.song7749.dbclient.domain.Database;
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
	POSTGRESQL(
			// dbms
			"postgresql",
			// driverName
			"org.postgresql.Driver",
			// connect url
			"jdbc:postgresql://{host}:{port}/{schemaName}?serverTimezone=UTC+9&characterEncoding={charset}",
			// validate query
			"select 1",
			// table list
			"SELECT C.RELNAME as TABLE_NAME, OBJ_DESCRIPTION(C.OID) as TABLE_COMMENT FROM PG_CATALOG.PG_CLASS C INNER JOIN PG_CATALOG.PG_NAMESPACE N ON C.RELNAMESPACE=N.OID WHERE C.RELKIND = 'r' AND NSPNAME = '{schemaName}'",
			//null,
			// field list
			"select col.ordinal_position as COLUMN_ID, col.column_name  as COLUMN_NAME, case when col.is_nullable = 'NO' then 'N' else 'Y' end as nullable, RA.constraint_type as COLUMN_KEY, col.udt_name as DATA_TYPE, concat(col.udt_name, (case when col.numeric_precision > 0 then  concat('(', CAST(col.numeric_precision AS text), ',' ,CAST(col.numeric_scale AS text), ')') when col.character_maximum_length > 0 then concat('(', CAST(col.character_maximum_length AS text), ')') else null end)) as DATA_LENGTH, db.datctype as CHARACTER_SET, '' as EXTRA, col.column_default as DEFAULT_VALUE, PD.DESCRIPTION as comments from pg_database db join INFORMATION_SCHEMA.columns col on col.table_schema = db.datname join PG_STAT_ALL_TABLES PS on ps.schemaname = col.table_schema and ps.relname = col.table_name left outer join (select r1.OBJOID, OBJSUBID, r2.attname, R1.description from PG_DESCRIPTION R1 join PG_ATTRIBUTE R2 on R1.OBJOID = R2.ATTRELID and R1.OBJSUBID = R2.ATTNUM) PD on PD.OBJOID = PS.RELID and PD.OBJSUBID <> 0 and pd.attname = col.column_name left outer join (SELECT A.TABLE_CATALOG, A.table_name, A.constraint_type, b.column_name, b.constraint_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS A , INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B where A.TABLE_CATALOG   = B.TABLE_CATALOG AND A.TABLE_SCHEMA    = B.TABLE_SCHEMA AND A.TABLE_NAME = B.TABLE_NAME AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME) RA on RA.table_catalog = col.table_schema and RA.table_name = col.table_name and RA.column_name = col.column_name where PS.SCHEMANAME = '{schemaName}' and PS.RELNAME = '{name}'",
			//null,
			// index list
			"select tablename as owner, indexname as INDEX_NAME, '' as INDEX_TYPE, (case when POSITION('UNIQUE' in indexdef) > 0 then 'UNIQUE' else 'NOT_UNIQUE' end) as UNIQUENESS, '' cardinality, '' as COLUMN_NAME, '' COLUMN_POSITION, 'ASC' as DESCEND from pg_indexes where schemaname = '{schemaName}' and tablename = '{name}'",
			// explain
			"EXPLAIN (ANALYSE) select * from {name}",
			// view list
			"SELECT TABLE_NAME AS NAME, 'view' AS COMMENTS, '' as LAST_UPDATE_TIME, 'VALID' as STATUS FROM INFORMATION_SCHEMA.VIEWS where TABLE_SCHEMA='{schemaName}'",
			// view detail
			"select TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, VIEW_DEFINITION, CHECK_OPTION, '' as IS_UPDATABLE, '' as SECURITY_TYPE, '' as CHARACTER_SET_CLIENT, '' as COLLATION_CONNECTION from INFORMATION_SCHEMA.views where table_schema = '{schemaName}' AND TABLE_NAME='{name}'",
			// view source
			"select table_name as NAME, view_definition as text from information_schema.views where table_schema = '{schemaName}' and table_name = '{name}'",
			// procedure list
			"select SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS from INFORMATION_SCHEMA.routines where specific_catalog = '{schemaName}' and specific_schema = '{schemaName}' and ROUTINE_TYPE = 'PROCEDURE'",
			// procedure detail
			"select SPECIFIC_NAME, ROUTINE_CATALOG, ROUTINE_SCHEMA, ROUTINE_NAME, ROUTINE_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_BODY, ROUTINE_DEFINITION, EXTERNAL_NAME, EXTERNAL_LANGUAGE, PARAMETER_STYLE, IS_DETERMINISTIC, SQL_DATA_ACCESS, SQL_PATH, SECURITY_TYPE, CREATED, LAST_ALTERED, '' as SQL_MODE, '' as ROUTINE_COMMENT, '' as definer, '' as CHARACTER_SET_CLIENT, '' as COLLATION_CONNECTION, '' as DATABASE_COLLATION from INFORMATION_SCHEMA.routines where ROUTINE_TYPE = 'PROCEDURE' and ROUTINE_SCHEMA = '{schemaName}' and SPECIFIC_NAME = '{name}'",
			// procedure source
			"select routine_name as name, routine_definition as text from INFORMATION_SCHEMA.routines where ROUTINE_TYPE = 'PROCEDURE' and ROUTINE_SCHEMA = '{schemaName}' and SPECIFIC_NAME = '{name}'",
			// function list
			"select SPECIFIC_NAME as NAME, LAST_ALTERED as LAST_UPDATE_TIME, 'VALID' as STATUS from INFORMATION_SCHEMA.routines where specific_catalog = '{schemaName}' and specific_schema = '{schemaName}' and ROUTINE_TYPE = 'FUNCTION'",
			// function detail
			"select SPECIFIC_NAME, ROUTINE_CATALOG, ROUTINE_SCHEMA, ROUTINE_NAME, ROUTINE_TYPE, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION, NUMERIC_SCALE, DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, DTD_IDENTIFIER, ROUTINE_BODY, ROUTINE_DEFINITION, EXTERNAL_NAME, EXTERNAL_LANGUAGE, PARAMETER_STYLE, IS_DETERMINISTIC, SQL_DATA_ACCESS, SQL_PATH, SECURITY_TYPE, CREATED, LAST_ALTERED, '' as SQL_MODE, '' as ROUTINE_COMMENT, '' as definer, '' as CHARACTER_SET_CLIENT, '' as COLLATION_CONNECTION, '' as DATABASE_COLLATION from INFORMATION_SCHEMA.routines where ROUTINE_TYPE = 'FUNCTION' and ROUTINE_SCHEMA = '{schemaName}' and SPECIFIC_NAME = '{name}'",
			// function source
			"select routine_name as name, routine_definition as text from INFORMATION_SCHEMA.routines where ROUTINE_TYPE = 'FUNCTION' and ROUTINE_SCHEMA = '{schemaName}' and SPECIFIC_NAME = '{name}'",
			// trigger list
			"SELECT  TRIGGER_NAME as NAME, '' as LAST_UPDATE_TIME, 'VALID' as  STATUS FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}'",
			// trigger detail
			"SELECT TRIGGER_CATALOG,TRIGGER_SCHEMA,TRIGGER_NAME,EVENT_MANIPULATION,EVENT_OBJECT_CATALOG,EVENT_OBJECT_SCHEMA,EVENT_OBJECT_TABLE,ACTION_ORDER,ACTION_CONDITION,ACTION_ORIENTATION,ACTION_TIMING,ACTION_REFERENCE_OLD_TABLE,ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW,ACTION_REFERENCE_NEW_ROW, CREATED, '' as SQL_MODE, '' as DEFINER, '' as CHARACTER_SET_CLIENT, '' as COLLATION_CONNECTION, '' as DATABASE_COLLATION  FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// trigger source
			"select pg_get_triggerdef(trig.oid) as trg_source from INFORMATION_SCHEMA.TRIGGERS r1 join pg_catalog.pg_trigger trig on trig.tgname = r1.trigger_name JOIN pg_catalog.pg_proc proc ON trig.tgfoid = proc.oid where r1.EVENT_OBJECT_SCHEMA = '{schemaName}' and trig.tgname='{name}'",
			// sequence list
			"SELECT CL.RELNAME AS NAME, COALESCE(PG_SEQUENCE_LAST_VALUE(CL.OID), 0) AS CURRENT_VALUE, SQ.MINIMUM_VALUE AS MIN_VALUE, SQ.MAXIMUM_VALUE AS MAX_VALUE, SQ.INCREMENT AS INCREMENT_BY FROM PG_CLASS AS CL JOIN PG_NAMESPACE NS ON CL.RELNAMESPACE = NS.OID JOIN INFORMATION_SCHEMA.SEQUENCES SQ ON SQ.SEQUENCE_SCHEMA = NS.NSPNAME AND SQ.SEQUENCE_NAME = CL.RELNAME WHERE CL.RELKIND = 'S' AND NS.NSPNAME NOT IN ('hint_plan') AND SQ.SEQUENCE_SCHEMA = '{schemaName}' ORDER BY 1,2 DESC",
			// sequence detail
			"SELECT CL.RELNAME AS NAME, COALESCE(PG_SEQUENCE_LAST_VALUE(CL.OID), 0) AS CURRENT_VALUE, SQ.MINIMUM_VALUE AS MIN_VALUE, SQ.MAXIMUM_VALUE AS MAX_VALUE, SQ.INCREMENT AS INCREMENT_BY FROM PG_CLASS AS CL JOIN PG_NAMESPACE NS ON CL.RELNAMESPACE = NS.OID JOIN INFORMATION_SCHEMA.SEQUENCES SQ ON SQ.SEQUENCE_SCHEMA = NS.NSPNAME AND SQ.SEQUENCE_NAME = CL.RELNAME WHERE CL.RELKIND = 'S' AND NS.NSPNAME NOT IN ('hint_plan') AND SQ.SEQUENCE_SCHEMA = '{schemaName}' and CL.RELNAME = '{name}' ORDER BY 1,2 DESC",
			// process list
			"SELECT pid as ID, query as INFO FROM pg_stat_activity",
			// kill connection
			"SELECT pg_cancel_backend({id})",
			// create table query
			"select string_agg(ddl_txt::text, E'\n') as table_ddl from ((select 'CREATE TABLE ' || 'public.test_info' || '(' || string_agg(pa.attname || ' ' || pg_catalog.format_type(pa.atttypid, pa.atttypmod) || coalesce((select\t' DEFAULT ' || substring(pg_get_expr(paf.adbin, paf.adrelid) for 128) from pg_attrdef paf where\tpaf.adrelid = pa.attrelid and paf.adnum = pa.attnum\tand pa.atthasdef), '') || case when pa.attnotnull = true then ' NOT NULL' else '' end, E'\n, ') || coalesce((select E'\n, ' || 'CONSTRAINT' || ' ' || conindid::regclass::varchar || ' ' || pg_get_constraintdef(oid) from pg_constraint where connamespace::regnamespace::varchar = '{schemaName}' and conrelid::regclass::varchar = '{name}' and contype = 'p'), '') || E'\n);' as ddl_txt from pg_attribute pa join pg_class pc on pa.attrelid = pc.oid where\tpc.relnamespace::regnamespace::varchar = '{schemaName}' and pc.relname::varchar = '{name}' and pa.attnum > 0\tand not pa.attisdropped\tand pc.relkind = 'r' group by pa.attrelid) union all (select string_agg(indexdef || ';' ::text, E'\n') as ddl_txt from pg_indexes where schemaname = '{schemaName}' and tablename = '{name}')) as t",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"select col.table_name, col.column_name  as COLUMN_NAME, PD.DESCRIPTION as COLUMN_COMMENT from pg_database db join INFORMATION_SCHEMA.columns col on col.table_schema = db.datname join PG_STAT_ALL_TABLES PS on ps.schemaname = col.table_schema and ps.relname = col.table_name join PG_DESCRIPTION PD on PD.OBJOID = PS.RELID and PD.OBJSUBID <> 0 join PG_ATTRIBUTE PA on pa.attname = col.column_name and PD.OBJOID = PA.ATTRELID and PD.OBJSUBID = PA.ATTNUM left outer join (SELECT A.TABLE_CATALOG, A.table_name, A.constraint_type, b.column_name, b.constraint_name FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS A , INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B where A.TABLE_CATALOG   = B.TABLE_CATALOG AND A.TABLE_SCHEMA    = B.TABLE_SCHEMA AND A.TABLE_NAME = B.TABLE_NAME AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME) RA on RA.table_catalog = col.table_schema and RA.table_name = col.table_name and RA.column_name = col.column_name WHERE TABLE_SCHEMA='{schemaName}'",
			// 한정자 추가
			//"select * from (select * from (select row_number() over() as ROW_NUM, A.* from (\n {sqlBody} \n) A ) ra where ra.ROW_NUM <= {end} ) rb where rb.ROW_NUM > {start}",
			"select rc.* from (select rb.* from (select row_number() over() as rnum, ra.* from (\n {sqlBody}\n) ra ) rb where rb.rnum <= {end}+{start} ) rc where rc.rnum > {start}",
			// Affected Row 를 발생시키는 명령어
			"insert,update,delete,create,drop,truncate,alter,kill"),
	@ApiModelProperty
	MYSQL(
			// dbms
			"mysql",
			// driverName
			"com.mysql.cj.jdbc.Driver",
			// connect url
			"jdbc:mysql://{host}:{port}/{schemaName}?serverTimezone=Asia/Seoul&autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}&useSSL=false",
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
			"SELECT TRIGGER_CATALOG,TRIGGER_SCHEMA,TRIGGER_NAME,EVENT_MANIPULATION,EVENT_OBJECT_CATALOG,EVENT_OBJECT_SCHEMA,EVENT_OBJECT_TABLE,ACTION_ORDER,ACTION_CONDITION,ACTION_ORIENTATION,ACTION_TIMING,ACTION_REFERENCE_OLD_TABLE,ACTION_REFERENCE_NEW_TABLE, ACTION_REFERENCE_OLD_ROW,ACTION_REFERENCE_NEW_ROW, CREATED,SQL_MODE,DEFINER,CHARACTER_SET_CLIENT,COLLATION_CONNECTION,DATABASE_COLLATION  FROM INFORMATION_SCHEMA.TRIGGERS WHERE  EVENT_OBJECT_SCHEMA='{schemaName}' AND TRIGGER_NAME='{name}'",
			// trigger source
			"show create trigger {name}",
			// sequence list
			"SELECT c.table_name as NAME, t.AUTO_INCREMENT as CURRENT_VALUE, '1' as MIN_VALUE, COLUMN_TYPE as MAX_VALUE, '1' as INCREMENT_BY from information_schema.columns c join information_schema.tables t on(c.table_schema=t.table_schema and c.table_name=t.table_name) where c.table_schema='{schemaName}' and c.extra='auto_increment'",
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

	MARIADB(
			// dbms
			"mysql",
			// driverName
			"org.mariadb.jdbc.Driver",
			// connect url
			"jdbc:mariadb://{host}:{port}/{schemaName}?autoReconnect=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding={charset}&useSSL=false",
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
			"SELECT c.table_name as NAME, t.AUTO_INCREMENT as CURRENT_VALUE, '1' as MIN_VALUE, COLUMN_TYPE as MAX_VALUE, '1' as INCREMENT_BY from information_schema.columns c join information_schema.tables t on(c.table_schema=t.table_schema and c.table_name=t.table_name) where c.table_schema='{schemaName}' and c.extra='auto_increment'",
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
			"SELECT T1.TABLE_NAME TABLE_NAME,T2.COMMENTS TABLE_COMMENT FROM ALL_TABLES T1, ALL_TAB_COMMENTS T2 WHERE T2.TABLE_NAME(+) = T1.TABLE_NAME and T1.OWNER=T2.OWNER and T1.OWNER=upper('{schemaOwner}') order by TABLE_NAME asc",
			// field list
			"SELECT a.COLUMN_ID,a.COLUMN_NAME,a.NULLABLE,decode(b.CONSTRAINT_TYPE,null,'NO','YES') COLUMN_KEY,a.DATA_TYPE,a.DATA_LENGTH,'' CHARACTER_SET,a.DATA_SCALE EXTRA,a.DATA_DEFAULT DEFAULT_VALUE,c.COMMENTS COMMENTS FROM ALL_TAB_COLUMNS a, ( SELECT a.TABLE_NAME,a.COLUMN_NAME,b.CONSTRAINT_TYPE FROM ALL_CONS_COLUMNS a, ALL_CONSTRAINTS b WHERE a.TABLE_NAME = b.TABLE_NAME AND a.CONSTRAINT_NAME = b.CONSTRAINT_NAME AND b.CONSTRAINT_TYPE='P') b, ALL_COL_COMMENTS c  WHERE a.TABLE_NAME = b.TABLE_NAME (+) AND a.COLUMN_NAME = b.COLUMN_NAME (+) AND a.TABLE_NAME = c.TABLE_NAME (+) AND a.COLUMN_NAME = c.COLUMN_NAME (+) AND a.OWNER=c.OWNER AND a.OWNER=upper('{schemaOwner}') AND a.TABLE_NAME = '{name}' ORDER BY a.COLUMN_ID",
			// index list
			"SELECT a.OWNER, a.INDEX_NAME, a.INDEX_TYPE, a.UNIQUENESS, a.NUM_ROWS CARDINALITY , b.COLUMN_NAME, b.COLUMN_POSITION,b.DESCEND FROM ALL_INDEXES a, ALL_IND_COLUMNS b WHERE a.index_name = b.index_name AND  a.OWNER=upper('{schemaOwner}') AND a.table_name='{name}'",
			// explain
			//"SELECT * from table(dbms_xplan.display('plan_table',null,'typical',null))",
			null,
			// view list
			"SELECT uv.VIEW_NAME as NAME, utc.COMMENTS AS COMMENTS, uo.LAST_DDL_TIME AS LAST_UPDATE_TIME, uo.STATUS FROM All_VIEWS uv LEFT JOIN All_TAB_COMMENTS utc ON (uv.VIEW_NAME=utc.TABLE_NAME and utc.TABLE_TYPE='VIEW') JOIN ALL_OBJECTS uo ON (uv.VIEW_NAME=uo.OBJECT_NAME AND uo.object_type = 'VIEW') WHERE uv.OWNER=uo.OWNER and uv.OWNER=upper('{schemaOwner}') order by NAME asc",
			// view detail
			"SELECT uv.VIEW_NAME, uv.TEXT_LENGTH, uv.TYPE_TEXT_LENGTH, uv.TYPE_TEXT, uv.OID_TEXT_LENGTH, uv.OID_TEXT, uv.VIEW_TYPE_OWNER, uv.VIEW_TYPE, uv.SUPERVIEW_NAME, uv.EDITIONING_VIEW, uv.READ_ONLY, uo.OBJECT_NAME, uo.SUBOBJECT_NAME, uo.OBJECT_ID, uo.DATA_OBJECT_ID, uo.OBJECT_TYPE, uo.CREATED, uo.LAST_DDL_TIME, uo.TIMESTAMP, uo.STATUS, uo.TEMPORARY, uo.GENERATED, uo.SECONDARY, uo.NAMESPACE, uo.EDITION_NAME FROM ALL_VIEWS uv JOIN ALL_OBJECTS uo on(uv.VIEW_NAME=uo.OBJECT_NAME) WHERE uv.OWNER=uo.OWNER and uv.OWNER=upper('{schemaOwner}') AND uv.VIEW_NAME=upper('{name}')",
			// view source
			"SELECT uv.VIEW_NAME as NAME, uv.TEXT FROM ALL_VIEWS uv JOIN ALL_OBJECTS uo ON (uv.VIEW_NAME=uo.OBJECT_NAME AND uo.object_type = 'VIEW') WHERE uv.OWNER=uo.OWNER and uv.OWNER=upper('{schemaOwner}')  AND uv.VIEW_NAME=upper('{name}')",
			// procedure list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from ALL_OBJECTS uo where uo.object_type = 'PROCEDURE' AND uo.OWNER=upper('{schemaOwner}')  order by NAME asc",
			// procedure detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from ALL_OBJECTS uo where uo.OWNER=upper('{schemaOwner}') AND OBJECT_NAME = upper('{name}')",
			// procedure source
			"SELECT us.NAME as NAME, SUBSTR(XMLAgg(XMLElement(x, '$^$', us.text) ORDER BY us.line).Extract('//text()').getClobVal(), 1) as text from ALL_SOURCE us where us.OWNER=upper('{schemaOwner}') AND us.NAME = upper('{name}') GROUP BY us.NAME",
			// function list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from ALL_OBJECTS uo where uo.object_type = 'FUNCTION' AND uo.OWNER=upper('{schemaOwner}') order by NAME asc",
			// function detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from all_objects uo where OWNER=upper('{schemaOwner}') AND OBJECT_NAME = upper('{name}')",
			// function source
			"SELECT us.NAME as NAME, SUBSTR(XMLAgg(XMLElement(x, '$^$', us.text) ORDER BY us.line).Extract('//text()').getClobVal(), 1) as text from ALL_SOURCE us where us.OWNER=upper('{schemaOwner}') AND us.NAME = upper('{name}') GROUP BY us.NAME",
			// trigger list
			"SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS from ALL_OBJECTS uo where uo.object_type = 'TRIGGER' AND uo.OWNER=upper('{schemaOwner}') order by NAME asc",
			// trigger detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from all_objects uo where OWNER=upper('{schemaOwner}') AND OBJECT_NAME = upper('{name}')",
			// trigger source
			"SELECT TRIGGER_NAME as NAME, TRIGGER_TYPE, TRIGGERING_EVENT, TABLE_OWNER, BASE_OBJECT_TYPE, TABLE_NAME, COLUMN_NAME, REFERENCING_NAMES, WHEN_CLAUSE, STATUS, DESCRIPTION, ACTION_TYPE, TRIGGER_BODY as TEXT, CROSSEDITION, BEFORE_STATEMENT, BEFORE_ROW, AFTER_ROW,AFTER_STATEMENT, INSTEAD_OF_ROW, FIRE_ONCE, APPLY_SERVER_ONLY from all_triggers where OWNER=upper('{schemaOwner}') AND TRIGGER_NAME=upper('{name}')",
			//sequence list
			"SELECT SEQUENCE_NAME as NAME, LAST_NUMBER as CURRENT_VALUE, MIN_VALUE, MAX_VALUE, INCREMENT_BY from all_sequences where SEQUENCE_OWNER=upper('{schemaOwner}') order by NAME asc",
			// sequence detail
			"SELECT OBJECT_NAME, SUBOBJECT_NAME, OBJECT_ID, DATA_OBJECT_ID, OBJECT_TYPE, CREATED, LAST_DDL_TIME, TIMESTAMP, STATUS, TEMPORARY, GENERATED, SECONDARY, NAMESPACE, EDITION_NAME from all_objects uo where uo.OWNER=upper('{schemaOwner}') AND uo.OBJECT_NAME = upper('{name}')",
			// process list
			"SELECT concat(concat(s.sid , ','), s.serial#) as ID, sql.sql_text as SQL_TEXT from v$session s join v$sql sql on s.sql_id = sql.sql_id where s.program='dbClient'",
			// kill process
			"alter system kill session '{id}'",
			// create table query
			"select dbms_metadata.get_ddl( 'TABLE', upper('{name}'), upper('{schemaOwner}') ) as CREATE_TALBE from dual",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT UTC.TABLE_NAME AS TABLE_NAME, UTC.COLUMN_NAME AS COLUMN_NAME, UCC.COMMENTS AS COLUMN_COMMENT FROM ALL_TAB_COLUMNS UTC , ALL_COL_COMMENTS UCC WHERE UTC.TABLE_NAME = UCC.TABLE_NAME (+) AND UTC.COLUMN_NAME = UCC.COLUMN_NAME (+) AND UTC.OWNER=upper('{schemaOwner}')",
			// 한정자 추가
			"SELECT * FROM ( SELECT ROWNUM AS RNUM , A.* FROM ( \n {sqlBody} \n ) A WHERE  ROWNUM <= {end} ) WHERE  RNUM > {start}",
			// Affected Row 를 발생시키는 명령어 -- comment on 의 경우 다른 방법이 필요할 듯.
			"insert,update,delete,create,drop,truncate,alter,comment on,grant,kill,declare,begin"),

	@ApiModelProperty
	MSSQL(
			// dbms
			"mssql",
			// driverName
			"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			// url
			"jdbc:sqlserver://{host}:{port};databaseName={schemaName};user={account};password={password}",
			// validate query
			"select 1 ",
			// table list
			"SELECT A.name AS TABLE_NAME, b.value AS TABLE_COMMENT FROM SYSOBJECTS A LEFT OUTER JOIN SYS.extended_properties B ON A.id = B.major_id AND B.minor_id = 0 WHERE A.xtype = 'U' ORDER  BY A.name ASC",
			// field list
			"SELECT A.ORDINAL_POSITION AS COLUMN_ID, A.COLUMN_NAME AS COLUMN_NAME, CASE WHEN A.IS_NULLABLE = 'NO' THEN 'N' ELSE 'Y' END AS NULLABLE, CASE WHEN C.COLUMN_NAME IS NULL THEN 'NO' ELSE 'YES' END AS COLUMN_KEY, A.DATA_TYPE, CASE WHEN A.CHARACTER_MAXIMUM_LENGTH IS NULL THEN A.NUMERIC_PRECISION ELSE A.CHARACTER_MAXIMUM_LENGTH END AS DATA_LENGTH,  A.COLLATION_NAME AS CHARACTER_SET, A.NUMERIC_SCALE AS EXTRA, A.COLUMN_DEFAULT AS DEFAULT_VALUE, B.value AS COMMENTS FROM INFORMATION_SCHEMA.COLUMNS A LEFT OUTER JOIN SYS.extended_properties B ON B.major_id = OBJECT_ID(A.TABLE_NAME) AND B.minor_id = A.ORDINAL_POSITION LEFT OUTER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE C ON C.TABLE_NAME = A.TABLE_NAME AND C.ORDINAL_POSITION = A.ORDINAL_POSITION WHERE A.TABLE_NAME = '{name}' ORDER BY COLUMN_ID ASC",
			// index list
			"SELECT C.TABLE_SCHEMA AS {schemaName}, A.name AS INDEX_NAME, A.type_desc AS INDEX_TYPE, CASE WHEN A.is_unique = 1 THEN 'UNIQUE' ELSE 'NONUNIQUE' END AS UNIQUENESS, '' AS CARDINALITY, C.COLUMN_NAME, B.key_ordinal AS COLUMN_POSITION, CASE WHEN B.is_descending_key = 1 THEN 'DESC' ELSE 'ASC' END AS DESCEND FROM sys.indexes A INNER JOIN sys.index_columns B ON B.object_id = A.object_id AND B.index_id = A.index_id INNER JOIN INFORMATION_SCHEMA.COLUMNS C ON OBJECT_ID(C.TABLE_NAME) = B.object_id AND C.ORDINAL_POSITION = B.column_id WHERE A.object_id = OBJECT_ID('{name}') ORDER BY A.index_id ASC",
			// explain
			null,
			// view list
			"SELECT A.name AS NAME, B.value AS COMMENTS, CONVERT(VARCHAR(10), A.modify_date, 111) AS LAST_UPDATE_TIME, '' AS STATUS FROM sys.views A LEFT OUTER JOIN sys.extended_properties B ON B.major_id = A.object_id AND B.minor_id = 0 AND B.name = 'MS_Description'",
			// view detail
			null,
			// view source
			"SELECT '{name}' AS NAME, OBJECT_DEFINITION (OBJECT_ID(N'{name}')) AS text FROM sys.views A LEFT OUTER JOIN sys.extended_properties B ON B.major_id = A.object_id AND B.minor_id = 0 AND B.name = 'MS_Description'",
			// procedure list
			"SELECT A.ROUTINE_NAME AS NAME, CONVERT(VARCHAR(10), A.LAST_ALTERED, 111) AS LAST_UPDATE_TIME, '' AS STATUS FROM INFORMATION_SCHEMA.ROUTINES A WHERE A.ROUTINE_TYPE = 'PROCEDURE' ORDER BY A.LAST_ALTERED DESC",
			// procedure detail
			"SELECT  ROUTINE_NAME AS OBJECT_NAME, '' AS SUBOBJECT_NAME, OBJECT_ID(ROUTINE_NAME) AS OBJECT_ID, '' AS DATA_OBJECT_ID, ROUTINE_TYPE AS OBJECT_TYPE, CREATED AS CREATED, LAST_ALTERED AS LAST_DDL_TIME, '' AS TIMESTAMP, '' AS STATUS, '' AS TEMPORARY, '' AS GENERATED, '' AS SECONDARY, '' AS NAMESPACE, '' AS EDITION_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME = '{name}'",
			// procedure source
			"SELECT '{name}' AS NAME, OBJECT_DEFINITION (OBJECT_ID(N'{name}')) AS text FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME = '{name}'",
			// function list
			"SELECT A.ROUTINE_NAME AS NAME, CONVERT(VARCHAR(10), A.LAST_ALTERED, 111) AS LAST_UPDATE_TIME, '' AS STATUS FROM INFORMATION_SCHEMA.ROUTINES A WHERE A.ROUTINE_TYPE = 'FUNCTION' ORDER BY A.LAST_ALTERED DESC",
			// function detail
			"SELECT ROUTINE_NAME AS OBJECT_NAME, '' AS SUBOBJECT_NAME, OBJECT_ID(ROUTINE_NAME) AS OBJECT_ID, '' AS DATA_OBJECT_ID, ROUTINE_TYPE AS OBJECT_TYPE, CREATED AS CREATED, LAST_ALTERED AS LAST_DDL_TIME, '' AS TIMESTAMP, '' AS STATUS, '' AS TEMPORARY, '' AS GENERATED, '' AS SECONDARY, '' AS NAMESPACE, '' AS EDITION_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME = '{name}'",
			// function source
			"SELECT '{name}' AS NAME, OBJECT_DEFINITION (OBJECT_ID(N'{name}')) AS text FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_NAME = '{name}'",
			// trigger list
			"SELECT name AS NAME, CONVERT(VARCHAR(10), modify_date, 111) AS LAST_UPDATE_TIME, '' AS STATUS FROM sys.triggers",
			// trigger detail
			"SELECT name AS OBJECT_NAME, '' AS SUBOBJECT_NAME, object_id AS OBJECT_ID, '' AS DATA_OBJECT_ID, type_desc AS OBJECT_TYPE, create_date AS CREATED, modify_date AS LAST_DDL_TIME, '' AS TIMESTAMP, '' AS STATUS, '' AS TEMPORARY, '' AS GENERATED, '' AS SECONDARY, '' AS NAMESPACE, '' AS EDITION_NAME FROM sys.triggers WHERE name = '{name}'",
			// trigger source
			"SELECT A.name AS NAME, '' AS TRIGGER_TYPE, C.TRIGGERING_EVENT, '' AS TABLE_OWNER, D.type_desc AS BASE_OBJECT_TYPE, D.name AS TABLE_NAME, '' AS COLUMN_NAME, '' AS REFERENCING_NAMES, '' AS WHEN_CLAUSE, '' AS STATUS, OBJECT_DEFINITION (OBJECT_ID(N'TRG_TB_SSO_USER_DATA')) AS DESCRIPTION, '' AS ACTION_TYPE, '' AS TEXT, '' AS CROSSEDITION, '' AS BEFORE_STATEMENT, '' AS BEFORE_ROW, '' AS AFTER_ROW, '' AS AFTER_STATEMENT, '' AS INSTEAD_OF_ROW, '' AS FIRE_ONCE, '' AS APPLY_SERVER_ONLY FROM sys.triggers A CROSS APPLY ( SELECT * FROM sysobjects WHERE id = A.object_id ) B CROSS APPLY ( SELECT STUFF( (SELECT ',' + type_desc FROM  sys.trigger_events WHERE object_id = A.object_id FOR XML PATH ('')), 1, 1, '') AS TRIGGERING_EVENT ) C  CROSS APPLY ( SELECT * FROM  SYS.tables WHERE object_id = A.parent_id) D WHERE A.name = '{name}'",
			// sequence list
			null,
			// sequence detail
			null,
			// process list
			"SELECT A.spid AS ID, B.text AS SQL_TEXT FROM sys.sysprocesses AS A CROSS APPLY sys.dm_exec_sql_text(sql_handle) AS B",
			// kill connection
			null,
			// create table query
			null,
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT A.TABLE_NAME, A.COLUMN_NAME AS COLUMN_NAME,  B.value AS COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS A LEFT OUTER JOIN SYS.extended_properties B ON B.major_id = OBJECT_ID(A.TABLE_NAME) AND B.minor_id = A.ORDINAL_POSITION LEFT OUTER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE C ON C.TABLE_NAME = A.TABLE_NAME AND C.ORDINAL_POSITION = A.ORDINAL_POSITION",
			// 한정자 추가
			"DECLARE @StartRowNum INT={start}, @EndRownum INT={end} {sqlBody} OFFSET @StartRowNum ROWS FETCH NEXT @EndRownum ROWS ONLY",
			// Affected Row 를 발생시키는 명령어
			"insert,update,delete,create,drop,truncate,alter,kill,comment on"),

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
			"SELECT ID, SEQUENCE_NAME NAME, CURRENT_VALUE, MIN_VALUE, MAX_VALUE, INCREMENT INCREMENT_BY FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='{schemaName}'",
			// sequence detail
			"SELECT * FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='{schemaName}' AND SEQUENCE_NAME='{name}'",
			// process list
			"select ID , STATEMENT as SQL_TEXT from information_schema.sessions",
			// kill connection
			"CALL CANCEL_SESSION({id})",
			// create table query
			"SELECT SQL CREATE_TALBE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='{name}'",
			// 자동완성용 테이블/필드 전체 리스트 조회
			"SELECT TABLE_NAME, COLUMN_NAME, REMARKS COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA='{schemaName}'",
			// 한정자 추가
			"{sqlBody} \n Limit {start},{end}",
			// Affected Row 를 발생시키는 명령어
			"insert,update,delete,create,drop,truncate,alter,kill,comment on"),

	@ApiModelProperty
	MONETDB(
			// dbms
			"monetdb",
			// driverName
			"nl.cwi.monetdb.jdbc.MonetDriver",
			// url
			"jdbc:monetdb://{host}:{port}/{schemaName}?so_timeout=7000&treat_clob_as_varchar=true",
			// validate query
			"select '1'",
			// table list
			"select name TABLE_NAME, '' TABLE_COMMENT from sys.tables where schema_id=(select id from sys.schemas where name='{schemaName}') order by name",
			// field list
			"select id COLUMN_ID, name COLUMN_NAME, null NULLABLE, '' COLUMN_KEY, type DATA_TYPE, type_digits DATA_LENGTH, ''  CHARACTER_SET, '' EXTRA, '' DEFAULT_VALUE, '' COMMENTS from sys.columns where table_id=(select id from sys.tables where schema_id=(select id from sys.schemas where name='{schemaName}') and name='{name}')",
			// index list
			null,
			// explain
			null,
			// view list
			null,
			// view detail
			null,
			// view source
			null,
			// procedure list
			null,
			// procedure detail
			null,
			// procedure source
			null,
			// function list
			null,
			// function detail
			null,
			// function source
			null,
			// trigger list
			null,
			// trigger detail
			null,
			// trigger source
			null,
			// sequence list
			null,
			// sequence detail
			null,
			// process list
			null,
			// kill connection
			null,
			// create table query
			null,
			// 자동완성용 테이블/필드 전체 리스트 조회
			null,
			// 한정자 추가
			"{sqlBody} \n Limit {end} OFFSET {start}",
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
			throw new IllegalArgumentException(e.getMessage());
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
			} else if(this.dbms.toLowerCase().equals("mssql")) {
				if(null != sqlBody && sqlBody.toLowerCase().indexOf("order by")<=0) {
					// SQL 내에 order by 가 없을 경우에는 paging 을 할 수 없다. order by 를 추가하라고 Alert 한다.
					throw new IllegalArgumentException("page 처리를 위해 PK 필드 Order By 조건을 추가하거나, use limit 를 false 로 변경하세요.");
				}
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

	public static DatabaseDriver getDatabaseDriverByDriverClassName(String driverClassName) {
		for(DatabaseDriver dd:DatabaseDriver.values()) {
			if(dd.driverName.equals(driverClassName)) {
				return dd;
			}
		}
		return null;
	}
}