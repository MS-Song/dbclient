package com.song7749.dl.dbclient.dao;

import java.util.List;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.TableVO;

public interface DatabaseInfoDao {

	List<TableVO> selectTableVOList(ServerInfo serverInfo);

	List<FieldVO> selectTableFieldVOList(ServerInfo serverInfo,String tableName);

	List<IndexVO> selectTableIndexVOList(ServerInfo serverInfo,String tableName);
}
