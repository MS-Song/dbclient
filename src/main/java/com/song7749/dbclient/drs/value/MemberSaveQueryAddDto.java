package com.song7749.dbclient.drs.value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 저장된 SQL")
public class MemberSaveQueryAddDto extends AbstractDto {

	private static final long serialVersionUID = -2575169297543693449L;

	@ApiModelProperty(value="회원ID", example="로그인 또는 인증키가 있을 경우에는 필요 없음",hidden=true)
	private Long memberId;

	@ApiModelProperty("Query 메모")
	@NotBlank
	@Size(min = 4)
	private String memo;

	@ApiModelProperty("Query 내용")
	@NotBlank
	@Size(min = 10, max=8000)
	private String query;

	@ApiModelProperty
	@NotNull
	private Long databaseId;

	public MemberSaveQueryAddDto() {}

	/**
	 * @param memberId
	 * @param memo
	 * @param query
	 * @param databaseId
	 */
	public MemberSaveQueryAddDto(Long memberId, @NotBlank @Size(min = 4) String memo,
			@NotBlank @Size(min = 10, max = 8000) String query, @NotNull Long databaseId) {
		this.memberId = memberId;
		this.memo = memo;
		this.query = query;
		this.databaseId = databaseId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getMemo() {
		return memo;
	}

	public String getQuery() {
		return query;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}
}