package com.song7749.dl.dbclient.dto;

import com.song7749.dl.base.Dto;

public class DeleteServerInfoDTO extends Dto{

	private static final long serialVersionUID = 7409423994013008959L;

	private Integer serverInfoSeq;

	public DeleteServerInfoDTO() {}

	public DeleteServerInfoDTO(Integer serverInfoSeq) {

		this.serverInfoSeq = serverInfoSeq;
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}
}
