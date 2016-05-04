package com.song7749.dl.member.vo;

import com.song7749.dl.base.AbstractVo;
import com.song7749.dl.dbclient.entities.ServerInfo;

/**
 * <pre>
 * Class Name : MemberDatabaseVO.java
 * Description : Member 와 연결되어 있는 Database 의 정보를 조회 한다.
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 5. 3.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 5. 3.
*/
public class MemberDatabaseVO extends AbstractVo{

	private static final long serialVersionUID = 6900101234791839070L;

	private ServerInfo serverInfo;

	public MemberDatabaseVO() {}

	public MemberDatabaseVO(ServerInfo serverInfo) {
		super();
		this.serverInfo = serverInfo;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}
}
