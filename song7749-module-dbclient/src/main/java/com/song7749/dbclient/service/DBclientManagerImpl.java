package com.song7749.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;
import static com.song7749.util.StringUtils.getSubStringEqualMatchIndex;
import static com.song7749.util.StringUtils.htmlentities;
import static com.song7749.util.StringUtils.isSubStringEqualMatch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.song7749.common.MessageVo;
import com.song7749.dbclient.domain.Database;
import com.song7749.dbclient.domain.DatabasePrivacyPolicy;
import com.song7749.dbclient.repository.DatabasePrivacyPolicyRepository;
import com.song7749.dbclient.repository.DatabaseRepository;
import com.song7749.dbclient.type.DatabaseDriver;
import com.song7749.dbclient.value.DatabaseAddDto;
import com.song7749.dbclient.value.DatabaseDdlVo;
import com.song7749.dbclient.value.ExecuteQueryDto;
import com.song7749.dbclient.value.FieldVo;
import com.song7749.dbclient.value.FunctionVo;
import com.song7749.dbclient.value.IndexVo;
import com.song7749.dbclient.value.ProcedureVo;
import com.song7749.dbclient.value.SequenceVo;
import com.song7749.dbclient.value.TableVo;
import com.song7749.dbclient.value.TriggerVo;
import com.song7749.dbclient.value.ViewVo;
import com.song7749.log.service.LogManager;
import com.song7749.log.value.LogQueryAddDto;
import com.song7749.member.service.LoginSession;
import com.zaxxer.hikari.HikariDataSource;


