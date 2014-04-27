package com.song7749.dl.dbclient.service;

import java.util.List;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.TableVO;

public interface DatabaseInfoManager {

	List<TableVO> getTableVOList(ServerInfo serverInfo);

	List<FieldVO> getFieldVOList(ServerInfo serverInfo,String tableName);
}