package com.song7749.dbclient.value;

import java.util.Date;

import com.song7749.base.AbstractVo;

public class LogQueryVo extends AbstractVo {

	private static final long serialVersionUID = 8473352501114190074L;

	private Long id;

	private String ip;

	private Date date;

	private String loginId;

	private Long databaseId;

	private String host;

	private String hostAlias;

	private String schemaName;

	private String account;

	private String query;

	public LogQueryVo() {}

	/**
	 * @param id
	 * @param ip
	 * @param date
	 * @param loginId
	 * @param databaseId
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param query
	 */
	public LogQueryVo(Long id, String ip, Date date, String loginId, Long databaseId, String host, String hostAlias,
			String schemaName, String account, String query) {
		this.id = id;
		this.ip = ip;
		this.date = date;
		this.loginId = loginId;
		this.databaseId = databaseId;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.query = query;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
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
}