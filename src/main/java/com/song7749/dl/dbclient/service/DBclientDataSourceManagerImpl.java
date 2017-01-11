package com.song7749.dl.dbclient.service;

import static com.song7749.util.LogMessageFormatter.format;
import static com.song7749.util.StringUtils.htmlentities;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.song7749.dl.dbclient.vo.DatabaseDdlVO;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.FunctionVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ProcedureVO;
import com.song7749.dl.dbclient.vo.SequenceVO;
import com.song7749.dl.dbclient.vo.TableVO;
import com.song7749.dl.dbclient.vo.TriggerVO;
import com.song7749.dl.dbclient.vo.ViewVO;
import com.song7749.log.dto.SaveQueryExecuteLogDTO;
import com.song7749.log.service.LogManager;
/**
 * <pre>
 * Class Name : DBclientDataSourceManagerImpl.java
 * Description : DBclientDataSourceManager implements class
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 27.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 27.
*/
@Service("dbClientDataSourceManager")
public class DBclientDataSourceManagerImpl implements DBclientDataSourceManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	// ConcurrentHashMap 중복 생성을 방지하고자 함.
	private final Map<ServerInfo, DataSource> dataSourceMap = new ConcurrentHashMap<ServerInfo, DataSource>();


	@Autowired
	ApplicationContext context;

	@Autowired
	private LogManager logManager;

	@Override
	public Connection getConnection(ServerInfo serverInfo) throws SQLException {

		logger.debug(format("{}","database server info"),serverInfo);

		/**
		 * serverInfo 안에 포함된 테이블 정보를 삭제하기 위해 객체를 새로 만든다.
		 */
		ServerInfo keyServerInfo = new ServerInfo();
		keyServerInfo.setServerInfoSeq(serverInfo.getServerInfoSeq());
		keyServerInfo.setHost(serverInfo.getHost());
		keyServerInfo.setSchemaName(serverInfo.getSchemaName());
		keyServerInfo.setPort(serverInfo.getPort());
		keyServerInfo.setDriver(serverInfo.getDriver());
		keyServerInfo.setAccount(serverInfo.getAccount());
		keyServerInfo.setCharset(serverInfo.getCharset());

		// connection 이 중복 생성 되는 것을 방지
		if (dataSourceMap.containsKey(keyServerInfo)) {
			logger.debug(format("Return Saved Connection! " + serverInfo.getHostAlias()));
		} else {
			logger.debug(format("Return New Connection! " + serverInfo.getHostAlias()));

			BasicDataSource bds = new BasicDataSource();
			// reference 를 미리 넣는다 (중복 생성 방지)
			dataSourceMap.put(keyServerInfo, bds);

			bds.setDriverClassName(serverInfo.getDriver().getDriverName());
			try {
				logger.debug(format("{}","database connect URL"),serverInfo.getDriver().getUrl(serverInfo));
				bds.setUrl(serverInfo.getDriver().getUrl(serverInfo));
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}
			bds.setUsername(serverInfo.getAccount());
			bds.setPassword(serverInfo.getPassword());
			bds.setValidationQuery("SELECT 1 FROM DUAL");
			bds.setValidationQueryTimeout(60);
			bds.setDefaultAutoCommit(false);
			bds.setMaxActive(20);
			bds.setInitialSize(10);
			bds.setMinIdle(10);
			bds.setMaxIdle(20);
			bds.setMaxWait(5000);
			bds.setTestOnReturn(true);
			bds.setTestOnReturn(false);
			bds.setTestWhileIdle(true);
			bds.setNumTestsPerEvictionRun(5);
			bds.setMinEvictableIdleTimeMillis(10000);
			bds.setTimeBetweenEvictionRunsMillis(50000);
			bds.setRemoveAbandoned(true);
			bds.setRemoveAbandonedTimeout(60);
			bds.setLogAbandoned(true);
			bds.setPoolPreparedStatements(true);

			// 오라클의 경우 client, terminal 이름을 변경 한다.
			if(serverInfo.getDriver().equals(DatabaseDriver.oracle)){
				bds.addConnectionProperty("v$session.program","dbClient");
				try {
					InetAddress localhost = java.net.InetAddress.getLocalHost();
					bds.addConnectionProperty("v$session.terminal",localhost.getHostName());
				} catch (UnknownHostException e) {
					logger.info(format("{}","oracle terminal name fail"),e.getMessage());
				}
			}
		}
		try {
			return dataSourceMap.get(keyServerInfo).getConnection();
		} catch (SQLException e) {
			dataSourceMap.remove(keyServerInfo);
			throw new SQLException(e);
		}
	}


	@Override
	public boolean closeConnection(ServerInfo serverInfo) throws SQLException {
		if(dataSourceMap.containsKey(serverInfo)){
			BasicDataSource bds = (BasicDataSource) dataSourceMap.get(serverInfo);
			bds.close();
		}
		return true;
	}

	@Override
	public List<TableVO> selectTableVOList(ServerInfo serverInfo) {

		List<TableVO> list = new ArrayList<TableVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getTableListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(new TableVO(
				++seq,
				map.get("TABLE_NAME"),
				map.get("TABLE_COMMENT")));
		}
		return list;
	}

	@Override
	public List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo) {

		List<FieldVO> list = new ArrayList<FieldVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getFieldListQueryQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new FieldVO(
				serverInfo.getName(),
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
	public List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo) {

		List<IndexVO> list = new ArrayList<IndexVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getIndexListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new IndexVO(
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

	/**
	 * 커넥션과 쿼리를 넘겨받고 실행 결과를 리턴한다.
	 * 조회에서 사용한다.
	 * @param conn
	 * @param executeQuery
	 * @return List<Map<String,String>>
	 */
	private List<Map<String,String>> executeQueryList(Connection conn,String executeQuery) throws SQLException{
		return executeQueryList(conn,executeQuery,true);
	}

	/**
	 * 커넥션과 쿼리를 넘겨받고 실행 결과를 리턴한다.
	 * 조회에서 사용한다.
	 * @param conn
	 * @param executeQuery
	 * @param isHtmlAllow
	 * @return List<Map<String,String>>
	 * @throws SQLException
	 */
	private List<Map<String,String>> executeQueryList(Connection conn,String executeQuery, boolean isHtmlAllow) throws SQLException{

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		logger.debug(format("excute Query : {} ","databaseInfo"),executeQuery);
		try {
			ps = conn.prepareStatement(executeQuery);
			rs = ps.executeQuery();
			rs.setFetchSize(100);
			logger.debug(format("{}","fetch size"),rs.getFetchSize());
			while (rs.next()) {
				Map<String, String> map=new LinkedHashMap<String, String>();
				for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
					// 내용물에 html 허용
					if(isHtmlAllow){
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
				closeAll(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				nullAll(conn, ps, rs);
			}
		}
		return list;
	}

	/**
	 * Query를 실행한다.
	 * 리턴 값이 없고, 실행만 할 경우에 사용한다.
	 * @param conn
	 * @param executeQuery
	 * @param isAutoCommit
	 * @return int affected rows
	 * @throws SQLException
	 */
	private void executeQuery(Connection conn,String executeQuery) throws SQLException{
		logger.debug(format("excute Query : {} ","databaseInfo"),executeQuery);
		try {
			conn.prepareStatement(executeQuery).execute();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				closeAll(conn, null, null);
			} catch (SQLException e) {
				logger.info(format("{}","executeQuery Error"),e.getMessage());
			} finally {
				nullAll(conn, null, null);
			}
		}
	}


	/**
	 * Query를 실행한다.
	 * @param conn
	 * @param executeQuery
	 * @param isAutoCommit
	 * @return int affected rows
	 * @throws SQLException
	 */
	private int executeQuery(Connection conn,String executeQuery,boolean isAutoCommit) throws SQLException{
		PreparedStatement ps = null;
		int affectedRows=0;

		logger.debug(format("excute Query : {} ","databaseInfo"),executeQuery);
		try {
			conn.setAutoCommit(isAutoCommit);
			ps = conn.prepareStatement(executeQuery);
			affectedRows = ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				closeAll(conn, ps, null);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				nullAll(conn, ps, null);
			}
		}
		return affectedRows;
	}

	/**
	 * 연결 모두 닫기
	 *
	 * @param conn
	 * @param ps
	 * @param rs
	 * @throws SQLException
	 */
	private void closeAll(Connection conn, PreparedStatement ps, ResultSet rs)
			throws SQLException {
		if (null != conn) {
			conn.close();
		}
		if (null != ps) {
			ps.close();
		}
		if (null != rs) {
			rs.close();
		}
	}

	/**
	 * null 로 변경하여 GC 가 일어나도록 유도
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	private void nullAll(Connection conn, PreparedStatement ps, ResultSet rs){
		conn=null;
		ps=null;
		rs=null;
	}

	@Override
	public List<Map<String, String>> executeQueryList(ServerInfo serverInfo,ExecuteResultListDTO dto) {
		List<Map<String, String>> list = null;

		// 실행 전에 Thread ID 를 Query 내에 추가 한다. 맨 뒤에 주석으로 추가 한다.
//		long threadId = Thread.currentThread().getId();
//		dto.setQuery(dto.getQuery() + " /* dbclient_thred_id["+ String.valueOf(threadId) +"] */");

		if(dto.getQuery().toLowerCase().startsWith("explain")){
			// 별도의 explain 쿼리가 존재하지 않으면..
			if(null!=serverInfo.getDriver().getExplainQuery()
					&& serverInfo.getDriver().getExplainQuery().equals("")){
				try{
					list = executeQueryList(getConnection(serverInfo), dto.getQuery(),dto.isHtmlAllow());
				} catch (SQLException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			} else {
			// 별도의 explain 쿼리가 존재하면 쿼리를 실행한 뒤에 explain query 를 다시 실행한다.
				try{
					Connection conn = getConnection(serverInfo);
					conn.prepareStatement(dto.getQuery()).execute();
					list = executeQueryList(conn, serverInfo.getDriver().getExplainQuery());
				} catch (SQLException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			}
		} else if(dto.getQuery().toLowerCase().startsWith("select")
				|| dto.getQuery().toLowerCase().startsWith("show")){
			try{
				list=executeQueryList(getConnection(serverInfo), dto.getQuery(),dto.isHtmlAllow());
			} catch (SQLException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		} else {
			try{
				int affectRows = executeQuery(getConnection(serverInfo), dto.getQuery(), dto.isAutoCommit());

				Map<String, String> affectedRowMap = new HashMap<String, String>();
				affectedRowMap.put("affectedRows", new Integer(affectRows).toString());

				list = new ArrayList<Map<String,String>>();
				list.add(affectedRowMap);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}

		// 쿼리 실행 로그 기록
		final SaveQueryExecuteLogDTO executeLogdto = new SaveQueryExecuteLogDTO(
				dto.getId(),
				dto.getIp(),
				dto.getHost(),
				"",
				dto.getSchemaName(),
				dto.getAccount(),
				dto.getQuery(),
				new Date());

		// 서비스 실행기 로딩
		ThreadPoolTaskExecutor serviceExecutor = (ThreadPoolTaskExecutor) context.getBean("QueryExecuteLogExecutor");
		// 서비스 실행기 실행
		serviceExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					logManager.saveQueryExecuteLog(executeLogdto);
				} catch (TaskRejectedException e) {
					throw new TaskRejectedException("로그인 로그 기록 실패. 관리자에게 문의 바랍니다.");
				}
			}
		});
		return list;
	}

	@Override
	public List<ViewVO> selectViewVOList(ServerInfo serverInfo) {
		List<ViewVO> list = new ArrayList<ViewVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getViewListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new ViewVO(
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
	public List<Map<String,String>> selectViewDetailList(ServerInfo serverInfo){
		try {
			return executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getViewDetailQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<ViewVO> selectViewVOSourceList(ServerInfo serverInfo) {

		List<ViewVO> list = new ArrayList<ViewVO>();
		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getViewSourceQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){

			String addSoruce = null;
			if("oracle".equals(serverInfo.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE VIEW "+map.get("NAME");
			} else if("mysql".equals(serverInfo.getDriver().getDbms())){
				addSoruce="DROP VIEW IF EXISTS "+map.get("NAME")+";\nCREATE VIEW "+map.get("NAME") + " ";
			}

			ViewVO vv = new ViewVO(map.get("TEXT"),addSoruce);
			list.add(vv);
		}
		return list;
	}


	@Override
	public List<ProcedureVO> selectProcedureVOList(ServerInfo serverInfo) {
		List<ProcedureVO> list = new ArrayList<ProcedureVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getProcedureListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new ProcedureVO(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;
	}

	@Override
	public List<Map<String,String>> selectProcedureDetailList(ServerInfo serverInfo){
		try {
			logger.debug(format("{}",""),serverInfo.getDriver().getProcedureDetailQuery(serverInfo));
			return executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getProcedureDetailQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<ProcedureVO> selectProcedureVOSourceList(ServerInfo serverInfo) {
		List<ProcedureVO> list = new ArrayList<ProcedureVO>();

		List<Map<String, String>> resultList = null;
		try {
			logger.debug(format("{}",""),serverInfo.getDriver().getProcedureSourceQuery(serverInfo));
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getProcedureSourceQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){

			String addSoruce = null;
			if("oracle".equals(serverInfo.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE ";
			} else if("mysql".equals(serverInfo.getDriver().getDbms())){
				addSoruce="DROP PROCEDURE IF EXISTS "+map.get("NAME")+";\nCREATE PROCEDURE "+map.get("NAME") + " ";
			}

			ProcedureVO pv = new ProcedureVO(map.get("TEXT"),addSoruce);
			list.add(pv);
		}
		return list;
	}

	@Override
	public List<FunctionVO> selectFunctionVOList(ServerInfo serverInfo) {
		List<FunctionVO> list = new ArrayList<FunctionVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getFunctionListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new FunctionVO(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;
	}

	@Override
	public List<Map<String,String>> selectFunctionDetailList(ServerInfo serverInfo){
		try {
			return executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getFunctionDetailQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<FunctionVO> selectFunctionVOSourceList(ServerInfo serverInfo) {
		List<FunctionVO> list = new ArrayList<FunctionVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getFunctionSourceQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){

			String addSoruce = null;
			if("oracle".equals(serverInfo.getDriver().getDbms())){
				addSoruce="CREATE OR REPLACE ";
			} else if("mysql".equals(serverInfo.getDriver().getDbms())){
				addSoruce="DROP FUNCTION IF EXISTS "+map.get("NAME")+"\n;CREATE FUNCTION "+map.get("NAME") + " ";
			}

			FunctionVO fv = new FunctionVO(map.get("TEXT"),addSoruce);
			list.add(fv);
		}
		return list;
	}

	@Override
	public List<TriggerVO> selectTriggerVOList(ServerInfo serverInfo) {
		List<TriggerVO> list = new ArrayList<TriggerVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getTriggerListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(
				new TriggerVO(
					++seq,
					map.get("NAME"),
					map.get("STATUS"),
					map.get("LAST_UPDATE_TIME")));
		}
		return list;

	}

	@Override
	public List<Map<String,String>> selectTriggerDetailList(ServerInfo serverInfo){
		try {
			return executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getTriggerDetailQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<TriggerVO> selectTriggerVOSourceList(ServerInfo serverInfo){
		List<TriggerVO> list = new ArrayList<TriggerVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getTriggerSourceQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			String addSoruce = null;
			if("oracle".equals(serverInfo.getDriver().getDbms())){
				addSoruce = "CREATE OR REPLACE TRIGGER " +map.get("DESCRIPTION");
				if(!com.song7749.util.StringUtils.isEmpty(map.get("WHEN_CLAUSE"))){
					addSoruce +="WHEN (" + map.get("WHEN_CLAUSE") + ") \n";
				} else {
					addSoruce +="\n";
				}
			} else if("mysql".equals(serverInfo.getDriver().getDbms())){
				addSoruce="DROP TRIGGER  IF EXISTS "+map.get("NAME")+"\nCREATE TRIGGER "+map.get("NAME");
				addSoruce+=" " + map.get("ACTION_TIMING");
				addSoruce+=" " + map.get("EVENT_MANIPULATION");
				addSoruce+=" " + map.get("EVENT_OBJECT_TABLE");
				if(!com.song7749.util.StringUtils.isEmpty(map.get("ACTION_ORIENTATION"))){
					addSoruce+="\nFOR EACH " + map.get("ACTION_ORIENTATION");
				}
				addSoruce+="\n";
			}


			TriggerVO tv = new TriggerVO(map.get("TEXT"),addSoruce);
			list.add(tv);
		}
		return list;
	}



	@Override
	public List<SequenceVO> selectSequenceVOList(ServerInfo serverInfo) {
		List<SequenceVO> list = new ArrayList<SequenceVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getSequenceListQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		int seq=0;
		for(Map<String,String> map:resultList){
			list.add(new SequenceVO(
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
	public List<Map<String,String>> selectSequenceDetailList(ServerInfo serverInfo){
		try {
			return executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getSequenceListDetailQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public List<DatabaseDdlVO> selectShowCreateTable(ServerInfo serverInfo){
		List<DatabaseDdlVO> list = new ArrayList<DatabaseDdlVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getShowCreateQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			if("oracle".equals(serverInfo.getDriver().getDbms())){
				list.add(new DatabaseDdlVO(map.get("CREATE_TALBE")));
			} else if("mysql".equals(serverInfo.getDriver().getDbms())){
				list.add(new DatabaseDdlVO(map.get("Create Table")));
			}
		}

		return list;
	}

	@Override
	public List<FieldVO> selectAllFieldList(ServerInfo serverInfo) {
		List<FieldVO> list = new ArrayList<FieldVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getAutoCompleteQuery(serverInfo));
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		for(Map<String,String> map:resultList){
			list.add(new FieldVO(map.get("TABLE_NAME"),map.get("COLUMN_NAME"),map.get("COLUMN_COMMENT")));
		}
		return list;
	}

	@Override
	public void killQuery(ServerInfo serverInfo, ExecuteResultListDTO dto) {
		// 프로세스 리스트를 조회 한다.
		List<Map<String, String>> list = null;
		try {
			list = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getProcessListQuery());
		} catch (SQLException e) {
			logger.info(format("{}","prorcessList 조회 실패"),e.getMessage());
		}

		// 프로세스 리스트를 검색해서 맞는 조건일 경우 해당 쿼리를 kill 한다.
		if(null!=list && list.size()>0){
			for(Map<String,String> porcess : list){
				// 실행중인 쿼리
				String runQuery = porcess.get("SQL_TEXT").replace("\n", "").replace("\t", "").replace(" ", "");
				// 중지 대상 쿼리
				String stopQuery = dto.getQuery().replace("\n", "").replace("\t", "").replace(" ", "");

				logger.debug(
						format(
								"{}\n{}",
								"SQL 중지 쿼리 비교"),
								runQuery,
								stopQuery);

				// 방금 사용한 쿼리가 맞을 경우
				if(runQuery.indexOf(stopQuery) >= 0){
					// 쿼리 실행 중단
					logger.debug(format("{}","SQL쿼리 중단 실행"),runQuery);
					try {
						executeQuery(getConnection(serverInfo),serverInfo.getDriver().getProcessKillQuery(porcess.get("ID")),true);
						// TODO thread 중단

					} catch (SQLException e) {
						logger.debug(format("{}","prorcess kill 실패"),e.getMessage());
					}
					break; // 해당하는 쿼리를 찾은 경우에는 중단 시킨다.
				}
			}
		}
	}

	@Override
	@PreDestroy
	protected void finalize() throws Throwable {
		for(ServerInfo ServerInfo : dataSourceMap.keySet()){
			try {
				((BasicDataSource)dataSourceMap.get(ServerInfo)).close();
			} catch (SQLException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}
		super.finalize();
	}
}