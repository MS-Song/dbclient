package com.song7749.dl.dbclient.service;

import java.util.List;

import javax.sql.DataSource;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

public interface DBclientDataSourceManager {

	DataSource getDataSource(ServerInfo serverInfo);

	List<TableVO> selectTableVOList(ServerInfo serverInfo);

	List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo, String tableName);

	List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo, String tableName);
}