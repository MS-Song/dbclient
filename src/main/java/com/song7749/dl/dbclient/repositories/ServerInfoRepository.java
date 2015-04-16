package com.song7749.dl.dbclient.repositories;

import java.util.List;

import com.song7749.dl.base.Repository;
import com.song7749.dl.dbclient.dto.FindServerInfoListDTO;
import com.song7749.dl.dbclient.entities.ServerInfo;

/**
 * <pre>
 * Class Name : ServerInfoRepository.java
 * Description : ServerInfo Repository
 * CRUD ServerInfo and find ServerInfo
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2015. 4. 16.		minsoo		new write
 *
 * </pre>
 *
 * @author minsoo
 * @since 2015. 4. 16.
 */
public interface ServerInfoRepository extends Repository<ServerInfo>{

	/**
	 * ServerList 조회
	 * @param dto
	 * @return List<ServerInfo>
	 */
	List<ServerInfo> findServerInfoList(FindServerInfoListDTO dto);
}
