package com.song7749.dbclient.value;

import com.song7749.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("회원의 저장된 쿼리 조회")
public class MemberSaveQueryFindDto extends AbstractDto {

	private static final long serialVersionUID = -9195231766212079042L;

	@ApiModelProperty("회원ID")
	private Long memberId;

	@ApiModelProperty("Database ID")
	private Long databaseId;

	@ApiModelProperty("저장된 Query ID")
	private Long memberSaveQueryId;

	public MemberSaveQueryFindDto() {}

	public MemberSaveQueryFindDto(Long memberId) {
		this.memberId = memberId;
	}

	public MemberSaveQueryFindDto(Long memberId, Long databaseId) {
		this.memberId = memberId;
		this.databaseId = databaseId;
	}

	public MemberSaveQueryFindDto(Long memberId, Long databaseId, Long memberSaveQueryId) {
		this.memberId = memberId;
		this.databaseId = databaseId;
		this.memberSaveQueryId = memberSaveQueryId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public Long getMemberSaveQueryId() {
		return memberSaveQueryId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setMemberSaveQueryId(Long memberSaveQueryId) {
		this.memberSaveQueryId = memberSaveQueryId;
	}
}