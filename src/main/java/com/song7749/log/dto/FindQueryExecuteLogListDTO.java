package com.song7749.log.dto;

import java.util.Date;

import com.song7749.dl.base.AbstractDto;

/**
 * <pre>
 * Class Name : FindQueryExecuteLogListDTO.java
 * Description : 실행 쿼리 리스트 조회
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 22.
*/
public class FindQueryExecuteLogListDTO extends AbstractDto{

	private static final long serialVersionUID = 2754019375376834746L;

	private String id;

	private String ip;

	private String host;

	private String hostAlias;

	private String schemaName;

	private String account;

	private String query;

	private Date startDate;

	private Date endDate;

	public FindQueryExecuteLogListDTO() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostAlias() {
		return hostAlias;
	}

	public void setHostAlias(String hostAlias) {
		this.hostAlias = hostAlias;
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

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}