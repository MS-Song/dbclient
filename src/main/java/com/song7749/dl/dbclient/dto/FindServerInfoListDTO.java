package com.song7749.dl.dbclient.dto;

import java.util.ArrayList;
import java.util.List;

import com.song7749.dl.base.AbstractDto;

public class FindServerInfoListDTO extends AbstractDto{

	private static final long serialVersionUID = -2933768698426325461L;

	private String host;

	private String schemaName;

	private String account;

	private List<Integer> serverInfoSeqList = new ArrayList<Integer>();

	public FindServerInfoListDTO() {}

	public FindServerInfoListDTO(boolean useCache) {
		super.setUseCache(useCache);
	}

	public FindServerInfoListDTO(String host, boolean useCache) {
		this.host = host;
		super.setUseCache(useCache);
	}

	public FindServerInfoListDTO(String host, String schemaName, String account, boolean useCache) {
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		super.setUseCache(useCache);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public List<Integer> getServerInfoSeqList() {
		return serverInfoSeqList;
	}

	public void setServerInfoSeqList(List<Integer> serverInfoSeqList) {
		this.serverInfoSeqList = serverInfoSeqList;
	}
}