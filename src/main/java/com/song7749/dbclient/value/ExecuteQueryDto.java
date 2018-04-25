package com.song7749.dbclient.value;

import javax.validation.constraints.NotNull;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Query 실행")
public class ExecuteQueryDto extends AbstractDto{

	private static final long serialVersionUID = 7378998437614912567L;

	@NotNull
	private Long id;

	private String loginId;

	private String ip;

	private String query;

	private String name;

	private boolean autoCommit = false;

	private boolean htmlAllow = false;

	private boolean usePLSQL = false;

	@ApiModelProperty("최대 조회 개수")
	private Long limit = 100L;
	/**
	 * offset.<br/>
	 */
	@ApiModelProperty("조회 시작 Offset")
	private Long offset = 0L;

	/**
	 * limit 사용 여부 .<br/>
	 */
	@ApiModelProperty("Result 수를 지정할 것인가?")
	private boolean useLimit = true;

	public ExecuteQueryDto() {}

	/**
	 * @param databaseId
	 * @param loginId
	 */
	public ExecuteQueryDto(@NotNull Long id, String loginId) {
		this.id = id;
		this.loginId = loginId;
	}

	/**
	 * @param databaseId
	 * @param loginId
	 * @param name
	 */
	public ExecuteQueryDto(@NotNull Long id, String loginId, String name) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.name = name;
	}

	/**
	 * @param databaseId
	 * @param loginId
	 * @param name
	 * @param useCache
	 * @param apikey
	 */
	public ExecuteQueryDto(@NotNull Long id, String loginId, String name, boolean useCache, String apikey) {
		super(useCache, apikey);
		this.id = id;
		this.loginId = loginId;
		this.name = name;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public boolean isHtmlAllow() {
		return htmlAllow;
	}

	public void setHtmlAllow(boolean htmlAllow) {
		this.htmlAllow = htmlAllow;
	}

	public boolean isUsePLSQL() {
		return usePLSQL;
	}

	public void setUsePLSQL(boolean usePLSQL) {
		this.usePLSQL = usePLSQL;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public boolean isUseLimit() {
		return useLimit;
	}

	public void setUseLimit(boolean useLimit) {
		this.useLimit = useLimit;
	}
}