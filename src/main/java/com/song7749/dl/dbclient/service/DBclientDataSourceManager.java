package com.song7749.dl.dbclient.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.song7749.dl.dbclient.dto.ExecuteResultListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

public interface DBclientDataSourceManager {

	Connection getConnection(ServerInfo serverInfo) throws SQLException ;

	List<TableVO> selectTableVOList(ServerInfo serverInfo);

	List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo, String tableName);

	List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo, String tableName);

	List<Map<String,String>> executeQueryList(ServerInfo serverInfo, ExecuteResultListDTO dto);
}