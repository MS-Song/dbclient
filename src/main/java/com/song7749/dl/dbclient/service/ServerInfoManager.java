package com.song7749.dl.dbclient.service;

import java.util.List;

import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;

public interface ServerInfoManager {

	void saveServerInfo(SaveServerInfoDTO dto);

	void modifyServerInfo(ModifyServerInfoDTO dto);

	void deleteServerInfo(DeleteServerInfoDTO dto);

	ServerInfoVO findServerInfo(FindServerInfoDTO dto);

	List<ServerInfoVO> findServerInfoList(FindServerInfoListDTO dto);
}