/**
 * <pre>
 * Class Name : DBclientManagerImpl.java
 * Description : DB 클라이언트 매니저 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 22.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 22.
*/
@Service("dbClientManager")
public class DBclientManagerImpl implements DBclientManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ModelMapper mapper;

	@Autowired
	DatabaseRepository databaseRepository;

	@Autowired
	DatabasePrivacyPolicyRepository dppRepository;

	@Autowired
	LogManager logManager;

	@Autowired
	LoginSession session;

	// ConcurrentHashMap 중복 생성을 방지하고자 함.
	private final Map<Database, DataSource> dataSourceMap = new ConcurrentHashMap<Database, DataSource>();

	public Map<Database, DataSource> getDataSourceMap(){
		return dataSourceMap;
	}

	@Override
	public Connection getConnection(Database database) throws SQLException {
		// connection 이 중복 생성 되는 것을 방지
		if (dataSourceMap.containsKey(database)) {
			logger.debug(format("Return Saved Connection! " + database.getHostAlias()));
		} else {
			logger.debug(format("Return New Connection! " + database.getHostAlias()));

			// data source 생성
			HikariDataSource hDataSource = new HikariDataSource();
			// reference 를 미리 넣는다 (중복 생성 방지)
			dataSourceMap.put(database, hDataSource);

			/**
			 * https://github.com/brettwooldridge/HikariCP
			 * 참고하여 설정을 정리 한다.
			 */
			//hDataSource.setDriverClassName(database.getDriver().getDriverName());
			//hDataSource.setConnectionTestQuery(database.getDriver().getValidateQuery());
			hDataSource.setJdbcUrl(database.getDriver().getUrl(database));
			hDataSource.setUsername(database.getAccount());
			hDataSource.setPassword(database.getPassword());
			hDataSource.setAutoCommit(false);
			hDataSource.setMaximumPoolSize(20);
			hDataSource.setMinimumIdle(10);
			hDataSource.setIdleTimeout(60000);
			hDataSource.setMaxLifetime(90000);
			hDataSource.setLoginTimeout(3);
			hDataSource.setConnectionTimeout(3000);
			hDataSource.setValidationTimeout(2000);
			hDataSource.setAllowPoolSuspension(false);
			hDataSource.addDataSourceProperty("cachePrepStmts", "true");
			hDataSource.addDataSourceProperty("prepStmtCacheSize", "250");
			hDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			hDataSource.setPoolName(database.getHost() + "[" + database.getHostAlias() + "]");

			// 오라클의 경우 client, terminal 이름을 변경 한다.
			if(database.getDriver().equals(DatabaseDriver.ORACLE)){
				hDataSource.addDataSourceProperty("v$session.program","dbClient");
				// schema alias 처리
				if(StringUtils.isNotBlank(database.getSchemaOwner())){
					hDataSource.setConnectionInitSql("ALTER SESSION SET CURRENT_SCHEMA="+database.getSchemaOwner());
				}

				try {
					InetAddress localhost = java.net.InetAddress.getLocalHost();
					hDataSource.addDataSourceProperty("v$session.terminal",localhost.getHostName());
				} catch (UnknownHostException e) {
					logger.info(format("{}","oracle terminal name fail"),e.getMessage());
				}
			}
		}
		try {
			return dataSourceMap.get(database).getConnection();
		} catch (SQLException e) {
			closeConnection(database);
			dataSourceMap.remove(database);
			throw new SQLException(e);
		}
	}

	@Override
	public boolean closeConnection(Database database) throws SQLException {
		if(dataSourceMap.containsKey(database)){
			HikariDataSource hDataSource = (HikariDataSource) dataSourceMap.get(database);
			// TODO 닫기 전에 사용 중인가 확인이 필요 하다.
			hDataSource.close();
		}
		return true;
	}

	@Override
	public MessageVo testConnection(DatabaseAddDto dto)  throws SQLException {
		logger.trace(format("{}", "Test Connection Start"),dto);

		Database database = mapper.map(dto, Database.class);
		// data source 생성
		HikariDataSource hDataSource = new HikariDataSource();
		hDataSource.setJdbcUrl(database.getDriver().getUrl(database));
		hDataSource.setUsername(database.getAccount());
		hDataSource.setPassword(database.getPassword());
		hDataSource.setAutoCommit(false);
		hDataSource.setMaximumPoolSize(1);
		hDataSource.setMinimumIdle(1);
		hDataSource.setIdleTimeout(60000);
		hDataSource.setMaxLifetime(90000);
		hDataSource.setLoginTimeout(3);
		hDataSource.setConnectionTimeout(3000);
		hDataSource.setValidationTimeout(2000);
		hDataSource.getConnection();
		hDataSource.getConnectionTestQuery();
		hDataSource.close();

		return new MessageVo(HttpStatus.OK.value(), "Database Connect Test Complete");
	}


	@Cacheable(value="com.song7749.database.cache",key="'selectTableVoList' + #dto.id")
	@Override
	public List<TableVo> selectTableVoList(ExecuteQueryDto dto) {
		// database 조회
		Database database = getDatabase(dto.getId());

		// 테이블 리스트 조회 쿼리 생성
		dto.setQuery(database.getDriver().getTableListQuery(database));

		List<TableVo> list = new ArrayList<TableVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(new TableVo(
				++seq,
				map.get("TABLE_NAME"),
				map.get("TABLE_COMMENT")));
		}
		return list;
	}

	@Override
	public List<FieldVo> selectTableFieldVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getFieldListQueryQuery(database));

		List<FieldVo> list = new ArrayList<FieldVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new FieldVo(
				database.getName(),
				map.get("COLUMN_ID"),
				map.get("COLUMN_NAME"),
				map.get("NULLABLE"),
				map.get("COLUMN_KEY"),
				map.get("DATA_TYPE"),
				map.get("DATA_LENGTH"),
				map.get("CHARACTER_SET"),
				map.get("EXTRA"),
				map.get("DEFAULT_VALUE"),
				map.get("COMMENTS")));
		}
		return list;
	}

	@Override
	public List<IndexVo> selectTableIndexVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getIndexListQuery(database));

		List<IndexVo> list = new ArrayList<IndexVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new IndexVo(
				map.get("OWNER") ,
				map.get("INDEX_NAME") ,
				map.get("INDEX_TYPE"),
				map.get("COLUMN_NAME"),
				map.get("COLUMN_POSITION"),
				map.get("CARDINALITY"),
				map.get("UNIQUENESS"),
				map.get("DESCEND")));
		}
		return list;
	}

	@Cacheable(value="com.song7749.database.cache",key="'selectViewVoList' + #dto.id")
	@Override
	public List<ViewVo> selectViewVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getViewListQuery(database));

		List<ViewVo> list = new ArrayList<ViewVo>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new ViewVo(
					++seq,
					map.get("NAME"),
					map.get("COMMENTS"),
					map.get("LAST_UPDATE_TIME"),
					map.get("STATUS"))
			);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectViewDetailList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getViewDetailQuery(database));

		try {
			return executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<ViewVo> selectViewVoSourceList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getViewSourceQuery(database));

		List<ViewVo> list = new ArrayList<ViewVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){

			String addSoruce = null;
			String text = null;
			if("oracle".equals(database.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE VIEW "+map.get("NAME") + " AS ";
				text=map.get("TEXT");
			} else if("mysql".equals(database.getDriver().getDbms())){
				addSoruce="";
				text=map.get("Create View");
			} else if("h2".equals(database.getDriver().getDbms())) {
				addSoruce="";
				text=map.get("VIEW_DEFINITION");
			}

			ViewVo vv = new ViewVo(text,addSoruce);
			logger.trace(format("{}", "Log Message"),vv);
			list.add(vv);
		}
		return list;
	}

	@Cacheable(value="com.song7749.database.cache",key="'selectProcedureVoList' + #dto.id")
	@Override
	public List<ProcedureVo> selectProcedureVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getProcedureListQuery(database));

		List<ProcedureVo> list = new ArrayList<ProcedureVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database),dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new ProcedureVo(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectProcedureDetailList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getProcedureDetailQuery(database));

		try {
			return executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<ProcedureVo> selectProcedureVoSourceList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getProcedureSourceQuery(database));


		List<ProcedureVo> list = new ArrayList<ProcedureVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			String text = null;
			String addSoruce = null;

			if("oracle".equals(database.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE ";//Create Procedure
				text = map.get("TEXT");
			} else if("mysql".equals(database.getDriver().getDbms())){
				addSoruce="";
				text = map.get("Create Procedure");
			} else if("h2".equals(database.getDriver().getDbms())) {
				addSoruce="CREATE ALIAS " + map.get("NAME") + " AS $$";
				text=map.get("TEXT") + " $$;";
			}
			ProcedureVo pv = new ProcedureVo(text,addSoruce);
			list.add(pv);
		}
		return list;
	}

	@Cacheable(value="com.song7749.database.cache",key="'selectFunctionVoList' + #dto.id")
	@Override
	public List<FunctionVo> selectFunctionVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getFunctionListQuery(database));

		List<FunctionVo> list = new ArrayList<FunctionVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new FunctionVo(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectFunctionDetailList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getFunctionDetailQuery(database));

		try {
			return executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<FunctionVo> selectFunctionVoSourceList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getFunctionSourceQuery(database));

		List<FunctionVo> list = new ArrayList<FunctionVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			String addSoruce = null;
			String text = null;
			if("oracle".equals(database.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE ";
				text=map.get("TEXT");
			} else if("mysql".equals(database.getDriver().getDbms())){
				addSoruce="";
				text=map.get("Create Function");
			} else if("h2".equals(database.getDriver().getDbms())) {
				addSoruce="CREATE ALIAS " + map.get("NAME") + " AS $$";
				text=map.get("TEXT") + " $$;";
			}

			FunctionVo fv = new FunctionVo(text,addSoruce);
			list.add(fv);
		}
		return list;
	}

	@Cacheable(value="com.song7749.database.cache",key="'selectTriggerVoList' + #dto.id")
	@Override
	public List<TriggerVo> selectTriggerVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getTriggerListQuery(database));


		List<TriggerVo> list = new ArrayList<TriggerVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new TriggerVo(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;

	}

	@Override
	public List<Map<String, String>> selectTriggerDetailList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getTriggerDetailQuery(database));

		try {
			return executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<TriggerVo> selectTriggerVoSourceList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getTriggerSourceQuery(database));

		List<TriggerVo> list = new ArrayList<TriggerVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			String addSoruce = null;
			String text=null;
			if("oracle".equals(database.getDriver().getDbms())){
				text=map.get("TEXT");
				addSoruce = "CREATE OR REPLACE TRIGGER " +map.get("DESCRIPTION");
				if(!com.song7749.util.StringUtils.isEmpty(map.get("WHEN_CLAUSE"))){
					addSoruce +="WHEN (" + map.get("WHEN_CLAUSE") + ") \n";
				} else {
					addSoruce +="\n";
				}
			} else if("mysql".equals(database.getDriver().getDbms())){
				text=map.get("SQL Original Statement");
				addSoruce="";
			}  else if("h2".equals(database.getDriver().getDbms())) {
				addSoruce="";
				text=map.get("TEXT");
			}

			TriggerVo tv = new TriggerVo(text,addSoruce);
			list.add(tv);
		}
		return list;
	}


	@Cacheable(value="com.song7749.database.cache",key="'selectSequenceVoList' + #dto.id")
	@Override
	public List<SequenceVo> selectSequenceVoList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getSequenceListQuery(database));

		List<SequenceVo> list = new ArrayList<SequenceVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(new SequenceVo(
					++seq,
					map.get("NAME"),
					map.get("LAST_VALUE"),
					map.get("MIN_VALUE"),
					map.get("MAX_VALUE"),
					map.get("INCREMENT_BY")));
		}
		return list;
	}

	@Override
	public List<Map<String, String>> selectSequenceDetailList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getSequenceListDetailQuery(database));

		try {
			return  executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<DatabaseDdlVo> selectShowCreateTable(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getShowCreateQuery(database));

		List<DatabaseDdlVo> list = new ArrayList<DatabaseDdlVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){

			if("oracle".equals(database.getDriver().getDbms())
					|| "h2".equals(database.getDriver().getDbms())){
				list.add(new DatabaseDdlVo(map.get("CREATE_TALBE")));

			} else if("mysql".equals(database.getDriver().getDbms())){
				list.add(new DatabaseDdlVo(map.get("Create Table")));
			}
		}

		return list;
	}

	@Cacheable(value="com.song7749.database.cache",key="'selectAllFieldList' + #dto.id")
	@Override
	public List<FieldVo> selectAllFieldList(ExecuteQueryDto dto) {
		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		dto.setQuery(database.getDriver().getAutoCompleteQuery(database));

		List<FieldVo> list = new ArrayList<FieldVo>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new FieldVo(map.get("TABLE_NAME"),map.get("COLUMN_NAME"),map.get("COLUMN_COMMENT")));
		}
		return list;
	}

	@Override
	public MessageVo executeQuery(ExecuteQueryDto dto) {
		Long startTime = System.currentTimeMillis();
		Database database = getDatabase(dto.getId());
		MessageVo vo = new MessageVo();
		vo.setHttpStatus(HttpStatus.OK.value());

		// row 에 update 가 일어나는 구문과 그 외로 분기한다.
		boolean isAffected=database.getDriver().isAffectedRowCommand(dto.getQuery());
		// DML 이나 PLSQL 인 경우에는 AffectedRow 가 발생한다.
		isAffected = isAffected || dto.isUsePLSQL();


		// 개인정보에 대한 처리 -- 개인 정보가 포함되면 실행을 중단 시킨다.
		validatePrivacyPolicy(database,dto.getQuery());

		// row 에 영향이 있는 쿼리
		if(isAffected){
			// 대량의 입력이 필요한 경우 여러번 실행 되도록 변경 한다. -- PLSQL 이 아니고, ; 가 여러개 들어간 경우에만 해당한다.
			if(!dto.isUsePLSQL() && StringUtils.countMatches(dto.getQuery(), ";") > 1) {
				int affectedRows = 0;
				String queries[] = dto.getQuery().split(";");
				for(String sql : queries) {
					if(StringUtils.isNotEmpty(sql)) {
						dto.setQuery(sql);
						try{
							affectedRows = executeWriteQuery(getConnection(database),dto);
						} catch (SQLException e) {
							throw new IllegalArgumentException(e.getMessage() + sql);
						}
					}
				}
				vo.setRowCount(affectedRows);
				vo.setMessage("실행이 완료되었습니다");
			} else {
				try{
					vo.setRowCount(executeWriteQuery(getConnection(database),dto));
					vo.setMessage("실행이 완료되었습니다");
				} catch (SQLException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			}
		} else { // 그 외 조회성 쿼리
			List<Map<String, String>> list = null;
			try{
				// 한정자 추가
				if(dto.isUseLimit()) {
					dto.setQuery(database.getDriver().getAddRangeOperator(dto.getQuery(), dto.getOffset(), dto.getLimit()));
				}

				list=executeReadQuery(getConnection(database), dto);
				if(null==list || list.size() == 0) {
					vo.setHttpStatus(HttpStatus.NO_CONTENT.value());
					vo.setRowCount(0);
					vo.setMessage("데이터가 없습니다.");
				} else {
					vo.setRowCount(list.size());
					vo.setContents(list);
				}
			} catch (SQLException e) {
				logger.debug(format("{}","SQL ERROR"),"[" + e.getErrorCode() + "][" + e.getSQLState() + "]" + e.getMessage());
				throw new IllegalArgumentException("[" + e.getErrorCode() + "][" + e.getSQLState() + "]" + e.getMessage());
			}
		}

		// 로그 기록 -- 비동기로 기록됨
		LogQueryAddDto logDto = new LogQueryAddDto(
				dto.getIp(),
				dto.getLoginId(),
				dto.getId(),
				database.getHost(),
				database.getHostAlias(),
				database.getSchemaName(),
				database.getAccount(),
				dto.getQuery());
		logManager.addQueryExecuteLog(logDto);

		vo.setProcessTime(System.currentTimeMillis() - startTime);
		return vo;
	}

	@Override
	public void killQuery(ExecuteQueryDto dto) {
		// 중지 대상 쿼리 저장
		String stopQuery = dto.getQuery();

		Database database = getDatabase(dto.getId());
		database.setName(dto.getName());
		// 프로세스 조회 쿼리로 변경
		dto.setQuery(database.getDriver().getProcessListQuery());

		// 프로세스 리스트를 조회 한다.
		List<Map<String, String>> list = null;
		try {
			list = executeReadQuery(getConnection(database), dto);
		} catch (SQLException e) {
			logger.info(format("{}","prorcessList 조회 실패"),e.getMessage());
		}

		// 프로세스 리스트를 검색해서 맞는 조건일 경우 해당 쿼리를 kill 한다.
		if(null!=list && list.size()>0){
			for(Map<String,String> porcess : list){
				if(null!=porcess.get("SQL_TEXT")) {
					// 실행중인 쿼리
					String runQuery = porcess.get("SQL_TEXT").replace("\n", "").replace("\t", "").replace(" ", "");
					// 중지 대상 쿼리
					stopQuery = stopQuery.replace("\n", "").replace("\t", "").replace(" ", "");
					// rownum 을 추가한 쿼리로 한번 더 검사한다.
					String stopQueryWithLimit = database.getDriver().getAddRangeOperator(stopQuery, dto.getOffset(), dto.getLimit()).replace("\n", "").replace("\t", "").replace(" ", "");

					logger.debug(
							format(
									"{}\n{}",
									"SQL 중지 쿼리 비교"),
									runQuery,
									stopQuery);

					// 방금 사용한 쿼리가 맞을 경우
					if(stopQuery.indexOf(runQuery) >= 0 || stopQueryWithLimit.indexOf(runQuery) >= 0){
						// 쿼리 실행 중단
						logger.debug(format("{}","SQL쿼리 중단 실행"),runQuery);
						try {
							dto.setQuery(database.getDriver().getProcessKillQuery(porcess.get("ID")));
							executeWriteQuery(getConnection(database),dto);
						} catch (SQLException e) {
							logger.debug(format("{}","prorcess kill 실패"),e.getMessage());
							throw new IllegalArgumentException(e.getMessage());
						}
						break; // 해당하는 쿼리를 찾은 경우에는 중단 시킨다.
					}
				}
			}
		}
	}

	/**
	 * 커넥션과 쿼리를 넘겨받고 실행 결과를 리턴한다.
	 * 조회에서 사용한다.
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	private List<Map<String,String>> executeReadQuery(Connection conn, ExecuteQueryDto dto) throws SQLException{
		logger.debug(format("{}","Try Read Excute Query"),dto.getQuery());
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		try {
			ps = conn.prepareStatement(dto.getQuery());
			rs = ps.executeQuery();
			rs.setFetchSize(100);

			while (rs.next()) {
				Map<String, String> map=new LinkedHashMap<String, String>();
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
					// 내용물에 html 허용
					if(dto.isHtmlAllow()){
						map.put(rs.getMetaData().getColumnLabel(i), rs.getString(rs.getMetaData().getColumnLabel(i)));
					} else {
						// 허용 안함
						map.put(rs.getMetaData().getColumnLabel(i), htmlentities(rs.getString(rs.getMetaData().getColumnLabel(i))));
					}
				}
				list.add(map);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				close(conn);
				close(ps);
				close(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				setNull(conn);
				setNull(ps);
				setNull(rs);
			}
		}
		return list;
	}

	/**
	 * Query를 실행하고 Affected row 를 반환 한다.
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	private int executeWriteQuery(Connection conn, ExecuteQueryDto dto) throws SQLException{
		logger.debug(format("{} ","Try Write Excute Query"),dto.getQuery());

		PreparedStatement ps = null;
		int affectedRows=0;

		try {
			conn.setAutoCommit(dto.isAutoCommit());
			ps = conn.prepareStatement(dto.getQuery());
			affectedRows = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				close(conn);
				close(ps);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				setNull(conn);
				setNull(ps);
			}
		}
		return affectedRows;
	}


	/**
	 * Callable Query 실행
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private List<Map<String,String>> executeCallableQuery(Connection conn, ExecuteQueryDto dto) throws SQLException{
		logger.debug(format("{} ","Try Callable Excute Query"),dto.getQuery());

		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(dto.isAutoCommit());
			cs = conn.prepareCall(dto.getQuery());
			cs.executeUpdate();
			cs.getMetaData();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				close(conn);
				close(cs);
				close(rs);
			} catch (SQLException e) {
				throw e;
			} finally {
				setNull(conn);
				setNull(cs);
				setNull(rs);
			}
		}
		return null;
	}

	private boolean validatePrivacyPolicy(Database database,String sql) {
		if(StringUtils.isBlank(sql)) {
			throw new IllegalArgumentException("입력된 SQL 이 없습니다.");
		}

		// 개행 문자 가공
		sql = sql.replace("\r", " ").replace("\n", " ").replace("\t", " ");
		// 개인정보 테이블을 조회 한다 -- 개인정보 필드는 많지 않다. 전체 조회 한다.
		List<DatabasePrivacyPolicy> dppList = dppRepository.findAll();
		if(dppList.isEmpty()) { // 개인정보 정의가 없으면 skip
			return true;
		}
		for(DatabasePrivacyPolicy dpp : dppList) {
			boolean isMatchTable = isSubStringEqualMatch(
					sql.toLowerCase(),
					dpp.getTableName().toLowerCase(),
					0,
					Arrays.asList(new String[] {" ",",",".","("}),
					Arrays.asList(new String[] {" ",",",")"}));

			// 테이블이 명확히 일치 하는 경우에만 검증
			if(isMatchTable) {
				// select * 가 있을 경우 fail
				if(sql.indexOf("*") >=0) {
					throw new IllegalArgumentException("개인 정보 테이블 "+dpp.getTableName()+" 에 '*' 가 포함되어 있습니다. 실행이 중단됩니다.");
				}

				// 매핑 결과
				boolean isMatchFieldName=false;
				// 필드 명칭 검색
				int fieldOffset = getSubStringEqualMatchIndex(
						sql.toLowerCase(),
						dpp.getFieldName().toLowerCase(),
						0,
						Arrays.asList(new String[] {" ",",",".","("}),
						Arrays.asList(new String[] {" ",",",")"}));

				// 해당 필드가 select ~ from 사이에 존재 하는지 검증
				if(fieldOffset >= 0){
					// select 의 위치를 모두 검색 한다.
					int startSelectIndex = 0;
					List<Integer> selectIndexList = new ArrayList<Integer>();
					while(true) {
						startSelectIndex = getSubStringEqualMatchIndex(
								sql.toLowerCase(),
								"select",
								startSelectIndex,
								Arrays.asList(new String[] {" ",",","("}),
								Arrays.asList(new String[] {" ","("}));
						if(startSelectIndex>=0) {
							selectIndexList.add(startSelectIndex);
						} else {
							break;
						}
						startSelectIndex += "select".length();

					}
					logger.trace(format("{}", "selectIndexList Message"),selectIndexList);

					// from 의 위치를 모두 검색 한다.
					int startFromIndex = 0;
					List<Integer> fromIndexList = new ArrayList<Integer>();
					while(true) {
						startFromIndex = getSubStringEqualMatchIndex(
								sql.toLowerCase(),
								"from",
								startFromIndex,
								Arrays.asList(new String[] {" ",")"}),
								Arrays.asList(new String[] {" ","("}));
						if(startFromIndex>=0) {
							fromIndexList.add(startFromIndex);
						} else {
							break;
						}
						startFromIndex+="from".length();
					}
					logger.trace(format("{}", "fromIndexList Message"),fromIndexList);

					if(selectIndexList.size()!=fromIndexList.size()) {
						throw new IllegalArgumentException("개인정보 검색 시에는 select 명령과 from 명령의 개수가 일치해야 합니다.");
					}

					// select 와 from 사이가 가장 짧은 구간부터 지워 간다.
					int start = 0;
					int end = 0;
					// from 을 먼저 검색한다.
					for(int fromIndex : fromIndexList) {
						// select 를 검색하는데, from 에 제일 가까운 from 과의 사이 값을 구한다.
						for(int selectIndex : selectIndexList) {
							// offset 이 - 로 가면, 엇갈림으로, + 상태를 유지 한다.
							if(fromIndex-selectIndex > 0) {
								start=selectIndex;
								end=fromIndex;
							}
						}
						// 첫 from 부터 select 를 이동하며 그 사이에 필드가 존재 하는지 확인 한다.
						// select ~ from 사이에 있는 경우에만 검증 한다.
						if(start <= fieldOffset && end >= fieldOffset) {
							isMatchFieldName=true;
							break;
						}
						// 한번 검증이 된 구간은  제외 시켜야 한다.
						selectIndexList.remove(new Integer(start));
					}
				}

				// 필드명이 있을 경우 fail
				if(isMatchFieldName){
					throw new IllegalArgumentException("개인 정보 테이블 "+dpp.getTableName()+" 에 개인정보 컬럼 " + dpp.getFieldName() + " 가 포함되어 있습니다. 개인정보 필드는 Select ~ From 사이에는 허용되지 않습니다. 실행이 중단됩니다.");
				}
			}
		}
		return true;
	}

	private void close(Connection conn) throws SQLException {
		if (null != conn) conn.close();
	}
	private void close(PreparedStatement ps) throws SQLException {
		if (null != ps) ps.close();
	}
	private void close(CallableStatement cs) throws SQLException {
		if (null != cs) cs.close();
	}
	private void close(ResultSet rs) throws SQLException {
		if (null != rs) rs.close();
	}
	private void setNull(Connection conn) {
		conn=null;
	}
	private void setNull(PreparedStatement ps) {
		ps=null;
	}
	private void setNull(CallableStatement cs) {
		cs=null;
	}
	private void setNull(ResultSet rs) {
		rs=null;
	}

	@Override
	public Database getDatabase(Long databaseId) {
		Optional<Database> oDatabase = databaseRepository.findById(databaseId);
		if(!oDatabase.isPresent()) {
			throw new IllegalArgumentException("Database Id 에 해당하는 Database 가 없습니다.");
		}

		return oDatabase.get();
	}
}