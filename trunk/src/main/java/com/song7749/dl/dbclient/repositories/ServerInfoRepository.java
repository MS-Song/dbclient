package com.song7749.dl.dbclient.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;

public interface ServerInfoRepository extends Repository<ServerInfo>{

	/**
	 * ServerList 조회
	 * @param dto
	 * @return List<ServerInfo>
	 */
	List<ServerInfo> findServerInfoList(FindServerInfoListDTO dto);
}
