package com.song7749.dl.dbclient.service;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.song7749.dl.dbclient.entities.ServerInfo;

public class DBclientDataSourceManagerImpl implements DBclientDataSourceManager{

	private Map<String,DataSource> dataSourceMap = new HashMap<String, DataSource>();

	@Override
	public DataSource getDataSource(ServerInfo serverInfo) {

		return null;
	}

	private String makeDataSourceName(ServerInfo serverInfo){
		return null;
	}

}
