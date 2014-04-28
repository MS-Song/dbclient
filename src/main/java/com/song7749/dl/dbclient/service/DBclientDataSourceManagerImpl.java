package com.song7749.dl.dbclient.service;

import java.lang.reflect.Field;
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
import com.song7749.util.StringUtils;

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
			bds.setUrl(getUrl(serverInfo));
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
			ps = conn.prepareStatement(getTableListQuery(serverInfo));
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
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo,
			String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo,
			String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 서버 정보를 이용해서 DB CONNECT URL 을 치환한다.
	 *
	 * @param serverInfo
	 * @return String DB 커넥션 URL
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String getUrl(ServerInfo serverInfo)
			throws IllegalArgumentException, IllegalAccessException {
		// 커넥션 정보 조합
		String url = serverInfo.getDriver().getUrl()
				+ serverInfo.getDriver().getParameter();
		// 필드 매치
		for (Field f : serverInfo.getClass().getDeclaredFields()) {
			// 필드를 읽기 가능한 상태로 변경
			f.setAccessible(true);
			// 커넥션 변경
			url = StringUtils.replace(f.getName(),
					f.get(serverInfo).toString(), url);
		}
		return url.replace("{", "").replace("}", "");
	}

	private String getTableListQuery(ServerInfo serverInfo){
		return StringUtils
			.replace("schemaName", serverInfo.getSchemaName(),serverInfo.getDriver().getTableListQuery())
			.replace("{", "").replace("}", "");
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
}