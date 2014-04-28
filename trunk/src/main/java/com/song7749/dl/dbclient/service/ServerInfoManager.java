package com.song7749.dl.dbclient.service;

import java.util.List;

import com.song7749.dl.dbclient.dto.DeleteServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoDTO;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.dto.FindTableDTO;
import com.song7749.dl.dbclient.dto.ModifyServerInfoDTO;
import com.song7749.dl.dbclient.dto.SaveServerInfoDTO;
import com.song7749.dl.dbclient.vo.FieldVO;
import com.song7749.dl.dbclient.vo.IndexVO;
import com.song7749.dl.dbclient.vo.ServerInfoVO;
import com.song7749.dl.dbclient.vo.TableVO;

public interface ServerInfoManager {

	void saveServerInfo(SaveServerInfoDTO dto);

	void modifyServerInfo(ModifyServerInfoDTO dto);

	void deleteServerInfo(DeleteServerInfoDTO dto);

	ServerInfoVO findServerInfo(FindServerInfoDTO dto);

	List<ServerInfoVO> findServerInfoList(FindServerInfoListDTO dto);

	void saveServerInfoFacade(List<SaveServerInfoDTO> list);

	void modifyServerInfoFacade(List<ModifyServerInfoDTO> list);

	List<TableVO> findTableVOList(FindTableDTO dto);

	List<FieldVO> findTableFieldVOList(FindTableDTO dto);

	List<IndexVO> findTableIndexVOList(FindTableDTO dto);
}
