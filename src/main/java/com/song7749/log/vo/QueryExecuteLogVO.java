package com.song7749.log.vo;

import java.util.Date;

import com.song7749.dl.base.AbstractVo;

/**
 * <pre>
 * Class Name : QueryExecuteLogVO.java
 * Description : 쿼리 실행 로그 조회 결과 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public class QueryExecuteLogVO extends AbstractVo{

	private static final long serialVersionUID = -2823225194560318715L;

	private Integer queryExeucteSeq;

	private String id;

	private String ip;

	private String host;

	private String hostAlias;

	private String schemaName;

	private String account;

	private String query;

	private Date executeDate;

	public QueryExecuteLogVO() {}

	public QueryExecuteLogVO(Integer queryExeucteSeq, String id, String ip,
			String host, String hostAlias, String schemaName, String account,
			String query, Date executeDate) {
		this.queryExeucteSeq = queryExeucteSeq;
		this.id = id;
		this.ip = ip;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.query = query;
		this.executeDate = executeDate;
	}

	public Integer getQueryExeucteSeq() {
		return queryExeucteSeq;
	}

	public void setQueryExeucteSeq(Integer queryExeucteSeq) {
		this.queryExeucteSeq = queryExeucteSeq;
	}

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

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
}