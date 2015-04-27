package com.song7749.dl.dbclient.convert;

import java.util.ArrayList;
import java.util.List;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.ServerInfoVO;

/**
 * <pre>
 * Class Name : ServerInfoConvert.java
 * Description : 서버 관련 정보 convert
 * entity 또는 MAP 객체를 VO 로 치환 한다.
 *
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 26.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 26.
 */
public class ServerInfoConvert {

	/**
	 * ServerInfo to ServerInfoVO
	 * @param serverInfo
	 * @return ServerInfoVO
	 */
	public static ServerInfoVO convert(ServerInfo serverInfo){
		if(null==serverInfo){
			return null;
		}
		return new ServerInfoVO(
				serverInfo.getServerInfoSeq(),
				serverInfo.getHost(),
				serverInfo.getSchemaName(),
				serverInfo.getAccount(),
				serverInfo.getPassword(),
				serverInfo.getDriver(),
				serverInfo.getCharset(),
				serverInfo.getPort());
	}

	/**
	 * List<ServerInfo> to List<ServerInfoVO>
	 * @param serverInfoList
	 * @return List<ServerInfoVO>
	 */
	public static List<ServerInfoVO> convert(List<ServerInfo> serverInfoList){
		if(null==serverInfoList){
			return null;
		}
		List<ServerInfoVO> list = new ArrayList<ServerInfoVO>();
		for (ServerInfo serverInfo : serverInfoList) {
			list.add(convert(serverInfo));
		}
		return list;
	}
}