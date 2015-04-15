package com.song7749.dl.dbclient.service;

import static com.song7749.util.LogMessageFormatter.logFormat;
import static com.song7749.util.StringUtils.htmlentities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

@Service("dbClientDataSourceManager")
public class DBclientDataSourceManagerImpl implements DBclientDataSourceManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<ServerInfo, DataSource> dataSourceMap = new HashMap<ServerInfo, DataSource>();

	@Override
	public Connection getConnection(ServerInfo serverInfo) throws SQLException {
		/**
		 * 테이블 정보를 제거하기 위해 추가함. 추후 변경 고려
		 */
		ServerInfo keyServerInfo = new ServerInfo();
		keyServerInfo.setServerInfoSeq(serverInfo.getServerInfoSeq());
		keyServerInfo.setHost(serverInfo.getHost());
		keyServerInfo.setSchemaName(serverInfo.getSchemaName());
		keyServerInfo.setPort(serverInfo.getPort());
		keyServerInfo.setDriver(serverInfo.getDriver());
		keyServerInfo.setAccount(serverInfo.getAccount());
		keyServerInfo.setCharset(serverInfo.getCharset());

		if (dataSourceMap.containsKey(keyServerInfo)) {
			logger.debug(logFormat("Return Saved Connection!"));
		} else {
			logger.debug(logFormat("Return New Connection!"));

			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(serverInfo.getDriver().getDriverName());
			try {
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
			dataSourceMap.put(keyServerInfo, bds);
		}
		try {
			return dataSourceMap.get(keyServerInfo).getConnection();
		} catch (SQLException e) {
			dataSourceMap.remove(keyServerInfo);
			throw new SQLException(e);
		}
	}

	@Override
	public List<TableVO> selectTableVOList(ServerInfo serverInfo) {

		List<TableVO> list = new ArrayList<TableVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getTableListQuery(serverInfo));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

		for(Map<String,String> map:resultList){
			list.add(new TableVO(
				map.get("TABLE_NAME"),
				map.get("TABLE_COMMENT")));
		}
		return list;
	}

	@Override
	public List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo, String tableName) {
		serverInfo.setTableName(tableName);

		List<FieldVO> list = new ArrayList<FieldVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getFieldListQueryQuery(serverInfo));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
		}

		for(Map<String,String> map:resultList){
			list.add(new FieldVO(
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
	public List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo, String tableName) {
		serverInfo.setTableName(tableName);

		List<IndexVO> list = new ArrayList<IndexVO>();

		List<Map<String, String>> resultList = null;
		try {
			resultList = executeQueryList(getConnection(serverInfo), serverInfo.getDriver().getIndexListQuery(serverInfo));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getCause());
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
	private List<Map<String,String>> executeQueryList(Connection conn,String executeQuery){
		return executeQueryList(conn,executeQuery,true);
	}

	/**
	 * 커넥션과 쿼리를 넘겨받고 실행 결과를 리턴한다.
	 * 조회에서 사용한다.
	 * @param conn
	 * @param executeQuery
	 * @param isHtmlAllow
	 * @return List<Map<String,String>>
	 */
	private List<Map<String,String>> executeQueryList(Connection conn,String executeQuery, boolean isHtmlAllow){

		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		logger.debug(logFormat("excute Query : {} ","databaseInfo"),executeQuery);
		try {
			ps = conn.prepareStatement(executeQuery);
			rs = ps.executeQuery();

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
			e.printStackTrace();
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

	private int executeQuery(Connection conn,String executeQuery,boolean isAutoCommit) throws SQLException{
		PreparedStatement ps = null;
		int affectedRows=0;
		try {
			conn.setAutoCommit(isAutoCommit);
			ps = conn.prepareStatement(executeQuery);
			affectedRows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

		if(dto.getQuery().toLowerCase().startsWith("explain")){
			// 별도의 explain 쿼리가 존재하지 않으면..
			if(serverInfo.getDriver().getExplainQuery().equals("")){
				try{
					list = executeQueryList(getConnection(serverInfo), dto.getQuery(),dto.isHtmlAllow());
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IllegalArgumentException(e.getCause());
				}
			} else {
			// 별도의 explain 쿼리가 존재하면 쿼리를 실행한 뒤에 explain query 를 다시 실행한다.
				try{
					Connection conn = getConnection(serverInfo);
					conn.prepareStatement(dto.getQuery()).execute();
					list = executeQueryList(conn, serverInfo.getDriver().getExplainQuery());
				} catch (SQLException e) {
					e.printStackTrace();
					throw new IllegalArgumentException(e.getCause());
				}
			}
		} else if(dto.getQuery().toLowerCase().startsWith("select")
				|| dto.getQuery().toLowerCase().startsWith("show")){
			try{
				list=executeQueryList(getConnection(serverInfo), dto.getQuery(),dto.isHtmlAllow());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getCause());
			}
		} else {
			try{
				int affectRows = executeQuery(getConnection(serverInfo), dto.getQuery(), dto.isAutoCommit());

				Map<String, String> affectedRowMap = new HashMap<String, String>();
				affectedRowMap.put("affectedRows", new Integer(affectRows).toString());

				list = new ArrayList<Map<String,String>>();
				list.add(affectedRowMap);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IllegalArgumentException(e.getCause());
			}
		}
		return list;
	}

	@Override
	@PreDestroy
	protected void finalize() throws Throwable {
		for(ServerInfo ServerInfo : dataSourceMap.keySet()){
			try {
				((BasicDataSource)dataSourceMap.get(ServerInfo)).close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		super.finalize();
	}
}