package com.song7749.dl.dbclient.service;

import java.util.ArrayList;
import java.util.List;

import com.song7749.dl.dbclient.entities.ServerInfo;
import com.song7749.dl.dbclient.vo.ServerInfoVO;

/**
 * <pre>
 * Class Name : ServerInfoConvert.java
 * Description : convert
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