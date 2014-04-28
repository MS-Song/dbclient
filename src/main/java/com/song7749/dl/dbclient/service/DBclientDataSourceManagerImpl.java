package com.song7749.dl.dbclient.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.stereotype.Service;

import com.song7749.dl.dbclient.entities.ServerInfo;

@Service("dbClientDataSourceManager")
public class DBclientDataSourceManagerImpl implements DBclientDataSourceManager{

	private final Map<ServerInfo,DataSource> dataSourceMap = new HashMap<ServerInfo, DataSource>();

	@Override
	public DataSource getDataSource(ServerInfo serverInfo) {
		if(dataSourceMap.containsKey(serverInfo)){
			return dataSourceMap.get(serverInfo);
		}

		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(serverInfo.getDriver().getDriverName());
		try {
			bds.setUrl(makeUrl(serverInfo));
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

	/**
	 * 서버 정보를 이용해서 DB CONNECT URL 을 치환한다.
	 * @param serverInfo
	 * @return String DB 커넥션 URL
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private String makeUrl(ServerInfo serverInfo) throws IllegalArgumentException, IllegalAccessException{
		// 커넥션 정보 조합
		String url = serverInfo.getDriver().getUrl()+serverInfo.getDriver().getParameter();
		// 필드 매치
		for(Field f:serverInfo.getClass().getDeclaredFields()){
			// 필드를 읽기 가능한 상태로 변경
			f.setAccessible(true);
			// 패턴 컴파일
			Pattern p = Pattern.compile(f.getName());
			 Matcher m = p.matcher(url);
			 url=m.replaceAll(f.get(serverInfo).toString()).replace("{", "").replace("}", "");
		}
		return url;
	}
}