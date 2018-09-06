package com.song7749.log.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.log.type.LogType;
import com.song7749.log.value.LogQueryVo;

/**
 * <pre>
 * Class Name : LogQuery.java
 * Description : 유저가 사용한 쿼리에 대한 로그
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
@Entity
@DiscriminatorValue(LogType.QUERY)
public class LogQuery extends Log{

	private static final long serialVersionUID = 240902778616234461L;

	@NotBlank
	@Column(nullable = false, updatable = false)
	private String loginId;

	@NotNull
	@Column(nullable=false,updatable=false)
	private Long databaseId;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String host;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String hostAlias;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String schemaName;

	@Column(nullable=false, updatable=false)
	@NotBlank
	private String account;

	@Lob
	@Column(nullable=false, updatable=false)
	@NotBlank
	@Size(max=12000)
	private String query;

	public LogQuery() {}

	/**
	 * @param loginId
	 * @param databaseId
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param query
	 */
	public LogQuery(@NotBlank @Size(min = 4, max = 20) String loginId, @NotBlank Long databaseId, @NotBlank String host,
			@NotBlank String hostAlias, @NotBlank String schemaName, @NotBlank String account,
			@NotBlank @Size(max = 12000) String query) {
		this.loginId = loginId;
		this.databaseId = databaseId;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.query = query;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
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

	public LogQueryVo getLogLoginVo(ModelMapper mapper) {
		return mapper.map(this, LogQueryVo.class);
	}
}