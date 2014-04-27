package com.song7749.dl.dbclient.service;

import javax.sql.DataSource;

import com.song7749.dl.dbclient.entities.ServerInfo;

public interface DBclientDataSourceManager {

	DataSource getDataSource(ServerInfo serverInfo);
}