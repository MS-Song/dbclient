package com.song7749.log.value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.base.AbstractDto;
import com.song7749.log.domain.LogQuery;

public class LogQueryAddDto  extends AbstractDto {

	private static final long serialVersionUID = -60340203267080918L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@NotBlank
	private String loginId;

	@NotNull
	private Long databaseId;

	@NotBlank
	private String host;

	@NotBlank
	private String hostAlias;

	@NotBlank
	private String schemaName;

	@NotBlank
	private String account;

	@NotBlank
	@Size(max=8000)
	private String query;

	public LogQueryAddDto() {}

	/**
	 * @param ip
	 * @param loginId
	 * @param databaseId
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param query
	 */
	public LogQueryAddDto(@NotBlank @Size(min = 8, max = 64) String ip,
			@NotBlank @Size(min = 4, max = 20) String loginId, @NotNull Long databaseId, @NotBlank String host,
			@NotBlank String hostAlias, @NotBlank String schemaName, @NotBlank String account,
			@NotBlank @Size(max = 8000) String query) {
		this.ip = ip;
		this.loginId = loginId;
		this.databaseId = databaseId;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.query = query;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public LogQuery getLogQuery(ModelMapper mapper) {
		return mapper.map(this, LogQuery.class);
	}

}