package com.song7749.dl.dbclient.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

@Service("dbClientDataSourceManager")
public class DBclientDataSourceManagerImpl implements DBclientDataSourceManager {

	Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<ServerInfo, DataSource> dataSourceMap = new HashMap<ServerInfo, DataSource>();

	@Override
	public DataSource getDataSource(ServerInfo serverInfo) {
		if (dataSourceMap.containsKey(serverInfo)) {
			return dataSourceMap.get(serverInfo);
		}

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
		bds.setMaxActive(3);
		bds.setInitialSize(3);
		bds.setMinIdle(1);
		bds.setMaxIdle(3);
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
		dataSourceMap.put(serverInfo, bds);

		return bds;
	}

	@Override
	public List<TableVO> selectTableVOList(ServerInfo serverInfo) {

		List<TableVO> list = new ArrayList<TableVO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = getDataSource(serverInfo).getConnection();
			ps = conn.prepareStatement(serverInfo.getDriver().getTableListQuery(serverInfo));
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new TableVO(rs.getString("TABLE_NAME"), rs.getString("TABLE_COMMENT")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(conn, ps, rs);
			} catch (SQLException e) {
				nullAll(conn, ps, rs);
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo, String tableName) {
		serverInfo.setTableName(tableName);

		List<FieldVO> list = new ArrayList<FieldVO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = getDataSource(serverInfo).getConnection();
			ps = conn.prepareStatement(serverInfo.getDriver().getFieldListQueryQuery(serverInfo));
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new FieldVO(
						rs.getString("COLUMN_ID"),
						rs.getString("COLUMN_NAME"),
						rs.getString("NULLABLE"),
						rs.getString("COLUMN_KEY"),
						rs.getString("DATA_TYPE"),
						rs.getString("DATA_LENGTH"),
						rs.getString("CHARACTER_SET"),
						rs.getString("EXTRA"),
						rs.getString("DEFAULT_VALUE"),
						rs.getString("COMMENT")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(conn, ps, rs);
			} catch (SQLException e) {
				nullAll(conn, ps, rs);
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo, String tableName) {
		serverInfo.setTableName(tableName);

		List<IndexVO> list = new ArrayList<IndexVO>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = getDataSource(serverInfo).getConnection();
			ps = conn.prepareStatement(serverInfo.getDriver().getIndexListQuery(serverInfo));
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new IndexVO(
					rs.getString("OWNER") ,
					rs.getString("INDEX_NAME") ,
					rs.getString("INDEX_TYPE"),
					rs.getString("COLUMN_NAME"),
					rs.getString("COLUMN_POSITION"),
					rs.getString("CARDINALITY"),
					rs.getString("UNIQUENESS"),
					rs.getString("DESCEND")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(conn, ps, rs);
				nullAll(conn, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
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
}