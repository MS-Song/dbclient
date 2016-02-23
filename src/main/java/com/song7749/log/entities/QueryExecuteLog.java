package com.song7749.log.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.Entities;
import com.song7749.util.validate.ValidateGroupInsert;

/**
 * <pre>
 * Class Name : QueryExecuteLog.java
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
public class QueryExecuteLog extends Entities{

	private static final long serialVersionUID = -7830990519208492192L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer queryExeucteSeq;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	@Size(min=4,max=20)
	private String id;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String ip;


	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String host;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String hostAlias;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String schemaName;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String account;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	@Size(max=4000)
	private String query;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private Date executeDate;

	public QueryExecuteLog() {}

	public QueryExecuteLog(String id, String ip,
			String host, String hostAlias, String schemaName, String account,
			String query, Date executeDate) {
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